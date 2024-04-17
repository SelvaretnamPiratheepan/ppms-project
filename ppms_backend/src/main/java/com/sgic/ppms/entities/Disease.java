package com.sgic.ppms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disease {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long diseaseid;
	public String icdname;
	private String icd10;
	private int icdcode;

	@ManyToOne
	@JoinColumn(name = "icdCategoryid")
	private ICDCategory icdCategory;
}
