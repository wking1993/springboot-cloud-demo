package com.kimile.web.auth.service;

import com.kimile.web.auth.po.User;
import com.kimile.web.auth.query.AuthQuery;

public interface AuthService {
	
	User auth(AuthQuery query);
	
}
