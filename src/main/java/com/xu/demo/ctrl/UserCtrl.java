package com.xu.demo.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xu.demo.model.User;
import com.xu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
            //System.out.println(userResult.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "updateUser")
    public void updateUser() {
        userService.updateUser(1);
        System.out.println("执行成功！");
    }

    @RequestMapping("toJson")
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    /**
     * 分页查询
     */
    @RequestMapping("pageQuery")
    public PageInfo<User> pageQuery() {
        PageHelper.startPage(0, 10);
        //通过service查询出符合条件的记录返回即可
        List<User> list = new ArrayList<>();
        PageInfo<User> info = new PageInfo<>(list);
        return info;
    }
}
