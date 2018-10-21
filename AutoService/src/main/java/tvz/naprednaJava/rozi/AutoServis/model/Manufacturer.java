package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tvz.naprednaJava.rozi.AutoServis.enums.Status;

@Entity
@Audited
@Table(name = "manufacturers")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Manufacturer extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3316390226893583576L;

	@Column(unique = true, nullable = false)
	private String name;

	@OneToMany(mappedBy = "manufacturer")
	private Collection<Item> items;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public Manufacturer() {
		super();
	}

	public Manufacturer(String name) {
		super();
		this.name = name;
		this.status = Status.ACTIVE;
	}
}
