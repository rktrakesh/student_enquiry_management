package com.sem.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sem.project.entities.EnquiryStatus;

public interface EnquiryStatusRepository extends JpaRepository<EnquiryStatus, Integer> {
	@Query("select enquriesName from EnquiryStatus")
	public List<String> getEnquiryStatus();
}
