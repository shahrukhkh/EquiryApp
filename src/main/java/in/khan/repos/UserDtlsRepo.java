package in.khan.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.khan.entities.UserDtlsEntity;

@Repository
public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer>{

	
	public UserDtlsEntity  findByUserEmail(String userEmail);
	
	public UserDtlsEntity findByUserEmailAndUserPwd(String email,String pwd);
}
