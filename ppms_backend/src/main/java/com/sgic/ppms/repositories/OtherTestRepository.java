package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.OtherTest;

@Repository
public interface OtherTestRepository extends JpaRepository<OtherTest, Long> {

	List<OtherTest> findByAdmitId(Long admitId);

	@Query("SELECT ot FROM OtherTest ot WHERE ot.otherTestM.id = :othertestmId")
	List<OtherTest> findByOtherTestMId(Long othertestmId);

}
