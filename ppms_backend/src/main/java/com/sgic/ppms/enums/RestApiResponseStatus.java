package com.sgic.ppms.enums;

public enum RestApiResponseStatus {

	OK(20000, "OK"), CREATED(21000, "CREATED"), VALIDATION_FAILURE(40000, "VALIDATION_FAILURE"),
	UPDATED(22000, "UPDATED"), NOT_UPDATED(30400, "NOT UPDATED"), RETRIEVED(23000, "RETRIEVED"),
	FAILURE(44000, "FAILURE"), DELETED(20400, "DELETED"), NOT_DELETED(40000, "NOT DELETED"),
	NOT_FOUND(40400, "NOT FOUND"), FOUND(30200, "FOUND"), NOT_CREATED(42200, "NOT CREATED"),
	NAME_EXISTS(40900, "CONFLICT"), BAD_REQUEST(40000, "BAD REQUEST"),
	INTERNAL_SERVER_ERROR(50000, "INTERNAL_SERVER_ERROR");

	private String status;

	private Integer code;

	RestApiResponseStatus(Integer code, String status) {
		this.status = status;
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public Integer getCode() {
		return code;
	}

	@Override
	public String toString() {
		return status + ":" + code;
	}

}