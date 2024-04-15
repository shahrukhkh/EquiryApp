package in.khan.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.khan.entities.EnqStatusEntity;
@Repository
public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
