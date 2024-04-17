package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.CXRDto;
import com.sgic.ppms.entities.CXR;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.CXRRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@Service
public class CXRServiceImpl implements CXRService {

	private final CXRRepository cxrRepository;

	public CXRServiceImpl(CXRRepository cxrRepository) {
		this.cxrRepository = cxrRepository;
	}

	@Transactional
	public ResponseWrapper<CXR> create(CXRDto cxrDto) {
		if (cxrDto.getAdmitId() == null || !admitExists(cxrDto.getAdmitId())) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ADMITID,
					null);
		}
		if (!admitExists(cxrDto.getAdmitId())) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ADMITID,
					null);
		}

		CXR cxr = new CXR();
		cxr.setDate(cxrDto.getDate());
		cxr.setResult(cxrDto.getResult());
		cxr.setDetails(cxrDto.getDetails());
		cxr.setAdmitId(cxrDto.getAdmitId());

		CXR savedCxr = cxrRepository.save(cxr);
		if (savedCxr != null) {
			return new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS,
					savedCxr);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}
	}

	public boolean admitExists(Long admitId) {
		return cxrRepository.existsByAdmitId(admitId);
	}

	public ResponseWrapper<CXR> getById(Integer id) {
		Optional<CXR> cxrOptional = cxrRepository.findById(id);
		if (cxrOptional.isPresent()) {
			return new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), ValidationMessages.RETRIEVED,
					cxrOptional.get());
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}
	}

	public ResponseWrapper<List<CXR>> getByAdmitId(Long admitId) {
		List<CXR> cxrList = cxrRepository.findByAdmitId(admitId);
		if (!cxrList.isEmpty()) {
			return new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), ValidationMessages.RETRIEVED,
					cxrList);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}
	}

	@Transactional
	public ResponseWrapper<CXR> update(Integer id, Boolean result, CXRDto cxrDto) {
		Optional<CXR> optionalCXR = cxrRepository.findById(id);
		if (optionalCXR.isPresent()) {
			CXR existingCXR = optionalCXR.get();
			existingCXR.setDate(cxrDto.getDate());
			existingCXR.setResult(result);
			existingCXR.setDetails(cxrDto.getDetails());
			CXR updatedCxr = cxrRepository.save(existingCXR);
			if (updatedCxr != null) {
				return new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
						updatedCxr);
			} else {
				return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						RestApiResponseStatus.NOT_FOUND.getStatus(), null);
			}
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}
	}

	@Transactional
	public ResponseWrapper<CXR> delete(Integer id) {
		Optional<CXR> cxrOptional = cxrRepository.findById(id);

		if (cxrOptional.isPresent()) {
			CXR deletedCxr = cxrOptional.get();
			String statusMessage = ValidationMessages.DELETE_SUCCESS;

			cxrRepository.deleteById(id);
			return new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(), statusMessage, deletedCxr);
		} else {
			String statusMessage = RestApiResponseStatus.NOT_FOUND.getStatus();
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), statusMessage, null);
		}
	}
}
