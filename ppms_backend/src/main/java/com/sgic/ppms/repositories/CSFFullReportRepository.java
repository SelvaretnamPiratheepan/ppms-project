package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.CSFFullReport;

@Repository
public interface CSFFullReportRepository extends JpaRepository<CSFFullReport, Long> {

	List<CSFFullReport> findByAdmitId(long admitId);

}
