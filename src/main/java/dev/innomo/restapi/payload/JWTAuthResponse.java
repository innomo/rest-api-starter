package dev.innomo.restapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTAuthResponse {
    private String token;
    private String tokenType = "Bearer";

    public JWTAuthResponse(String token) {
        this.token = token;
    }
}
