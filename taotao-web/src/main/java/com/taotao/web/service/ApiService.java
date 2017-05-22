package com.taotao.web.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.bean.HttpResult;

@Service
public class ApiService {
    
    @Autowired
    private CloseableHttpClient httpClient;
    
    @Autowired
    private RequestConfig requestConfig;
    
    public String doGet(String url) throws ClientProtocolException, IOException{
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }
        } finally{
            if(response != null){
                response.close();
            }
        }
        return null;
    }
    
    public String doGet(String url,Map<String,String> param) throws URISyntaxException, ClientProtocolException, IOException{
        URIBuilder builder = new URIBuilder(url);
        for(Map.Entry<String, String> entry:param.entrySet()){
            builder.addParameter(entry.getKey(),entry.getValue());
        }
        return doGet(builder.build().toString());
    }
    
    public HttpResult doPost(String url,Map<String,String> param) throws ParseException, IOException{
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if(null != param){
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for(Map.Entry<String, String> entry:param.entrySet()){
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(),"UTF-8"));
        } finally{
            if(response != null){
                response.close();
            }
        }
    }
    
    public HttpResult doPost(String url) throws ParseException, IOException{
        return this.doPost(url, null);
    }

}
