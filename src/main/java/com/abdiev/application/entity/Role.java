package com.abdiev.application.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    ROLE_ADMIN(List.of("READ","MODIFY","WRITE")),
    ROLE_USER(List.of("READ")),
    ROLE_PRIME(List.of("READ","WRITE"));

    private final List<String> permissions;

    Role(List<String> permissions) {
        this.permissions = permissions;
    }

    private List<String> getPermissions() {
        return permissions;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> collect = getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        collect.add(new SimpleGrantedAuthority("ROLE_" +this.name()));
        return collect;
    }


}
