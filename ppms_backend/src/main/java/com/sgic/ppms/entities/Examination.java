package com.sgic.ppms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "examination")
public class Examination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String childId;

	@Column(unique = true)
	private Long admitId;

	private Long anthropometryId;
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
