package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.TbgroupTbuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbuserTbgroupRepository extends JpaRepository<TbgroupTbuser, String> {
    Boolean existsByTbgroupIdAndTbuserId(String tbgroupId, String tbuserId);
}
