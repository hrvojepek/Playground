package tvz.naprednaJava.rozi.AutoServis;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.enums.UserStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Item;
import tvz.naprednaJava.rozi.AutoServis.model.Manufacturer;
import tvz.naprednaJava.rozi.AutoServis.model.Permission;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.ItemService;
import tvz.naprednaJava.rozi.AutoServis.service.ManufacturerService;
import tvz.naprednaJava.rozi.AutoServis.service.PermissionService;
import tvz.naprednaJava.rozi.AutoServis.service.RepairService;
import tvz.naprednaJava.rozi.AutoServis.service.RoleService;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Component
public class StartupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private StationService stationService;

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private RepairService repairService;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		Permission userCreate = createPermissionIfNotFound("user.create");
		Permission userEdit = createPermissionIfNotFound("user.edit");
		Permission userView = createPermissionIfNotFound("user.view");
		Permission userDelete = createPermissionIfNotFound("user.delete");
		Permission roleCreate = createPermissionIfNotFound("role.create");
		Permission roleEdit = createPermissionIfNotFound("role.edit");
		Permission roleView = createPermissionIfNotFound("role.view");
		Permission permissionCreate = createPermissionIfNotFound("permission.create");
		Permission permissionEdit = createPermissionIfNotFound("permission.edit");
		Permission permissionView = createPermissionIfNotFound("permission.view");
		Permission administrationAccess = createPermissionIfNotFound("administration.access");
		Permission reservationCreate = createPermissionIfNotFound("reservation.create");
		Permission reservationEdit = createPermissionIfNotFound("reservation.edit");
		Permission reservationViewAdmin = createPermissionIfNotFound("reservation.view.admin");
		Permission reservationView = createPermissionIfNotFound("reservation.view");
		Permission itemCreate = createPermissionIfNotFound("item.create");
		Permission itemEdit = createPermissionIfNotFound("item.edit");
		Permission itemView = createPermissionIfNotFound("item.view");
		Permission manufacturerCreate = createPermissionIfNotFound("manufacturer.create");
		Permission manufacturerEdit = createPermissionIfNotFound("manufacturer.edit");
		Permission manufacturerView = createPermissionIfNotFound("manufacturer.view");
		Permission receiptCreate = createPermissionIfNotFound("receipt.create");
		Permission receiptEdit = createPermissionIfNotFound("receipt.edit");
		Permission receiptView = createPermissionIfNotFound("receipt.view");
		Permission repairCreate = createPermissionIfNotFound("repair.create");
		Permission repairEdit = createPermissionIfNotFound("repair.edit");
		Permission repairView = createPermissionIfNotFound("repair.view");
		Permission serviceStationCreate = createPermissionIfNotFound("serviceStation.create");
		Permission serviceStationEdit = createPermissionIfNotFound("serviceStation.edit");
		Permission serviceStationView = createPermissionIfNotFound("serviceStation.view");

		List<Permission> adminPermissions = new ArrayList<>();
		adminPermissions.add(userCreate);
		adminPermissions.add(userEdit);
		adminPermissions.add(userView);
		adminPermissions.add(userDelete);
		adminPermissions.add(roleCreate);
		adminPermissions.add(roleEdit);
		adminPermissions.add(roleView);
		adminPermissions.add(permissionCreate);
		adminPermissions.add(permissionEdit);
		adminPermissions.add(permissionView);
		adminPermissions.add(administrationAccess);
		adminPermissions.add(reservationCreate);
		adminPermissions.add(reservationEdit);
		adminPermissions.add(reservationViewAdmin);
		adminPermissions.add(reservationView);
		adminPermissions.add(itemCreate);
		adminPermissions.add(itemEdit);
		adminPermissions.add(itemView);
		adminPermissions.add(manufacturerCreate);
		adminPermissions.add(manufacturerEdit);
		adminPermissions.add(manufacturerView);
		adminPermissions.add(receiptCreate);
		adminPermissions.add(receiptEdit);
		adminPermissions.add(receiptView);
		adminPermissions.add(repairCreate);
		adminPermissions.add(repairEdit);
		adminPermissions.add(repairView);
		adminPermissions.add(serviceStationCreate);
		adminPermissions.add(serviceStationEdit);
		adminPermissions.add(serviceStationView);
		Role adminRole = createRoleIfNotFound("Admin", adminPermissions);
		createAdminIfNotFound(adminRole);

		List<Permission> managerPermissions = new ArrayList<>();
		managerPermissions.add(userView);
		managerPermissions.add(reservationCreate);
		managerPermissions.add(reservationEdit);
		managerPermissions.add(reservationView);
		adminPermissions.add(reservationViewAdmin);
		managerPermissions.add(itemCreate);
		managerPermissions.add(itemEdit);
		managerPermissions.add(itemView);
		managerPermissions.add(manufacturerCreate);
		managerPermissions.add(manufacturerEdit);
		managerPermissions.add(manufacturerView);
		managerPermissions.add(receiptCreate);
		managerPermissions.add(receiptEdit);
		managerPermissions.add(receiptView);
		managerPermissions.add(repairCreate);
		managerPermissions.add(repairEdit);
		managerPermissions.add(repairView);
		managerPermissions.add(serviceStationEdit);
		managerPermissions.add(serviceStationView);
		List<Permission> employeePermissions = new ArrayList<>();
		employeePermissions.add(userView);
		employeePermissions.add(reservationCreate);
		employeePermissions.add(reservationEdit);
		employeePermissions.add(reservationView);
		adminPermissions.add(reservationViewAdmin);
		employeePermissions.add(itemEdit);
		employeePermissions.add(itemView);
		employeePermissions.add(manufacturerView);
		employeePermissions.add(receiptCreate);
		employeePermissions.add(receiptEdit);
		employeePermissions.add(receiptView);
		employeePermissions.add(repairView);
		employeePermissions.add(serviceStationView);
		Role managerRole = createRoleIfNotFound("Manager", managerPermissions);
		Role employeeRole = createRoleIfNotFound("Employee", employeePermissions);
		createManagersEmployeesAndServiceStations(managerRole, employeeRole);

		List<Permission> customerPermissions = new ArrayList<>();
		customerPermissions.add(userView);
		customerPermissions.add(reservationCreate);
		customerPermissions.add(reservationEdit);
		customerPermissions.add(reservationView);
		customerPermissions.add(itemView);
		customerPermissions.add(manufacturerView);
		customerPermissions.add(receiptView);
		customerPermissions.add(repairView);
		customerPermissions.add(serviceStationView);
		createRoleIfNotFound("Customer", customerPermissions);

		createManufacturersIfNotFound();
		createItemsIfNotFound();
		createRepairsIfNotFound();
	}

	@Transactional
	private Permission createPermissionIfNotFound(String name) {
		Permission permission = permissionService.getByName(name);
		if (permission == null) {
			permission = new Permission();
			permission.setName(name);
			permissionService.create(permission);
		}
		return permission;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, List<Permission> permissions) {
		Role role = roleService.getByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
			role.setPermissions(permissions);
			roleService.create(role);
		}
		return role;
	}

	@Transactional
	private User createAdminIfNotFound(Role adminRole) {
		User user = userService.getByUsername("root");
		if (user == null) {
			user = new User("Admin", "User", "auser@tvz.hr", "root", new BCryptPasswordEncoder().encode("rootPass"), UserStatus.ACTIVE);
			user.setRole(adminRole);
			userService.create(user);
		}
		return user;
	}

	@Transactional
	private void createManagersEmployeesAndServiceStations(Role managerRole, Role employeeRole) {
		// Stuff for station 1
		User manager = userService.getByUsername("pero");
		if (manager == null) {
			manager = new User("Pero", "Perić", "pperic@tvz.hr", "pero", new BCryptPasswordEncoder().encode("peroPass"), UserStatus.ACTIVE);
			manager.setRole(managerRole);
			userService.create(manager);
		}

		Station serviceStation = createServiceStation("Zelena stanica", "Ilica 50, Zagreb", LocalTime.of(8, 0), LocalTime.of(20, 0), "45.8110073,16.0160445", manager);
		User emplyee1 = userService.getByUsername("zeljko");
		if (emplyee1 == null) {
			emplyee1 = new User("Željko", "Pernar", "zpernar@tvz.hr", "zeljko", new BCryptPasswordEncoder().encode("zeljkoPass"), UserStatus.ACTIVE);
			emplyee1.setRole(employeeRole);
			emplyee1.setEmployeeOfStation(serviceStation);
			userService.create(emplyee1);
		}
		User emplyee2 = userService.getByUsername("ivan");
		if (emplyee2 == null) {
			emplyee2 = new User("Ivan", "Nešto", "inesto@tvz.hr", "ivan", new BCryptPasswordEncoder().encode("ivanPass"), UserStatus.ACTIVE);
			emplyee2.setRole(employeeRole);
			emplyee2.setEmployeeOfStation(serviceStation);
			userService.create(emplyee2);
		}
		User emplyee3 = userService.getByUsername("arthur");
		if (emplyee3 == null) {
			emplyee3 = new User("Arthur", "Dent", "adent@tvz.hr", "arthur", new BCryptPasswordEncoder().encode("arthurPass"), UserStatus.ACTIVE);
			emplyee3.setRole(employeeRole);
			emplyee3.setEmployeeOfStation(serviceStation);
			userService.create(emplyee3);
		}
		// Stuff for station 2
		manager = userService.getByUsername("ford");
		if (manager == null) {
			manager = new User("Ford", "Prefect", "fprefect@tvz.hr", "ford", new BCryptPasswordEncoder().encode("fordPass"), UserStatus.ACTIVE);
			manager.setRole(managerRole);
			userService.create(manager);
		}
		serviceStation = createServiceStation("Stanica 2", "Vukovarska 15, Zagreb", LocalTime.of(8, 30), LocalTime.of(20, 30), "45.7726488,15.9514577", manager);
		emplyee1 = userService.getByUsername("marko");
		if (emplyee1 == null) {
			emplyee1 = new User("Marko", "Perković", "mperkovic@tvz.hr", "marko", new BCryptPasswordEncoder().encode("markoPass"), UserStatus.ACTIVE);
			emplyee1.setRole(employeeRole);
			emplyee1.setEmployeeOfStation(serviceStation);
			userService.create(emplyee1);
		}
		emplyee2 = userService.getByUsername("sinisa");
		if (emplyee2 == null) {
			emplyee2 = new User("Siniša", "Vuco", "svuco@tvz.hr", "sinisa", new BCryptPasswordEncoder().encode("sinisaPass"), UserStatus.ACTIVE);
			emplyee2.setRole(employeeRole);
			emplyee2.setEmployeeOfStation(serviceStation);
			userService.create(emplyee2);
		}
		emplyee3 = userService.getByUsername("mate");
		if (emplyee3 == null) {
			emplyee3 = new User("Mate", "Mišo", "mmiso@tvz.hr", "mate", new BCryptPasswordEncoder().encode("matePass"), UserStatus.ACTIVE);
			emplyee3.setRole(employeeRole);
			emplyee3.setEmployeeOfStation(serviceStation);
			userService.create(emplyee3);
		}
	}

	@Transactional
	private Station createServiceStation(String name, String address, LocalTime openFrom, LocalTime openUntil, String geolocation, User manager) {
		Station serviceStation = stationService.getActiveByName(name);
		if (serviceStation == null) {
			serviceStation = new Station(name, address, openFrom, openUntil, geolocation);
			serviceStation.setManager(manager);
			stationService.create(serviceStation);
		}
		return serviceStation;
	}

	private void createManufacturersIfNotFound() {
		Manufacturer manufacturer = null;
		if (manufacturerService.getByName("Manufacturer 1") == null) {
			manufacturer = new Manufacturer("Manufacturer 1");
			manufacturerService.create(manufacturer);
		}
		if (manufacturerService.getByName("Manufacturer 2") == null) {
			manufacturer = new Manufacturer("Manufacturer 2");
			manufacturerService.create(manufacturer);
		}
		if (manufacturerService.getByName("Manufacturer 3") == null) {
			manufacturer = new Manufacturer("Manufacturer 3");
			manufacturerService.create(manufacturer);
		}
		if (manufacturerService.getByName("Manufacturer 4") == null) {
			manufacturer = new Manufacturer("Manufacturer 4");
			manufacturerService.create(manufacturer);
		}
		if (manufacturerService.getByName("Manufacturer 5") == null) {
			manufacturer = new Manufacturer("Manufacturer 5");
			manufacturerService.create(manufacturer);
		}
	}

	private void createItemsIfNotFound() {
		Item item = itemService.getByName("Car battery 12V HT1568158");
		if (item == null) {
			item = new Item("Car battery 12V HT1568158", new BigDecimal(45), 5, "Opis ove baterije za auto.", manufacturerService.getByName("Manufacturer 1"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Windscreen wipers 48/2182");
		if (item == null) {
			item = new Item("Windscreen wipers 48/2182", new BigDecimal(10), 30, "Neki tekst koji opisuje zašto je ova guma za brisače odlična.",
					manufacturerService.getByName("Manufacturer 1"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Disc brakes 17812");
		if (item == null) {
			item = new Item("Disc brakes 17812", new BigDecimal(35), 24, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 2"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Coupler BSH-7182");
		if (item == null) {
			item = new Item("Coupler BSH-7182", new BigDecimal(25), 16, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 3"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Windscreen");
		if (item == null) {
			item = new Item("Windscreen", new BigDecimal(158), 3, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 4"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Motor oil KT9605");
		if (item == null) {
			item = new Item("Motor oil KT9605", new BigDecimal(18), 11, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 5"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Motor oil KT5605");
		if (item == null) {
			item = new Item("Motor oil KT5605", new BigDecimal(40), 26, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 2"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Carburetor nb57bf7854");
		if (item == null) {
			item = new Item("Carburetor nb57bf7854", new BigDecimal(84), 8, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 3"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Front shock absorber VW DERBY 64 1.9 SDI 47kw");
		if (item == null) {
			item = new Item("Front shock absorber VW DERBY 64 1.9 SDI 47kw", new BigDecimal(72), 7, "Opis proizvoda.",
					manufacturerService.getByName("Manufacturer 4"));
			itemService.create(item);
			item = null;
		}
		item = itemService.getByName("Gear shifter");
		if (item == null) {
			item = new Item("Gear shifter", new BigDecimal(69), 14, "Opis proizvoda.", manufacturerService.getByName("Manufacturer 5"));
			itemService.create(item);
			item = null;
		}
	}

	private void createRepairsIfNotFound() {
		Repair repair = repairService.getActiveByName("Break repair");
		if (repair == null) {
			repair = new Repair("Break repair", new BigDecimal(20), "Break repair service description.");
			repairService.create(repair);
			repair = null;
		}
		repair = repairService.getActiveByName("Tire balancing");
		if (repair == null) {
			repair = new Repair("Tire balancing", new BigDecimal(12), "Tire balancing service description.");
			repairService.create(repair);
			repair = null;
		}
		repair = repairService.getActiveByName("Oil change");
		if (repair == null) {
			repair = new Repair("Oil change", new BigDecimal(10), "Oil change service description.");
			repairService.create(repair);
			repair = null;
		}
		repair = repairService.getActiveByName("Exhaust repair");
		if (repair == null) {
			repair = new Repair("Exhaust repair", new BigDecimal(15), "Exhaust repair service description.");
			repairService.create(repair);
			repair = null;
		}
		repair = repairService.getActiveByName("Motor repair");
		if (repair == null) {
			repair = new Repair("Motor repair", new BigDecimal(23), "Motor repair service description.");
			repairService.create(repair);
			repair = null;
		}
	}
}
