package com.casic.accountInfo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.casic.accountInfo.model.Activation;
import com.casic.accountInfo.model.ChangeMessage;
import com.casic.synchronize.model.AopResult;
import com.casic.tenant.model.TenantInfo;
import com.casic.tenant.service.TenantInfoService;
import com.casic.util.OpenIdUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;

@Controller
@RequestMapping("/accountinfo")
public class AccountInfo {
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private TenantInfoService tenantInfoService;

	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	

	@RequestMapping(value="changps",method=RequestMethod.POST)
	@ResponseBody
	public AopResult changePassword(@RequestBody ChangeMessage msg){
		String password =  msg.getPassword();
		String enPassword=EncryptUtil.encryptSha256(password);
		String newPassword =  msg.getRepassword();
		sysUserService.updPwd(msg.getUserId(), newPassword);
		return AopResult.ok();
	}
	@RequestMapping(value="getinfo",method=RequestMethod.POST)
	@ResponseBody
	public AopResult getAccount(@RequestBody ChangeMessage msg){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("address", msg.getAddress());
		map.put("orgname", msg.getOrgname());
		map.put("startTime",msg.getStartTime());
		if(msg.getStartTime()!=null&&msg.getEndTime()==null){
			map.put("endTime", new Date());
		}else{
			map.put("endTime", msg.getEndTime());
		}
		
		//System.out.println(sysUserService.getUserBytimeAndAddress(map));
		List< Map<String, Object>> s=sysUserService.getUserBytimeAndAddress(map);
		return  AopResult.build(200, s.size()+"", s);
		
		
	}
	@RequestMapping(value="getorginfo",method=RequestMethod.POST)
	@ResponseBody
	public AopResult getOrg(@RequestBody ChangeMessage msg){
		
		
		
		
		return null;
	}
	
	
	
	
	
	@RequestMapping(value="activation",method=RequestMethod.POST)
	@ResponseBody
	public AopResult Activation(@RequestBody Activation ac){
		long orgId=ac.getOrgId();
		TenantInfo info=new TenantInfo();
		info.setName(ac.getOrgName());
		
		info.setConnecter(ac.getConnecter());
		
		String relMes="[\""+ac.getAccount()+":"+ac.getMobile()+":"+ac.getPassword()+"\"]";
		com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(relMes);
		String account = "";
		String mobile="";
		String password="";
		for (int i=0;i< jsonArray.size();i++) {
			account=jsonArray.get(i).toString().split(":")[0].trim();
			mobile=jsonArray.get(i).toString().split(":")[1].trim();
			password=jsonArray.get(i).toString().split(":")[2].trim();
		}
		if(password.equals("")){
			password="123456";
		}
		if(orgId!=0){
			ISysOrg sysOrg = sysOrgService.getById(orgId);
			if(sysOrg != null){
				//验证电话号码是否存在
				System.out.println(mobile);
				List<ISysUser> sysUsers=sysUserService.getByMobile(mobile);
				System.out.println(sysUsers);
				System.out.println(sysUsers.size());
					if(sysUsers.size()>0&&sysUsers!=null){
						//存在 则返回已经存在   同时初始化密码
						for (int i = 0; i < sysUsers.size(); i++) {
							if(sysUsers.get(i).getOrgId()==orgId){
								ISysUser user=sysUsers.get(i);
								sysUserService.updPwd(user.getUserId(), password);
								return AopResult.build(200, "账号密码已经初始化");
							}
						}
						
						return AopResult.build(304, "您输入的账号已经被注册 ");
					}
					try {
						System.out.println("有企业但是没有用户");
						//SysUser sysUser = sysUserService.doInitAfterRegisterSysUser((SysOrg)sysOrg, relMes);
						
					//	return AopResult.build(200, "账号已经建立",sysUser);
						 SysUser newuser=new SysUser();
						 newuser.setFullname(account);
						 newuser.setShortAccount(account);
						 Long[] aryOrgCharge=new Long[1];
						 
						 
						 
						 
							aryOrgCharge[0]=SysUserOrg.CHARRGE_NO.longValue();
							while(true){
								if(sysUserService.isAccountExist(account)){
									account=account+"a";
								}else{
									break;
								}
							}
							newuser.setAccount(account);
							newuser.setSex("1");
							long newId=UniqueIdUtil.genId();
							newuser.setUserId(newId);
							newuser.setPassword(EncryptUtil.encryptSha256("123456"));
							newuser.setOrgId(sysOrg.getOrgId());
							newuser.setOrgSn(sysOrg.getOrgId());
							newuser.setCreatetime(new Date());
							newuser.setMobile(mobile);
							newuser.setIsLock((short)0);
							newuser.setIsExpired((short)0);
							newuser.setStatus((short)1);
							newuser.setOpenId(OpenIdUtil.getOpenId());
							
							sysUserService.add(newuser);
							return AopResult.build(200, "账号添加成功",newuser);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return AopResult.build(300, "未知错误");
					}
				//如果没有账户新建一个管理员账户
			}else{
				//建立新的企业
				System.out.println("没有企业 ");
				info.setRegistertime(new Date());
				//验证电话是否重复
			    String mobile1=ac.getMobile();
				List<ISysUser> sysUsers = sysUserService.getByMobile(mobile1);
				boolean isExist;
				if(sysUsers!=null){
					if(sysUsers.size()>0){
						isExist= false;
					}else{
						isExist= true;
					}
				}else{
					isExist= true;
				}
				
				
				if(!isExist){
					return  AopResult.build(300, "电话已经注册请更换电话");
				}
				
				//验证用户名是否重复
				if(sysUserService.isAccountExist(ac.getAccount())){
					return AopResult.build(300, "该账户已经注册 请修改用户名");
				}
				Map<String,Object> map;
					try {
						map=tenantInfoService.registerSysOrgNoUser(info, relMes);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return AopResult.build(301, "激活失败"); 
					}
				return  AopResult.build(200, "ok", map);
				
			}	
			
			
		}else{
			
			return  AopResult.build(302, "orgId未输入");
		}
	
	}
}
