package com.kimile.web.fsh.substitution.interceptor;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class MyLoadBalancerInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		final URI originalUri = request.getURI();
		String serviceName = originalUri.getHost();
		System.out.println("进入自定义的请求拦截器中" + serviceName);
		return execution.execute(request, body);
	}
	
}
