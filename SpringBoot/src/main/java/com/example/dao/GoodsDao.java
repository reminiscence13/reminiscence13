package com.example.dao;

import com.example.entity.Goods;
import com.example.entity.Params;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GoodsDao extends Mapper<Goods> {

    @Select("select * from goods")
    List<Goods> findALl();

    List<Goods> FindBySearch(@Param("params") Params params);


}
