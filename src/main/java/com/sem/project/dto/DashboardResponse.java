package com.sem.project.dto;

import lombok.Data;

@Data
public class DashboardResponse {
	
	private Integer totalEnquiries;
	private Integer enrolledEnquiries;
	private Integer lostEnquiries;

}
