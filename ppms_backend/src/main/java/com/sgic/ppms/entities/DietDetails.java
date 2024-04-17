package com.sgic.ppms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DietDetails")
public class DietDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "childIdd", insertable = false, updatable = false)
	private String childId;
	@ManyToOne
	@JoinColumn(name = "childId")
	private ChildDetail childDetail;
	@ManyToOne
	@JoinColumn(name = "foodList_id")
	private Food food;
	private int quantity_for_week;

}
