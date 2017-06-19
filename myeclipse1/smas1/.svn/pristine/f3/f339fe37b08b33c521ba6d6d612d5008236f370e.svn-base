package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import csdc.dao.IHibernateBaseDao;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.webService.smdb.client.SOAPEnvTool;


/**
 * 按照老师要求： 在smas中入库部级初评审核结果，然后将部级审核后的信息同步至smdb
 * 说明：
 * （1）根据smas中间表对应的smdb申请id记录，对smdb项目进行逐条更新
 */
public class SynchronizationMinistryAuditInfo extends SynchronizationProjectData {

	private SmdbClient smdbClient;
	private String year; // 同步部级审核信息年份
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法

	protected List<String> badItemsList; //无法解析的id集合,最终打印出来，核查数据
	private Map<String, String> argsMap;//数据同步参数
	public SynchronizationMinistryAuditInfo() {
		
	}
	/**
	 * 项目申请数据同步
	 * @param smdbService
	 * @param dao
	 * @param projectType
	 * @param year
	 * @param fetchSize
	 */
	public SynchronizationMinistryAuditInfo(SmdbClient smdbClient, IHibernateBaseDao dao, String year) {
		this.smdbClient = smdbClient;
		this.year = year;
		super.dao = dao;

		if (argsMap == null) {
			argsMap = new HashMap<String, String>();
		} else {
			argsMap.clear();
		}
		if (badItemsList == null) {
			badItemsList = new LinkedList<String>();
		}

	}

	@Override
	protected void work() throws Throwable {
		Long begin = System.currentTimeMillis();
		String type = "general";
		Map argumentMap = new HashMap<String, Object>();
		argumentMap.put("year", Integer.parseInt(year));
		argumentMap.put("type", type);
		//查询部级最终审核结果信息
		/**
		 * 部级审核人员信息与User表中的人员信息,且smas中的部级审核人员与smdb中的部级审核人员一致
		 */
		String hqlfirstAudit = "select smdbApp.smasApplID, smdbApp.smdbApplID, p.ministryAuditorName, p.ministryAuditStatus, p.ministryAuditResult, p.ministryAuditOpinion, p.ministryAuditDate, p.finalAuditOpinionFeedback from SmdbProjectApplication smdbApp, ProjectApplication p where p.type = :type and p.year = :year " +
				" and p.id = smdbApp.smasApplID ";
	
		List<Object[]> resultList = dao.query(hqlfirstAudit, argumentMap);
		if (!resultList.isEmpty()) {
			
			//建立连接
			String responseConnestion = smdbClient.OpenSecurityConnection("fwzx", "csdc702", "AES");
			System.out.println(responseConnestion);
			
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println("smas中  " + year +" 年 " + type +"类型项目共有：" + resultList.size() + "个需要同步（部级）初评结果信息");
			for (int j = 0; j < resultList.size(); j++) {
				System.out.print(sdf.format(new Date()) + ":开始更新 " + (j+1) + "/" + resultList.size());
				Object[] o = resultList.get(j);
				//统一数据类型
				String[] str = new String[o.length];
				for (int i = 0; i < o.length ; i++) {
					if (o[i] instanceof String) {
						str[i] = (String) o[i];
					} else if (o[i] instanceof Integer) {
						int temp = (Integer) o[i];
						str[i] = String.valueOf(temp);
					} else if (o[i] instanceof Double) {
						str[i] = String.valueOf(o[i]);
					} else if (o[i] instanceof Date) {
						str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
					} else if (o[i] == null) {
						str[i] = "null";//当对象为空时
					}
				}
				String smasAppID = str[0];
				String smdbAppID = str[1];
				String ministryAuditorName = str[2];
				String ministryAuditStatus = str[3];
				String ministryAuditResult = str[4];
				
				String ministryAuditOpinion = str[5];
				String ministryAuditDate = str[6];
				String finalAuditOpinionFeedback = str[7];

				if (smdbAppID == null ||smdbAppID.equals("")) {
					badItemsList.add(smasAppID + "(" + smdbAppID+ ")");
					continue;
				}
				//其他参数，若为空，则填充“null”
				argsMap.put("projectApplicationID",smdbAppID);
				argsMap.put("ministryAuditorName",ministryAuditorName);
				argsMap.put("ministryAuditStatus",ministryAuditStatus);
				argsMap.put("ministryAuditResult",ministryAuditResult);
				argsMap.put("ministryAuditOpinion",ministryAuditOpinion);
				argsMap.put("ministryAuditDate",ministryAuditDate);
				argsMap.put("finalAuditOpinionFeedback",finalAuditOpinionFeedback);
				
				String backData = smdbClient.invokeSecurityService("fwzx", "csdc702", "SmasService", "updateProjectApplicationMinistryAuditInfo", argsMap);
				if (null == backData) {
					throw new RuntimeException("执行异常撤销");//事务回滚
				} else if(backData.equals("busy")) {
					//已在任务初始化前进行判定，不会出现另一个任务处于等待状态情况
					Thread.sleep(smdbClient.WaitUnitTime*6);
					j--;
				} else {
					//解析服务端返回信息
					//调用更新接口，反馈信息时notice且内容是success 或者error类型
					Map backMap = SOAPEnvTool.parseNormalResponse(backData);
					if (backMap.get("type") != null && backMap.get("type").equals("data")) {
						//不会返回 data类型数据
					} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
						//成功处理:返回类型是"success"且返回内容也是"success"
						if (backMap.get("content").equals("success")) {
							System.out.println("    处理状态：success" );
						}
					} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
						//服务端更新异常
						badItemsList.add(smasAppID + "(" + smdbAppID+ ")");
						System.out.println("    处理状态：error, 更新失败 ");
					}
				}
			}
			
			//释放连接
			String requestClose = smdbClient.closeSecurityConnection();
			System.out.println(requestClose);
			
			Long end = System.currentTimeMillis();
			System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
			//输出打印无法解析的信息
			printRecordedListContent(badItemsList);
		}
	}
	
}
