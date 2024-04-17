package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.PostnatalHistoryDto;
import com.sgic.ppms.entities.PostnatalHistory;
import com.sgic.ppms.repositories.PostnatalHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostnatalHistoryServiceImpl implements PostnatalHistoryService {

	private final PostnatalHistoryRepository postnatalHistoryRepository;

	@Override
	public PostnatalHistory createPostnatalHistory(PostnatalHistoryDto postnatalHistoryDto) {
		PostnatalHistory postnatalHistory = new PostnatalHistory();
		BeanUtils.copyProperties(postnatalHistoryDto, postnatalHistory);
		return postnatalHistoryRepository.save(postnatalHistory);
	}

	@Override
	public List<PostnatalHistory> getAllPostnatalHistory() {
		return postnatalHistoryRepository.findAll();
	}

	@Override
	public PostnatalHistory getPostnatalHistoryById(Long id) {
		return postnatalHistoryRepository.findById(id).get();
	}

	@Override
	public PostnatalHistory updatePostnatalHistoryById(Long id, PostnatalHistoryDto postnatalHistoryDto) {
		PostnatalHistory existingPostnatalHistory = postnatalHistoryRepository.findById(id).get();
		if (existingPostnatalHistory != null) {
			BeanUtils.copyProperties(postnatalHistoryDto, existingPostnatalHistory);
			existingPostnatalHistory.setId(id);
			return postnatalHistoryRepository.save(existingPostnatalHistory);
		}
		return null;
	}

	@Override
	public boolean deletePostnatalHistoryById(Long id) {
		if (postnatalHistoryRepository.existsById(id)) {
			postnatalHistoryRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
