package com.taotao.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    private static Logger logger = LoggerFactory.getLogger(ItemController.class);
    
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ResponseEntity<Item> queryItemById(@PathVariable("itemId")Long itemId){
        
        try {
            Item item =  this.itemService.queryById(itemId);
            System.out.println("====================================");
            if(null == item){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item,@RequestParam("desc")String desc,
            @RequestParam("itemParams")String itemParams
            ){
        
        try {
            if(logger.isDebugEnabled()){
                logger.debug("开始保存数据：item = {}",item.toString());
            }
            itemService.saveItem(item,desc,itemParams);
            if(logger.isDebugEnabled()){
                logger.debug("保存数据成功：id = {}",item.getId());
            }
            //创建成功，放回201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value="page",defaultValue="1")Integer page,
            @RequestParam(value="rows",defaultValue="30")Integer rows
            ){
        try {
            PageInfo<Item> pageInfo = this.itemService.queryItemList(page, rows);
            return ResponseEntity.ok(new EasyUIResult(pageInfo.getTotal(), pageInfo.getList()));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 
     * 逻辑删除
     * */
    @RequestMapping(method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteItemsByIds(@RequestParam("ids")List<Object> ids){
        try {
            this.itemService.updateByIds(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    /**
     * 更新
     * 
     * */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc")String desc,
            @RequestParam("itemParams")String itemParams,
            @RequestParam("itemParamId")Long itemParamId
            ){
        
        try {
            if(logger.isDebugEnabled()){
                logger.debug("开始更新数据：item = {}",item.toString());
            }
            
            ItemParamItem itemParamItem = null;
            if(null != itemParams){
                itemParamItem = new ItemParamItem();
                itemParamItem.setParamData(itemParams);
                itemParamItem.setId(itemParamId);
            }
            
            itemService.updateItem(item,desc,itemParamItem);
            if(logger.isDebugEnabled()){
                logger.debug("更新数据成功：id = {}",item.getId());
            }
            //创建成功，放回201
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
