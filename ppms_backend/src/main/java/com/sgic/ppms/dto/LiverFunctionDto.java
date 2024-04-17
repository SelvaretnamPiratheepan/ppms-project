package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiverFunctionDto {
	private Long id;
	private Long admitId;
	private LocalDate date;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float sgpt;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float sgot;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float s_chole;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float prot;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float alb;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float glob;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float sbr;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float alp;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Float rbs;

	// public LiverFunctionDto(){

}

//    public LiverFunctionDto(Long id, Long admitId, LocalDate date, Float sgpt, Float sgot, Float s_chole, Float prot, Float alb, Float glob, Float sbr, Float alp, Float rbs) {
//        this.id = id;
//        this.admitId = admitId;
//        this.date = date;
//        this.sgpt = sgpt;
//        this.sgot = sgot;
//        this.s_chole = s_chole;
//        this.prot = prot;
//        this.alb = alb;
//        this.glob = glob;
//        this.sbr = sbr;
//        this.alp = alp;
//        this.rbs = rbs;
//    }
//}
