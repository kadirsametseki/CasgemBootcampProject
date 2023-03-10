package com.kodlamaio.bootcampProject.business.concretes.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.bootcampProject.business.abstracts.users.InstructorService;
import com.kodlamaio.bootcampProject.business.constants.Messages;
import com.kodlamaio.bootcampProject.business.requests.instructor.CreateInstructorRequest;
import com.kodlamaio.bootcampProject.business.requests.instructor.UpdateInstructorRequest;
import com.kodlamaio.bootcampProject.business.responses.instructor.CreateInstructorResponse;
import com.kodlamaio.bootcampProject.business.responses.instructor.GetAllInstructorResponse;
import com.kodlamaio.bootcampProject.business.responses.instructor.GetInstructorResponse;
import com.kodlamaio.bootcampProject.business.responses.instructor.UpdateInstructorResponse;
import com.kodlamaio.bootcampProject.core.utilities.exceptions.BusinessException;
import com.kodlamaio.bootcampProject.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessDataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessResult;
import com.kodlamaio.bootcampProject.dataAccess.abstracts.users.InstructorRepository;
import com.kodlamaio.bootcampProject.entities.users.Instructor;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InstructorManager implements InstructorService {

	private InstructorRepository instructorRepository;
	private ModelMapperService modelMapperService;

	@Override
	public Result delete(int id) {
		checkIfInstructorExistsById(id);
		this.instructorRepository.deleteById(id);

		return new SuccessResult(Messages.InstructorDeleted);

	}

	@Override
	public DataResult<CreateInstructorResponse> add(CreateInstructorRequest createInstructorRequest) {
		checkIfInstructorExistsByNationalIdentity(createInstructorRequest.getNationalIdentity());
		Instructor instructor = this.modelMapperService.forRequest().map(createInstructorRequest, Instructor.class);

		this.instructorRepository.save(instructor);

		CreateInstructorResponse createInstructorResponse = this.modelMapperService.forResponse().map(instructor,
				CreateInstructorResponse.class);

		return new SuccessDataResult<CreateInstructorResponse>(createInstructorResponse, Messages.InstructorCreated);
	}

	@Override
	public DataResult<List<GetAllInstructorResponse>> getAll() {
		List<Instructor> instructors = this.instructorRepository.findAll();

		List<GetAllInstructorResponse> response = instructors.stream().map(
				instructor -> this.modelMapperService.forResponse().map(instructor, GetAllInstructorResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllInstructorResponse>>(response, Messages.InstructorListed);
	}

	@Override
	public DataResult<UpdateInstructorResponse> update(UpdateInstructorRequest updateInstructorRequest) {
		checkIfInstructorExistsById(updateInstructorRequest.getId());
		checkIfInstructorExistsByNationalIdentity(updateInstructorRequest.getNationalIdentity());
		Instructor instructor = this.modelMapperService.forRequest().map(updateInstructorRequest, Instructor.class);

		this.instructorRepository.save(instructor);

		UpdateInstructorResponse response = this.modelMapperService.forResponse().map(instructor,
				UpdateInstructorResponse.class);

		return new SuccessDataResult<UpdateInstructorResponse>(response, Messages.InstructorUpdated);
	}

	@Override
	public DataResult<GetInstructorResponse> getById(int id) {
		checkIfInstructorExistsById(id);
		Instructor instructor = this.instructorRepository.findById(id).get();

		GetInstructorResponse response = this.modelMapperService.forResponse().map(instructor,
				GetInstructorResponse.class);

		return new SuccessDataResult<GetInstructorResponse>(response);
	}

	private void checkIfInstructorExistsByNationalIdentity(String nationalIdentity) {
		Instructor instructor = this.instructorRepository.findByNationalIdentity(nationalIdentity);
		if (instructor != null) {
			throw new BusinessException(Messages.InstructorExists);
		}
	}

	private void checkIfInstructorExistsById(int id) {
		Instructor instructor = this.instructorRepository.findById(id).orElse(null);
		if (instructor == null) {
			throw new BusinessException(Messages.InstructorNotExists);
		}
	}

	// Instructor findByNationalIdentity(String nationalIdentity); repository'den
	// al??nd??.

}
