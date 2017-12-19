package com.xu.demo.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述
 *
 * @author xushaohua on 2017-12-19.
 */
@Controller
@RequestMapping("/api/exception")
public class ExceptionCtrl {

	@RequestMapping("/errorPage")
	public void errorPage() throws Exception{
		System.out.println("错误页面跳转测试");
		throw new Exception("请求错误");
	}
}
