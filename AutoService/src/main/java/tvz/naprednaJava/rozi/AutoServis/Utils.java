package tvz.naprednaJava.rozi.AutoServis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class Utils {
	
	public static List<String> getErrorMessages(BindingResult bindingResult) {
		List<String> errorMessages = new ArrayList<String>();
		if (!bindingResult.hasErrors()) {
			return errorMessages;
		}
		for (ObjectError objectError : bindingResult.getAllErrors()) {
			errorMessages.add(objectError.getCode());
		}
		return errorMessages;
	}
}
