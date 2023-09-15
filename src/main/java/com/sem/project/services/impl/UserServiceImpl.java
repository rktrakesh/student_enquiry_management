package com.sem.project.services.impl;

import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sem.project.dto.LoginForm;
import com.sem.project.dto.SignUpForm;
import com.sem.project.dto.UnlockForm;
import com.sem.project.entities.UserDetails;
import com.sem.project.repositories.UserDetailsRepository;
import com.sem.project.services.UserService;
import com.sem.project.utils.EmailUtils;
import com.sem.project.utils.PasswordUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserDetailsRepository userRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean signUp(SignUpForm signUp) {
		
		UserDetails userEmail = userRepo.findByEmail(signUp.getEmail());
		if(userEmail != null) {
			return false;
		}
		
		//to store all to UserDetails from binding class
		UserDetails user = new UserDetails();
		BeanUtils.copyProperties(signUp, user);
		
		//get the random password from utilities
		String tempPwd = PasswordUtils.randomPasswordGenetator();
		user.setPassword(tempPwd);
		
		//set status to locked
		user.setAccountStatus("LOCKED");
		
		//save all the data in database
		userRepo.save(user);
		
		//send email 
		String to = user.getEmail();
		String subject = "Unlock Your Account | RKTripathy pvt ltd.";
		StringBuffer body = new StringBuffer();
		body.append("<h1>Use bellow temporary password to unlock your account.</h1>");
		body.append("Temporary password : "+tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8082/unlock?email="+to+"\">Click here to ulock your account.</a>");
		
		emailUtils.sendEmail(to, subject, body.toString());
		
		return true;
	}
	
	@Override
	public boolean unlockForn(UnlockForm unlock) {
		UserDetails user = userRepo.findByEmail(unlock.getEmail());
		if(user.getPassword().equals(unlock.getTempPassword())) {
			user.setPassword(unlock.getNewPassword());
			user.setAccountStatus("UNLOCKED");
			userRepo.save(user);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String login(LoginForm form) {
		UserDetails entity = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());
		
		//first we have to check whether the email and password are present in the database
		if(entity == null) {
			return "Invalid credentials!";
		}
		
		if(entity.getAccountStatus().equals("LOCKED")) {
			return "Your account is locked, check your email to unlock it.";
		}
		
		session.setAttribute("userId", entity.getUserId());
		
		return "success";
	}

	@Override
	public String forgotPassword(String email) {
		UserDetails user = userRepo.findByEmail(email);
		if(user != null) {
				String to = user.getEmail();
				String subject = "Forgot password | Check this mail to get the password";
				StringBuffer body = new StringBuffer();
				body.append("<h1>This is your password.</h1>");
				body.append("Your password : "+user.getPassword());
				emailUtils.sendEmail(to, subject, body.toString());
				return "success";
		}
		
		return "Enter valid email id.";
	}

}
