package com.sem.project.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sem.project.constrants.AppConstrants;
import com.sem.project.dto.DashboardResponse;
import com.sem.project.dto.EnquiryForm;
import com.sem.project.dto.EnquirySearchCriteria;
import com.sem.project.entities.StudentEnquiry;
import com.sem.project.entities.UserDetails;
import com.sem.project.repositories.CourseRepository;
import com.sem.project.repositories.EnquiryStatusRepository;
import com.sem.project.repositories.StudentEnquiryRepository;
import com.sem.project.repositories.UserDetailsRepository;
import com.sem.project.services.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private EnquiryStatusRepository enquiryRepo;
	
	@Autowired
	private UserDetailsRepository userRepository;
	
	@Autowired
	private StudentEnquiryRepository studentRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public DashboardResponse dashboardResponse(Integer userId) {
		
		DashboardResponse response = new DashboardResponse();
		
		Optional<UserDetails> user = userRepository.findById(userId);
		if (user.isPresent()) {
			UserDetails userDetails = user.get();
			List<StudentEnquiry> enquries = userDetails.getEnquries();
			Integer totalEnquiries = enquries.size();
			
			Integer enrolledEnquries = enquries.stream().filter(e -> e.getEnquiryStatus().equals(AppConstrants.STR_ENROLLED)).collect(Collectors.toList()).size();
		
			Integer lostEnquiries = enquries.stream().filter(e -> e.getEnquiryStatus().equals(AppConstrants.STR_LOST)).collect(Collectors.toList()).size();
			
			response.setTotalEnquiries(totalEnquiries);
			response.setEnrolledEnquiries(enrolledEnquries);
			response.setLostEnquiries(lostEnquiries);
		}
		return response;
	}

	@Override
	public List<String> getCourseName() {
		List<String> cources = courseRepository.getCources();
		return cources;
	}

	@Override
	public List<String> getEnquiryStatus() {
		List<String> enquiryStatus = enquiryRepo.getEnquiryStatus();
		return enquiryStatus;
	}

	@Override
	public boolean upsertEnquiry(EnquiryForm enquiry) {
		StudentEnquiry studentEnquiry = new StudentEnquiry();
		BeanUtils.copyProperties(enquiry, studentEnquiry);
		Integer userId = (Integer)session.getAttribute("userId");
		UserDetails userDetails = userRepository.findById(userId).get();
		studentEnquiry.setUser(userDetails);
		studentRepo.save(studentEnquiry);
		return true;
	}

	@Override
	public List<StudentEnquiry> getEnquiry() {
		Integer userId = (Integer)session.getAttribute("userId");
		Optional<UserDetails> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			UserDetails userDetails = findById.get();
			List<StudentEnquiry> enquries = userDetails.getEnquries();
			return enquries;
		}
		return null;
	}

	@Override
	public List<StudentEnquiry> getFilteredEnquiries(Integer userId, EnquirySearchCriteria criteria) {
		Optional<UserDetails> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			UserDetails userDetails = findById.get();
			List<StudentEnquiry> enquries = userDetails.getEnquries();
			
			if(null!=criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
				enquries = enquries.stream().filter(e -> e.getCourseName().equals(criteria.getCourseName())).collect(Collectors.toList());
			}
			
			if(null!=criteria.getEnqueryStatus() && !"".equals(criteria.getEnqueryStatus())) {
				enquries = enquries.stream().filter(e -> e.getEnquiryStatus().equals(criteria.getEnqueryStatus())).collect(Collectors.toList());
			}
			
			if(null!=criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enquries = enquries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode())).collect(Collectors.toList());
			}
			
			return enquries;
		}
		return null;
	}

	@Override
	public String getStudentById(Integer studentId) {
			return "enquiryRepo.findById(studentId).orElseThrow(()-> new IllegalArgumentException(\"Invalid student ID\"))";
	}

}



























