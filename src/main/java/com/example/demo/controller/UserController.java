package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final RedisTemplate<String, Object> redisTemplate;
	@GetMapping(value = "/users")
	public List<User> getMyDomains(){
		User user = new User();
		user.setName("John" + new Random().nextInt());
		userService.save(user);
		redisTemplate.opsForValue().get("123");
		return userService.findAll();
	}
}
