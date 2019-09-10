package com.jackchen.service;

import com.jackchen.pojo.News;
import com.jackchen.utils.R;

public interface NewsService {

    public R findNews();

    //根据id查找
    public News findOne(long id);
}
