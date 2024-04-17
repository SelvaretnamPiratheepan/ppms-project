package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.FoodDto;
import com.sgic.ppms.entities.Food;
import com.sgic.ppms.repositories.FoodRepository;

import jakarta.transaction.Transactional;

@Service
public class FoodServiceImpl implements FoodService {
	@Autowired
	private FoodRepository foodRepository;

	public FoodServiceImpl(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	public List<Food> getAllFoods() {
		return foodRepository.findAll();
	}

	@Transactional
	public Food createFood(FoodDto foodDto) {
		Food food = new Food();
		BeanUtils.copyProperties(foodDto, food);
		return foodRepository.save(food);
	}

	public boolean deleteById(Long id) {
		Optional<Food> foodOptional = foodRepository.findById(id);
		if (foodOptional.isPresent()) {
			foodRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Food getById(long id) {
		return foodRepository.findById(id).orElse(null);

	}

	public boolean existsByname(String name) {
		return foodRepository.existsByname(name);
	}
}
