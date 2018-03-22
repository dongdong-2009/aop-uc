package com.casic.community.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.community.model.QuestionBean;
import com.casic.community.model.VisitorCountBean;
import com.casic.community.service.CountService;
import com.casic.community.service.QuestionService;
import com.casic.util.TimeUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController{
	
	private static int pageSize = 10;
	
	@Resource
	private QuestionService questionService;
	
	@Resource
	private CountService countService;
	
	@RequestMapping("getAllQuestionByPage")
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		int page=RequestUtil.getInt(request,"page");
		int lmt = (page-1)*pageSize;
		List<QuestionBean> list=questionService.getAllQuestionByPage(lmt,pageSize);
		List<QuestionBean> result = new ArrayList<QuestionBean>();
		for(QuestionBean que:list){
			String time = que.getCreateTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        Date date = format.parse(time);  
	        time =TimeUtil.format(date);
	        que.setCreateTime(time);
	        result.add(que);
		}
		int size = questionService.getAllCount();
		int pages = (size%pageSize==0?size/pageSize:size/pageSize+1);//总页数
		JSONObject json = new JSONObject();
		json.put("list", result);
		json.put("pages", pages);
		return json;
	}
	
	@RequestMapping("getSolvedQuestionByPage")
	@ResponseBody
	public JSONObject solvedList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		int page=RequestUtil.getInt(request,"page");
		int lmt = (page-1)*pageSize;
		List<QuestionBean> list=questionService.getSolvedQuestionByPage(lmt,pageSize);
		List<QuestionBean> result = new ArrayList<QuestionBean>();
		for(QuestionBean que:list){
			String time = que.getCreateTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Date date = format.parse(time);  
			time =TimeUtil.format(date);
			que.setCreateTime(time);
			result.add(que);
		}
		int size = questionService.getSolvedCount();
		int pages = (size%pageSize==0?size/pageSize:size/pageSize+1);//总页数
		JSONObject json = new JSONObject();
		json.put("list", result);
		json.put("pages", pages);
		return json;
	}
	
	@RequestMapping("getUnsolvedQuestionByPage")
	@ResponseBody
	public JSONObject unsolvedList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		int page=RequestUtil.getInt(request,"page");
		int lmt = (page-1)*pageSize;
		List<QuestionBean> list=questionService.getUnsolvedQuestionByPage(lmt,pageSize);
		List<QuestionBean> result = new ArrayList<QuestionBean>();
		for(QuestionBean que:list){
			String time = que.getCreateTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Date date = format.parse(time);  
			time =TimeUtil.format(date);
			que.setCreateTime(time);
			result.add(que);
		}
		int size = questionService.getUnsolvedCount();
		int pages = (size%pageSize==0?size/pageSize:size/pageSize+1);//总页数
		JSONObject json = new JSONObject();
		json.put("list", result);
		json.put("pages", pages);
		return json;
	}
	
	@RequestMapping("getMyQuestionByPage")
	@ResponseBody
	public JSONObject myList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long currentUserId = ContextUtil.getCurrentUserId();
		int page=RequestUtil.getInt(request,"page");
		int lmt = (page-1)*pageSize;
		List<QuestionBean> list=questionService.getMyQuestionByPage(lmt,pageSize,currentUserId);
		List<QuestionBean> result = new ArrayList<QuestionBean>();
		for(QuestionBean que:list){
			String time = que.getCreateTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Date date = format.parse(time);  
			time =TimeUtil.format(date);
			que.setCreateTime(time);
			result.add(que);
		}
		int size = questionService.getMyCount(currentUserId);
		int pages = (size%pageSize==0?size/pageSize:size/pageSize+1);//总页数
		JSONObject json = new JSONObject();
		json.put("list", result);
		json.put("pages", pages);
		return json;
	}
	
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = new ModelAndView("/community/questionAdd.jsp");
		return mv;
	}
	
	@RequestMapping("updateStatus")
	@ResponseBody
	public String updateStatus(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String result="采纳成功";
		String questionId = request.getParameter("questionId");
		int flag=questionService.updateStatus(Long.parseLong(questionId));
		if(flag==0){
			result="采纳失败";
		}
		return result;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ISysUser user = ContextUtil.getCurrentUser();
		String res="发布成功";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			java.util.Date date=new java.util.Date();  
			String title=RequestUtil.getString(request,"title");
			String content=RequestUtil.getString(request,"content");
			QuestionBean question = new QuestionBean();
			question.setTitle(title);
			question.setContent(content);
			question.setId(UniqueIdUtil.genId());
			question.setCreateById(user.getUserId()+"");
			question.setCreateByName(user.getFullname());
			question.setCreateTime(sdf.format(date));
			question.setStatus("0");
			questionService.add(question);
			VisitorCountBean count=new VisitorCountBean();
			count.setId(UniqueIdUtil.genId());
			count.setPassageId(question.getId());
			count.setReadCount(0l);
			count.setReplyCount(0l);
			countService.add(count);
		}
		catch(Exception e){
			res="发布失败";
		}
		
		return res;
	}
}
