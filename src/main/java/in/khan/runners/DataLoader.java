package in.khan.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.khan.entities.CourseEntity;
import in.khan.entities.EnqStatusEntity;
import in.khan.repos.CourseRepo;
import in.khan.repos.EnqStatusRepo;
@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		CourseEntity   c1= new  CourseEntity();
		c1.setCouresName("Java");
		CourseEntity   c2= new  CourseEntity();
		c2.setCouresName("DevOps");
		CourseEntity   c3= new  CourseEntity();
		c3.setCouresName("AWS");
		CourseEntity   c4= new  CourseEntity();
		c4.setCouresName("React");
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		enqStatusRepo.deleteAll();
		EnqStatusEntity e1= new EnqStatusEntity();
		e1.setStatusName("New");
		EnqStatusEntity e2= new EnqStatusEntity();
		e2.setStatusName("Enrolled");
		EnqStatusEntity e3= new EnqStatusEntity();
		e3.setStatusName("Lost");
		
		enqStatusRepo.saveAll(Arrays.asList(e1,e2,e3));
		
		
	}
}
