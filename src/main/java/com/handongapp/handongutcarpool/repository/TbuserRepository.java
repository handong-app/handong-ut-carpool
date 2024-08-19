package com.handongapp.handongutcarpool.repository;

import com.handongapp.handongutcarpool.domain.Tbuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbuserRepository extends JpaRepository<Tbuser, String> {

}
