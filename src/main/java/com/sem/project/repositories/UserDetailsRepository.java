package com.sem.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sem.project.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
	
	public UserDetails findByEmail(String email);
	
	public UserDetails findByEmailAndPassword(String email, String password);

}
