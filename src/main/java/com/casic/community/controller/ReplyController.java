package com.casic.community.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.community.dao.PraiseDao;
import com.casic.community.model.PraiseBean;
import com.casic.community.model.QuestionBean;
import com.casic.community.model.ReplyBean;
import com.casic.community.model.VisitorCountBean;
import com.casic.community.res.ReplyRes;
import com.casic.community.service.CommunityPraiseService;
import com.casic.community.service.CommunityReplyService;
import com.casic.community.service.CountService;
import com.casic.community.service.QuestionService;
import com.casic.util.StringUtil;
import com.casic.util.TimeUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.model.system.SysUser;

@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController{

	@Resource
	private CommunityReplyService replyService;
	
	@Resource
	private QuestionService questionService;
	
	@Resource
	private CommunityPraiseService praiseService;
	

	@Resource
	private CountService countService;
	
	@RequestMapping("/communityReply")
	public ModelAndView joinStandard(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		
		String questionId=RequestUtil.getString(request, "questionId");
		//1.查询问题的标题和内容
		Long queId=null;
		if(!StringUtil.isEmpty(questionId)){
			queId=Long.parseLong(questionId);
		}
		VisitorCountBean countBean = countService.findByPassageId(queId);
		if(null == countBean || "".equals(countBean)){
			VisitorCountBean bean=new VisitorCountBean();
			bean.setId(UniqueIdUtil.genId());
			bean.setPassageId(queId);
			bean.setReadCount(0L);
			bean.setReplyCount(0L);
			countService.add(bean);
		}
		countService.updateReadCountById(queId);
		String returnUrl = RequestUtil.getUrl(request);
		QuestionBean questionBean=questionService.getById(queId);
		ISysUser  user=ContextUtil.getCurrentUser();
		ModelAndView mv = new ModelAndView("/community/communityReply.jsp");
		return mv.addObject("questionBean", questionBean).addObject("user", user).addObject("returnUrl", returnUrl);
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("replyQuestion")
	@ResponseBody
	public ResultMessage replyQuestion(ReplyBean replyBean,HttpServletRequest request) throws Exception{
		ResultMessage res=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		replyBean.setId(UniqueIdUtil.genId());
		replyBean.setCreateTime(format.format(new Date()));
		if(ContextUtil.getCurrentUser().getUserId()!=null&&ContextUtil.getCurrentUser().getUserId()!=0)
		replyBean.setReplyId(ContextUtil.getCurrentUser().getUserId().toString());
		replyBean.setReplyName(ContextUtil.getCurrentUser().getFullname());
		int result=replyService.addReply(replyBean);
		
		
		
		if(result>0){
			res=new ResultMessage(ResultMessage.Success, "回复成功!");
		}else{
			res=new ResultMessage(ResultMessage.Fail, "回复失败");
		}
		return res;
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllReply")
	@ResponseBody
	public ReplyRes getAllReply(HttpServletRequest request,
	        @RequestParam(value="page",defaultValue="1") int page ,
	        @RequestParam(value="pageSize") int pagesize) throws Exception{
		ReplyRes json=new ReplyRes();
		String questionId=RequestUtil.getString(request, "questionId");
		QueryFilter queryFilter=new QueryFilter(request, true);
		queryFilter.getFilters().clear();
		PageBean pageBean=new PageBean(page, pagesize);
		queryFilter.setPageBean(pageBean);
		queryFilter.addFilter("questionId", questionId);
		queryFilter.addFilter("orderField", "createTime");
		List<ReplyBean> replyBeans=replyService.getAll(queryFilter);
		
		for (ReplyBean replyBean : replyBeans) {
			String creteTime=replyBean.getCreateTime();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!StringUtil.isEmpty(creteTime)){
				Date date=format.parse(creteTime);
				replyBean.setCreatetime(date);
				replyBean.setTimeAgo(TimeUtil.format(date));
			}
			List<PraiseBean> praiseBeans=replyBean.getPraiseBeans();
			if(praiseBeans!=null&&praiseBeans.size()>0){
			for (PraiseBean praiseBean : praiseBeans) {
				if(ContextUtil.getCurrentUser()!=null){
					//表示已登录(查询该用户的点赞状态)
					if(praiseBean.getUserId().equals(ContextUtil.getCurrentUser().getUserId().toString()))
					{
						replyBean.setStatus(praiseBean.getStatus());	
						replyBean.setPraiseId(praiseBean.getId());
					}
				}
			}
		  }
		}
		
		
		json.setReplyBeans(replyBeans);
		json.setTotal(queryFilter.getPageBean().getTotalCount());
		Long queId=null;
		if(!StringUtil.isEmpty(questionId)){
			queId=Long.parseLong(questionId);
		}
		countService.updateReplyCountById(queId,json.getTotal());
		return json;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updatePraise")
	@ResponseBody
	public ResultMessage updatePraise(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage res=null;
		String userId=RequestUtil.getString(request, "userId");
		String replyId=RequestUtil.getString(request, "replyId");
		String status=RequestUtil.getString(request, "status");
		String praiseId=RequestUtil.getString(request, "praiseId");
		Integer sum=0;
		if(!StringUtil.isEmpty(replyId)){
			Long id=Long.parseLong(replyId);
			ReplyBean replyBean=replyService.getById(id);
			if(replyBean.getCount()!=null&&!("").equals(replyBean.getCount())){
				sum=Integer.parseInt(replyBean.getCount());
			}
		}
		PraiseBean praiseBean=new PraiseBean();
		int result=0;
		//查询是否操作过点赞操作
		if(!StringUtil.isEmpty(praiseId)){
			//执行update
			if(status.equals("1")){
				//表示点赞操作
				sum++;
			}else{
				sum--;
			}
			praiseBean.setId(Long.parseLong(praiseId));
		
			praiseBean.setStatus(status);
			result=praiseService.updatePraise(praiseBean);
		}else{
			//执行add
			praiseBean.setId(UniqueIdUtil.genId());
			if(!StringUtil.isEmpty(replyId))
				praiseBean.setReplyId(Long.parseLong(replyId));
			praiseBean.setUserId(userId);
			praiseBean.setStatus(status);
			result=praiseService.addPraise(praiseBean);
			//相当于点赞
			sum++;
		}
	   //更新赞总数到数据库中	
		result=replyService.updateCountById(replyId,sum.toString()); 
		if(result>0){
			res=new ResultMessage(ResultMessage.Success, JSONObject.fromObject(praiseBean).toString());
		}else{
			res=new ResultMessage(ResultMessage.Fail, "操作失败");
		}
		return res;
	}
}
