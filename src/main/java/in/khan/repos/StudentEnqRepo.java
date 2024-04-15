package in.khan.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.khan.entities.StudentEnqEntity;
@Repository
public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

	@Modifying
	@Query(value="DELETE FROM kit_student_enquiries WHERE enq_id =:id",nativeQuery = true)
	public void deleteEnqMn(@Param("id") Integer id);
}
