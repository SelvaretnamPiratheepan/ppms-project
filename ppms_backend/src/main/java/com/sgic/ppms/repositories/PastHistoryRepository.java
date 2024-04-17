// PastHistoryRepository.java
package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.PastHistory;

@Repository
public interface PastHistoryRepository extends JpaRepository<PastHistory, Integer> {
	List<PastHistory> findByChildId(String childId);
}
