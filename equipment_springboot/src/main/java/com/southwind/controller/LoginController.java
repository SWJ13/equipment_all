package com.southwind.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.Admin;
import com.southwind.entity.Teacher;
import com.southwind.form.LoginForm;
import com.southwind.form.RegisterForm;
import com.southwind.service.AdminService;
import com.southwind.service.TeacherService;
import com.southwind.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/login")
    public ResultVO login(LoginForm loginForm) {
        if (loginForm.getType().equals("admin")) {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", loginForm.getUsername());
            Admin admin = this.adminService.getOne(queryWrapper);
            if(admin == null){
                return new ResultVO(-1,"用户名错误",null);
            }
            if(!admin.getPassword().equals(loginForm.getPassword())){
                return new ResultVO(-1,"密码错误",null);
            }
            return new ResultVO(0,"",admin);
        } else {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", loginForm.getUsername());
            Teacher teacher = this.teacherService.getOne(queryWrapper);
            if(teacher == null){
                return new ResultVO(-1,"用户名错误",null);
            }
            if(!teacher.getPassword().equals(loginForm.getPassword())){
                return new ResultVO(-1,"密码错误",null);
            }
            return new ResultVO(0,"",teacher);
        }
    }

    @PostMapping("/register")
    public ResultVO register(@RequestBody RegisterForm registerForm) {
        if ("admin".equals(registerForm.getType())) {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", registerForm.getUsername());
            Admin exist = this.adminService.getOne(queryWrapper);
            if (exist != null) {
                return new ResultVO(-1, "用户名已存在", null);
            }
            Admin admin = new Admin();
            admin.setUsername(registerForm.getUsername());
            admin.setPassword(registerForm.getPassword());
            admin.setName(registerForm.getName());
            admin.setAge(registerForm.getAge());
            admin.setGender(registerForm.getGender());
            admin.setAddress(registerForm.getAddress());
            boolean saved = this.adminService.save(admin);
            return saved ? new ResultVO(0, "注册成功", admin) : new ResultVO(-1, "注册失败", null);
        } else {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", registerForm.getUsername());
            Teacher exist = this.teacherService.getOne(queryWrapper);
            if (exist != null) {
                return new ResultVO(-1, "用户名已存在", null);
            }
            Teacher teacher = new Teacher();
            teacher.setUsername(registerForm.getUsername());
            teacher.setPassword(registerForm.getPassword());
            teacher.setName(registerForm.getName());
            teacher.setAge(registerForm.getAge());
            teacher.setGender(registerForm.getGender());
            teacher.setAddress(registerForm.getAddress());
            boolean saved = this.teacherService.save(teacher);
            return saved ? new ResultVO(0, "注册成功", teacher) : new ResultVO(-1, "注册失败", null);
        }
    }
}
