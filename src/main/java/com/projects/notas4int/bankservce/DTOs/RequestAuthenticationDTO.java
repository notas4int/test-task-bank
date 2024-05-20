package com.projects.notas4int.bankservce.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuthenticationDTO {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
