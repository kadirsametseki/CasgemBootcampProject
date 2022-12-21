package com.kodlamaio.bootcampProject.business.abstracts.users;

import java.util.List;

import com.kodlamaio.bootcampProject.business.requests.instructor.CreateInstructorRequest;
import com.kodlamaio.bootcampProject.business.requests.instructor.UpdateInstructorRequest;
import com.kodlamaio.bootcampProject.business.responses.instructor.CreateInstructorResponse;
import com.kodlamaio.bootcampProject.business.responses.instructor.GetAllInstructorResponse;
import com.kodlamaio.bootcampProject.business.responses.instructor.GetInstructorResponse;
import com.kodlamaio.bootcampProject.business.responses.instructor.UpdateInstructorResponse;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;

public interface InstructorService {
	Result delete(int id);
	DataResult<CreateInstructorResponse> add(CreateInstructorRequest createInstructorRequest);
	DataResult<UpdateInstructorResponse> update(UpdateInstructorRequest updateInstructorRequest);
	DataResult<List<GetAllInstructorResponse>> getAll();
	DataResult<GetInstructorResponse> getById(int id);
}
