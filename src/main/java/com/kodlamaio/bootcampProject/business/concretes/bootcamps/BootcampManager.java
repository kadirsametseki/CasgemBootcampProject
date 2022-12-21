package com.kodlamaio.bootcampProject.business.concretes.bootcamps;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.bootcampProject.business.abstracts.bootcamps.BootcampService;
import com.kodlamaio.bootcampProject.business.abstracts.users.InstructorService;
import com.kodlamaio.bootcampProject.business.constants.Messages;
import com.kodlamaio.bootcampProject.business.requests.bootcamp.CreateBootcampRequest;
import com.kodlamaio.bootcampProject.business.requests.bootcamp.UpdateBootcampRequest;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.CreateBootcampResponse;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.GetAllBootcampsResponse;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.GetBootcampResponse;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.UpdateBootcampResponse;
import com.kodlamaio.bootcampProject.core.utilities.exceptions.BusinessException;
import com.kodlamaio.bootcampProject.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessDataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessResult;
import com.kodlamaio.bootcampProject.dataAccess.abstracts.bootcamps.BootcampRepository;
import com.kodlamaio.bootcampProject.entities.bootcamps.Bootcamp;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BootcampManager implements BootcampService {

	private ModelMapperService modelMapperService;
	private BootcampRepository bootcampRepository;
	private InstructorService instructorService;

	@Override
	public Result delete(int id) {
		checkIfBootcampExistsById(id);
		this.bootcampRepository.deleteById(id);
		return new SuccessResult(Messages.BootcampDeleted);
	}

	@Override
	public DataResult<CreateBootcampResponse> add(CreateBootcampRequest createBootcampRequest) {
		checkIfInstructorExistsById(createBootcampRequest.getInstructorId());
		Bootcamp bootcamp = this.modelMapperService.forRequest().map(createBootcampRequest, Bootcamp.class);
		bootcamp.setId(0);
		this.bootcampRepository.save(bootcamp);

		CreateBootcampResponse createBootcampResponse = this.modelMapperService.forResponse().map(bootcamp,
				CreateBootcampResponse.class);
		return new SuccessDataResult<CreateBootcampResponse>(createBootcampResponse, Messages.BootcampCreated);
	}

	@Override
	public DataResult<UpdateBootcampResponse> update(UpdateBootcampRequest updateBootcampRequest) {
		checkIfInstructorExistsById(updateBootcampRequest.getInstructorId());
		checkIfBootcampExistsById(updateBootcampRequest.getId());
		Bootcamp bootcamp = this.modelMapperService.forRequest().map(updateBootcampRequest, Bootcamp.class);
		this.bootcampRepository.save(bootcamp);

		UpdateBootcampResponse updateBootcampResponse = this.modelMapperService.forResponse().map(bootcamp,
				UpdateBootcampResponse.class);
		return new SuccessDataResult<UpdateBootcampResponse>(updateBootcampResponse, Messages.BootcampUpdated);
	}

	@Override
	public DataResult<List<GetAllBootcampsResponse>> getAll() {
		List<Bootcamp> bootcamps = this.bootcampRepository.findAll();
		List<GetAllBootcampsResponse> bootcampsResponse = bootcamps.stream()
				.map(bootcamp -> modelMapperService.forResponse().map(bootcamp, GetAllBootcampsResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllBootcampsResponse>>(bootcampsResponse, Messages.BootcampListed);
	}

	@Override
	public DataResult<GetBootcampResponse> getById(int id) {
		checkIfBootcampExistsById(id);
		Bootcamp bootcamp = this.bootcampRepository.findById(id).get();
		GetBootcampResponse getBootcampResponse = this.modelMapperService.forResponse().map(bootcamp,
				GetBootcampResponse.class);

		return new SuccessDataResult<GetBootcampResponse>(getBootcampResponse, Messages.BootcampListedById);
	}

	private void checkIfInstructorExistsById(int instructorId) {
		var result = this.instructorService.getById(instructorId);
		if (result == null) {
			throw new BusinessException(Messages.InstructorNotExists);
		}
	}

	private void checkIfBootcampExistsById(int id) {
		Bootcamp bootcamp = this.bootcampRepository.findById(id).orElse(null);
		if (bootcamp == null) {
			throw new BusinessException(Messages.BootcampNotExists);
		}
	}

}