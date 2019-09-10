package com.jackchen.service.impl;

import com.jackchen.mapper.NewsMapper;
import com.jackchen.pojo.News;
import com.jackchen.pojo.NewsExample;
import com.jackchen.service.NewsService;
import com.jackchen.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public R findNews() {
        //todo
        /*NewsExample newsExample = new NewsExample();
        NewsExample.Criteria criteria = newsExample.createCriteria();*/

        List<News> news = newsMapper.selectByExample(null);
        /*for (News news1 : news) {
            System.out.println(news1);
        }*/
        return R.ok().put("news",news);
    }

    @Override
    public News findOne(long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        //System.out.println(news);
        return news;
    }
}
