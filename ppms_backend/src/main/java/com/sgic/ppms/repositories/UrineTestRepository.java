package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.UrineTest;

public interface UrineTestRepository extends JpaRepository<UrineTest, Long> {
	List<UrineTest> findByAdmitId(Long admitId);
}
