package com.jackchen.service;

import com.github.pagehelper.PageInfo;
import com.jackchen.pojo.Product;
import com.jackchen.utils.R;

public interface ProductService {

    public R ishot();

    public Product selectByPrimaryKey(long id);

    PageInfo findList(int pageindex, int size);
}
