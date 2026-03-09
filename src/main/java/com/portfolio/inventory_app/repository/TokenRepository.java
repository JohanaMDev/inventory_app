package com.portfolio.inventory_app.repository;

import com.portfolio.inventory_app.model.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
    @Query("""
        select t from Token t inner join t.usuario u
            where u.id = :usuarioId and (t.expired = false or t.revoked = false)
    """)
    List<Token> findAllValidTokensByUser(Long usuarioId);

}
