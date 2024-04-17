package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.ParentDetailEntity;

@Repository
public interface ParentDetailRepository extends JpaRepository<ParentDetailEntity, Long> {

	List<ParentDetailEntity> findByChildDetailChildId(String childId);
}
