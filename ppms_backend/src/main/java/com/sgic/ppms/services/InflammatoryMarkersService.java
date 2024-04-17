package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.InflammatoryMarkersDto;

public interface InflammatoryMarkersService {
	InflammatoryMarkersDto createInflammatoryMarkers(InflammatoryMarkersDto inflammatoryMarkersDto);

	InflammatoryMarkersDto updateInflammatoryMarkers(Long id, InflammatoryMarkersDto inflammatoryMarkersDto);

	List<InflammatoryMarkersDto> getInflammatoryMarkersByAdmitId(Long admitId);

	InflammatoryMarkersDto getInflammatoryMarkersById(Long Id);

	boolean admitExists(Long admitId);

	boolean inflammatoryFound(Long Id);

	void deleteInflammatoryMarkersById(Long id);
}
