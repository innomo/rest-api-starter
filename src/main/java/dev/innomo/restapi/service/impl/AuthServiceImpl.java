package dev.innomo.restapi.service.impl;

import dev.innomo.restapi.payload.LoginDto;
import dev.innomo.restapi.payload.RegisterDto;
import dev.innomo.restapi.repository.RoleRepository;
import dev.innomo.restapi.repository.UserRepository;
import dev.innomo.restapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String login(LoginDto loginRequest) {
        return "";
    }

    @Override
    public String logout() {
        return "";
    }

    @Override
    public String register(RegisterDto registerRequest) {
        return "";
    }
}
