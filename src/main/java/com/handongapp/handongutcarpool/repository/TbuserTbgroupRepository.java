package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbuserTbgroupRepository extends JpaRepository<TbgroupTbuser, String> {
    Boolean existsByTbgroupIdAndTbuserId(String tbgroupId, String tbuserId);
    Optional<TbgroupTbuser> findByTbgroupIdAndTbuserId(String tbgroupId, String tbuserId);
}
