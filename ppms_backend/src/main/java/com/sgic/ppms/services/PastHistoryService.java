package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.PastHistoryDto;
import com.sgic.ppms.entities.PastHistory;

public interface PastHistoryService {
	PastHistory createPastHistory(PastHistoryDto pastHistoryDto);

	List<PastHistory> getPastHistoryByChildId(String childId);

	PastHistory updatePastHistory(int Id, PastHistoryDto pastHistoryDto);

	boolean deletePastHistory(String childId);
}
