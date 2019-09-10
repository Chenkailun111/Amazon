package com.jackchen.service;

import com.jackchen.pojo.Comment;

import java.util.List;

public interface CommentService {

    int insertSelective(Comment comment);

    List<Comment> findAll();
}
