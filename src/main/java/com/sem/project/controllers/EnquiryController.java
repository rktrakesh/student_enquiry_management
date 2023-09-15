package com.sem.project.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sem.project.dto.DashboardResponse;
import com.sem.project.dto.EnquiryForm;
import com.sem.project.dto.EnquirySearchCriteria;
import com.sem.project.entities.StudentEnquiry;
import com.sem.project.services.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService enquiryService;
	
	@GetMapping("logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("dashboard")
	public String dashboard(Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		DashboardResponse dashboardResponse = enquiryService.dashboardResponse(userId);
		model.addAttribute("dashboardResponse",dashboardResponse);
		return "dashboard";
	}
	
	@PostMapping("/enquiries")
	public String addEnquiry(@ModelAttribute("enquiryObject") EnquiryForm enquiryObject, Model model) {
		boolean enquiry = enquiryService.upsertEnquiry(enquiryObject);
		if (enquiry) {
			model.addAttribute("succMsg","Student details added successfully.");
		}else {
			model.addAttribute("errMsg","Something error occures.");
		}
		return "add-enquiry";
	}
	
	@GetMapping("enquiry")
	public String addEnquiryPage(Model model) {
		model.addAttribute("courseName",enquiryService.getCourseName());
		model.addAttribute("enquiryName",enquiryService.getEnquiryStatus());
		model.addAttribute("enquiryObject",new EnquiryForm());
		return "add-enquiry";
	}
	
	@GetMapping("getEnquiry")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		List<StudentEnquiry> enquiry = enquiryService.getEnquiry();
		model.addAttribute("enquiry",enquiry);
		return "view-enquiries";
	}
	
	private void initForm(Model model) {
		List<String> cources = enquiryService.getCourseName();
		List<String> enquiryStatus = enquiryService.getEnquiryStatus();
		EnquiryForm form = new EnquiryForm();
		model.addAttribute("cources",cources);
		model.addAttribute("formObj",form);
		model.addAttribute("enqStatus", enquiryStatus);
	}
	
	@GetMapping("searchCriteria")
	public String getSearchCriteria(@RequestParam String cname, 
			@RequestParam String enquiries, 
			@RequestParam String mode, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		initForm(model);
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnqueryStatus(enquiries);
		criteria.setClassMode(mode);
		
		List<StudentEnquiry> filteredEnquiries = enquiryService.getFilteredEnquiries(userId, criteria);
		model.addAttribute("enquiry",filteredEnquiries);
		
		return "new-filtered-enquiries";
	}
	
	@GetMapping("editEnquiry/{studentId}")
	public String editEnquiryPage(@PathVariable("studentId") Integer studentId,
			@RequestParam(name = "edit", defaultValue = "false") boolean edit,Model model) {
		enquiryService.getStudentById(studentId);
		model.addAttribute("courseName",enquiryService.getCourseName());
		model.addAttribute("enquiryName",enquiryService.getEnquiryStatus());
		model.addAttribute("editMode",edit);
		model.addAttribute("enquiryObject",new EnquiryForm());
		return "edit-enquiry";
	}
	
}



































