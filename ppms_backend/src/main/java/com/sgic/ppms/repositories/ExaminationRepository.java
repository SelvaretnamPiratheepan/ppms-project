package com.sgic.ppms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

	List<Examination> findByChildId(String childId);

	Optional<Examination> findByAdmitId(Long admitId);

}
