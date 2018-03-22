package com.casic.util;


import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public final class PropertiesUtils{
	//文件路径
	private static final String[] CFG_PATHS = new String[]{"/conf/chinapay.properties","/conf/sms.properties","/conf/merchantInfo.properties","/conf/app.properties"};
	//实例
	private static PropertiesUtils instance;

	private Resource resource = null;
	private Properties props = null;
	
	

	/*
	 * 刷新
	 */
	private synchronized void refresh() {
		if(null != props) {
			props.clear();
		}
		for(String path : CFG_PATHS ) {
			try {
				resource = new ClassPathResource(path);
				if(null == props) {
					props = PropertiesLoaderUtils.loadProperties(resource);
				} else {
					props.putAll(PropertiesLoaderUtils.loadProperties(resource));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 检查实例
	 */
	private static synchronized void checkInstance() {
		if(instance == null) {
			synchronized(CFG_PATHS) {
				if(instance == null) {
					instance = new PropertiesUtils();
				}
			}
		}
	}


	/**
	 *
	 * desc: 读取配置信息
	 * <p>创建人：唐贵斌 , 2014年5月25日 下午1:12:50</p>
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		checkInstance();
		return instance.props.getProperty(key);
	}

	/**
	 *
	 * desc: 修改配置信息
	 * <p>创建人：唐贵斌 , 2014年5月25日 下午1:12:50</p>
	 * @param key
	 * @return
	 */
	public static void setProperty(String key, String value) {
		checkInstance();
		instance.props.setProperty(key, value);
	}

	/**
	 *
	 * desc:读取配置信息
	 * <p>创建人：唐贵斌 , 2014年5月25日 下午1:13:06</p>
	 * @param key
	 * @param defStr
	 * @return
	 */
	public static String getProperty(String key, String defStr) {
		checkInstance();
		return instance.props.getProperty(key, defStr);
	}

	private PropertiesUtils() {
		super();
		refresh();
	}
	
	
}
