package com.fzh.reffie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reffie.common.R;
import com.fzh.reffie.entity.Employee;
import com.fzh.reffie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

//Slf4j添加打印日志的注解
@Slf4j
@RestController
@RequestMapping("/employee")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //①.将页面提交的密码password进行md5加密处理, 得到加密后的字符串 md5密文是不可逆的
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //②.根据页面提交的用户名username查询数据库中员工数据信息
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //③.如果没有查询到, 则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }
        //④.密码比对，如果不一致, 则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        //⑤.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        //⑥. 登录成功，将员工id存入Session, 并返回登录成功结果
//        获取session对象,然后把要绑定对象/值 绑定到session对象上 用户的一次会话共享一个session对象。
        request.getSession().setAttribute("employee", emp.getId());
        //上面的employee把用户写死了 用户退出都退出了  emp.getId().toString()动态的获取ID
        //request.getSession().setAttribute(emp.getId().toString(), emp.getId());
        emp.setPassword("");//不让前端看到密码

        return R.success(emp);//emp 从数据库查出来的用户信息
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的ID
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加方法
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工,员工信息:{}", employee.toString());
        //设置初始密码并进行加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //获取用户创建时间和用户修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获取当前用户ID
        Long empId = (Long) request.getSession().getAttribute("employee");

        //通过ID获取当前创建人 和 修改人
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> findBypage(int page, int pageSize, String name) {

        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        /*if(page == null){
            page=1;
        }
        if(pageSize == null){
            pageSize=10;
        }*/

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件  StringUtils.isNotEmpty(name)判断姓名是否为空
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件  orderByDesc
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据ID修改员工信息
     *
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        //获取员工id
        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据Id查数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}
