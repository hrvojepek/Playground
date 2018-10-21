package tvz.naprednaJava.rozi.AutoServis.form.validators;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.form.StationForm;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;

@Component
public class StationFormValidator implements Validator {

	@Autowired
	private StationService stationService;

	@Override
	public boolean supports(Class<?> arg0) {
		return StationForm.class.equals(arg0);
	}

	@Override
	public void validate(Object object, Errors errors) {
		StationForm form = (StationForm) object;
		if (form.getStation().getName() == null || form.getStation().getName().isEmpty()) {
			errors.rejectValue("station.name", "Potrebno je odrediti naziv poslovnice.");
		} else if (form.getStation().getName().length() < 3) {
			errors.rejectValue("station.name", "Naziv poslovnice mora imati više od 3 znaka.");
		} else if (form.getMode() == FormMode.NEW && stationService.getActiveByName(form.getStation().getName()) != null) {
			errors.rejectValue("station.name",
					String.format("Nije moguće kreirati poslovnicu naziva %s zato što ona već postoji.", form.getStation().getName()));
		} else if (form.getMode() == FormMode.EDIT) {
			Station station = stationService.getActiveByName(form.getStation().getName());
			if (station != null && station.getId() != form.getStation().getId()) {
				errors.rejectValue("station.name", String.format("Poslovnica naziva %s već postoji.", form.getStation().getName()));
			}
		}

		if (form.getStation().getAddress() == null || form.getStation().getAddress().isEmpty()) {
			errors.rejectValue("station.address", "Potrebno je odrediti adresu poslovnice.");
		} else if (form.getStation().getAddress().length() < 3) {
			errors.rejectValue("station.address", "Adresa poslovnice mora imati više od 3 znaka.");
		} else if (form.getMode() == FormMode.NEW && stationService.getActiveByAddress(form.getStation().getAddress()) != null) {
			errors.rejectValue("station.address",
					String.format("Nije moguće kreirati poslovnicu zato što se adresa %s već koristi.", form.getStation().getAddress()));
		} else if (form.getMode() == FormMode.EDIT) {
			Station station = stationService.getActiveByAddress(form.getStation().getAddress());
			if (station != null && station.getId() != form.getStation().getId()) {
				errors.rejectValue("station.address",
						String.format("Nije moguće ažurirati poslovnicu zato što se adresa %s već koristi.", form.getStation().getAddress()));
			}
		}

		if (form.getStation().getGeolocation() == null || form.getStation().getGeolocation().isEmpty()) {
			errors.rejectValue("station.geolocation", "Potrebno je odrediti geolokaciju poslovnice.");
		} else if (form.getStation().getGeolocation().split(",").length != 2) {
			errors.rejectValue("station.geolocation", "Geolokacija mora sadržavati dvije vrijednosti. Širinu i dužinu.");
		} else if (form.getMode() == FormMode.NEW && stationService.getActiveByGeolocation(form.getStation().getGeolocation()) != null) {
			errors.rejectValue("station.geolocation",
					String.format("Nije moguće kreirati poslovnicu zato što se geolokacija %s već koristi.", form.getStation().getGeolocation()));
		} else if (form.getMode() == FormMode.EDIT) {
			Station station = stationService.getActiveByGeolocation(form.getStation().getGeolocation());
			if (station != null && station.getId() != form.getStation().getId()) {
				errors.rejectValue("station.geolocation",
						String.format("Nije moguće ažurirati poslovnicu zato što se geolokacija %s već koristi.", form.getStation().getGeolocation()));
			}
		} else {
			double lat = Double.valueOf(form.getStation().getGeolocation().split(",")[0]);
			double lng = Double.valueOf(form.getStation().getGeolocation().split(",")[1]);
			if (lat < -90 || lat > 90) {
				errors.rejectValue("station.geolocation",
						String.format("Geolokacijska vrijednost %s nije valjana. Visina mora biti između -90 i 90", form.getStation().getGeolocation()));
			}
			if (lng < -180 || lng > 180) {
				errors.rejectValue("station.geolocation",
						String.format("Geolokacijska vrijednost %s nije valjana. Đirina mora biti između -180 i 180", form.getStation().getGeolocation()));
			}
		}
		
		LocalTime openFrom = null;
		LocalTime openUntil = null;
		if (form.getOpenFrom() != null && !form.getOpenFrom().isEmpty()) {
			try {
				openFrom = LocalTime.parse(form.getOpenFrom());
			} catch (DateTimeParseException e) {
				errors.rejectValue("openFrom", "Početak radnog vremena je zadan u krivom formatu.");
			}
		}
		if (form.getOpenUntil() != null && !form.getOpenUntil().isEmpty()) {
			try {
				openUntil = LocalTime.parse(form.getOpenUntil());
			} catch (DateTimeParseException e) {
				errors.rejectValue("openUntil", "Kraj radnog vremena je zadan u krivom formatu.");
			}
		}
		if (openFrom != null && openUntil != null && openFrom.isAfter(openUntil)) {
			errors.rejectValue("openUntil", "Početak radnog vremena mora biti prije kraja radnog vremena.");
		}
		
		if (form.getStation().getManager() != null && stationService.getByManager(form.getStation().getManager()) != null) {
			errors.rejectValue("station.manager", "Svaka stanica mora imati svog voditelja. Nije moguće da jedna osoba vodi dvije poslovnice.");
		}
	}
}
