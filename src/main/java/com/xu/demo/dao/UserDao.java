package com.xu.demo.dao;

import com.xu.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 *
 * @author xushaohua on 2017-11-22.
 */
@Mapper
public interface UserDao {
	User findOne(User user);
}
