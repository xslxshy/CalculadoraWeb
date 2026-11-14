package br.edu.calc.plus.CalculatorPlus.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String nome;

    public CustomUserDetails(String username, String nome) {
        this.username = username;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override
    public String getPassword() { return ""; }
    @Override
    public String getUsername() { return username; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
