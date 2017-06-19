package csdc.tool.execution.finder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.tools.ant.taskdefs.Length;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.importer.Tools;
import csdc.tool.info.ImporterInfo;

/**
 * 专家辅助类
 * @author fengcl
 *
 */
@Component
public class ExpertFinder {

	/**
	 * 学校编号+姓名 到 专家id列表 的映射
	 */ 
	private Map<String, List<String>> expertMap = null;
	
	/**
	 * 专家ID 到 专家名 的映射
	 */
	private Map<String, String> expertIdNameMap = null;
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * 内部专家最大编号
	 */
	private int maxInnerExpertNumber = 0;
	
	/**
	 * 外部专家最大编号
	 */
	private int maxOuterExpertNumber = 0;
	
	/**
	 * Excel与数据库 身份证相匹配总计
	 */
	private int idCardMatchCnt = 0;
	
	/**
	 * 是否考虑学校
	 */
	private int flag = 0;
	
	/**
	 * Excel与数据库 通过电话等匹配且证件合法的专家
	 */
	private List<String> phoneMatchAndIdCardLegalExperts = new ArrayList<String>();
	
	/**
	 * Excel与数据库 通过电话等匹配但身份证不合法的专家
	 */
	private List<String> phoneMatchButIdCardIlLegalExperts = new ArrayList<String>();
	
	/**
	 * Excel与数据库 未匹配的专家
	 */
	private List<String> unMatchExperts = new ArrayList<String>();
	
	/**
	 * 新增专家
	 */
	private List<String> newExperts = new ArrayList<String>();
	
	/**
	 * 表示数据库中存在当前key(即同校同名),新增专家的身份证idCards
	 */
	HashSet<String> newIdCards = new HashSet<String>();;
	/**
	 * 初始化器
	 */
	public void initData(){
		//初始化专家编号
		Object o1 = dao.queryUnique("select max(e.number) from Expert e where e.number < 20000000");
		maxInnerExpertNumber = (o1 == null) ? 0 : (Integer) o1;
		Object o2 = dao.queryUnique("select max(e.number) from Expert e");
		maxOuterExpertNumber = Math.max(20000000, (o2 == null) ? 0 : (Integer) o2);
		
		//初始化计数器
		idCardMatchCnt = 0;
		phoneMatchAndIdCardLegalExperts.clear();
		phoneMatchButIdCardIlLegalExperts.clear();
		unMatchExperts.clear();
		newExperts.clear();
		
		//测试后再确定这些
		newIdCards.clear();
		//核查后确实需要新增的数据
//		newIdCards.add("370102196910292989");
//		newIdCards.add("420601196608281731");
//		newIdCards.add("320211690502003");
//		newIdCards.add("310110630510430");
//		newIdCards.add("342123197710130523");
//		newIdCards.add("610103195112293639");
//		newIdCards.add("130403197108081810");
//		newIdCards.add("211002196705155150");
//		newIdCards.add("210204196203101403");
//		newIdCards.add("340104195412293017");
//		newIdCards.add("130403198208012112");
//		newIdCards.add("440106196510154011");
//		newIdCards.add("430302197508060014");
//		newIdCards.add("422301196109020516");
		newIdCards.add("21080319790701152X");
		newIdCards.add("230229197111120354");
		newIdCards.add("320802197702081013");
		newIdCards.add("32010619661017125X");
		newIdCards.add("510102195604208453");
	}
	
	public void printResult(){
		System.out.println("Excel与数据库 通过身份证匹配的总数量（已入库）：" + idCardMatchCnt);
		System.out.println("Excel与数据库 通过电话等匹配且证件合法的专家数量（已入库）：" + phoneMatchAndIdCardLegalExperts.size());
		for (String pmExpert : phoneMatchAndIdCardLegalExperts) {
			System.out.println(pmExpert);
		}
		System.out.println("Excel与数据库 通过电话等匹配但身份证不合法的专家数量（未入库）：" + phoneMatchButIdCardIlLegalExperts.size());
		for (String pmExpert : phoneMatchButIdCardIlLegalExperts) {
			System.out.println(pmExpert);
		}
		System.out.println("Excel与数据库 未匹配上待人工处理的专家数量（未入库）：" + unMatchExperts.size());
		for (String umExpert : unMatchExperts) {
			System.out.println(umExpert);
		}
		System.out.println("Excel 新增专家数量（已入库）：" + newExperts.size());
		for (String newExpert : newExperts) {
			System.out.println(newExpert);
		}
	}
	
	/**
	 * 初始化expertMap
	 */
	private void initExpertMap() {
		long begin = System.currentTimeMillis();
		
		expertMap = new HashMap<String, List<String>>();
		List<Object[]> experts = dao.query("select e.universityCode, e.name, e.id from Expert e");
		for (Object[] expert : experts) {
			if (expert[0] == null) {
				System.out.println("ok");
			}
			
			String key = null;
			String univCode = expert[0].toString();
			String expertName = StringTool.fix((String) expert[1]);
			String expertId = expert[2].toString();
			
			if (flag == 1) {
				key = expertName;
			} else {
				key = univCode + expertName;
			}
			
			List<String> expertIds = expertMap.get(key);
			if (null == expertIds) {
				expertIds = new ArrayList<String>();
				expertMap.put(key, expertIds);
			}
			expertIds.add(expertId);
		}
		
		System.out.println("init Expert complete! 共有["+ experts.size() +"]个专家； Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}

	/**
	 * 初始化expertIdNameMap
	 */
	private void initExpertIdNameMap() {
		long begin = System.currentTimeMillis();
		
		expertIdNameMap = new HashMap<String, String>();
		List<Object[]> experts = dao.query("select e.name, e.id from Expert e");
		for (Object[] expert : experts) {
			String expertName = StringTool.fix((String) expert[0]);
			String expertId = expert[1].toString();
			expertIdNameMap.put(expertId, expertName);
		}
		
		System.out.println("init ExpertIdName complete! 共有["+ experts.size() +"]个专家； Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}	
	
	/**
	 * 根据[学校代码]和[专家姓名]，已经相关的属性找到Expert实体
	 * @param universityCode 如果不考虑学校信息，可以为空
	 * @param expertNname
	 * @param conditions
	 * @param addIfNotFound
	 * @return
	 */
	public Expert findExpert(String universityCode, String expertNname, Map<ImporterInfo, String> conditions, boolean addIfNotFound) {
		//初始化工具类
		Tools allTools = new Tools();
		
		String fixedExertName = StringTool.fix(expertNname);
		if (fixedExertName.isEmpty()) {
			throw new RuntimeException("专家姓名不应为空！");
		}
		
		if (universityCode == null || universityCode.isEmpty()) {
			flag = 1;
		}
		if (expertIdNameMap == null) {
			initExpertIdNameMap();
		}
		Expert expert = null;
		String idCardNumber = conditions.get(ImporterInfo.IDCARD_NUMBER);//Excel中的身份证号码
		
		//通过Excel中的专家ID和专家姓名与数据库中的专家ID与专家姓名做匹配，如果匹配上，则直接得到专家
		String expertIdString = conditions.get(ImporterInfo.EXPERT_ID);//Excel中的专家ID
		if (expertIdString != null) {
			String expertNameString = expertIdNameMap.get(expertIdString);//库中的专家ID所对应的专家姓名
			if (expertNameString != null && expertNameString.equals(expertNname)) {
				expert = (Expert) dao.query(Expert.class, expertIdString);
				if (universityCode != null && expert.getUniversityCode() != null && !universityCode.equals(expert.getUniversityCode())) {
					System.out.println("专家所在高校修改"); //如果专家所在高校改变，输出提示信息并手工核查此专家是否确实改变
					newIdCards.add(idCardNumber);
				} else {
					expert.setUniversityCode(universityCode);//专家所在高校以Excel中信息为准
					if (conditions.get(ImporterInfo.IDCARD_TYPE).contains("身份证") && allTools.IdCardNumberChecker(idCardNumber)) {
						conditions.put(ImporterInfo.BIRTHDAY, allTools.getBirthDayFormIdNumber(idCardNumber));//以身份证生日为准
					}				
					return expert;
				}

			}
		}
		
		if (expertMap == null) {
			initExpertMap();
		}
		String key = universityCode + fixedExertName;
		List<String> expertIds = expertMap.get(key);
		
		
		if (expertIds != null) {//在数据库中找到同校同名的专家
			int size = expertIds.size();//找到多少个专家
			for (int i = 0; i < size; i++) {
				
				expert = (Expert) dao.query(Expert.class, expertIds.get(i));
				String fixedIdCard = allTools.checkAndGetIdCardNumber(idCardNumber, expert.getIdCardNumber());
				if (null != fixedIdCard) {//如果身份证号码相同
					conditions.put(ImporterInfo.BIRTHDAY, allTools.getBirthDayFormIdNumber(fixedIdCard));//以身份证生日为准
					conditions.put(ImporterInfo.IDCARD_NUMBER, fixedIdCard);//以调整后的身份证为准
					idCardMatchCnt++;
					return expert;
				}
				else {//否则匹配电话+邮箱+传真来判别
					StringBuffer str1 = new StringBuffer();
					String mobilePhone = conditions.get(ImporterInfo.MOBILE_PHONE);
					String email = conditions.get(ImporterInfo.EMAIL);
					String homePhone = conditions.get(ImporterInfo.HOME_PHONE);
					String officePhone = conditions.get(ImporterInfo.OFFICE_PHONE);
					String officeFax = conditions.get(ImporterInfo.OFFICE_FAX);
					if (null != mobilePhone && !mobilePhone.trim().isEmpty() && mobilePhone.length() > 6) {
						str1.append(mobilePhone).append(";");
					}
					if (null != homePhone && !homePhone.trim().isEmpty() && homePhone.length() > 6) {
						str1.append(homePhone).append(";");
					}
					if (null != officePhone && !officePhone.trim().isEmpty() && officePhone.length() > 6) {
						str1.append(officePhone).append(";");
					}
					if (null != officeFax && !officeFax.trim().isEmpty() && officeFax.length() > 6) {
						str1.append(officeFax).append(";");
					}
					StringBuffer str2 = new StringBuffer();
					if (null != expert.getMobilePhone() && !expert.getMobilePhone().trim().isEmpty() && expert.getMobilePhone().length() > 6) {
						str2.append(expert.getMobilePhone()).append(";");
					}
					if (null != expert.getHomePhone() && !expert.getHomePhone().trim().isEmpty() && expert.getHomePhone().length() > 6) {
						str2.append(expert.getHomePhone()).append(";");
					}
					if (null != expert.getOfficePhone() && !expert.getOfficePhone().trim().isEmpty() && expert.getOfficePhone().length() > 6) {
						str2.append(expert.getOfficePhone()).append(";");
					}
					if (null != expert.getOfficeFax() && !expert.getOfficeFax().trim().isEmpty() && expert.getOfficeFax().length() > 6) {
						str2.append(expert.getOfficeFax()).append(";");
					}
					if (allTools.checkContains(str1.toString(), str2.toString()) || allTools.checkEmail(email, expert.getEmail())) {//通过电话、邮箱等组合条件确定了是同一人
						if (null != conditions.get(ImporterInfo.IDCARD_TYPE)) {
							if (conditions.get(ImporterInfo.IDCARD_TYPE).contains("身份证") && allTools.IdCardNumberChecker(idCardNumber)) {//Excel中身份证号码合法
								phoneMatchAndIdCardLegalExperts.add(key+idCardNumber);
								conditions.put(ImporterInfo.BIRTHDAY, allTools.getBirthDayFormIdNumber(idCardNumber));//以身份证生日为准
								return expert;
							} else if (conditions.get(ImporterInfo.IDCARD_TYPE).contains("护照") || conditions.get(ImporterInfo.IDCARD_TYPE).contains("军官证") || conditions.get(ImporterInfo.IDCARD_TYPE).contains("台胞证") ) {
								phoneMatchAndIdCardLegalExperts.add(key+idCardNumber);
								return expert;
							} else {//是同一人，但是证件号码不合法或证件号为空的暂剔除，待人工处理
								phoneMatchButIdCardIlLegalExperts.add(key+idCardNumber);
								return null;
							}
						} else {//身份证为空
							return expert;
						}

					}
				}
				//如果遍历到最后一个还没有找到的暂剔除，表示数据库中存在当前key(即同校同名)，但需人工判断是否为新增 
				if (i == size - 1 && !newIdCards.contains(idCardNumber)) {
					unMatchExperts.add(key+idCardNumber);
					return null;
				}
			}//遍历end
		}
		// 当前key不存在的新增专家[null == expertIds] 或 人工判断后确定的新增专家[newIdCards]
		if((null == expertIds || newIdCards.contains(idCardNumber)) && addIfNotFound){//新增专家	
			expert = new Expert();
			expert.setName(StringTool.replaceBlank(expertNname));
			expert.setUniversityCode(universityCode);
			// XXX 每次外部专家与内部专家的确定需要和王老师确定			
//			Integer univCodeNumber = Integer.valueOf(universityCode);
//			当导入数据为内部专家
			expert.setNumber(++maxInnerExpertNumber);
			expert.setExpertType(1);

//			//当导入数据为外部专家
//			expert.setNumber(++maxOuterExpertNumber);
//			expert.setExpertType(2);

			expert.setIsReviewer(1);//参评
			expert.setIsKey(0);		//非重点人
			expert.setRating(1+"");	//评价等级1（最低等级）
			expert.setType("专职人员");	//新入库的均设为专职人员
			
			if (null == expertIds) {
				expertIds = new ArrayList<String>();
				expertMap.put(key, expertIds);
			}
			expertIds.add((String) dao.add(expert));
			newExperts.add(key + idCardNumber);
			
			String idBirthday = allTools.getBirthDayFormIdNumber(idCardNumber); 
			if(null != idBirthday){// 修正生日，以身份证生日为准
				conditions.put(ImporterInfo.BIRTHDAY, idBirthday);
			}
			return expert;
		}
		
		return null;
	}
	
	
	/**
	 * 根据[学校代码]和[专家姓名]，已经相关的属性找到Expert实体
	 * @param universityCode 如果不考虑学校信息，可以为空
	 * @param expertNname
	 * @param conditions
	 * @param addIfNotFound
	 * @return
	 */
	public Object findExpert(String universityCode, String expertNname, Map<ImporterInfo, String> conditions) {
		//初始化工具类
		Tools allTools = new Tools();
		
		String fixedExertName = StringTool.fix(expertNname);
		if (fixedExertName.isEmpty()) {
			throw new RuntimeException("专家姓名不应为空！");
		}
		
		if (universityCode == null || universityCode.isEmpty()) {
			flag = 1;
		}
		if (expertIdNameMap == null) {
			initExpertIdNameMap();
		}
		Expert expert = null;
		String idCardNumber = conditions.get(ImporterInfo.IDCARD_NUMBER);//Excel中的身份证号码
		
		if (expertMap == null) {
			initExpertMap();
		}
		String key = universityCode + fixedExertName;
		List<String> expertIds = expertMap.get(key);
		
		
		if (expertIds != null) {//在数据库中找到同校同名的专家
			int size = expertIds.size();//找到多少个专家
			for (int i = 0; i < size; i++) {
				
				expert = (Expert) dao.query(Expert.class, expertIds.get(i));
				String fixedIdCard = allTools.checkAndGetIdCardNumber(idCardNumber, expert.getIdCardNumber());
				if (null != fixedIdCard) {//如果身份证号码相同
					conditions.put(ImporterInfo.BIRTHDAY, allTools.getBirthDayFormIdNumber(fixedIdCard));//以身份证生日为准
					conditions.put(ImporterInfo.IDCARD_NUMBER, fixedIdCard);//以调整后的身份证为准
					return expert;
				}
				else {//否则匹配电话+邮箱+传真来判别
					StringBuffer str1 = new StringBuffer();
					String mobilePhone = conditions.get(ImporterInfo.MOBILE_PHONE);
					String email = conditions.get(ImporterInfo.EMAIL);
					String homePhone = conditions.get(ImporterInfo.HOME_PHONE);
					String officePhone = conditions.get(ImporterInfo.OFFICE_PHONE);
					String officeFax = conditions.get(ImporterInfo.OFFICE_FAX);
					if (null != mobilePhone && !mobilePhone.trim().isEmpty() && mobilePhone.length() > 6) {
						str1.append(mobilePhone).append(";");
					}
					if (null != homePhone && !homePhone.trim().isEmpty() && homePhone.length() > 6) {
						str1.append(homePhone).append(";");
					}
					if (null != officePhone && !officePhone.trim().isEmpty() && officePhone.length() > 6) {
						str1.append(officePhone).append(";");
					}
					if (null != officeFax && !officeFax.trim().isEmpty() && officeFax.length() > 6) {
						str1.append(officeFax).append(";");
					}
					StringBuffer str2 = new StringBuffer();
					if (null != expert.getMobilePhone() && !expert.getMobilePhone().trim().isEmpty() && expert.getMobilePhone().length() > 6) {
						str2.append(expert.getMobilePhone()).append(";");
					}
					if (null != expert.getHomePhone() && !expert.getHomePhone().trim().isEmpty() && expert.getHomePhone().length() > 6) {
						str2.append(expert.getHomePhone()).append(";");
					}
					if (null != expert.getOfficePhone() && !expert.getOfficePhone().trim().isEmpty() && expert.getOfficePhone().length() > 6) {
						str2.append(expert.getOfficePhone()).append(";");
					}
					if (null != expert.getOfficeFax() && !expert.getOfficeFax().trim().isEmpty() && expert.getOfficeFax().length() > 6) {
						str2.append(expert.getOfficeFax()).append(";");
					}
					if (allTools.checkContains(str1.toString(), str2.toString()) || allTools.checkEmail(email, expert.getEmail())) {//通过电话、邮箱等组合条件确定了是同一人
						if (null != conditions.get(ImporterInfo.IDCARD_TYPE)) {
							if (conditions.get(ImporterInfo.IDCARD_TYPE).contains("身份证") && allTools.IdCardNumberChecker(idCardNumber)) {//Excel中身份证号码合法
								return expert;
							} else if (conditions.get(ImporterInfo.IDCARD_TYPE).contains("护照") || conditions.get(ImporterInfo.IDCARD_TYPE).contains("军官证") || conditions.get(ImporterInfo.IDCARD_TYPE).contains("台胞证") ) {
								return expert;
							} else {//是同一人，但是证件号码不合法或证件号为空的暂剔除，待人工处理
								return "failed";
							}
						} else {//身份证为空
							return expert;
						}

					}
				}
			}//遍历end
		}
		return null;//未找到此专家
	}
	
	
	/**
	 * 按导入时间更新专家的专兼职信息（时间最新的设为专职）
	 */
	public void updateType(){
		//定义日期比较器
		Comparator<Expert> dateComparator = new Comparator<Expert>() {
			public int compare(Expert o1, Expert o2) {
				Date date1 = o1.getImportedDate();
				Date date2 = o2.getImportedDate();
				if (date1 != null && date2 != null) {
					return date1.after(date2) ? -1 : 1;
				}else if (date1 != null && date2 == null) {
					return -1;
				}else if (date1 == null && date2 != null) {
					return 1;
				}else {
					return 0;
				}
			}		
		};
		//查询有多个相同身份证号码的专家（排除了姓名不同、身份证号相同的情况）
		List<Expert> list = dao.query("select e1 from Expert e1 where e1.idCardNumber is not null and e1.idCardNumber in (select e2.idCardNumber from Expert e2 group by idCardNumber, name having count(idCardNumber) >1) order by e1.idCardNumber asc, e1.importedDate desc");
		HashMap<String, List<Expert>> expMap = new HashMap<String, List<Expert>>();
		//按导入时间将同名同身份证号的专家降序排列
		for (Expert expert : list) {
			List<Expert> experts = expMap.get(expert.getIdCardNumber()); 
			if (null == experts) {
				experts = new ArrayList<Expert>();
				expMap.put(expert.getIdCardNumber(), experts);
			}
			experts.add(expert);
			Collections.sort(experts, dateComparator);
		}
		//同名同身份证号专家取第一个设为专职（导入时间最新），其余设为兼职
		for (Entry<String, List<Expert>> entry : expMap.entrySet()) {
			List<Expert> experts = entry.getValue();
			for (int i = 0; i < experts.size(); i++) {
				Expert expert = experts.get(i);
				if(i == 0){
					expert.setType("专职人员");
				}else {
					expert.setType("兼职人员");
				}
			}
		}
	}
	public String fixMoeProjectInfo(String moeProjectInfo, Expert expert){
		String resultString = "";
		try {
			if (moeProjectInfo == null || moeProjectInfo.length() == 0) {
				return null; 
			}
			if(!moeProjectInfo.startsWith("<moeProjectData>")) {
				System.out.printf("信息错误%s\n",moeProjectInfo);//需手工修复
				throw new RuntimeException("未实现的方法");
//				return null;
			}
			if (moeProjectInfo.equals("<moeProjectData></moeProjectData>")) {//修复excel宏设计不完善遗留的问题
				return null;
			}
			Document document = DocumentHelper.parseText(moeProjectInfo);//根据excel宏中数据生成的“承担教育部项目情况”xml节点树
			Element rootElement = document.getRootElement();
			Element oldInfoElement = rootElement.element("oldProjectInfos");
			String oldInfo4Excel = "";
			if(oldInfoElement != null){
				oldInfo4Excel = rootElement.elementText("oldProjectInfos");//取出excel宏中的“承担教育部项目情况”信息
				rootElement.remove(oldInfoElement);
			}
			String oldInfo4Database = "";
			if (expert.getMoeProject() != null && expert.getMoeProject().length() != 0) {
				Document document2 = DocumentHelper.parseText(expert.getMoeProject());//根据数据库中的数据生成“承担教育部项目情况”xml节点树
				Element rooteElement2 = document2.getRootElement();
				oldInfo4Database = rooteElement2.elementText("oldProjectInfos");
			}
			String mergeProjectOldInfo = mergeProjectInfo(oldInfo4Excel, oldInfo4Database); //合并（excel宏中数据、数据库中数据）
			if (mergeProjectOldInfo.length() != 0) {
				rootElement.addElement("oldProjectInfos");
				rootElement.element("oldProjectInfos").setText(mergeProjectOldInfo);
			}
			
			String finalString = document.asXML();//xml节点树转换为String字符串
			if (finalString.indexOf("<moeProjectData/>") > 0 && finalString.indexOf("<moeProjectData>") < 0) {
				return null;
			}
			String finalString2 = finalString.substring(finalString.indexOf("<moeProjectData>"));
			resultString = finalString2;
			if (finalString2.equals("<moeProjectData><newProjectInfos/></moeProjectData>")) {
				return null;
			}else{
				if (finalString2.indexOf("<newProjectInfos/>") > 0 && finalString2.indexOf("<newProjectInfos>") < 0) {//清除“<newProjectInfos>”字符串
					int location = finalString2.indexOf("<newProjectInfos/>");
					String firstString = finalString2.substring(0, location);
					location = finalString2.indexOf("<newProjectInfos/>");
					resultString = firstString + finalString2.substring(location + "<newProjectInfos>".length() + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Integer.parseInt("ERROR!");
		}
		if (resultString.length() == 0) {
			return null;
		}
		return resultString;
	}
	public String fixNationalProjectInfo(String nationalProjectInfo, Expert expert){
		String resultString = "";
		try {
			if (nationalProjectInfo == null || nationalProjectInfo.length() == 0) {
				return null; 
			}
			if(!nationalProjectInfo.startsWith("<nationalProjectData>")) {
				System.out.printf("信息错误%s\n",nationalProjectInfo);//需手工修复
			}
			if (nationalProjectInfo.equals("<nationalProjectData></nationalProjectData>")) {
				return null;
			}
			
			Document document = DocumentHelper.parseText(nationalProjectInfo);//根据excel宏中数据生成的“承担国家社科项目情况”xml节点树
			Element rootElement = document.getRootElement();
			Element oldInfoElement = rootElement.element("oldProjectInfos");
			String oldInfo4Excel = "";
			if(oldInfoElement != null){
				oldInfo4Excel = rootElement.elementText("oldProjectInfos");//取出excel宏中的“承担国家社科项目情况”信息
				rootElement.remove(oldInfoElement);
			}
			String oldInfo4Database = "";
			if (expert.getNationalProject() != null && expert.getNationalProject().length() != 0) {
				Document document2 = DocumentHelper.parseText(expert.getNationalProject());//根据数据库中的数据生成“承担国家社科项目情况”xml节点树
				Element rooteElement2 = document2.getRootElement();
				oldInfo4Database = rooteElement2.elementText("oldProjectInfos");
			}
			String mergeProjectOldInfo = mergeProjectInfo(oldInfo4Excel, oldInfo4Database); //合并（excel宏中数据、数据库中数据）
			if (mergeProjectOldInfo.length() != 0) {
				rootElement.addElement("oldProjectInfos");
				rootElement.element("oldProjectInfos").setText(mergeProjectOldInfo);
			}
						
			String finalString = document.asXML();//xml节点树转换为String字符串
			if (finalString.indexOf("<nationalProjectData/>") > 0 && finalString.indexOf("<nationalProjectData>") < 0) {
				return null;
			}
			String finalString2 = finalString.substring(finalString.indexOf("<nationalProjectData>"));
			resultString = finalString2;
			if (finalString2.equals("<nationalProjectData><newProjectInfos/></nationalProjectData>")) {
				return null;
			}else{
				if (finalString2.indexOf("<newProjectInfos/>") > 0 && finalString2.indexOf("<newProjectInfos>") < 0) {//清除“<newProjectInfos>”字符串
					int location = finalString2.indexOf("<newProjectInfos/>");
					String firstString = finalString2.substring(0, location);
					location = finalString2.indexOf("<newProjectInfos/>");
					resultString = firstString + finalString2.substring(location + "<newProjectInfos>".length() + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Integer.parseInt("ERROR!");
		}
		if (resultString.length() == 0) {
			return null;
		}
		return resultString;
	}
	
	public String mergeProjectInfo(String string1, String string2){//合并excel宏中项目信息与数据库中项目信息
		String mergeProjectInfo = ""; 
		if (string1 != null && string2 != null && string1.length() != 0 && string2.length() != 0) {
			if (string1.equals("无")) {
				string1 = "";
			}
			if (string2.equals("无")) {
				string2 = "";
			}
			Set<String> projectInfoSet = new HashSet<String>();
			String[] string1Infos = string1.split("; ");
			String[] string2Infos = string2.split("; ");
			for (int i = 0; i < string1Infos.length; i++) {
				projectInfoSet.add(string1Infos[i]);
			}
			for (int i = 0; i < string2Infos.length; i++) {
				projectInfoSet.add(string2Infos[i]);
			}
			for(String string : projectInfoSet){
				mergeProjectInfo = mergeProjectInfo + string + "; ";
			}
			if (projectInfoSet.size() > 0) {
				mergeProjectInfo = mergeProjectInfo.substring(0, mergeProjectInfo.length() - 2);
			}
		}else if(string1 != null && string1.length() != 0) {
			mergeProjectInfo = string1;
		}else {
			if(string2 == null){
				mergeProjectInfo = "";
			}else{
				mergeProjectInfo = string2;
			}
		}
		return mergeProjectInfo;
	}
}