package com.casic.util;

import java.io.*;
import java.net.*;
 
public class GetURLData {
  public static String stringSendGet(String url,String param)
    {
      String result="";
      String urlName="";
      try {
          urlName=url+param;
            URL U=new URL(urlName);
            URLConnection connection=U.openConnection();
            connection.connect();
            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while((line=in.readLine())!=null)
            {
                result+=line;
            }
                in.close();
         
        }catch (Exception e) {
                System.out.println("与服务器连接发生异常错误:"+e.toString());
                System.out.println("连接地址是:"+urlName);
        }
        return result;
      }
       
      public static void main(String[] args){
          String s = GetURLData.stringSendGet("http://www.iec365.com:8080/ecweb/htyunact/user/GetUserInfo.do?method=getUserById&user_id=nmdxdz","");
          String s1 = GetURLData.stringSendGet("http://www.iec365.com:8080/ecweb/htyun/GetUserInfo.do?method=addUser&USER_ID=nmdxdz","");
          System.out.println(s);
          System.out.println(s1);
      }
}