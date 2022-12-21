package com.kodlamaio.bootcampProject.business.concretes.applications;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.bootcampProject.business.abstracts.applications.ApplicationService;
import com.kodlamaio.bootcampProject.business.abstracts.blacklists.BlacklistService;
import com.kodlamaio.bootcampProject.business.abstracts.bootcamps.BootcampService;
import com.kodlamaio.bootcampProject.business.abstracts.users.ApplicantService;
import com.kodlamaio.bootcampProject.business.constants.Messages;
import com.kodlamaio.bootcampProject.business.requests.application.CreateApplicationRequest;
import com.kodlamaio.bootcampProject.business.requests.application.UpdateApplicationRequest;
import com.kodlamaio.bootcampProject.business.responses.application.CreateApplicationResponse;
import com.kodlamaio.bootcampProject.business.responses.application.GetAllApplicationsResponse;
import com.kodlamaio.bootcampProject.business.responses.application.GetApplicationResponse;
import com.kodlamaio.bootcampProject.business.responses.application.UpdateApplicationResponse;
import com.kodlamaio.bootcampProject.core.utilities.exceptions.BusinessException;
import com.kodlamaio.bootcampProject.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessDataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessResult;
import com.kodlamaio.bootcampProject.dataAccess.abstracts.applications.ApplicationRepository;
import com.kodlamaio.bootcampProject.entities.applications.Application;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ApplicationManager implements ApplicationService {

	private ModelMapperService modelMapperService;
	private ApplicationRepository applicationRepository;
	private BootcampService bootcampService;
	private ApplicantService applicantService;
	private BlacklistService blacklistService;

	@Override
	public Result delete(int id) {
		checkIfApplicationExistsById(id);
		this.applicationRepository.deleteById(id);
		return new SuccessResult(Messages.ApplicationDeleted);
	}

	@Override
	public DataResult<CreateApplicationResponse> add(CreateApplicationRequest createApplicationRequest) {
		Application application = this.modelMapperService.forRequest().map(createApplicationRequest, Application.class);
		application.setId(0);
		this.applicationRepository.save(application);

		CreateApplicationResponse createApplicationResponse = this.modelMapperService.forResponse().map(application,
				CreateApplicationResponse.class);
		return new SuccessDataResult<CreateApplicationResponse>(createApplicationResponse, Messages.ApplicationCreated);

	}

	@Override
	public DataResult<UpdateApplicationResponse> update(UpdateApplicationRequest updateApplicationRequest) {
		checkIfApplicantExistsById(updateApplicationRequest.getApplicantId());
		checkIfBootcampExistsById(updateApplicationRequest.getBootcampId());
		checkIfExistsApplicationByBlackListId(updateApplicationRequest.getApplicantId());
		checkIfApplicationExistsById(updateApplicationRequest.getId());
		Application application = this.modelMapperService.forRequest().map(updateApplicationRequest, Application.class);
		this.applicationRepository.save(application);

		UpdateApplicationResponse updateApplicationResponse = this.modelMapperService.forResponse().map(application,
				UpdateApplicationResponse.class);
		return new SuccessDataResult<UpdateApplicationResponse>(updateApplicationResponse, Messages.ApplicationUpdated);
	}

	@Override
	public DataResult<List<GetAllApplicationsResponse>> getAll() {
		List<Application> applications = this.applicationRepository.findAll();
		List<GetAllApplicationsResponse> applicationsResponse = applications.stream()
				.map(application -> modelMapperService.forResponse().map(application, GetAllApplicationsResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(applicationsResponse, Messages.ApplicationListed);
	}

	@Override
	public DataResult<GetApplicationResponse> getById(int id) {
		checkIfApplicationExistsById(id);
		Application application = this.applicationRepository.findById(id).get();
		GetApplicationResponse getApplicationResponse = this.modelMapperService.forResponse().map(application,
				GetApplicationResponse.class);

		return new SuccessDataResult<GetApplicationResponse>(getApplicationResponse, Messages.ApplicationListedById);
	}

	private void checkIfApplicationExistsById(int id) {
		Application application = this.applicationRepository.findById(id).orElse(null);
		if (application == null) {
			throw new BusinessException(Messages.ApplicationNotExists);
		}
	}

	private void checkIfApplicantExistsById(int applicantId) {
		var result = this.applicantService.getById(applicantId);
		if (result == null) {
			throw new BusinessException(Messages.ApplicantNotExists);
		}
	}

	private void checkIfBootcampExistsById(int bootcampId) {
		var result = this.bootcampService.getById(bootcampId);
		if (result == null) {
			throw new BusinessException(Messages.BootcampNotExists);
		}
	}
	
	private void checkIfExistsApplicationByBlackListId(int id) {
		var result = blacklistService.getByApplicantId(id);
		if (result != null) {
			throw new BusinessException(Messages.InBlacklist);
		}
	}
	

}
