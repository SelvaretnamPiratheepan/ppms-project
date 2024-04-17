package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.PostnatalHistory;

@Repository
public interface PostnatalHistoryRepository extends JpaRepository<PostnatalHistory, Long> {

}
