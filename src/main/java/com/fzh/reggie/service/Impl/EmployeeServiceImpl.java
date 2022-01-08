package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.entity.Employee;
import com.fzh.reggie.mapper.EmployeeMapper;
import com.fzh.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;


//ServiceImpl 是mybatisplus 提供的
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
