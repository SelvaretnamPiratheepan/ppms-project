package com.sgic.ppms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * contains custom error messages
 *
 */

@Component
@PropertySource("classpath:ErrorMessages.properties")
public class ErrorCodes {

	@Value("${validation.employee.notExists}")
	private String employeeNotExist;

	@Value("${validation.email.alreadyExist}")
	private String emailAlreadyExist;

}
