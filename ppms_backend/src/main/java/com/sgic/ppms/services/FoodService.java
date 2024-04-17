package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.FoodDto;
import com.sgic.ppms.entities.Food;

public interface FoodService {

	List<Food> getAllFoods();

	Food getById(long id);

	boolean existsByname(String name);

	Food createFood(FoodDto foodDto);

	boolean deleteById(Long id);
}
