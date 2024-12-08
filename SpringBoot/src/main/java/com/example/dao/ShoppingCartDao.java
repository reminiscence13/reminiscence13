package com.example.dao;

import com.example.entity.Cart;
import com.example.entity.Params;
import com.example.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface ShoppingCartDao extends Mapper<Cart> {

    @Select("select * from shoppingcart")
    List<Cart> findALl();



    List<Cart> FindByuserid(@Param("userid") Integer userid);

    List<Cart> FindBySearch(@Param("userid") Integer userid);

    @Insert("insert into shoppingcart(userid,goodsid,price,quantity) values(#{userid},#{goodsid},#{price},#{quantity})")
    void addtocart(Integer userid, Integer goodsid,Integer price,Integer quantity);

    @Insert("insert into myorder(goodsid,userid,email,total) values(#{goodsid},#{userid},#{email},#{total})")
    void buy(Integer goodsid, Integer userid,String email,Integer total);

    @Select("select * from shoppingcart where goodsid = #{goodsid} and userid = #{userid} limit 1")
    Cart findByid(@Param("goodsid")Integer goodsid,@Param("userid") Integer userid);


    void updatecart(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
}
