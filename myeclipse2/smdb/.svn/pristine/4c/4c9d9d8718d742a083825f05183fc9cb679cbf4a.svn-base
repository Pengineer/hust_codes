package csdc.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.service.IMessageAuxiliaryService;
import csdc.tool.HqlTool;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Pager;

public class MessageAuxiliaryService extends BaseService implements IMessageAuxiliaryService {
	
	
	/**
	 * 根据人员ID提供同领域的研究人员信息
	 * @param personId	研究人员id
	 * @param type	研究人员类型
	 * @return 
	 */
	public List getSameSearchData(String personId) {
		Boolean flag = false;
		Map map = new HashMap();
		map.put("personId", personId);
		List result = new ArrayList();
		List academicList = dao.query("from Academic ac where ac.person.id = :personId", map);
		if (academicList.size() != 0) {
			Academic academic = (Academic) academicList.get(0);
			if (null != academic.getDisciplineType()) { //第一步：如果当前研究人员学科门类信息存在，找到与当前研究人员具有相同学科门类的全部人员信息
				map.put("disciplineType", "%" + academic.getDisciplineType() + "%");//当前研究人员的学科门类
				List ids = dao.query("select person.id from Person person left join person.academic ac where person.id != :personId and LOWER(ac.disciplineType) like :disciplineType ", map);
				for (int i = 0; i < ids.size(); i++) {
					//第二步：根据找到的全部人员信息，找到跟指定研究人员具有相同学科信息的研究人员
					map.put("personId", (String) ids.get(i));
					List disciplineList = dao.query("select ac.discipline from Academic ac where ac.person.id = :personId", map);//当前研究人员的学科信息
					if (null != academic.getDiscipline()) {
						String[] discipline = academic.getDiscipline().split("; ");
						for (String string : discipline) {
							if (disciplineList.toString().contains(string)) {
								flag = true;
							} else {
								flag = false;
							}
						}
					}
					while(flag){//当前研究人员的学科包含了指定的研究人员的学科信息
						//第三步：根据第二步得到的人员信息，找到跟指定研究人员具有相同的研究领域的人员
						map.put("personId", (String) ids.get(i));
						List researchFieldList = dao.query("select ac.researchField from Academic ac where ac.person.id = :personId ", map);
						if (null!= academic.getResearchField()) {
							String researchField = academic.getResearchField();
							if (researchFieldList.toString().contains(researchField)){
								map.put("personId", (String) ids.get(i));
								//第四步  找出那些有立项信息的人员（只有立项了的人员才推荐）
								List grantedList = dao.query(" from ProjectGranted pg where pg.applicantId = :personId", map);
								if (grantedList.size() != 0) {
									//根据personId找到当前人员的类型
									int type = getSearchTypeByid((String) ids.get(i));
									if (type == 1) {
										List personList = dao.query("select p.name, t.university.name, a.discipline, p.id from Teacher t, Person p left join p.academic a where t.person.id = p.id and p.id = :personId", map);
										result.add(personList);
									} else if (type == 2) {
										List personList = dao.query("select p.name, e.agencyName, a.discipline, p.id from Expert e, Person p left join p.academic a where e.person.id = p.id and p.id = :personId", map);
										result.add(personList);
									} else if (type == 3) {
										List personList = dao.query("select p.name, st.university.name, a.discipline, p.id from Student st, Person p left join p.academic a where st.person.id = p.id and p.id = :personId", map);
										result.add(personList);
									}
								}
							}
							flag = false;
						}
					}
				}
			}
		} 
		return result;
	}
	
	/**
	 * 根据personId找到当前研究人员的类型
	 * @param personId
	 * @return type 1：教师；2：外部专家； 3：学生
	 */
	public int getSearchTypeByid(String personId){
		int type = 0;
		Map paraMap = new HashMap();
		paraMap.put("personId", personId);
		//首先查找是否是教师
		List teacher = dao.query("select t.id from Teacher t where t.person.id = :personId",paraMap);
		if (teacher.size() != 0) {
			type = 1;
		} else {
			List expert = dao.query("select e.id from Expert e where e.person.id = :personId",paraMap);
			if (expert.size() != 0) {
				type = 2;
			} else {
				List studet = dao.query("select s.id from Student s where s.person.id = :personId",paraMap);
				if (studet.size() != 0) {
					type = 3;
				}
			}
		}
		return type;
	}
	
	/**
	 *  @param loginer	当前登陆者
	 *  @return List： 菜单的名称和菜单的入口地址，
	 *  例如{"description":"一般项目立项数据","url":"project/general/application/granted/toList.action?update=1"}
	 * 
	 */
	public List getHistoryMenu(LoginInfo loginer){
		List result = new ArrayList();
		Map paraMap = new HashMap();
		StringBuffer hql = new StringBuffer();
		paraMap.put("accountId", loginer.getAccount().getId());
		hql.append("select to_char(l.request), to_char(l.date,'yyyy-MM-dd'), count(l.id) from Log l where l.account.id = :accountId and to_char(l.response) like '%进入列表%' ");
		hql.append(" group by to_char(l.request),to_char(l.date,'yyyy-MM-dd') order by to_char(l.date,'yyyy-MM-dd') desc, count(l.id) desc ");
		//统计用户访问的情况，可以找到用户经常访问的那些数据
		List laData = dao.query(hql.toString(),paraMap, 0 , 20);
		if (laData.size() > 0) {
			for (int i = 0; i < laData.size() && i<10; i++) {
				Object[] oo = (Object[]) laData.get(i);
				String data = (String) oo[0];
				JSONObject jsonOjb = JSONObject.fromObject(data);
				String name = jsonOjb.getString("description");
				String url = jsonOjb.getString("url");
				result.add(name);
				result.add(url);
			}
		}
		for (int i = 0; i < result.size() - 1; i++) {
			for (int j = result.size() - 1; j > i; j--) {
				if (result.get(j).equals(result.get(i))) {
					result.remove(j);
				}
			}
		}  
		return result;
	}
	
	
	/**
	 * 根据指定账号的日志记录找到用户查看数据的记录
	 * @param account 账号
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @param num  查询条数
	 * @return List<Map> name：用户查看的数据名称；sum：用户查看某一条记录的次数
	 */
	public List getLogHistory(Account account, Date startDate, Date endDate, int num){
		List laData = new ArrayList();
		Map paraMap = new HashMap();
		StringBuffer hql = new StringBuffer();
		paraMap.put("accountId", account.getId());
		hql.append("select l.eventDscription, l.ip, count(l.id) from Log l where l.account.id = :accountId ");
		if (startDate != null) {// 设置统计开始时间
			hql.append(" and l.date >= :startDate ");
			paraMap.put("startDate", startDate);
		}
		if (endDate != null) {// 设置统计结束时间
			hql.append(" and l.date <= :endDate ");
			paraMap.put("endDate", endDate);
		}
		hql.append(" group by l.eventDscription, l.ip order by count(l.id) desc ");
		//统计用户访问的情况，可以找到用户经常访问的那些数据
		if (num == 0) {
			laData = dao.query(hql.toString(), paraMap);
		} else {
			laData = dao.query(hql.toString(), paraMap, 0, num);
		}
		return laData;
	}
	
	/**
	 * 项目模块列表的辅助信息显示
	 * @param pager
	 * @param column 排序的列
	 * @param xTitle X轴标题
	 * @param yTitle Y轴标题
	 * @param key 用于统计的主键
	 * @return map
	 */
	public Map projectListAssist(Pager pager, String column, String xTitle , String yTitle, String key){
		List lData = new ArrayList();
		List lTitle = new ArrayList();
		Map jsonMap = new HashMap();
		HqlTool hqlTool = new HqlTool(pager.getHql());
		if (column.contains(", ")) {
			String[] c = column.split(", ");
			lTitle.add("待审");
			lTitle.add("同意");
			lTitle.add("不同意");
			for (int i = 0; i < c.length; i++) {
				if (c[i].contains(" desc")) {
					c[i] = c[i].substring(0, c[i].length()-5);
				}
			}
			StringBuffer hql1 = new StringBuffer();
			hql1.append("select " + hqlTool.selectClause);
			hql1.append(" from " + hqlTool.fromClause);
			hql1.append(" where ");
			String clause1 = hql1.toString();
			StringBuffer hql2 = new StringBuffer();
			hql2.append(hqlTool.whereClause);
			if (null != hqlTool.groupClause) {
				hql2.append(" group by " + hqlTool.groupClause);
			}
			hql2.append(hqlTool.havingClause == null ? " ": " having "+hqlTool.havingClause);
			String clause2 = hql2.toString();
			//待审
			StringBuffer hql4pending = new StringBuffer();
			hql4pending.append(clause1);
			String pending = c[0] + " = 0 and ";
			hql4pending.append(pending);
			hql4pending.append(clause2);
			//同意
			StringBuffer hql4agree = new StringBuffer();
			hql4agree.append(clause1);
			String agree = c[0] + " != 0 and " + c[1] + " = 2 and ";
			hql4agree.append(agree);
			hql4agree.append(clause2);
			//不同意
			StringBuffer hql4disAgree = new StringBuffer();
			hql4disAgree.append(clause1);
			String disAgree = c[0] + " != 0 and " + c[1] + " = 1 and ";
			hql4disAgree.append(disAgree);
			hql4disAgree.append(clause2);
			String[] HQL = {
					hql4pending.toString(),
					hql4agree.toString(),
					hql4disAgree.toString()
			};
			List<Object[]> listObject = new ArrayList<Object[]>(); 
			for(int i = 0; i < HQL.length; i++){
				int num = (int) dao.count(HQL[i],pager.getParaMap());
				Object[] dataObjects = new Object[2];
				dataObjects[0] = num;
				dataObjects[1] = lTitle.get(i);
				listObject.add(dataObjects);
			}
			Comparator<Object[]> countComparator = new Comparator<Object[]>() {
				public int compare(Object[] o1, Object[] o2) {
					Double cnt1 = Double.parseDouble(String.valueOf(o1[0]));
					Double cnt2 = Double.parseDouble(String.valueOf(o2[0]));
					return cnt2.compareTo(cnt1);
				}
			};
			Collections.sort(listObject, countComparator);
			lTitle.clear();
			for (int i = 0; i < listObject.size(); i++) {
				Object[] ob = listObject.get(i);
				lData.add(ob[0]);
				lTitle.add(ob[1]);
			}
		} else {
			StringBuffer hql = new StringBuffer();
			if (column.contains(" desc")) {
				column = column.substring(0, column.length()-5);
			}
			String selectString = hqlTool.selectClause.substring(0, 6);
			hql.append("select count(distinct " +  key + ") , " + column);
			hql.append(" from " + hqlTool.fromClause);
			hql.append(" where " +hqlTool.whereClause);
			hql.append(" group by " +column);
			hql.append(" order by " + " count(distinct " +  key + ") desc ");
			hql.append(hqlTool.havingClause == null ? " ": " having "+hqlTool.havingClause);
			List<Object[]> list = dao.query(hql.toString(),pager.getParaMap());
			int j = list.size() >= 10 ? 10 : list.size();
			for (int i = 0; i < j; i++) {
				Object[] o = (Object[]) list.get(i);
				if (key.equals("gra.id") && column.equals("gra.status")) { //立项状态数字变汉字
					int status = (Integer) o[1];
					if (status == 1) {
						lTitle.add("在研");
					} else if (status == 2) {
						lTitle.add("结项");
					} else if (status == 3) {
						lTitle.add("中止");
					} else if (status == 4) {
						lTitle.add("撤项");
					} else {
						lTitle.add(o[1] == null ? "未知" : o[1]);
					}
				} else {
					lTitle.add(o[1] == null ? "未知" : o[1]);
				}
				lData.add(o[0]);
			}
		}
		jsonMap.put("lTitle", lTitle);
		jsonMap.put("lData", lData);
		jsonMap.put("xTitle", xTitle);
		jsonMap.put("yTitle", yTitle);
		return jsonMap;
	}
	
}
