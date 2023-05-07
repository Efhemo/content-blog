package com.efhem.content.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    @Query("""
    SELECT t from TokenEntity t inner join User u on t.user.id = u.id
    WHERE u.id = :userId AND (t.isExpired = false or t.isRevoked = false )
    """)
    List<TokenEntity> findAllValidTokenByUser(Integer userId);

    Optional<TokenEntity> findByToken(String token);
}
