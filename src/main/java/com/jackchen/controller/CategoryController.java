package com.jackchen.controller;

import com.jackchen.pojo.ProductCategory;
import com.jackchen.service.CategoryService;
import com.jackchen.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/findCategory1")
    @ResponseBody
    public R findMenu() throws Exception{

        //查询菜单，验证
        /*R menu = categoryService.findMenu();
        List<Map<String, Object>> menuList = (List<Map<String, Object>>) menu.get("menuList");
        for (Map<String, Object> map : menuList) {
            long id = (Long)map.get("id");
            String name = (String) map.get("name");
            System.out.println("????"+name);
            System.out.println("------"+id);
            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("childlist");

            //子菜单遍历
            for (Map<String, Object> stringObjectMap : list) {
                System.out.println(stringObjectMap.get("name")+"-------------");
            }
        }*/
        return categoryService.findMenu();
    }

    //找到具体的id选项，二级目录
    @RequestMapping("/findSecondCategory")
    public String findSecondCategory(Long id,String productName,
                                     HttpServletResponse response,
                                     HttpServletRequest request){
        List<ProductCategory> product = categoryService.findSecondProduct(id,productName);

        //二级目录下的对应的商品secondCategory
        request.setAttribute("category",product);
        return "/Cartlist";
    }
}
