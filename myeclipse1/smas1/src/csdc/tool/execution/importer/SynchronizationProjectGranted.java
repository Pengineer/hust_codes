package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import csdc.bean.ProjectGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.webService.ProjectGrantedRecordResolver;
import csdc.tool.webService.smdb.client.SOAPEnvTool;

public class SynchronizationProjectGranted extends SynchronizationProjectData {
	
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

	public SynchronizationProjectGranted() {
		
	}
	/**
	 * 项目申请数据同步
	 * @param smdbClient
	 * @param dao
	 * @param projectType
	 * @param year
	 * @param fetchSize
	 */
	public SynchronizationProjectGranted(SmdbClient smdbClient, IHibernateBaseDao dao, String projectType, String year, int fetchSize) {
		this.smdbClient = smdbClient;
		super.dao = dao;
		
		this.projectType = projectType;
		this.year = year;
		this.fetchSize = fetchSize; 
		
		this.fetchIndex = 0; 
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
		
	}
	
	
	
	@Override
	protected void work() throws Throwable {
		boolean unDone = true;//获取所有数据
		
		argsMap.put("projectType", projectType);
		argsMap.put("year", year);
		argsMap.put("fetchSize",Integer.toString(fetchSize));//500~ 1000
		
	
		

		//此处为非安全模式
	//导入一般项目申报
		do {
			
			fetchIndex++;
			argsMap.put("counts", Integer.toString(fetchIndex));
			
			Long begin = System.currentTimeMillis();
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println(sdf.format(new Date()) + ":开始获取第 " + fetchIndex + " 批数据..." );
			String backData = smdbClient.invokeDirect("fwzx", "csdc702", "SmasService", "requestSmdbProjectGranted", argsMap);
			if (null == backData) {
				throw new RuntimeException("执行期间异常撤销");//事务回滚
			} else if(backData.equals("busy")) {
				Thread.sleep(smdbClient.WaitUnitTime);
			} else {
				//解析服务端返回信息
				Map backMap = SOAPEnvTool.parseNormalResponse(backData);
				if (backMap.get("type") != null && backMap.get("type").equals("data")) {
					//返回的是Element类型数据
					Element contentElement = (Element) backMap.get("records");
					//取出本次获取的records Element元素
					Element recordsElement = (Element) contentElement.selectNodes("//records").get(0);
					/*Element totalNumElement = (Element)contentElement.selectNodes("//totalNum").get(0);
					String totalNumString = totalNumElement.getText();*/
					//批量入库
					persistBatchProjectDate(recordsElement);
					Long end = System.currentTimeMillis();
					System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
					
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
		//输出本年度专家申报统计信息
		System.out.println("本年度数据库中申报项目信息统计：\n 共：" +  existingExperts.size() + "个专家申报本年度 " + projectType +" 类型项目！");

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
		new ProjectGrantedRecordResolver().parse(item, sdf, dao, projectType, badItemsList);
				clearDao(false);
			}
			
		}
	}

	
	
	
	

}
