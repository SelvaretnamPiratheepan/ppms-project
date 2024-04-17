package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.LiverFunctionDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.LiverFunction;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.LiverFunctionRepository;

@Service
public class LiverFunctionImpl implements LiverFunctionService {
	@Autowired
	private LiverFunctionRepository liverFunctionRepository;
	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public LiverFunction createLiverFunction(LiverFunctionDto liverFunctionDto) {
		LiverFunction liverFunction = new LiverFunction();
		BeanUtils.copyProperties(liverFunctionDto, liverFunction);
		Admit admit = admitRepository.findById(liverFunctionDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException());
		liverFunction.setAdmit(admit);
		return liverFunctionRepository.save(liverFunction);
	}

	public LiverFunction getLiverFunctionById(Long id) {
		Optional<LiverFunction> optionalLiverFunction = liverFunctionRepository.findById(id);
		return optionalLiverFunction.orElse(null);
	}

	public List<LiverFunction> getAllLiverFunction() {
		return liverFunctionRepository.findAll();
	}

	public List<LiverFunction> getAllLiverFunctionByAdmitId(Long admitId) {

		List<LiverFunction> entities = liverFunctionRepository.findByAdmitId(admitId);
		return entities;
	}

	@Transactional
	public LiverFunction updateLiverFunction(Long id, LiverFunctionDto updatedLiverFunctionDto) {
		Optional<LiverFunction> optionalLiverFunction = liverFunctionRepository.findById(id);

		if (optionalLiverFunction.isPresent()) {
			LiverFunction liverFunction = optionalLiverFunction.get();
			updateLiverFunctionDetails(liverFunction, updatedLiverFunctionDto);
			return liverFunctionRepository.save(liverFunction);
		} else {
			return null;
		}
	}

	private void updateLiverFunctionDetails(LiverFunction liverFunction, LiverFunctionDto updatedLiverFunctionDto) {
		if (updatedLiverFunctionDto.getAdmitId() != null) {
			Admit admit = admitRepository.findById(updatedLiverFunctionDto.getAdmitId())
					.orElseThrow(() -> new IllegalArgumentException());
			liverFunction.setAdmit(admit);
		}
		if (updatedLiverFunctionDto.getDate() != null) {
			liverFunction.setDate(updatedLiverFunctionDto.getDate());
		}
		if (updatedLiverFunctionDto.getSgpt() != null) {
			liverFunction.setSgpt(updatedLiverFunctionDto.getSgpt());
		}
		if (updatedLiverFunctionDto.getSgot() != null) {
			liverFunction.setSgot(updatedLiverFunctionDto.getSgot());
		}
		if (updatedLiverFunctionDto.getS_chole() != null) {
			liverFunction.setS_chole(updatedLiverFunctionDto.getS_chole());
		}
		if (updatedLiverFunctionDto.getProt() != null) {
			liverFunction.setProt(updatedLiverFunctionDto.getProt());
		}
		if (updatedLiverFunctionDto.getAlb() != null) {
			liverFunction.setAlb(updatedLiverFunctionDto.getAlb());
		}
		if (updatedLiverFunctionDto.getGlob() != null) {
			liverFunction.setGlob(updatedLiverFunctionDto.getGlob());
		}
		if (updatedLiverFunctionDto.getSbr() != null) {
			liverFunction.setSbr(updatedLiverFunctionDto.getSbr());
		}
		if (updatedLiverFunctionDto.getAlp() != null) {
			liverFunction.setAlp(updatedLiverFunctionDto.getAlp());
		}
		if (updatedLiverFunctionDto.getRbs() != null) {
			liverFunction.setRbs(updatedLiverFunctionDto.getRbs());
		}

	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	public RestApiResponseStatus deleteLiverFunction(long id) {
		Optional<LiverFunction> optionalLiverFunction = liverFunctionRepository.findById(id);

		if (optionalLiverFunction.isPresent()) {
			liverFunctionRepository.deleteById(id);
			return RestApiResponseStatus.DELETED;
		} else {
			return RestApiResponseStatus.NOT_FOUND;
		}
	}
}
