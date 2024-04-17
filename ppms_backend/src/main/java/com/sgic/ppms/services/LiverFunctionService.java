package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.LiverFunctionDto;
import com.sgic.ppms.entities.LiverFunction;
import com.sgic.ppms.enums.RestApiResponseStatus;

public interface LiverFunctionService {
	LiverFunction createLiverFunction(LiverFunctionDto liverFunctionDto);

	LiverFunction getLiverFunctionById(Long id);

	List<LiverFunction> getAllLiverFunction();

	List<LiverFunction> getAllLiverFunctionByAdmitId(Long admitId);

	LiverFunction updateLiverFunction(Long id, LiverFunctionDto updatedLiverFunctionDto);

	RestApiResponseStatus deleteLiverFunction(long id);

	boolean admitExists(Long admitId);
}
