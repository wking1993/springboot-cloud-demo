package com.kimile.web.auth.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kimile.web.auth.base.ResponseData;
import com.kimile.web.auth.po.User;
import com.kimile.web.auth.query.AuthQuery;
import com.kimile.web.auth.service.AuthService;
import com.kimile.web.auth.util.JWTUtils;

@RestController
@RequestMapping("/oauth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/token")
	public ResponseData auth(@RequestBody AuthQuery query) throws Exception {
		if (StringUtils.isBlank(query.getAccessKey()) || StringUtils.isBlank(query.getSecretKey())) {
			return ResponseData.failByParam("accessKey and secretKey not null");
		}
		User user = authService.auth(query);
		if (user == null) {
			return ResponseData.failByParam("认证失败");
		}
		JWTUtils jwt = JWTUtils.getInstance();
		return ResponseData.ok(jwt.getToken(user.getId().toString()));
	}
	
}
