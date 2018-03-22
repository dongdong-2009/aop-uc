package com.casic.pub.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.ResourceUtils;

import sun.net.TelnetInputStream;


/**
 * 实现FTP 客户端的各种操作
 *
 * @commons-net：http://apache.mirror.phpchina.com/commons/net/binaries/commons-net-1.4.1.zip
 * @jakarta-oro：http://mirror.vmmatrix.net/apache/jakarta/oro/source/jakarta-oro-2.0.8.zip
 * @commons-io：http://apache.mirror.phpchina.com/commons/io/binaries/commons-io-1.3.2-bin.zip
 *
 * @author 
 * @version 2008-06-10 Ftp.java
 *
 */
public class FtpFileUtil {

    private static Log logger;
    /**
     * FTP 登录用户名
     */
    private static String userName;
    /**
     * FTP 登录密码
     */
    private static String password;
    /**
     * FTP 服务器地址IP地址
     */
    private static String ip;
    /**
     * FTP 端口
     */
    private static int port;
    /**
     * 属性集
     */
    private static Properties property = null;
    /**
     * 配置文件的路径名
     */
    private static String configFile = "classpath:conf/ftp.properties";
    /**
     * FTP 客户端代理
     */
    private static FTPClient ftpClient = null;
    /**
     * 时间格式化
     */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    /**
     * FTP
     */
    private static final String[] FILE_TYPES = { "文件", "目录", "符号链接", "未知类型" };
    /**
     * 传输模式为二进制文件.
     */
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    /**
     * 传输模式为ASCII，默认为ASCII
     */
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;
    public  static int  i=1;
    
	public static void main(String[] args) {
        connectServer();
        setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
        uploadFile("c:/logo.gif","upload/800001/logo.gif");
        closeConnect();// 关闭连接
    }

	/**
     * 上传单个文件，并重命名
     *
     * @param localFilePath--本地文件路径
     * @param newFileName--新的文件名，含目标路径,可以命名为空""
     * @return true 上传成功，false 上传失败
     */
    public static boolean uploadFile(File localFile, String newFileName) {
        boolean flag = true;
        try {
            connectServer();
            ftpClient.setFileType(BINARY_FILE_TYPE);
            // ftp.setFileType(FTP.ASCII_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            // ftp.changeWorkingDirectory(remoteDir);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            File newFile = new File(newFileName);
            String dir = newFile.getParentFile().getPath();
            //逐级进入ftp目录
            gotoWorkDirectory(dir);
            InputStream input = new FileInputStream(localFile);
            if (input == null) {
                logger.debug("本地文件不存在,请重新选择！");
            }
            if(newFileName.trim().equals("")){
            	newFileName = localFile.getName();
            }
            String fileName = newFileName;
            int i = fileName.lastIndexOf(File.separator)==-1?fileName.lastIndexOf("/"):fileName.lastIndexOf(File.separator);
            if(i!=0){
            	fileName = fileName.substring(i + 1);
            }
            flag = ftpClient.storeFile(fileName, input);
            if (flag) {
                System.out.println("upload File succeed");
            } else {
                System.out.println("upload File false");
            }
            input.close();

        } catch (IOException e) {
            logger.debug("本地文件上传失败！", e);
        } catch (Exception e) {
            logger.debug("本地文件上传失败！", e);
        }
        return flag;
    }
    
    /**
     * 上传单个文件，并重命名
     *
     * @param localFilePath--本地文件路径
     * @param newFileName--新的文件名,可以命名为空""
     * @return true 上传成功，false 上传失败
     */
    public static boolean uploadFile(String localFile, String newFileName) {
        return uploadFile(new File(localFile), newFileName);
    }

    /**
     * 上传多个文件
     *
     * @param localFilePath--本地文件夹路径
     * @return true 上传成功，false 上传失败
     */
    public static String uploadManyFile(String localFile) {
        boolean flag = true;
        StringBuffer strBuf = new StringBuffer();
        int n = 0;
        try {
            connectServer();
            File file = new File(localFile);// 在此目录中找文件

            File file2[] = file.listFiles();

            for (int i = 0; i < file2.length; i++ ) {
                File file1 = new File(file2[i].getAbsolutePath());
                if (file1.isDirectory()) {// 文件夹中还有文件夹
                    uploadManyFile(file2[i].getAbsolutePath());
                } else {
                    flag = uploadFile(file2[i].getAbsolutePath(), "");
                }
                if (!flag) {
                    n++ ;
                    strBuf.append(file2[i].getName() + "\r\n");

                }
            }
            if (n > 0) {
                strBuf.append("共有" + n + "上传失败，分别为\r\n");
            }
            System.out.println(strBuf.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.debug("本地文件上传失败！找不到上传文件！", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("本地文件上传失败！", e);
        }
        return strBuf.toString();
    }

    /**
     * 上传多个文件
     *
     * @param localFilePath--本地文件夹路径
     * @param newFileName--目标路径
     * @return true 上传成功，false 上传失败
     */
    public static String uploadManyFile(String localFile, String newFileName) {
        boolean flag = true;
        StringBuffer strBuf = new StringBuffer();
        int n = 0;
        try {
            connectServer();
            File file = new File(localFile);// 在此目录中找文件

            File file2[] = file.listFiles();

            for (int i = 0; i < file2.length; i++ ) {

                File file1 = new File(file2[i].getAbsolutePath());
                System.out.println(file1.isFile());
                if (file1.isDirectory()) {// 文件夹中还有文件夹

                    uploadManyFile(file2[i].getAbsolutePath(), newFileName);
                } else {
                    String tmpNewFileName = "";
                    if (newFileName.substring(newFileName.length() - 1).equals(
                            "/")) {

                        tmpNewFileName = newFileName + "/" + file2[i].getName();
                    } else {

                        tmpNewFileName = newFileName + "/" + file2[i].getName();
                    }
                    System.out.println(tmpNewFileName);
                    flag = uploadFile(file2[i].getAbsolutePath(),
                            tmpNewFileName);
                }
                if (!flag) {
                    n ++;
                    strBuf.append(file2[i].getName() + "\r\n");

                }
            }
            if (n > 0) {
                strBuf.append("共有" + n + "上传失败，分别为\r\n");
            }
            System.out.println(strBuf.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.debug("本地文件上传失败！找不到上传文件！", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("本地文件上传失败！", e);
        }
        return strBuf.toString();
    }

    /**
     * 下载文件
     *
     * @param remoteFileName
     *            --服务器上的文件名
     * @param localFileName--本地文件名
     * @return true 下载成功，false 下载失败
     *
     */
    public static boolean loadFile(String remoteFileName, String localFileName) {
        boolean flag = true;
        // 下载文件
        BufferedOutputStream buffOut = null;
        try {
        	connectServer();
            buffOut = new BufferedOutputStream(new FileOutputStream(
                    localFileName));
            flag = ftpClient.retrieveFile(remoteFileName, buffOut);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("本地文件下载失败！", e);
        } finally {
            try {
                if (buffOut != null)
                    buffOut.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return flag;
    }

    /**
     * 删除一个文件
     */
    public static boolean deleteFile(String filename) {
        boolean flag = true;
        try {
            connectServer();

            flag = ftpClient.deleteFile(filename);
            if (flag) {
                System.out.println("delete  File succeed");

            } else {
                System.out.println("delete File false");

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除目录
     */
    public static void deleteDirectory(String pathname) {
        try {
            connectServer();
            File file = new File(pathname);
            if (file.isDirectory()) {
                File file2[] = file.listFiles();
            } else {
                deleteFile(pathname);

            }
            ftpClient.removeDirectory(pathname);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 删除空目录
     */
    public static void deleteEmptyDirectory(String pathname) {
        try {
            connectServer();
            ftpClient.removeDirectory(pathname);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 列出服务器上文件和目录
     *
     * @param regStr
     *            --匹配的正则表达式
     */
    @SuppressWarnings("unchecked")
    public static void listRemoteFiles(String regStr) {
        try {
        	connectServer();
            // FtpClient.changeWorkingDirectory(regStr);
            String files[] = ftpClient.listNames(regStr);
            if (files == null || files.length == 0)
                System.out.println("There has not any file!");
            else {
                for (int i = 0; i < files.length; i++ ) {
                    System.out.println(files[i]);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出Ftp服务器上的所有文件和目录
     *
     */
    public static void listRemoteAllFiles() {
        try {
        	connectServer();
            String[] names = ftpClient.listNames();
            for (int i = 0; i < names.length; i++ ) {
                System.out.println(names[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public static void closeConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置配置文件
     *
     * @param configFile
     */
    public static void setConfigFile(String configFile) {
        FtpFileUtil.configFile = configFile;
    }

    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     *
     * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    public static void setFileType(int fileType) {
        try {
            connectServer();
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扩展使用
     *
     * @return
     */
    protected static FTPClient getFtpClient() {
        connectServer();
        return ftpClient;
    }

    /**
     * 设置参数
     *
     * @param configFile
     *            --参数的配置文件
     */
    private static void setArg(String configFile) {
    	if(property==null){
	        property = new Properties();
	        BufferedInputStream inBuff = null;
	        try {
	            File file = ResourceUtils.getFile(configFile);	            		
	            inBuff = new BufferedInputStream(new FileInputStream(file));
	            property.load(inBuff);
	            userName = property.getProperty("username");
	            password = property.getProperty("password");
	            ip = property.getProperty("ip");
	            port = Integer.parseInt(property.getProperty("port"));
	        } catch (FileNotFoundException e1) {
	            System.out.println("配置文件 【" +configFile +"】不存在！");
	            e1.printStackTrace();
	        } catch (IOException e) {
	            System.out.println("配置文件 【" +configFile+ "】无法读取！");
	            e.printStackTrace();
	        }
    	}
    }
    
    /**
     * 连接到服务器
     *
     * @return true 连接服务器成功，false 连接服务器失败
     */
    public static boolean connectServer() {
        boolean flag = true;
        if (ftpClient == null) {
        	int reply;
            try {
                setArg(configFile);
                ftpClient = new FTPClient();
                ftpClient.setControlEncoding("GBK");
                ftpClient.setDefaultPort(port);
                ftpClient.configure(getFtpConfig());
                ftpClient.connect(ip);
                ftpClient.login(userName, password);
                ftpClient.setDefaultPort(port);
                reply = ftpClient.getReplyCode();
                ftpClient.setDataTimeout(120000);
                
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE,FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode(); 

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    System.err.println("FTP server refused connection.");
                    flag = false;
                }
            } catch (SocketException e) {
                flag = false;
                e.printStackTrace();
                System.err.println("登录ftp服务器【" +ip+ "】失败,连接超时！");
            } catch (IOException e) {
                flag = false;

                e.printStackTrace();
                System.err.println("登录ftp服务器【"+ ip +"】失败，FTP服务器无法打开！");
            }
        }
        if(ftpClient != null){
	        try{
	        	ftpClient.setFileType(BINARY_FILE_TYPE);
	        }catch(Exception ne){
	        	int reply;
	            try {
	                setArg(configFile);
	                ftpClient = new FTPClient();
	                ftpClient.setControlEncoding("GBK");
	                ftpClient.setDefaultPort(port);
	                //ftpClient.configure(getFtpConfig());
	                ftpClient.connect(ip);
	                ftpClient.login(userName, password);
	                ftpClient.setDefaultPort(port);
	                
	                ftpClient.setFileType(FTP.BINARY_FILE_TYPE,FTP.BINARY_FILE_TYPE);
	                ftpClient.enterLocalPassiveMode(); 
	                //System.out.print(FtpClient.getReplyString());
	                reply = ftpClient.getReplyCode();
	                ftpClient.setDataTimeout(120000);
	
	                if (!FTPReply.isPositiveCompletion(reply)) {
	                    ftpClient.disconnect();
	                    System.err.println("FTP server refused connection.");
	                    flag = false;
	                }
	            } catch (SocketException e) {
	                flag = false;
	                e.printStackTrace();
	                System.err.println("登录ftp服务器【" +ip+ "】失败,连接超时！");
	            } catch (IOException e) {
	                flag = false;
	                e.printStackTrace();
	                System.err.println("登录ftp服务器【"+ ip +"】失败，FTP服务器无法打开！");
	            }
	        }
        }
        return flag;
    }

    /**
     * 进入到服务器的某个目录下
     *
     * @param directory
     */
    public static void changeWorkingDirectory(String directory) {
        try {
            connectServer();
            ftpClient.changeWorkingDirectory(directory);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 返回到上一层目录
     */
    public static void changeToParentDirectory() {
        try {
            connectServer();
            ftpClient.changeToParentDirectory();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 重命名文件
     *
     * @param oldFileName
     *            --原文件名
     * @param newFileName
     *            --新文件名
     */
    public static void renameFile(String oldFileName, String newFileName) {
        try {
            connectServer();
            ftpClient.rename(oldFileName, newFileName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 设置FTP客服端的配置--一般可以不设置
     *
     * @return
     */
    private static FTPClientConfig getFtpConfig() {
        FTPClientConfig ftpConfig = new FTPClientConfig(
                FTPClientConfig.SYST_UNIX);
        ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        return ftpConfig;
    }

    /**
     * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
     *
     * @param obj
     * @return
     */
    private static String iso8859togbk(Object obj) {
        try {
            if (obj == null)
                return "";
            else
                return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 进入ftp服务器目录
     * @param dir
     * @return
     */
    public static boolean gotoWorkDirectory(String dir){
    	connectServer();
        boolean flag = true;
        try {
            // System.out.println("dir=======" dir);
        	int i = -1;
        	String tmpDir = "";
        	while((i = dir.indexOf(File.separator))!=-1 || (i = dir.indexOf("/"))!=-1){
        		String parentDir = dir.substring(0, i);
        		if(parentDir!=null && !parentDir.isEmpty()){
        			tmpDir = tmpDir + File.separator + parentDir;
        			flag = ftpClient.makeDirectory(parentDir);
        			ftpClient.changeWorkingDirectory(tmpDir);
        		}
        		dir = dir.substring(i + 1);
        	}
        	if(!dir.isEmpty()){
        		tmpDir = tmpDir + File.separator + dir;
        		flag = ftpClient.makeDirectory(dir);
        		ftpClient.changeWorkingDirectory(tmpDir);
        	}
            if (flag) {
                System.out.println("make Directory " +dir +" succeed");
            } else {
                System.out.println("make Directory " +dir+ " false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir
     *            文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public static boolean makeDirectory(String dir) {
        connectServer();
        boolean flag = true;
        try {
            // System.out.println("dir=======" dir);
        		flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("make Directory " +dir +" succeed");

            } else {

                System.out.println("make Directory " +dir+ " false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		FtpFileUtil.logger = logger;
	}
	
	/**
	 * FTP文件下载 返回InputStream
	 * @param SourceFileName
	 * @param destinationFileName
	 * @return
	 * @throws Exception
	 */
	public static InputStream downFile(String sourceFileName) throws Exception {
		connectServer();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
        ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
		InputStream is = ftpClient.retrieveFileStream(sourceFileName);
		return is;
	}
	
	/**
	 * FTP文件下载 返回InputStream
	 * @param SourceFileName
	 * @param destinationFileName
	 * @return
	 * @throws Exception
	 */
	public static boolean readFile(String sourceFileName, String targetPath) throws Exception {
		boolean flag = false;
		try {
			OutputStream out = new FileOutputStream(targetPath);  
			ftpClient.retrieveFile(sourceFileName, out);
			flag = true;
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 上传文件
	 * @param is 原文件流
	 * @param filePath 新文件地址名称
	 * @return
	 */
	public static boolean uploadFile(InputStream is, String filePath) {
		boolean flag = true;
        try {
            connectServer();
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            
            // ftp.setFileType(FTP.ASCII_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            // ftp.changeWorkingDirectory(remoteDir);
            File newFile = new File(filePath);
            String dir = newFile.getParentFile().getPath();
            //逐级进入ftp目录
            gotoWorkDirectory(dir);
            flag = ftpClient.storeFile(filePath, is);
            if (flag) {
                System.out.println("upload File succeed");
            } else {
                System.out.println("upload File false");
            }
            is.close();

        } catch (IOException e) {
            logger.debug("本地文件上传失败！", e);
        } catch (Exception e) {
            logger.debug("本地文件上传失败！", e);
        }
        return flag;
		
	}

	public static boolean readFile(String remotePath, OutputStream out) {
		boolean flag = false;
		try {
			ftpClient.retrieveFile(remotePath, out);
			flag = true;
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


}
