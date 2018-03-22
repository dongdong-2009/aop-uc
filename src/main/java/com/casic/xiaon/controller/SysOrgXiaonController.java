package com.casic.xiaon.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ws.security.message.token.Timestamp;
import org.compass.core.json.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.appleframe.utils.web.RequestUtils;
import com.casic.tenant.model.BranchBean;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.BranchBeanService;
import com.casic.tenant.service.TenantInfoService;
import com.casic.util.GetURLData;
import com.casic.util.PropertiesUtils;
import com.casic.util.StringUtil;
import com.casic.xiaon.model.SysOrgXiaon;
import com.casic.xiaon.service.SysOrgXiaonService;
import com.hotent.core.annotion.Action;
import com.hotent.core.cache.ICache;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.SysOrgInfoService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


/**
 * 对象功能:sys_org_xiaon_pwd Model对象
 * 开发公司:航天科工集团
 * 开发人员:LZY
 * 创建时间:20160530
 */
@Controller
@RequestMapping("/xiaon")
public class SysOrgXiaonController extends BaseController{

	String password = this.password(10);
	
//	@Resource
//	private IndustryTenantReleaseService industryTenantReleaseService;
	@Resource
	private SysOrgXiaonService sysOrgXiaonService;
	@Resource
	private TenantInfoService infoService;
	@Resource
	private BranchBeanService bbs;
	@Resource
	private ICache iCache;	
	@Resource
	private TenantInfoService tenantInfoService;
	/**
	* 我的客服   欢迎页面  sys_org_xiaon
	* @param request
	* @param response
	* @return
	* @throws Exception
	*/
	@RequestMapping("onLineService")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("/xiaon/sysOrgXiaonIndex.jsp");
		ISysUser user = ContextUtil.getCurrentUser();
		Long sysOrgId;
		if (user != null) {
			sysOrgId = (user.getOrgSn()!=null&&user.getOrgSn()!=0L)?user.getOrgSn():(user.getOrgId());
		}else{
			sysOrgId=(Long)iCache.getByKey("orgInfoId");
		}
		TenantInfo info = infoService.getById(sysOrgId);
		// STATE 新注册为0，审核通过为2，审核未通过为1
		String state = info.getState()+"";
		String res = "未通过";
		if("5".equals(state)){
			res = "已通过";
		}
		SysOrgXiaon sysOrgXiao = sysOrgXiaonService.getByUserId(sysOrgId);	
		if(sysOrgXiao != null){
			ModelAndView xmav = new ModelAndView("/xiaon/sysOrgXiaonList.jsp");
			return xmav.addObject("sysOrgXiaon",sysOrgXiao).addObject("state", state);			
		}else{
			return mav.addObject("sysOrgXiao",sysOrgXiao).addObject("info", info).addObject("state",state).addObject("res",res);
		}
		
	}
	
	/**
	* 	禁用商家列表
	*/
	@RequestMapping("disableList")
	public ModelAndView disableList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = RequestUtil.getString(request, "type");
		ModelAndView xmav = new ModelAndView("/xiaon/sysOrgXiaonDisable.jsp");
		QueryFilter queryFilter= new QueryFilter(request,"sysOrgXiaoItem");		//分页
		queryFilter.getPageBean().setPagesize(5);
		queryFilter.getFilters().put("service", type);
		List<SysOrgXiaon> sysOrgXiaon = sysOrgXiaonService.getType(queryFilter);	
		return xmav.addObject("sysOrgXiaon",sysOrgXiaon);
	}
	
	/**
	* 	启用商家列表
	*/
	@RequestMapping("enableList")
	public ModelAndView enableList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = RequestUtil.getString(request, "type");
		ModelAndView xmav = new ModelAndView("/xiaon/sysOrgXiaonEnable.jsp");
		QueryFilter queryFilter= new QueryFilter(request,"sysOrgXiaoItem");		//分页
		queryFilter.getPageBean().setPagesize(5);
		queryFilter.getFilters().put("service", type);
		List<SysOrgXiaon> sysOrgXiaon = sysOrgXiaonService.getType(queryFilter);		
		return xmav.addObject("sysOrgXiaon",sysOrgXiaon);
	}
	
	
	
	
	/**
	* 我的客服   列表  sys_org_xiaon
	* @param request
	* @param response
	* @return
	* @throws Exception
	*/
	@RequestMapping("xiaonList")
	public ModelAndView xiaonList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("/xiaon/sysOrgXiaonList.jsp");
		String id = request.getParameter("id");
		SysOrgXiaon sysOrgXiaon = sysOrgXiaonService.getByUserId(Long.parseLong(id));	
		mav.addObject("sysOrgXiaon",sysOrgXiaon);
		return mav;
	}
	
	/**
	 * 填写 企业认证信息
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("certified")
	@Action(description = "企业认证信息填写")
	public ModelAndView certified(HttpServletRequest request,HttpServletResponse response) throws Exception {
		long entId = RequestUtil.getLong(request, "entId");
		TenantInfo info = infoService.getById(entId);
		return this.getAutoView().addObject("info", info);
	}
	
	/**
	 * @description add  and  update
	 * @param id
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addInfo")
	@ResponseBody
	public Map<String, Object> addInfo(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String orgId = request.getParameter("id");
		if(!StringUtil.isEmpty(orgId)){			
			TenantInfo tenantInfo = tenantInfoService.getById(Long.parseLong(orgId));
			if(tenantInfo!=null){
				System.out.println(tenantInfo.getState());
				if(tenantInfo.getState() != 5){
					map.put("doMsg", "3");
					map.put("msg", "您还未通过实名审核，请审核通过后再开通小能");
					return map;
				}
			}
		}
		
		String url = PropertiesUtils.getProperty("platform.url");
		boolean flag=false;
		String backmsg="";
		try {
		if(url != null && url.contains("uc.casicloud.com")){
			//此为生产环境，先删除小能的信息
			String sitekey = "abc2323";								//此值固定
			Long curday = System.currentTimeMillis()/1000;	//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis()/1000;
//			Date date=new Date();
//			long time = date.getTime()/1000; //获取当前时间转为unix时间戳格式
			System.out.println(curday+"====="+curtime);
			String authkey = sitekey + curday;
			JSONObject obj=new JSONObject();
			JSONArray arr = new JSONArray();
			obj.put("siteid","yw_"+orgId);	// 商家id
			obj.put("encrypt","md5");
			arr.add(obj);
			//小能商家信息同步接口	mcentertest.ntalker.com
			String enterprise =GetURLData.stringSendGet("http://bkpirb.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=del"+"&merchantInfo="+arr,"");
			//GetURLData.stringSendGet("http://mcentertest.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=add"+"&receptionInfo="+objStr, "");
			System.out.println(enterprise+"删除返回");
			JSONObject jsonObject = JSONObject.fromObject(enterprise);
			String code = jsonObject.get("success").toString();
			String errorcode="";
			if("-1".equals(code)){
				//删除失败
				errorcode=jsonObject.get("errorcode").toString();
				backmsg=jsonObject.get("errormsg").toString();
				flag=true;
				if("100005".equals(errorcode)){
					//商户不存在
					flag=false;
				}
			}
			System.out.println("成功or失败："+code+"====="+"错误码："+errorcode+"===错误信息："+backmsg);
		}
		if(flag){
			//
			map.put("doMsg", "3");
			map.put("msg",backmsg);
			return map;
		}		
			SysOrgXiaon sysOrgXiaon = sysOrgXiaonService.getByUserId(Long.valueOf(orgId));
			if(null == sysOrgXiaon){
				SysOrgXiaon sysOrgXiao = new SysOrgXiaon();
				sysOrgXiao.setId(UniqueIdUtil.genId());
				sysOrgXiao.setSiteid(orgId.toString());
				sysOrgXiao.setName(request.getParameter("username"));
				sysOrgXiao.setPass(password);
				sysOrgXiao.setAccount("yw_"+orgId);
				sysOrgXiao.setKfnum("10");
				sysOrgXiao.setType(0l);
				sysOrgXiao.setService("0");
				sysOrgXiao.setCreateTime(new Date());
				this.classify(request, response);
				String resultMsg = this.enterprise(request, response);//同步商户返回结果
				int strIndex = resultMsg.indexOf("{");
				String result = resultMsg.substring(strIndex, resultMsg.length());
				JSONObject jsonObject = JSONObject.fromObject(result);
				Object object = jsonObject.getString("success");	
				String msg ="";
				if("1".equals(object)) {//同步成功
					System.out.println(sysOrgXiao);
					sysOrgXiaonService.add(sysOrgXiao);
					map.put("doMsg", "1");
					map.put("name", request.getParameter("connecter"));
					map.put("tel", request.getParameter("tel"));
					map.put("email", request.getParameter("email"));
				}else{//同步不成功					
					map.put("doMsg","0");	
					msg = jsonObject.getString("errormsg");
					map.put("msg", msg);
				}
				
			}else{
				SysOrgXiaon sysOrgXiao = new SysOrgXiaon();	
				Long type = RequestUtil.getLong(request, "type");
				sysOrgXiao.setSiteid(orgId.toString());
				sysOrgXiao.setType(type);
				sysOrgXiao.setDeadlineTime(new Date());
				sysOrgXiaonService.gUpdate(sysOrgXiao);
				System.out.println(sysOrgXiao);
				map.put("doMsg","2");
				map.put("msg","更新成功");
			}
			//map.put("msg", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}	
	@RequestMapping("saveServes")
	@Action(description = "商户修改服务")
	public ModelAndView saveServes(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/xiaon/sysOrgXiaonList.jsp");
		SysOrgXiaon sysOrgXiao = new SysOrgXiaon();
		String userId = request.getParameter("siteid");
		String service = RequestUtil.getString(request, "service");
		sysOrgXiao.setSiteid(userId.toString());
		sysOrgXiao.setService(service);
		sysOrgXiaonService.gUpdate(sysOrgXiao);
		sysOrgXiao = sysOrgXiaonService.getByUserId(Long.valueOf(userId));
		return mav.addObject("sysOrgXiaon", sysOrgXiao);
	}
	
	/**
	 *  取得sys_org_xiaon   实体
	 * @param request
	 * @return
	 * @throws Exception
	 * */
	public SysOrgXiaon getFormObject(HttpServletRequest request) throws Exception{
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] {"yyyy-MM-dd"})));
		String json = RequestUtils.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		SysOrgXiaon SysOrgXiaon = (SysOrgXiaon)JSONObject.toBean(obj,SysOrgXiaon.class);
		return SysOrgXiaon;
	}
	
//		////////////////小能//////////////////
//		String url="http://b2b.casicloud.com";
//		@RequestMapping("xiaonJson")
//		public @ResponseBody JSONObject xiaonJson(String itemid,HttpServletRequest request,HttpServletResponse response) throws Exception{
//			Long id = Long.valueOf(itemid);
//			IndustryTenantReleaseBean bean = industryTenantReleaseService.getById(id);
//			List<SuppliersImage> imgList = industryTenantReleaseService.getImgList(id);
//			JSONObject obj=new JSONObject();
//			JSONObject obj2=new JSONObject();
//			obj.put("id", itemid);
//			obj.put("name", bean.getCapandproname());
//			obj.put("orderprice", bean.getPrice());					//商品价格
//			if(imgList.get(0).getImgUrl() !=null && imgList.get(0).getImgUrl() !=""){
//				obj.put("imageurl",imgList.get(0).getImgUrl());
//			}else{
//				obj.put("imageurl", "http://b2b.casicloud.com/pages/cloud6.0/yzz/body/images/nengli.jpg");
//			}
//			obj.put("url", url+"/industryMall/hall/releaseDetail.ht?id="+itemid);
//			obj2.put("item", obj);
//			System.out.println(obj2.toString());
//			return obj2;
//		
//		}
//		
		/**
		* 分类信息接口
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		@RequestMapping("classify")
		public String classify(HttpServletRequest request,HttpServletResponse response) throws Exception{
			////////分类接口需要的字段////////
			String sitekey = "abc2323";	//此值固定
			Long curday = System.currentTimeMillis();				//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis();
			String authkey = sitekey + curday;
			JSONObject obj=new JSONObject();
			obj.put("id", "1001");
			obj.put("name", "%e5%b7%b2%e8%ae%a4%e8%af%81%e4%bc%81%e4%b8%9a");  //已认证企业				//url编码格式
			obj.put("pid", "0");
			obj.put("level", "1");
			String objStr = "[" + obj.toString() + "]";
			//小能分类信息接口      
			String c = GetURLData.stringSendGet("http://bkpi10.ntalker.com/index.php/shopapi/classify?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=add"+"&classifies="+objStr+"&siteid=yw_1000","");
			//GetURLData.stringSendGet("http://mcentertest.ntalker.com/index.php/shopapi/classify?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=add"+"&classifies="+objStr,"");
			System.out.println(objStr.toString());
			System.out.println(c);
			return c;
		}
		
		
		/**
		*  创建商户接口
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		@RequestMapping("enterprise")
		public String enterprise(HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			String name =ContextUtil.getCurrentOrgInfoFromSession().getName();
			////////分类接口需要的字段////////
			String sitekey = "abc2323";								//此值固定
			Long curday = System.currentTimeMillis();				//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis();
			String authkey = sitekey + curday;
			JSONObject obj=new JSONObject();
			JSONArray arr = new JSONArray();
			obj.put("siteid","yw_"+ContextUtil.getCurrentOrgId());		// 商家id
			obj.put("name",name);									//商家名称
			obj.put("classifyid","1001");									//商家分类id
			obj.put("kfnum","10");										//商家可以创建的账号
			obj.put("account",ContextUtil.getCurrentOrgId());			//商家主账号
			obj.put("pass",password);							//商家主账号密码
			obj.put("encrypt","md5");
			arr.add(obj);
			//小能商家信息同步接口	mcentertest.ntalker.com
			String enterprise =GetURLData.stringSendGet("http://bkpirb.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=add"+"&merchantInfo="+arr,"");
			//GetURLData.stringSendGet("http://mcentertest.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=add"+"&receptionInfo="+objStr, "");
			System.out.println(arr);
			System.out.println(enterprise.toString());
			return enterprise;
		}
		
		
		/**
		*  删除  商户账号（小能）
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		@RequestMapping("deluser")
		public String deluser(HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			String name =ContextUtil.getCurrentOrgInfoFromSession().getName();
			////////分类接口需要的字段////////
			String sitekey = "abc2323";								//此值固定
			Long curday = System.currentTimeMillis();				//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis();
			String authkey = sitekey + curday;
			JSONObject obj=new JSONObject();
			JSONArray arr = new JSONArray();
			obj.put("siteid","yw_4520301");		// 商家id
			obj.put("encrypt","md5");
			arr.add(obj);
			//小能商家信息同步接口	mcentertest.ntalker.com
			String enterprise =GetURLData.stringSendGet("http://bkpirb.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=del"+"&merchantInfo="+arr,"");
			//GetURLData.stringSendGet("http://mcentertest.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=add"+"&receptionInfo="+objStr, "");
			System.out.println(enterprise+"删除返回");
			return enterprise;
		}
		
		
		
		/**
		* 应答客服组同步接口
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		@RequestMapping("serviceGroup")
		public String serviceGroup(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String sitekey = "abc2323";								//此值固定
			Long curday = System.currentTimeMillis();				//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis();
			String authkey = sitekey + curday;
			JSONObject obj = new JSONObject();
			JSONArray arr = new JSONArray();
			obj.put("id", ContextUtil.getCurrentOrgId());
			obj.put("name", "接待组1");
			obj.put("siteid",ContextUtil.getCurrentOrgId());
			arr.add(obj);
			
			String objStr = "[" + arr.toString() + "]";
			//小能应答客服组同步接口  mcentertest.ntalker.com
			//response.sendRedirect("http://bkpirb.ntalker.com/index.php/shopapi/ServiceGroup?curtime="+curtime+"&authkey="+MD5(authkey)+"&siteid=yw_1201"+"&action=showall"+"&receptionInfo="+objStr);
			GetURLData.stringSendGet("http://mcentertest.ntalker.com/index.php/shopapi/ServiceGroup?curtime="+curtime+"&authkey="+MD5(authkey)+"&siteid="+ContextUtil.getCurrentOrgId()+"&action=showall"+"&receptionInfo="+objStr,"");
			System.out.println(arr.toString());
			return objStr;
		}
		
		/**
		* 禁用商家
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		@RequestMapping("disable")
		public JSONArray disable(HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			ContextUtil.getCurrentOrgInfoFromSession().getName();
			////////分类接口需要的字段////////
			String sitekey = "abc2323";								//此值固定
			Long curday = System.currentTimeMillis();				//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis();
			String authkey = sitekey + curday;
			JSONArray arr = new JSONArray();
			
			JSONObject obj = new JSONObject();
			obj.put("siteid", ContextUtil.getCurrentOrgId());
			arr.add(obj);
			String objStr = "[" + arr.toString() + "]";
			//小能商家信息同步接口	mcentertest.ntalker.com
			//response.sendRedirect("http://bkpirb.ntalker.com/index.php/shopapi/Enterprise?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=disable"+"&merchantInfo="+objStr);
			String disable = GetURLData.stringSendGet("http://bkpirb.ntalker.com/index.php/shopapi/classify?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=disable"+"&merchantInfo="+objStr,"");
			
			JSONObject json = JSONObject.fromObject(disable);
			json.getString("success");
			Long success = Long.valueOf(json.getString("success"));
			if(success == 1){
				String userId = request.getParameter("id");
				String type = request.getParameter("type");
				SysOrgXiaon sysOrgXiaon = sysOrgXiaonService.getByUserId(Long.valueOf(userId));
				if(type!=null){
					sysOrgXiaon.setType(Long.valueOf(type));
				}
				sysOrgXiaonService.update(sysOrgXiaon);
			}
			System.out.println(disable.toString());
			return arr;
		}
		
		/**
		* 启用商家
		* @param request
		* @param response
		* @return
		* @throws Exception
		*/
		
		@RequestMapping("enable")
		public JSONArray enable(HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			ContextUtil.getCurrentOrgInfoFromSession().getName();
			////////分类接口需要的字段////////
			String sitekey = "abc2323";								//此值固定
			Long curday = System.currentTimeMillis();				//获取当前时间转为unix时间戳格式
			Long curtime = System.currentTimeMillis();
			String authkey = sitekey + curday;
			JSONArray arr = new JSONArray();
			//小能商家信息同步接口	mcentertest.ntalker.com
			String enable = GetURLData.stringSendGet("http://bkpirb.ntalker.com/index.php/shopapi/classify?curtime="+curtime+"&authkey="+MD5(authkey)+"&action=enable", "");
			
			JSONObject json = JSONObject.fromObject(enable);
			json.getString("success");
			Long success = Long.valueOf(json.getString("success"));
			if(success == 1){
				String userId = request.getParameter("id");
				String type = request.getParameter("type");
				SysOrgXiaon sysOrgXiaon = sysOrgXiaonService.getByUserId(Long.valueOf(userId));
				if(type!=null){
					sysOrgXiaon.setType(Long.valueOf(type));
				}
				sysOrgXiaonService.update(sysOrgXiaon);
			}
			System.out.println(arr.toString());
			return arr;
		}
		
		
		//MD5加密
		private static String MD5(String sourceStr) {
		    String result = "";
		    try {
		        MessageDigest md = MessageDigest.getInstance("MD5");
		        md.update(sourceStr.getBytes());
		        byte b[] = md.digest();
		        int i;
		        StringBuffer buf = new StringBuffer("");
		        for (int offset = 0; offset < b.length; offset++) {
		            i = b[offset];
		            if (i < 0)
		                i += 256;
		            if (i < 16)
		                buf.append("0");
		            buf.append(Integer.toHexString(i));
		        }
		        result = buf.toString();
		    } catch (NoSuchAlgorithmException e) {
		        System.out.println(e);
		    }
		    return result;
		}
		
		
		///生成随机密码
		private static String password(int pwd_len){
			String val = "";  
			Random random = new Random();  
			  
			//参数length，表示生成几位随机数  
			for(int i = 0; i < 10; i++) {  
			      
			    String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
			    //输出字母还是数字  
			    if( "char".equalsIgnoreCase(charOrNum) ) {  
			        //输出是大写字母还是小写字母  
			        int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
			        val += (char)(random.nextInt(26) + temp);  
			    } else if( "num".equalsIgnoreCase(charOrNum) ) {  
			        val += String.valueOf(random.nextInt(10));  
			    }  
			}  
			System.out.println(val);
			return val;  
		}  
		///////////////end//////////////////
}
