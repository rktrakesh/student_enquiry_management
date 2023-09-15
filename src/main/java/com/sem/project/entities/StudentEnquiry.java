package com.sem.project.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StudentEnquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;
	private String name;
	private String mobile;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetails user;
	
}
