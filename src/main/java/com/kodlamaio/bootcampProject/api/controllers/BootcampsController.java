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

import com.kodlamaio.bootcampProject.business.abstracts.bootcamps.BootcampService;
import com.kodlamaio.bootcampProject.business.requests.bootcamp.CreateBootcampRequest;
import com.kodlamaio.bootcampProject.business.requests.bootcamp.UpdateBootcampRequest;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.CreateBootcampResponse;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.GetAllBootcampsResponse;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.GetBootcampResponse;
import com.kodlamaio.bootcampProject.business.responses.bootcamp.UpdateBootcampResponse;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/bootcamps")
@AllArgsConstructor
public class BootcampsController {
	
	private BootcampService bootcampService;
	
	@GetMapping("/{id}")
	public DataResult<GetBootcampResponse> getById(@PathVariable int id) {
		return this.bootcampService.getById(id);
	}
	
	@GetMapping()
	public DataResult<List<GetAllBootcampsResponse>>  getAll(){
		return this.bootcampService.getAll();
	}
	
	@PostMapping()
	public DataResult<CreateBootcampResponse> add(@Valid @RequestBody CreateBootcampRequest createBootcampRequest){
		return this.bootcampService.add(createBootcampRequest);
	}
	
	@PutMapping()
	public DataResult<UpdateBootcampResponse> update(@Valid @RequestBody UpdateBootcampRequest updateBootcampRequest){
		return this.bootcampService.update(updateBootcampRequest);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable int id) {
		return this.bootcampService.delete(id);
	}
	
	
}
