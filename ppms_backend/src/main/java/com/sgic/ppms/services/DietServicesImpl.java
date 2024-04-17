package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DietDto;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.entities.DietDetails;
import com.sgic.ppms.entities.Food;
import com.sgic.ppms.repositories.ChildRepository;
import com.sgic.ppms.repositories.DietRepository;
import com.sgic.ppms.repositories.FoodRepository;

import jakarta.transaction.Transactional;

@Service
public class DietServicesImpl implements DietServices {
	@Autowired
	private DietRepository dietRepository;
	@Autowired
	private ChildRepository childRepository;
	@Autowired
	private FoodRepository foodRepository;

	public List<DietDetails> getAllDiets() {
		return dietRepository.findAll();

	}

	public Optional<DietDetails> getById(long id) {
		return dietRepository.findById(id);

	}

	public void delete(long id) {
		dietRepository.findById(id);
	}

	public boolean childExists(String childId) {
		return childRepository.existsByChildId(childId);
	}

	public boolean foodExists(Long foodListId) {
		return foodRepository.existsById(foodListId);
	}

	@Transactional
	public DietDetails create(DietDto dietDto) {

		DietDetails dietDetails = new DietDetails();
		ChildDetail childDetail = childRepository.findById(dietDto.getChildDetailId()).orElseThrow(
				() -> new IllegalArgumentException("Child not found with id:" + dietDto.getChildDetailId()));

		dietDetails.setChildDetail(childDetail);

		Food food = foodRepository.findById(dietDto.getFoodListId())
				.orElseThrow(() -> new IllegalArgumentException("Food not found with id:" + dietDto.getFoodListId()));

		dietDetails.setFood(food);
		dietDto.setId(0L);
		BeanUtils.copyProperties(dietDto, dietDetails);
		return dietRepository.save(dietDetails);

	}

	public boolean dietExist(Long id) {
		return dietRepository.existsById(id);

	}

	@Transactional
	public DietDetails update(DietDto dietDto) {

		DietDetails dietDetails = new DietDetails();
		ChildDetail childDetail = childRepository.findById(dietDto.getChildDetailId()).orElseThrow(
				() -> new IllegalArgumentException("Child not found with id:" + dietDto.getChildDetailId()));

		dietDetails.setChildDetail(childDetail);

		Food food = foodRepository.findById(dietDto.getFoodListId())
				.orElseThrow(() -> new IllegalArgumentException("Food not found with id:" + dietDto.getFoodListId()));

		dietDetails.setFood(food);
		BeanUtils.copyProperties(dietDto, dietDetails);
		return dietRepository.save(dietDetails);

	}

	public List<DietDetails> getByChildDetailId(String childDetailId) {
		return dietRepository.findByChildDetailId(childDetailId);
	}

}
