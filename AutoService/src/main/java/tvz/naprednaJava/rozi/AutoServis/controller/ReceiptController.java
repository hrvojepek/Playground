package tvz.naprednaJava.rozi.AutoServis.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tvz.naprednaJava.rozi.AutoServis.AuthUser;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.enums.ReservationStatus;
import tvz.naprednaJava.rozi.AutoServis.form.ReceiptForm;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Receipt;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.model.Reservation;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.ItemService;
import tvz.naprednaJava.rozi.AutoServis.service.ReceiptService;
import tvz.naprednaJava.rozi.AutoServis.service.RepairService;
import tvz.naprednaJava.rozi.AutoServis.service.ReservationService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Controller
@RequestMapping("/private")
public class ReceiptController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private StationService stationService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private RepairService repairService;

	@Autowired
	private ReceiptService receiptService;

	@PreAuthorize("hasRole('ROLE_Customer')")
	@RequestMapping("/myReceipts")
	public String listMyReceipts(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (!user.getRole().getName().equals("Customer")) {
			redirectAttributes.addFlashAttribute("errors", "Samo klijent može vidjeti svoje račune.");
			return "redirect:/private/";
		}

		model.addAttribute("receipts", receiptService.getAllByCustomer(user));
		return "authorized_pages/receipts/index";
	}

	@PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Manager', 'ROLE_Employee')")
	@RequestMapping("/receipts")
	public String listReceipts(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("receipts", receiptService.getAll());
		return "authorized_pages/receipts/index";
	}

	@PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Manager', 'ROLE_Employee')")
	@RequestMapping("/receipt/new/{reservationId}")
	public String newReceipt(@PathVariable Long reservationId, Model model, RedirectAttributes redirectAttributes) {
		Reservation reservation = reservationService.getById(reservationId);
		if (reservation == null) {
			redirectAttributes.addFlashAttribute("errors", "Nije moguće kreirati račun bez rezervacije.");
			return "redirect:/private/";
		}

		ReceiptForm form = new ReceiptForm(FormMode.NEW, new Receipt());
		form.setReservationId(reservationId);
		form.setServiceStationsId(reservation.getStation().getId());
		model.addAttribute("form", form);

		model.addAttribute("station", reservation.getStation());
		List<Item> items = new ArrayList<>();
		for (Item item : itemService.getAll()) {
			if (item.getUnitsInStock() > 0) {
				items.add(item);
			}
		}
		model.addAttribute("items", items);
		model.addAttribute("repairs", repairService.getAllActive());

		return "authorized_pages/receipts/add";
	}

	@RequestMapping("/receipt/view/{receipt}")
	public String view(@PathVariable Receipt receipt, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (user.getRole().getName().equals("Customer") && user.getId() != receipt.getCustomer().getId()) {
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovoj stranici.");
			return "redirect:/private/";
		} else if (user.getRole().getName().equals("Employee") && receipt.getStation().getId() != user.getEmployeeOfStation().getId()) {
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovom računu. Niste zaposleni na ovoj stanici.");
			return "redirect:/private/";
		} else if (user.getRole().getName().equals("Manager") && receipt.getStation().getId() != user.getManagerOfStation().getId()) {
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovom računu. Niste voditelj ove stanice.");
			return "redirect:/private/";
		}

		model.addAttribute("receipt", receipt);

		return "authorized_pages/receipts/view";
	}

	@PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Manager', 'ROLE_Employee')")
	@RequestMapping(value = "/receipt/create", method = RequestMethod.POST)
	public String create(@ModelAttribute ReceiptForm form, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (user.getRole().getName().equals("Customer")) {
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovoj stranici.");
			return "redirect:/private/";
		}

		Receipt receipt = form.getReceipt();
		receipt.setBiller(user);
		Reservation reservation = reservationService.getById(form.getReservationId());
		receipt.setCustomer(reservation.getCustomer());

		if (receipt.getItems().isEmpty() && receipt.getRepairs().isEmpty()) {
			redirectAttributes.addFlashAttribute("errors", "Nije moguće kreirati račun bez proizvoda i usluga servisa.");
			return "redirect:/private/receipt/new/" + reservation.getId();
		}
		BigDecimal totalItemCost = new BigDecimal("0");
		for (Item item : receipt.getItems()) {
			int stock = item.getUnitsInStock() - 1;
			item.setUnitsInStock(stock);
			totalItemCost = totalItemCost.add(item.getPricePerUnit());
		}
		BigDecimal totalRepairServicesCost = new BigDecimal("0");
		for (Repair repair : receipt.getRepairs()) {
			totalRepairServicesCost = totalRepairServicesCost.add(repair.getPricePerHour());
		}
		receipt.setTotal(totalItemCost.add(totalRepairServicesCost));

		Station station = stationService.getById(form.getServiceStationsId());
		receipt.setStation(station);
		receipt.setReservation(reservation);
		receiptService.create(receipt);

		reservation.setReservationStatus(ReservationStatus.BILLED);
		reservationService.update(reservation);
		// update items because we reduced item stock number
		for (Item item : receipt.getItems()) {
			itemService.update(item);
		}

		redirectAttributes.addFlashAttribute("success", "Račun uspješno kreiran.");
		return "redirect:/private/receipts";
	}
}
