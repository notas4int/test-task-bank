package com.projects.notas4int.bankservce.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegisterDTO {
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotNull
    @Positive
    private double balance;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phone;

    @Email
    @NotEmpty
    private String email;
}
