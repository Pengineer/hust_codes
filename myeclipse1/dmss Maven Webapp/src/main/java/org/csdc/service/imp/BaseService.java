package org.csdc.service.imp;

import java.io.File;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.Application;
import org.csdc.bean.GlobalInfo;
import org.csdc.dao.IHibernateBaseDao;
import org.csdc.model.Account;
import org.csdc.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础业务类
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class BaseService  {
	@Autowired
	protected IHibernateBaseDao baseDao;
	
	@Autowired
	protected Application application;
	
	public static String ACCOUNT_BUFFER="account"; //创建全局静态变量来保存会话的账户	
	
	/**
	 * 校验密码是否为空或小于6位
	 * @param password
	 * @return
	 */
	public boolean validatePassword(String password){
		if(null ==password ||password.length()<6)
			return false;
		return true;
	}
	
	/**
	 * 根据MD5值生成实际存储路径
	 * @param md5
	 * @return
	 */
	public String md5toPath(String md5){
		return md5.substring(0,2)+File.separator+md5.substring(2,4)+File.separator+md5.substring(4,6)+File.separator+md5;
	}
	
	/**
	 * 获取session中的账号信息
	 * @param request
	 * @return
	 */
	public Account getSessionAccount(HttpServletRequest request){
		return (Account) request.getSession().getAttribute(GlobalInfo.ACCOUNT_BUFFER);
	}
	
	/**
	 * 获取文档的本地目录
	 * @param doc
	 * @return
	 */
	public String getFetchPath(Document doc){
		return  application.getRootPath()+File.separator+"hdfs"+File.separator+"fetch"+File.separator+doc.getPath()+"."+doc.getType();
	}
	
	/**
	 * 获取文件大小的字符串表示
	 * @param n 文件大小，单位字节
	 * @return
	 */
	public String getDiskSize(long n){
		Double r = 0.0;
		DecimalFormat df = new DecimalFormat("#.00");
		if(n/1024/1024/1024>=1){
			r = n/1024.0/1024.0/1024.0;
			return df.format(r)+"GB";
		}else if (n/1024/1024>=1) {
			r = n/1024.0/1024.0;
			return df.format(r)+"MB";
		}else if (n/1024>=1) {
			r = n/1024.0;
			return df.format(r)+"KB";
		}else {
			return df.format(n)+"B";
		}
	}
}
