package dev.innomo.restapi.payload;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private String username;
}
