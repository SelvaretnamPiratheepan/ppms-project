package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.RouteDto;
import com.sgic.ppms.entities.Route;
import com.sgic.ppms.repositories.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteRepository routeRepository;

	public List<Route> getAllRoutes() {
		return routeRepository.findAll();
	}

	public Route CreateRoute(RouteDto routeDto) {
		Route existingRoute = routeRepository.findByName(routeDto.getName());
		if (existingRoute != null) {
			return existingRoute;
		} else {
			Route route = new Route();
			BeanUtils.copyProperties(routeDto, route);
			return routeRepository.save(route);
		}
	}

	public boolean deleteRouteById(int id) {
		Optional<Route> optionalRoute = routeRepository.findById(id);
		if (optionalRoute.isPresent()) {
			routeRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Optional<Route> getRouteById(long id) {
		return routeRepository.findById(id);
	}

	public int getRouteIdByName(String route_name) {
		Route route = routeRepository.findByName(route_name);
		return (route != null) ? route.getId() : -1;
	}

}
