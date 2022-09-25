package com.app.medidrone.security.service;

import com.app.medidrone.dao.entity.User;

public interface UserService {
	void save(User user);

    User findByUsername(String username);
}
