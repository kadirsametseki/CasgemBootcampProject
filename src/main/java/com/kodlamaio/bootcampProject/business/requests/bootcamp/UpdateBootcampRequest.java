package com.kodlamaio.bootcampProject.business.requests.bootcamp;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBootcampRequest {
	@NotNull
	@NotEmpty
	@Min(0)
	private int id;
	
	@NotNull
	@NotEmpty
	@Min(0)
	private int instructorId;
	
	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateStart;
	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateEnd;
	@NotNull
	@NotEmpty
	private int state;
	
	
}