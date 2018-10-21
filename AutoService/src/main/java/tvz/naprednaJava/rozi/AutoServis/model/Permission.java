package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Audited
@Table(name = "permissions")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Permission extends BaseObject implements Serializable {

	private static final long serialVersionUID = 2753988931399793970L;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles;
}
