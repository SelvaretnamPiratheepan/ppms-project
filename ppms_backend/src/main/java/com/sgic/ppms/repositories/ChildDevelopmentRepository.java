package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.ChildDevelopment;

@Repository
public interface ChildDevelopmentRepository extends JpaRepository<ChildDevelopment, Long> {
	List<ChildDevelopment> findByChildId(String childId);

	void deleteById(Long Id);

	void deleteByChildId(String childId);

	ChildDevelopment findByChildIdAndDevelopmentalStageId(String childId, Long developmentalStageId);

	boolean existsByChildIdAndDevelopmentalStageId(String childId, Long developmentalStageId);

	Boolean existsByChildId(String ChildId);
}
