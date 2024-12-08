package com.example.controller;

import com.example.common.Result;
import com.example.entity.Order;
import com.example.entity.Params;
import com.example.service.MyOrderService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class MyOrderController {

    @Resource
    private MyOrderService myOrderService;

    @GetMapping("/search")
    public Result FindBySearch(Params params){
        PageInfo<Order> info = myOrderService.FindBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public  Result delete(@PathVariable Integer id){
        myOrderService.delete(id);
        return Result.success();
    }
}
