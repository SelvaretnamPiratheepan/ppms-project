package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.PresentingComplaint;

@Repository
public interface PresentingComplaintRepository extends JpaRepository<PresentingComplaint, Long> {
	List<PresentingComplaint> findByAdmitId(Long admitId);
}
