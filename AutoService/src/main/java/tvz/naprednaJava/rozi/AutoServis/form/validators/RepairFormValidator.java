package tvz.naprednaJava.rozi.AutoServis.form.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.form.RepairForm;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.service.RepairService;

@Component
public class RepairFormValidator implements Validator {

	@Autowired
	private RepairService repairService;

	@Override
	public boolean supports(Class<?> arg0) {
		return RepairForm.class.equals(arg0);
	}

	@Override
	public void validate(Object object, Errors errors) {
		RepairForm form = (RepairForm) object;
		if (form.getRepair().getName() == null || form.getRepair().getName().isEmpty()) {
			errors.rejectValue("repair.name", "Potrebno je odrediti naziv usluge servisa.");
		} else if (form.getRepair().getName().length() < 3) {
			errors.rejectValue("repair.name", "Naziv usluge servisa mora imati više od 3 znaka.");
		} else if (form.getMode() == FormMode.NEW && repairService.getActiveByName(form.getRepair().getName()) != null) {
			errors.rejectValue("repair.name",
					String.format("Nije moguće kreirati uslugu servisa naziva %s zato što on već postoji.", form.getRepair().getName()));
		} else if (form.getMode() == FormMode.EDIT) {
			Repair repair = repairService.getActiveByName(form.getRepair().getName());
			if (repairService != null && repair.getId() != form.getRepair().getId()) {
				errors.rejectValue("repair.name", String.format("Usluga servisa naziva %s već postoji.", form.getRepair().getName()));
			}
		}

		if (form.getRepair().getPricePerHour() == null) {
			errors.rejectValue("repair.pricePerHour", "Cijena usluge servisa po satu mora biti zadana.");
		} else if (form.getRepair().getPricePerHour().signum() < 1) {
			errors.rejectValue("repair.pricePerHour", "Cijena usluge servisa po satu mora biti pozitivna i veća od nule.");
		}
	}

}
