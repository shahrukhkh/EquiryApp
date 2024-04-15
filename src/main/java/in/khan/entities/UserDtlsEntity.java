package in.khan.entities;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name="KIT_USER_DTLS")
@Data
public class UserDtlsEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String userName;
	private String userEmail;
	private String userPwd;
	private Long userNo;
	private String accStatus;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL ,fetch =FetchType.EAGER )
	private List<StudentEnqEntity> enquiries;
}
