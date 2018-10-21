package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tvz.naprednaJava.rozi.AutoServis.enums.Status;

@Entity
@Audited
@Table(name = "repairs")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Repair extends BaseObject implements Serializable {

	private static final long serialVersionUID = -2160025339950079687L;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal pricePerHour;

	@Lob
	@Column
	private String description;

	@ManyToMany(mappedBy = "repairs")
	private Collection<Receipt> receipts;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public Repair() {
		super();
	}

	public Repair(String name, BigDecimal pricePerHour, String description) {
		super();
		this.name = name;
		this.pricePerHour = pricePerHour;
		this.description = description;
		this.status = Status.ACTIVE;
	}
}
