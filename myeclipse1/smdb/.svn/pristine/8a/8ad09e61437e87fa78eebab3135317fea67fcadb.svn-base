package csdc.action.projectFund;



public class PostFundAction extends ProjectFundBaseAction {
	/**
	 * 后期资助项目拨款概况管理
	 */
	private static final long serialVersionUID = -1945211174021278877L;
	private static final String PAGE_NAME = "postFeePage";//列表页面名称
	private static final String PROJECT_TYPE = "post";//项目类别
	private static final String GRANTED_CLASS_NAME = "PostGranted";//一般项目立项类类名
	private static final int CHECK_GRANTED_FLAG = 23;//获得判断立项项目是否在管辖范围内的标志位
	//管理人员使用
	private static final String HQL2 = "from ProjectFunding pf, PostGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so left outer join app.topic top where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ";
	//研究人员使用
	private static final String HQL3 = "from ProjectFunding pf, PostGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.topic top left outer join gra.subtype so, PostMember mem " +
		"where mem.application.id = app.id and pf.grantedId=gra.id ";
	//清单查看用
	private static final String HQL4 = "from ProjectFunding pf, PostGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so left outer join app.topic top where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.grantedFundList.id = :entityId ";
	
	public String pageName() {
		return PostFundAction.PAGE_NAME;
	}
	public String projectType(){
		return PostFundAction.PROJECT_TYPE;
	}
	public int checkGrantedFlag(){
		return PostFundAction.CHECK_GRANTED_FLAG;
	}
	public String grantedClassName(){
		return PostFundAction.GRANTED_CLASS_NAME;
	}
	public String listHql2(){
		return PostFundAction.HQL2;
	}
	public String listHql3(){
		return PostFundAction.HQL3;
	}
	public String listHql4(){
		return PostFundAction.HQL4;
	}
}

