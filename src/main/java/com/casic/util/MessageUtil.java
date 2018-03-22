package com.casic.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageUtil {

	 //发送系统消息http地址
   // private static String TOPIC = "http://localhost:8081/api/sysTopicSender.ht";
    private static String TOPIC = "http://mq.casicloud.com/api/sysTopicSender.ht";
    
    public static String sendSysTopic(String mqName,Object obj) throws IOException {
        Map<String, Object> mqparams = new HashMap<String, Object>();
        mqparams.put("mqName",mqName );
        mqparams.put("mqValue", obj.toString());
        mqparams.put("systemId","100");
        String res1 = "";
    	res1 = HttpClientUtil.JsonPostInvoke(TOPIC, mqparams);
    	System.out.println("调用子系统接口结果=" + res1);
    	return res1;
    }
}
