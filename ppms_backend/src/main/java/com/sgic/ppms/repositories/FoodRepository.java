package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
	Food findByName(String name);

	boolean existsByname(String name);
}
