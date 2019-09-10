package com.jackchen.service.impl;

import com.jackchen.mapper.CommentMapper;
import com.jackchen.pojo.Comment;
import com.jackchen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int insertSelective(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    @Override
    public List<Comment> findAll() {
        return commentMapper.findAll();
    }
}
