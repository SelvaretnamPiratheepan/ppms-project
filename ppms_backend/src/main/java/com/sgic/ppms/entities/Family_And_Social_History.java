package com.sgic.ppms.entities;

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
@Table
public class Family_And_Social_History {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String FamType;
	private String FamilyHistoryOfIllness;
	private Boolean Consanguinity;
	private String House;
	private String H_Type;
	private String Roof;
	private String Floor;
	private String Toilet;
	private String WaterResource;
	private String Chlorination;
	private String NearestHospital;
	private Double Distance;
	private Integer NoOfChild;
	private String ModeOfTransport;

}
