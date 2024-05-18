package com.projects.notas4int.bankservce.repositories;

import com.projects.notas4int.bankservce.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select COUNT(c) > 0 from Client c join BankAccount bk on c.id = bk.id " +
            "where c.email = ?1 or c.phone = ?2 or bk.login = ?3")
    boolean existsByPhoneOrEmailOrLogin(String email, String phone, String login);

    boolean existsClientByPhone(String phone);

    boolean existsClientByEmail(String email);
}
