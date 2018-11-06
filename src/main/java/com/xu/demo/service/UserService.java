package com.xu.demo.service;

import com.xu.demo.model.User;

/**
 * 描述
 *
 * @author xushaohua on 2017-11-22.
 */
public interface UserService {
	User findOne(User user) throws Exception;
	void add(User user) throws Exception;
	void updateUser(int id);
}
