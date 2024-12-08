package com.example.service;

import com.example.dao.MyOrderDao;
import com.example.entity.Order;
import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyOrderService {

    @Resource
    private MyOrderDao myOrderDao;

    public  PageInfo<Order> FindBySearch(Params params){
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Order> list  = myOrderDao.FindBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(Integer id) { myOrderDao.del(id); }
}
