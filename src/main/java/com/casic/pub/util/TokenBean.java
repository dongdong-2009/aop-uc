package com.casic.pub.util;

import java.io.Serializable;

public class TokenBean implements Serializable{

	private String  uploadToken;
	private String  key;
	private String bucket;
	private String downloadToken;
	public String getDownloadToken() {
		return downloadToken;
	}
	public void setDownloadToken(String downloadToken) {
		this.downloadToken = downloadToken;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getUploadToken() {
		return uploadToken;
	}
	public void setUploadToken(String uploadToken) {
		this.uploadToken = uploadToken;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
