package com.jackchen.controller;

import com.jackchen.pojo.News;
import com.jackchen.service.NewsService;
import com.jackchen.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;
    @RequestMapping("/findNews")
    public R findNews(){
        System.out.println("================findNews");
        return newsService.findNews();
    }

    @RequestMapping("/findNewsDetail")
    public void findNewsDetail(
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 String id) throws Exception{
        long newsId = Long.parseLong(id);
        System.out.println(newsId+"!!!!!!!!!!!!!!!");
        News one = newsService.findOne(newsId);
        System.out.println(one);
       // modelMap.addAttribute("news",one);
        request.setAttribute("news",one);
        request.getRequestDispatcher("/news_view.jsp").forward(request,response);
    }
}
