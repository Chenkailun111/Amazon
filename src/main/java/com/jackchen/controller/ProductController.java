package com.jackchen.controller;

import com.github.pagehelper.PageInfo;
import com.jackchen.pojo.Product;
import com.jackchen.service.ProductService;
import com.jackchen.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @RequestMapping("/findHotlist")
    @ResponseBody
    public R findHotList(){
        return productService.ishot();
    }


    @RequestMapping("/findProductDetail")
    public void findProductDetail(
            HttpServletRequest request,
            HttpServletResponse response,
            long id) throws Exception{

        response.setContentType("text/html;charset=utf-8");//防止输出中文乱码问题

        Product product = productService.selectByPrimaryKey(id);
        request.setAttribute("product",product);

        String histroyId = linkId(String.valueOf(id), request);//获取拼好的商品id,格式:1-2-3-4

        Cookie cookie = new Cookie("histroyId", histroyId);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 10);
        response.addCookie(cookie);

        request.getRequestDispatcher("/product_view.jsp").forward(request,response);
    }


    //拼接方法(拼接的逻辑都写在这里)
    private String linkId(String id, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String histroyId = null;
        boolean flag = false;//默认没有找到指定名字的cookie
        //非第一次访问的时候
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("histroyId")) {
                    flag = true;
                    histroyId = cookie.getValue();//1-2-3
                    break;
                }
            }
        }

        //浏览第一个商品的时候
        if (cookies == null || flag == false) {
            histroyId = id;
        }

        LinkedList<String> list = new LinkedList<String>(Arrays.asList(histroyId.split("-")));
        //假如长度不满4,冲突:先删除冲突的id,再把id添加到头部,不冲突,直接放到头部

        if (list.size() < 4) {
            if (list.contains(id)) {
                list.remove(id);//先删除
            }

        }

        if (list.size() == 4) {
            if (list.contains(id) == false) {
                list.removeLast();// 删除最后一个id
            } else {
                list.remove(id);//先删除
            }

        }
        list.addFirst(id);//添加到头部
        //动态拼接id
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if(i>0){
                sb.append("-");
            }
            sb.append(list.get(i));//拼接字符串
        }
        return sb.toString();
    }


    //取出cookie信息添加到index.jsp中
    @RequestMapping("/getCookie")
    @ResponseBody
    public List getCookies(HttpServletResponse response,
                           HttpServletRequest request) throws Exception {

        System.out.println("获取cookie");
        response.setContentType("text/html;charset=utf-8");//防止输出中文乱码问题
        //获取保存在cookie中的商品编号
        Cookie[] cookies = request.getCookies();

        List<Product> list = new ArrayList<>();
        boolean flag = false;//默认没有找到指定名字的cookie
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("histroyId")) {
                    flag = true;//找到
                    String value = cookie.getValue();
                    String[] ids = value.split("-");

                    for (String id : ids) {
                        long productId = Long.parseLong(id);
                        Product product = productService.selectByPrimaryKey(productId);
                        list.add(product);
                    }
                    break;
                }
            }
        }
        if (cookies == null || flag == false) {
            return null;
        }
       return list;
    }


    //商品列表
    @RequestMapping(value = "/findList",method = RequestMethod.GET)
    @ResponseBody
    public R findList(@RequestParam(value="index",defaultValue = "1") int pageindex,
                      HttpSession session,
                      @RequestParam(value="size",defaultValue = "5") int size){

        PageInfo pageInfo = productService.findList(pageindex, size);
        pageInfo.setPageSize(size);
//        session.setAttribute("pi",pageInfo);
        Map map = new HashMap();
        map.put("prolist",pageInfo.getList());
        map.put("pi",pageInfo);
        return R.ok().put("map",map);
    }


}

