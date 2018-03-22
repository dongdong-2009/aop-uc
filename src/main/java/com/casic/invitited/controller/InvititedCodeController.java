package com.casic.invitited.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;






















import com.appleframe.common.SpringContextHolder;
import com.casic.invitited.model.InvitiedEntity;
import com.casic.invitited.service.InvitedService;
import com.casic.service.pub.CloudMailService;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.util.PropertiesUtils;
import com.casic.util.SmsUtil;
import com.casic.util.StringUtil;
import com.hotent.core.cache.ICache;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;

@Controller
@RequestMapping("/invitited")
public class InvititedCodeController extends BaseController{
	
	@Resource
	private TenantInfoService tenantInfoService;
	
	@Resource
	private ICache iCache;
	
	@Resource
	private InvitedService invitedService;
	
	@Resource
	private CloudMailService cloudMailService;
	
	/**
	 * 邀请码列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<TenantInfo> list = new ArrayList<TenantInfo>();
		ModelAndView mv = new ModelAndView("/invitited/invititedList.jsp");
		list=tenantInfoService.getAll(new QueryFilter(request,"tenantInfoItem"));
		mv.addObject("tenantInfoList",list);
		return mv;
	}
	
	@RequestMapping("myList")
	public ModelAndView myList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user = ContextUtil.getCurrentUser();
		long invititedCode =((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		QueryFilter queryFilter = new QueryFilter(request,"tenantInfoItem");
		queryFilter.addFilter("invititedCode", invititedCode);
		List<TenantInfo> list = new ArrayList<TenantInfo>();
		ModelAndView mv = new ModelAndView("/invitited/myInvititedList.jsp");
		list=tenantInfoService.getAll(queryFilter);
		mv.addObject("tenantInfoList",list);
		return mv;
	}
	
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user=ContextUtil.getCurrentUser();
		long tenantId = ((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		ModelAndView mv = new ModelAndView("/invitited/invititedAdd.jsp");
		mv.addObject("tenantId", tenantId);
		return mv;
	}
	/**
	 * 邀请补录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("makeup")
	public ModelAndView makeup(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user=ContextUtil.getCurrentUser();
		long tenantId = ((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		ModelAndView mv = new ModelAndView("/invitited/invititedMakeup.jsp");
	    TenantInfo tenantInfo=tenantInfoService.getById(tenantId);
		TenantInfo invitedInfo=null;
		if(tenantInfo!=null){
			if(!StringUtil.isEmpty(tenantInfo.getInvititedCode())){
				String  invitedCode=tenantInfo.getInvititedCode();
				invitedInfo=tenantInfoService.getById(Long.parseLong(invitedCode));
			}
		}
		long invititedCode =((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		QueryFilter queryFilter = new QueryFilter(request,"tenantInfoItem");
		queryFilter.addFilter("invititedCode", invititedCode);
		queryFilter.addFilter("orderField","buluTime");
		List<TenantInfo> list = new ArrayList<TenantInfo>();
	  ///  ModelAndView mv = new ModelAndView("/invitited/myInvititedList.jsp");
		list=tenantInfoService.getAll(queryFilter);
		mv.addObject("tenantInfoList",list);
		mv.addObject("tenantInfo", tenantInfo).addObject("invitedInfo", invitedInfo);
		return mv;
	}
	@RequestMapping("echarts")
	public ModelAndView echarts(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user=ContextUtil.getCurrentUser();
		long tenantId = ((user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():user.getOrgId());
		String begincreatetime=RequestUtil.getString(request, "begincreatetime");
		String endcreatetime=RequestUtil.getString(request, "endcreatetime");
		ModelAndView mv = new ModelAndView("/invitited/invititedEcharts.jsp");
		mv.addObject("tenantId", tenantId).addObject("endcreatetime", endcreatetime).addObject("begincreatetime", begincreatetime);
		return mv;
	}
	
	@RequestMapping("loadData")
	@ResponseBody
	public ResultMessage loadData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ResultMessage resultMessage=null;
		Long tenantId=ContextUtil.getCurrentTenantId();
		//获得当前的企业Id
		//根据企业Id去查询邀请码的数量查询完成企业的认证数
		
		try {
			InvitiedEntity inList= invitedService.loadData(tenantId.toString());
			resultMessage=new ResultMessage(ResultMessage.Success,JSONObject.fromObject(inList).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage=new ResultMessage(ResultMessage.Fail,e.getMessage() );	
		}
		return resultMessage;
	}
	@RequestMapping("newLoadData")
	@ResponseBody
	public ResultMessage newLoadData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ResultMessage resultMessage=null;
		Long tenantId=ContextUtil.getCurrentTenantId();
		if(tenantId==null){
			tenantId=(Long)iCache.getByKey("orgInfoId");
		}
		String begincreatetime=RequestUtil.getString(request, "begincreatetime");
		String endcreatetime=RequestUtil.getString(request, "endcreatetime");
		
		//获得当前的企业Id
		//根据企业Id去查询邀请码的数量查询完成企业的认证数
		
		try {
			InvitiedEntity inList= invitedService.loadDataByTenantId(tenantId.toString(),begincreatetime,endcreatetime);
			resultMessage=new ResultMessage(ResultMessage.Success,JSONObject.fromObject(inList).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage=new ResultMessage(ResultMessage.Fail,e.getMessage() );	
		}
		return resultMessage;
		
	       
	}
	
	@RequestMapping("welcome")
	@ResponseBody
	public String welcome(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String result = "邀请成功";
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String content = request.getParameter("content");
		String tenantId=RequestUtil.getString(request, "tenantId");
		cloudMailService.sendMessage(email,"企业邀请",content,content);
		
		
		String tpl_id = PropertiesUtils.getProperty("zhuce.tpl_id");
    	String apikey = PropertiesUtils.getProperty("sms.apikey");
//    	String codeValue = PropertiesUtils.getProperty("sms.codeValue");
    	//String codeValue = String.valueOf((int)(Math.random()*9000+1000));
    	//SmsCache.add(mobile, codeValue);
    	
    //	tpl_value=urlencode("#code#=1234&#company#=云片网") 
    	//SmsUtil.sendSms(content, mobile);
    	try {
			SmsUtil.sendSms(apikey, content, mobile);
		} catch (Exception e) {
			result="邀请失败";
			e.printStackTrace();
		}
   
		
		
		return result;
		
	}
	
	//excel导出
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SysOrgInfo org = ContextUtil.getCurrentOrgInfoFromSession();
		
		long invititedCode = org.getSysOrgInfoId();
		QueryFilter queryFilter = new QueryFilter(request,"tenantInfoItem");
		queryFilter.getFilters().clear();
		queryFilter.addFilter("invititedCode", invititedCode);
		try {
			queryFilter.getPageBean().setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
			queryFilter.getPageBean().setPagesize(Integer.parseInt(request.getParameter("pageSize")));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    List<TenantInfo> list=tenantInfoService.getAll(queryFilter);
	    //构造页面数据
	   // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("我的邀请");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();
    
     
        // 设置单元格垂直居中对齐
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        style.setWrapText(true);
        // 设置单元格字体样式
        HSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        style.setFont(font);
        // 设置单元格边框为细线条
   
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("企业名称");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("账户类型");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("创建日期");  
        cell.setCellStyle(style); 
        
        for (int i = 0; i < list.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            TenantInfo tenantInfo = (TenantInfo) list.get((i));  
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue((double) (i+1));  
            row.createCell((short) 1).setCellValue(tenantInfo.getName());  
            row.createCell((short) 2).setCellValue("注册会员");  
            cell = row.createCell((short) 3);  
            Date createtime = null;
            try {
				 createtime = tenantInfo.getCreatetime();
				 cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(createtime));  
			} catch (Exception e) {
				cell.setCellValue("");
			}
           
        }  
        // 第六步，将文件存到指定位置  
        String fileName=new String(("我的邀请").getBytes("gb2312"), "iso8859-1")+ ".xls";
        response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes()));  
        OutputStream os= new BufferedOutputStream(response.getOutputStream());  
        response.setContentType("application/vnd.ms-excel;charset=gb2312");  
   
        try  
        {  
        	
          
            wb.write(os);
            os.flush();
            os.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
		
	@RequestMapping("myNewList")
	public ModelAndView myNewList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user=ContextUtil.getCurrentUser();
		long invititedCode = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		QueryFilter queryFilter = new QueryFilter(request,"tenantInfoItem");
		queryFilter.addFilter("invititedCode", invititedCode);
		List<TenantInfo> list = new ArrayList<TenantInfo>();
		ModelAndView mv = new ModelAndView("/invitited/myNewInvititedList.jsp");
		list=tenantInfoService.getAll(queryFilter);
		mv.addObject("tenantInfoList",list);
		return mv;
	}
	
	
}
