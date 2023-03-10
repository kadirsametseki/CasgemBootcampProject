package com.kodlamaio.bootcampProject.business.responses.employee;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeResponse {
	private int id;
	private LocalDate dateOfBirth;
	private String nationalIdentity;
	private String position;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
