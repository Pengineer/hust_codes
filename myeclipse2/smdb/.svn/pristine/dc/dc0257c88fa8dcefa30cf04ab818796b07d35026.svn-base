package csdc.service.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.bean.Account;
import csdc.bean.Person;
import csdc.service.IAuxiliaryService;

/**
 * 辅助信息平台的Service
 * @author yangfq
 *
 */
public class AuxiliaryService extends BaseService implements IAuxiliaryService{
	
	/**
	 * 根据entityId和人员类型查找对应的账号
	 * @param entityId
	 * @param type 1:管理人员；2：外部专家；3：教师；4：学生
	 * @return Account
	 */
	public Account getAccountByBelongId(String entityId, int type){
		Account account = null;
		String personId = null;
		Map paraMap = new HashMap();
		paraMap.put("entityId", entityId);
		if (type == 1 ) {
			List accountList = dao.query(" from Account a where a.officer.id = :entityId", paraMap);
			if (accountList.size() != 0) {
				account = (Account)accountList.get(0);
			}
		} else if (type == 2) {
			// 专家账号传过来的是expertId，但是要存personId
			Person person = (Person) dao.query("select p from Person p, Expert e where p.id = e.person.id and e.id = :entityId", paraMap).get(0);
			// 所属ID更新为personId
			personId = person.getId();
		} else if (type == 3) {
			// 教师账号传过来的是teacherId，但是要存personId
			Person person = (Person) dao.query("select p from Person p, Teacher t where p.id = t.person.id and t.id = :entityId", paraMap).get(0);
			// 所属ID更新为personId
			personId = person.getId();
		} else if (type == 4) {
			// 学生账号传过来的是studentId，但是要存personId
			Person person = (Person) dao.query("select p from Person p, Student s where p.id = s.person.id and s.id = :entityId", paraMap).get(0);
			// 所属ID更新为personId
			personId = person.getId();
		}
		if (personId != null ) {
			paraMap.put("personId", personId);
			List accList = dao.query(" from Account a where a.person.id = :personId", paraMap);
			account = accList.size() != 0 ? (Account) accList.get(0) : null;
		}
		return account;
	}
	
	/**
	 * 根据人员类型和entityId得到PersonId
	 * @param entityId
	 * @param personType 1-5:管理人员；6：外部专家；7：教师；8：学生
	 */
	public String getPersonId(String entityId, int personType){
		String personId = null;
		Map paraMap = new HashMap();
		paraMap.put("entityId", entityId);
		if (personType == 1 || personType == 2 || personType == 3 || personType == 4 || personType == 5){ //管理人员传的是officerId
			personId = (String) dao.query("select p.id from Officer o, Person p where o.person.id = p.id and o.id = :entityId", paraMap).get(0);
		} else if (personType == 6 ) {
			// 专家账号传过来的是expertId，但是要存personId
			personId = (String) dao.query("select p.id from Person p, Expert e where p.id = e.person.id and e.id = :entityId", paraMap).get(0);
		} else if (personType == 7 ) {
			// 教师账号传过来的是teacherId，但是要存personId
			personId = (String) dao.query("select p.id from Person p, Teacher t where p.id = t.person.id and t.id = :entityId", paraMap).get(0);
		} else if (personType == 8 ) {
			// 学生账号传过来的是studentId，但是要存personId
			personId = (String) dao.query("select p.id from Person p, Student s where p.id = s.person.id and s.id = :entityId", paraMap).get(0);
		}
		return personId;
	}
	
	/**
	 * 根据前台的personType转换成对应的人员类型
	 * @param personType
	 */
	public String getPersonType(int personType){
		String chineseType = "";
		switch (personType) {
		case 1:
			chineseType = "部级管理人员";
			break;
		case 2:
			chineseType = "省级管理人员";
			break;
		case 3:
			chineseType = "高校管理人员";
			break;
		case 4:
			chineseType = "院系管理人员";
			break;
		case 5:
			chineseType = "基地管理人员";
			break;
		case 6:
			chineseType = "外部专家";
			break;
		case 7:
			chineseType = "教师";
			break;
		case 8:
			chineseType = "学生";
			break;
		default:
			break;
		}
		return chineseType;
	}
	
	/**
	 * 统计研究人员参与项目的立项、中检、结项的情况
	 * @param personId 研究人员的ID
	 * @param director 0：参与的全部项目；1：作为负责人的项目
	 * @return Map 申报、立项、中检、结项的情况
	 */
	public Map projectStatistic(String personId, int director) {
		Long[] number = { 0L, 0L, 0L, 0L };// 分别对应申报、立项、中检、结项的数量
		Map paraMap = new HashMap();
		StringBuffer hql = new StringBuffer();
		StringBuffer hql4Mid = new StringBuffer();
		StringBuffer hql4End = new StringBuffer();
		StringBuffer hql4App = new StringBuffer();
		StringBuffer hql4Gra = new StringBuffer();
		Map map = new HashMap();
		map.put("personId", personId);
		
		hql4App.append("select app.id, app.name, app.type, app.applicantName from ProjectApplication app, ProjectMember pm " 
				+ "where pm.applicationId = app.id and pm.member.id = :personId and ( app.finalAuditStatus !=3 or app.finalAuditResult !=2) ");
		if (director == 1) {//作为项目负责人参与的项目
			hql4App.append(" and pm.isDirector = 1 ");
		}
		List appList = dao.query(hql4App.toString(), map);
		paraMap.put("appList", appList.size() == 0 ? null : appList);//未立项的项目
		hql4Gra.append("select pg.id, pg.name, pg.projectType, pg.status, pg.applicantName from ProjectGranted pg, ProjectApplication app, ProjectMember pm "
				+ "where pg.applicationId = app.id and pm.applicationId = app.id and pm.member.id = :personId ");
		if (director == 1) {//作为项目负责人参与的项目
			hql4Gra.append(" and pm.isDirector = 1 ");
		}
		List graList = dao.query(hql4Gra.toString(), map);
		paraMap.put("graList", graList.size() == 0 ? null : graList);//已经立项的项目
		
		hql.append("select count(app.id) from ProjectApplication app, ProjectMember pm where pm.applicationId = app.id and pm.member.id = :personId ");
		//申报
		if (director == 1) {//作为项目负责人参与的项目
			hql.append(" and pm.isDirector = 1 ");
		}
		number[0] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();
		//立项
		hql.append(" and app.finalAuditStatus=3 and app.finalAuditResult=2 ");
		if (director == 1) {//作为项目负责人参与的项目
			hql.append(" and pm.isDirector = 1 ");
		}
		number[1] = ((Number) dao.query(hql.toString(), map).get(0)).longValue();
		//中检
		hql4Mid.append("select count(midi.id) from ProjectMidinspection midi, ProjectGranted pg, ProjectApplication app, ProjectMember pm"
				+ " where midi.grantedId = pg.id and pg.applicationId = app.id and pm.applicationId = app.id and pm.member.id = :personId "
				+ " and midi.finalAuditResult=2 and midi.finalAuditStatus=3 ");
		if (director == 1) {//作为项目负责人参与的项目
			hql.append(" and pm.isDirector = 1 ");
		}
		number[2] = ((Number) dao.query(hql4Mid.toString(), map).get(0)).longValue();
		//结项
		hql4End.append("select count(endi.id) from ProjectEndinspection endi, ProjectGranted pg, ProjectApplication app, ProjectMember pm"
				+ " where endi.grantedId = pg.id and pg.applicationId = app.id and pm.applicationId = app.id and pm.member.id = :personId "
				+ " and endi.finalAuditResultEnd=2 and endi.finalAuditStatus=3 ");
		if (director == 1) {//作为项目负责人参与的项目
			hql.append(" and pm.isDirector = 1 ");
		}
		number[3] = ((Number) dao.query(hql4End.toString(), map).get(0)).longValue();
		paraMap.put("projectNumber", number);
		return paraMap;
	}
	

	/**
	 * 查询科研人员的奖励情况
	 * @param personId 研究人员的ID
	 */
	public List getAward(String personId){
		Map paraMap = new HashMap();
		StringBuffer hql = new StringBuffer();
		paraMap.put("personId", personId);
		hql.append("select aa.id, aa.productName, pr.productType, aa.session, aa.status from AwardApplication aa , Product pr where pr.id = aa.product.id and aa.applicant.id = :personId");
		List awardList = dao.query(hql.toString(),paraMap);
		return awardList;
	}
	
	/**
	 * 查询科研人员的成果情况
	 * @param personId 研究人员的ID
	 */
	
	public Map getProduct(String personId){
		Map paraMap = new HashMap();
		paraMap.put("personId", personId);
		//论文
		List paperList = dao.query("select p.id, p.chineseName, s.name, p.agencyName, p.publication from Paper p left join p.type s where p.author.id = :personId", paraMap);
		//著作
		List bookList = dao.query("select b.id, b.chineseName, s.name, b.agencyName, b.publishUnit from Book b left join b.type s where b.author.id = :personId", paraMap);
		//报告
		List consuList = dao.query("select c.id, c.chineseName, '研究咨询报告', c.agencyName, c.adoptType from Consultation c where c.author.id = :personId", paraMap);
		//电子出版物
		List elecList = dao.query("select e.id, e.chineseName, s.name, e.agencyName, e.publishUnit from Electronic e left join e.type s where e.author.id = :personId", paraMap);
		paraMap.clear();
		paraMap.put("paperList", paperList.size() == 0 ? null: paperList);
		paraMap.put("bookList", bookList.size() == 0 ? null : bookList);
		paraMap.put("consuList", consuList.size() == 0 ? null: consuList);
		paraMap.put("elecList", elecList.size() == 0 ?null:elecList);
		return  paraMap;
	}
	
	/**
	 * 组装查询的数据类型
	 * @param query_data 查询的数据类型
	 */
	public String getFormatedData(List<Integer> query_data){
		StringBuffer resultsString = new StringBuffer();
		for (int data : query_data) {
			switch (data) {
			case 1:
				resultsString.append("访问记录,　");
				break;
			case 2:
				resultsString.append("参与项目,　");
				break;
			case 3:
				resultsString.append("获奖情况,　");
				break;
			case 4:
				resultsString.append("所获成果,　");
				break;
			default:
				break;
			}
			
		}
		if(resultsString.toString().endsWith(",　")) {
			return  resultsString.toString().substring(0, resultsString.toString().length() - 2);
		} else {
			return resultsString.toString();
		}
	}
	
	
	/**
	 * 计算比例（率）
	 * @param dividend	被除数
	 * @param divider	除数
	 * @return	百分数（保留两位小数）
	 */
	public Object calculate(Object dividend, Object divider){
		Number per = 0;
		DecimalFormat df=(DecimalFormat)NumberFormat.getPercentInstance();
		df.applyPattern("#0.00");
		
		if (null != divider && null != dividend) {
			double _dividend = Double.parseDouble(String.valueOf(dividend));
			double _divider = Double.parseDouble(String.valueOf(divider));
			if(_divider != 0){
				double result = _dividend / _divider;
				String percentResult = df.format(result*100);
				try {
					per = df.parse(percentResult);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {
				try {
					per = df.parse("1");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return per+"%";
	}
	
	/**
	 * 处理list的数据
	 * @param listData 处理之前的LIST
	 * @param List laData 处理之后的LIST
	 */
	public void listDealWith(List listData,List laData) {
		Object[] o;// 缓存查询结果的一行
		String[] item;// 缓存查询结果一行中的每一字段
		
		for (Object p : listData) {
			o = (Object[]) p;
			item = new String[o.length];
			for (int i = 0; i < o.length; i++) {
				if (o[i] == null) {// 如果字段值为空，则以位置替换
					item[i] = "未知";
				} else {// 如果字段值非空，则做进一步判断
					item[i] = o[i].toString();
				}
			}
			laData.add(item);// 将处理好的数据存入laData
		}
	}
	
}
