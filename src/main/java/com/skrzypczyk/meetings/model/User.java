package com.skrzypczyk.meetings.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Transient
    private String password;

    private String encodedPassword;

    @Column(unique = true)
    private String email;

    @Transient
    private String passwordConfirm;

    @Column
    private String activationToken;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "participants")
    private Set<Event> events = new HashSet<>();

    @Column(name = "IS_ENABLED", columnDefinition = "boolean default false", nullable = false)
    private Boolean enabled = false;

    @Column(name = "IS_NON_LOCKED", columnDefinition = "boolean default true", nullable = false)
    private Boolean accountNonLocked = true;

    @Column(name = "IS_NON_EXPIRED", columnDefinition = "boolean default true", nullable = false)
    private Boolean accountNonExpired = true;

    @Column(name = "IS_CREDENTIALS_NON_EXPIRED", columnDefinition = "boolean default true", nullable = false)
    private Boolean credentialsNonExpired = true;

    @ManyToMany
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, String encodedPassword, String email, Boolean enabled, Boolean accountNonLocked, Boolean accountNonExpired, Boolean credentialsNonExpired, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.encodedPassword = encodedPassword;
        this.email = email;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
