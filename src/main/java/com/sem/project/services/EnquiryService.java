package com.sem.project.services;

import java.util.List;

import com.sem.project.dto.DashboardResponse;
import com.sem.project.dto.EnquiryForm;
import com.sem.project.dto.EnquirySearchCriteria;
import com.sem.project.entities.StudentEnquiry;

public interface EnquiryService {
	
	public List<String> getCourseName();
	
	public List<String> getEnquiryStatus();
	
	public DashboardResponse dashboardResponse(Integer userId);
	
	public boolean upsertEnquiry(EnquiryForm enquiry);
	
	public List<StudentEnquiry> getFilteredEnquiries(Integer userId, EnquirySearchCriteria criteria);
	
	public List<StudentEnquiry> getEnquiry();

	public String getStudentById(Integer studentId);

}
