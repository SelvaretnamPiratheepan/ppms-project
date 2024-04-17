package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.LiverFunction;

public interface LiverFunctionRepository extends JpaRepository<LiverFunction, Long> {

	List<LiverFunction> findByAdmitId(Long admitId);
}
