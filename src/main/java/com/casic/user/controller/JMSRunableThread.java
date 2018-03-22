package com.casic.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import com.casic.msgLog.model.SysMsgLog;
import com.casic.msgLog.service.SysMsgLogService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.service.system.SubSystemService;

public class JMSRunableThread implements Runnable{
	private String context;
	private String queues;
	private List <SubSystem>list=new ArrayList<SubSystem>();
	private String systemId="";
	public JMSRunableThread(String queues ,String context) {
		this.context=context;
		this.queues=queues;
		this.run();
	}
	public JMSRunableThread(String queues ,String context,List<SubSystem> list) {
		this.context=context;
		this.queues=queues;
		this.list=list;
		this.run();
	}
	public JMSRunableThread(String queues ,String context,String systemId) {
		this.context=context;
		this.queues=queues;
		this.systemId=systemId;
		this.run();
	}
	/**
	 * 
	 * 	user_add		用户新增
		user_update		用户修改
		user_del        用户删除
		
		tenant_add		企业新增
		tenant_update	企业修改

		role_add		角色新增
		role_update		角色修改
        role_del        角色删除
        
		userRole_update		用户角色分配 ，修改
		resourcerole_update	资源角色分配，修改
		
		
		resources_add		资源添加
		resources_update	资源修改
		resources_delete	资源删除
	 */
	@Override
	public void run() {
		JmsTemplate jmsTemplate=(JmsTemplate) AppUtil.getBean("jmsTemplate");
		JdbcTemplate jdbcTemplate=(JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		SysMsgLogService sysMsgLogService=(SysMsgLogService) AppUtil.getBean("sysMsgLogService");
		List<SubSystem> all =new ArrayList<SubSystem>();
		if(list.size()>0){
			all=list;
		}		
		else if(systemId!=""){
			ArrayList<SubSystem> sublist = new ArrayList<SubSystem>();
			  List<Map<String, Object>> subsystemListmap = jdbcTemplate.queryForList("select * From sys_subsystem t where t.systemId=?",systemId);
		      if(subsystemListmap!=null&&subsystemListmap.size()>0){
					for (Map<String, Object> m : subsystemListmap) {
						SubSystem subsystem1 =new SubSystem();
						subsystem1.setSystemId(Long.parseLong(m.get("systemId").toString()));
						subsystem1.setSync(Integer.parseInt(m.get("sync").toString()));//sync:0 不同步信息  1同步用户企业信息  2 同步用户企业角色资源
						sublist.add(subsystem1);
					}	
			   }	
		      all=sublist;
		}else{
			  ArrayList<SubSystem> sublist = new ArrayList<SubSystem>();

			  List<Map<String, Object>> subsystemListmap = jdbcTemplate.queryForList("select * From sys_subsystem t where t.sync != ? ",0);//0 不同步信息  1同步用户企业信息  2 同步用户企业角色资源
		      if(subsystemListmap!=null&&subsystemListmap.size()>0){
					for (Map<String, Object> m : subsystemListmap) {
						SubSystem subsystem1 =new SubSystem();
						subsystem1.setSystemId(Long.parseLong(m.get("systemId").toString()));
						subsystem1.setSync(Integer.parseInt(m.get("sync").toString()));
						sublist.add(subsystem1);
					}	
			   }	
		    all=sublist;
		}		
			
		
		
	      for (SubSystem subSystem : all) {
	    	  
	    	  if(1==subSystem.getSync()&&(queues.contains("user_")||queues.contains("tenant_"))){
	    		  jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public  javax.jms.Message createMessage(Session session) throws JMSException {
							MapMessage ms1=session.createMapMessage();
						    ms1.setString(queues,context);
							return ms1;
						}
					});
	    	  }else{
	    		  jmsTemplate.send(subSystem.getSystemId()+"", new MessageCreator() {
						public  javax.jms.Message createMessage(Session session) throws JMSException {
							MapMessage ms1=session.createMapMessage();
						    ms1.setString(queues,context);
							return ms1;
						}
					});
	    	  }
	    	  	SysMsgLog msgLog = new SysMsgLog();
				msgLog.setId(UniqueIdUtil.genId());
				msgLog.setSendrersonal("");
				msgLog.setOperation(queues);
				msgLog.setReceiverersonal(subSystem.getSystemId()+"");
				msgLog.setSendcontent(context);
				sysMsgLogService.add(msgLog); 
				
			}
		
	}
	
}
