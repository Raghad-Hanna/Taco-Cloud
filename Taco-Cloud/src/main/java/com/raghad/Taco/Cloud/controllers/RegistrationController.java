package com.raghad.Taco.Cloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import com.raghad.Taco.Cloud.repositories.JPAUserRepository;
import com.raghad.Taco.Cloud.DTOs.RegistrationForm;

@Controller
@RequestMapping(path = "/")
public class RegistrationController {
    private JPAUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(JPAUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegistrationForm() {
        return "registration_form";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        this.userRepository.save(form.toUser(this.passwordEncoder));
        return "redirect:/login";
    }
}
