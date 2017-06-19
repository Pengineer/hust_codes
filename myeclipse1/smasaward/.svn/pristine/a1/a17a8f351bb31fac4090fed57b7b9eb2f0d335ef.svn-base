package csdc.service.webService.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import EDU.oswego.cs.dl.util.concurrent.Takable;

import com.sun.jmx.snmp.tasks.Task;
import com.sun.org.apache.bcel.internal.generic.NEW;

import csdc.bean.ProjectApplication;
import csdc.dao.HibernateBaseDao;
import csdc.service.imp.BaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.bean.SynchronizationInfo;
import csdc.tool.execution.Test;
import csdc.tool.execution.importer.SynchronizationMinistryAuditInfo;
import csdc.tool.execution.importer.SynchronizationProjectApplication;
import csdc.tool.execution.importer.SynchronizationProjectData;
import csdc.tool.execution.importer.SynchronizationUniversityInfo;
import csdc.tool.webService.ProjectApplicationRecordResolver;
import csdc.tool.webService.ProjectGrantedRecordResolver;
import csdc.tool.webService.ProjectVariationRecordResolver;
import csdc.tool.webService.RecordResolver;
import csdc.tool.webService.smdb.client.SOAPEnvTool;

/**
 * 客户端调用服务
 * 2014-12-19
 */
public class ClientWebService extends BaseService implements IClientWebService {
	

	
	@Autowired
	protected HibernateBaseDao dao;
	
	/**
	 * 说明：
	 * 1.一次执行一个任务
	 * 2.本层数据处理方法内会改变状态变量
	 * 3.（1）如果发生异常runStatus = 5，相关的状态变量会保留“现场”变量，提供继续从异常位置继续执行；
	 * 	  （2）暂停runStatus = 2，程序目前会在单线程睡眠固定时间后，轮询此标志，如果runStatus = 1 ，则继续；
	 * 	  （3）取消runStatus = 3，程序会抛出特定异常，引起事务回滚；
	 */
	private SynchronizationProjectData currentTaskExecution = null;//当前任务对象
	
	private Map jasonMap = new HashMap();
	private SynchronizationInfo synchronizationInfoSwapObject ;
	private boolean isBusy;//前台根据此标志，进行提示

	private SynchronizationInfo getSynchronizationInfoSwapObject() {
		if (synchronizationInfoSwapObject == null) {
			synchronizationInfoSwapObject = new SynchronizationInfo();
		}
		return synchronizationInfoSwapObject;
	}
	//任务开始变量初始化
	private void taskBeginInitalSetting(long totalNum, int taskType) {
		getSynchronizationInfoSwapObject().setTotalNum(totalNum);
		getSynchronizationInfoSwapObject().setTaskType(taskType);
	}
	/**
	 * 任务状态更新,前后台交互相关参数变量
	 * 目前只接受命令[1:执行(继续)；2：暂停；3：取消；]
	 * 当反馈的runStatus=4时，表示任务完成
	 */
	public Map currentTaskStatusUpdating(int runOrder) {
		//根据前台控制命令，修改状态标识
		SynchronizationInfo synchronizationInfo = currentTaskExecution.updateCurrentSynchronizationInfo(runOrder);
		synchronizationInfo.update();

		//更新状态
		jasonMap.put("totalNum", synchronizationInfo.getTotalNum());
		jasonMap.put("currentNum", synchronizationInfo.getCurrentNum());
		jasonMap.put("runStatus", synchronizationInfo.getRunStatus());//运行状态反馈至前台
		
		jasonMap.put("taskType", synchronizationInfo.getTaskType());
		if (1 == synchronizationInfo.getTaskType()) {//批量获取方式
			jasonMap.put("batchTotal", synchronizationInfo.getBatchTotal());
			jasonMap.put("batchIndex", synchronizationInfo.getBatchIndex());
		}
		return jasonMap;
	}

	
	/**
	 * 在线接口 <br>
	 * 申报数据:requestSmdbProjectApplication<br>
	 * 立项数据:requestSmdbProjectGranted<br>
	 * 变更数据:requestSmdbProjectVariation<br>
	 * 中检数据:requestSmdbProjectMidinspection<br>
	 * 结项数据:requestSmdbProjectEndinspection<br>
	 */

	//在线直接同步，数据直接入库正式表,中间表只保留相关关系
	//只对系统当前年分的数据进行同步：如defaultyear:2015,只同步2015年的申报数据
	public void projectDataSynchronizationOnline(String serviceName, String methodName, 
			String projectType, String year, int fetchSize) {
		SmdbClient smdbClient = SmdbClient.getInstance();
		if (smdbClient.isBusy()) {
			setBusy(true);//一次只执行一个任务
			return;
		} else {
			if (serviceName.equals("smdbService") && methodName.equals("requestProjectApplication")) {
				currentTaskExecution = new SynchronizationProjectApplication(smdbClient, dao, projectType, year, fetchSize);
			} else if (serviceName.equals("smdbService") && methodName.equals("requestSynchronizationUniversity")) {
				new SynchronizationUniversityInfo(smdbClient, dao).excute();
			} else if (serviceName.equals("smdbService") && methodName.equals("updateProjectApplicationMinistryAuditInfo")) {
				new SynchronizationMinistryAuditInfo(smdbClient, dao, year).excute();
			}
			currentTaskExecution.excute();
			if (!smdbClient.isBusy()) {
				setBusy(false);//一次只执行一个任务
			}
		
		}
	}


	/**
	 * 往年数据数据同步调用（fix）
	 * @param serviceName
	 * @param methodName
	 * @throws Exception
	 */
	public void fixProjectDataSynchronization(String serviceName, String methodName, String year) throws Exception {
		//修复数据，单例获取组件
		SmdbClient smdbClient = SmdbClient.getInstance();
		if (serviceName.equals("smdbService") && methodName.equals("requestProjectApplicationFix")) {
//			fixProjectApplication(smdbClient,"SmasService", "requestUniqueProjectApplication");
			System.out.println("申报数据ok");
		} else if (serviceName.equals("smdbService") && methodName.equals("requestProjectGrantedFix")) {
//			fixProjectGranted("SmasService", "requestUniqueProjectGranted" );//服务名称
			System.out.println("立项数据ok");
		} else if (serviceName.equals("smdbService") && methodName.equals("requestProjectVariationFix")) {
//			fixProjectVariation("SmasService", "requestBatchProjectVariation" );
			System.out.println("变更数据ok");
		} else if (serviceName.equals("smdbService") && methodName.equals("requestProjectMidinspectionFix")) {
//			fixProjectMidinspection("SmasService", "requestBatchProjectMidinspection");
			System.out.println("中检数据ok");
		} else if (serviceName.equals("smdbService") && methodName.equals("requestProjectEndinspectionFix")) {
//			fixProjectEndinspection("SmasService", "requestBatchProjectEndinspection");
			System.out.println("结项数据ok");
		}else if (serviceName.equals("smdbService") && methodName.equals("fixSmasProgram")) {
			//smas与匹配同步
//			new SynchronizationFixSmasProgram(smdbService, dao, year).excute();
			new Test(dao).excute();

		} 
	}
	/**
	 * 数据同步 --> 往年数据
	 * "SmasService"
	 */
	@SuppressWarnings("unchecked")
	private void fixProjectApplication(SmdbClient smdbClient, String serviceName, String methodName) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
		List<String> badIdsList = new ArrayList<String>();
		Map argsMap = new HashMap<String, String>();
		List<Object[]> paList = dao.query("select pa.projectName, pa.director, pa.type, pa.id, pa.year from ProjectApplication pa where pa.isImported is null and pa.projectType != '专项任务' order by pa.type");
//		List<Object[]> paList = dao.query("select pa.projectName, pa.director, pa.type, pa.id, pa.year from ProjectApplication pa where pa.isImported is null and pa.projectType != '专项任务' and pa.type = 'instp'");

		int total = paList.size();
		for(int i = 0; i < paList.size(); i++) {
			Object[] o = paList.get(i);
			String[] pa = new String[o.length];
			for (int j = 0; j < o.length; j++) {
				if (o[j] instanceof String){
					pa[j] = (String) o[j];
				} else if (o[j] instanceof Integer) {
					int temp = (Integer) o[j];
					pa[j] = String.valueOf(temp);
				}
			}
			String projectName = pa[0].trim();
			String applicantName = pa[1].trim();
			String projectType =  pa[2];
			String id = pa[3];
			String year = pa[4];
			//撤项 —— 重复数据,这些重复数据满足条件，但是不需要进行处理同步立项信息
			if(id.equals("126818")||id.equals("123083")||id.equals("137887")||id.equals("102259")||id.equals("120592")||id.equals("95926")||id.equals("109111")||id.equals("107708") ){//过滤重复申报数据id
				continue;
			}
			argsMap.put("projectName", projectName);
			argsMap.put("applicantName", applicantName);
			argsMap.put("projectType", projectType);
			argsMap.put("year", year);
			String backData = smdbClient.invokeDirectory("fwzx", "csdc702", serviceName, methodName, argsMap);
			if (null == backData) {
				throw new RuntimeException("执行异常");//事务回滚
			} else if(backData.equals("busy")) {
				//3分钟后再次发送同样请求
				Thread.sleep(smdbClient.WaitUnitTime);
				i--;//
			} else {
				//解析服务端返回信息
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				String contentDomXml = null;
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					contentDomXml = (String) backMap.get("content");
					Document recordsDom = DocumentHelper.parseText(contentDomXml);
					Element rootElement = recordsDom.getRootElement();
					Element item = (Element) rootElement.element("records").elements("item").get(0);
					if (item != null) {
						try {
							//数据先不入库，打印文件中核查后在入库
							new ProjectApplicationRecordResolver().parseFix(item, sdf, dao, projectType, id);
						} catch (Exception e) {
							badIdsList.add(id);
						}
					}
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据");
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					badIdsList.add((String) backMap.get("content"));
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")) {
					badIdsList.add(id);
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else {
					badIdsList.add(id);
				}
				if (i == 1000) {
					dao.flush();
					dao.clear();
				}
			}
		}
		printErrorApplicationIDsString(badIdsList);//打印有问题的条目
	}

	//修复立项数据-批量获取逐条入库[依据smas端最终审核通过的申报id，从中间表中取出对应的smdb申报id，通过web服务获取逐条立项数据]
	/**
	 * 数据同步 --> 往年数据
	 * @param serviceName
	 * @param methodName
	 * @throws Exception
	 */
	private void fixProjectGranted(SmdbClient smdbClient, String serviceName, String methodName) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
		List<String> badIdsList = new ArrayList<String>();
		Map argsMap = new HashMap<String, String>();
		List<Object[]> paList = dao.query("select smdbpa.smdbApplID, pa.type, pa.id from ProjectApplication pa,SmdbProjectApplication smdbpa where pa.projectType != '专项任务' and pa.id = smdbpa.smasApplID and pa.isImported is not null and pa.finalAuditResult = 2 ");
		/*修复重复申报引入的问题
		 * String idprocess = "126818;123901;123083;123090;137449;137887;102259;120592;120605;109111;95470;95926;121541;109011;117764;107708;";
		String[] idsArray = idprocess.substring(0, idprocess.length() - 1).split(";");
		Map temMap = new HashMap<String, String[]>();
		temMap.put("idsArray", idsArray);
		List<Object[]> paList = dao.query("select smdbpa.smdbApplID, pa.type, pa.id from ProjectApplication pa,SmdbProjectApplication smdbpa where pa.projectType != '专项任务' and pa.id = smdbpa.smasApplID and pa.isImported is not null and pa.finalAuditResult = 2 " +
				"and pa.id in (:idsArray)", temMap);
		*/
		int total = paList.size();
		for(int i = 0; i < paList.size(); i++) {
			Object[] obj = paList.get(i);
			String smdbApplicationID = (String) obj[0];
			String projectType = (String) obj[1];
			String smasAppid = (String) obj[2];
			//撤项 —— 重复数据,这些重复数据满足条件，但是不许要处理同步立项信息
			if(smasAppid.equals("126818")||smasAppid.equals("123083")||smasAppid.equals("137887")||smasAppid.equals("102259")||smasAppid.equals("120592")||smasAppid.equals("95926")||smasAppid.equals("109111")||smasAppid.equals("107708") ){//过滤重复申报数据id
				continue;
			}
			//如果已经存获取直接跳过
			Map countMap = new HashMap<String, String>();
			countMap.put("smdbApplID", smdbApplicationID);
			int exits = (int) dao.count("from SmdbProjectGranted smdbpg left join smdbpg.smdbApplication smdbpa where smdbpa.smdbApplID = :smdbApplID", countMap);
			if(exits >= 1) {
				System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据:本数据已在库中");
				continue;
			}
			argsMap.put("projectApplicationID", smdbApplicationID);
			argsMap.put("projectType", projectType);
			String backData = smdbClient.invokeDirectory("fwzx", "csdc702", serviceName, methodName, argsMap);
			if (null == backData) {
				throw new RuntimeException("执行异常");//事务回滚
			} else if(backData.equals("busy")) {
				//3分钟后再次发送同样请求
				Thread.sleep(smdbClient.WaitUnitTime);
				i--;//
			} else {
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				String contentDomXml = null;
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					contentDomXml = (String) backMap.get("content");
					Document recordsDom = DocumentHelper.parseText(contentDomXml);
					Element rootElement = recordsDom.getRootElement();
					Element item = (Element) rootElement.element("records").elements("item").get(0);
					if (item != null) {
						try {
							new ProjectGrantedRecordResolver().parse(item, sdf, dao, projectType, badIdsList);
						} catch (Exception e) {
							badIdsList.add(smasAppid + "[异常ID]");
							continue;
						}
					}
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据");
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					badIdsList.add((String) backMap.get("content") + "[返回内容]");
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")) {
					badIdsList.add(smasAppid);
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else {
					badIdsList.add(smasAppid + "[其他原因]");
				}
				if (i % 2000 == 0) {
					dao.flush();
					dao.clear();
				}
			}
		}
		printErrorApplicationIDsString(badIdsList);//打印有问题的条目
	}
	//根据目前规范的立项项目（排除专项任务项目）为蓝本，用smdb端的立项项目id，项目类型完成本地变更项目同步工作
	/**
	 * 数据同步 --> 往年数据
	 * @param serviceName
	 * @param methodName
	 * @throws Exception
	 */
	private void fixProjectVariation(SmdbClient smdbClient, String serviceName, String methodName) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
		List<String> badIdsList = new ArrayList<String>();
		Map argsMap = new HashMap<String, String>();
		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id from ProjectGranted pg, SmdbProjectGranted smdbpg where (pg.subtype != '专项任务' or pg.subtype is null) and pg.id = smdbpg.smasGrantedID ");
		
//		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id from ProjectGranted pg, SmdbProjectGranted smdbpg where (pg.subtype != '专项任务' or pg.subtype is null) and pg.id = smdbpg.smasGrantedID and pg.type = 'instp' ");// 测试立项基地项目对应变更

		//入库异常的smas端立项id
//		String[] processIds = {"4028d8824b723d3d014b725a819e0016","4028d8824b723d3d014b726710e224be","4028d8824b723d3d014b726d7fe137e2","4028d8824b723d3d014b726faa493eea","4028d8824b723d3d014b72758db950ca","4028d8824b723d3d014b7277618b5642"};
//		Map argMap = new HashMap<String, List>();
//		argMap.put("processIds", processIds);
//		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id  from ProjectGranted pg, SmdbProjectGranted smdbpg where pg.subtype != '专项任务' and pg.id = smdbpg.smasGrantedID and pg.id in (:processIds)", argMap);
		int total = paList.size();
		for(int i = 0; i < paList.size(); i++) {
			Object[] obj = paList.get(i);
			String smdbGrantedID = (String) obj[0];
			String projectType = (String) obj[1];
			String smasGrantedID = (String) obj[2];
			//因为是一对多的关系，不能直接判断此立项项目的变更数据是否存在表中
			//只能在入库时判断如果存在则不进行数据的入库操作
			argsMap.put("projectGrantedID", smdbGrantedID);
			argsMap.put("projectType", projectType);
			String backData = smdbClient.invokeDirectory("fwzx", "csdc702", serviceName, methodName, argsMap);
			if (null == backData) {
				throw new RuntimeException("执行异常");//事务回滚
			} else if(backData.equals("busy")) {
				//3分钟后再次发送同样请求
				Thread.sleep(smdbClient.WaitUnitTime);
				i--;//
			} else {
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				String contentDomXml = null;
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					contentDomXml = (String) backMap.get("content");
					Document recordsDom = DocumentHelper.parseText(contentDomXml);
					Element rootElement = recordsDom.getRootElement();
					//返回多条处理
					List<Element> itemList = rootElement.element("records").elements("item");
					for (Element item : itemList) {
						if (item != null) {
							try {
								new ProjectVariationRecordResolver().parse(item, sdf, dao, projectType, badIdsList);
							} catch (Exception e) {
								badIdsList.add(smasGrantedID + "[入库异常smas端ID]");
								continue;
							}
						}
					}
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据（入库" + itemList.size() + "条)");
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					badIdsList.add((String) backMap.get("content") + "[返回内容信息提示]");
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")) {
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据：(notice)" + backMap.get("content") );
				} else {
					badIdsList.add(smasGrantedID + "[其他原因]");
				}
				if (i % 2000 == 0) {
					dao.flush();
					dao.clear();
				}
			}
		}
		printErrorApplicationIDsString(badIdsList);//打印入库问题信息
	}
	//根据目前规范的立项项目（排除专项任务项目）为蓝本，用smdb端的立项项目id，项目类型完成本地中检项目的同步工作
	/**
	 * 数据同步 --> 往年数据
	 * @param serviceName
	 * @param methodName
	 * @throws Exception
	 */
	private void fixProjectMidinspection(SmdbClient smdbClient, String serviceName, String methodName) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
		List<String> badIdsList = new ArrayList<String>();
		Map argsMap = new HashMap<String, String>();
		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id  from ProjectGranted pg, SmdbProjectGranted smdbpg where (pg.subtype != '专项任务' or pg.subtype is null) and pg.id = smdbpg.smasGrantedID ");
//		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id  from ProjectGranted pg, SmdbProjectGranted smdbpg where (pg.subtype != '专项任务' or pg.subtype is null) and pg.id = smdbpg.smasGrantedID and pg.type = 'instp' ");
		//入库异常的smas端立项id
		int total = paList.size();
		for(int i = 0; i < paList.size(); i++) {
			Object[] obj = paList.get(i);
			String smdbGrantedID = (String) obj[0];
			String projectType = (String) obj[1];
			String smasGrantedID = (String) obj[2];
			//因为是一对多的关系，不能直接判断此立项项目的变更数据是否存在表中
			//只能在入库是判断如果存在则不进行数据的入库操作
			argsMap.put("projectGrantedID", smdbGrantedID);
			argsMap.put("projectType", projectType);
			String backData = smdbClient.invokeDirectory("fwzx", "csdc702", serviceName, methodName, argsMap);
			if (null == backData) {
				throw new RuntimeException("执行异常");//事务回滚
			} else if(backData.equals("busy")) {
				//3分钟后再次发送同样请求
				Thread.sleep(smdbClient.WaitUnitTime);
				i--;//
			} else {
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				String contentDomXml = null;
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					contentDomXml = (String) backMap.get("content");
					Document recordsDom = DocumentHelper.parseText(contentDomXml);
					Element rootElement = recordsDom.getRootElement();
					//返回多条处理
					List<Element> itemList = rootElement.element("records").elements("item");
					for (Element item : itemList) {
						if (item != null) {
							try {
								new ProjectVariationRecordResolver().parse(item, sdf, dao, projectType, badIdsList);
							} catch (Exception e) {
								badIdsList.add(smasGrantedID + "[入库异常smas端ID]");
								continue;
							}
						}
					}
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据（入库" + itemList.size() + "条)");
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					badIdsList.add((String) backMap.get("content") + "[返回内容信息提示]");
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")) {
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据：(notice)" + backMap.get("content") );
				} else {
					badIdsList.add(smasGrantedID + "[其他原因]");
				}
				if (i % 2000 == 0) {
					dao.flush();
					dao.clear();
				}
			}
		}
		printErrorApplicationIDsString(badIdsList);//打印入库问题信息
	}
	//根据目前规范的立项项目（排除专项“任务项目”）为蓝本，用smdb端的立项项目id，项目类型完成本地项目的同步工作
	/**
	 * 数据同步 --> 往年数据
	 * @param serviceName
	 * @param methodName
	 * @throws Exception
	 */
	private void fixProjectEndinspection(SmdbClient smdbClient, String serviceName, String methodName) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
		List<String> badIdsList = new ArrayList<String>();
		Map argsMap = new HashMap<String, String>();
		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id  from ProjectGranted pg, SmdbProjectGranted smdbpg where (pg.subtype != '专项任务' or pg.subtype is null) and pg.id = smdbpg.smasGrantedID ");
//		List<Object[]> paList = dao.query("select smdbpg.smdbGrantedID, pg.type, pg.id  from ProjectGranted pg, SmdbProjectGranted smdbpg where (pg.subtype != '专项任务' or pg.subtype is null) and pg.id = smdbpg.smasGrantedID and pg.type = 'instp' ");
		//入库异常的smas端立项id
		int total = paList.size();
		for(int i = 0; i < paList.size(); i++) {
			Object[] obj = paList.get(i);
			String smdbGrantedID = (String) obj[0];
			String projectType = (String) obj[1];
			String smasGrantedID = (String) obj[2];
			/**
			 * 特殊数据处理
			 */
//			smdbGrantedID = "4028d88a2d354302012d3546c9884753";
			//因为是一对多的关系，不能直接判断此立项项目的变更数据是否存在表中
			//只能在入库是判断如果存在则不进行数据的入库操作
			argsMap.put("projectGrantedID", smdbGrantedID);
			argsMap.put("projectType", projectType);
			String backData = smdbClient.invokeDirectory("fwzx", "csdc702", serviceName, methodName, argsMap);
			if (null == backData) {
				throw new RuntimeException("执行异常");//事务回滚
			} else if(backData.equals("busy")) {
				//3分钟后再次发送同样请求
				Thread.sleep(smdbClient.WaitUnitTime);
				i--;//
			} else {
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				String contentDomXml = null;
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					contentDomXml = (String) backMap.get("content");
					Document recordsDom = DocumentHelper.parseText(contentDomXml);
					Element rootElement = recordsDom.getRootElement();
					//返回多条处理
					List<Element> itemList = rootElement.element("records").elements("item");
					for (Element item : itemList) {
						if (item != null) {
							try {
								new ProjectVariationRecordResolver().parse(item, sdf, dao, projectType, badIdsList);
							} catch (Exception e) {
								badIdsList.add(smasGrantedID + "[入库异常smas端ID]");
								continue;
							}
						}
					}
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据（入库" + itemList.size() + "条)");
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					badIdsList.add((String) backMap.get("content") + "[返回内容信息提示]");
					System.err.println("\n######\n" + backMap.get("content") + "\n#######");
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")) {
					System.out.println(projectType + "/第" + (i+1) + "次同步完成，总共有" + total + "条数据：(notice)" + backMap.get("content") );
				} else {
					badIdsList.add(smasGrantedID + "[其他原因]");
				}
				if (i % 2000 == 0) {
					dao.flush();
					dao.clear();
				}
			}
			
		}
		printErrorApplicationIDsString(badIdsList);//打印入库问题信息
	}
	
	/**
	 * 修复程序 --> 只用来进行修复调用
	 * （执行一次）
	 */
	@Transactional
	public void fixContentDataMethod(String serviceName, String methodName) {
		List<String> paList = dao.query("select pa.id  from ProjectGranted pg left join pg.application pa, SmdbProjectGranted smdbpg where pg.subtype != '专项任务' and pg.id = smdbpg.smasGrantedID ");
		//入库异常的smas端立项id
		int total = paList.size();
		for(int i = 0; i < paList.size(); i++) {
			String smasAppliID = paList.get(i);
			
			ProjectApplication pa = (ProjectApplication) dao.query(ProjectApplication.class, smasAppliID);
			pa.setIsGranted(1);
			dao.addOrModify(pa);
			
			if (i % 2000 == 0) {
				dao.flush();
				dao.clear();
			}
			System.out.println(smasAppliID + "/第" + (i+1) + "次修改，总共有" + total + "条数据");
		}
	}
	
	private void fileOutput(String file, String serviceName, String methodName, int i) throws IOException{
		String filename;
		if(i>0){			
			filename = ApplicationContainer.sc.getRealPath("/system/interfaces/smdbResource/" + serviceName + "_" + methodName + "_" + i + ".xml");
		}else filename = ApplicationContainer.sc.getRealPath("/system/interfaces/smdbResource/" + serviceName + "_" + methodName + ".xml");
		FileOutputStream output = new FileOutputStream(filename);
		Writer out = new OutputStreamWriter(output, "utf-8");
		out.write(file);
		out.close();
		output.close();
	}
	
	//数据直接入库
	@Transactional
	private void importerSmdbWebServicesXmlData_fixA(String serviceName, String methodName, String projectType, String domContent, int i, List badIdsList, RecordResolver parser) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
		Document recordsDom = DocumentHelper.parseText(domContent);
		Element rootElement = recordsDom.getRootElement();
		List<Element> elemtList = rootElement.element("records").elements("item");
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				parser.parse(item, sdf, dao, projectType, badIdsList);
				System.out.println(serviceName + "_" + methodName + "_" + projectType + "：第" + i + "批入库信息：" + j + "/" + elemtList.size() + ";");
			}
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 打印中的结果并清空
	 * @param badIdsList
	 */
	private void printErrorApplicationIDsString(List<String> badIdsList) {
		if (!badIdsList.isEmpty()) {
			//打印id
			StringBuffer badidStringBuffer = new StringBuffer();
			for (int i = 0; i < badIdsList.size(); i++) {
				badidStringBuffer.append(badIdsList.get(i) );
				if (i != badIdsList.size() - 1) {
					badidStringBuffer.append(";");//英文间隔字符
				}
			}
			System.out.println(badidStringBuffer.toString());//打印错误的id
			//清空
			badIdsList.clear();
		} else {
			System.out.println("*****没有入库异常数据*****");
		}
	}
	protected String getArgumentByTag(Element elementRoot, String tag) {
		 String context = null;
		 context = elementRoot.elementText(tag);
		 if (context != null) {
			return context;
		}
		return "null";
	}
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}


}
