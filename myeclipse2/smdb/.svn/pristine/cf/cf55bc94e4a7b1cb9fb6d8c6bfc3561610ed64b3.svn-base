package csdc.service.imp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import csdc.service.IMobilePersonService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Mobile;

public class MobilePersonService extends BaseService implements IMobilePersonService {
	
//	/**
//	 * 获取各类人员初级检索查询语句
//	 * @param loginer     当前登录账号信息对象引用变量
//	 * @param keyword     初级检索关键字：人员姓名
//	 * @param personType  人员类型：1:部级管理人员;2:省级管理人员;3:高校管理人员;4:院系管理人员;5.基地管理人员;6:外部专家;7:教师;8:学生;
//	 * @return paraMap    查询语句hql ，参数map
//	 */
//	@SuppressWarnings("unchecked")
//	public Map getSimpleSearchHql(LoginInfo loginer, String keyword, Integer personType) {
//		StringBuffer hql = new StringBuffer();
//		Map map = new HashMap();
//		switch (personType) {
//		case 1://部级管理人员
////			hql.append("select p.name, ag.name, o.id, p.gender, ag.sname, ag.id, o.position from Officer o left join o.person p left join o.agency ag ");
//			hql.append("select p.name, ag.name, o.id , o.position, ag.sname, p.gender from Officer o left join o.person p left join o.agency ag ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY) && loginer.getIsPrincipal() == 1) {// 部级账号
//				hql.append(" where ag.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" ag.type = 1 ");
//			break;
//		case 2://省级管理人员
////			hql.append("select p.name, ag.name, o.id, p.gender, ag.sname, ag.id, o.position from Officer o left join o.person p left join o.agency ag ");
//			hql.append("select p.name, ag.name, o.id, o.position, ag.sname, p.gender from Officer o left join o.person p left join o.agency ag ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级账号
//				hql.append(" where ag.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" ag.type = 2 ");
//			break;
//		case 3://高校管理人员
////			hql.append("select p.name, u.name, o.id, p.gender, u.sname, u.id, o.position from Officer o left join o.person p left join o.agency u ");
//			hql.append("select p.name, u.name, o.id, o.position, u.sname, p.gender from Officer o left join o.person p left join o.agency u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where (u.type = 3 or u.type = 4) and ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
//				hql.append(" where u.type = 4 and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if ((loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) && loginer.getIsPrincipal() == 1) {// 校级账号
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" o.department.id is null and o.institute.id is null ");
//			break;
//		case 4://院系管理人员
////			hql.append("select p.name, u.name, o.id, p.gender, d.name, d.id, u.id, o.position from Officer o left join o.person p left join o.department d left join d.subjection u ");
//			hql.append("select p.name, u.name, o.id, o.position, d.name, p.gender from Officer o left join o.person p left join o.department d left join d.university u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
//				hql.append(" where u.type = 4 and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT) && loginer.getIsPrincipal() == 1) {// 院系账号
//				hql.append(" where d.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" d.id is not null ");
//			break;
//		case 5://基地管理人员
////			hql.append("select p.name, u.name, o.id, p.gender, i.name, i.id, u.id, o.position from Officer o left join o.person p left join o.institute i left join i.subjection u ");
//			hql.append("select p.name, u.name, o.id, o.position, i.name, p.gender from Officer o left join o.person p left join o.institute i left join i.subjection u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
//				hql.append(" left join i.type sys where (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3) and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE) && loginer.getIsPrincipal() == 1) {// 基地账号
//				hql.append(" where i.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" i.id is not null ");
//			break;
//		case 6://外部专家
////			hql.append("select p.name, e.agencyName, e.id, p.gender, e.divisionName, e.position from Expert e left join e.person p ");
//			hql.append("select p.name, e.agencyName, e.id, ac.specialityTitle, e.divisionName, p.gender from Expert e left join e.person p left join p.academic ac ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
//				hql.append(" where 1=1 ");
//			} else {
//				hql.append(" where 1=0 ");
//			}
//			break;
//		case 7://教师
////			hql.append("select p.name, u.name, t.id, p.gender, u.id, d.name, d.id, i.name, i.id, t.position from Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u ");
//			hql.append("select p.name, u.name, t.id, ac.specialityTitle, CONCAT(d.name, i.name), p.gender from Teacher t left join t.person p left join p.academic ac left join t.department d left join t.institute i left join t.university u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，根据教师所在单位是否归省管进行判断
//				hql.append(" left join i.type sys where ((sys.code = '02' or sys.code = '03') and u.type = 3 or u.type = 4 ) and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据教师所在单位是否归校管进行判断
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号，根据教师是否在当前院系判断
//				hql.append(" where d.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 基地账号，根据教师是否在当前基地判断
//				hql.append(" where i.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" t.type = '专职人员' ");
//			break;
//		case 8://学生
////			hql.append("select p.name, u.name, s.id, p.gender, u.id, d.name, d.id, i.name, i.id, s.type from Student s left join s.person p left join s.department d left join s.institute i left join s.university u ");
//			hql.append("select p.name, u.name, s.id, s.type, CONCAT(d.name, i.name), p.gender from Student s left join s.person p left join s.department d left join s.institute i left join s.university u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，根据学生所在单位是否归省管进行判断
//				hql.append(" left join i.type sys where ((sys.code = '02' or sys.code = '03') and u.type = 3 or u.type = 4 ) and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据学生所在单位是否归校管进行判断
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号，根据学生是否在当前院系判断
//				hql.append(" where d.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 基地账号，根据学生是否在当前基地判断
//				hql.append(" where i.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" 1 = 1 ");
//			break;
//		}
//		map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
//		hql.append(" and LOWER(p.name) like :keyword");
//		hql.append(" order by p.name ");//默认按照姓名排序
//		
//		Map paraMap = new HashMap();
//		paraMap.put("map", map);
//		paraMap.put("hql", hql);
//		return paraMap;
//	}
//	
//	/**
//	 * 获取各类人员高级检索查询语句
//	 * @param loginer	 	  当前登录账号信息对象引用变量	
//	 * @param mobile     	 Moblie工具类对象引用变量：高级检索条件
//	 * @param personType	  人员类型：1:部级管理人员;2:省级管理人员;3:高校管理人员;4:院系管理人员;5.基地管理人员;6:外部专家;7:教师;8:学生;
//	 * @return paraMap		   查询语句hql ，参数map
//	 */
//	@SuppressWarnings("unchecked")
//	public Map getAdvSearchHql(LoginInfo loginer, Mobile mobile, Integer personType) {
//		String name = mobile.getName();
//		String gender = null;
//		if(!mobile.getGender().isEmpty()){
//			gender = mobile.getGender();
//		}
//		Integer startAge = null;
//		Integer endAge = null;
//		if(Pattern.matches("\\d+", mobile.getStartAge().trim())){
//			startAge = Integer.parseInt(mobile.getStartAge().trim());
//		}//判断输入是否数字，是则转换，否则为null
//		if(Pattern.matches("\\d+", mobile.getEndAge().trim())){
//			endAge = Integer.parseInt(mobile.getEndAge().trim());
//		}//判断输入是否数字，是则转换，否则为null
//		String unitName = mobile.getUnitName();
//		String deptName = mobile.getDeptName();
//		String position = mobile.getPosition();
//		String specialityTitle = mobile.getSpecialityTitle();
//		String disciplineType = mobile.getDisciplineType();
//		String staffCardNumber = mobile.getStaffCardNumber();
//		
//		StringBuffer hql = new StringBuffer();
//		Map map = new HashMap();
//		switch (personType) {
//		case 1://部级管理人员
//			hql.append("select p.name, ag.name, o.id, o.position, ag.sname, p.gender from Officer o left join o.person p left join o.agency ag ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR )) {// 系统管理员
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY) && loginer.getIsPrincipal() == 1) {// 部级账号
//				hql.append(" where ag.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" ag.type = 1 ");
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and LOWER(ag.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and LOWER(ag.sname) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and LOWER(o.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
//				staffCardNumber = staffCardNumber.toLowerCase();
//				hql.append(" and LOWER(o.staffCardNumber) like :staffCardNumber");
//				map.put("staffCardNumber", "%" + staffCardNumber + "%");
//			}
//			break;
//		case 2://省级管理人员
//			hql.append("select p.name, ag.name, o.id, o.position, ag.sname, p.gender from Officer o left join o.person p left join o.agency ag ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级账号
//				hql.append(" where ag.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" ag.type = 2 ");
//
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and  LOWER(ag.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and  LOWER(ag.sname) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and  LOWER(o.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
//				staffCardNumber = staffCardNumber.toLowerCase();
//				hql.append(" and  LOWER(o.staffCardNumber) like :staffCardNumber");
//				map.put("staffCardNumber", "%" + staffCardNumber + "%");
//			}
//			break;
//		case 3://高校管理人员
//			hql.append("select p.name, u.name, o.id, o.position, u.sname, p.gender from Officer o left join o.person p left join o.agency u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where (u.type = 3 or u.type = 4) and ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
//				hql.append(" where u.type = 4 and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if ((loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY ) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) && loginer.getIsPrincipal() == 1) {// 校级账号
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" o.department.id is null and o.institute.id is null ");
//
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and  LOWER(u.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and  LOWER(u.sname) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and  LOWER(o.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
//				staffCardNumber = staffCardNumber.toLowerCase();
//				hql.append(" and  LOWER(o.staffCardNumber) like :staffCardNumber");
//				map.put("staffCardNumber", "%" + staffCardNumber + "%");
//			}
//			break;
//		case 4://院系管理人员
//			hql.append("select p.name, u.name, o.id, o.position, d.name, p.gender from Officer o left join o.person p left join o.department d left join d.university u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
//				hql.append(" where u.type = 4 and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT) && loginer.getIsPrincipal() == 1) {// 院系账号
//				hql.append(" where d.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" d.id is not null ");
//
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and  LOWER(u.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and  LOWER(d.name) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and  LOWER(o.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
//				staffCardNumber = staffCardNumber.toLowerCase();
//				hql.append(" and  LOWER(o.staffCardNumber) like :staffCardNumber");
//				map.put("staffCardNumber", "%" + staffCardNumber + "%");
//			}
//			break;
//		case 5://基地管理人员
//			hql.append("select p.name, u.name, o.id, o.position, i.name, p.gender from Officer o left join o.person p left join o.institute i left join i.subjection u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
//				hql.append(" left join i.type sys where (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3) and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE) && loginer.getIsPrincipal() == 1) {// 基地账号
//				hql.append(" where i.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" i.id is not null ");
//
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and  LOWER(u.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and  LOWER(i.name) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and  LOWER(o.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
//				staffCardNumber = staffCardNumber.toLowerCase();
//				hql.append(" and  LOWER(o.staffCardNumber) like :staffCardNumber");
//				map.put("staffCardNumber", "%" + staffCardNumber + "%");
//			}
//			break;
//		case 6://外部专家
//			hql.append("select p.name, e.agencyName, e.id, ac.specialityTitle, e.divisionName, p.gender from Expert e left join e.person p left join p.academic ac ");
//			hql.append(" left join p.academic a ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
//				hql.append(" where 1=1 ");
//			} else {
//				hql.append(" where 1=0 ");
//			}
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and LOWER(e.agencyName) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and LOWER(e.divisionName) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and LOWER(e.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(specialityTitle != null && !specialityTitle.isEmpty()){
//				specialityTitle = specialityTitle.toLowerCase();
//				hql.append(" and LOWER(a.specialityTitle) like :specialityTitle");
//				map.put("specialityTitle", "%" + specialityTitle + "%");
//			}
//			if(disciplineType != null && !disciplineType.isEmpty()){
//				disciplineType = disciplineType.toLowerCase();
//				hql.append(" and LOWER(a.disciplineType) like :disciplineType");
//				map.put("disciplineType", "%" + disciplineType + "%");
//			}	
//			break;
//		case 7://教师
//			hql.append("select p.name, u.name, t.id, ac.specialityTitle, CONCAT(d.name, i.name), p.gender from Teacher t left join t.person p left join p.academic ac left join t.department d left join t.institute i left join t.university u ");
//			hql.append(" left join p.academic a ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，根据教师所在单位是否归省管进行判断
//				hql.append(" left join i.type sys where ((sys.code = '02' or sys.code = '03') and u.type = 3 or u.type = 4 ) and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据教师所在单位是否归校管进行判断
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号，根据教师是否在当前院系判断
//				hql.append(" where d.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 基地账号，根据教师是否在当前基地判断
//				hql.append(" where i.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" t.type = '专职人员' ");
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and  LOWER(u.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and LOWER(CONCAT(d.name, i.name)) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and  LOWER(t.position) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			if(specialityTitle != null && !specialityTitle.isEmpty()){
//				specialityTitle = specialityTitle.toLowerCase();
//				hql.append(" and  LOWER(a.specialityTitle) like :specialityTitle");
//				map.put("specialityTitle", "%" + specialityTitle + "%");
//			}
//			if(disciplineType != null && !disciplineType.isEmpty()){
//				disciplineType = disciplineType.toLowerCase();
//				hql.append(" and  LOWER(a.disciplineType) like :disciplineType");
//				map.put("disciplineType", "%" + disciplineType + "%");
//			}
//			break;
//		case 8://学生
//			hql.append("select p.name, u.name, s.id, s.type, CONCAT(d.name, i.name), p.gender from Student s left join s.person p left join s.department d left join s.institute i left join s.university u ");
//			if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
//				hql.append(" where ");
//			} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，根据学生所在单位是否归省管进行判断
//				hql.append(" left join i.type sys where ((sys.code = '02' or sys.code = '03') and u.type = 3 or u.type = 4 ) and u.subjection.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据学生所在单位是否归校管进行判断
//				hql.append(" where u.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号，根据学生是否在当前院系判断
//				hql.append(" where d.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 基地账号，根据学生是否在当前基地判断
//				hql.append(" where i.id = :unitId and ");
//				map.put("unitId", loginer.getCurrentBelongUnitId());
//			} else {
//				hql.append(" where 1 = 0 and ");
//			}
//			hql.append(" 1 = 1 ");
//			// 处理查询条件
//			if(unitName != null && !unitName.isEmpty())	{
//				unitName = unitName.toLowerCase();
//				hql.append(" and  LOWER(u.name) like :unitName");
//				map.put("unitName", "%" + unitName + "%");
//			}
//			if(deptName != null && !deptName.isEmpty())	{
//				deptName = deptName.toLowerCase();
//				hql.append(" and  LOWER(CONCAT(d.name, i.name)) like :deptName");
//				map.put("deptName", "%" + deptName + "%");
//			}
//			if(position != null && !position.isEmpty()){
//				position = position.toLowerCase();
//				hql.append(" and  LOWER(s.type) like :position");
//				map.put("position", "%" + position + "%");
//			}
//			break;
//		}
//		// 处理公共查询条件
//		if(name != null && !name.isEmpty())	{
//			name = name.toLowerCase();
//			hql.append(" and  LOWER(p.name) like :name");
//			map.put("name", "%" + name + "%");
//		}
//		if(gender != null && !gender.equals("--请选择--") && !gender.isEmpty()){
//			gender = gender.toLowerCase();
//			hql.append(" and  LOWER(p.gender) like :gender");
//			map.put("gender", "%" + gender + "%");
//		}
////		if(age != null){
////			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
////			Calendar cal = Calendar.getInstance();
////			Integer year = cal.get(Calendar.YEAR);//获得当前年
////			Integer year1 = year - age;
////			Integer year2 = year - age - 1;
////			Calendar date1 = Calendar.getInstance();
////			Calendar date2 = Calendar.getInstance();
////			date1.set(Calendar.YEAR, year1);
////			date2.set(Calendar.YEAR, year2);
////			String date1String = df.format(date1.getTime());
////			String date2String = df.format(date2.getTime());
////			hql.append(" and to_char(p.birthday, 'yyyy-MM-dd') <= :date1 and to_char(p.birthday, 'yyyy-MM-dd') >= :date2");
////			map.put("date1", date1String);
////			map.put("date2", date2String);
////		}
//		if(startAge != null && endAge != null){
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			Calendar cal = Calendar.getInstance();
//			Integer year = cal.get(Calendar.YEAR);//获得当前年
//			Integer year1 = year - startAge;
//			Integer year2 = year - endAge - 1;
//			Calendar date1 = Calendar.getInstance();
//			Calendar date2 = Calendar.getInstance();
//			date1.set(Calendar.YEAR, year1);
//			date2.set(Calendar.YEAR, year2);
//			String date1String = df.format(date1.getTime());
//			String date2String = df.format(date2.getTime());
//			hql.append(" and to_char(p.birthday, 'yyyy-MM-dd') <= :date1 and to_char(p.birthday, 'yyyy-MM-dd') >= :date2");
//			map.put("date1", date1String);
//			map.put("date2", date2String);
//		}
//		hql.append(" order by p.name ");//默认按照姓名排序
//		
//		Map paraMap = new HashMap();
//		paraMap.put("map", map);
//		paraMap.put("hql", hql);
//		return paraMap;
//	}
	
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
