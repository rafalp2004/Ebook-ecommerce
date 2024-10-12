package com.ebookeria.ecommerce.service.user_details;

import com.ebookeria.ecommerce.entity.Role;
import com.ebookeria.ecommerce.entity.User;
import com.ebookeria.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).
                orElseThrow(() ->
                        new UsernameNotFoundException("User with email: " + email + " not found"));


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapAuthorities(user.getRoles())
        );

    }

    private Collection<? extends GrantedAuthority> mapAuthorities(List<Role> roles) {
        return roles.stream()
                .map((r) -> new SimpleGrantedAuthority(r.getName()))
                .toList();
    }


}
