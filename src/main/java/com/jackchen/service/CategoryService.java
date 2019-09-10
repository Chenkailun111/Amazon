package com.jackchen.service;

import com.jackchen.pojo.Product;
import com.jackchen.pojo.ProductCategory;
import com.jackchen.utils.R;

import java.util.List;

public interface CategoryService {

    //查找menu
    public R findMenu();

    //找寻具体的二级目录的商品
    public List<ProductCategory> findSecondProduct(Long id,String productName);
}
