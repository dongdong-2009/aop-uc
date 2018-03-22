package com.casic.pub.filesystem;

/**
 * 
 */

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.casic.pub.filesystem.FileAdaptor;
import com.casic.pub.util.FtpFileUtil;

/**
 * 
 * FTP系统共享存储
 * @author zouping 2014-11-01
 *
 */
public class FTPFileAdaptor implements FileAdaptor{
	/**
	 * 写文件
	 * @param file
	 * @param path
	 */
	public void writeFile(File file, String filePath){
		FtpFileUtil.connectServer();
		FtpFileUtil.uploadFile(file, filePath);
		FtpFileUtil.closeConnect();
	}
	
	/**
	 * 读文件
	 * @param filePath
	 * @return
	 */
	public InputStream readFile(String filePath){
		try {
			FtpFileUtil.connectServer();
			InputStream in = FtpFileUtil.downFile(filePath);
			FtpFileUtil.closeConnect();
			return in;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读文件
	 * @param filePath
	 * @return
	 */
	public boolean downFile(String remotePath, String filePath){
		boolean flag = true;
		try {
			FtpFileUtil.connectServer();
			flag = FtpFileUtil.readFile(remotePath, filePath);
			FtpFileUtil.closeConnect();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	@Override
	public void writeFile(InputStream is, String filePath) {
		FtpFileUtil.connectServer();
		FtpFileUtil.uploadFile(is, filePath);
		FtpFileUtil.closeConnect();
	}
}
