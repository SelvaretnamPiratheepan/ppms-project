package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.BirthHistory;

@Repository
public interface BirthHistoryRepository extends JpaRepository<BirthHistory, Long> {

	BirthHistory findByChildId(String childId);
}
