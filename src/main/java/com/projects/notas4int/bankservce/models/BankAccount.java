package com.projects.notas4int.bankservce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bank-account", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
    @Id
    private long id;

    @NotEmpty
    @Size(min = 4, max = 10)
    private String login;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotNull
    @Min(0)
    private double balance;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Client client;

    @OneToMany(mappedBy = "bankAccount")
    private List<Token> tokens;

    @Override
    public String toString() {
        return "PostalItem{" +
                "id=" + id +
                ", login=" + login +
                ", password=" + password +
                ", balance='" + balance +
                '}';
    }
}
