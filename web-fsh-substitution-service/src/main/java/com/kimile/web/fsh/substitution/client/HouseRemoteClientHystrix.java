package com.kimile.web.fsh.substitution.client;

import org.springframework.stereotype.Component;

import com.kimile.web.fsh.substitution.ov.HouseInfo;

@Component
public class HouseRemoteClientHystrix implements HouseRemoteClient {

	@Override
	public String hello() {
		return "It is failed!";
	}

	@Override
	public HouseInfo houseInfo(Long houseId) {
		return new HouseInfo(1001L, "西安", "高新", "领海大厦");
	}

}
