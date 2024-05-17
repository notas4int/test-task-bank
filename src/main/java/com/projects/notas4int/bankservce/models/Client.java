package com.projects.notas4int.bankservce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birthday")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirthday;

    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phone;

    // TODO: 17.05.2024 добавить аннотацию для проверки почты
    @Email
    @NotEmpty
    private String email;

    // TODO: 17.05.2024 добавить каскадирование
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private BankAccount bankAccount;
}
