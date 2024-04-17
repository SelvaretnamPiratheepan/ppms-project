package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.OtherTestM;

@Repository
public interface OtherTestMRepository extends JpaRepository<OtherTestM, Long> {

	boolean existsByTestname(String testname);
}
