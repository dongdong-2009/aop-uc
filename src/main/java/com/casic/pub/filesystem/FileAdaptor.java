package com.casic.pub.filesystem;

/**
 * 
 */

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 文件共享存储适配器，天智网所有文件处理的读写接口
 * 无论是磁盘存储，Ftp存储，还是采用淘宝的ODS存储机制，都通过该接口进行读取
 * @author zouping 2014-11-01
 *
 */
public interface FileAdaptor {
	Logger logger = LoggerFactory.getLogger(FileAdaptor.class);
	
	/**
	 * 写文件
	 * @param file 目标文件
	 * @param filePath 文件存储相对路径（含文件名） 
	 */
	public void writeFile(File file, String filePath);
	
	
	/**
	 * 写文件
	 * @param is 目标文件流
	 * @param filePath 文件存储相对路径（含文件名） 
	 */
	public void writeFile(InputStream is, String filePath);
	
	/**
	 * 读文件
	 * @param filePath 文件读取路径（含文件名）
	 * @return
	 */
	public InputStream readFile(String filePath);

	/**
	 * 生成本地文件
	 * @param attUrl
	 * @param pdfPath
	 */
	public boolean downFile(String attUrl, String pdfPath);
	
}
