package com.casic.synchronize.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframe.utils.reflect.GenericsUtils;
import com.casic.base.model.config.OrgRelationShip;
import com.casic.base.model.config.UserRelationShip;
import com.casic.base.service.config.OrgRelationShipService;
import com.casic.base.service.config.UserRelationShipService;
import com.casic.synchronize.model.AopResult;
import com.casic.synchronize.model.Message;
import com.casic.util.HttpClientUtil;
import com.casic.util.OpenIdUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysOrg;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserRoleService;

@Controller
@RequestMapping("/synchronize")
public class SynchronizeController {
	@Autowired
	private OrgRelationShipService orgservice;
	
	@Resource
	private SubSystemService service;
	
	@Autowired
	private UserRelationShipService userservice;
	
	@Autowired
	private SysUserService sysuserservice;
	
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SubSystemService subSystemService;
	
	@Resource
	private SysUserOrgService sysUserOrgService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@RequestMapping(value="byaccount",method=RequestMethod.POST)
	@ResponseBody
	public AopResult Synchonize(@RequestBody Message msg){
		
		if(msg.getYwAccount()!=null&&!msg.getYwAccount().equals("")){
			if(msg.getPassword()!=null&&!msg.getPassword().equals(""))
			{
				System.out.println("有账号");
				return SynchronizeByAccount(msg);
			}
		}
		
		System.out.println("没有账号");
		return SynchronizeWithoutAccount(msg);
	}
	
	
	
	public AopResult SynchronizeByAccount(Message msg){
		if(msg.getStEId()==null||msg.getStEId().equals("")){
			return AopResult.build(300, "企业账号未绑定");
		}
		if(msg.getStUId()==null||msg.getStUId().equals("")){
			return AopResult.build(301, "用户账号未填写");
		}
		if(msg.getSystemId()==null||msg.getSystemId().equals("")){
			return AopResult.build(306, "未知子系统请求");
		}
		
		
		
		//根据合作企业id去查返回sys_orgRelationShip_mapping Model对象
		OrgRelationShip orgship=orgservice.getShipByAccount(msg.getStEId());
		//根据合作企业用户id去查返回sys_userRelationship_mapping Model对象
		UserRelationShip usership=userservice.getShipBypartnerUserId(msg.getStUId());
		//根据合作企业id和合作企业用户id去查返回sys_userRelationship_mapping Model对象
		UserRelationShip accountship=userservice.getShipByAccountAndPartenId(msg.getStEId(), msg.getYwAccount());
		//根据系统id去查一个对象
		SubSystem subSystem = subSystemService.getById(Long.parseLong(msg.getSystemId()));
		System.out.println(orgship);
		System.out.println(usership);
		System.out.println(accountship);
		if(subSystem==null){//判断是否为云网的子系统
			return AopResult.build(306, "未知子系统请求");
		}
		
		if(orgship!=null&&orgship.getStatus().equals("1")){//判断企业账号是否绑定如果没有绑定 返回错误信息
			if(usership!=null&&usership.getStatus().equals("1")){//判断用户账号是否绑定 绑定后返回错误信息
				Message returnmsg=new Message();
				returnmsg.setYwAccount(usership.getAccount());
				return AopResult.build(301, "用户账号已绑定",returnmsg);
			}else{
				if(msg.getYwOrgID()!=null&&!msg.getYwOrgID().equals("")){//判断账号的登录方式 如果以企业号和用户名进行登录的  自动拼接正确用户名
					msg.setYwAccount(msg.getYwOrgID()+"_"+msg.getYwAccount());
				}
				if(accountship==null||!accountship.getStatus().equals("1")){//判断云网账号是否与其他账号进行绑定

					// 未绑定进行有云网账号进行云网账号的验证   账号有误则返回错误信息 账号争取则进行绑定
					if(sysuserservice.getByVip(msg.getYwAccount(), EncryptUtil.encryptSha256(msg.getPassword())).size()<1){
						return AopResult.build(302, "输入的航天云网账号有误绑定失败");
					}else{
						Map<String,Object> map=new HashMap<String, Object>();
						ISysUser user= sysuserservice.getByAccount(msg.getYwAccount());
						System.out.println(user);
						ISysOrg sysOrg = sysOrgService.getById(orgship.getEntpId());
						
						List<ISysRole> roles = sysRoleService.getByUserId(user.getUserId());
						map.put("userId", user.getUserId());
						map.put("fullName", user.getFullname());
						map.put("tenantId", sysOrg.getOrgId());
						map.put("tenantName", sysOrg.getOrgName());
						map.put("ywOrgID", orgship.getEntpId());
						String a=msg.getSystemId();
						map.put("systemId", a);
						if(sysuserservice.getByAccount(msg.getYwAccount()).getOrgSn().equals(orgship.getEntpId())){//校验云网账号是否属于该企业
							System.out.println(roles.size());
							if(roles.size()==0||!roles.get(0).getRoleName().equals("航天云网-企业管理员")){//校验是否是企业的管理员用户
								int i=0;
								for(;i<roles.size();i++){
									if(roles.get(i).getRoleName().equals("航天云网-采购人员")){
										break;
									}
								}
								System.out.println("......");
								Long[] Nid=new Long[1];
								Nid[0]=sysuserservice.getByAccount(msg.getYwAccount()).getUserId();
								System.out.println(Nid);
								
								if(i==roles.size()){
									try {
										userRoleService.add(10000045050019l, Nid);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
								
								
								UserRelationShip newship=new UserRelationShip();
								newship.setId(UniqueIdUtil.genId());
								newship.setAccount(msg.getYwAccount());
								newship.setPartnerId(msg.getStEId());
								newship.setPartnerUserId(msg.getStUId());
								newship.setUserId(Nid[0]);
								newship.setStatus("1");
								newship.setApplyTime(new Date());
								newship.setFromsystemid(Long.valueOf(msg.getSystemId()));
								userservice.addNewShip(newship);
								return new AopResult(map);
							}else{
								return AopResult.build(305, "所提供的账号为企业的管理员账户不能被绑定");
							}
						}else{
							return AopResult.build(304, "所提供的账号并不属于该公司");
						}
					}
				
				}else{
					return AopResult.build(303, "所提供的云网账号已经被绑定");
				}
				
			}
		}
			else{	
				
				return AopResult.build(300, "企业账号未绑定");	
		}
		
		
	}

	public AopResult SynchronizeWithoutAccount(Message msg){
		
		if(msg.getStEId()==null||msg.getStEId().equals("")){
			return AopResult.build(300, "企业账号未绑定");
		}
		if(msg.getStUId()==null||msg.getStUId().equals("")){
			return AopResult.build(301, "用户账号未填写");
		}
		if(msg.getSystemId()==null||msg.getSystemId().equals("")){
			return AopResult.build(306, "未知子系统请求");
		}
		
		
		
		OrgRelationShip orgship=orgservice.getShipByAccount(msg.getStEId());
		UserRelationShip usership=userservice.getShipBypartnerUserId(msg.getStUId());
		SubSystem subSystem = subSystemService.getById(Long.parseLong(msg.getSystemId()));
		System.out.println(orgship);
		System.out.println(usership);
		if(subSystem==null){//判断是否为云网的子系统
			return AopResult.build(306, "未知子系统请求");
		}
		if(orgship!=null&&orgship.getStatus().equals("1")){//判断企业账号是否绑定如果没有绑定 返回错误信息
			
			if(usership!=null&&usership.getStatus().equals("1")){//判断用户账号是否绑定 绑定后返回错误信息
				Message returnmsg=new Message();
				returnmsg.setYwAccount(usership.getAccount());
				return AopResult.build(301, "用户账号已绑定",returnmsg);
				
			}else{
				//对没有绑定的用户创建账号进行绑定
				SysUser newuser=new SysUser();
				String newAccount=orgship.getEntpId()+"_"+msg.getStUName();
				Long[] aryOrgCharge=new Long[1];
				aryOrgCharge[0]=SysUserOrg.CHARRGE_NO.longValue();
				
				while(true){
					if(sysuserservice.isAccountExist(newAccount)){
						newAccount=newAccount+"a";
					}else{
						break;
					}
				}
				newuser.setAccount(newAccount);
				newuser.setFullname(msg.getStUName());
				newuser.setSex("1");
				
				
				long newId=UniqueIdUtil.genId();
				newuser.setUserId(newId);
				newuser.setPassword(EncryptUtil.encryptSha256("123456"));
				newuser.setOrgId(orgship.getEntpId());
				newuser.setOrgSn(orgship.getEntpId());
				newuser.setCreatetime(new Date());
				newuser.setIsLock((short)0);
				newuser.setIsExpired((short)0);
				newuser.setStatus((short)1);
				newuser.setOpenId(OpenIdUtil.getOpenId());
				newuser.setShortAccount(msg.getStUName());
				sysuserservice.add(newuser);
				// 添加用户和组织关系。
				Long[] Nid=new Long[1];
				Nid[0]=newId;
				System.out.println(Nid[0]);
				try {
					sysUserOrgService.saveUserOrg(newId, new Long[]{orgship.getEntpId()}, 1l, aryOrgCharge);
					userRoleService.add(10000045050019L, Nid);//添加角色
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				UserRelationShip newship=new UserRelationShip();
				long newid=UniqueIdUtil.genId();
				newship.setId(newid);
				newship.setAccount(newuser.getAccount());
				newship.setPartnerId(msg.getStEId());
				newship.setPartnerUserId(msg.getStUId());
				newship.setUserId(newuser.getUserId());
				newship.setStatus("1");
				newship.setApplyTime(new Date());
				newship.setFromsystemid(Long.valueOf(msg.getSystemId()));
				userservice.addNewShip(newship);

				Map<String,Object> map=new HashMap<String, Object>();
				ISysOrg sysOrg = sysOrgService.getById(orgship.getEntpId());
				
				map.put("userId", newid);
				map.put("fullName", msg.getStUName());
				map.put("tenantId", sysOrg.getOrgId());
				map.put("tenantName", sysOrg.getOrgName());
				map.put("ywAccount", newAccount);
				map.put("ywOrgID", orgship.getEntpId());
				String ps="123456";
				map.put("password",ps);
				
				
				
				return new AopResult(map);
			}
		}
			else{	
				return AopResult.build(300, "企业账号未绑定");	
		}
	}
	
	@RequestMapping(value="checkaccount",method=RequestMethod.POST)
	@ResponseBody
	public AopResult ChenkAccount(@RequestBody Message msg){
		if(msg.getYwOrgID()!=null&&!msg.getYwOrgID().equals("")){
			msg.setYwAccount(msg.getYwOrgID()+"_"+msg.getYwAccount());
		}
		int a=sysuserservice.getByVip(msg.getYwAccount(), EncryptUtil.encryptSha256(msg.getPassword())).size();
		
		boolean result;
		if(a<1){
			result= false;
		}else{
			result= true;
		}
		return new AopResult(result);
	}
	
	@RequestMapping(value="checkaccountbymobile",method=RequestMethod.POST)
	@ResponseBody
	public AopResult ChenkAccountByMobile(@RequestBody Message msg){
		/*if(msg.getYwOrgID()!=null&&!msg.getYwOrgID().equals("")){
			msg.setYwAccount(msg.getYwOrgID()+"_"+msg.getYwAccount());
		}*/
		int a=sysuserservice.getByMobile(msg.getMobile(), EncryptUtil.encryptSha256(msg.getPassword())).size();
		
		boolean result;
		if(a<1){
			result= false;
		}else{
			result= true;
		}
		return new AopResult(result);
	}
	
	
	@RequestMapping(value="checkship",method=RequestMethod.POST)
	@ResponseBody
	public  AopResult CheckShip(@RequestBody Message msg){
		if(msg.getStEId()==null||msg.getStEId().equals("")){
			return AopResult.build(300, "企业账号未填写");
		}
		if(msg.getStUId()==null||msg.getStUId().equals("")){
			return AopResult.build(301, "用户账号未填写");
		}
		if(msg.getSystemId()==null||msg.getSystemId().equals("")){
			return AopResult.build(306, "未知子系统请求");
		}
		

		OrgRelationShip orgship=orgservice.getShipByAccount(msg.getStEId());
		UserRelationShip usership=userservice.getShipBypartnerUserId(msg.getStUId());
		SubSystem subSystem = subSystemService.getById(Long.parseLong(msg.getSystemId()));
		
		if(subSystem==null){//判断是否为云网的子系统
			return AopResult.build(306, "未知子系统请求");
		}
		
		if(orgship!=null&&orgship.getStatus().equals("1")){//企业账号未绑定
			if(usership!=null&&usership.getStatus().equals("1")){//判断用户账号是否绑定 绑定后返回错误信息
				ISysUser user= sysuserservice.getByAccount(usership.getAccount());
				ISysOrg sysOrg = sysOrgService.getById(orgship.getEntpId());
				Map<String,Object> map=new HashMap<String, Object>();
				
				map.put("tenantId",sysOrg.getOrgId());
				map.put("tenantName",sysOrg.getOrgName());
				map.put("userId", usership.getUserId());
				map.put("fullName", user.getFullname());
				
				
				
				return AopResult.build(200, "用户账号已绑定",map);
			}else{
				return AopResult.build(301, "用户未绑定");
			}
			
		}else{	
				return AopResult.build(300, "企业账号未绑定");	
		}	
	}
	@RequestMapping(value="unbind",method=RequestMethod.POST)
	@ResponseBody
	public  AopResult Unbind(@RequestBody Message msg){
		//校验子系统id
		if(msg.getSystemId()==null||msg.getSystemId().equals("")){
			return AopResult.build(306, "未知子系统请求");
		}
		SubSystem subSystem = subSystemService.getById(Long.parseLong(msg.getSystemId()));
		if(subSystem==null){//判断是否为云网的子系统
			return AopResult.build(306, "未知子系统请求");
		}
		//校验账号和密码
		if(sysuserservice.getByVip(msg.getYwAccount(), EncryptUtil.encryptSha256(msg.getPassword())).size()<1){
			return AopResult.build(302, "输入的航天云网账号有误解绑失败");
		}
		UserRelationShip delship=new UserRelationShip();
		delship.setAccount(msg.getYwAccount());
		delship.setPartnerUserId(msg.getStUId());
		userservice.Unbind(delship);
		return AopResult.ok();
	}
	@RequestMapping(value="getuserid",method=RequestMethod.POST)
	@ResponseBody
	public  AopResult getUserId(@RequestBody Message msg){
		//校验子系统id
		if(msg.getSystemId()==null||msg.getSystemId().equals("")){
			return AopResult.build(306, "未知子系统请求");
		}
		SubSystem subSystem = subSystemService.getById(Long.parseLong(msg.getSystemId()));
		if(subSystem==null){//判断是否为云网的子系统
			return AopResult.build(306, "未知子系统请求");
		}
		
		if(msg.getStUId()==null){
			return AopResult.build(301, "子系统账号ID为空");
		}
		List<UserRelationShip> list = new ArrayList<UserRelationShip>();
		UserRelationShip usership = new UserRelationShip();
		//先获得关系列表
		list = userservice.getShipBypartnerUserIdList(msg.getStUId());
		if(list==null|| list.size()==0){
			return AopResult.build(302, "该账号没有云网绑定账号");
		}
		//如果返回多个值在根据用户id和系统id查询用户
		else if(list.size()>1){
			list = userservice.getShipBypartnerUserIdSystemId(msg.getStUId(),msg.getSystemId());
			if(list.size()==1){
				usership = list.get(0);
			}
			else if(list.size()>1){
				return AopResult.build(305, "用户重复绑定，请联系开发人员");
			}
			else{
				return AopResult.build(306, "多个子账号没有绑定系统来源，请联系开发人员");
			}
		}
		else{
			usership = list.get(0);
			long systemId = usership.getFromsystemid();
			if(systemId==0){
				usership.setFromsystemid(Long.parseLong(msg.getSystemId()));
				
				userservice.update(usership);
			}
		}
		//UserRelationShip usership=userservice.getShipBypartnerUserId(msg.getStUId());
		/*if(usership==null){
			return AopResult.build(302, "该账号没有云网绑定账号");
		}*/
		Map<String,Object> map=new HashMap<String, Object>();
		
		map.put("YwUserId",usership.getUserId());
		
		return AopResult.build(200, "ok" , map);
	}
	
	public static void main(String[] args) {
		// String url =
		// "http://106.74.152.118:2381/api/getFailureListByOrgId.ht";

		//String url = "http://ds.casicloud.com:2381/api/getAllOrgs.ht";
		// String url = "http://172.17.70.207:8080/api/getAllOrgs.ht";
		String url = "http://172.17.70.216/synchronize/getuserid.ht";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stUId", "123213213123");// 企业id
		//params.put("colId", "stat");
		//params.put("currentPage", 1);
		params.put("systemId", 100);
		// params.put("orgId", 4530301);
		// params.put("orgId", 740115);
		// params.put("pageSize", 288);

		// params.put("maxrows", "10");
		System.out.println(params);
		String result = "";
		try {
			result = HttpClientUtil.JsonPostInvoke(url, params);
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(result);
			System.out.println(json.get("success"));

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(result);
	}
}
