package in.khan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.khan.binding.DashboardResponse;
import in.khan.binding.EnquiryForm;
import in.khan.binding.EnquirySearchCriteria;
import in.khan.entities.StudentEnqEntity;
import in.khan.services.EnquiryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService enquiryService;
	@GetMapping("/logout")
	public String logOut()
	{
		session.invalidate();
		return "index";
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		Integer userID=(Integer) session.getAttribute("userID");
		DashboardResponse dashboardData = enquiryService.getDashboardData(userID);
		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";
	}
	@GetMapping("/enquiry")
	public String addEnquiry(Model model)
	{
		formInto(model);
		
		return "add-enquiry";
	}
	
	private void formInto(Model model)
	{
		List<String> courseName = enquiryService.getCourseName();
		List<String> status = enquiryService.geteEnqStatus();
		EnquiryForm form=new EnquiryForm();
		
		model.addAttribute("courseName", courseName);
		model.addAttribute("status", status);
		model.addAttribute("form", form);
	}
	
	@PostMapping("/enquiry")
	public String handleAddEnquiry(@ModelAttribute("form") EnquiryForm form, Model model)
	{
		boolean status = enquiryService.upsertEnquiry(form);
		if(status)
		{
			model.addAttribute("sucMsg", "Enquiry Added!");
		}
		else {
			model.addAttribute("errMsg", "Failed to Add Enquiry");
		}
		formInto(model);
		return "add-enquiry";
	}
	@GetMapping("/enquires")
	public String viewEnquiry(Model model)
	{
		formInto(model);
		List<StudentEnqEntity> list = enquiryService.getenquiry();
		model.addAttribute("lists", list);
		return "view-enquiries";
	}
	@GetMapping("filter-enq")
	public String filterEnq(@RequestParam String cname,@RequestParam String status, @RequestParam String mode,Model model)
	{
		EnquirySearchCriteria e= new EnquirySearchCriteria();
		e.setClassMode(mode);
		e.setCourseName(cname);
		e.setEnquiryStatus(status);
		Integer userID=(Integer) session.getAttribute("userID");
		
		List<StudentEnqEntity> list = enquiryService.getEnquiries(userID, e);
		model.addAttribute("lists", list);

		return "newFile";
	}
	@GetMapping("/edit/{id}")
	public String editData(@PathVariable Integer id,Model  model)
	{
		StudentEnqEntity enqById = enquiryService.getEnqById(id);
		
		
		List<String> courseName = enquiryService.getCourseName();
		List<String> status = enquiryService.geteEnqStatus();
		EnquiryForm form=new EnquiryForm();
		form.setStudentPhno(enqById.getStudentPhno());
		form.setEnqId(enqById.getEnqId());
		form.setStudentName(enqById.getStudentName());
		
		model.addAttribute("courseName", courseName);
		model.addAttribute("status", status);
		model.addAttribute("form", form);
		return "add-enquiry";
	}
	@GetMapping("/delete/{id}")
	public String handleDelete(@PathVariable Integer id)
	{
		boolean status = enquiryService.deleteEnqF(id);
		if(status)
		{
			return "redirect:/enquires";
		}
		return  "redirect:/add-enquiry";
		
		
	}
}
