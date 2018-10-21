package tvz.naprednaJava.rozi.AutoServis.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tvz.naprednaJava.rozi.AutoServis.AuthUser;
import tvz.naprednaJava.rozi.AutoServis.enums.UserStatus;
import tvz.naprednaJava.rozi.AutoServis.model.Permission;
import tvz.naprednaJava.rozi.AutoServis.model.Role;
import tvz.naprednaJava.rozi.AutoServis.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("User with username %s not found.", username));
		}

		boolean enabled = user.getStatus() == UserStatus.ACTIVE;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Set<GrantedAuthority> authorities = getGrantedAuthorities(user);

		return new AuthUser(user.getId(), username, user.getPassword(), user.getFirstName(), user.getLastName(),
				user.getEmail(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private Set<GrantedAuthority> getGrantedAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
		for (Permission permission : user.getRole().getPermissions()) {
			authorities.add(new SimpleGrantedAuthority(permission.getName()));
		}
		return authorities;
	}
}
