package tvz.naprednaJava.rozi.AutoServis.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.Audited;
import tvz.naprednaJava.rozi.AutoServis.enums.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Audited
@Table(name = "users")
//@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class User extends BaseObject implements Serializable {

	private static final long serialVersionUID = -4819287527605601001L;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@ManyToOne(cascade = CascadeType.ALL)
	private Role role;

	@OneToMany(mappedBy = "customer")
	private Collection<Reservation> reservationCustomers;

	@OneToMany(mappedBy = "repairman")
	private Collection<Reservation> reservationRepairmen;

	@OneToMany(mappedBy = "biller")
	private Collection<Receipt> receiptBiller;

	@OneToMany(mappedBy = "customer")
	private Collection<Receipt> receiptCustomer;

	@OneToOne(mappedBy = "manager")
	private Station managerOfStation;

	@ManyToOne(cascade = CascadeType.ALL)
	private Station employeeOfStation;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String username, String password, UserStatus status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.status = status;
	} 

	public void setUserStatus(UserStatus userStatus) {
		this.status = userStatus;
	}
}
