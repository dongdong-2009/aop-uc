package com.casic.pub.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.appleframe.utils.date.DateUtils;
import com.appleframe.utils.file.FileUtils;
import com.appleframe.utils.web.RequestUtils;
import com.casic.pub.filesystem.FileAdaptor;
import com.casic.pub.filesystem.FileAdaptorFactory;
import com.casic.pub.util.CompressPicUtil;
import com.casic.pub.util.RandomCode;
import com.casic.pub.util.TokenBean;
import com.casic.pub.util.TokenUtil;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;

import net.sf.json.JSONObject;

/**
 * 上传文件的控制器类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/pub/image")
public class ImageUploadController extends BaseController{
	//100K
	public static long FILE_MAX_SIZE = 100 * 1024;
	//压缩比例
	public static float FILE_COMPRESS_SCAL = 1.1F;
	
	/**
	 * 文件子文件存储路径
	 */
	public static String FILE_PATH_CHILD_DIR = "upload";
	
	@Resource
	private Properties configproperties;
	
	@RequestMapping("toUpload")
	public ModelAndView toUpload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnUrl = RequestUtil.getPrePage(request);
		String width = RequestUtil.getString(request, "width");
		return getAutoView().addObject("returnUrl", returnUrl).addObject("width", width)
							.addObject("tenantId", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
	};
	/*@RequestMapping("toCloudUpload")
	public ModelAndView toCloudUpload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long tenantId = 3340301l;
		//Long tenantId =  ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		//String returnUrl = RequestUtil.getPrePage(request);
		String returnUrl = request.getHeader("Referer");
		Boolean isSingle = RequestUtil.getBoolean(request, "isSingle",false);
		String viewName = "/pub/imageToCloudUpload.jsp";
		if(isSingle){
			viewName = "/pub/imageToCloudUploadSingle.jsp";
		}else{
			viewName = "/pub/imageToCloudUpload.jsp";
		}
		ModelAndView mv = new ModelAndView(viewName);
		return mv.addObject("returnUrl", returnUrl)
				.addObject("_callback",request.getParameter("_callback"))
				.addObject("widths",request.getParameter("widths"))
				.addObject("tenantId", tenantId);
	};*/
	
	@RequestMapping("toCloudUpload")
	@Action(description = "外协→返回上传图片的弹窗页。示例：用户在新建需求等有图片上传功能的页面上，点击添加图片时触发此方法")
	public ModelAndView toCloudUpload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnUrl = RequestUtil.getPrePage(request);
		Boolean isSingle = RequestUtil.getBoolean(request, "isSingle",false);
		String pageType = RequestUtil.getString(request, "pageType");
		String viewName = "/pub/imageToCloudUpload.jsp";
		if(isSingle){
			viewName = "/pub/imageToCloudUploadSingle.jsp";
		}else{
			viewName = "/pub/imageToCloudUpload.jsp";
		}
		ModelAndView mv = new ModelAndView(viewName);
		return mv.addObject("returnUrl", returnUrl)
				.addObject("_callback",request.getParameter("_callback"))
				.addObject("widths",request.getParameter("widths"))
				.addObject("tenantId", ContextUtil.getCurrentTenantId())
				.addObject("pageType", pageType);
	};
	
	
	@RequestMapping("getToken")
	@ResponseBody
	@Action(description = "外协→生成token和key。示例：生成上传附件、图片的弹窗页后，自动请求该接口，用于与千牛云的上传文件验证")
	public JSONObject getToken(HttpServletRequest request){
		TokenBean tb=new TokenBean();
		tb.setUploadToken(TokenUtil.getUpToken0());
		tb.setKey(RandomCode.g());
		return JSONObject.fromObject(tb);
	}
	
	@RequestMapping("toUploadFile")
	public ModelAndView toUploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String returnUrl=RequestUtil.getPrePage(request);
		return getAutoView().addObject("returnUrl", returnUrl)
				.addObject("tenantId", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
	};
	
	@RequestMapping("upload")
	public ModelAndView upload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		
		//企业Id
		String tenantId = RequestUtils.getString(request, "tenantId", "nosession");
		String widths = RequestUtils.getString(request, "width");
		String resultMsg = "文件上传成功";
		ServletContext sc = request.getSession().getServletContext();
		
		/**
		String parentPath = "/" + FILE_PATH_CHILD_DIR;//文件存放父级目录
		String fileDirSet = configproperties.getProperty("fileDir");
		if( fileDirSet!= null){//存放绝对路径,D:/tianzhi_files/upload/430001
			String rootPath = fileDirSet + "/" + FILE_PATH_CHILD_DIR;
			parentPath = rootPath + "/" + tenantId;
		}else{//存放在项目文件夹,web/upload/430001 
			parentPath = sc.getRealPath(FILE_PATH_CHILD_DIR + "/" + tenantId);
		}
		File parentFile = new File(parentPath);
		if(!parentFile.exists()){
			parentFile.mkdir();
		}**/
		
		//图片相对目录
		String parentDir =  FILE_PATH_CHILD_DIR + File.separator + tenantId;
		
		//图片存放临时目录
		String tmpPath = FILE_PATH_CHILD_DIR + File.separator + "tmp";
		if(!FileUtils.exists(new File(tmpPath))){
			FileUtils.mkdirs(tmpPath);
		}
		
		//文件名
		String originalFilename = file.getOriginalFilename();
		String destFilename= transFileNameByTime(tenantId, originalFilename);
		
		//图片路径
		String filePath = parentDir + File.separator + destFilename;
		
		try{		
			//存放到临时路径				
			String tmpFilePath = tmpPath + File.separator + destFilename;
			saveFileFromInputStream(file.getInputStream(), tmpFilePath);
			File tmpFile = new File(tmpFilePath);
			BufferedImage bi = null;
			bi = ImageIO.read(tmpFile);
			
			int width = bi.getWidth(); // 像素
			int height = bi.getHeight(); // 像素
			int nweWidth = bi.getHeight(); // 像素
			int newHeight = bi.getHeight(); // 像素
			
			//遍历压缩
			if(width >400 || height > 480){
				if(widths != "400" ){//当为富文本编辑器上传图片时
					// 为等比缩放计算输出的图片宽度及高度   
					int ratesSize = Integer.parseInt(widths);
					double rate1 = (double) width / (double) ratesSize + 0.1;   
					// 根据缩放比率大的进行缩放控制   
					double rate = rate1;   
					nweWidth = (int) ((double) width / rate);   
					newHeight = (int) ((double) height / rate);  
				}else{//当为非富文本编辑器上传图片时
					// 为等比缩放计算输出的图片宽度及高度   
					double rate1 = (double) width / (double) 400 + 0.1;   
					double rate2 = (double) height / (double) 480 + 0.1;   
					// 根据缩放比率大的进行缩放控制   
					double rate = rate1 > rate2 ? rate1 : rate2;   
					nweWidth = (int) ((double) width / rate);   
					newHeight = (int) ((double) height / rate);
				}
				//压缩图片
				CompressPicUtil compressPic = new CompressPicUtil();
				compressPic.compressPic(tmpFile, tmpPath + File.separator, destFilename, nweWidth, newHeight, true);
			}
			
			//共享存储
			syncFile(tmpFile, filePath);
			//删除临时图片
			FileUtils.delete(tmpPath);
		}catch(Exception e){
			resultMsg = "error" + e.getMessage();
		}
		
		//filePath,处理\\,js不认\\,变成;
		parentDir = parentDir.replaceAll("\\" + File.separator, File.pathSeparator);
		return getAutoView().addObject("resultMsg",resultMsg)
							.addObject("width",widths)
							.addObject("filePath", parentDir + File.pathSeparator + destFilename)
							.addObject("resultMsg", resultMsg)
							.addObject("fileName", originalFilename);
	}
	@RequestMapping("ajaxUpload")
	@ResponseBody
	public JSONObject ajaxUpload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		
		//企业Id
		String tenantId = String.valueOf(3340301l);
		//String tenantId = String.valueOf(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		String widths = RequestUtils.getString(request, "width");
		String resultMsg = "success";
		ServletContext sc = request.getSession().getServletContext();
		//图片相对目录
		String parentDir =  FILE_PATH_CHILD_DIR + File.separator + tenantId;
		//图片存放临时目录
		String tmpPath = FILE_PATH_CHILD_DIR + File.separator + "tmp";
		if(!FileUtils.exists(new File(tmpPath))){
			FileUtils.mkdirs(tmpPath);
		}
		//文件名
		String originalFilename = file.getOriginalFilename();
		String destFilename= transFileNameByTime(tenantId, originalFilename);
		//图片路径
		String filePath = parentDir + File.separator + destFilename;
		try{		
			//存放到临时路径				
			String tmpFilePath = tmpPath + File.separator + destFilename;
			saveFileFromInputStream(file.getInputStream(), tmpFilePath);
			File tmpFile = new File(tmpFilePath);
			BufferedImage bi = null;
			bi = ImageIO.read(tmpFile);
			
			int width = bi.getWidth(); // 像素
			int height = bi.getHeight(); // 像素
			int nweWidth = bi.getHeight(); // 像素
			int newHeight = bi.getHeight(); // 像素
			
			//遍历压缩
			/*if(width >400 || height > 480 ){
				if(widths != "400" && !StringUtil.isEmpty(widths)){//当为富文本编辑器上传图片时
					// 为等比缩放计算输出的图片宽度及高度   
					int ratesSize = Integer.parseInt(widths);
					double rate1 = (double) width / (double) ratesSize + 0.1;   
					// 根据缩放比率大的进行缩放控制   
					double rate = rate1;   
					nweWidth = (int) ((double) width / rate);   
					newHeight = (int) ((double) height / rate);  
				}else{//当为非富文本编辑器上传图片时
					// 为等比缩放计算输出的图片宽度及高度   
					double rate1 = (double) width / (double) 400 + 0.1;   
					double rate2 = (double) height / (double) 480 + 0.1;   
					// 根据缩放比率大的进行缩放控制   
					double rate = rate1 > rate2 ? rate1 : rate2;   
					nweWidth = (int) ((double) width / rate);   
					newHeight = (int) ((double) height / rate);
				}
				//压缩图片
				CompressPicUtil compressPic = new CompressPicUtil();
				compressPic.compressPic(tmpFile, tmpPath + File.separator, destFilename, nweWidth, newHeight, true);
			}*/
			
			//共享存储
			syncFile(tmpFile, filePath);
			//删除临时图片
			FileUtils.delete(tmpPath);
		}catch(Exception e){
			resultMsg = "error" + e.getMessage();
		}
		//filePath,处理\\,js不认\\,变成;
		parentDir = parentDir.replaceAll("\\" + File.separator, ";");
		jsonObject.put("resultMsg", resultMsg);
		jsonObject.put("width", widths);
		jsonObject.put("_callback", request.getParameter("_callback"));
		jsonObject.put("filePath", parentDir + ";" + destFilename);
		jsonObject.put("fileName", originalFilename);
		jsonObject.put("resultMsg", resultMsg);
		return jsonObject;
	}
	@RequestMapping("ajaxUploadIE")
	@ResponseBody
	public void ajaxUploadIE(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		
		//企业Id
		String tenantId = String.valueOf(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		String widths = RequestUtils.getString(request, "width");
		String resultMsg = "success";
		ServletContext sc = request.getSession().getServletContext();
		//图片相对目录
		String parentDir =  FILE_PATH_CHILD_DIR + File.separator + tenantId;
		//图片存放临时目录
		String tmpPath = FILE_PATH_CHILD_DIR + File.separator + "tmp";
		if(!FileUtils.exists(new File(tmpPath))){
			FileUtils.mkdirs(tmpPath);
		}
		//文件名
		String originalFilename = file.getOriginalFilename();
		String destFilename= transFileNameByTime(tenantId, originalFilename);
		//图片路径
		String filePath = parentDir + File.separator + destFilename;
		try{		
			//存放到临时路径				
			String tmpFilePath = tmpPath + File.separator + destFilename;
			saveFileFromInputStream(file.getInputStream(), tmpFilePath);
			File tmpFile = new File(tmpFilePath);
			BufferedImage bi = null;
			bi = ImageIO.read(tmpFile);
			int width = bi.getWidth(); // 像素
			int height = bi.getHeight(); // 像素
			int nweWidth = bi.getHeight(); // 像素
			int newHeight = bi.getHeight(); // 像素
			
			//遍历压缩
			/*if(width >400 || height > 480){
				if(widths != "400" && !StringUtil.isEmpty(widths)){//当为富文本编辑器上传图片时
					// 为等比缩放计算输出的图片宽度及高度   
					int ratesSize = Integer.parseInt(widths);
					double rate1 = (double) width / (double) ratesSize + 0.1;   
					// 根据缩放比率大的进行缩放控制   
					double rate = rate1;   
					nweWidth = (int) ((double) width / rate);   
					newHeight = (int) ((double) height / rate);  
				}else{//当为非富文本编辑器上传图片时
					// 为等比缩放计算输出的图片宽度及高度   
					double rate1 = (double) width / (double) 400 + 0.1;   
					double rate2 = (double) height / (double) 480 + 0.1;   
					// 根据缩放比率大的进行缩放控制   
					double rate = rate1 > rate2 ? rate1 : rate2;   
					nweWidth = (int) ((double) width / rate);   
					newHeight = (int) ((double) height / rate);
				}
				//压缩图片
				CompressPicUtil compressPic = new CompressPicUtil();
				compressPic.compressPic(tmpFile, tmpPath + File.separator, destFilename, nweWidth, newHeight, true);
			}*/
			
			//共享存储
			syncFile(tmpFile, filePath);
			//删除临时图片
			FileUtils.delete(tmpPath);
		}catch(Exception e){
			resultMsg = "error" + e.getMessage();
		}
		//filePath,处理\\,js不认\\,变成;
		parentDir = parentDir.replaceAll("\\" + File.separator, ";");
		jsonObject.put("resultMsg", resultMsg);
		jsonObject.put("width", widths);
		jsonObject.put("_callback", request.getParameter("_callback"));
		jsonObject.put("filePath", parentDir + ";" + destFilename);
		jsonObject.put("fileName", originalFilename);
		jsonObject.put("resultMsg", resultMsg);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonObject.toString()); 
	}
	/**
	 * 文件同步
	 * @param srcFile
	 * @param destFile
	 */
	public void syncFile(File srcFile, String destFile){
		try{
			FileAdaptor adaptor = FileAdaptorFactory.getAdaptor();
			adaptor.writeFile(srcFile, destFile);
		}catch(Exception e){
			System.out.println("文件同步失败");
			e.printStackTrace();
			logger.error("文件同步失败", e);
		}
	}
	
	/**保存文件
    * @param stream
    * @param path
    * @param filename
    * @throws IOException
    */
   public void saveFileFromInputStream(InputStream stream,String path) throws IOException{      
       FileOutputStream fs=new FileOutputStream( path );
       byte[] buffer =new byte[1024*1024];
       int bytesum = 0;
       int byteread = 0;
       while ((byteread=stream.read(buffer))!=-1){
          bytesum+=byteread;
          fs.write(buffer,0,byteread);
          fs.flush();
       } 
       fs.close();
       stream.close();      
   }       

   public static void main(String[] args){
	   File srcFile = new File("D:\\IMAG0028.jpg");
	   //临时目录
	   String tmpDir = srcFile.getParentFile().getAbsolutePath() + "tmp";
	   String tmpPath = tmpDir + "\\" + srcFile.getName();
	   File tmpFile = new File(tmpPath);
	   FileUtils.copyFile(srcFile, tmpFile);
	   while(tmpFile.length() > 100*1024){//遍历压缩
			//压缩图片
			BufferedImage bi = null;
			try {
				bi = ImageIO.read(tmpFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int width = bi.getWidth(); // 像素
			int height = bi.getHeight(); // 像素
			System.out.println("width=" + width + ",height=" + height + ".");
			CompressPicUtil compressPic = new CompressPicUtil();
			int widthResult = Math.round(width/FILE_COMPRESS_SCAL);
			int heightResult = Math.round(height/FILE_COMPRESS_SCAL);
			compressPic.compressPic(tmpFile, tmpDir + File.separator, srcFile.getName(), width, height, true);
	   }
   }
   
   /**
	 * 按日期
	 * @param file
	 */
	public String transFileNameByTime(Object prefix,String fileName){
		String name = DateUtils.getShortCurrentDate("yyMMddHHmmss");
		Assert.notNull(fileName);
		fileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		return prefix + "_" + name + fileName;
	}
}
