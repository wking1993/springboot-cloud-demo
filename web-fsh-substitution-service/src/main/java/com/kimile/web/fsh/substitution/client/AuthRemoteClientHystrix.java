package com.kimile.web.fsh.substitution.client;

import org.springframework.stereotype.Component;

import com.kimile.web.fsh.substitution.base.ResponseData;
import com.kimile.web.fsh.substitution.query.AuthQuery;

@Component
public class AuthRemoteClientHystrix implements AuthRemoteClient {

	@Override
	public ResponseData auth(AuthQuery query) {
		return ResponseData.fail();
	}

}
