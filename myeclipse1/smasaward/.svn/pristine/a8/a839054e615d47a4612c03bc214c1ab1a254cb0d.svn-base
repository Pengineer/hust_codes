package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.taskdefs.Sleep;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.omg.CORBA.Current;
import org.springframework.stereotype.Component;

import csdc.dao.IHibernateBaseDao;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.bean.SynchronizationInfo;
import csdc.tool.webService.ProjectApplicationRecordResolver;
import csdc.tool.webService.smdb.client.SOAPEnvTool;

/**
 * smas同步的流程
 * 1.smdb进行查重分析
 * 2.smas同步项目申报（可以不包含初评查重信息）
 * 3.smas“高校更新”
 * 4.smas同步“部级查重结果同步”
 * 5.smas专家匹配
 */
@Component
public class SynchronizationProjectApplication extends SynchronizationProjectData {
	
	private SynchronizationInfo synchronizationInfo = null;//同步的各种变量参数以及控制信息的中间对象

	private int fetchSize;//每次同步的条数（前台设定）
	private int fetchIndex; //基于1开始
	
	private SmdbClient smdbClient;
	private String projectType; //前台传递
	private String year; //前台传递

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
	protected List<String> badItemsList; //无法解析的id集合,最终打印出来，核查数据
	//本年度专家库中专家申报项目的专家统计
	HashSet<String> existingExperts;
	private Map<String, String> argsMap;//数据同步参数

	protected Tools tools;//所有方便的数据同步工具，本次同步用到专家集合，近初始化专家集合即可

	public SynchronizationProjectApplication() {
		
	}
	/**
	 * 项目申请数据同步
	 * @param smdbClient
	 * @param dao
	 * @param projectType
	 * @param year
	 * @param fetchSize
	 */
	public SynchronizationProjectApplication(SmdbClient smdbClient, IHibernateBaseDao dao, String projectType, String year, int fetchSize) {
		this.smdbClient = smdbClient;
		super.dao = dao;
		
		this.projectType = projectType;
		this.year = year;
		this.fetchSize = fetchSize; 
		
		this.fetchIndex = 1; 
		if (argsMap == null) {
			argsMap = new HashMap<String, String>();
		} else {
			argsMap.clear();
		}
		if (badItemsList == null) {
			badItemsList = new LinkedList<String>();
		}
		if (existingExperts == null) {
			existingExperts = new HashSet<String>();
		}
		
		if (synchronizationInfo == null) {
			synchronizationInfo = new SynchronizationInfo();
			
		} else {
			synchronizationInfo.setInitialization();
		}
		//执行
		init();
	}
	
	private void init() {
		//初始化，数据库中专家map信息： universitycode + applicantName ： Expert 
//		long begin = System.currentTimeMillis();
//		if (tools == null) {
//			tools = new Tools(dao);
//		}
//		System.out.println("初始化一般项目同步工具 ··· ");
//		tools.initExpertMap();//专家代码集合
//		tools.initNameUnivMap();//高校代码集合
//		System.out.println("一般项目同步工具 初始化完毕,用时： " + 	(new Date().getTime() - begin));
		 
	}
	@Override
	protected void work() throws Throwable {
		boolean unDone = true;//获取所有数据
		
		argsMap.put("projectType", projectType);
		argsMap.put("year", year);
		argsMap.put("fetchSize",Integer.toString(fetchSize));//500~ 1000
		argsMap.put("counts", Integer.toString(fetchIndex));
	//-------------任务类型设置，总数大小必须请求一次才能获取-------------
		synchronizationInfo.setTaskType(1);
		synchronizationInfo.setBatchIndex(fetchIndex);
		synchronizationInfo.setBatchSize(fetchSize);
	//导入一般项目申报
		do {
	//-------------控制逻辑-------------
			//在每次服务调用之前响应控制命令操作 [1:执行(继续)；2：暂停；3：取消] ,目前系统设计接受以上三种控制命令
			if (2 == synchronizationInfo.getRunStatus()) {//1个小时
				//2：暂停,等待时长1小时
				for (int i = 0; i < smdbClient.WaitTime1H; i++) {
					if (1 == synchronizationInfo.getRunStatus()) {
						//1:执行（继续）
						break;
					}
					if (i == (60*60 - 1)) {
						throw new RuntimeException("任务撤销");
					}
					//TODO 为避免session长时间不响应而销毁，需要后续根据建立跳动包发送情况
					//直接服务调用则不需要这方面考虑
					Thread.sleep(smdbClient.WaitUnitTime);
				}
				
			} else if (3 == synchronizationInfo.getRunStatus()) {
				//3：取消(重新开始状态)
				
				throw new RuntimeException("任务撤销");//事务回滚
			}
			if (1 != synchronizationInfo.getRunStatus()) {
				
				throw new RuntimeException("服务调用前撤销");//事务回滚
			}
	//-------------服务调用-------------
			Long begin = System.currentTimeMillis();
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println(sdf.format(new Date()) + ":开始获取第 " + fetchIndex + " 批数据..." );
			String backData = smdbClient.invokeDirectory("fwzx", "csdc702", "SmasService", "requestSmdbProjectApplication", argsMap);
			if (null == backData) {
				throw new RuntimeException("执行期间异常撤销");//事务回滚
			} else if(backData.equals("busy")) {
				Thread.sleep(smdbClient.WaitUnitTime);
			} else {
				//解析服务端返回信息
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					//返回的是Element类型数据
					Element contentElement = (Element) backMap.get("content");
					//取出本次获取的records Element元素
					Element recordsElement = (Element) contentElement.selectNodes("//records").get(0);
					Element totalNumElement = (Element)contentElement.selectNodes("//totalNum").get(0);
					String totalNumString = totalNumElement.getText();
					if(totalNumString != null) {
						synchronizationInfo.setTotalNum(Long.parseLong(totalNumString));
					}
					//批量入库
					persistBatchProjectDate(recordsElement);
					Long end = System.currentTimeMillis();
					System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
					
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
					if (backMap.get("content").equals("success")) {
						unDone = false;
						synchronizationInfo.setRunStatus(4);
						System.out.println("数据同步工作完毕！");
					}
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					throw new RuntimeException((String)backMap.get("content"));
				}
				//修改请求条件
				setSynchronizationCondition();
			}
		} while (unDone);
		
		//输出无法解析的信息
		printRecordedListContent(badItemsList);
		//输出本年度专家申报统计信息
		System.out.println("本年度数据库中申报项目信息统计：\n 共：" +  existingExperts.size() + "个专家申报本年度 " + projectType +" 类型项目！");

	}

	private void setSynchronizationCondition() {
		int nextIndex = ++fetchIndex;
		//保持同步更新
		argsMap.put("counts", Integer.toString(nextIndex));
		synchronizationInfo.setBatchIndex(nextIndex);
	}
	/**
	 * 处理在线通过的数据信息，逐条持久化数据库并记录中间表
	 * @param contentXmlString
	 * @throws Exception 
	 */
	private void persistBatchProjectDate(Element contentElement) throws Exception {
		//数据处理
		List<Element> elemtList = contentElement.elements("item");
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				//执行新数据同步
//				new ProjectApplicationRecordResolver(item, sdf, tools, dao, projectType, badItemsList, existingExperts).work();
				clearDao(false);
				synchronizationInfo.setCurrentNumPlusPlus();
				//修改处理当前条件变量
				long theCurrentNum1 = synchronizationInfo.getCurrentNum();
				long theCurrentNum2 = synchronizationInfo.getBatchSize() * (synchronizationInfo.getBatchIndex() - 1) + (j + 1);
				//参数协调一致
				if (theCurrentNum1 != theCurrentNum2 ) {
					boolean isOk;
					isOk = true;
					isOk = false; //TODO 需要调试，各项参数保持逻辑上保持一致
				}
				
			}
			
		}
	}
	
	@Override
	public SynchronizationInfo updateCurrentSynchronizationInfo(Integer runStatus) {
		if (runStatus != null) {
			if (runStatus == 3) {//取消（重新开始）功能
				smdbClient.setNotBusy();
			}
			synchronizationInfo.setRunStatus(runStatus);
		}
		//返回状态对象
		return synchronizationInfo;
	
	}

}
