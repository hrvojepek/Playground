package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Audited
@Table(name = "roles")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Role extends BaseObject implements Serializable {

	private static final long serialVersionUID = -4387497633227059757L;

	@Column(unique = true, nullable = false)
	private String name;

	@OneToMany(mappedBy = "role")
	private Set<User> users;

	@ManyToMany
	@JoinTable(name = "roles_permissions", 
		joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
	)
	private Collection<Permission> permissions; 
}
