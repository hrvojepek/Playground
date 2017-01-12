package hr.hpek.playing.entity;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the address database table.
 * 
 */

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String street;

	private String streetNo;

	private City city;
	
	private List<Contact> contacts;

	public Address() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNo() {
		return this.streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setAddress(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setAddress(null);

		return contact;
	}
	
	
	@Override
	public String toString() {
		return "Address (id=" + id + ", street=" + street + ")";
	}

}