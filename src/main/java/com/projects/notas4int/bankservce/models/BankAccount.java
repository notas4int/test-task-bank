package com.projects.notas4int.bankservce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank-account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    private long id;

    @NotEmpty
    private String login;

    // TODO: 17.05.2024 добавить проверку на длину и наличие символов
    @NotEmpty
    private String password;

//    @NotEmpty
    @Positive
    private double balance;

    // TODO: 17.05.2024 добавить каскадирование
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Client client;
}
