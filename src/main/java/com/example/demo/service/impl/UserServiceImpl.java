package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AbstractBaseService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractBaseService<User, Long> implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository baseRepository) {
		super(baseRepository);
		this.userRepository = baseRepository;
	}
}
