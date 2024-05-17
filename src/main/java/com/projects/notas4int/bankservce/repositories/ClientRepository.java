package com.projects.notas4int.bankservce.repositories;

import com.projects.notas4int.bankservce.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
