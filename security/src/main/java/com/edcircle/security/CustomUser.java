package com.edcircle.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.edcircle.store.entities.User;

public class CustomUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 7473972170656778163L;

	private final User user;

	public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
