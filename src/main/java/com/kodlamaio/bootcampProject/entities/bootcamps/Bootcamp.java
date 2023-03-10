package com.kodlamaio.bootcampProject.entities.bootcamps;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.kodlamaio.bootcampProject.entities.applications.Application;
import com.kodlamaio.bootcampProject.entities.users.Instructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bootcamps")
public class Bootcamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;
	@Column(name = "dataStart")
	private LocalDate dateStart;
	@Column(name = "dateEnd")
	private LocalDate dateEnd;
	@Column(name = "state")
	private int state;

	@ManyToOne()
	@JoinColumn(name = "instructorId")
	private Instructor instructor;

	@OneToMany(mappedBy = "bootcamp")
	private List<Application> applications;
}