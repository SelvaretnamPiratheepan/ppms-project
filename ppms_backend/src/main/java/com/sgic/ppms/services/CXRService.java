package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.CXRDto;
import com.sgic.ppms.entities.CXR;
import com.sgic.ppms.util.ResponseWrapper;

public interface CXRService {
	ResponseWrapper<CXR> create(CXRDto cxrDto);

	ResponseWrapper<CXR> getById(Integer id);

	ResponseWrapper<CXR> update(Integer id, Boolean result, CXRDto cxrDto);

	ResponseWrapper<CXR> delete(Integer id);

	ResponseWrapper<List<CXR>> getByAdmitId(Long admitId);
}