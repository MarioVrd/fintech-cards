/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.exceptions.AppException;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.JwtAuthenticationResponse;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.RegisterRequest;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Mario
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtTokenProvider tokenProvider;
    
    
    @GetMapping("/login")
    public String displayLogin(Model model) {
        
        model.addAttribute("login_req", new LoginRequest());
        
        return "login";
    }

    @GetMapping("/register")
    public String displayRegistration(Model model) {
        
        model.addAttribute("register_req", new RegisterRequest());
        
        return "register";
    }

    
    @PostMapping("/login")
    public /*ResponseEntity<?>*/String authenticateUser(@Valid /*@RequestBody*/ LoginRequest loginRequest, 
            HttpServletResponse response) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException("User not found!"));
        
        String jwt = tokenProvider.generateToken(authentication, user);
        
        Cookie tokenCookie = new Cookie("Token", jwt);
        tokenCookie.setPath("/");
        
        //response.addHeader("Authorization", "Bearer " + jwt);
        response.addCookie(tokenCookie);
        
        //return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        return "redirect:/cards";
    }
    
    
    @PostMapping("/register")
    public /*ResponseEntity<?>*/String registerUser(@Valid RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            //return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
            //        HttpStatus.BAD_REQUEST);
            return "redirect:/api/auth/register";
        }
        
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            //return new ResponseEntity(new ApiResponse(false, "Email is already taken!"),
            //        HttpStatus.BAD_REQUEST);
            return "redirect:/api/auth/register";
        }
        
        // Creating user account
        User user = new User(registerRequest.getUsername(), registerRequest.getName(), 
                registerRequest.getSurname(), registerRequest.getEmail(), registerRequest.getPassword());
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        
        user.setRoles(Collections.singleton(userRole));
        
        User result = userRepository.save(user);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        
        //return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
        return "redirect:/api/auth/login";
    }
    
}
