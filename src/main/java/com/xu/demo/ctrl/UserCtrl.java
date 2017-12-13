package com.xu.demo.ctrl;

import com.xu.demo.model.User;
import com.xu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author xushaohua on 2017-11-22.
 */
@RestController
@RequestMapping("/api/user")
public class UserCtrl {
	@Autowired
	UserService userService;

	@RequestMapping("find")
	public void findOne() {
		try {
			User user = new User();
			user.setId(1);
			User userResult = userService.findOne(user);
			System.out.println(userResult.getName());
			System.out.println(userResult.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
