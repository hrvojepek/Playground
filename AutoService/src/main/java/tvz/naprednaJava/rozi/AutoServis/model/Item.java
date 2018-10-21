package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tvz.naprednaJava.rozi.AutoServis.enums.Status;

@Entity
@Audited
@Table(name = "items")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Item extends BaseObject implements Serializable {

	private static final long serialVersionUID = -2248185070419923235L;

	@Column(unique = true, nullable = false)
	private String name;

	@Column
	private BigDecimal pricePerUnit;

	@Column
	private int unitsInStock;

	@Lob
	@Column(nullable = true)
	private String description;

	@ManyToOne(cascade = CascadeType.ALL)
	private Manufacturer manufacturer;

	@ManyToMany(mappedBy = "items")
	private Collection<Receipt> receipts;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public Item() {
		super();
	}

	public Item(String name, BigDecimal pricePerUnit, int unitsInStock, String description, Manufacturer manufacturer) {
		super();
		this.name = name;
		this.pricePerUnit = pricePerUnit;
		this.unitsInStock = unitsInStock;
		this.description = description;
		this.manufacturer = manufacturer;
		this.status = Status.ACTIVE;
	}
}
