package csdc.tool.execution.importer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Expert;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 导入项目Excel所需的工具包
 * @author xuhan
 *
 */

@SuppressWarnings("unchecked")
public class Tools {
	
	/**
	 * 学校代码到学校的映射
	 */
	public static Map<String, University> codeUnivMap;
	
	/**
	 * 学校名称到学校的映射
	 */
	public static Map<String, University> nameUnivMap;
	
	/**
	 * 学校代码+姓名 到专家实体的映射
	 */
	public static Map<String, Expert> expertMap;
	/**
	 * 学校代码+姓名+身份证信息  到专家实体的映射
	 */
	public static Map<String, Expert> uniNamIdcard2ExpertMap;
	
	/**
	 * 专家编号到专家实体的映射
	 */
	public static Map<Integer, Expert> expertNumberMap;
	
	/**
	 * 学科名称 到 学科代码 的映射
	 */
	public static Map<String, String> discNameCodeMap;

	/**
	 * 学科代码 到 学科名称 的映射
	 */
	public static Map<String, String> discCodeNameMap;

	/**
	 * 学校名称变更
	 */
	public static Map<String, String> univNameChange;


	@Autowired
	public IHibernateBaseDao dao;
	
	/**
	 * 日期格式化（yyyy-mm-dd）  
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public Tools() {
		
	}
	
	public Tools( IHibernateBaseDao dao) {
		this.dao = dao;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 初始化学校代码到学校实体的映射
	 * @return
	 */
	public void initCodeUnivMap() {
		codeUnivMap = new HashMap<String, University>();
		nameUnivMap = new HashMap<String, University>();
		List<University> universityList = dao.query("select University from University University");
		for (University University : universityList) {
			codeUnivMap.put(University.getCode(), University);
			nameUnivMap.put(University.getName(), University);
		}
	}

	/**
	 * 初始化学校名称到学校实体的映射
	 * @return
	 */
	public void initNameUnivMap() {
		initCodeUnivMap();
	}

	/**
	 * 初始化 学科名称 到 学科代码 的映射
	 * @return
	 */
	public void initDiscNameCodeMap() {
		long beginTime  = new Date().getTime();
		List<SystemOption> list = dao.query("select so from SystemOption so where so.standard like '%GBT13745%' order by so.standard asc");
		discNameCodeMap = new HashMap<String, String>();
		discCodeNameMap = new HashMap<String, String>();
		for (SystemOption so : list) {
			discNameCodeMap.put(so.getName().toLowerCase(), so.getCode());
			discNameCodeMap.put(so.getName().toLowerCase().replaceAll("学", ""), so.getCode());
			discCodeNameMap.put(so.getCode(), so.getName());
		}
		discNameCodeMap.put("sxzzty", "970");	//思想政治教育
		discNameCodeMap.put("xszzty", "970");	//思想政治教育
		discNameCodeMap.put("sxzzjy", "970");	//思想政治教育
		discNameCodeMap.put("gat", "980");		//港澳台问题研究
		discNameCodeMap.put("gjw", "990");		//国际问题研究
		discNameCodeMap.put("xlx", "190");		//心理学
		System.out.println("initDiscNameCodeMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化  学科代码 到 学科名称 的映射
	 * @return
	 */
	public void initDiscCodeNameMap() {
		initDiscNameCodeMap();
	}
	
	/**
	 * 初始化 学校代码+姓名 到 专家实体的映射
	 * @return
	 */
	public void initExpertMap() {
		expertMap = new HashMap<String, Expert>();
		expertNumberMap = new HashMap<Integer, Expert>();
		uniNamIdcard2ExpertMap = new HashMap<String, Expert>();
		List<Object[]> list = dao.query("select u, e from Expert e, University u where e.universityCode = u.code");
		for (Object[] objects : list) {
			University univ = (University) objects[0];
			Expert expert = (Expert) objects[1];
//			expertMap.put(univ.getCode() + expert.getName().replaceAll("[^\\u4e00-\\u9fa5]+", ""), expert);
			expertMap.put(univ.getCode() + expert.getName().replaceAll("\\s+", ""), expert);
			expertNumberMap.put(expert.getNumber(), expert);
			uniNamIdcard2ExpertMap.put(univ.getCode() + expert.getName().replaceAll("\\s+", "") + expert.getIdCardNumber(), expert);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 将学校名称替换成最新名称
	 * @param univCode
	 * @return
	 * @throws Exception 
	 */
	public static String getNewestUnivName(String univName) throws Exception {
		if (univNameChange == null) {
			File file = new File("D:\\csdc\\一般项目\\1.申报立项数据\\学校更名.xls");
			UniversityNameChangesImporter universityNameChangesImporter = new UniversityNameChangesImporter(file);
			universityNameChangesImporter.work();
			univNameChange = universityNameChangesImporter.universityNameChangesMap;
		}
		String newestUnivName = univNameChange.get(univName);
		return newestUnivName == null ? univName : newestUnivName;
	}
	
	/**
	 * 根据学校代码找到学校实体
	 * @param univCode
	 * @return
	 */
	public University getUnivByCode(String univCode) {
		if (codeUnivMap == null) {
			initCodeUnivMap();
		}
		return codeUnivMap.get(univCode);
	}
	
	/**
	 * 根据学校名称找到学校实体
	 * @param univCode
	 * @return
	 * @throws Exception 
	 */
	public University getUnivByName(String univName) throws Exception {
		if (nameUnivMap == null) {
			initNameUnivMap();
		}
		return nameUnivMap.get(getNewestUnivName(univName));
	}
	
	/**
	 * 根据人名和学校找到expert实体
	 * @param personName
	 * @param university
	 * @return
	 */
	public Expert getExpert(String personName, University university) {
		if (expertMap == null) {
			initExpertMap();
		}
		
		String key = university.getCode() + personName.replaceAll("\\s+", "");
		Expert expert = expertMap.get(key);
		return expert;
	}
	
	public Expert getExpert(String personName, String universityCode) {
		if (expertMap == null) {
			initExpertMap();
		}
		
		String key = universityCode + personName.replaceAll("\\s+", "");
		Expert expert = expertMap.get(key);
		return expert;
	}

	public Expert getExpert(String personName, String universityCode, String idCard) {
		if (uniNamIdcard2ExpertMap == null) {
			initExpertMap();
		}
		
		String key = universityCode + personName.replaceAll("\\s+", "") + idCard;
		Expert expert = uniNamIdcard2ExpertMap.get(key);
		return expert;
	}
	/**
	 * 根据专家编号找到专家
	 * @return
	 */
	public Expert getExpertByNumber(Integer number) {
		if (expertNumberMap == null) {
			initExpertMap();
		}
		return expertNumberMap.get(number);
	}
	
	/**
	 * 疑似同一个专家
	 *     1. 四种联系方式(homePhone mobilePhone officePhone email)至少有一种是一个专家的包含另一个专家的
	 * (或)2. 生日非空且相同
	 * @param expert1
	 * @param expert2
	 * @return
	 */
	public boolean sameExpert(Expert e1, Expert e2) {
		if (!e1.getName().equals(e2.getName())) {
			return false;
		}
		if (!e1.getUniversityCode().equals(e2.getUniversityCode())) {
			return false;
		}
		//一个联系方式也没有，就是用来和人合并的
		if (e2.getHomePhone() == null && e2.getMobilePhone() == null && e2.getOfficePhone() == null && e2.getEmail() == null) {
			return true;
		}
		if (e1.getHomePhone() != null && !e1.getHomePhone().isEmpty() && e2.getHomePhone() != null && !e2.getHomePhone().isEmpty()) {
			if (e1.getHomePhone().contains(e2.getHomePhone()) || e2.getHomePhone().contains(e1.getHomePhone())) {
				return true;
			}
		}
		if (e1.getMobilePhone() != null && !e1.getMobilePhone().isEmpty() && e2.getMobilePhone() != null && !e2.getMobilePhone().isEmpty()) {
			if (e1.getMobilePhone().contains(e2.getMobilePhone()) || e2.getMobilePhone().contains(e1.getMobilePhone())) {
				return true;
			}
		}
		if (e1.getOfficePhone() != null && !e1.getOfficePhone().isEmpty() && e2.getOfficePhone() != null && !e2.getOfficePhone().isEmpty()) {
			if (e1.getOfficePhone().contains(e2.getOfficePhone()) || e2.getOfficePhone().contains(e1.getOfficePhone())) {
				return true;
			}
		}
		if (e1.getEmail() != null && !e1.getEmail().isEmpty() && e2.getEmail() != null && !e2.getEmail().isEmpty()) {
			if (e1.getEmail().contains(e2.getEmail()) || e2.getEmail().contains(e1.getEmail())) {
				return true;
			}
		}
		if (e1.getBirthday() != null && e2.getBirthday() != null) {
			if (e1.getBirthday().compareTo(e2.getBirthday()) == 0) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 合并两个专家(将e2合并至e1,然后删除e2)，将相应匹配也合并
	 * @param e1
	 * @param e2
	 */
	public void mergeExpert(Expert e1, Expert e2) {
		if (e1.getName().contains("[待删除]") || e2.getName().contains("[待删除]")) {
			return;
		}
		
		//替换性别
		if (e2.getGender() != null) {
			e1.setGender(e2.getGender());
		}
		//替换生日
		if (e2.getBirthday() != null) {
			e1.setBirthday(e2.getBirthday());
		}
		//合并专业职称
		if (e1.getSpecialityTitle() == null || e2.getSpecialityTitle() != null && e1.getSpecialityTitle().length() < e2.getSpecialityTitle().length()) {
			e1.setSpecialityTitle(e2.getSpecialityTitle());
		}
		//合并身份证号
		if (e1.getIdCardNumber() == null || e2.getIdCardNumber() != null && e1.getIdCardNumber().length() < e2.getIdCardNumber().length()) {
			e1.setIdCardNumber(e2.getIdCardNumber());
		}
		//合并学科
		e1.setDiscipline(transformDisc(e1.getDiscipline() + " " + e2.getDiscipline()));
		//合并家庭电话
		e1.setHomePhone(mergePhoneNumber(e1.getHomePhone(), e2.getHomePhone()));
		//替换移动电话
		if (e2.getMobilePhone() != null && e2.getMobilePhone().replaceAll("\\D+", "").length() >= 11) {
			e1.setMobilePhone(e2.getMobilePhone());
		}
		//合并办公电话
		e1.setOfficePhone(mergePhoneNumber(e1.getOfficePhone(), e2.getOfficePhone()));
		//合并邮箱
		if (e1.getEmail() == null || e2.getEmail() != null && !e1.getEmail().contains(e2.getEmail())) {
			e1.setEmail((e1.getEmail() == null ? "" : e1.getEmail() + "; ") + e2.getEmail());
		}
		//合并承担教育部项目情况
		if (e1.getMoeProject() == null || e2.getMoeProject() != null && e1.getMoeProject().length() < e2.getMoeProject().length()) {
			e1.setMoeProject(e2.getMoeProject());
		}
		//合并承担国家社科项目情况
		if (e1.getNationalProject() == null || e2.getNationalProject() != null && e1.getNationalProject().length() < e2.getNationalProject().length()) {
			e1.setNationalProject(e2.getNationalProject());
		}
		//合并行政职务
		if (e1.getJob() == null || e2.getJob() != null && e1.getJob().length() < e2.getJob().length()) {
			e1.setJob(e2.getJob());
		}
		//合并是否博导
		if (e2.getIsDoctorTutor() != null) {
			e1.setIsDoctorTutor(e2.getIsDoctorTutor());
		}
		//合并所在院系所
		if (e1.getDepartment() == null || e2.getDepartment() != null && e1.getDepartment().length() < e2.getDepartment().length()) {
			e1.setDepartment(e2.getDepartment());
		}
		//合并最后学位
		if (e1.getDegree() == null || e2.getDegree() != null && e1.getDegree().length() < e2.getDegree().length()) {
			e1.setDegree(e2.getDegree());
		}
		//合并外语语种
		if (e1.getLanguage() == null || e2.getLanguage() != null && !e1.getLanguage().contains(e2.getLanguage())) {
			e1.setLanguage((e1.getLanguage() == null ? "" : e1.getLanguage() + "; ") + e2.getLanguage());
		}
		//合并岗位等级
		if (e1.getPositionLevel() == null || e2.getPositionLevel() != null && e1.getPositionLevel().length() < e2.getPositionLevel().length()) {
			e1.setPositionLevel(e2.getPositionLevel());
		}
		//合并人才奖励资助情况
		if (e1.getAward() == null || e2.getAward() != null && !e1.getAward().contains(e2.getAward())) {
			e1.setAward((e1.getAward() == null ? "" : e1.getAward() + "; ") + e2.getAward());
		}
		//合并学术研究方向
		if (e1.getResearchField() == null || e2.getResearchField() != null && !e1.getResearchField().contains(e2.getResearchField())) {
			e1.setResearchField((e1.getResearchField() == null ? "" : e1.getResearchField() + "; ") + e2.getResearchField());
		}
		//合并学术兼职
		if (e1.getPartTime() == null || e2.getPartTime() != null && !e1.getPartTime().contains(e2.getPartTime())) {
			e1.setPartTime((e1.getPartTime() == null ? "" : e1.getPartTime() + "; ") + e2.getPartTime());
		}
		//合并通讯地址、办公邮编
		if (e1.getOfficeAddress() == null && e1.getOfficePostcode() == null) {
			e1.setOfficeAddress(e2.getOfficeAddress());
			e1.setOfficePostcode(e2.getOfficePostcode());
		}
		//合并备注
		if (e1.getRemark() == null)e1.setRemark("");
		if (e2.getRemark() == null)e2.setRemark("");
		List<String> remark = new ArrayList<String>(new TreeSet<String>(Arrays.asList((e1.getRemark() + "; " + e2.getRemark()).split("; "))));
		String newRemark = "";
		for (String string : remark) {
			if (string.length() > 0) {
				newRemark += (newRemark.isEmpty() ? "" : "; ") + string;
			}
		}
		e1.setRemark(newRemark);
		
		//替换是否参评
		if (e2.getIsReviewer() != null) {
			e1.setIsReviewer(e2.getIsReviewer());
		}
		
		//替换评价等级
		if (e2.getRating() != null && e2.getRating().length() > 0) {
			e1.setRating(e2.getRating());
		}

		//合并过往参评年份
		List<String> ry = new ArrayList<String>(new TreeSet<String>(Arrays.asList((e1.getGeneralReviewYears() + " " + e2.getGeneralReviewYears()).split("\\D+"))));
		String newRy = "";
		for (String string : ry) {
			if (string.length() > 0) {
				newRy += (newRy.isEmpty() ? "" : "; ") + string;
			}
		}
		e1.setGeneralReviewYears(newRy);
		
		//合并过往申请年份
		List<String> ay = new ArrayList<String>(new TreeSet<String>(Arrays.asList((e1.getGeneralApplyYears() + " " + e2.getGeneralApplyYears()).split("\\D+"))));
		String newAy = "";
		for (String string : ay) {
			if (string.length() > 0) {
				newAy += (newAy.isEmpty() ? "" : "; ") + string;
			}
		}
		e1.setGeneralApplyYears(newAy);
		
		//合并匹配条目
		if (e2.getApplicationReview() != null) {
			for (Iterator iterator = e2.getApplicationReview().iterator(); iterator.hasNext();) {
				ProjectApplicationReview pr = (ProjectApplicationReview) iterator.next();
				pr.setReviewer(e1);
//				session.update(pr);
				dao.modify(pr);
			}
		}
		
		//合并退评原因
		if (e1.getReason() == null)e1.setReason("");
		if (e2.getReason() == null)e2.setReason("");
		List<String> reason = new ArrayList<String>(new TreeSet<String>(Arrays.asList((e1.getReason() + "; " + e2.getReason()).split("; "))));
		String newReason = "";
		for (String string : reason) {
			if (string.length() > 0) {
				newReason += (newReason.isEmpty() ? "" : "; ") + string;
			}
		}
		e1.setReason(newReason);

		if (e2.getApplicationReviewUpdate() != null) {
			for (Iterator iterator = e2.getApplicationReviewUpdate().iterator(); iterator.hasNext();) {
				ProjectApplicationReviewUpdate pru = (ProjectApplicationReviewUpdate) iterator.next();
				pru.setReviewer(e1);
//				session.update(pru);
				dao.modify(pru);
			}
		}
/*		if (e2.getInstpReviewer() != null) {
			for (Iterator iterator = e2.getInstpReviewer().iterator(); iterator.hasNext();) {
				InstpReviewer ipr = (InstpReviewer) iterator.next();
				ipr.setReviewer(e1);
//				session.update(ipr);s
				dao.modify(ipr);
			}
		}*/

//		session.updatse(e1);
		dao.modify(e1);

		if (e2.getId() != null) {
			e2.setName("[待删除]" + e2.getName());
//			session.update(e2);
			dao.modify(e2);
		}
	}
	
	
	/**
	 * 把乱七八糟的学科代码or学科名称 变成 [ 学科代码; ...]格式 
	 * @param input
	 * @return
	 */
	public String transformDisc(String input) {
		if (discNameCodeMap == null) {
			initDiscNameCodeMap();
		}
		String[] disc = toDBC(input).toLowerCase().replaceAll("\\s*[·\\.]\\s*", ".").split("[^\\.\\w\\u4e00-\\u9fa5/]+|(?<=[^\\d\\.])(?=[\\d\\.])|(?<=[\\d\\.])(?=[^\\d\\.])");
		List<String> dList = new ArrayList<String> ();
		for (String curDisc : disc) {
//			System.out.println("cur = " + curDisc);
			String cur = curDisc.replaceAll("\\.", "");
			if (cur.matches("\\d+")){
				if (cur.length() > 7 || cur.length() < 3)
					continue;
				while (cur.length() < 7)
					cur += "0";
				while (cur.length() >= 5 && (cur.endsWith("00") || !discCodeNameMap.containsKey(cur)))
					cur = cur.substring(0, cur.length() - 2);
				if (discCodeNameMap.containsKey(cur))
					dList.add(cur);
			} else if (discNameCodeMap.containsKey(cur)){
				dList.add(discNameCodeMap.get(cur));
			}
		}
		Collections.sort(dList, new Comparator() {
			public int compare(Object o1, Object o2) {
				String a = (String)o1, b = (String)o2;
				return a.compareTo(b) < 0 ? -1 : 1;
			}
		});
		StringBuffer result = new StringBuffer();
		for (int j = 0; j < dList.size(); j++) {
			String thisDisc = dList.get(j);
			//不是任意串的前缀的串才加入
			if (j == dList.size() - 1 || !dList.get(j + 1).startsWith(thisDisc)){
				if (result.length() > 0) {
					result.append("; ");
				}
				result.append(thisDisc);
			}
		}
		return result.length() == 0 ? "" : result.toString();
	}
	
	/**
	 * 将号码add合并至origin
	 * @param origin
	 * @param add
	 * @return
	 */
	public String mergePhoneNumber(String origin, String add) {
		if (origin == null) {
			origin = "";
		}
		String phones[] = toDBC(add == null ? "" : add).replaceAll("\\s*[_-]+\\s*", "-").replaceAll("\\((\\d+)\\)\\D*", "$1-").toLowerCase().split("[^\\d-]+");
		for (String tmp : phones) {
			//把区号去掉后非空、且原来没有此号码
			String withoutAreaCode = tmp.replaceAll("^\\d*-", "");
			if (withoutAreaCode.length() > 0 && !origin.contains(withoutAreaCode)) {
				String bakup = origin;
				origin += (origin.isEmpty() ? "" : "; ") + tmp;
				if (origin.length() > 40) {
					origin = bakup;
				}
			}
		}
		return origin;
	}

	
	/**
	 * 根据乱七八糟的日期字符串分析出正确日期
	 * @param rawDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date getDate(String rawDate) {
		rawDate = toDBC(rawDate).trim();
		if (rawDate.isEmpty()) {
			return null;
		}
		if (rawDate.matches("(19|20)\\d{4}") || rawDate.matches("20\\d{4}")) {
			Integer year = Integer.parseInt(rawDate) / 100;
			Integer month = Integer.parseInt(rawDate) % 100;
			if (month >= 1 && month <= 12) {
				return new Date(year - 1900, month - 1, 1);
			}
		}
		if (rawDate.matches("-?\\d{5,6}")) {
			//Excel中的日期在常规格式下的值(1900-1-1过去的天数 + 2)
			return new Date(Long.parseLong(rawDate) * 86400000L - 2209161600000L);
		} else if (rawDate.matches("-?\\d{7,}")) {
			//1970-1-1 00:00:00过去的毫秒数
			return new Date(Long.parseLong(rawDate));
		}
		String tmp[] = rawDate.replaceAll("\\D+", " ").trim().split("\\s+");
		if (tmp.length == 0 || tmp[0].isEmpty()) {
			return null;
		}
		Integer mid;
		Integer year = Integer.parseInt(tmp[0]);
		Integer month = tmp.length > 1 ? Integer.parseInt(tmp[1]) : 1;
		Integer date = tmp.length > 2 ? Integer.parseInt(tmp[2]) : 1;
		if (month > 31) {
			mid = month; month = year; year = mid;
		} else if (date > 31) {
			mid = date; date = year; year = mid;
		}
		if (month > 12) {
			mid = date;	date = month; month = mid;
		}
		if (year < 10) {
			year += 2000;
		} else if (year < 100) {
			year += 1900;
		}
		return new Date(year - 1900, month - 1, date);
	}
	
	/**
	 * 根据乱七八糟的经费字符串分析出经费
	 * @param input String.
	 * @return 
	 */
	public Double getFee(String input) {
		String tmp = toDBC(input).replaceAll("[^\\d\\.]", "");
		try {
			return Double.parseDouble(tmp);
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	/**
	 * 全角转半角
	 * @param input 全角字符串.
	 * @return 半角字符串
	 */
	public String toDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	/**
	 * 比较两个用分隔符隔开的电话号码是否存在包含
	 * @param str1
	 * @param str2
	 * @return true：是，false：否
	 */
	public boolean checkContains(String str1, String str2){
		//方法一，比较尾6位数
//		str1 = StringTool.toDBC(str1);
//		str2 = StringTool.toDBC(str2);
//		String[] str1s = str1.split("[^-\\d\\(\\)]+");
//		String[] str2s = str2.split("[^-\\d\\(\\)]+");
//		for (String phone1 : str1s) {
//			if (phone1.length() >= 6) {//小于6位的电话号码判定为无效号码
//				String suffix = phone1.substring(phone1.length() - 6);
//				for (String phone2 : str2s) {
//					if(phone2.length() >= 6 && phone2.endsWith(suffix)){
//						return true;
//					}
//				}
//			}
//		}
//		return false;
		
		//方法二，判断包含关系
		//[全角转半角] + [(027)转027-]+ [去掉"-"]
		str1 = StringTool.toDBC(str1).replaceAll("\\((\\d*)\\)", "$1-").replace("-", "");
		str2 = StringTool.toDBC(str2).replaceAll("\\((\\d*)\\)", "$1-").replace("-", "");
		//分割子项，非"-"、数字、()的作为分隔符
		String[] str1s = str1.split("[^-\\d\\(\\)]+");
		String[] str2s = str2.split("[^-\\d\\(\\)]+");
		//先判断str2 是否包含 str1 的每一项
		for (String str : str1s) {
			if (str.length() >= 6 && str2.contains(str)) {//小于6位的电话号码判定为无效号码
				return true;
			}
		}
		//再判断str1 是否包含 str2 的每一项
		for (String str : str2s) {
			if (str.length() >= 6 && str1.contains(str)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 检测两个邮箱字符串是否存在相同
	 * @param str1	
	 * @param str2
	 * @return	true：是，false：否
	 */
	public boolean checkEmail(String str1, String str2){
		if (null == str1 || str1.trim().isEmpty() || null == str2 || str2.trim().isEmpty()) {
			return false;
		}
		str1 = StringTool.toDBC(str1);
		str2 = StringTool.toDBC(str2);
		String[] str1s = str1.split("[\\s;,、]+");
		String[] str2s = str2.split("[\\s;,、]+");
		//先判断str2 是否包含 str1 的每一项
		for (String email1 : str1s) {
			for (String email2 : str2s) {
				if (!email1.isEmpty() && email1.equals(email2)) {//电话号码或邮箱的位数最小为7位
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 比较两个身份证号码是否相同，如果相同则返回较长的号码
	 * @param idCardNumber1
	 * @param idCardNumber2
	 * @return 相同：返回号码，不相同：返回null
	 */
	public String checkAndGetIdCardNumber(String idCardNumber1, String idCardNumber2) {
		if (null == idCardNumber2 || null == idCardNumber1) {
			return null;
		}
		String repexStr = "[0-9]{15}|[0-9]{17}[0-9Xx]"; 
		String idCardNumber = null;
		idCardNumber1 = idCardNumber1.toUpperCase();
		idCardNumber2 = idCardNumber2.toUpperCase();
		if (idCardNumber1.matches(repexStr) && idCardNumber2.matches(repexStr)) {
			int lenth1 = idCardNumber1.length();
			int lenth2 = idCardNumber2.length();
			if (lenth1 == lenth2) {
				if (idCardNumber1.equals(idCardNumber2)) {
					idCardNumber = idCardNumber1;
					return idCardNumber;
				}
			}else if (lenth1 > lenth2) {
				idCardNumber = idCardNumber1;
				idCardNumber1 = idCardNumber1.substring(0, 6) + idCardNumber1.substring(8, lenth1 - 1);//变成15位身份证号码
				if (idCardNumber1.equals(idCardNumber2)) {
					return idCardNumber;
				}
			}else {
				idCardNumber = idCardNumber2;
				idCardNumber2 = idCardNumber2.substring(0, 6) + idCardNumber2.substring(8, lenth2 - 1);//变成15位身份证号码
				if (idCardNumber1.equals(idCardNumber2)) {
					return idCardNumber;
				}
			}
		}
		return null;
	}
	
	/**
	 * 比较两个身份证号码是否相同
	 * @param idCardNumber1
	 * @param idCardNumber2
	 * @return
	 */
	public boolean checkIdCardNumber(String idCardNumber1, String idCardNumber2) {
		if (null == idCardNumber2 || null == idCardNumber1) {
			return false;
		}
		String repexStr = "[0-9]{15}|[0-9]{17}[0-9Xx]"; 
		idCardNumber1 = idCardNumber1.toUpperCase();
		idCardNumber2 = idCardNumber2.toUpperCase();
		boolean result = idCardNumber1.matches(repexStr) && idCardNumber2.matches(repexStr);
		if (result) {
			int lenth1 = idCardNumber1.length();
			int lenth2 = idCardNumber2.length();
			if (lenth1 == lenth2) {
				result = idCardNumber1.equals(idCardNumber2);
			}else if (lenth1 > lenth2) {
				idCardNumber1 = idCardNumber1.substring(0, 6) + idCardNumber1.substring(8, lenth1 - 1);//变成15位身份证号码
				result = idCardNumber1.equals(idCardNumber2);
			}else {
				idCardNumber2 = idCardNumber2.substring(0, 6) + idCardNumber2.substring(8, lenth2 - 1);//变成15位身份证号码
				result = idCardNumber1.equals(idCardNumber2);
			}
		}
		return result;
	}
	
	/**
	 * 身份证与生日是否匹配
	 * @param idCardNumber
	 * @param birthday
	 * @return 
	 */
	@SuppressWarnings("deprecation")
	public boolean IdNumberMatchBirthday(String idCardNumber, Date birthday) {
		boolean result = idCardNumber.matches("[0-9]{15}|[0-9]{17}[0-9Xx]");
		int year = 0;
		int month = 0;
		int date = 0;
		if (result) {
			if (idCardNumber.length() == 15) {
				year = Integer.parseInt("19" + idCardNumber.substring(6, 8));
				month = Integer.parseInt(idCardNumber.substring(8, 10));
				date = Integer.parseInt(idCardNumber.substring(10, 12));
			} else {
				year = Integer.parseInt(idCardNumber.substring(6, 10));
				month = Integer.parseInt(idCardNumber.substring(10, 12));
				date = Integer.parseInt(idCardNumber.substring(12, 14));
			}
			String idBirthDay = sdf.format(new Date(year - 1900, month-1, date));
			result = idBirthDay.equals(sdf.format(birthday));
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public String getBirthDayFormIdNumber(String idCardNumber) {
		boolean result = idCardNumber.matches("[0-9]{15}|[0-9]{17}[0-9Xx]");
		String idBirthDay = null;
		if (result) {
			int year = 0;
			int month = 0;
			int date = 0;
			if (idCardNumber.length() == 15) {
				year = Integer.parseInt("19" + idCardNumber.substring(6, 8));
				month = Integer.parseInt(idCardNumber.substring(8, 10));
				date = Integer.parseInt(idCardNumber.substring(10, 12));
			} else {
				year = Integer.parseInt(idCardNumber.substring(6, 10));
				month = Integer.parseInt(idCardNumber.substring(10, 12));
				date = Integer.parseInt(idCardNumber.substring(12, 14));
			}
			idBirthDay = sdf.format(new Date(year - 1900, month-1, date));
		}
		return idBirthDay;
	}
	
	public boolean IdCardNumberChecker(String idCardNumber) {
		boolean result = idCardNumber.matches("[0-9]{15}|[0-9]{17}[0-9Xx]");
		if (result) {
			int year, month, date;
			if (idCardNumber.length() == 15) {
				year = Integer.parseInt(idCardNumber.substring(6, 8));
				month = Integer.parseInt(idCardNumber.substring(8, 10));
				date = Integer.parseInt(idCardNumber.substring(10, 12));
			} else {
				year = Integer.parseInt(idCardNumber.substring(6, 10));
				month = Integer.parseInt(idCardNumber.substring(10, 12));
				date = Integer.parseInt(idCardNumber.substring(12, 14));
			}
			switch (month) {
			case 2:
				result = (date >= 1) && (year % 4 == 0 ? date <= 29 : date <= 28);
				break;
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				result = (date >= 1) && (date <= 31);
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				result = (date >= 1) && (date <= 31);
				break;
			default:
				result = false;
				break;
			}
		}
		return result;
	}
}
