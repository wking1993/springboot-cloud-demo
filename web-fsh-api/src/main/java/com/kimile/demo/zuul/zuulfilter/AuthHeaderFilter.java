package com.kimile.demo.zuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 在API网关将服务进行具体转发时，不能采用Feign拦截器的方式进行Token的传递
 * 在Zuul中，我们可以用pre过滤器来完成这个工作，在路由之前将Token信息添加到请求头中，然后将请求头转发到具体的服务上
 * 此部分代码，尚缺少获取Token的定时器，可以直接使用消费者部分代码，但是必须依赖申请Token的Feign客户端的定义，需完善
 */
public class AuthHeaderFilter extends ZuulFilter {
	
	public AuthHeaderFilter() {
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Object success = ctx.get("isSuccess");
		return success == null ? true : Boolean.parseBoolean(success.toString());
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.addZuulRequestHeader("Authorization", System.getProperty("web.auth.token"));
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 5;
	}
	
}
