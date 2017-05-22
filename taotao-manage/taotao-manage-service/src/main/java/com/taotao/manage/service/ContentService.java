package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;

@Service
public class ContentService extends BaseService<Content>{

    @Autowired
    private ContentMapper contentMapper;
    
    @Override
    public Mapper<Content> getMapper() {
        return contentMapper;
    }

    public PageInfo<Content> queryList(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<Content> (contentMapper.queryListOrderByUpdateDesc(categoryId));
    }

}
