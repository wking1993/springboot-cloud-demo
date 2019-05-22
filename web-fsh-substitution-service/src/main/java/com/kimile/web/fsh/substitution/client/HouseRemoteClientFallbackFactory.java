package com.kimile.web.fsh.substitution.client;

import org.springframework.stereotype.Component;

import com.kimile.web.fsh.substitution.ov.HouseInfo;

import feign.hystrix.FallbackFactory;

@Component
public class HouseRemoteClientFallbackFactory implements FallbackFactory<HouseRemoteClient> {

	@Override
	public HouseRemoteClient create(Throwable cause) {
		System.out.println("===============" + cause.getMessage());
		return new HouseRemoteClient() {
			
			@Override
			public HouseInfo houseInfo(Long houseId) {
				return new HouseInfo();
			}
			
			@Override
			public String hello() {
				return "Factory fail message";
			}
			
		};
	}
	
}
