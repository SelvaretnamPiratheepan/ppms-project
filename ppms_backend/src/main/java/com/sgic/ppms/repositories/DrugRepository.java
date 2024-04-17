package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Drug;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Integer> {

	Optional<Drug> findById(long id);

	Drug findByName(String name);

}
