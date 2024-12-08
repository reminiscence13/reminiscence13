package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> findALl(){
        return userDao.findALl();
    }

    public PageInfo<User> FindBySearch(Params params){
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List <User> list  =  userDao.FindBySearch(params);
        return PageInfo.of(list);
    }

    //新增用户
    public void add(User user){
        // 1.用户名一定要有，否则不让新增
        if(user.getName() == null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空");
        }
        // 2.进行重复性判断，不允许同一名字重复新增
        User user1 = userDao.findByName(user.getName());
        if (user1 != null){
            //说明已经有了，不允许新增
            throw new CustomException("该用户名已存在");
        }
        //初始化一个默认密码
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userDao.insertSelective(user);
    }

    //修改用户信息
    public void update(User user){
        userDao.updateByPrimaryKeySelective(user);
    }

    public void delete(Integer id) {
        userDao.deleteByPrimaryKey(id);
    }

    public User login(User user) {
        // 1.非空判断
        if(user.getName() == null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空");
        }
        if(user.getPassword() == null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空");
        }
        // 2.去数据库里根据用户名和密码查询用户信息
        // 如果查出来有，说明输入的都对
        // 如果没有，则输入有误，无法登陆
        User user1 = userDao.findByNameandPassword(user.getName(),user.getPassword());
        if (user1 == null){
            throw new CustomException("用户名或密码输入错误");
        }
        return user1;
    }
}
