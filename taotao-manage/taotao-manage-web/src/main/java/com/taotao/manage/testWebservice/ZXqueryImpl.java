package com.taotao.manage.testWebservice;

import javax.jws.WebService;

@WebService
public class ZXqueryImpl implements ZXquery{

    @Override
    public String getZXInfo(ResultInfo resultInfo) {
        System.out.println("服务端接收到数据！");
        return null;
    }

}
