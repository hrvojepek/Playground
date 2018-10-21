package tvz.naprednaJava.rozi.AutoServis.controller;

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
import tvz.naprednaJava.rozi.AutoServis.form.RepairForm;
import tvz.naprednaJava.rozi.AutoServis.form.validators.RepairFormValidator;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.service.RepairService;

@Controller
public class RepairController {

	@Autowired
	private RepairService repairService;

	@Autowired
	private RepairFormValidator repairFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(repairFormValidator);
	}

	@RequestMapping("/repairs")
	public String showUserTable(Model model) {
		model.addAttribute("repairs", repairService.getAllActive());
		return "repair/index";
	}

	@PreAuthorize(value = "hasAnyAuthority('repair.view', 'repair.edit', 'repair.create')")
	@RequestMapping("/private/repairs")
	public String privateServicesTable(Model model) {
		model.addAttribute("repairs", repairService.getAllActive());
		return "authorized_pages/repair/index";
	}

	@PreAuthorize(value = "hasAuthority('repair.create')")
	@RequestMapping("/private/repair/new")
	public String add(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("form", new RepairForm(FormMode.NEW));
		return "authorized_pages/repair/add-repair";
	}

	@PreAuthorize(value = "hasAuthority('repair.edit')")
	@RequestMapping("/private/repair/edit/{repair}")
	public String edit(@PathVariable Repair repair, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("form", new RepairForm(FormMode.EDIT, repair));
		return "authorized_pages/repair/edit-repair";
	}

	@PreAuthorize(value = "hasAuthority('repair.view')")
	@RequestMapping("/private/repair/view/{repair}")
	public String view(@PathVariable Repair repair, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("repair", repair);
		return "authorized_pages/repair/view-repair";
	}

	@PreAuthorize(value = "hasAuthority('repair.create')")
	@RequestMapping(value = "/private/repair/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("form") @Validated RepairForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		List<String> errors = Utils.getErrorMessages(bindingResult);
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/private/repairs";
		}

		Repair repair = form.getRepair();
		repair.setStatus(Status.ACTIVE);
		if (repairService.create(repair) == null) {
			redirectAttributes.addFlashAttribute("errors",
					String.format("Došlo je do pogreške prilikom kreiranja usluge servisa naziva %s.", form.getRepair().getName()));
			return "redirect:/private/repairs";
		}

		redirectAttributes.addFlashAttribute("success", "Usluga servisa je uspješno kreirana.");
		return "redirect:/private/repairs";
	}

	@PreAuthorize(value = "hasAuthority('repair.edit')")
	@RequestMapping(value = "/private/repair/update", method = RequestMethod.POST)
	public String update(@RequestParam String action, @ModelAttribute("form") @Validated RepairForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// validate action
		if (action.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", "Potrebno je odrediti akciju nad objektom.");
			return "redirect:/private/repairs";
		} else if (!action.equals("delete") && !action.equals("save")) {
			redirectAttributes.addFlashAttribute("errors", "Akcija " + action + " nije dozvoljena.");
			return "redirect:/private/repairs";
		} else if (action.equals("delete")) {
			repairService.delete(form.getRepair());
			redirectAttributes.addFlashAttribute("success", "Usluga servisa je uspješno izbrisana.");
			return "redirect:/private/repairs";
		}
		// validate form
		List<String> errors = Utils.getErrorMessages(bindingResult);
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/private/repairs";
		}

		if (repairService.update(form.getRepair()) == null) {
			redirectAttributes.addFlashAttribute("errors",
					String.format("Došlo je do pogreške prilikom ažuriranja usluge servisa s id-om %d", form.getRepair().getId()));
			return "redirect:/private/repairs";
		}

		redirectAttributes.addFlashAttribute("success", "Usluga servisa je uspješno ažurirana.");
		return "redirect:/private/repairs";
	}
}
