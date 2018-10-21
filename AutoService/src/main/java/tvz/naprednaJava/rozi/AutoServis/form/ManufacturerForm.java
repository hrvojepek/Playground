package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;

@Data
@NoArgsConstructor
public class ManufacturerForm {

	private Manufacturer manufacturer;

	private FormMode mode;

	public ManufacturerForm(FormMode mode) {
		super();
		this.mode = mode;
	}

	public ManufacturerForm(FormMode mode, Manufacturer manufacturer) {
		super();
		this.mode = mode;
		this.manufacturer = manufacturer;
	}
}
