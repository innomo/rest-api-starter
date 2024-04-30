package dev.innomo.restapi.service;

import dev.innomo.restapi.payload.LoginDto;
import dev.innomo.restapi.payload.RegisterDto;

public interface AuthService {
    public String login(LoginDto loginRequest);
    public String logout();
    public String register(RegisterDto registerRequest);
}
