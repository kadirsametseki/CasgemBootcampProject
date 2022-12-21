package com.kodlamaio.bootcampProject.business.concretes.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.bootcampProject.business.abstracts.users.EmployeeService;
import com.kodlamaio.bootcampProject.business.constants.Messages;
import com.kodlamaio.bootcampProject.business.requests.employee.CreateEmployeeRequest;
import com.kodlamaio.bootcampProject.business.requests.employee.UpdateEmployeeRequest;
import com.kodlamaio.bootcampProject.business.responses.employee.CreateEmployeeResponse;
import com.kodlamaio.bootcampProject.business.responses.employee.GetAllEmployeesResponse;
import com.kodlamaio.bootcampProject.business.responses.employee.GetEmployeeResponse;
import com.kodlamaio.bootcampProject.business.responses.employee.UpdateEmployeeResponse;
import com.kodlamaio.bootcampProject.core.utilities.exceptions.BusinessException;
import com.kodlamaio.bootcampProject.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessDataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessResult;
import com.kodlamaio.bootcampProject.dataAccess.abstracts.users.EmployeeRepository;
import com.kodlamaio.bootcampProject.entities.users.Employee;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeManager implements EmployeeService{

	private ModelMapperService modelMapperService;
	private EmployeeRepository employeeRepository;
	
	@Override
	public Result delete(int id) {
		checkIfEmployeeExistsById(id);
		this.employeeRepository.deleteById(id);
		return new SuccessResult(Messages.EmployeeDeleted);
	}

	@Override
	public DataResult<CreateEmployeeResponse> add(CreateEmployeeRequest createEmployeeRequest) {
		checkIfEmployeeExistsByNationalIdentity(createEmployeeRequest.getNationalIdentity());
		Employee employee = this.modelMapperService.forRequest().map(createEmployeeRequest, Employee.class);
		this.employeeRepository.save(employee);

		CreateEmployeeResponse createEmployeeResponse = this.modelMapperService.forResponse().map(employee,
				CreateEmployeeResponse.class);
		return new SuccessDataResult<CreateEmployeeResponse>(createEmployeeResponse, Messages.EmployeeCreated);

	}

	@Override
	public DataResult<UpdateEmployeeResponse> update(UpdateEmployeeRequest updateEmployeeRequest) {
		checkIfEmployeeExistsById(updateEmployeeRequest.getId());
		checkIfEmployeeExistsByNationalIdentity(updateEmployeeRequest.getNationalIdentity());
		Employee employee = this.modelMapperService.forRequest().map(updateEmployeeRequest, Employee.class);
		this.employeeRepository.save(employee);

		UpdateEmployeeResponse updateEmployeeResponse = this.modelMapperService.forResponse().map(employee,
				UpdateEmployeeResponse.class);
		return new SuccessDataResult<UpdateEmployeeResponse>(updateEmployeeResponse, Messages.EmployeeUpdated);
	}

	@Override
	public DataResult<List<GetAllEmployeesResponse>> getAll() {

		List<Employee> employees = this.employeeRepository.findAll();
		List<GetAllEmployeesResponse> employeesResponse = employees.stream()
				.map(employee -> this.modelMapperService.forResponse().map(employee, GetAllEmployeesResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllEmployeesResponse>>(employeesResponse);

	}

	@Override
	public DataResult<GetEmployeeResponse> getById(int id) {
		checkIfEmployeeExistsById(id);
		Employee employee = this.employeeRepository.findById(id).get();
		GetEmployeeResponse employeeResponse = this.modelMapperService.forResponse().map(employee,
				GetEmployeeResponse.class);
		return new SuccessDataResult<GetEmployeeResponse>(employeeResponse, Messages.EmployeeListed);

	}

	private void checkIfEmployeeExistsByNationalIdentity(String nationalIdentity) {
		Employee employee = this.employeeRepository.findByNationalIdentity(nationalIdentity);
		if (employee != null) {
			throw new BusinessException(Messages.EmployeeExists);
		}
	}

	private void checkIfEmployeeExistsById(int id) {
		Employee employee = this.employeeRepository.findById(id).orElse(null);
		if (employee == null) {
			throw new BusinessException(Messages.EmployeeNotExists);
		}
	}
	

	// Employee findByNationalIdentity(String nationalIdentity); Repository'den alındı.
}