package com.example.dao;

import com.example.entity.Order;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MyOrderDao extends Mapper<Order> {


    @Select("select * from order")
    List<Order> findALl();

    List<Order> FindBySearch(@Param("params") Params params);

    int del(@Param("id") int id);
}
