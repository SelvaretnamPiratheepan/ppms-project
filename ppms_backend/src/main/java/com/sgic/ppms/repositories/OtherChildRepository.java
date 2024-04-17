package com.sgic.ppms.repositories;

import com.sgic.ppms.entities.OtherChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OtherChildRepository extends JpaRepository<OtherChild,Long> {

    OtherChild save(OtherChild otherChild);

}
