package com.example.demo.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRefreshTokenRequest {

    @NotBlank private String refreshToken;
}
