package com.taotao.manage.controller;

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
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamServece;

@RequestMapping("item/param")
@Controller
public class ItemParamController {
    
    @Autowired
    private ItemParamServece itemParamService;
    
    /**
     * 查找模板
     * */
    @RequestMapping(value="{itemCatId}",method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryByitemCatId(@PathVariable("itemCatId")Long itemCatId){
        
        try {
            ItemParam param  = new ItemParam();
            param.setItemCatId(itemCatId);
            ItemParam itemParam = this.itemParamService.queryOne(param);
            if(null == itemParam){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParam);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 保存模板
     * */
    @RequestMapping(value="{itemCatId}",method=RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId")Long itemCatId,@RequestParam("paramData") String paramData){
        
        try {
            ItemParam param  = new ItemParam();
            param.setItemCatId(itemCatId);
            param.setParamData(paramData);
            this.itemParamService.save(param);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**显示列表*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryList(
            @RequestParam(value="page",defaultValue="1")Integer page,
            @RequestParam(value="rows",defaultValue="10")Integer rows
            ){
        
        try {
            PageInfo<ItemParam> pageInfo = this.itemParamService.queryPageListByWhere(null, page, rows);
            return ResponseEntity.ok(new EasyUIResult(pageInfo.getTotal(),pageInfo.getList()));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
