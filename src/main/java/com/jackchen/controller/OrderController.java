package com.jackchen.controller;

import com.jackchen.pojo.Order;
import com.jackchen.pojo.OrderDetail;
import com.jackchen.pojo.Product;
import com.jackchen.pojo.User;
import com.jackchen.service.OrderService;
import com.jackchen.service.ProductService;
import com.jackchen.service.UserLoginService;
import com.jackchen.utils.RequiredLogin;
import com.jackchen.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 立即购买
     * @param count
     * @param id
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequiredLogin
    @RequestMapping("/buy")
    public String buy(@RequestParam(value = "count") long count,
                    @RequestParam(value = "id") long id,
                    HttpServletResponse response,
                    HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=utf8");
        System.out.println(count+"============"+id);
        //通过product找到目录
        Product product = productService.selectByPrimaryKey(id);
        //组装订单明细数据
        //支付功能，支付成功更改订单状态
        Order order = new Order();
        order.setStatus(1); //可以设置支付状态：0未支付 1已支付
        order.setType(1);
        order.setCreateTime(new Date());
        BigDecimal money = new BigDecimal(count).multiply(product.getPrice());
        System.out.println(money+"付款金额------------------------");
        order.setMoney(money);
        //设置用户人的信息，uid,用户名，用户住址
        User currentUser = ShiroUtils.getCurrentUser();
        User byName = userLoginService.findByName(currentUser.getUname());
        order.setUid(byName.getId());
        order.setUname(byName.getUname());
        order.setUaddress(byName.getAddress());
        PrintWriter out=response.getWriter();
        //todo 插入订单表，订单明细表
        orderService.insertOrder(order,product.getId(),count);
        //todo 此时应该跳转到支付页面,完成支付之后返回到主页
       /* if (k>0){
            out.write("<script>alert('购买成功');top.location.href='/shopping-result.jsp'</script>");
        }*/
       request.setAttribute("orderId",order.getId());
       return "/shopping-result";
    }

    @RequiredLogin
    //查看订单明细
    @RequestMapping("/findOrderDetail")
    public String findOrderDetail(
                                  HttpServletResponse response,
                                  HttpServletRequest request){
        User currentUser = ShiroUtils.getCurrentUser();
        User byName = userLoginService.findByName(currentUser.getUname());
        List<Order> all = orderService.findAll(byName.getId());
        for (Order order : all) {
            System.out.println("订单"+order);
        }
        request.setAttribute("orderList",all);
        return "/orders_view";
    }

    /**
     * @RequestParam(value = "price") long price,
     *                       @RequestParam(value = "pid") long pid,
     *                       @RequestParam(value = "quantity") long quantity
     */
    //结算所有清单
    @RequiredLogin
    @RequestMapping("/doBuy")
    public void doBuy(long[] single, HttpServletResponse response,
                      HttpServletRequest request ) throws Exception{
        //如果更改数量应该更改购物车表，合理做法是应该保存购物车到cookie，
        //登录之后，清空购物车，保存到redis之中，购买应该有一个id选项，通过
        //选项结算具体的款项

        response.setContentType("text/html;charset=utf-8");//防止输出中文乱码问题
        PrintWriter out=response.getWriter();
        for (long i : single) {
            System.out.println("购物车id"+i);
        }
        orderService.caculateMoney(single);

        out.write("<script>alert('结算成功');top.location.href='/shopping-result.jsp'</script>");
    }
}
