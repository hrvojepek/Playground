package tvz.naprednaJava.rozi.AutoServis.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tvz.naprednaJava.rozi.AutoServis.enums.Status;

@Entity
@Audited
@Table(name = "stations")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Station extends BaseObject implements Serializable {

	private static final long serialVersionUID = -796249792916148074L;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String address;

	@Column(unique = true, nullable = true)
	private String geolocation;

	@Column
	private LocalTime openFrom;

	@Column
	private LocalTime openUntil;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "manager")
	private User manager;

	@OneToMany(mappedBy = "employeeOfStation")
	private Collection<User> employees;

	@OneToMany(mappedBy = "station")
	private Collection<Reservation> reservations;

	@OneToMany(mappedBy = "station")
	private Collection<Receipt> receipts;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public Station() {
		super();
	}

	public Station(String name, String address, LocalTime openFrom, LocalTime openUntil, String geolocation) {
		super();
		this.status = Status.ACTIVE;
		this.name = name;
		this.address = address;
		this.openFrom = openFrom;
		this.openUntil = openUntil;
		this.geolocation = geolocation;
	}
}
