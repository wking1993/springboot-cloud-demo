package com.kimile.web.fsh.substitution.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

	public FeignBasicAuthRequestInterceptor() {
	}
	
	@Override
	public void apply(RequestTemplate template) {
		template.header("Authorization", System.getProperty("web.auth.token"));
	}

}
