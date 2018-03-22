/**
 * 2017年11月6日OperationStatic.java下午6:24:35aop-uc-internetOperationStaticTODO
 */
package com.casic.util;

/**
 * @author Administrator
 *OperationStatic$
 */
public enum OperationStatic {
	delete("删除",3),
	update("修改",2),
	add("增加",1);
	private OperationStatic() {
	}
	private OperationStatic(String name,int code) {
		this.code = code;
		this.name = name;
	}
	private int code;
	private String name;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}





