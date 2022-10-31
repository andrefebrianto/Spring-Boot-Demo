package com.example.demo.repository;

import com.example.demo.model.entity.RefreshToken;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends _BaseEntityRepository<RefreshToken, UUID> {

    List<RefreshToken> findAllByUserId(Long userId);

    Optional<RefreshToken> findByToken(String token);
}
