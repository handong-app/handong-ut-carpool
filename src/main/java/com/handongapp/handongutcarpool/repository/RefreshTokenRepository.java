package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByContent(String Content);
    List<RefreshToken> findByTbuserId(String TbuserId);
}
