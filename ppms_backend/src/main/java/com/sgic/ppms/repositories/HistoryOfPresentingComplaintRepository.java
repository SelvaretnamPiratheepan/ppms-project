package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.HistoryOfPresentingComplaint;

public interface HistoryOfPresentingComplaintRepository extends JpaRepository<HistoryOfPresentingComplaint, Long> {
	List<HistoryOfPresentingComplaint> findByAdmitId(Long admitId);
}
