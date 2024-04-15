package in.khan.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.khan.entities.CourseEntity;
@Repository
public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
