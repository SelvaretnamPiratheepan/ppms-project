package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExaminationDto {
	private Long id;
	private String childId;
	private Long admitId;

	@NotNull(message = ValidationMessages.HEIGHT_NOTNULL)
	@Positive(message = "Height" + ValidationMessages.POSITIVE)
	private double Height;

	@NotNull(message = ValidationMessages.WEIGHT_NOTNULL)
	@Positive(message = "Weight" + ValidationMessages.POSITIVE)
	private double Weight;
	private String facialAppearance;
	private Boolean febrile;
	private String dehydrated;
	private String clubbing;
	private String oedema;
	private String lymphNodes;
	private String cyanosis;
	private String pallor;
	private String jaundice;
	private Integer pulse;
	private Integer bp;
	private String rhythm;
	private Boolean murmur;
	private String site;
	private String type;
	private Integer rate;
	private String airEntry;
	private String auscultationFindings;
	private String ent;
	private String palpation;
	private Boolean liver;
	private Boolean spleen;
	private Boolean freeFluid;
	private String consciousLevel;
	private String cranialNerves;
	private String upperLimbs;
	private String lowerLimbs;
	private String cerebellarFunction;
	private String fundi;
	private String other;
}