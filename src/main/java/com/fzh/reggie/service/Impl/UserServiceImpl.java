package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.entity.User;
import com.fzh.reggie.mapper.UserMapper;
import com.fzh.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/12 23:11
 * @description 1
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
