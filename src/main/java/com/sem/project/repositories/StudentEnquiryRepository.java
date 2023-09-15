package com.sem.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sem.project.entities.StudentEnquiry;

public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiry, Integer> {

	
}
