package com.casic.model;

import java.util.HashMap;
import java.util.Map;

public class DataParam {
	private String systemId;
	private String uri;
	Map<String, Object> data = new HashMap<String, Object>();
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
