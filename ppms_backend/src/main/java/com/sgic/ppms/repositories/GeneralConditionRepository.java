
package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.GeneralCondition;

public interface GeneralConditionRepository extends JpaRepository<GeneralCondition, Integer> {
	GeneralCondition findByAdmitId(Long admitId);
}
