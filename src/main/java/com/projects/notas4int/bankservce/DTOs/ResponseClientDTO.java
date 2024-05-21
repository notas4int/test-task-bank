package com.projects.notas4int.bankservce.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseClientDTO {
    private long id;

    private String name;

    private String surname;

    private String middleName;

    private LocalDate dateOfBirthday;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phone;

    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$")
    private String email;

}
