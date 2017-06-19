package org.csdc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csdc.bean.GlobalInfo;
import org.csdc.bean.JsonData;
import org.csdc.domain.system.LoginForm;
import org.csdc.model.Account;
import org.csdc.service.imp.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class MainController extends BaseController{
	@Autowired
	private BaseService baseService;
	
	/**
	 * 进入主页面
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="/main")
    public ModelAndView main(HttpServletRequest request){
		Account account = (Account) request.getSession().getAttribute(GlobalInfo.ACCOUNT_BUFFER);
        request.setAttribute("username", account.getName());
		return new ModelAndView("main");
    }
	
	/**
	 * 心跳。第三方接口用来判断DMSS是否在线
	 * @return
	 */
	@RequestMapping(value="/heartBeat", produces="application/json")
    @ResponseBody
	public Object beat(){
		return new JsonData();
	}
	
	@RequestMapping(value="/register")
	@ResponseBody
	public ModelAndView registerPage(HttpServletRequest request) {
		return new ModelAndView("register");
	}
}
