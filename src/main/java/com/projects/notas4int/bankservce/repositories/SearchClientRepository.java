package com.projects.notas4int.bankservce.repositories;

import com.projects.notas4int.bankservce.models.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SearchClientRepository extends JpaRepository<Client, Long> {
    Slice<Client> findByDateOfBirthdayGreaterThan(LocalDate localDate, Pageable pageable);
    Optional<Client> findClientByPhone(String phone);
    Optional<Client> findClientBySurnameAndNameAndMiddleName(String surname, String name, String middleName);
    Optional<Client> findClientByEmail(String email);
}
