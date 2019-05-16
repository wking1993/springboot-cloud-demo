package com.kimile.web.fsh.substitution.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		System.out.println(template.url());
	}

}
