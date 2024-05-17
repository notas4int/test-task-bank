package com.projects.notas4int.bankservce.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RequestClientDTO {
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    private double balance;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phoneNumber;

    @Email
    private String email;
}
