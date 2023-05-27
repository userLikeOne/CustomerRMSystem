package com.dxf.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxf.springboot.common.R;
import com.dxf.springboot.entity.Employee;
import com.dxf.springboot.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 员工后台登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if(emp==null){
            return R.error("用户名或密码错误，请重新输入");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("用户名或密码错误，请重新输入");
        }
        //5、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 后台员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("账户退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        String password = DigestUtils.md5DigestAsHex("123456".getBytes()); //对 123456 进行MD5加密
        long emp = (long) request.getSession().getAttribute("employee"); // 获取员工id
        employee.setRole(0); // 默认创建用户
        employee.setPassword(password);// 设置初始密码
        employeeService.save(employee);
        return R.success("用户添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        // 1.创建page对象，分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        // 2.条件构造器：对姓名模糊查询
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getUsername,name);
        // 3.执行分页查询
        /**
         * page(分页信息，查询信息)
         * 信息存储在pageInfo内
         */
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        employeeService.updateById(employee);
        return R.success("状态修改成功");
    }

    @DeleteMapping
    public R<String> remove(@RequestBody Employee employee){
        log.info("删除={}",employee.getId());
        boolean ret = employeeService.removeById(employee.getId());
        if (ret){
            return R.success("账户删除成功!");
        } else {
            return R.success("账户删除失败!");
        }
    }

    @PutMapping("/edit")
    public R<String> edit(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        employeeService.updateById(employee);
        return R.success("账户修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> get(@PathVariable long id){
        Employee emp = employeeService.getById(id);
        if (emp!=null){
            return R.success(emp);
        }
        return R.error("没有查到对应员工信息");
    }
}
