package com.PrimeFaces.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


public class SpringUser extends org.springframework.security.core.userdetails.User {

    public SpringUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static SpringUser of(User user) {
        return SpringUser.of(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public static SpringUser of(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        return new SpringUser(username, password, authorities);
    }

}
