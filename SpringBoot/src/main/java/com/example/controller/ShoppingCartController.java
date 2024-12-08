package com.example.controller;

import com.example.common.Result;
import com.example.entity.Cart;
import com.example.entity.Goods;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.ShoppingCartService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ShoppingCart")

public class ShoppingCartController {

    @Resource
    private ShoppingCartService ShoppingCartService;



    @GetMapping("/search")
    public Result FindBySearch(Params params){
        PageInfo<Cart> info = ShoppingCartService.FindBySearch(params);
        return Result.success(info);
    }

    @PostMapping("/addtocart")
    public Result addtocart(@RequestBody Cart cart){
        ShoppingCartService.add(cart);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        ShoppingCartService.delete(id);
        return Result.success();
    }

    @PostMapping("/buy")
    public Result buy(@RequestBody Cart cart){
        ShoppingCartService.buy(cart);
        System.out.println(cart.getEmail());
        return Result.success();
    }
}
