package com.sgic.ppms.dto;

import com.sgic.ppms.enums.TimeUnit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryOfPresentingComplaintDto {

	private long AdmitId;
	private String historyOfComplaint;
	private long durationValue;
	private TimeUnit timeUnit;

}