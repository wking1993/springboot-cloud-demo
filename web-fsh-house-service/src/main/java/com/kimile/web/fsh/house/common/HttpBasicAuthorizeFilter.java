package com.kimile.web.fsh.house.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.kimile.web.fsh.house.base.ResponseCode;
import com.kimile.web.fsh.house.base.ResponseData;
import com.kimile.web.fsh.house.util.JWTUtils;
import com.kimile.web.fsh.house.util.JsonUtils;

/**
 * 服务提供方，provider，服务消费接口时，provider需要对其进行身份验证，验证通过才能消费接口
 * 调用之前写好的JwtUtils类来检查Token是否合法，合法则放行，不合法则进行拦截并给出友好提示；
 */
public class HttpBasicAuthorizeFilter implements Filter {

	JWTUtils jwtUtils = JWTUtils.getInstance();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		String auth = httpRequest.getHeader("Authorization");
		System.out.println("===========auth==============" + auth);
		//验证Token
		if (!StringUtils.hasText(auth)) {
			PrintWriter print = httpResponse.getWriter();
			print.write(JsonUtils.toJson(ResponseData.fail("非法请求[缺少Authorization信息]", ResponseCode.NO_AUTH_CODE.getCode())));
			return;
		}
		JWTUtils.JWTResult jwt = jwtUtils.checkToken(auth);
		if (!jwt.isStatus()) {
			PrintWriter print = httpResponse.getWriter();
			print.write(JsonUtils.toJson(ResponseData.fail(jwt.getMsg(), jwt.getCode())));
			return;
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void destroy() {
		
	}
	
}
