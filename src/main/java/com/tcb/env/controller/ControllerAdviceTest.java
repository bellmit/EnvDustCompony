package com.tcb.env.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

//@ControllerAdvice
public class ControllerAdviceTest {
	protected static Logger logger = Logger
			.getLogger(ControllerAdviceTest.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String processUnauthenticatedException(NativeWebRequest request,
			Exception e) {
		logger.error(e.getMessage());
		return "viewName"; // 返回一个逻辑视图名
	}

}
