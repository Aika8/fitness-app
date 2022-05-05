package spring.first.fitness.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.first.fitness.entity.AuthProvider;
import spring.first.fitness.entity.Role;
import spring.first.fitness.entity.Users;
import spring.first.fitness.exceptions.BadRequestException;
import spring.first.fitness.payload.ApiResponse;
import spring.first.fitness.payload.AuthResponse;
import spring.first.fitness.payload.LoginRequest;
import spring.first.fitness.payload.SignUpRequest;
import spring.first.fitness.repos.RoleRepository;
import spring.first.fitness.repos.UserRepository;
import spring.first.fitness.security.oauth2.TokenProvider;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        Users user = new Users();
        Role role = roleRepository.findByName("ROLE_USER");

        if (role == null) {
            role = Role.builder()
                    .description("registered user")
                    .name("ROLE_USER")
                    .title("registered user")
                    .weight(2)
                    .build();
        }

        Role newRole = roleRepository.save(role);
        user.setRole(newRole);
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());

        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

}
