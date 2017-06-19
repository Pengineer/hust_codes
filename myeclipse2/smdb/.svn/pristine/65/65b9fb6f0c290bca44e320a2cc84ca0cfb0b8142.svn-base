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


public class KeyMidFundListAction extends FundListBaseAction {
	/**
	 * 重大攻关项目中检拨款清单管理
	 */
	private static final long serialVersionUID = -8697512346066048341L;
	private static final String PAGE_NAME = "keyMidFundPage";//列表页面名称
	private static final String PROJECT_TYPE = "key";//项目类别
	private static final String FUND_TYPE = "mid";//拨款类别
	//管理人员使用
	private static final String HQL2 = "from FundList fl where fl.projectType =     'key' and fl.fundType =     'mid' ";
	public String pageName() {
		return KeyMidFundListAction.PAGE_NAME;
	}
	public String projectType(){
		return KeyMidFundListAction.PROJECT_TYPE;
	}
	public String fundType(){
		return KeyMidFundListAction.FUND_TYPE;
	}
	public String listHql2(){
		return KeyMidFundListAction.HQL2;
	}
}