package com.kodlamaio.bootcampProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.bootcampProject.business.abstracts.users.EmployeeService;
import com.kodlamaio.bootcampProject.business.requests.employee.CreateEmployeeRequest;
import com.kodlamaio.bootcampProject.business.requests.employee.UpdateEmployeeRequest;
import com.kodlamaio.bootcampProject.business.responses.employee.CreateEmployeeResponse;
import com.kodlamaio.bootcampProject.business.responses.employee.GetAllEmployeesResponse;
import com.kodlamaio.bootcampProject.business.responses.employee.GetEmployeeResponse;
import com.kodlamaio.bootcampProject.business.responses.employee.UpdateEmployeeResponse;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeesController {
	
	private EmployeeService employeeService;
	
	@GetMapping("/{id}")
	public DataResult<GetEmployeeResponse> getById(@PathVariable int id) {
		return this.employeeService.getById(id);
	}
	
	
	@GetMapping()
	public DataResult<List<GetAllEmployeesResponse>> getAll(){
		return this.employeeService.getAll();
	}
	
	@PostMapping()
	public DataResult<CreateEmployeeResponse> add(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest){
		return this.employeeService.add(createEmployeeRequest);
	}
	
	@PutMapping()
	public DataResult<UpdateEmployeeResponse> update(@Valid @RequestBody UpdateEmployeeRequest updateEmployeeRequest){
		return this.employeeService.update(updateEmployeeRequest);
	}
		
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable int id) {
		return this.employeeService.delete(id);
	}
	
}
