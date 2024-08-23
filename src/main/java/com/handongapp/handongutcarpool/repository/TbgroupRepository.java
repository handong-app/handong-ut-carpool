package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.Tbgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbgroupRepository extends JpaRepository<Tbgroup, String> {
}
