package com.taotao.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;

@Service
public class IndexService {
    @Value("${MANAGE_TAOTAO_URL}")
    private String MANAGE_TAOTAO_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;
    
    @Autowired
    private ApiService apiService;
    
    public static final ObjectMapper MAPPER = new ObjectMapper();
    
//    public String getIndexAd1(){
//        String url = MANAGE_TAOTAO_URL+INDEX_AD1_URL;
//        try {
//            String jsonData = apiService.doGet(url);
//            JsonNode jsonNode = MAPPER.readTree(jsonData);
//            ArrayNode arrayNode = (ArrayNode) jsonNode.get("rows");
//            List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
//            for(JsonNode node:arrayNode){
//                Map<String,Object> map = new LinkedHashMap<String, Object>();
//                map.put("srcB", node.get("pic").asText());
//                map.put("height", 240);
//                map.put("alt", node.get("title").asText());
//                map.put("width", 670);
//                map.put("src", map.get("srcB"));
//                map.put("widthB", 550);
//                map.put("href", node.get("url").asText());
//                map.put("heightB", 240);
//                result.add(map);
//            }
//            return MAPPER.writeValueAsString(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return null;
//    }
    
    /**改进后的方法*/
    public String getIndexAd1(){
        String url = MANAGE_TAOTAO_URL+INDEX_AD1_URL;
        try {
            String jsonData = apiService.doGet(url);
            
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            
            EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);
            List<Content> contents = (List<Content>) easyUIResult.getRows();
            List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
            for(Content content:contents){
                Map<String,Object> map = new LinkedHashMap<String, Object>();
                map.put("srcB", content.getPic());
                map.put("height", 240);
                map.put("alt", content.getTitle());
                map.put("width", 670);
                map.put("src", content.getPic());
                map.put("widthB", 550);
                map.put("href", content.getUrl());
                map.put("heightB", 240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
