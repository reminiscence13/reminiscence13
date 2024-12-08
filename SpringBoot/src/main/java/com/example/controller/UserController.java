package com.example.controller;

import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User loginUser = userService.login(user);
        return Result.success(loginUser);
    }

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }

    //新增信息
    @PostMapping
    public Result save(@RequestBody User user){
        if(user.getId() == null){
            userService.add(user);
        } else{
            userService.update(user);
        }
        return Result.success();
    }

    //获取所有信息
    @GetMapping()
    public Result findAll(){
        List<User> list = userService.findALl();
        return Result.success(list);
    }

    //查询
    @GetMapping("/search")
    public Result FindBySearch(Params params){
        PageInfo<User> info = userService.FindBySearch(params);
        return Result.success(info);
    }

    //删除信息
    @DeleteMapping("/{id}")
    public  Result delete(@PathVariable Integer id){
        userService.delete(id);
        return Result.success();
    }
}
