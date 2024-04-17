package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sgic.ppms.entities.RadiologyOther;

@EnableJpaRepositories
public interface RadiologyOtherRepository extends JpaRepository<RadiologyOther, Long> {

	List<RadiologyOther> findByAdmitId(long admitId);

	@Query("SELECT ot FROM RadiologyOther ot WHERE ot.otherTestM.id = :otherTestMId")
	List<RadiologyOther> findByOtherTestMId(long otherTestMId);

}
