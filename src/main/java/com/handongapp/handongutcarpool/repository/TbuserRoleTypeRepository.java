package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.TbuserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbuserRoleTypeRepository extends JpaRepository<TbuserRoleType, String> {
    List<TbuserRoleType> findByTbuserId(String tbuserId);
}