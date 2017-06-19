package csdc.action.projectFund;



public class InstpFundAction extends ProjectFundBaseAction {
	/**
	 * 基地项目拨款概况管理
	 */
	private static final long serialVersionUID = 2544987290341776990L;
	private static final String PAGE_NAME = "instpFeePage";//列表页面名称
	private static final String PROJECT_TYPE = "instp";//项目类别
	private static final String GRANTED_CLASS_NAME = "InstpGranted";//一般项目立项类类名
	private static final int CHECK_GRANTED_FLAG = 22;//获得判断立项项目是否在管辖范围内的标志位
	//管理人员使用
	private static final String HQL2 = "from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ";
	//研究人员使用
	private static final String HQL3 = "from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so, InstpMember mem " +
		"where mem.application.id = app.id and pf.grantedId=gra.id ";
	//清单查看用
	private static final String HQL4 = "from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.projectFundingList.id = :entityId ";
	
	public String pageName() {
		return InstpFundAction.PAGE_NAME;
	}
	public String projectType(){
		return InstpFundAction.PROJECT_TYPE;
	}
	public int checkGrantedFlag(){
		return InstpFundAction.CHECK_GRANTED_FLAG;
	}
	public String grantedClassName(){
		return InstpFundAction.GRANTED_CLASS_NAME;
	}
	public String listHql2(){
		return InstpFundAction.HQL2;
	}
	public String listHql3(){
		return InstpFundAction.HQL3;
	}
	public String listHql4(){
		return InstpFundAction.HQL4;
	}
}

