package csdc.tool.execution.importer.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Discipline;
import csdc.bean.Doctoral;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 导入项目Excel所需的工具包
 * @author xuhan
 *
 */
@Component
public class Tool {
	
	/**
	 * 学科名称 到 学科代码 的映射
	 */
	public Map<String, String> discNameCodeMap;

	/**
	 * 学科代码 到 学科名称 的映射
	 */
	public Map<String, String> discCodeNameMap;

	/**
	 * 学校代码  到 该校“其他院系” 的映射
	 */
	public Map<String, Department> univNameOtherDeptMap;

	/**
	 * 学校代码+(院系/基地/博士点)名称 到机构ID的映射
	 */
	public Map<String, String> unitMap;

	/**
	 *  Person Id 到学术信息Id的映射
	 */
	public Map<String, String> academicMap;
	
	@Autowired
	private IHibernateBaseDao dao;

	
//	public Session session;

	/////////////////////////////////////////////////////////////////////////////////////
	
//	/**
//	 * 默认构造器，自行生成一个session
//	 */
//	public Tools() {
//		setSessionFactory((SessionFactory) SpringBean.getBean("sessionFactory", ApplicationContainer.sc));
//		session = super.getSession();
//	}
//
//	/**
//	 * 从外部引进session，当需要配合外部数据库读写操作时请用这个
//	 * @param session
//	 */
//	public Tools(Session session) {
//		this.session = session;
//	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
//		teacherMap = null;
		discNameCodeMap = null;
		discCodeNameMap = null;
		univNameOtherDeptMap = null;
		academicMap = null;
//		officerMap = null;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 初始化学校代码  到 该校“其他院系” 的映射
	 * @return
	 */
	public void initUnivNameOtherDeptMap() {
		long beginTime  = new Date().getTime();
		List<Object[]> list = dao.query("select d, u.code from Department d left join d.university u where d.name = '其他院系' ");
		univNameOtherDeptMap = new HashMap<String, Department>();
		for (Object[] objects : list) {
			univNameOtherDeptMap.put((String)objects[1], (Department) objects[0]);
		}
		System.out.println("initUnivNameOtherDeptMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
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
	
//	/**
//	 * 初始化 学校代码+姓名 到 教师实体(内含person实体、department实体)的映射
//	 * @return
//	 */
//	public void initTeacherMap() {
//		long beginTime  = new Date().getTime();
//		teacherMap = new HashMap<String, String>();
//		List<Object[]> list = baseDao.query("select t.university.code, t.person.name, t.id from Teacher t where t.institute is null");
//		for (Object[] str : list) {
//			teacherMap.put(str[0] + ((String) str[1]).replaceAll("[^A-Za-z\\u4e00-\\u9fa5·]+", ""), (String) str[2]);
//		}
//		System.out.println("initTeacherMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
//	}
//	
//	/**
//	 * 初始化 学校代码+姓名 到 管理人员实体(内含person实体)的映射
//	 * @return
//	 */
//	public void initOfficerMap() {
//		long beginTime  = new Date().getTime();
//		officerMap = new HashMap<String, List<String>>();
//		List<Object[]> list = baseDao.query("select o.agency.code, o.person.name, o.id from Officer o");
//		for (Object[] str : list) {
//			officerMap.put(str[0] + ((String) str[1]).replaceAll("[^A-Za-z\\u4e00-\\u9fa5·]+", ""), (String) str[2]);
//		}
//		System.out.println("initOfficerMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
//	}
		
	/**
	 * 初始化 Person Id 到学术信息的映射
	 * @return
	 */
	public void initAcademicMap() {
		long beginTime  = new Date().getTime();
		academicMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select a.person.id, a.id from Academic a");
		for (Object[] str: list) {
			academicMap.put((String)str[0], (String) str[1]);
		}
		System.out.println("initAcademicMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	/**
	 * 初始化 学校代码+(院系/基地/博士点/重点学科)名称 到机构实体的映射
	 * @return
	 */
	public void initUnitMap() {
		long beginTime  = new Date().getTime();
		unitMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select d.university.code, d.name, d.id from Department d");
		for (Object[] str : list) {
			String univCode = (String) str[0];
			String unitName = StringTool.toDBC((String) str[1]).replaceAll("\\s+", "");
			if (!unitName.isEmpty()) {
				unitMap.put(univCode + unitName, (String) str[2]);
			}
		}
		
		list = dao.query("select i.subjection.code, i.name, i.id from Institute i");
		for (Object[] str : list) {
			String univCode = (String) str[0];
			String unitName = StringTool.toDBC((String) str[1]).replaceAll("\\s+", "");
			if (!unitName.isEmpty()) {
				unitMap.put(univCode + unitName, (String) str[2]);
				if (unitName.endsWith("研究院")) {
					unitMap.put(univCode + unitName.replaceAll("研究院$", "研究中心"), (String) str[2]);
				} else if (unitName.endsWith("研究中心")) {
					unitMap.put(univCode + unitName.replaceAll("研究中心$", "研究院"), (String) str[2]);
				}
			}
		}

		list = dao.query("select d.university.code, d.name, d.id from Doctoral d");
		for (Object[] str : list) {
			String univCode = (String) str[0];
			String unitName = StringTool.toDBC((String) str[1]).replaceAll("\\s+", "");
			if (!unitName.isEmpty()) {
				unitMap.put(univCode + unitName, (String) str[2]);
			}
		}

		list = dao.query("select d.university.code, d.name, d.id from Discipline d");
		for (Object[] str : list) {
			String univCode = (String) str[0];
			String unitName = StringTool.toDBC((String) str[1]).replaceAll("\\s+", "");
			if (!unitName.isEmpty()) {
				unitMap.put(univCode + unitName, (String) str[2]);
			}
		}
		System.out.println("initUnitMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 根据机构名和学校找到院系/基地/博士点/重点学科 实体，找不到则new一个返回
	 * @param personName
	 * @param university
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Object getUnit(String unitName, Agency university, Class unitClass, boolean addIfNotFind) throws InstantiationException, IllegalAccessException {
		if (unitMap == null) {
			initUnitMap();
		}
		String key = university.getCode() + StringTool.toDBC(unitName).replaceAll("\\s+", "");
		Object unit = unitMap.containsKey(key) ? dao.query(unitClass, unitMap.get(key)) : null;
		if (unit == null && addIfNotFind) {
			unit = unitClass.newInstance();
			if (unit instanceof Department) {
				((Department)unit).setUniversity(university);
				((Department)unit).setName(unitName);
			} else if (unit instanceof Institute) {
				((Institute)unit).setSubjection(university);
				((Institute)unit).setName(unitName);
				//没招，这个要非空，先随便填一个，出去了再改	- -!
				((Institute)unit).setType((SystemOption) dao.query(SystemOption.class, "qtyjjg"));
			} else if (unit instanceof Doctoral) {
				((Doctoral)unit).setUniversity(university);
				((Doctoral)unit).setName(unitName);
				((Doctoral)unit).setCode("-");
			} else if (unit instanceof Discipline) {
				((Discipline)unit).setUniversity(university);
				((Discipline)unit).setName(unitName);
				((Discipline)unit).setCode("-");
			}
			
			String id = (String) dao.add(unit);
			if (!university.getCode().equals(key)) {
				unitMap.put(key, id);
			}
			
		}
		return unit;
	}
	
	/**
	 * 获取person的 学术信息
	 * @param person
	 * @return
	 */
	public Academic getAcademic(Person person) {
		if (academicMap == null) {
			initAcademicMap();
		}
		
//		List<Academic> aList = baseDao.query("select a from Academic a where a.person.id = '" + person.getId() + "'");
//		Academic academic = aList.isEmpty() ? null : aList.get(0);
		Academic academic = academicMap.containsKey(person.getId()) ? (Academic)dao.query(Academic.class, academicMap.get(person.getId())) : null;
		if (academic == null) {
			academic = new Academic();
			academic.setPerson(person);
//			session.save(academic);
			academicMap.put(person.getId(), (String) dao.add(academic));
		}
		return academic;
	}
	
	/**
	 * 找到某学校的"其他院系"
	 * @param university
	 * @return
	 */
	public Department getOtherDepartment(Agency university) {
		if (univNameOtherDeptMap == null) {
			initUnivNameOtherDeptMap();
		}
		Department otherDepartment = univNameOtherDeptMap.get(university.getCode());
		if (otherDepartment == null) {
			otherDepartment = new Department();
			otherDepartment.setUniversity(university);
			otherDepartment.setName("其他院系");
			univNameOtherDeptMap.put(university.getCode(), otherDepartment);
			dao.add(otherDepartment);
		}
		return otherDepartment;
	}
	
	/**
	 * 把乱七八糟的学科代码or学科名称 变成 [ 学科代码/学科名称; ...]格式 
	 * @param input
	 * @return
	 */
	public String transformDisc(String input) {
		if (discNameCodeMap == null) {
			initDiscNameCodeMap();
		}
		String[] disc = StringTool.toDBC(input).toLowerCase().replaceAll("\\s*[·\\.]\\s*", ".").split("[^\\.\\w\\u4e00-\\u9fa5/]+|(?<=[^\\d\\.])(?=[\\d\\.])|(?<=[\\d\\.])(?=[^\\d\\.])");
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
		for (int j = 0; j < dList.size(); j++){
			String thisDisc = dList.get(j);
			//不是任意串的前缀的串才加入
			if (j == dList.size() - 1 || !dList.get(j + 1).startsWith(thisDisc)){
				if (result.length() > 0) {
					result.append("; ");
				}
				result.append(thisDisc).append("/").append(discCodeNameMap.get(thisDisc));
			}
		}
		return result.length() == 0 ? "" : result.toString();
	}
	
	/**
	 * 根据乱七八糟的日期字符串分析出正确日期
	 * @param rawDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date getDate(String rawDate) {
		if (rawDate == null) {
			return null;
		}
		rawDate = StringTool.toDBC(rawDate).trim();
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
		String tmp = StringTool.toDBC(input).replaceAll("[^\\d\\.]", "");
		try {
			return Double.parseDouble(tmp);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据乱七八糟的姓名字符串分析出姓名
	 * 中文姓名允许中间有'·'，不允许有空格
	 * 英文姓名允许中间有一个空格
	 * @param name 原始姓名字符串
	 * @return
	 */
	public String getName(String name) {
		name = name.replaceAll("[\\.。,，、]+", "·").replaceAll("[^A-Za-z\\u4e00-\\u9fa5 ·]+", "").replaceAll("^[· ]+", "").replaceAll("[· ]+$", "");
		Pattern p = Pattern.compile("[A-Za-z]");
		Matcher m = p.matcher(name);
		if (m.find()) {
			name = name.replaceAll("[· ]+", " ");
		} else {
			name = name.replaceAll("\\s+", "").replaceAll("·+", "·");
		}
		return name;
	}

	/**
	 * 根据年月日获取Date
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date getDate(Integer year, Integer month, Integer date) {
		return new Date(year - 1900, month - 1, date);
	}

	/////////////////////////////////////////////////////////

}
