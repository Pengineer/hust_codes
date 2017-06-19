package csdc.service.imp;

import java.util.HashMap;
import java.util.Map;

import csdc.service.IMobilePersonService;

public class MobilePersonService extends BaseService implements IMobilePersonService {
	
	/**
	 * 获取各类人员信息查看查询语句
	 * @param entityId   	待查看的人员id
	 * @param personType  待查看人员类型
	 * @param listType    待查看人员类别
	 * @return paraMap		查询语句hql ，参数map
	 */
	@SuppressWarnings("unchecked")
	public Map getViewHql(String entityId, String personType, Integer listType) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("entityId", entityId);
		if("manager".equals(personType)){
			switch (listType) {
			case 1://部级管理人员
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), ag.name, ag.sname, o.position, o.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress from Officer o left join o.person p left join o.agency ag where o.id = :entityId ");
				break;
			case 2://省级管理人员
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), ag.name, ag.sname, o.position, o.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress from Officer o left join o.person p left join o.agency ag where o.id = :entityId ");
				break;
			case 3://高校管理人员
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), u.name, u.sname, o.position, o.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress from Officer o left join o.person p left join o.agency u where o.id = :entityId ");
				break;
			case 4://院系管理人员
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), u.name, d.name, o.position, o.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress from Officer o left join o.person p left join o.department d left join d.university u where o.id = :entityId ");
				break;
			case 5://基地管理人员
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), i.name, u.name, o.position, o.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress from Officer o left join o.person p left join o.institute i left join i.subjection u where o.id = :entityId ");
				break;
			}
		}
		else if("researcher".equals(personType)){
			switch(listType){
			case 1://外部专家
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), e.agencyName, e.divisionName, e.position, e.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress, ac.specialityTitle from Expert e left join e.person p left join p.academic ac where e.id = :entityId ");
				break;
			case 2://教师
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), u.name, CONCAT(d.name, i.name), t.position, t.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress, ac.specialityTitle from Teacher t left join t.person p left join p.academic ac left join t.department d left join t.institute i left join t.university u where t.id = :entityId ");
				break;
			case 3://学生
				hql.append("select p.name, p.gender, TO_CHAR(p.birthday, 'yyyy-MM-dd'), u.name, CONCAT(d.name, i.name), s.major, s.type, p.mobilePhone, p.officePhone, p.email, p.officeAddress from Student s left join s.person p left join s.department d left join d.university u left join s.institute i where s.id = :entityId ");
				break;
			}
		}
		Map paraMap = new HashMap();
		paraMap.put("map", map);
		paraMap.put("hql", hql);
		return paraMap;
	}
}
