package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;

import csdc.bean.University;
import csdc.dao.IHibernateBaseDao;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.webService.smdb.client.SOAPEnvTool;

//更新smas中的高校信息，项目相关高校信息进行同步
public class SynchronizationUniversityInfo extends SynchronizationProjectData {
	private SmdbClient smdbClient;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法
	protected List<String> badItemsList; //无法解析的id集合,最终打印出来，核查数据
	private Map<String, String> argsMap;//数据同步参数
	public SynchronizationUniversityInfo() {
		
	}
	/**
	 * 项目申请数据同步
	 * @param smdbService
	 * @param dao
	 * @param projectType
	 * @param year
	 * @param fetchSize
	 */
	public SynchronizationUniversityInfo(SmdbClient smdbClient, IHibernateBaseDao dao) {
		this.smdbClient = smdbClient;
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
	/**
	 * 根据本次项目同步中smas中没有的高校“代码”，逐条获取并入库高校信息；
	 * （不需要本年度的申请信息与新增高校关联，通过高校代码关联)
	 */
	@Override
	protected void work() throws Throwable {
		Long begin = System.currentTimeMillis();
		String hql = "select p.universityCode from ProjectApplication p where p.universityCode not in ( select u.code from University u ) ";
		List<String> resultList = dao.query(hql);
		if (!resultList.isEmpty()) {
			Set resultSet = new HashSet<String>(resultList);
			List<String> univCodesList = new ArrayList<String>(resultSet);
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println("smas中有 " + univCodesList.size() + "个高校需要同步...");
			String responseConnestion = smdbClient.OpenSecurityConnection("fwzx", "csdc702", "AES");
			System.out.println(responseConnestion);
			for (int i = 0; i < univCodesList.size(); i++) {
				String code = univCodesList.get(i);
				System.out.print(sdf.format(new Date()) + ":开始获取第 " + (i+1) + " 个...");
				argsMap.put("universityCode",code);
				String backData = smdbClient.invokeSecurityService("fwzx", "csdc702", "SmasService", "requestUniqueUniversity", argsMap);
				if (null == backData) {
					throw new RuntimeException("执行异常");
				} else if(backData.equals("busy")) {
					Thread.sleep(SmdbClient.WaitTime1H);
					i--;
				} else {
					Map backMap = SOAPEnvTool.parseNormalResponse(backData);
					if (backMap.get("type") != null && backMap.get("type").equals("data")) {
						Element recordsElement = (Element) backMap.get("records");
						persistUniversityDomXml(recordsElement);
						System.out.println("	成功处理！");
					} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
						if (backMap.get("content").equals("success")) {
							System.out.println("数据同步工作完毕！");
						}
					} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
						System.out.println("\n######\n  Stop! information : error " + backMap.get("content") + "\n#######");
						//此处转译为RuntimeException，使Spring能够回滚
						throw new RuntimeException((String) backMap.get("content"));
					}
				}
			}
			String requestClose = smdbClient.closeSecurityConnection();
			System.out.println(requestClose);
			Long end = System.currentTimeMillis();
			System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
			printRecordedListContent(badItemsList);
		}
	}
	/**
	 * 处理在线通过的数据信息，逐条持久化数据库并记录中间表
	 * @param contentXmlString
	 * @throws Exception 
	 */
	private void persistUniversityDomXml(Element contentElement) throws Exception {
		List<Element> elemtList = contentElement.elements("item");
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				addUniversityObject(item);
				clearDao(false);
			}
		}
	}
	
	private void addUniversityObject(Element item) {
		String code = getArgumentByTag(item, "code");
		if (!code.equals("null") && !code.equals("") && !checkUniversityExist(code)) {
			//不存在,入库
			String name = getArgumentByTag(item, "name");
			String founderCode = getArgumentByTag(item, "founderCode");
			String type = getArgumentByTag(item, "type");
//			String remark = getArgumentByTag(item, "remark");//smdb无此备注字段
			String abbr = getArgumentByTag(item, "abbr");
//			String standard = getArgumentByTag(item, "standard");//smdb无此字段
			String provinceName = getArgumentByTag(item, "provinceName");
			String provinceCode = getArgumentByTag(item, "provinceCode");
			String style = getArgumentByTag(item, "style");
			String importedDate = getArgumentByTag(item, "importedDate");
//			String useExpert = getArgumentByTag(item, "useExpert");
//			String useViceExpert = getArgumentByTag(item, "useViceExpert");
//			String rating = getArgumentByTag(item, "rating");
			University university = new University();
			//设置默认属性
			university.setUseExpert(1);
			university.setUseViceExpert(0);
			university.setRating("1");
			university.setCode(code);
			if (!name.equals("null")) {
				university.setName(name);
			}
			if (!founderCode.equals("null")) {
				university.setFounderCode(founderCode);
			}
			if (!type.equals("null")) {
				
				university.setType(Integer.parseInt(type));
			}
//			if (!remark.equals("null")) {
//				university.setRemark(remark);
//			}
			if (!abbr.equals("null")) {
				university.setAbbr(abbr);
			}
//			if (!standard.equals("null")) {
//				university.setStandard(standard);
//			}
			if (!provinceName.equals("null")) {
				university.setProvinceName(provinceName);
			}
			if (!provinceCode.equals("null")) {
				university.setProvinceCode(provinceCode);
			}
			if (!style.equals("null")) {
				university.setStyle(style);
			}
			if (!importedDate.equals("null")) {
				university.setImportedDate(_date(importedDate));
			}
//			if (!useExpert.equals("null")) {
//				university.setUseExpert(Integer.parseInt(useExpert));
//			}
//			if (!useViceExpert.equals("null")) {
//				university.setUseViceExpert(Integer.parseInt(useViceExpert));
//			}
//			if (!rating.equals("null")) {
//				university.setRating(rating);
//			}
			dao.add(university);
		}
	}
	
	private boolean checkUniversityExist(String universityCode) {
		boolean beIn = false;
		try {
			List resuList = dao.query(" from University u where u.code = ? ", universityCode);
			if (resuList.size() > 0) {
				beIn = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			beIn = true;//存在
		}
		return beIn;
	}

}
