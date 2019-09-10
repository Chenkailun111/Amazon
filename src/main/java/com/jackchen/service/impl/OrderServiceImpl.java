package com.jackchen.service.impl;

import com.jackchen.mapper.*;
import com.jackchen.pojo.*;
import com.jackchen.service.OrderService;
import com.jackchen.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Order> findAll(long userId) {
        //todo 查询全部订单数据
        return orderMapper.findAll(userId);
    }

    @Override
    public Order selectOne(long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertSelective(Order order) {

        return orderMapper.insertSelective(order);
    }

    //本来要做成分发更合理，立即购买
    @Transactional
    @Override
    public void insertOrder(Order order,long productId,long count)throws Exception {
        //插入数据库和订单明细
        orderMapper.insertSelective(order);
        //todo 插入到订单明细
        OrderDetail detail = new OrderDetail();
        detail.setOid(order.getId());
        detail.setPid(productId);
        detail.setQuantity(count);
        detail.setMoney(order.getMoney());

        orderDetailMapper.insertSelective(detail);
        //清空购物车
    }

    @Transactional
    @Override
    public void caculateMoney(long[] ids) throws Exception {
        Order order = new Order();
        //通过id找到购物车，
        ShopCart shopCart = shopCartMapper.selectByPrimaryKey(ids[0]);
        //组装订单数据
        order.setType(1);
        order.setType(1);
        User user = userMapper.selectByPrimaryKey(shopCart.getUid());
        //用户数据
        order.setUaddress(user.getAddress());
        order.setUid(user.getId());
        order.setUname(user.getUname());
        order.setCreateTime(new Date());
        orderMapper.insertSelective(order);
        //总金额
        BigDecimal sumMoney = new BigDecimal(0);
        //TODO 组长订单明细，一个订单下面可能有多个条目信息
        for (long id : ids) {
            OrderDetail detail = new OrderDetail();
            ShopCart shopCart1 = shopCartMapper.selectByPrimaryKey(id);
            System.out.println("!!!!!!!!!!!!!订单id"+order.getId());
            detail.setOid(order.getId());
            //一个类目的金额
            Product product = productMapper.selectByPrimaryKey(shopCart1.getPid());

            detail.setPid(product.getId());
            long quantity = shopCart1.getPnum();
            detail.setQuantity(quantity);
            //todo  每一个详细订单的金额
            BigDecimal money = product.getPrice().multiply(
                    new BigDecimal(shopCart1.getPnum())
            );
            detail.setMoney(money);
            //每一个类目插入到数据库
            orderDetailMapper.insertSelective(detail);

            //todo 总金额
            sumMoney = sumMoney.add(money);
        }
        //订单总金额
        order.setMoney(sumMoney);
        //插入订单
        orderMapper.updateByPrimaryKeySelective(order);

        //如果操作都无误，清空购物车
        for (long id : ids) {
            shopCartMapper.deleteByPrimaryKey(id);
        }
    }
}
