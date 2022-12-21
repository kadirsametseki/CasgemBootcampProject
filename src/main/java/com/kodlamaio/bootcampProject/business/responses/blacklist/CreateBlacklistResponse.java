package com.kodlamaio.bootcampProject.business.responses.blacklist;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlacklistResponse {
	private int id;
	private LocalDate date;
	private String reason;
	private int applicantId;
}