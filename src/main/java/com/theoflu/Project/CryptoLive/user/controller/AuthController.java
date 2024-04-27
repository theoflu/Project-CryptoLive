package com.theoflu.Project.CryptoLive.user.controller;

import com.theoflu.Project.CryptoLive.user.configs.JwtUtils;
import com.theoflu.Project.CryptoLive.user.entity.ERole;
import com.theoflu.Project.CryptoLive.user.entity.Role;
import com.theoflu.Project.CryptoLive.user.entity.UserDetailsImpl;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.repository.RoleRepository;
import com.theoflu.Project.CryptoLive.user.repository.UserRepository;
import com.theoflu.Project.CryptoLive.user.response.JwtResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;


    @PostMapping("/signin")
    public JwtResponse authenticateUser(@RequestBody UserEntity loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody UserEntity signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        Set<Role> roles = new HashSet<>();


        roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        // Create new user's account
        UserEntity user = UserEntity.builder().email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .streamkey("supersecret")
                .roles(roles)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}