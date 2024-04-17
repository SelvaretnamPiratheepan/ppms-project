package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sgic.ppms.entities.DietDetails;

@EnableJpaRepositories
public interface DietRepository extends JpaRepository<DietDetails, Long> {

	@Query("SELECT d FROM DietDetails d WHERE d.childDetail.childId = :childDetailId")
	List<DietDetails> findByChildDetailId(String childDetailId);

}
