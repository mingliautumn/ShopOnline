package com.taotao.common.bean;

public class HttpResult {
    
    private Integer statusCode;
    private String data;
    
    public HttpResult(){
        
    }
    
    public HttpResult(Integer statusCode, String data) {
        super();
        this.statusCode = statusCode;
        this.data = data;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    
    

}
