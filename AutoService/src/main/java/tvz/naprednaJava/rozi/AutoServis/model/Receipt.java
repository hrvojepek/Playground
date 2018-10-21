package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tvz.naprednaJava.rozi.AutoServis.enums.Status;

@Entity
@Audited
@Table(name = "receipts")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Receipt extends BaseObject implements Serializable {

	private static final long serialVersionUID = 6217471459261409836L;

	@Column
	private BigDecimal total;

	@ManyToMany
	@JoinTable(name = "receipts_items", joinColumns = @JoinColumn(name = "receipt_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
	private Collection<Item> items;

	// need to modify this. We need info for how many hours each service was performed
	@ManyToMany
	@JoinTable(name = "receipts_repairs", joinColumns = @JoinColumn(name = "receipt_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "repair_id", referencedColumnName = "id"))
	private Collection<Repair> repairs;

	@ManyToOne(cascade = CascadeType.ALL)
	private Station station;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reservation")
	private Reservation reservation;

	@ManyToOne(cascade = CascadeType.ALL)
	private User biller;

	@ManyToOne(cascade = CascadeType.ALL)
	private User customer;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;
}
