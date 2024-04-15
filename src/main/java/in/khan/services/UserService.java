package in.khan.services;

import in.khan.binding.LoginForm;
import in.khan.binding.SignUpForm;
import in.khan.binding.UnlockForm;

public interface UserService {

	
	
	public String login(LoginForm form);
	public boolean signUp(SignUpForm form);
	public boolean unlockAccount(UnlockForm form);
	public String forgotPwd(String email);
}
