package com.taotao.web.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

public class IdleConnectionEvictor extends Thread{
    
    private final HttpClientConnectionManager connMgr;
    
    private volatile boolean shutdown;
    
    public IdleConnectionEvictor(HttpClientConnectionManager connMgr){
        this.connMgr=connMgr;
        this.start();//启动当前线程
    }
    
    @Override
    public void run(){
        try {
            while(!shutdown){
                synchronized(this){
                    wait(5000);
                    connMgr.closeExpiredConnections();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void shutdown(){
        shutdown=true;
        synchronized (this) {
            notifyAll();
        }
    }

}
