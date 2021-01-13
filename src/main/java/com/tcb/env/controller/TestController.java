package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tcb.env.pojo.Test;
import com.tcb.env.pojo.User;
import com.tcb.env.service.ITestService;
import com.tcb.env.service.IUserService;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("/TestController")
public class TestController {

	private static Logger logger = Logger.getLogger(TestController.class);
	Gson gson = new Gson();

	@Resource
	private ITestService testService;
	
	@Resource
	private IUserService userService;

	@RequestMapping(value = "/toIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView toIndex(HttpServletRequest request, ModelAndView mv) {
		try {
			// request 可以获取请求参数
/*			int id = Integer.valueOf(request.getParameter("id"));//返回整型
			List<Test> list = new ArrayList<Test>();//创建一个list用于接收		
			list = testService.getTest(id);*/
			User user = new User();
			user.setUserId(3);
			List<User> list = new ArrayList<User>();
			list = userService.getUser(user,false);
			
			mv.addObject("message", "无转换：" + list + ";转换：" + gson.toJson(list));
			mv.setViewName("/html/MyTestHtml");//jsp页面可以${message}显示，html只能通过AJAX获取

		} catch (Exception ex) {
			logger.error("查询数据库失败，信息为：" + ex.getMessage());
		}
		return mv;
	}

	@RequestMapping(value = "/toGson", method = { RequestMethod.POST })
	public @ResponseBody List<Test> toGson(String strTest1, String strTest2) {
		List<Test> list = new ArrayList<Test>();
		for (int i = 0; i < 2; i++) {
			Test testnew = new Test();
			testnew.setId(i);
			testnew.setName("test" + i + "receive1:" + strTest1 + ",receive2:"
					+ strTest2);
			list.add(testnew);
		}
		return list;
	}

	@RequestMapping(value = "/toTest", method = { RequestMethod.POST })
	public @ResponseBody List<Test> toTest(Test test) {
		List<Test> list = new ArrayList<Test>();
		for (int i = 0; i < 3; i++) {
			Test testnew = new Test();
			testnew.setId(i);
			testnew.setName(test.getName() + i + " from server");
			list.add(testnew);
		}
		return list;
	}

}
