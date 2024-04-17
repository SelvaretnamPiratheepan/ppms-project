package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DietDto;
import com.sgic.ppms.entities.DietDetails;

@Service
public interface DietServices {

	List<DietDetails> getAllDiets();

	Optional<DietDetails> getById(long id);

	void delete(long id);

	boolean childExists(String childId);

	boolean foodExists(Long foodListId);

	DietDetails create(DietDto dietDto);

	boolean dietExist(Long id);

	DietDetails update(DietDto dietDto);

	List<DietDetails> getByChildDetailId(String childId);

}
