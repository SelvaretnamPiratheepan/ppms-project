package com.sgic.ppms.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseWrapper<T> {
	private int statusCode;
	private String statusMessage;
	private T data;
}