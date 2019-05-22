package com.kimile.demo.zuul.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.kimile.demo.zuul.base.ResponseCode;
import com.kimile.demo.zuul.base.ResponseData;
import com.kimile.demo.zuul.util.JsonUtils;
import com.netflix.zuul.context.RequestContext;

@Component
public class ServiceConsumerFallbackProvider implements ZuulFallbackProvider {

	private Logger log = LoggerFactory.getLogger(ServiceConsumerFallbackProvider.class);
	
	/*
	 * 此方法返回*表示对所有服务进行回退操作，如果只想对某个服务进行回退，那就返回需要回退的服务名称，这个名称一定要是注册到Eureka中的名称
	 */
	@Override
	public String getRoute() {
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
				headers.setContentType(mt);
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				RequestContext ctx = RequestContext.getCurrentContext();
				Throwable throwable = ctx.getThrowable();
				if (throwable != null) {
					log.error("", throwable.getCause());
				}
				ResponseData data = ResponseData.fail("服务器内部错误", ResponseCode.SERVER_ERROR_CODE.getCode());
				return new ByteArrayInputStream(JsonUtils.toJson(data).getBytes());
			}
			
			@Override
			public String getStatusText() throws IOException {
				return this.getStatusCode().getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return this.getStatusCode().value();
			}
			
			@Override
			public void close() {
				
			}
		};
	}
	
	
	
}
