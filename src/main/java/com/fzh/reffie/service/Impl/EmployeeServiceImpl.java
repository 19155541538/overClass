package com.fzh.reffie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reffie.entity.Employee;
import com.fzh.reffie.mapper.EmployeeMapper;
import com.fzh.reffie.service.EmployeeService;
import org.springframework.stereotype.Service;


//ServiceImpl 是mybatisplus 提供的
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
