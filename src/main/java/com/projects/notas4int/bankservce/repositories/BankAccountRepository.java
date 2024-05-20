package com.projects.notas4int.bankservce.repositories;

import com.projects.notas4int.bankservce.models.BankAccount;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findBankAccountByLogin(String login);

    @Modifying
    @Query("update BankAccount ba set ba.balance =:balance where ba.login =:login")
    void updateAccountBalanceByLogin(@Param("balance") double balance, @Param("login") String login);
}
