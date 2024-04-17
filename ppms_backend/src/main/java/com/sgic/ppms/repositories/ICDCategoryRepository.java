package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.ICDCategory;

@Repository
public interface ICDCategoryRepository extends JpaRepository<ICDCategory, Long> {
	ICDCategory findByName(String name);

	boolean existsByName(String name);
}
