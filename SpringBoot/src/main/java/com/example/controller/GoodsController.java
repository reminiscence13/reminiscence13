package com.example.controller;

import com.example.common.Result;
import com.example.entity.Goods;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.service.GoodsService;
import com.example.service.ShoppingCartService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Goods")
public class GoodsController {

    @Resource
    private GoodsService GoodsService;

    //新增信息
    @PostMapping("/add")
    public Result save(@RequestBody Goods goods) {
        if (goods.getGoodsid() == null) {
            GoodsService.add(goods);
        } else {
            GoodsService.update(goods);
        }
        return Result.success();
    }
    //获取所有信息
    @GetMapping()
    public Result findAll(){
        List<Goods> list = GoodsService.findALl();
        return Result.success(list);
    }

    //查询
    @GetMapping("/search")
    public Result FindBySearch(Params params){
        PageInfo<Goods> info = GoodsService.FindBySearch(params);
        return Result.success(info);
    }

    //删除信息
    @DeleteMapping("/{id}")
    public  Result delete(@PathVariable Integer id){
        GoodsService.delete(id);
        return Result.success();
    }


}
