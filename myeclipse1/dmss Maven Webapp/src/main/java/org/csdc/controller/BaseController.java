package org.csdc.controller;

import java.io.File;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.Application;
import org.csdc.bean.GlobalInfo;
import org.csdc.bean.JsonData;
import org.csdc.dao.IBaseDao;
import org.csdc.model.Account;
import org.csdc.model.Document;
import org.csdc.service.imp.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 基础控制器（所有控制器的积累）
 * @author jintf
 * @date 2014-6-15
 */
public class BaseController {

	
	@Autowired
	protected BaseService baseService;
	
	@Autowired
	protected IBaseDao baseDao;
	@Autowired
	protected Application application;
	
	public Account getSessionAccount(HttpServletRequest request){
		return (Account) request.getSession().getAttribute(GlobalInfo.ACCOUNT_BUFFER);
	}
	

	/**
	 * 获取文档的本地目录
	 * @param doc
	 * @return
	 */
	public String getFetchPath(Document doc){
		return  baseService.getFetchPath(doc);
	}
	
	public String getDiskSize(long n){
		return baseService.getDiskSize(n);
	}
}
