package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.USSAdboDto;
import com.sgic.ppms.entities.USSAdbo;

public interface USSAdboService {

	USSAdbo create(USSAdboDto ussAdboDto);

	List<USSAdbo> getByAdmitId(long admitId);

	Optional<USSAdbo> getById(Long id);

	USSAdbo update(USSAdboDto ussAdboDto);

	boolean delete(Long id);

	boolean admitExists(Long admitId);

	boolean ussAdboExist(Long id);

}
