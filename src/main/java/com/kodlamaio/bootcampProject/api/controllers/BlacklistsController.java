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

import com.kodlamaio.bootcampProject.business.abstracts.blacklists.BlacklistService;
import com.kodlamaio.bootcampProject.business.requests.blacklist.CreateBlacklistRequest;
import com.kodlamaio.bootcampProject.business.requests.blacklist.UpdateBlacklistRequest;
import com.kodlamaio.bootcampProject.business.responses.blacklist.CreateBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.GetAllBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.GetBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.UpdateBlacklistResponse;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/blacklists")
@AllArgsConstructor
public class BlacklistsController {
	
	private BlacklistService blacklistService;
	
	@GetMapping("/{id}")
	public DataResult<GetBlacklistResponse> getById(@PathVariable int id) {
		return this.blacklistService.getById(id);
	}
	
	@GetMapping()
	public DataResult<List<GetAllBlacklistResponse>>  getAll(){
		return this.blacklistService.getAll();
	}
	
	@PostMapping()
	public DataResult<CreateBlacklistResponse> add(@Valid @RequestBody CreateBlacklistRequest createBlacklistRequest){
		return this.blacklistService.add(createBlacklistRequest);
	}
	
	@PutMapping()
	public DataResult<UpdateBlacklistResponse> update(@Valid @RequestBody UpdateBlacklistRequest updateBlacklistRequest){
		return this.blacklistService.update(updateBlacklistRequest);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable int id) {
		return this.blacklistService.delete(id);
	}
	
	
}
