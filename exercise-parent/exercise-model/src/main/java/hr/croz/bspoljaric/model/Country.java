package hr.croz.bspoljaric.model;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String alpha2;

	private String alpha3;

	private String name;

	private List<City> cities;

	public Country() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlpha2() {
		return this.alpha2;
	}

	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}

	public String getAlpha3() {
		return this.alpha3;
	}

	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public City addCity(City city) {
		getCities().add(city);
		city.setCountry(this);

		return city;
	}

	public City removeCity(City city) {
		getCities().remove(city);
		city.setCountry(null);

		return city;
	}

}