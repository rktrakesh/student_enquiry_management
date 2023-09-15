package com.sem.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sem.project.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	@Query("select courses from Course")
	public List<String> getCources();
}
