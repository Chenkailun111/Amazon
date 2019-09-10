package com.jackchen.service;

import com.jackchen.pojo.ShopCart;

import java.util.List;

public interface ShopCartService {
    public ShopCart selectOne(long id);

    public int insertSelective(ShopCart cart);

    //新增或者更改
    public int saveOrUpdate(ShopCart shopCart);
    //根据用户id查询所有的购物车信息
    List<ShopCart> findCarts(long id);

    ShopCart findByPidAndUserId(long pid,long userId);

    public int updateSelective(ShopCart cart);

    //通过id删除
    public int deleteById(long id);
}
