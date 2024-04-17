package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.Food_Allergy;

public interface Food_AllergyRepository extends JpaRepository<Food_Allergy, Integer> {

	Food_Allergy findByName(String name);

}