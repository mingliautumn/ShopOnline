package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ModelAndView showItemDetail(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("item");
        
        /**商品信息*/
        Item item = this.itemService.queryItemById(itemId);
        mv.addObject("item", item);
        
        /**商品描述*/
        ItemDesc itemDesc = this.itemService.queryItemDescById(itemId);
        mv.addObject("itemDesc", itemDesc);
        
        /**規格參數*/
        String itemParamHtml = this.itemService.queryItemParamById(itemId);
        mv.addObject("itemParam", itemParamHtml);
        
        return mv;
    }

}
