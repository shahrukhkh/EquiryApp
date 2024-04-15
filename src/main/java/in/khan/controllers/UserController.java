package in.khan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.khan.binding.LoginForm;
import in.khan.binding.SignUpForm;
import in.khan.binding.UnlockForm;
import in.khan.services.UserService;
@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form ,Model model) {
		boolean status = service.signUp(form);
		if(status)
		{
			model.addAttribute("sucMsg","Account Created , Check Your Email");
		}
		else {
			model.addAttribute("err","Choose Unique Email!");
		}
		return "signup";
	}
	
	@GetMapping("/signup")
	public String signUp(Model model)
	{
		model.addAttribute("user",new SignUpForm());
		
		return "signup";
	}
	
	
	@GetMapping("/login")
	public String loginPage(Model model)
	{
		model.addAttribute("loginPage", new LoginForm());
		
		return "login";
	}
	@PostMapping("/login")
	public String login(@ModelAttribute("loginPage") LoginForm form, Model model)
	{
		String status = service.login(form);
		if(status.contains("success"))
		{
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";
	}
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlk") UnlockForm form, Model model)
	{
		if(form.getNewPwd().equals(form.getCnfPwd()))
		{
			boolean status = service.unlockAccount(form);
			if(status)
			{
				model.addAttribute("sucMsg", "Your account is unlocked");
				
			}else
			{
				model.addAttribute("errMsg", "Incorrect temporary password, check your mail!");
			}
		}
		else
		{
			model.addAttribute("errMsg", "New password and Confirm password should be same!");
		}
		
		return "unlock";
	}
	
	@GetMapping("/unlock")
	public String unlock(@RequestParam String email,Model model)
	{
		UnlockForm form=new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlk", form);
		return "unlock";
	}
	
	
	
	
	
	
	
	@GetMapping("/forgot")
	public String forgot()
	{
		return "forgotPwd";
	}
	@PostMapping("/forgot")
	public String handleForgot(@RequestParam("email") String email,Model model)
	{
		String status = service.forgotPwd(email);
		if(status.contains("Check Your Email!"))
		{
			model.addAttribute("sucMsg", status);
		}
		else {
			model.addAttribute("errMsg", status);
		}
		return "forgotPwd";
	}
}
