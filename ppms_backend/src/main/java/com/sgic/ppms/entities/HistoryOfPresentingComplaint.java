package com.sgic.ppms.entities;

import com.sgic.ppms.enums.TimeUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "historyOfPresentingComplaint")
public class HistoryOfPresentingComplaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "admitId")
	private Admit admit;

	private String historyOfComplaint;

	private long durationValue;

	@Enumerated(EnumType.STRING)
	private TimeUnit timeUnit;

}
