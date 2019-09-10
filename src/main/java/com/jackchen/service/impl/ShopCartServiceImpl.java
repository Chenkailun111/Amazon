package com.jackchen.service.impl;

import com.jackchen.mapper.ShopCartMapper;
import com.jackchen.pojo.ShopCart;
import com.jackchen.pojo.ShopCartExample;
import com.jackchen.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;
    @Override
    public ShopCart selectOne(long id) {
        return shopCartMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertSelective(ShopCart cart) {
        return shopCartMapper.insertSelective(cart);
    }

    @Override
    public int saveOrUpdate(ShopCart shopCart) {
        int k = 0;
        if (shopCart.getId() != null){
            k = shopCartMapper.updateByPrimaryKeySelective(shopCart);
        }else {
            k = shopCartMapper.insertSelective(shopCart);
        }
        return k;
    }

    @Override
    public List<ShopCart> findCarts(long id) {
        return shopCartMapper.findCarts(id);
    }

    //通过商品id查找购物车的记录
    @Override
    public ShopCart findByPidAndUserId(long pid,long userId) {
        ShopCart carts = shopCartMapper.findByPidAndUserId(pid, userId);
        return carts;
    }

    @Override
    public int updateSelective(ShopCart cart) {
        return shopCartMapper.insertSelective(cart);
    }

    @Override
    public int deleteById(long id) {
        return shopCartMapper.deleteByPrimaryKey(id);
    }
}
