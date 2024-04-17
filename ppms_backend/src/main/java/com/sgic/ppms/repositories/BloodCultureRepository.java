package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.BloodCultureDetailEntity;

@Repository
public interface BloodCultureRepository extends JpaRepository<BloodCultureDetailEntity, Long> {
	@Query("SELECT d FROM DietDetails d WHERE d.childDetail.childId = :childDetailId")
	List<BloodCultureDetailEntity> findByChildDetailId(String childDetailId);
}
