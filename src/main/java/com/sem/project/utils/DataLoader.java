package com.sem.project.utils;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sem.project.entities.Course;
import com.sem.project.entities.EnquiryStatus;
import com.sem.project.repositories.CourseRepository;
import com.sem.project.repositories.EnquiryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private EnquiryStatusRepository enquiryRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepo.deleteAll();
		Course c1 = new Course();
		c1.setCourses("JAVA");
		Course c2 = new Course();
		c2.setCourses("PYTHON");
		Course c3 = new Course();
		c3.setCourses("DEVOPS");
		Course c4 = new Course();
		c4.setCourses("AWS");
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		enquiryRepository.deleteAll();
		EnquiryStatus s1 = new EnquiryStatus();
		s1.setEnquriesName("NEW");
		EnquiryStatus s2 = new EnquiryStatus();
		s2.setEnquriesName("ENROLLED");
		EnquiryStatus s3 = new EnquiryStatus();
		s3.setEnquriesName("LOST");
		enquiryRepository.saveAll(Arrays.asList(s1,s2,s3));
	}

}
