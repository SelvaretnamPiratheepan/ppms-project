package com.sgic.ppms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildDevelopmentDTO {
	private String childId;
	private Long developmentalStageId;
	private Boolean status;
}
