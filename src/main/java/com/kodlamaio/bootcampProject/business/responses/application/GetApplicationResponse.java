package com.kodlamaio.bootcampProject.business.responses.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationResponse {
	private int id;
	private int bootcampId;
	private int applicantId;
	private int state;
}
