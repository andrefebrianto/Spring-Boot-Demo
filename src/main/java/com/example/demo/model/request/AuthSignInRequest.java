package com.example.demo.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSignInRequest {

    @Email @NotBlank private String email;

    @NotBlank private String password;
}
