package com.example.service;

import com.example.dao.GoodsDao;
import com.example.entity.Goods;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsDao GoodsDao;

    public List<Goods> findALl(){
        return GoodsDao.findALl();
    }

    public PageInfo<Goods> FindBySearch(Params params){
        //开启分页
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List <Goods> list  =  GoodsDao.FindBySearch(params);
        return PageInfo.of(list);
    }

    //新增用户
    public void add(Goods goods){
        // 1.用户名一定要有，否则不让新增
        if(goods.getGoodsname() == null || "".equals(goods.getGoodsname())) {
            throw new CustomException("商品名不能为空");
        }
        GoodsDao.insertSelective(goods);
    }

    //修改用户信息
    public void update(Goods goods){
        GoodsDao.updateByPrimaryKeySelective(goods);
    }

    public void delete(Integer id) {
        GoodsDao.deleteByPrimaryKey(id);
    }


}
