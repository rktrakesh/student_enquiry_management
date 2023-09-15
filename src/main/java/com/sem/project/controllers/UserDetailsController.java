package com.sem.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sem.project.dto.LoginForm;
import com.sem.project.dto.SignUpForm;
import com.sem.project.dto.UnlockForm;
import com.sem.project.services.UserService;

@Controller
public class UserDetailsController {
	
	@Autowired
	private UserService userService;
	
	//for signUp functionality
	@GetMapping("/signup")
	public String registration(SignUpForm signUp, Model model) {
		model.addAttribute("user", signUp);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean signUp = userService.signUp(form);
		if (signUp) {
			model.addAttribute("succMsg","Account created successfully, please check your email.");
		}else {
			model.addAttribute("error","Email already exists, use another email.");
		}
		return "signup";
	}
	
	//for unlock password
	@GetMapping("/unlock")
	public String unlockPassword(@RequestParam String email, Model model) {
		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlock", unlockForm);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String updatePassword(@ModelAttribute("unlock") UnlockForm form, Model model) {
		if(form.getNewPassword().equals(form.getConfirmPassword())) {
			boolean status = userService.unlockForn(form);
			if(status) {
				model.addAttribute("succMsg","Your account has been unlocked.");
			}else {
				model.addAttribute("errMsg","Given temporary password is incorrect.");
			}
			return "unlock";
		}else{
			model.addAttribute("errMsg","Both New and Confirm password should be same.");
			return "unlock";
		}
	}
	
	//login controllers
	@GetMapping("/login")
	public String signinPage(LoginForm form, Model model) {
		model.addAttribute("login", form);
		return "signin";
	}
	
	@PostMapping("/login")
	public String signin(@ModelAttribute ("login") LoginForm form, Model model) {
		
		String status = userService.login(form);
		if(status.contains("success")) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("errMsg",status);
		
		return "signin";
	}
	
	//here we have to write controller layer for forgot password
	@GetMapping("/forgot")
	public String forgotPasswordPage() {
		return "forgot-password";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPassword(@RequestParam("email") String email, Model model) {
		String status = userService.forgotPassword(email);
		if(status.contains("success")) {
			model.addAttribute("successMsg","Your password sent successfully to your email, please check your email.");
		}else {
			model.addAttribute("errMsg",status);
		}
		return "forgot-password";
	}
	
}
