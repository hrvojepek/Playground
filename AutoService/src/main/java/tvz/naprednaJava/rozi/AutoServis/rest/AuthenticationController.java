package tvz.naprednaJava.rozi.AutoServis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;
import tvz.naprednaJava.rozi.AutoServis.model.User;
import tvz.naprednaJava.rozi.AutoServis.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Hrvoje on 18.06.2017..
 */

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final LogoutHandler logoutHandler;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, LogoutHandler logoutHandler,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.logoutHandler = logoutHandler;
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login(@RequestBody User user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    @GetMapping("/getUser")
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            return userService.getByUsername(authentication.getName());
        } else {
            return null;
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
    }

}

