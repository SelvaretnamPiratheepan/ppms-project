package com.sgic.ppms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parent_details")
public class ParentDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "childDetail_id")
	private ChildDetail childDetail;
	private String name;
	private String ContactNumber;
	private String Relationship;
	private Integer Age;
	private String Habits;
	private Boolean Illness;
	private String DetailsOfIllness;
	private String Occupation;
	private Long Salary;
}
