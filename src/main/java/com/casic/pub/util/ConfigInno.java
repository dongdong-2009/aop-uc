package com.casic.pub.util;

import com.qiniu.util.Auth;

public final class ConfigInno {
    public static final Auth dummyAuth = Auth.create("abc", "1234567890");

 //innocasiclouds@163.com 7牛账号对应的密钥
    public static final Auth innoAuth = Auth.create(
            "UVFsqUbvfPmloYwhmZDNiNCtgljg-nNabRiyL7ub",
            "A_kDdc6QAUrEcrA5vg0umS6giByQdvhg_fXMaKmw");
    
    //空间
    
    //测试的空间
//   public static final String bucket = "ceshi";
    //生产的空间
    public static final String bucket = "casicloud";
    
    public static final String bucketPrivate = "privateclouds";
    public static final String bucketCk="chuangke";
    //需要覆盖的文件键值 key 可为当前项目文件全路径
    public static final String key = "/image/project/tempCaseHp.png";
    
    //域名
    public static final String domain = "file1.casicloud.com";

    private ConfigInno() {
    }

    public static boolean isTravis() {
        return "travis".equals(System.getenv("QINIU_TEST_ENV"));
    }
}
