package com.casic.util;

public enum EnumUtils {
	NOVALID("验证不通过","0000"),
	OVERlENGTH("邮箱长度（5-30）不正确","0001"),
	OVERlENGTH1("邮箱重复","00011"),
	NAMECHEAPT("企业名重复","0002"),
	MOBILECHEAPT("手机号重复","0003"),
	NameEmpty("企业名不能为空","0004"),
	SUCCESS("验证通过","0005"),
	SPECIAL("非数字","0006"),
	INVICODE("邀请码不存在","0007"),
	NOTEXIST("不存在","0009"),
	EXIST("不存在","00010"),
	NotEmpty("不能为空","0008");
	
	
	
	private EnumUtils(String name, String code) {
		this.name = name;
		this.code = code;
	}
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}