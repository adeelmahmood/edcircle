package com.edcircle.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edcircle.store.entities.User;
import com.edcircle.store.entities.UserRole;
import com.edcircle.store.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService service;

	@Autowired
	public CustomUserDetailsService(UserService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = service.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("username " + username + " not found in system"));
		return new CustomUser(user, getAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Set<UserRole> roles) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
		}
		return authorities;
	}

}
