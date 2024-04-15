package in.khan.services;

import java.util.List;

import in.khan.binding.DashboardResponse;
import in.khan.binding.EnquiryForm;
import in.khan.binding.EnquirySearchCriteria;
import in.khan.entities.StudentEnqEntity;

public interface EnquiryService {

	public List<String> getCourseName();
	public List<String> geteEnqStatus();
	public DashboardResponse getDashboardData(Integer UserId);
	public boolean upsertEnquiry(EnquiryForm form);
	public List<StudentEnqEntity> getEnquiries(Integer userID, EnquirySearchCriteria criteria);
	
	public List<StudentEnqEntity> getenquiry();
	public StudentEnqEntity getEnqById(Integer id);
	public boolean deleteEnqF(Integer id);
	
}
