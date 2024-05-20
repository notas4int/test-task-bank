package com.projects.notas4int.bankservce.repositories;

import com.projects.notas4int.bankservce.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join BankAccount bk\s
      on t.bankAccount.id = bk.id\s
      where bk.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByAccount(Long id);

    Optional<Token> findByToken(String token);
}
