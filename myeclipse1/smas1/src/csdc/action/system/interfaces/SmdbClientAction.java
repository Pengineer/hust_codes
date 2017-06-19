package csdc.action.system.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.sun.mail.imap.protocol.Status; 

import csdc.action.BaseAction;
import csdc.service.webService.client.IClientWebService;

public class SmdbClientAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IClientWebService clientWebService;
	//SMDB接口参数
	private int dataTypeTag;
	private int projectTypeTag;
	private String projectYear;
	/**
	 * 接口参数设置
	 */
	private String serviceName = "smdbService";
	private String methodName;//方法名
	private String projectType;//项目类型
	private Integer year;//项目年度
	
	private Integer fetchSize = 500;//申请数据每次取的数量建议（500~1000）
	private long costTime;// 
	/**
	 * 在线同步数据
	 * 数据 smdb --> smas 数据流向
	 * @return
	 */
	public String syncDataOnline() {
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		long begin = System.currentTimeMillis();
		methodName = _convertDataType1(dataTypeTag);
		projectType = _convertProjectType(projectTypeTag);
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		String defaultYear = session.get("defaultYear").toString();
		int defaultYearInt = Integer.parseInt(defaultYear);	//系统当前年
		//只同步当前年份的数据，所以当前年份的字段信息作用不大
		year = 2015;//currentYear，可以后续放在前台设置
		try {
			clientWebService.projectDataSynchronizationOnline(serviceName, methodName, projectType, year.toString(), fetchSize );
		} catch (Exception e) {
			e.printStackTrace();
		}
		costTime = new Date().getTime() - begin;
		return SUCCESS;
	}
	
	/**
	 * 同步往年数据
	 * 数据 以smas为基础，将向smdb获取对应的信息,将往年信息补全；
	 * 2015年度信息同步工作
	 * @return
	 */
	public String syncDataFix() {
		year = 2015;//前台手动设定
		long begin = System.currentTimeMillis();
		methodName = _convertDataType2(dataTypeTag);
		projectType = _convertProjectType(projectTypeTag);
		try {
			//往年数据同步处理
			clientWebService.fixProjectDataSynchronization(serviceName, methodName, year.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		costTime = new Date().getTime() - begin;
		return SUCCESS;
	}

	private String _convertProjectType(int tag) {
		String projectType = null;
		switch (tag) {
		case 1:
			projectType = "general";
			break;
		case 2:
			projectType = "instp";
			break;
		default:
			projectType = "null";
			break;
		}
		return projectType;
	}
	
	private String _convertDataType1(int tag) {
		String methodName = null;
		switch (tag) {
		case 1:
			methodName = "requestProjectApplication";
			break;
		case 2:
			methodName = "requestProjectGranted";
			break;
		case 3:
			methodName = "requestProjectVariation";
			break;
		case 4:
			methodName = "requestProjectMidinspection";
			break;
		case 5:
			methodName = "requestProjectEndinspection";
			break;
		case 6:
			methodName = "requestSynchronizationUniversity";
			break;
		case 7:
			methodName = "updateProjectApplicationMinistryAuditInfo";
			break;
		default:
			methodName = "null";
			break;
		}
		return methodName;
	}
	
	private String _convertDataType2(int tag) {
		String methodName = null;
		switch (tag) {
		case 1:
			methodName = "requestProjectApplicationFix";
			break;
		case 2:
			methodName = "requestProjectGrantedFix";
			break;
		case 3:
			methodName = "requestProjectVariationFix";
			break;
		case 4:
			methodName = "requestProjectMidinspectionFix";
			break;
		case 5:
			methodName = "requestProjectEndinspectionFix";
			break;		
		case 6:
			methodName = "fixSmasProgram";
			break;
			
		default:
			methodName = "null";
			break;
		}
		return methodName;
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}


	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	@Override
	public String pageName() {
		return null;
	}

	@Override
	public String[] column() {
		return null;
	}

	@Override
	public String dateFormat() {
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return null;
	}

	public IClientWebService getClientWebService() {
		return clientWebService;
	}

	public void setClientWebService(IClientWebService clientWebService) {
		this.clientWebService = clientWebService;
	}

	public int getDataTypeTag() {
		return dataTypeTag;
	}

	public void setDataTypeTag(int dataTypeTag) {
		this.dataTypeTag = dataTypeTag;
	}

	public int getProjectTypeTag() {
		return projectTypeTag;
	}

	public void setProjectTypeTag(int projectTypeTag) {
		this.projectTypeTag = projectTypeTag;
	}

	public String getProjectYear() {
		return projectYear;
	}

	public void setProjectYear(String projectYear) {
		this.projectYear = projectYear;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

}
