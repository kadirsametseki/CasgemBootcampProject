package com.kodlamaio.bootcampProject.business.concretes.blacklists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.bootcampProject.business.abstracts.blacklists.BlacklistService;
import com.kodlamaio.bootcampProject.business.abstracts.users.ApplicantService;
import com.kodlamaio.bootcampProject.business.constants.Messages;
import com.kodlamaio.bootcampProject.business.requests.blacklist.CreateBlacklistRequest;
import com.kodlamaio.bootcampProject.business.requests.blacklist.UpdateBlacklistRequest;
import com.kodlamaio.bootcampProject.business.responses.blacklist.CreateBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.GetAllBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.GetBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.UpdateBlacklistResponse;
import com.kodlamaio.bootcampProject.core.utilities.exceptions.BusinessException;
import com.kodlamaio.bootcampProject.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessDataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.SuccessResult;
import com.kodlamaio.bootcampProject.dataAccess.abstracts.blacklists.BlacklistRepository;
import com.kodlamaio.bootcampProject.entities.blacklists.Blacklist;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlacklistManager implements BlacklistService{
	
	private ModelMapperService modelMapperService;
	private BlacklistRepository blacklistRepository;
	private ApplicantService applicantService;
	
	@Override
	public Result delete(int id) {
		checkIfBlacklistExistsById(id);
		this.blacklistRepository.deleteById(id);

		return new SuccessResult(Messages.BlacklistDeleted);
	}

	@Override
	public DataResult<CreateBlacklistResponse> add(CreateBlacklistRequest createBlacklistRequest) {
		checkIfApplicantExistsById(createBlacklistRequest.getApplicantId());
		Blacklist blacklist = this.modelMapperService.forRequest().map(createBlacklistRequest, Blacklist.class);
		blacklist.setId(0);
		this.blacklistRepository.save(blacklist);

		CreateBlacklistResponse createBlacklistResponse = this.modelMapperService.forResponse().map(blacklist,
				CreateBlacklistResponse.class);
		return new SuccessDataResult<CreateBlacklistResponse>(createBlacklistResponse, Messages.BlacklistCreated);
	}

	@Override
	public DataResult<UpdateBlacklistResponse> update(UpdateBlacklistRequest updateBlacklistRequest) {
		checkIfApplicantExistsById(updateBlacklistRequest.getApplicantId());
		checkIfBlacklistExistsById(updateBlacklistRequest.getId());
		Blacklist blacklist = this.modelMapperService.forRequest().map(updateBlacklistRequest, Blacklist.class);
		this.blacklistRepository.save(blacklist);

		UpdateBlacklistResponse updateBlacklistResponse = this.modelMapperService.forResponse().map(blacklist,
				UpdateBlacklistResponse.class);
		return new SuccessDataResult<UpdateBlacklistResponse>(updateBlacklistResponse, Messages.BlacklistUpdate);
	}

	@Override
	public DataResult<List<GetAllBlacklistResponse>> getAll() {
		List<Blacklist> blacklists = this.blacklistRepository.findAll();
		List<GetAllBlacklistResponse> blacklistsResponse = blacklists.stream()
				.map(blacklist -> modelMapperService.forResponse().map(blacklist, GetAllBlacklistResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllBlacklistResponse>>(blacklistsResponse, Messages.BlacklistListed);
	}

	@Override
	public DataResult<GetBlacklistResponse> getById(int id) {
		checkIfBlacklistExistsById(id);
		Blacklist blacklist = this.blacklistRepository.findById(id).get();
		GetBlacklistResponse getBlacklistResponse = this.modelMapperService.forResponse().map(blacklist,
				GetBlacklistResponse.class);

		return new SuccessDataResult<GetBlacklistResponse>(getBlacklistResponse, Messages.BlacklistById);
	}

	@Override
	public DataResult<GetBlacklistResponse> getByApplicantId(int applicantId) {
		Blacklist blacklist = this.blacklistRepository.findByApplicantId(applicantId);
		GetBlacklistResponse getBlacklistResponse = this.modelMapperService.forResponse().map(blacklist,
				GetBlacklistResponse.class);

		return new SuccessDataResult<GetBlacklistResponse>(getBlacklistResponse);
	}

	private void checkIfBlacklistExistsById(int id) {
		Blacklist blacklist = this.blacklistRepository.findById(id).orElse(null);
		if (blacklist == null) {
			throw new BusinessException(Messages.BlacklistNotExists);
		}
	}

	private void checkIfApplicantExistsById(int applicantId) {
		var result = this.applicantService.getById(applicantId);
		if (result == null) {
			throw new BusinessException(Messages.ApplicantNotExists);
		}
	}
}
