package tvz.naprednaJava.rozi.AutoServis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import tvz.naprednaJava.rozi.AutoServis.service.UserService;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

	@Autowired
	private UserService userService;

	@Override
	public Long getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		return 1L;
//		return userService.getByUsername(authentication.getName()).getId();
	}

}
