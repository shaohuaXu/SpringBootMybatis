package com.xu.demo.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 简单的页面渲染
 *
 * @author xushaohua on 2017-12-18.
 */
@Controller
@RequestMapping("/api/hello")
public class HelloCtrl {
	@RequestMapping("index")
	public String index(ModelMap model){
		model.addAttribute("host", "www.baidu.com");
		return "index";
	}
}
