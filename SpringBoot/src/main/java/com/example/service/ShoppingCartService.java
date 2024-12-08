package com.example.service;

import com.example.entity.Cart;
import com.example.dao.ShoppingCartDao;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCartService {

    @Resource
    private ShoppingCartDao ShoppingCartDao;

    public  PageInfo<Cart> FindBySearch(Params params){
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List <Cart> list  =  ShoppingCartDao.FindBySearch(params.getUserid());
        return PageInfo.of(list);
    }

    @Transactional
    public void add(Cart cart) {
        Cart cart1;
        cart1 = ShoppingCartDao.findByid(cart.getGoodsid(),cart.getUserid());
        if (cart1 != null){
            ShoppingCartDao.updatecart(cart.getGoodsid(),cart.getUserid());
        }
        else{
            ShoppingCartDao.addtocart(cart.getUserid(),cart.getGoodsid(),cart.getPrice(),1);
        }
    }

    public void delete(Integer id) { ShoppingCartDao.deleteByPrimaryKey(id); }

    public void buy(Cart cart){
        ShoppingCartDao.buy(cart.getGoodsid(),cart.getUserid(),cart.getEmail(),cart.getTotal());
        ShoppingCartDao.deleteByPrimaryKey(cart.getId());
    }
}
