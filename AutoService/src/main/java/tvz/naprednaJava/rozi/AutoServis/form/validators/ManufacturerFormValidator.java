package tvz.naprednaJava.rozi.AutoServis.form.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.form.ManufacturerForm;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;

@Component
public class ManufacturerFormValidator implements Validator {

	@Autowired
	private ManufacturerService manufacturerService;

	@Override
	public boolean supports(Class<?> arg0) {
		return ManufacturerForm.class.equals(arg0);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ManufacturerForm form = (ManufacturerForm) object;
		if (form.getManufacturer().getName() == null || form.getManufacturer().getName().isEmpty()) {
			errors.rejectValue("manufacturer.name", "Potrebno je odrediti naziv proizvođača.");
		} else if (form.getManufacturer().getName().length() < 3) {
			errors.rejectValue("manufacturer.name", "Naziv proizvođača mora imati više od 3 znaka.");
		} else if (form.getMode() == FormMode.NEW && manufacturerService.getActiveByName(form.getManufacturer().getName()) != null) {
			errors.rejectValue("manufacturer.name",
					String.format("Nije moguće kreirati proizvođača naziva %s zato što on već postoji.", form.getManufacturer().getName()));
		} else if (form.getMode() == FormMode.EDIT) {
			Manufacturer m = manufacturerService.getActiveByName(form.getManufacturer().getName());
			if (m != null && m.getId() != form.getManufacturer().getId()) {
				errors.rejectValue("manufacturer.name", String.format("Proizvođač %s već postoji.", form.getManufacturer().getName()));
			}
		}
	}
}
