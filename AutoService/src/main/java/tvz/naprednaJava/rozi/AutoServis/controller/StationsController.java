package tvz.naprednaJava.rozi.AutoServis.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tvz.naprednaJava.rozi.AutoServis.Utils;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.enums.Status;
import tvz.naprednaJava.rozi.AutoServis.form.StationForm;
import tvz.naprednaJava.rozi.AutoServis.form.validators.StationFormValidator;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Controller
public class StationsController {

	@Autowired
	private StationService stationService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private StationFormValidator stationFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(stationFormValidator);
	}

	@RequestMapping("/stations")
	public String publicStationsList(Model model) {
		List<Station> stations = (List<Station>) stationService.getAllActive();
		model.addAttribute("stations", stations);
		return "stations/index";
	}

	@PreAuthorize(value = "hasAnyAuthority('serviceStation.view', 'serviceStation.edit', 'serviceStation.create')")
	@RequestMapping("/private/stations")
	public String privateStationsList(Model model) {
		model.addAttribute("stations", stationService.getAllActive());
		return "authorized_pages/stations/index";
	}

	@PreAuthorize(value = "hasAuthority('serviceStation.create')")
	@RequestMapping("/private/station/new")
	public String add(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("form", new StationForm(FormMode.NEW));
		List<User> managers = (List<User>) userService.getAllWithRole(roleService.getByName("Manager"));
		model.addAttribute("managers", managers);
		return "authorized_pages/stations/add-station";
	}

	@PreAuthorize(value = "hasAuthority('serviceStation.edit')")
	@RequestMapping("/private/station/edit/{station}")
	public String edit(@PathVariable Station station, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("form", new StationForm(FormMode.EDIT, station));
		model.addAttribute("managers", userService.getAllWithRole(roleService.getByName("Manager")));
		return "authorized_pages/stations/edit-station";
	}

	@PreAuthorize(value = "hasAuthority('serviceStation.view')")
	@RequestMapping("/private/station/view/{station}")
	public String view(@PathVariable Station station, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("station", station);
		return "authorized_pages/stations/view-station";
	}

	@PreAuthorize(value = "hasAuthority('serviceStation.create')")
	@RequestMapping(value = "/private/station/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("form") @Validated StationForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		List<String> errors = Utils.getErrorMessages(bindingResult);
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/private/stations";
		}

		Station station = form.getStation();
		station.setStatus(Status.ACTIVE);
		if (form.getOpenFrom() != null && !form.getOpenFrom().isEmpty()) {
			station.setOpenFrom(LocalTime.parse(form.getOpenFrom()));
		}
		if (form.getOpenUntil() != null && !form.getOpenUntil().isEmpty()) {
			station.setOpenUntil(LocalTime.parse(form.getOpenUntil()));
		}
		if (stationService.create(station) == null) {
			redirectAttributes.addFlashAttribute("errors",
					String.format("Došlo je do pogreške prilikom kreiranja poslovnice naziva %s.", form.getStation().getName()));
			return "redirect:/private/stations";
		}

		redirectAttributes.addFlashAttribute("success", "Poslovnica je uspješno kreirana.");
		return "redirect:/private/stations";
	}

	@PreAuthorize(value = "hasAuthority('serviceStation.edit')")
	@RequestMapping(value = "/private/station/update", method = RequestMethod.POST)
	public String update(@RequestParam String action, @ModelAttribute("form") @Validated StationForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// validate action
		if (action.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", "Potrebno je odrediti akciju nad objektom.");
			return "redirect:/private/stations";
		} else if (!action.equals("delete") && !action.equals("save")) {
			redirectAttributes.addFlashAttribute("errors", "Akcija " + action + " nije dozvoljena.");
			return "redirect:/private/stations";
		} else if (action.equals("delete")) {
			stationService.delete(form.getStation());
			redirectAttributes.addFlashAttribute("success", "Poslovnica je uspješno izbrisana.");
			return "redirect:/private/stations";
		}
		// validate form
		List<String> errors = Utils.getErrorMessages(bindingResult);
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/private/stations";
		}

		Station station = form.getStation();
		if (form.getOpenFrom() != null && !form.getOpenFrom().isEmpty()) {
			station.setOpenFrom(LocalTime.parse(form.getOpenFrom()));
		}
		if (form.getOpenUntil() != null && !form.getOpenUntil().isEmpty()) {
			station.setOpenUntil(LocalTime.parse(form.getOpenUntil()));
		}
		if (stationService.update(station) == null) {
			redirectAttributes.addFlashAttribute("errors",
					String.format("Došlo je do pogreške prilikom ažuriranja poslovnice s id-om %d", form.getStation().getId()));
			return "redirect:/private/stations";
		}

		redirectAttributes.addFlashAttribute("success", "Poslovnica je uspješno ažuriran.");
		return "redirect:/private/stations";
	}
}
