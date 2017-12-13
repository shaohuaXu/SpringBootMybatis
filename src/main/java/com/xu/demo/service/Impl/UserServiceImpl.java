package com.xu.demo.service.Impl;

import com.xu.demo.dao.UserDao;
import com.xu.demo.model.User;
import com.xu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author xushaohua on 2017-11-22.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public User findOne(User user) throws Exception {
		return userDao.findOne(user);
	}

	@Override
	public void add(User user) throws Exception {

	}
}
