package csdc.action.fundList;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.FundList;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.tool.DoubleTool;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;


public class PostEndFundListAction extends FundListBaseAction {
	/**
	 * 后期资助项目结项拨款清单管理
	 */
	private static final long serialVersionUID = 6929019106903014921L;
	private static final String PAGE_NAME = "postEndFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "post";//项目类别
	private static final String FUND_TYPE = "end";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType =    'post' and fl.fundType =     'end' ";
	public String pageName() {
		return PostEndFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return PostEndFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return PostEndFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return PostEndFundListAction.HQL2;
	}
}