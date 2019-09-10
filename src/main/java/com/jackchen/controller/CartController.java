package com.jackchen.controller;

import com.jackchen.pojo.Product;
import com.jackchen.pojo.ShopCart;
import com.jackchen.pojo.User;
import com.jackchen.service.ProductService;
import com.jackchen.service.ShopCartService;
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
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private UserLoginService userLoginService;
    /**
     * 添加购物车，此处的方法本来是前端页面的所有属性传入过来，
     * 存入数据库，当真正付款后，更改订单的状态，没有付款可以设置
     * 订单状态未付款
     *
     * 此处存入的是数据库，可以采用rabbitmq的方式进行。
     * @param count
     * @param id
     */
    @RequiredLogin
    @RequestMapping("/addCart")
    public void addCart(@RequestParam(value = "count") int count,
                        @RequestParam(value = "id") long id,
                        HttpServletResponse response,
                        HttpServletRequest request
    ) throws Exception{
        response.setContentType("text/html;charset=utf-8");//防止输出中文乱码问题
        PrintWriter out=response.getWriter();
        System.out.println(count+"============"+id);
        int k; //代表是否操作成功

        //通过product找到目录
        Product product = productService.selectByPrimaryKey(id);
        ShopCart shopCart = new ShopCart();

        shopCart.setPid(product.getId()); // 商品id
        shopCart.setPnum(count);
        //todo 如果商品id已经存入过+1
        //通过商品id查找购物车记录
        User currentUser = ShiroUtils.getCurrentUser();

        //TODO 设置当前用户，这个方法是需要登录,详情到购物车详情页面

        shopCart.setUid(currentUser.getId());
        //找到是否添加过信息 TODO
        ShopCart cart = shopCartService.findByPidAndUserId(product.getId(),currentUser.getId());
        if (cart!=null){ //证明之前添加过信息
            //找到具体的记录，更改数据
            ShopCart shopCart1 = shopCartService.selectOne(cart.getId());
            //jack 此条信息数目+1
            shopCart.setPnum(shopCart1.getPnum()+count);
            shopCart.setId(shopCart1.getId());
        }
        //新增数据或者是更改数据
        k = shopCartService.saveOrUpdate(shopCart);

        if (k>0){
            out.write("<script>alert('添加购物车成功');top.location.href='/index.jsp'</script>");
        }
        //request.getRequestDispatcher("/index.jsp").forward(request,response);
    }


    @RequiredLogin
    @RequestMapping("/showCar")
    public String showCar(HttpServletRequest request,
                          HttpServletResponse response){
        //查询数据库数据返回到页面
        User currentUser = ShiroUtils.getCurrentUser();

        List<ShopCart> carts = shopCartService.findCarts(currentUser.getId());
        request.setAttribute("shopCart",carts);

        return "/shopping";
    }


    //删除购物车的信息通过id
    @RequestMapping("/deleteById")
    public void deleteById(@RequestParam long id,
                           HttpServletResponse response,
                           HttpServletRequest request) throws Exception{
        response.setContentType("text/html;charset=utf-8");//防止输出中文乱码问题
        PrintWriter out=response.getWriter();
        int k = shopCartService.deleteById(id);
        if (k>0){
            out.write("<script>alert('删除购物车成功');top.location.href='/showCar'</script>");
        }
    }
}
