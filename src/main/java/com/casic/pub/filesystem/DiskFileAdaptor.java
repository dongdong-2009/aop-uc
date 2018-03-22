package com.casic.pub.filesystem;

/**
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.Test;

import com.appleframe.utils.common.ObjectUtils;
import com.appleframe.utils.file.FileUtils;
import com.casic.pub.filesystem.FileAdaptor;
import com.hotent.core.util.AppUtil;

/**
 * 
 * 磁盘系统共享存储
 * @author zouping 2014-11-01
 *
 */
public class DiskFileAdaptor implements FileAdaptor{
	public static String DEFAULT_ROOT_DIR = "D:/tian_file";
	public static String root;
	public static Properties properties;
	
	public DiskFileAdaptor(){
		if(properties==null){
			try {
				File file = org.springframework.util.ResourceUtils.getFile("classpath:conf/system.properties");
				properties = new Properties();
				properties.load(new FileInputStream(file));
				root = ObjectUtils.getString(properties.get("file.dir"), DEFAULT_ROOT_DIR);
			} catch (FileNotFoundException e) {
				logger.error("{}文件不存在", "classpath:/conf/system.properties");
			} catch (IOException e) {
				logger.error("{}文件不存在", "classpath:/conf/system.properties");
			}
		}
	}
	
	/**
	 * 根据相对路径获取文件绝对路径
	 * @param path
	 * @return
	 */
	public String getAbsPath(String relatePath){
		//文件绝对路径,没有则创建
		String[] paths = relatePath.split("\\\\|/");
		String tmp = root;
		for(int i=0;i<paths.length-1;i++){
			tmp = tmp  + "/" + paths[i];
			if(!FileUtils.exists(new File(tmp))){
				FileUtils.mkdirs(tmp);
			}
		}
		String filePath =  root + "/" + relatePath;
		return filePath;
	}
	
	/**
	 * 写文件
	 * @param file
	 * @param path
	 */
	public void writeFile(File file, String filePath){
		if(file == null)
			return;
		
		if(!FileUtils.exists(file)){
			logger.error("{}文件不存在", file.getAbsolutePath());
		}
		
		FileUtils.copyFile(file, new File(getAbsPath(filePath)));
	}
	
	/**
	 * 读文件
	 * @param filePath
	 * @return
	 */
	public InputStream readFile(String filePath){
		try {
			return new FileInputStream(new File(root + "/" + filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	public void testMkDir(){
		DiskFileAdaptor file = new DiskFileAdaptor();
		String filePath = "upload\\800001/test.png";
		file.writeFile(new File("D:\\tianzhi_file/upload\\800001/800001_140905141345.png"), filePath);
	}

	@Override
	public void writeFile(InputStream zis, String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean downFile(String attUrl, String pdfPath) {
		return false;
	}
}
