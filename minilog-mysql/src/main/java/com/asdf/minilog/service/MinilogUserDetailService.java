package com.asdf.minilog.service;

import com.asdf.minilog.entity.User;
import com.asdf.minilog.exception.UserNotFoundException;
import com.asdf.minilog.repository.UserRepository;
import com.asdf.minilog.security.MinilogGrantedAuthority;
import com.asdf.minilog.security.MinilogUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class MinillogUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MinillogUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with user name : " + username)));

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(MinilogGrantedAuthority::new)
                .toList();

        return new MinilogUserDetails(user.getId(), username, user.getPassword(), authorities);
    }
}
