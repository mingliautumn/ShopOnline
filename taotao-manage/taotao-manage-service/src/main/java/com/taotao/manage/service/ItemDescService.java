package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemDescService extends BaseService<ItemDesc>{
    
    @Autowired
    private ItemDescMapper itemDescMapper; 

    @Override
    public Mapper<ItemDesc> getMapper() {
        return itemDescMapper;
    }

}
