package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.stereotype.Component;

import csdc.dao.IHibernateBaseDao;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.webService.ProjectApplicationRecordResolver;
import csdc.tool.webService.smdb.client.SOAPEnvTool;

/**
 * smas同步的流程
 * 1.smdb进行查重分析
 * 2.smas同步项目申请（可以不包含初评查重信息）
 * 3.smas“高校更新”
 * 4.smas同步“部级查重结果同步”
 * 5.smas专家匹配
 */
@Component
public class SynchronizationProjectApplication extends SynchronizationProjectData {
	
	private int fetchSize;//每次同步的条数（前台设定）
	private int fetchIndex; //基于1开始
	
	private SmdbClient smdbClient;
	private String projectType; //前台传递
	private String year; //前台传递

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
	protected List<String> badItemsList; //无法解析的id集合,最终打印出来，核查数据
	//本年度专家库中专家申请项目的专家统计
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
		//执行
		init();
	}
	
	private void init() {
		//初始化，数据库中专家map信息： universitycode + applicantName ： Expert 
		long begin = System.currentTimeMillis();
		if (tools == null) {
			tools = new Tools(dao);
		}
		System.out.println("初始化一般项目同步工具 ··· ");
		tools.initExpertMap();//专家代码集合
		tools.initNameUnivMap();//高校代码集合
		System.out.println("一般项目同步工具 初始化完毕,用时： " + 	(new Date().getTime() - begin));
		 
	}
	@Override
	protected void work() throws Throwable {
		boolean unDone = true;//获取所有数据
		argsMap.put("projectType", projectType);
		argsMap.put("year", year);
		argsMap.put("fetchSize",Integer.toString(fetchSize));//500~ 1000
		argsMap.put("counts", Integer.toString(fetchIndex));
		do {
			Long begin = System.currentTimeMillis();
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println(sdf.format(new Date()) + ":开始获取第 " + fetchIndex + " 批数据..." );
			String backData = smdbClient.invokeDirect("fwzx", "csdc702", "SmasService", "requestSmdbProjectApplication", argsMap);
			
			if (null == backData) {
				throw new RuntimeException("执行期间异常撤销");//事务回滚
			} else if(backData.equals("busy")) {
				Thread.sleep(smdbClient.WaitUnitTime*6);//延时6秒
			} else {
				//解析服务端返回信息
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					//返回的是Element类型数据
					Element recordsElement = (Element) backMap.get("records");
					//取出本次获取的records Element元素
					Element totalNumElement = (Element)recordsElement.selectNodes("//totalNum").get(0);
					//String totalNumString = totalNumElement.getText();
					int totalNum = Integer.parseInt(totalNumElement.getText());
					
					if(totalNum<fetchIndex*fetchSize ||totalNum==fetchIndex*fetchSize){
						unDone =false;
					}
					
						
					//批量入库
					persistBatchProjectDate(recordsElement);
					Long end = System.currentTimeMillis();
					System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
					
					//修改同步变量
					this.fetchIndex++;
					argsMap.put("counts", Integer.toString(fetchIndex));
					
				} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
					if (backMap.get("content").equals("success")) {
						unDone = false;
						System.out.println("数据同步工作完毕！");
					}
				} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
					throw new RuntimeException((String)backMap.get("content"));
				}
			}
		} while (unDone);
		
		//输出无法解析的信息
		printRecordedListContent(badItemsList);
		//输出本年度专家申请统计信息
		System.out.println("本年度数据库中申请项目信息统计：\n 共：" +  existingExperts.size() + "个专家申请本年度 " + projectType +" 类型项目！");

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
				new ProjectApplicationRecordResolver(item, sdf, tools, dao, projectType, badItemsList, existingExperts).work();
				clearDao(false);
			}
		}
	}


}
