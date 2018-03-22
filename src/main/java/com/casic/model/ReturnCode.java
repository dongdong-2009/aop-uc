package com.casic.model;
/**
 * 
 * @author ypchenga
 *
 */
public enum ReturnCode {
	
	SUCCESS(200,"请求成功"),
	REQUEST_PARAMETER_IS_EMPTY (201,"请求参数为空"),
	REQUEST_PARAMETER_EXCEPTION(202,"请求参数异常"),
	CALLBACK_INFORMATION(203,"接口调用回调信息"),
	UNKNOWN_ERROR(500, "未知错误异常");
	
	
    int code;
    String msg;

    private ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    };
    
    public static ReturnCode getInstance(int code) { 
    	ReturnCode[] codeArr = ReturnCode.values(); 
        for (ReturnCode rc : codeArr) { 
          if (rc.getCode() == code) { 
              return rc; 
          } 
        }
        throw new IllegalArgumentException("code值非法，没有符合该状态的枚举对象"); 
    } 
}
