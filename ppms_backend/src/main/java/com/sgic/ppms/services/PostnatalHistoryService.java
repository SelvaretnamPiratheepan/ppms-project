package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.PostnatalHistoryDto;
import com.sgic.ppms.entities.PostnatalHistory;

public interface PostnatalHistoryService {

	PostnatalHistory createPostnatalHistory(PostnatalHistoryDto postnatalHistoryDto);

	List<PostnatalHistory> getAllPostnatalHistory();

	PostnatalHistory getPostnatalHistoryById(Long id);

	boolean deletePostnatalHistoryById(Long id);

	PostnatalHistory updatePostnatalHistoryById(Long id, PostnatalHistoryDto postnatalHistoryDto);
}
