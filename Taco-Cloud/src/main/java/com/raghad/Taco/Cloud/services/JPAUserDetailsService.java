package com.raghad.Taco.Cloud.services;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import com.raghad.Taco.Cloud.repositories.JPAUserRepository;
import com.raghad.Taco.Cloud.models.User;

@Service
public class JPAUserDetailsService implements UserDetailsService {
    private JPAUserRepository userRepository;

    @Autowired
    public JPAUserDetailsService(JPAUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByUsername(username);
        if(user.isPresent())
            return user.get();
        throw new UsernameNotFoundException("username not found");
    }

}
