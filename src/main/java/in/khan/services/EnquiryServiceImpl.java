package in.khan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.khan.binding.DashboardResponse;
import in.khan.binding.EnquiryForm;
import in.khan.binding.EnquirySearchCriteria;
import in.khan.entities.CourseEntity;
import in.khan.entities.EnqStatusEntity;
import in.khan.entities.StudentEnqEntity;
import in.khan.entities.UserDtlsEntity;
import in.khan.repos.CourseRepo;
import in.khan.repos.EnqStatusRepo;
import in.khan.repos.StudentEnqRepo;
import in.khan.repos.UserDtlsRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Repository
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo dtlsRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	@Autowired
	private StudentEnqRepo enqRepo;
	@Autowired
	private HttpSession session;
	
	@Override
	public List<String> getCourseName() {
		List<CourseEntity> all = courseRepo.findAll();
		List<String> names= new ArrayList<>();
		for(CourseEntity c : all)
		{
			names.add(c.getCouresName());
		}
		return names;
	}

	@Override
	public List<String> geteEnqStatus() {
		List<EnqStatusEntity> list = enqStatusRepo.findAll();
		List<String> names=new ArrayList<>();
		
		for(EnqStatusEntity e : list)
		{
			names.add(e.getStatusName());
		}
		return names;
	}

	@Override
	public DashboardResponse getDashboardData(Integer UserId) {
		
		DashboardResponse dashboardResponse=new DashboardResponse();
		Optional<UserDtlsEntity> findById = dtlsRepo.findById(UserId);
		if(findById.isPresent())
		{
			UserDtlsEntity entity = findById.get();
			List<StudentEnqEntity> enquiries = entity.getEnquiries();
			Integer totalSize = enquiries.size();
			
			Integer enrolled = enquiries.stream()
			.filter(e -> e.getEnqStatus().equals("Enrolled"))
			.collect(Collectors.toList()).size();
			
			Integer lost= enquiries.stream()
					.filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();
			
			
			dashboardResponse.setTotalEnquiryCnt(totalSize);
			dashboardResponse.setEnrolledCnt(enrolled);
			dashboardResponse.setLostCnt(lost);
			
		}
		return dashboardResponse;
	}

	@Override
	public boolean upsertEnquiry(EnquiryForm form) {
		StudentEnqEntity entity= new StudentEnqEntity();
		if(form.getEnqId()!=null)
		{
			BeanUtils.copyProperties(form, entity);	
		}
		else
		{
			entity.setClassMode(form.getClassMode());
			entity.setCourseName(form.getCourseName());
			entity.setEnqStatus(form.getEnqStatus());
			entity.setStudentPhno(form.getStudentPhno());
			entity.setStudentName(form.getStudentName());
		
		}
		
		
		Integer userID=(Integer)session.getAttribute("userID");
		UserDtlsEntity byId = dtlsRepo.findById(userID).get();
		entity.setUser(byId);
		enqRepo.save(entity);
		return true ;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries(Integer userID, EnquirySearchCriteria criteria) {
		Optional<UserDtlsEntity> byId = dtlsRepo.findById(userID);
		if(byId.isPresent())
		{
			UserDtlsEntity entity = byId.get();
			List<StudentEnqEntity> enquiries = entity.getEnquiries();
			
			
			if(null!=criteria.getClassMode() && !"".equals(criteria.getClassMode()))
			{
				enquiries=enquiries.stream()
				.filter(e -> e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getCourseName() && !"".equals(criteria.getCourseName()))
			{
				enquiries=enquiries.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getEnquiryStatus() && !"".equals(criteria.getEnquiryStatus()))
			{
				enquiries=enquiries.stream()
				.filter(e -> e.getEnqStatus().equals(criteria.getEnquiryStatus()))
				.collect(Collectors.toList());
			}
			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getenquiry() {
		Integer userID=(Integer)session.getAttribute("userID");
		Optional<UserDtlsEntity> byId = dtlsRepo.findById(userID);
		if(byId.isPresent())
		{
			UserDtlsEntity entity = byId.get();
			List<StudentEnqEntity> enquiries = entity.getEnquiries();
			return enquiries;
		}
		return null;
	}
	@Override
	public StudentEnqEntity getEnqById(Integer id) {
		Optional<StudentEnqEntity> byId = enqRepo.findById(id);
		if(byId.isPresent())
		{
			StudentEnqEntity studentEnqEntity = byId.get();
			return studentEnqEntity;
		}
		return null;
	}
	@Override
	@Transactional
	public boolean deleteEnqF(Integer id) {
		
		if(enqRepo.existsById(id))
		{
			enqRepo.deleteEnqMn(id);
			return true;
		}
		
		return false;
	}

}
