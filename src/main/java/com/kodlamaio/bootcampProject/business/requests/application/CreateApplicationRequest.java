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
public class CreateApplicationRequest {
	@Min(0)
	@NotNull
	@NotEmpty
	private int bootcampId;
	@Min(0)
	@NotNull
	@NotEmpty
	private int applicantId;
	@NotNull
	@NotEmpty
	private int state;

}