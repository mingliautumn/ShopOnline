package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


@Service
public class RedisService {
    
    @Autowired(required=false)
    private ShardedJedisPool  shardedJedisPool;
    
    private <T> T execute(Function<ShardedJedis,T> fun){
     // 定义集群连接池
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();

            return fun.callback(shardedJedis);
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
    }
    
    /**设置值*/
    public String set(final String key,final String value){

        return this.execute(new Function<ShardedJedis,String>(){

            @Override
            public String callback(ShardedJedis shardedJedis) {
               return shardedJedis.set(key, value);
            }
            
        });
    }
    
    /**获取值*/
    public String get(final String key){
        return this.execute(new Function<ShardedJedis,String>(){

            @Override
            public String callback(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
            
        });
    }
    
   /**删除*/
    public Long del(final String key){
        return this.execute(new Function<ShardedJedis,Long>(){

            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
            
        });
    }
    
    /**设置生存时间*/
    public Long expire(final String key,final Integer seconds){
        return this.execute(new Function<ShardedJedis,Long>(){

            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }
            
        });
    }
    
    /**设置值以及生存时间，单位为妙*/
    public String set(final String key,final String value,final Integer seconds){
        return this.execute(new Function<ShardedJedis, String>() {

            @Override
            public String callback(ShardedJedis shardedJedis) {
                String result = shardedJedis.set(key, value);
                shardedJedis.expire(result, seconds);
                return result;
            }
        });
    }
}
