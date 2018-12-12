package com.xu.demo.ctrl;

import com.xu.demo.model.User;
import com.xu.demo.utils.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * JacksonSerializer用法
 *
 * Created by shaohua on 2018/12/12.
 */
@RestController
@RequestMapping(value = "/api/jackson")
public class JacksonCtrl {
    @Autowired
    JacksonSerializer jacksonSerializer;

    @RequestMapping(value = "testJackson")
    public String testJackson() {
        User user = new User();
        user.setAge("18");
        user.setMoney(new BigDecimal("58.32"));
        user.setName("world");
        return jacksonSerializer.serialize(user);
    }
}
