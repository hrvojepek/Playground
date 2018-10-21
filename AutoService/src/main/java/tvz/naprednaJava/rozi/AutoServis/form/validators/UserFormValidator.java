package tvz.naprednaJava.rozi.AutoServis.form.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tvz.naprednaJava.rozi.AutoServis.form.UserForm;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Component
public class UserFormValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		UserForm form = (UserForm) object;
	}

}
