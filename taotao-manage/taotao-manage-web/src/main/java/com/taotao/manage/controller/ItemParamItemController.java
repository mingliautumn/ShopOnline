package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@RequestMapping("item/param/item")//item/param/item
@Controller
public class ItemParamItemController {
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    
    /**
     * 查询商品规格参数
     * */
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryItemParamItemByItemId(@PathVariable("itemId")Long itemId){

        try {
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(itemId);
            ItemParamItem paramItem = itemParamItemService.queryOne(itemParamItem);
            if(null == paramItem){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(paramItem);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
