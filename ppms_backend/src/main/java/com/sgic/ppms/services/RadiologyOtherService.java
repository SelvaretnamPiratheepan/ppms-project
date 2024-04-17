package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.RadiologyOtherDto;
import com.sgic.ppms.entities.RadiologyOther;

public interface RadiologyOtherService {

	List<RadiologyOther> getByAdmitId(Long admitId);

	List<RadiologyOther> getByTestId(Long testId);

	Optional<RadiologyOther> getById(Long id);

	RadiologyOther create(RadiologyOtherDto otherDto);

	RadiologyOther update(RadiologyOtherDto otherDto);

	boolean delete(Long id);

	boolean admitExists(Long admitId);

	boolean testExists(Long testId);

	boolean radiologyOtherExist(Long id);

}
