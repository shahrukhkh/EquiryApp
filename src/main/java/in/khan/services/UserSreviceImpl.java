package in.khan.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.khan.binding.LoginForm;
import in.khan.binding.SignUpForm;
import in.khan.binding.UnlockForm;
import in.khan.entities.UserDtlsEntity;
import in.khan.repos.UserDtlsRepo;
import in.khan.utils.EmailUtil;
import in.khan.utils.PwdUtil;
import jakarta.servlet.http.HttpSession;
@Service
public class UserSreviceImpl implements UserService {

	
	@Autowired
	private UserDtlsRepo dtlsRepo;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private HttpSession httpSession;
	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = dtlsRepo.findByUserEmailAndUserPwd(form.getUserName(),form.getUserPwd());
		if(entity ==null)
		{
			return "Invalid Credentials";
		}
		if(entity.getAccStatus().equals("LOCKED"))
		{
			return "Your account is Locked!";
		}
		httpSession.setAttribute("userID", entity.getUserId());
		return "success";
	}
	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDtlsEntity user = dtlsRepo.findByUserEmail(form.getUserEmail());
		if(user != null)
		{
			return false;
		}
		UserDtlsEntity entity= new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
			String tempPwd = PwdUtil.generatePwd();
			entity.setUserPwd(tempPwd);
			entity.setAccStatus("LOCKED");
			dtlsRepo.save(entity);
			String to =form.getUserEmail();
			String subject="Unlock your account || KhanIT";
			StringBuffer body= new StringBuffer("");
			body.append("<h1>Use below temporary pwd to unlock your a/c</h1>");
			body.append("temporary pwd: "+tempPwd);
			body.append("<br/>");
			body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">click here to unlock</a>");
			emailUtil.sendEmail(to, subject, body.toString());
			return true;
	}
	@Override
	public boolean unlockAccount(UnlockForm form) {
	
		UserDtlsEntity entity = dtlsRepo.findByUserEmail(form.getEmail());
		if(entity.getUserPwd().equals(form.getTempPwd()))
		{
			entity.setAccStatus("UNLOCKED");
			entity.setUserPwd(form.getNewPwd());
			dtlsRepo.save(entity);
			return true;
		}
		else {
			return false;
		}
	}
	@Override
	public String forgotPwd(String email) {
		UserDtlsEntity entity = dtlsRepo.findByUserEmail(email);
		if(entity == null)
		{
			return "Invalid Email!";
		}
		String subject="Recover Password";
		String body="Here is your password :: "+entity.getUserPwd();
		emailUtil.sendEmail(email, subject, body);
		return "Check Your Email!";
	}

}
