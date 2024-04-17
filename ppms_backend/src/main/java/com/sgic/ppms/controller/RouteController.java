package com.sgic.ppms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.RouteDto;
import com.sgic.ppms.entities.Route;
import com.sgic.ppms.services.RouteService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.ROUTE)
public class RouteController {

	@Autowired
	private RouteService routeService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Route>>> getAllRoutes() {
		List<Route> routes = routeService.getAllRoutes();
		ResponseWrapper<List<Route>> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
				ValidationMessages.GET_SUCCESS, routes);
		return ResponseEntity.ok(responseWrapper);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<Route>> CreateRoute(@RequestBody RouteDto routeDto) {
		String routeName = routeDto.getName();
		int existingRouteId = routeService.getRouteIdByName(routeName);
		if (existingRouteId != -1) {
			Route existingRoute = routeService.getRouteById(existingRouteId).orElse(null);
			ResponseWrapper<Route> responseWrapper = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(),
					ValidationMessages.NAME_EXISTS, existingRoute);
			return ResponseEntity.badRequest().body(responseWrapper);
		} else {
			Route route = routeService.CreateRoute(routeDto);
			ResponseWrapper<Route> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.RETRIEVED, route);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteDrug(@PathVariable("id") int id) {
		boolean deleteStatus = routeService.deleteRouteById(id);
		if (deleteStatus) {
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.DELETE_SUCCESS, null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<Route>> getRouteById(@PathVariable("id") long id) {
		Optional<Route> routeOptional = routeService.getRouteById(id);
		if (routeOptional.isPresent()) {
			Route route = routeOptional.get();
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.GET_SUCCESS, route));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

}