package tvz.naprednaJava.rozi.AutoServis.form.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.form.ItemForm;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.service.ItemService;

@Component
public class ItemFormValidator implements Validator {

	@Autowired
	private ItemService itemService;

	@Override
	public boolean supports(Class<?> arg0) {
		return ItemForm.class.equals(arg0);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ItemForm form = (ItemForm) object;
		if (form.getItem().getName() == null || form.getItem().getName().isEmpty()) {
			errors.rejectValue("item.name", "Potrebno je odrediti naziv proizvoda.");
		} else if (form.getItem().getName().length() < 3) {
			errors.rejectValue("item.name", "Naziv proizvoda mora imati više od 3 znaka.");
		} else if (form.getMode() == FormMode.NEW && itemService.getActiveByName(form.getItem().getName()) != null) {
			errors.rejectValue("item.name",
					String.format("Nije moguće kreirati proizvod naziva %s zato što on već postoji.", form.getItem().getName()));
		} else if (form.getMode() == FormMode.EDIT) {
			Item item = itemService.getActiveByName(form.getItem().getName());
			if (item != null && item.getId() != form.getItem().getId()) {
				errors.rejectValue("item.name", String.format("Proizvod naziva %s već postoji.", form.getItem().getName()));
			}
		}
		
		if (form.getUnitsInStock().isEmpty()) {
			errors.rejectValue("unitsInStock", "Količina proizvoda mora biti zadana.");
		} else {
			try {
				int i = Integer.parseInt(form.getUnitsInStock());
				if (i < 0) {
					errors.rejectValue("unitsInStock", "Količina proizvoda ne može biti manja od 0.");
				}
			} catch (NumberFormatException e) {
				errors.rejectValue("unitsInStock", "Količina proizvoda treba biti cijeli broj.");
			}
		}
		
		if (form.getItem().getPricePerUnit() == null) {
			errors.rejectValue("item.pricePerUnit", "Cijena proizvoda mora biti zadana.");
		} else if (form.getItem().getPricePerUnit().signum() < 1) {
			errors.rejectValue("item.pricePerUnit", "Cijena proizvoda mora biti pozitivna i veća od nule.");
		}
		
		if (form.getItem().getManufacturer() == null) {
			errors.rejectValue("item.manufacturer", "Potrebno je odabrati proizvođača.");
		}
	}

}
