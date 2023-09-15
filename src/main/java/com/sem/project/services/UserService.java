package com.sem.project.services;

import com.sem.project.dto.LoginForm;
import com.sem.project.dto.SignUpForm;
import com.sem.project.dto.UnlockForm;

public interface UserService {
	
	public String login(LoginForm login);
	
	public boolean signUp(SignUpForm signUp);
	
	public String forgotPassword(String email);
	
	public boolean unlockForn(UnlockForm unlock);

}
