package io.kimmking.cache.controller;

import io.kimmking.cache.entity.User;
import io.kimmking.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/user/find")
    User find(Integer id) {
        return userService.find(id);
    }

    @RequestMapping("/user/list")
    List<User> list() {
        return userService.list();
    }

}