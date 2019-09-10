package com.jackchen.controller;

import com.jackchen.pojo.Comment;
import com.jackchen.service.CommentService;
import com.jackchen.utils.RequiredLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommitController {

    @Autowired
    private CommentService commentService;

    @RequiredLogin
    @RequestMapping("/addComment")
    public String addComment(Comment comment){
            commentService.insertSelective(comment);
        return "/index";
    }

    @RequiredLogin
    @RequestMapping("/guestbook")
    public String guestbook(HttpServletRequest request){
        List<Comment> all = commentService.findAll();
        request.setAttribute("all",all);
        return "/guestbook";
    }
}
