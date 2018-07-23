package com.bobo.web.controller;

import com.bobo.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/20下午2:21
 */
@RestController
public class UserController {




    @GetMapping("/user")
    @JsonView(User.UserSimpleView.class)
    public List<User> user(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }
}
