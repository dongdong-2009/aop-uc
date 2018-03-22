package com.casic.pub.util;

import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;

public class TokenUtil {
	public static Auth  auth = Auth.create("UVFsqUbvfPmloYwhmZDNiNCtgljg-nNabRiyL7ub", 
			 "A_kDdc6QAUrEcrA5vg0umS6giByQdvhg_fXMaKmw");
	 public static final Auth innoAuth = ConfigInno.innoAuth;
	    
	    //空间
	  public static final String bucket = ConfigInno.bucket;
	  public static final String bucketPrivate = ConfigInno.bucketPrivate;
	  public static final String bucketCk = ConfigInno.bucketCk;
	public  static String getUpToken0(){
	    return innoAuth.uploadToken(bucket);
	}
	
	public static String getUpTokenS(String key){
		return innoAuth.uploadToken(bucketCk, key);
	}
	public  static String getUpTokenCk(){
	    return innoAuth.uploadToken(bucketCk);
	}
	public static String getEncodedEntryURI(String bucket,String key){
		 
		return BucketManager.entry(bucket, key);
	}
	// 覆盖上传
	public static String getUpToken1(String key){
	    return innoAuth.uploadToken(bucket, key);
	}
	public static String getPrivateUrl(String url){
		return auth.privateDownloadUrl(url);
	}
	/*
	// 设置指定上传策略
	private String getUpToken2(){
	    return auth.uploadToken("bucket", null, 3600, new StringMap()
	         .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
	         .put("callbackBody", "key=$(key)&hash=$(etag)"));
	}

	// 设置预处理、去除非限定的策略字段
	private String getUpToken3(){
	    return auth.uploadToken("bucket", null, 3600, new StringMap()
	            .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
	            .putNotEmpty("persistentPipeline", ""), true);
	}*/

	public static void main(String[] args) {
		String token = getUpToken0();
		System.out.println(token);
	}
}
