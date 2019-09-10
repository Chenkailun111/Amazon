package com.jackchen.service.impl;

import com.jackchen.mapper.ProductCategoryMapper;
import com.jackchen.pojo.Product;
import com.jackchen.pojo.ProductCategory;
import com.jackchen.service.CategoryService;
import com.jackchen.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryMapper categoryMapper;
    @Override
    public R findMenu() {
        List<Map<String, Object>> parentMenu =
                categoryMapper.findParentMenu();

        for (Map<String, Object> map : parentMenu) {
            //二级目录
            long id = (Long)map.get("id");
            List<Map<String, Object>> menuMap = categoryMapper.findMenuByParentId(id);
            map.put("childlist",menuMap);
        }
        return R.ok().put("menuList",parentMenu);
    }

    @Override
    public List<ProductCategory> findSecondProduct(Long id,String productName) {

        List<ProductCategory> products = categoryMapper.findSecondProduct(id,productName);
        return products;
    }
}
