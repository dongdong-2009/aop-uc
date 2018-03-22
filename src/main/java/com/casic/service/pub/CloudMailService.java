package com.casic.service.pub;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.user.model.Register;
import com.casic.util.GetURLData;
import com.casic.util.SmsCache;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.model.mail.OutMailUserSeting;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.mail.OutMailService;
import com.hotent.platform.service.mail.OutMailUserSetingService;

/**
 * 发送邮箱
 * @author Administrator
 *
 */
@Service
public class CloudMailService {
	@Resource
	private OutMailService outMailService;
	
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	
	/**
	 * 企业注册成功后发送邮件
	 * @param sysOrgInfo
	 * @param sysOrg
	 */
	public void sendSuccessfulRegMessage(SysOrgInfo sysOrgInfo, SysOrg sysOrg, String password, String context, String serverPath){
		String content = 
				"<div class=\"n2success\">" +
					"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
							"<div class=\"j01-2success\">恭喜您: 注册成功！您可以直接登录平台,请牢记以下信息<br />" +
								"企业账号 : " + sysOrgInfo.getSysOrgInfoId() + "<br />" +
								"企业管理员账号 : " + sysOrg.getSysUser().getShortAccount() + "<br />" +
								"企业管理员密码 : " + password + 
							"</div>" +
							"<div class=\"j01-4\">" +
								"<div class=\"msg_normalsuccess\">" +
									"请点击<a href=\"" + serverPath + "" + context + "\">此处</a>进行登录！" +
								"注意：" +
								"登录后请尽快完善注册信息！平台管理员审核通过后，才能够使用商机/商品/能力发布和添加商友等功能！" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
		GetURLData.stringSendGet("http://uc.casicloud.com/mailServer/sendMail?subject=【航天云网注册成功信息】恭喜您，您已成功注册航天云网！&content="+content+"&toAddress="+sysOrgInfo.getEmail(), "");
		//sendMessage(sysOrgInfo.getEmail(),"航天云网注册成功！请查看登录身份信息。", content, context);
	}
	/**
	 * 企业注册成功后发送邮件
	 * @param sysOrgInfo
	 * @param sysOrg
	 */
	public void sendRegMessage(SysOrgInfo sysOrgInfo, String shortAccount, String password, String context, String serverPath){
		String content = 
				"<div class=\"n2success\">" +
					"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
							"<div class=\"j01-2success\">恭喜您: 密码重置成功！您可以直接登录平台,请牢记以下信息<br />" +
								"企业账号 : " + sysOrgInfo.getSysOrgInfoId() + "<br />" +
								"企业管理员账号 : " + shortAccount + "<br />" +
								"企业管理员密码 : " + password + 
							"</div>" +
							"<div class=\"j01-4\">" +
								"<div class=\"msg_normalsuccess\">" +
									"请点击<a href=\"" + serverPath + "" + context + "\">此处</a>进行登录！" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
		sendMessage(sysOrgInfo.getEmail(),"恭喜您，密码已经重置！", content, context);
	}
	/**
	 * 云网企业注册成功后发送邮件
	 * @param sysOrgInfo
	 * @param sysOrg
	 */
	public void sendRegMessageCloud(SysOrgInfo sysOrgInfo, String shortAccount,String sysPassword, String password, String context, String serverPath){
		/*String content = 
				"<div class=\"n2success\">" +
						"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
						"<div class=\"j01-2success\">恭喜您: 您已成功注册航天云网！您可以直接登录平台,请牢记以下信息（系统邮件，请勿回复！）<br />" +
						"企业账号 : " + sysOrgInfo.getSysOrgInfoId() + "<br />" +
						"企业注册管理员账号 : " + shortAccount + "<br />" +
						"企业注册管理员密码 : " + password + "<br /><br />" +
						"企业内置默认管理员账号 : system<br />" +
						"企业内置默认管理员密码 : " + sysPassword + 
						"</div>" +
						"<div class=\"j01-4\">" +
						"<div class=\"msg_normalsuccess\">" +
						"请点击<a href=\"" + serverPath + "" + context + "\">此处</a>进行登录！" +
						"</div>" +
						"</div>" +
						"</div>" +
						"</div>" +
						"</div>";*/
		String feedback = GetURLData.stringSendGet("http://uc.casicloud.com/mailServer/sendMail?sysOrgInfoId=" + sysOrgInfo.getSysOrgInfoId() + "&shortAccount=" + shortAccount + "&password=" + password + "&sysPassword="+sysPassword+"&toAddress="+sysOrgInfo.getEmail(), "");
		//sendMessage(sysOrgInfo.getEmail(),"【航天云网注册成功信息】恭喜您，您已成功注册航天云网！", content, context);
	}
	/**
	 * 云网个人注册成功后发送邮件
	 * @param sysOrgInfo
	 * @param sysOrg
	 */
	public void sendRegMessagePersonal(String email, String shortAccount, String password){
//		String content = 
//				"<div class=\"n2success\">" +
//						"<div class=\"n2-1success\">" +
//						"<div class=\"n2-2\">" +
//						"<div class=\"j01-2success\">恭喜您: 您已成功注册航天云网！请牢记以下信息（系统邮件，请勿回复！）<br />" +
//						"用户名 : " + shortAccount + "<br />" +
//						"密码 : " + password + "<br /><br />"+
//						"</div>" +
//						"</div>" +
//						"</div>" +
//						"</div>";"
		//sendMessage(email,"【航天云网注册成功信息】恭喜您，您已成功注册航天云网！", content, null);
	GetURLData.stringSendGet("http://uc.casicloud.com/mailServer/sendMail?subject=【航天云网注册成功信息】恭喜您，您已成功注册航天云网！&shortAccount=" + shortAccount + "&password=" + password +"&toAddress="+email, "");
	}
	
	public void sendRegMessagePersonal(String email,String mobile, String shortAccount, String password){

		GetURLData.stringSendGet("http://uc.casicloud.com/mailServer/sendMailV2?subject=【航天云网注册成功信息】恭喜您，您已成功注册航天云网！&shortAccount=" + shortAccount + "&mobile=" + mobile+ "&password=" + password +"&toAddress="+email, "");
	}
	
	
	public void sendSuccessfulRegMessageRegister(SysOrgInfo sysOrgInfo,Register register, String context, String serverPath){
		String content = 
				"<div class=\"n2success\">" +
					"<div class=\"n2-1success\">" +
						"<div class=\"n2-2\">" +
							"<div class=\"j01-2success\">恭喜您: 添加成功！<br />" +
								"邮箱 : " + register.getEmail() + "<br/>"+
							"姓名："+register.getName()+"<br/>"+
							"身份证号："+register.getIdentity()+"<br/>"+
							"手机号码："+register.getTellphone()+"<br>"+
							"获奖证书："+register.getCredential()+"<br/>"+
							"作品介绍："+register.getIntroduce()+ "<br />"+
							"</div>" +
							"<div class=\"j01-4\">" +								
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
		GetURLData.stringSendGet("http://uc.casicloud.com/mailServer/sendMail?subject=【航天云网注册成功信息】恭喜您，您已成功注册航天云网！&content="+content+"&toAddress="+register.getEmail(), "");
		//sendMessage(register.getEmail(),"恭喜您，添加成功", content, context);
	}
	
	/**
	 * 发送邮件
	 * @param receiveAddress
	 * @param content
	 */
	public  void sendMessage(String receiveAddress, String title, String content, String context){
		//获取平台管理员的邮箱设置
		OutMailUserSeting outMailUserSeting=outMailUserSetingService.getByIsDefault(1);
		
		//邮件内容
		OutMail outMail = new OutMail();
		outMail.setMailDate(new Date());
		outMail.setSenderName(outMailUserSeting.getUserName());
		outMail.setIsReply(0);
		outMail.setUserId(1L);
		outMail.setSetId(outMailUserSeting.getId());
		outMail.setReceiverAddresses(receiveAddress);
		outMail.setCcAddresses(outMailUserSeting.getMailAddress());
		outMail.setTitle(title);
		outMail.setContent(content);
		outMail.setTypes(2);
		outMail.setSetId(outMailUserSeting.getId());
		outMail.setMailId(0L);
		outMail.setSenderName("航天云网");
		outMail.setSenderAddresses(outMailUserSeting.getMailAddress());		
		try {
			outMailService.sendMail(outMail, 1, 0, 0, context);
		} catch (Exception e) {
			System.out.println("邮件发送失败" + e.getMessage());
		}
	}
	
	/**
	 * 发送验证码
	 * @param email
	 * @param mobile
	 * @param shortAccount
	 * @param password
	 */
	public void SendVerifyCode(String email){
		String codeValue = String.valueOf((int)(Math.random()*9000+1000));
    	SmsCache.add(email, codeValue);
    	try {
    		//uc.casicloud.com
			String url = "http://uc.casicloud.com/mailServer/sendVerifyCode?toAddress="+email+"&codeValue="+codeValue;
			GetURLData.stringSendGet(url, "");
			System.out.println("邮箱号码：【"+email+"】,邮件发送成功,验证码为：【" +codeValue+"】");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("邮件发送失败" + e.getMessage());
		}
		
	}
	
	
	
}
