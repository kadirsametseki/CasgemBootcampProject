package com.kodlamaio.bootcampProject.business.requests.application;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationRequest {
	@NotNull
	@NotEmpty
	@Min(0)
	private int id;
	@NotNull
	@NotEmpty
	@Min(0)
	private int bootcampId;
	@NotNull
	@NotEmpty
	@Min(0)
	private int applicantId;
	@NotNull
	@NotEmpty
	private int state;

}