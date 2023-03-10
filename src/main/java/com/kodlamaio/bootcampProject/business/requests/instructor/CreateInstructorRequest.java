package com.kodlamaio.bootcampProject.business.requests.instructor;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstructorRequest {
	@Size(min = 11, max = 11)
	@NotNull
	@NotEmpty
	private String nationalIdentity;
	
	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateOfBirth;
	
	@NotEmpty
	@NotNull
	@Size(min = 3)
	private String firstName;
	
	@NotEmpty
	@NotNull
	private String lastName;
	
	@Email
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@NotEmpty
	private String companyName;
	
}