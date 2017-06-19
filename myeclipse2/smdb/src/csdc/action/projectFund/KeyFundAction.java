package csdc.action.projectFund;



public class KeyFundAction extends ProjectFundBaseAction {
	/**
	 * 重大攻关项目拨款概况管理
	 */
	private static final long serialVersionUID = -2544919778763845799L;
	private static final String PAGE_NAME = "keyFeePage";//列表页面名称
	private static final String PROJECT_TYPE = "key";//项目类别
	private static final String GRANTED_CLASS_NAME = "KeyGranted";//一般项目立项类类名
	private static final int CHECK_GRANTED_FLAG = 25;//获得判断立项项目是否在管辖范围内的标志位
	//管理人员使用
	private static final String HQL2 = "from ProjectFunding pf, KeyGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.researchType so where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ";
	//研究人员使用
	private static final String HQL3 = "from ProjectFunding pf, KeyGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.researchType so, KeyMember mem " +
		"where mem.application.id = app.id and pf.grantedId=gra.id ";
	//清单查看用
	private static final String HQL4 = "from ProjectFunding pf, KeyGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.researchType so where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.grantedFundList.id = :entityId ";
	
	public String pageName() {
		return KeyFundAction.PAGE_NAME;
	}
	public String projectType(){
		return KeyFundAction.PROJECT_TYPE;
	}
	public int checkGrantedFlag(){
		return KeyFundAction.CHECK_GRANTED_FLAG;
	}
	public String grantedClassName(){
		return KeyFundAction.GRANTED_CLASS_NAME;
	}
	public String listHql2(){
		return KeyFundAction.HQL2;
	}
	public String listHql3(){
		return KeyFundAction.HQL3;
	}
	public String listHql4(){
		return KeyFundAction.HQL4;
	}
}

