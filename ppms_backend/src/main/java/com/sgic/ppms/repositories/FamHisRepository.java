package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Family_And_Social_History;

@Repository
public interface FamHisRepository extends JpaRepository<Family_And_Social_History, Integer> {

}
