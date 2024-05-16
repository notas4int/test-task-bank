package com.projects.notas4int.bankservce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    @Positive
    private double balance;

    // TODO: 17.05.2024 добавить каскадирование
    @OneToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
