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


public class InstpGrantedFundListAction extends FundListBaseAction {
	/**
	 * 基地项目立项拨款清单管理
	 */
	private static final long serialVersionUID = -5843897364869611290L;
	private static final String PAGE_NAME = "instpGrantedFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "instp";//项目类别
	private static final String FUND_TYPE = "granted";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType =   'instp' and fl.fundType = 'granted' ";
	public String pageName() {
		return InstpGrantedFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return InstpGrantedFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return InstpGrantedFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return InstpGrantedFundListAction.HQL2;
	}
}