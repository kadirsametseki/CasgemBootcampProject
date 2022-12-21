package com.kodlamaio.bootcampProject.business.concretes.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.bootcampProject.business.abstracts.users.ApplicantService;
import com.kodlamaio.bootcampProject.business.constants.Messages;
import com.kodlamaio.bootcampProject.business.requests.applicant.CreateApplicantRequest;
import com.kodlamaio.bootcampProject.business.requests.applicant.UpdateApplicantRequest;
import com.kodlamaio.bootcampProject.business.responses.applicant.CreateApplicantResponse;
import com.kodlamaio.bootcampProject.business.responses.applicant.GetAllApplicantsResponse;
import com.kodlamaio.bootcampProject.business.responses.applicant.GetApplicantResponse;
import com.kodlamaio.bootcampProject.business.responses.applicant.UpdateApplicantResponse;
import com.kodlamaio.bootcampProject.core.utilities.exceptions.BusinessException;
import com.kodlamaio.bootcampProject.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessDataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessResult;
import com.kodlamaio.bootcampProject.dataAccess.abstracts.users.ApplicantRepository;
import com.kodlamaio.bootcampProject.entities.users.Applicant;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicantManager implements ApplicantService {

	private ApplicantRepository applicantRepository;
	private ModelMapperService modelMapperService;

	@Override
	public Result delete(int id) {
		checkIfApplicantExistsById(id);
		this.applicantRepository.deleteById(id);
		return new SuccessResult(Messages.ApplicantDeleted);
	}

	@Override
	public DataResult<CreateApplicantResponse> add(CreateApplicantRequest createApplicantRequest) {
		checkIfApplicantExistsByNationalIdentity(createApplicantRequest.getNationalIdentity());
		Applicant applicant = modelMapperService.forRequest().map(createApplicantRequest, Applicant.class);
		applicantRepository.save(applicant);
		CreateApplicantResponse response = this.modelMapperService.forResponse().map(applicant,
				CreateApplicantResponse.class);
		return new SuccessDataResult<CreateApplicantResponse>(response, Messages.ApplicantCreated);

	}

	@Override
	public DataResult<UpdateApplicantResponse> update(UpdateApplicantRequest updateApplicantRequest) {
		checkIfApplicantExistsById(updateApplicantRequest.getId());
		checkIfApplicantExistsByNationalIdentity(updateApplicantRequest.getNationalIdentity());
		Applicant applicant = this.modelMapperService.forRequest().map(updateApplicantRequest, Applicant.class);
		this.applicantRepository.save(applicant);
		UpdateApplicantResponse updateApplicantResponse = this.modelMapperService.forResponse().map(applicant,
				UpdateApplicantResponse.class);
		return new SuccessDataResult<UpdateApplicantResponse>(updateApplicantResponse, Messages.ApplicantUpdated);

	}

	@Override
	public DataResult<List<GetAllApplicantsResponse>> getAll() {
		List<Applicant> applicants = applicantRepository.findAll();
		List<GetAllApplicantsResponse> getAllApplicantResponses = applicants.stream()
				.map(applicant -> modelMapperService.forResponse().map(applicant, GetAllApplicantsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllApplicantsResponse>>(getAllApplicantResponses,
				Messages.ApplicantListed);
	}

	@Override
	public DataResult<GetApplicantResponse> getById(int id) {
		checkIfApplicantExistsById(id);
		Applicant applicant = this.applicantRepository.findById(id).get();
		GetApplicantResponse getApplicantResponse = this.modelMapperService.forResponse().map(applicant,
				GetApplicantResponse.class);

		return new SuccessDataResult<GetApplicantResponse>(getApplicantResponse);
	}

	private void checkIfApplicantExistsByNationalIdentity(String nationalIdentity) {
		Applicant applicant = this.applicantRepository.findByNationalIdentity(nationalIdentity);
		if (applicant != null) {
			throw new BusinessException(Messages.ApplicantExists);
		}
	}

	private void checkIfApplicantExistsById(int id) {
		Applicant applicant = applicantRepository.findById(id).orElse(null);
		if (applicant == null) {
			throw new BusinessException(Messages.ApplicantNotExists);
		}
	}
	// Applicant findByNationalIdentity(String nationalIdentity); Repository'den
	// alındı.
}
