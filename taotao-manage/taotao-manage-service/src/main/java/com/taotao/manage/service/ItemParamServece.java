package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemParam;

@Service
public class ItemParamServece extends BaseService<ItemParam>{

    @Autowired
    private ItemParamMapper itemParamMapper;
    
    @Override
    public Mapper<ItemParam> getMapper() {
        return this.itemParamMapper;
    }

}
