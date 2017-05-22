package com.taotao.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>{
    
    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ItemDescService itemDescService;
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    
    public void saveItem(Item item, String desc,String itemParams) {
        
        item.setStatus(1);
        item.setId(null);
        super.save(item);
        
        if(null != itemParams){
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(item.getId());
            itemParamItem.setParamData(itemParams);
            itemParamItemService.save(itemParamItem);
        }
        
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
        
    }

    @Override
    public Mapper<Item> getMapper() {
        return itemMapper;
    }

    public PageInfo<Item> queryItemList(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        example.createCriteria().andNotEqualTo("status", "3");
        this.queryPageListByExample(example, page, rows);
        return super.queryPageListByExample(example, page, rows);
    }
    
    /**
     * 逻辑删除
     * */
    public void updateByIds(List<Object> ids){
        Example example = new Example(Item.class);
        example.createCriteria().andIn("id", ids);
        Item record = new Item();
        record.setStatus(3);//状态3表示逻辑删除
        itemMapper.updateByExampleSelective(record, example);
    }

    public void updateItem(Item item, String desc,ItemParamItem itemParamItem) {
        item.setStatus(null);
        item.setCreated(null);
        super.updateSelective(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.update(itemDesc);
        
        if(null != itemParamItem){
            itemParamItemService.updateSelective(itemParamItem);
        }
        
    }

}
