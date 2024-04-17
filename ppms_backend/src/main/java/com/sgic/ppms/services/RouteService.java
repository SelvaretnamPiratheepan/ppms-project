package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.RouteDto;
import com.sgic.ppms.entities.Route;

public interface RouteService {
	Route CreateRoute(RouteDto routeDto);

	List<Route> getAllRoutes();

	boolean deleteRouteById(int id);

	Optional<Route> getRouteById(long id);

	int getRouteIdByName(String route_name);

}