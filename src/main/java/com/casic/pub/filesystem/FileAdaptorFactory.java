package com.casic.pub.filesystem;

import java.util.Properties;

import com.appleframe.utils.common.ObjectUtils;
import com.hotent.core.util.AppUtil;

/**
 * 
 * 文件适配器工厂，决定产生哪种文件适配器
 * @author zouping 2014-11-01
 *
 */
public class FileAdaptorFactory {
	
	/**
	 * 返回默认适配器,根据配置文件读取是那个适配器
	 * @return
	 */
	public static FileAdaptor getAdaptor(){
		//默认为disk适配器
		Properties properties = (Properties)AppUtil.getBean("systemproperties");
		String adaptor = ObjectUtils.getString(properties.get("file.adaptor"),"disk");
		if(adaptor.equals("disk"))
			return new DiskFileAdaptor(); 
		else if(adaptor.equals("ftp"))
			return new FTPFileAdaptor();
		
		return null;
	}
	
	/**
	 * 返回默认适配器,根据配置文件读取是那个适配器
	 * @return
	 */
	public static FileAdaptor getAdaptor(String name){
		if(name.equals("disk"))
			return new DiskFileAdaptor(); 
		else if(name.equals("ftp"))
			return new FTPFileAdaptor();
		
		return null;
	}
}
