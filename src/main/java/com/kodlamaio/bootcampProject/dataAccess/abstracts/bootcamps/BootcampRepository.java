package com.kodlamaio.bootcampProject.dataAccess.abstracts.bootcamps;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.bootcampProject.entities.bootcamps.Bootcamp;

public interface BootcampRepository extends JpaRepository<Bootcamp, Integer>{
	
}
