package hr.croz.bspoljaric.model;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the sex database table.
 * 
 */
public class Sex implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private List<Contact> contacts;

	public Sex() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setSex(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setSex(null);

		return contact;
	}

}