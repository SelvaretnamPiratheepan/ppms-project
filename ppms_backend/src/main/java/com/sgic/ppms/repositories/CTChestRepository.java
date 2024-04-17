package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.CTChest;

public interface CTChestRepository extends JpaRepository<CTChest, Long> {

	List<CTChest> findByAdmitId(long admitId);

}
