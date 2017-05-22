package com.taotao.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@RequestMapping("content/category")
@Controller
public class ContentCategoryController {
    
    @Autowired
    private ContentCategoryService categoryService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryList(
            @RequestParam(value="id",defaultValue="0")Long parentId
            ){
        ContentCategory category = new ContentCategory();
        category.setParentId(parentId);
        try {
            List<ContentCategory> list = categoryService.queryByWhere(category);
            if(null == list || list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategroy(ContentCategory contentCategory){
        try {
            contentCategory.setStatus(1);
            contentCategory.setSortOrder(1);
            contentCategory.setIsParent(false);
            categoryService.save(contentCategory);
            ContentCategory parent = categoryService.queryById(contentCategory.getParentId());
            if(null != parent && !parent.getIsParent()){
                parent.setIsParent(true);
                categoryService.updateSelective(parent);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory){
        try {
            categoryService.updateSelective(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCategory (ContentCategory contentCategory){
        try {
            List<Object> ids = new ArrayList<Object>();
            ids.add(contentCategory.getId());
            this.findAllSubNode(contentCategory.getId(), ids);
            categoryService.deleteByIds(ids, "id", ContentCategory.class);
            ContentCategory parent = new ContentCategory();
            parent.setParentId(contentCategory.getId());
            List<ContentCategory> list = this.categoryService.queryByWhere(parent);
            if(null == list && list.size()>0){
                parent.setId(contentCategory.getParentId());
                parent.setIsParent(false);
                categoryService.updateSelective(parent);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    public void findAllSubNode(Long parentId,List<Object> ids){
        ContentCategory category = new ContentCategory();
        category.setParentId(parentId);
        List<ContentCategory> list = categoryService.queryByWhere(category);
        for(ContentCategory cg:list){
            ids.add(cg.getId());
            if(cg.getIsParent()){
                findAllSubNode(cg.getId(), ids);
            }
        }
    }

}
