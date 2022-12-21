package com.kodlamaio.bootcampProject.business.abstracts.blacklists;

import java.util.List;

import com.kodlamaio.bootcampProject.business.requests.blacklist.CreateBlacklistRequest;
import com.kodlamaio.bootcampProject.business.requests.blacklist.UpdateBlacklistRequest;
import com.kodlamaio.bootcampProject.business.responses.blacklist.CreateBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.GetAllBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.GetBlacklistResponse;
import com.kodlamaio.bootcampProject.business.responses.blacklist.UpdateBlacklistResponse;
import com.kodlamaio.bootcampProject.core.utilities.results.DataResult;
import com.kodlamaio.bootcampProject.core.utilities.results.Result;

public interface BlacklistService {
	Result delete(int id);
	DataResult<CreateBlacklistResponse> add(CreateBlacklistRequest createBlacklistRequest);
	DataResult<UpdateBlacklistResponse> update(UpdateBlacklistRequest updateBlacklistRequest);
	DataResult<List<GetAllBlacklistResponse>> getAll();
	DataResult<GetBlacklistResponse> getById(int id);
	
	DataResult<GetBlacklistResponse> getByApplicantId(int applicantId); 
}
