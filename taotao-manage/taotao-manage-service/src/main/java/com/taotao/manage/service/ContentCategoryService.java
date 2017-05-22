package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory>{
    
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public Mapper<ContentCategory> getMapper() {
        return contentCategoryMapper;
    }

}
