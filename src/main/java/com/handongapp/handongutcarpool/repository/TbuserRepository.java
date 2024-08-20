package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.Tbuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbuserRepository extends JpaRepository<Tbuser, String> {
    Optional<Tbuser> findByHakbun(String hakbun);
    Boolean existsByHakbun(String hakbun);
}
