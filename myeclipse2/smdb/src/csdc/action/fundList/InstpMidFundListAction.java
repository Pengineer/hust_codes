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


public class InstpMidFundListAction extends FundListBaseAction {
	/**
	 * 基地项目中检拨款清单管理
	 */
	private static final long serialVersionUID = -5843897364869611290L;
	private static final String PAGE_NAME = "instpMidFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "instp";//项目类别
	private static final String FUND_TYPE = "mid";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType =   'instp' and fl.fundType =     'mid' ";
	public String pageName() {
		return InstpMidFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return InstpMidFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return InstpMidFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return InstpMidFundListAction.HQL2;
	}
}