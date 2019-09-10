package com.jackchen.service;

import com.jackchen.pojo.Order;
import com.jackchen.pojo.OrderDetail;

import java.util.List;

public interface OrderService {

    public List<Order> findAll(long userId);

    public Order selectOne(long id);

    public int insertSelective(Order order);

    //新增订单
    public void insertOrder(Order order,long productId,long count) throws Exception;

    //结算清单
    public void caculateMoney(long[] ids) throws Exception;
}
