package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Dose;

@Repository
public interface DoseRepository extends JpaRepository<Dose, Integer> {

	Dose findByName(String name);

	Optional<Dose> findById(long id);

}
