package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgic.ppms.entities.InflammatoryMarkers;

public interface InflammatoryMarkersRepository extends JpaRepository<InflammatoryMarkers, Long> {
	@Query("SELECT h FROM InflammatoryMarkers h WHERE h.admit.Id = :admitId")
	List<InflammatoryMarkers> findByAdmitId(@Param("admitId") Long admitId);

	Boolean existsByAdmitId(Long admitId);
}
