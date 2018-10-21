package tvz.naprednaJava.rozi.AutoServis.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import tvz.naprednaJava.rozi.AutoServis.form.ReservationForm;
import tvz.naprednaJava.rozi.AutoServis.model.Reservation;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.ReservationService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Controller
@RequestMapping("/private")
public class ReservationController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private StationService serviceStationService;

	@PreAuthorize("hasRole('ROLE_Customer')")
	@RequestMapping("/myReservations")
	public String myReservation(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (!user.getRole().getName().equals("Customer")) {
			redirectAttributes.addFlashAttribute("errors", "Samo klijent može vidjeti svoje rezervacije.");
			return "redirect:/private/";
		}

		model.addAttribute("reservations", reservationService.getAllByCustomer(user));
		return "authorized_pages/reservations/index";
	}

	@RequestMapping("/reservations")
	public String allReservations(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (user.getRole().getName().equals("Customer")) {
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovoj rezervaciji.");
			return "redirect:/private/";
		}
		model.addAttribute("reservations", reservationService.getAll());
		return "authorized_pages/reservations/index";
	}

	@RequestMapping("/reservation/new")
	public String newReservation(Model model, RedirectAttributes redirectAttributes) {
		ReservationForm form = new ReservationForm(FormMode.NEW);
		model.addAttribute("form", form);
		model.addAttribute("stations", serviceStationService.getAll());

		return "authorized_pages/reservations/add";
	}

	@RequestMapping("/reservation/view/{reservation}")
	public String view(@PathVariable Reservation reservation, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		model.addAttribute("authUser", user);
		model.addAttribute("reservation", reservation);

		return "authorized_pages/reservations/view";
	}

	@RequestMapping("/reservation/edit/{reservation}")
	public String edit(@PathVariable Reservation reservation, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (user.getRole().getName().equals("Customer")) {
			if (reservation.getCustomer().getId() != user.getId()) {
				// Klijenti koji nisu vlasnici ove rezervacije ne mogu joj
				// pristupiti
				redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovoj rezervaciji.");
				return "redirect:/private/";
			}
			if (reservation.getReservationStatus() != ReservationStatus.RESERVED) {
				redirectAttributes.addFlashAttribute("errors", "Ne možete ažurirati podatke zato što je popravak u tijeku ili je gotov.");
				return "redirect:/private/";
			}
		} else if (user.getRole().getName().equals("Employee") && reservation.getStation().getId() != user.getEmployeeOfStation().getId()) {
			// Employee može ažurirati rezervaciju ako je u poslovnici u kojoj on radi
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovoj rezervaciji. Niste zaposleni na ovoj stanici.");
			return "redirect:/private/";
		} else if (user.getRole().getName().equals("Manager") && reservation.getStation().getId() != user.getManagerOfStation().getId()) {
			// Manager može ažurirati rezervaciju ako je u njegovoj poslovnici
			redirectAttributes.addFlashAttribute("errors", "Nemate prava pristupiti ovoj rezervaciji. Niste voditelj ove stanice.");
			return "redirect:/private/";
		}

		if (model.containsAttribute("form")) {
			model.addAttribute("form", model.asMap().get("form"));
		} else {
			model.addAttribute("form", new ReservationForm(FormMode.EDIT, reservation));
		}

		model.addAttribute("stations", serviceStationService.getAll());
		model.addAttribute("repairmen", reservation.getStation().getEmployees());
		model.addAttribute("statuses", ReservationStatus.getForReservationEdit());

		return "authorized_pages/reservations/edit-reservation";
	}

	@RequestMapping(value = "/reservation/create", method = RequestMethod.POST)
	public String create(@ModelAttribute ReservationForm form, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (!user.getRole().getName().equals("Customer")) {
			redirectAttributes.addFlashAttribute("errors", "Samo klijent može kreirati novu rezervaciju.");
			return "redirect:/private/";
		} else {
			form.getReservation().setCustomer(user);
		}
		if (form.getRepairStartDate() != null && !form.getRepairStartDate().isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime repairStartDate = LocalDateTime.parse(form.getRepairStartDate(), formatter);
			if (provjeriTermin(repairStartDate, form.getReservation().getStation())) {
				form.getReservation().setRepairStartDate(repairStartDate);
			} else {
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("errors", "Termin nije slobodan. Molimo odaberite drugi termin.");
				return "redirect:/private/reservation/new/";
			}
		} else {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", "Morate odabrati termin početka popravka.");
			return "redirect:/private/reservation/new/";
		}

		form.getReservation().setReservationStatus(ReservationStatus.RESERVED);

		reservationService.create(form.getReservation());

		redirectAttributes.addFlashAttribute("success", "Rezervacija uspješno kreirana");
		return "redirect:/private/myReservations";
	}

	@RequestMapping(value = "/reservation/update", method = RequestMethod.POST)
	public String update(@ModelAttribute ReservationForm form, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		Reservation dbReservation = reservationService.getById(form.getReservation().getId());

		if (dbReservation.getReservationStatus() == ReservationStatus.BILLED) {
			redirectAttributes.addFlashAttribute("errors", "Nije moguće ažurirati naplačene servise.");
			return "redirect:/private/";
		}

		User user = userService.getById(authUser.getId());
		if (user.getRole().getName().equals("Customer")) {
			if (form.getReservation().getStation() != null && form.getReservation().getStation().getId() != null) {
				if (dbReservation.getStation().getId() != form.getReservation().getStation().getId()) {
					dbReservation.setStation(form.getReservation().getStation());
				}
			} else {
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("errors", "Niste odabrali stanicu servisa.");
				return "redirect:/private/reservation/edit/" + dbReservation.getId();
			}
		} else {
			if (form.getEstimatedRepairEndDate() != null && !form.getEstimatedRepairEndDate().isEmpty()) {
				LocalDateTime estimatedRepairEndDate = LocalDateTime.parse(form.getEstimatedRepairEndDate(), formatter);
				if (provjeriTermin(estimatedRepairEndDate, dbReservation.getStation())) {
					// provjera da li je termin valjan
					if (dbReservation.getEstimatedRepairEndDate() != null) {
						// pokušaj update-a
						if (!dbReservation.getEstimatedRepairEndDate().equals(estimatedRepairEndDate)) {
							dbReservation.setEstimatedRepairEndDate(estimatedRepairEndDate);
						}
					} else {
						// to je potpuno novi termin
						dbReservation.setEstimatedRepairEndDate(estimatedRepairEndDate);
					}
				} else {
					redirectAttributes.addFlashAttribute("form", form);
					redirectAttributes.addFlashAttribute("errors",
							"Predviđeno vrijeme popravka nije valjano. Stanica u tom terminu ne radi. Odaberite drugi termin.");
					return "redirect:/private/reservation/edit/" + dbReservation.getId();
				}
			}
			if (form.getReservation().getRepairman() != null) {
				if (dbReservation.getRepairman() != null) {
					// repairman update
					if (form.getReservation().getRepairman().getId() != dbReservation.getRepairman().getId()) {
						dbReservation.setRepairman(form.getReservation().getRepairman());
					}
				} else {
					// new repairman
					dbReservation.setRepairman(form.getReservation().getRepairman());
				}
			}
			if (form.getReservation().getReservationStatus() != null && form.getReservation().getReservationStatus() != dbReservation.getReservationStatus()) {
				dbReservation.setReservationStatus(form.getReservation().getReservationStatus());
			}
		}

		if (form.getRepairStartDate() != null && !form.getRepairStartDate().isEmpty()) {
			LocalDateTime repairStartDate = LocalDateTime.parse(form.getRepairStartDate(), formatter);
			if (!dbReservation.getRepairStartDate().equals(repairStartDate)) {
				// vremena nisu ista. Provjeriti da li je novo vrijeme validno.
				if (provjeriTermin(repairStartDate, dbReservation.getStation()) && reservationService
						.getAllByServiceStationAndRepairStartDateBetween(dbReservation.getStation(), repairStartDate.minusHours(1), repairStartDate)
						.isEmpty()) {
					form.getReservation().setRepairStartDate(repairStartDate);
				} else {
					redirectAttributes.addFlashAttribute("form", form);
					redirectAttributes.addFlashAttribute("errors", "Termin početka popravka nije slobodan. Molimo odaberite drugi termin.");
					return "redirect:/private/reservation/edit/" + dbReservation.getId();
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("form", form);
			redirectAttributes.addFlashAttribute("errors", "Morate odabrati termin početka popravka.");
			return "redirect:/private/reservation/edit/" + dbReservation.getId();
		}

		reservationService.update(dbReservation);

		redirectAttributes.addFlashAttribute("success", "Rezervacija uspješno ažurirana.");
		if (user.getRole().getName().equals("Customer")) {
			return "redirect:/private/myReservations";
		} else {
			return "redirect:/private/reservations";
		}

	}

	private boolean provjeriTermin(LocalDateTime dateTime, Station station) {
		if (station.getOpenFrom() != null && dateTime.toLocalTime().isBefore(station.getOpenFrom())) {
			return false;
		} else if (station.getOpenUntil() != null && dateTime.toLocalTime().isAfter(station.getOpenUntil())) {
			return false;
		} else {
			return true;
		}
	}
}
