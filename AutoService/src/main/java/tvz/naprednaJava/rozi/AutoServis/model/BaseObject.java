package tvz.naprednaJava.rozi.AutoServis.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseObject {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@CreatedDate
	private LocalDateTime createdDate;

//	@Column
//	@CreatedBy
//	private Long createdBy;
//
//	@Column
//	@LastModifiedDate
//	private LocalDateTime modifiedDate;
//
//	@Column
//	@LastModifiedBy
//	private Long modifiedBy;
}
