package com.kimile.demo.zuul.zuulfilter;

import org.apache.commons.lang.StringUtils;

import com.kimile.demo.zuul.base.ResponseCode;
import com.kimile.demo.zuul.base.ResponseData;
import com.kimile.demo.zuul.conf.BasicConf;
import com.kimile.demo.zuul.util.IpUtils;
import com.kimile.demo.zuul.util.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class IpFilter extends ZuulFilter {
	
	private BasicConf basicConf;
	
	public IpFilter() {
		super();
	}
	
	/*
	 * 是否执行该过滤器，true为执行，false为不执行，这个也可以利用配置中心来实现，达到动态的开启和关闭过滤器
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Object success = ctx.get("isSuccess");
		return success == null ? true : Boolean.parseBoolean(success.toString());
	}
	
	/*
	 * 过滤器类型，pre、route、post、error
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/*
	 * 过滤器的执行顺序，数值越小，优先级越高
	 */
	@Override
	public int filterOrder() {
		return 1;
	}
	
	/*
	 * 执行自己的业务逻辑
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String ip = IpUtils.getIpAddr(ctx.getRequest());
		
		//模拟错误
		System.out.println(2/0);
		
		//在黑名单中禁用
		if (StringUtils.isNotBlank(ip) && basicConf != null && basicConf.getIpStr().contains(ip)) {
			//以下代码，实现Zuul过滤器中对请求进行拦截，表示Zuul不需要将当前请求转发到后端的服务，但是并不阻止后边的过滤器的执行
			ctx.setSendZuulResponse(false);
			ResponseData data = ResponseData.fail("非法请求", ResponseCode.NO_AUTH_CODE.getCode());
			ctx.setResponseBody(JsonUtils.toJson(data));
			ctx.getResponse().setContentType("application/json; charset=utf-8");
			//改造拦截代码，增加是否成功标识的传递，让后续的过滤器用这个值来决定自己是否需要执行，改造shouldFilter方法即可
			ctx.set("isSuccess", false);
		}
		return null;
	}
	
}
