package com.kimile.demo.zuul.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kimile.demo.zuul.base.ResponseCode;
import com.kimile.demo.zuul.base.ResponseData;

/*
 * 与Spring Boot中统一进行异常处理的区别：
 * @ControllerAdvice注解主要是针对Controller中的方法做处理，作用于@RequestMapping标注的方法上，只对定义的接口异常有效，在Zuul中是无效的
 */
@RestController
public class ErrorHandlerController implements ErrorController {
	
	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public ResponseData error(HttpServletRequest request) {
		Map<String, Object> errorAttributes = getErrorAttributes(request);
		String message = (String) errorAttributes.get("message");
		String trace = (String) errorAttributes.get("trace");
		if (StringUtils.isNotBlank(trace)) {
			message += String.format(" and trace %s", trace);
		}
		System.out.println(message);
		return ResponseData.fail(message, ResponseCode.SERVER_ERROR_CODE.getCode());
	}
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getErrorAttributes(requestAttributes, true);
	}
	
}
