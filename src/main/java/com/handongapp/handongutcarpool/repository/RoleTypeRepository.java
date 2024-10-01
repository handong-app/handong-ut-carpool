package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleTypeRepository extends JpaRepository<RoleType, String>{
	RoleType findByTypeName(String typeName);
}
