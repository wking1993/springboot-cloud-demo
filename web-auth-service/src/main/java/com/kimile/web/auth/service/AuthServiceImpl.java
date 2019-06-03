package com.kimile.web.auth.service;

import org.springframework.stereotype.Service;

import com.kimile.web.auth.po.User;
import com.kimile.web.auth.query.AuthQuery;

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public User auth(AuthQuery query) {
		return new User(1L);
	}

}
