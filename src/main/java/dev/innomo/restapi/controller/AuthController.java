package dev.innomo.restapi.controller;

import dev.innomo.restapi.model.Role;
import dev.innomo.restapi.model.User;
import dev.innomo.restapi.payload.JWTAuthResponse;
import dev.innomo.restapi.payload.LoginDto;
import dev.innomo.restapi.payload.RegisterDto;
import dev.innomo.restapi.repository.RoleRepository;
import dev.innomo.restapi.repository.UserRepository;
import dev.innomo.restapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final  AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(), loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Get token from tokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
//        return ResponseEntity.ok("Bearer " + authentication.getPrincipal());
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerRequest){
        //Check user from DB
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            return new ResponseEntity<>("Username already exist!", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return new ResponseEntity<>("Email already exist!", HttpStatus.BAD_REQUEST);
        }
        //Create New User
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getUsername());

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
