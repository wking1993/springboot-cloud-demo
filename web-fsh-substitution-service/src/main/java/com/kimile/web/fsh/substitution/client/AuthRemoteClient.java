package com.kimile.web.fsh.substitution.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kimile.web.fsh.substitution.base.ResponseData;
import com.kimile.web.fsh.substitution.configs.FeignConfiguration;
import com.kimile.web.fsh.substitution.query.AuthQuery;

@FeignClient(value = "web-auth-service", path = "/oauth", 
	configuration = FeignConfiguration.class, fallback = AuthRemoteClientHystrix.class)
public interface AuthRemoteClient {
	
	/**
	 * 调用认证，获取token
	 * 每次调用接口之前都去认证，会导致性能降低，且Token是可以设置过期时间的，没有必要每次都去重新申请；
	 * 可以将Token缓存到本地或者Redis中。需要注意的是缓存时间必须小于Token的过期时间（暂未实现）
	 * @param query
	 * @return
	 */
	@PostMapping("/token")
	ResponseData auth(@RequestBody AuthQuery query);
	
}
