package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.CTChestDto;
import com.sgic.ppms.entities.CTChest;

public interface CTChestService {

	CTChest create(CTChestDto ctChestDto);

	List<CTChest> getByAdmitId(long admitId);

	Optional<CTChest> getById(Long id);

	CTChest update(CTChestDto ctChestDto);

	boolean delete(Long id);

	boolean admitExists(Long admitId);

	boolean ctChestExist(Long id);

}
