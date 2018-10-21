package tvz.naprednaJava.rozi.AutoServis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tvz.naprednaJava.rozi.AutoServis.AuthUser;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.enums.UserStatus;
import tvz.naprednaJava.rozi.AutoServis.form.UserForm;
import tvz.naprednaJava.rozi.AutoServis.form.validators.UserFormValidator;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private StationService stationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserFormValidator userFormValidator;

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}

	@PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Manager', 'ROLE_Employee')")
	@RequestMapping("/private/clients")
	public String showClients(Model model, RedirectAttributes redirectAttributes) {
		List<User> users = (List<User>) userService.getAllWithRole(roleService.getByName("Customer"));
		model.addAttribute("users", users);
		model.addAttribute("clients", true);
		return "authorized_pages/users/index";
	}

	@RequestMapping("/private/users-table")
	public String showUserTable(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		if (user.getRole().getName().equals("Customer")) {
			redirectAttributes.addFlashAttribute("errors", "Nemate dozvolu za pristup ovoj stranici.");
			return "redirect:/private/";
		}

		List<User> users = (List<User>) userService.getAllWithRole(roleService.getByName("Admin"));
		users.addAll(userService.getAllWithRole(roleService.getByName("Manager")));
		users.addAll(userService.getAllWithRole(roleService.getByName("Employee")));
		model.addAttribute("users", users);

		model.addAttribute("authUser", user);

		return "authorized_pages/users/index";
	}

	@RequestMapping("/private/user/view")
	public String viewMyProfile(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getByUsername(username);
		model.addAttribute("user", user);
		model.addAttribute("myProfile", true);
		return "authorized_pages/users/view";
	}

	@PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Manager', 'ROLE_Employee')")
	@RequestMapping("/private/user/view/{user}")
	public String userView(@PathVariable User user, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser1) {
		if (user == null) {
			redirectAttributes.addFlashAttribute("errors", "Korisnik nije pronađen");
			return "redirect:/";
		}
		User authUser = userService.getById(authUser1.getId());
		if (authUser.getId() == user.getId()) {
			model.addAttribute("myProfile", true);
		}
		model.addAttribute("user", user);
		return "authorized_pages/users/view";
	}

	@RequestMapping("/private/user/edit")
	public String editMyProfile(Model model, @AuthenticationPrincipal AuthUser authUser) {
		User user = userService.getById(authUser.getId());
		model.addAttribute("user", user);
		model.addAttribute("myProfile", true);
		model.addAttribute("statuses", UserStatus.values());
		if (user.getRole().getName().equals("Admin")) {
			List<Role> roles = (List<Role>) roleService.getAll();
			model.addAttribute("roles", roles);
		}

		UserForm form = new UserForm(FormMode.EDIT, user);
		model.addAttribute("form", form);
		return "authorized_pages/users/edit";
	}

	@RequestMapping("/private/user/edit/{user}")
	public String editUser(@PathVariable User user, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AuthUser authUser1) {
		if (user == null) {
			redirectAttributes.addFlashAttribute("errors", "Korisnik nije pronađen");
			return "redirect:/private/";
		}
		User authUser = userService.getById(authUser1.getId());
		// Customers and employees can only edit their profile
		if (authUser.getRole().getName().equals("Customer") || authUser.getRole().getName().equals("Employee")) {
			if (authUser.getId() != user.getId()) {
				redirectAttributes.addFlashAttribute("errors", "Nemate dozvolu za pristup ovoj stranici.");
				return "redirect:/private/";
			} else {
				// Customer or employee editing his profile
				model.addAttribute("myProfile", true);
			}
		} else if (authUser.getRole().getName().equals("Manager")) {
			// Manager can edit his profile and profiles of employees where he is in charg
			if (authUser.getId() != user.getId()) {
				// Check if this user id is registered as employ of statin where he works
				boolean isEmployeeOfStation = false;
				for (User tmpUser : authUser.getManagerOfStation().getEmployees()) {
					if (tmpUser.getId() == user.getId()) {
						isEmployeeOfStation = true;
					}
				}
				if (isEmployeeOfStation == false) {
					redirectAttributes.addFlashAttribute("errors", "Nemate dozvolu za pristup ovoj stranici.");
					return "redirect:/private/";
				} else {
					// Manager editing profile of employee thats employeed on station where manager is manager :)
					model.addAttribute("managerEdit", true);
				}
			} else {
				// Manager editing his profile
				model.addAttribute("myProfile", true);
			}
		} else {
			// Admin editing his profile
			if (authUser.getId() == user.getId()) {
				model.addAttribute("myProfile", true);
			}
		}
		if (authUser.getRole().getName().equals("Admin")) {
			model.addAttribute("roles", roleService.getAll());
			model.addAttribute("stations", stationService.getAll());
		}

		model.addAttribute("statuses", UserStatus.values());

		UserForm form = new UserForm(FormMode.EDIT, user);
		model.addAttribute("form", form);

		return "authorized_pages/users/edit";
	}

	@PreAuthorize("hasRole('ROLE_Admin')")
	@RequestMapping("/private/user/add")
	public String addUser(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("roles", roleService.getAll());
		model.addAttribute("stations", stationService.getAll());
		model.addAttribute("statuses", UserStatus.values());

		UserForm form = new UserForm(FormMode.NEW);
		model.addAttribute("form", form);
		return "authorized_pages/users/add";
	}

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public String createPublicUser(@ModelAttribute UserForm form, Model model, RedirectAttributes redirectAttributes) {
		if (form.getNewPassword().equals(form.getNewPasswordConfirm()) && !form.getNewPassword().equals("") && !form.getNewPasswordConfirm().equals("")) {
			form.getUser().setPassword(passwordEncoder.encode(form.getNewPassword()));
		} else {
			redirectAttributes.addFlashAttribute("errors", "Lozinke se ne podudaraju");
			return "redirect:/";
		}
		if (userService.getByUsername(form.getUser().getUsername()) != null) {
			redirectAttributes.addFlashAttribute("errors", String.format("Korisničko ime %s se već koristi.", form.getUser().getUsername()));
			return "redirect:/";
		}
		if (userService.getByEmail(form.getUser().getEmail()) != null) {
			redirectAttributes.addFlashAttribute("errors", String.format("E-mail adresa %s se već koristi.", form.getUser().getEmail()));
			return "redirect:/";
		}

		form.getUser().setUserStatus(UserStatus.ACTIVE);
		Role role = roleService.getByName("Customer");
		form.getUser().setRole(role);

		userService.create(form.getUser());

		redirectAttributes.addFlashAttribute("success", "Korisnik uspješno kreiran");
		return "redirect:/";
	}

	@RequestMapping(value = "/private/user/create", method = RequestMethod.POST)
	public String createUser(@ModelAttribute UserForm form, Model model, RedirectAttributes redirectAttributes) {
		if (form.getNewPassword().equals(form.getNewPasswordConfirm()) && !form.getNewPassword().equals("") && !form.getNewPasswordConfirm().equals("")) {
			form.getUser().setPassword(passwordEncoder.encode(form.getNewPassword()));
		} else {
			redirectAttributes.addFlashAttribute("errors", "Lozinke se ne podudaraju");
			return "redirect:/private/";
		}
		if (userService.getByUsername(form.getUser().getUsername()) != null) {
			redirectAttributes.addFlashAttribute("errors", String.format("Korisničko ime %s se već koristi.", form.getUser().getUsername()));
			return "redirect:/private/";
		}
		if (userService.getByEmail(form.getUser().getEmail()) != null) {
			redirectAttributes.addFlashAttribute("errors", String.format("E-mail adresa %s se već koristi.", form.getUser().getEmail()));
			return "redirect:/private/";
		}

		User user = form.getUser();
		if (user.getRole().getName().equals("Admin") || user.getRole().getName().equals("Customer")) {
			// Admin and customer can't be employee or manager of station
			if (user.getEmployeeOfStation() != null) {
				user.setEmployeeOfStation(null);
			}
			if (user.getManagerOfStation() != null) {
				user.setManagerOfStation(null);
			}
		} else if (user.getRole().getName().equals("Manager")) {
			// Manager can't be employee of service station
			if (user.getEmployeeOfStation() != null) {
				user.setEmployeeOfStation(null);
			}
		} else if (user.getRole().getName().equals("Employee")) {
			// Employee can't be manager of service station
			if (user.getManagerOfStation() != null) {
				user.setManagerOfStation(null);
			}
		}

		userService.create(user);

		redirectAttributes.addFlashAttribute("success", "Korisnik uspješno kreiran");
		return "redirect:/private/";
	}

	@RequestMapping(value = "/private/user/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute UserForm form, Model model, RedirectAttributes redirectAttributes) {
		if (form.getUser() != null) {
			if (form.getUser().getId() == null) {
				redirectAttributes.addFlashAttribute("errors", "Korisnički id ne postoji u formi. Nije moguće ažurirati korisnika.");
				return "redirect:/private/";
			}
		} else {
			redirectAttributes.addFlashAttribute("errors", "Korisnik ne postoji u formi. Nije moguće ažurirati korisnika.");
			return "redirect:/private/";
		}
		User dbUser = userService.getById(form.getUser().getId());

		// provjera mijenjaju li se lozinke
		if (!form.getNewPassword().equals("") && !form.getNewPasswordConfirm().equals("")) {
			if (form.getNewPassword().equals(form.getNewPasswordConfirm())) {
				String newPass = passwordEncoder.encode(form.getNewPassword());
				if (!dbUser.getPassword().equals(newPass)) {
					// pass update
					dbUser.setPassword(newPass);
					redirectAttributes.addFlashAttribute("success", "Lozinke ažurirana.");
				} else {
					redirectAttributes.addFlashAttribute("error", "Lozinke nije ažurirana. Stara i nova lozinka su iste.");
				}
			} else {
				redirectAttributes.addFlashAttribute("errors", "Lozinke se ne podudaraju");
				return "redirect:/private/";
			}
		}

		if (form.getUser().getUsername() != null && !dbUser.getUsername().equals(form.getUser().getUsername())) {
			// username changed
			if (userService.getByUsername(form.getUser().getUsername()) != null) {
				redirectAttributes.addFlashAttribute("errors", String.format("Korisničko ime %s se već koristi.", form.getUser().getUsername()));
				return "redirect:/private/";
			}
			if (form.getUser().getUsername().equals("")) {
				redirectAttributes.addFlashAttribute("errors", "Korisničko ime mora biti zadano.");
				return "redirect:/private/";
			}
			dbUser.setUsername(form.getUser().getUsername());
		}
		if (form.getUser().getEmail() != null && !dbUser.getEmail().equals(form.getUser().getEmail())) {
			// email changed
			if (userService.getByEmail(form.getUser().getEmail()) != null) {
				redirectAttributes.addFlashAttribute("errors", String.format("E-mail adresa %s se već koristi.", form.getUser().getEmail()));
				return "redirect:/private/";
			}
			if (form.getUser().getEmail().equals("")) {
				redirectAttributes.addFlashAttribute("errors", "E-mail mora biti zadan.");
				return "redirect:/private/";
			}
			dbUser.setEmail(form.getUser().getEmail());
		}
		if (form.getUser().getStatus() != null && dbUser.getStatus() != form.getUser().getStatus()) {
			dbUser.setUserStatus(form.getUser().getStatus());
		}
		if (form.getUser().getRole() != null && dbUser.getRole().getId() != form.getUser().getRole().getId()) {
			// Roles changed. Clear old jobs
			if (dbUser.getRole().getName().equals("Manager")) {
				dbUser.setManagerOfStation(null);
			}
			if (dbUser.getRole().getName().equals("Employee")) {
				dbUser.setEmployeeOfStation(null);
			}
			dbUser.setRole(form.getUser().getRole());
		}
		if (form.getUser().getRole().getName().equals("Manager") && form.getUser().getManagerOfStation() != null) {
			if (dbUser.getManagerOfStation() != null && dbUser.getManagerOfStation().getId() != form.getUser().getManagerOfStation().getId()) {
				// changed which station he is manager on
				dbUser.setManagerOfStation(form.getUser().getManagerOfStation());
			} else if (dbUser.getManagerOfStation() == null) {
				// was not manager of station but now is
				dbUser.setManagerOfStation(form.getUser().getManagerOfStation());
			}
		}
		if (form.getUser().getRole().getName().equals("Employee") && form.getUser().getEmployeeOfStation() != null) {
			if (dbUser.getEmployeeOfStation() != null && dbUser.getEmployeeOfStation().getId() != form.getUser().getEmployeeOfStation().getId()) {
				// changed which station he is working on
				dbUser.setEmployeeOfStation(form.getUser().getEmployeeOfStation());
			} else if (dbUser.getEmployeeOfStation() == null) {
				// was not working on a station but now is
				dbUser.setEmployeeOfStation(form.getUser().getEmployeeOfStation());
			}
		}
		if (!dbUser.getFirstName().equals(form.getUser().getFirstName())) {
			dbUser.setFirstName(form.getUser().getFirstName());
		}
		if (!dbUser.getLastName().equals(form.getUser().getLastName())) {
			dbUser.setLastName(form.getUser().getLastName());
		}

		userService.update(dbUser);

		redirectAttributes.addFlashAttribute("success", "Korisnički podatci uspješno ažurirani");
		return "redirect:/private/";
	}

	@PreAuthorize("hasRole('ROLE_Admin')")
	@RequestMapping("/private/user/delete/{user}")
	public String delete(@PathVariable User user, Model model, RedirectAttributes redirectAttributes) {
		user.setUserStatus(UserStatus.DELETED);
		userService.update(user);

		redirectAttributes.addFlashAttribute("success", "Korisnik uspješno obrisan.");

		return "redirect:/private/";
	}

}
