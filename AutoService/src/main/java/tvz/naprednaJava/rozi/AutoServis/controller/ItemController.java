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
import tvz.naprednaJava.rozi.AutoServis.form.ItemForm;
import tvz.naprednaJava.rozi.AutoServis.form.validators.ItemFormValidator;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.service.ItemService;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private ItemFormValidator itemFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(itemFormValidator);
	}

	@RequestMapping("/items")
	public String index(Model model) {
		model.addAttribute("items", itemService.getAllActive());
		return "items/index";
	}

	@PreAuthorize(value = "hasAnyAuthority('item.view', 'item.edit', 'item.create')")
	@RequestMapping("/private/items")
	public String privateProductTable(Model model) {
		model.addAttribute("items", itemService.getAllActive());
		return "authorized_pages/items/index";
	}

	@PreAuthorize(value = "hasAnyAuthority('item.view', 'item.edit', 'item.create')")
	@RequestMapping("/private/items/manufacturer/{manufacturer}")
	public String privateProductTableOfManufacturer(@PathVariable Manufacturer manufacturer, Model model) {
		model.addAttribute("items", itemService.getAllActiveByManufacturer(manufacturer));
		return "authorized_pages/items/index";
	}

	@PreAuthorize(value = "hasAuthority('item.create')")
	@RequestMapping("/private/item/new")
	public String add(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("form", new ItemForm(FormMode.NEW));
		model.addAttribute("manufacturers", manufacturerService.getAllActive());
		return "authorized_pages/items/add-item";
	}

	@PreAuthorize(value = "hasAuthority('item.edit')")
	@RequestMapping("/private/item/edit/{item}")
	public String edit(@PathVariable Item item, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("form", new ItemForm(FormMode.EDIT, item));
		model.addAttribute("manufacturers", manufacturerService.getAllActive());
		return "authorized_pages/items/edit-item";
	}

	@PreAuthorize(value = "hasAuthority('item.view')")
	@RequestMapping("/private/item/view/{item}")
	public String view(@PathVariable Item item, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("item", item);
		return "authorized_pages/items/view-item";
	}

	@PreAuthorize(value = "hasAuthority('item.create')")
	@RequestMapping(value = "/private/item/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("form") @Validated ItemForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		List<String> errors = Utils.getErrorMessages(bindingResult);
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/private/items";
		}

		Item item = form.getItem();
		item.setStatus(Status.ACTIVE);
		item.setUnitsInStock(Integer.parseInt(form.getUnitsInStock()));
		if (itemService.create(item) == null) {
			redirectAttributes.addFlashAttribute("errors",
					String.format("Došlo je do pogreške prilikom kreiranja proizvoda naziva %s.", form.getItem().getName()));
			return "redirect:/private/items";
		}

		redirectAttributes.addFlashAttribute("success", "Proizvod je uspješno kreiran.");
		return "redirect:/private/items";
	}

	@PreAuthorize(value = "hasAuthority('item.edit')")
	@RequestMapping(value = "/private/item/update", method = RequestMethod.POST)
	public String update(@RequestParam String action, @ModelAttribute("form") @Validated ItemForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// validate action
		if (action.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", "Potrebno je odrediti akciju nad objektom.");
			return "redirect:/private/items";
		} else if (!action.equals("delete") && !action.equals("save")) {
			redirectAttributes.addFlashAttribute("errors", "Akcija " + action + " nije dozvoljena.");
			return "redirect:/private/items";
		} else if (action.equals("delete")) {
			itemService.delete(form.getItem());
			redirectAttributes.addFlashAttribute("success", "Proizvod je uspješno izbrisan.");
			return "redirect:/private/items";
		}
		// validate form
		List<String> errors = Utils.getErrorMessages(bindingResult);
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/private/items";
		}

		Item item = form.getItem();
		item.setUnitsInStock(Integer.parseInt(form.getUnitsInStock()));
		if (itemService.update(item) == null) {
			redirectAttributes.addFlashAttribute("errors",
					String.format("Došlo je do pogreške prilikom ažuriranja proizvoda s id-om %d", form.getItem().getId()));
			return "redirect:/private/items";
		}

		redirectAttributes.addFlashAttribute("success", "Proizvod je uspješno ažuriran.");
		return "redirect:/private/items";
	}
}
