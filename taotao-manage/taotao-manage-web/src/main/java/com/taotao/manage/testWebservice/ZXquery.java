package com.taotao.manage.testWebservice;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(
        name="ZXquery",
        serviceName="ZXqueryService",
        portName="ZXqueryPort",
        targetNamespace="http://com.cupdata.uc/"
        )
public interface ZXquery {
    
    @WebMethod(operationName="getZXquert")
    public @WebResult(name="result")String getZXInfo(ResultInfo resultInfo);

}
