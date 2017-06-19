package csdc.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import csdc.bean.Account;
import csdc.bean.Business;
import csdc.bean.SystemOption;
import csdc.service.IBusinessService;
import csdc.tool.bean.AccountType;

/**
 * 业务管理接口实现
 * @author 肖雅
 * @version 2011.12.29
 */
public class BusinessService extends BaseService implements IBusinessService {
	
	
	/**
	 * 判断业务类型是否存在
	 * @param businessType 业务名称
	 * @param businessId 业务Id
	 * @return true不存在，false存在
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean checkBusinessType(String businessSubType, String businessId, int startYear, int endYear ) {
		if (businessSubType == null || businessSubType.equals("-1")) {// 业务类型为空，则视为已存在
			return false;
		} else {// 业务类型非空，则查询数据库进行判断
			Map map = new HashMap();
			String hql1, hql2;
			String code = "";
			hql1 = "select so.code from SystemOption so where so.id = ?";
			List<String> l = dao.query(hql1, businessSubType);
			if(l.size() > 0){//获取业务类型的code
				code = l.get(0);
			}
			if(businessId == null || businessId.equals("")){//添加判断
				map.put("businessSubType", businessSubType);
				map.put("startYear", startYear);
				map.put("endYear", endYear);
				map.put("code", code);
				if(code.substring(2).equals("1") || code.substring(2).equals("5") || code.substring(2).equals("6")){//若为申请或者年检或年度选题业务且存在同类激活的业务，则不能添加
					hql2 = "select b.id from Business b left outer join b.subType so where b.subType.code=:code";
				}else{//其他业务若存在同类业务且对象起止年份一致，则不能添加
					hql2 = "select b.id from Business b left outer join b.subType so where b.subType.id=:businessSubType and b.startYear=:startYear and b.endYear=:endYear";
				}
			}else{//修改判断
				map.put("businessSubType", businessSubType);
				map.put("businessId", businessId);
				map.put("startYear", startYear);
				map.put("endYear", endYear);
				map.put("code", code);
				if(code.substring(2).equals("1") || code.substring(2).equals("5") || code.substring(2).equals("6")){//若为申请或者年检业务且存在同类激活的业务，则不能添加
					hql2 = "select b.id from Business b left outer join b.subType so where b.subType.code=:code and b.id!=:businessId";
				}else{//其他业务若存在同类业务且对象起止年份一致，则不能添加
					hql2 = "select b.id from Business b where b.subType.id=:businessSubType and b.startYear=:startYear and b.endYear=:endYear and b.id!=:businessId";
				}
			}
			List<String> list = dao.query(hql2, map);
			return list.isEmpty() ? true : false;
		}
	}
	
	/**
	 * 判断业务对象起止年份是否相等
	 * @param businessType 业务子类id
	 * @param businessId 业务Id
	 * @return true相等，false不相等
	 */
	@SuppressWarnings("unchecked")
	public boolean isBusinessYearEqual(String businessSubType, int startYear, int endYear ) {
		if (businessSubType == null || businessSubType.equals("-1") || businessSubType.isEmpty()) {// 业务子类为空，则视为不相等
			return false;
		} else {// 业务子类非空，则查询数据库进行判断
			String hql = "select so.code from SystemOption so where so.id = ?";
			List<String> list = dao.query(hql, businessSubType);
			if(list.size() > 0){
				String code = list.get(0).substring(2);
				if((code.equals("1") || code.equals("6"))&& (startYear != endYear)) return false; 
			}
		}
		return true;
	}
	
	/**
	 * 判断业务对象起止年份是否交叉
	 * @param businessType 业务子类id
	 * @param businessId 业务Id
	 * @return true不交叉，false交叉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean isBusinessYearCrossed(String businessSubType, String businessId, int startYear, int endYear ) {
		if (businessSubType == null || businessSubType.equals("-1")) {// 业务类型为空，则视为交叉
			return false;
		} else {// 业务类型非空，则查询数据库进行判断
			Map map = new HashMap();
			String hql;
			if(businessId == null || businessId.equals("")){//添加判断
				map.put("businessSubType", businessSubType);
				hql = "select b.startYear, b.endYear from Business b where b.subType.id =:businessSubType order by b.startYear";
				List list = dao.query(hql, map);
				if(list.size() > 0){
					Object[] a = (Object[]) list.get(0);
					if(endYear < (Integer)a[0])
						return true;
					Object[] b = (Object[]) list.get(list.size() - 1);
					if(startYear > (Integer)b[1])
						return true;
					for (int i = 1; i < list.size(); i++){
						Object[] c = (Object[]) list.get(i);
						if(startYear > (Integer)((Object[])list.get(i - 1))[1] && endYear < (Integer)c[0])
							return true;
					}
				}else{
					return true;
				}
			}else{
				map.put("businessSubType", businessSubType);
				map.put("businessId", businessId);
				hql = "select b.startYear, b.endYear from Business b where b.subType.id=:businessSubType and b.id !=:businessId";
				List list = dao.query(hql, map);
				if(list.size() > 0){
					Object[] a = (Object[]) list.get(0);
					if(endYear < (Integer)a[0])
						return true;
					Object[] b = (Object[]) list.get(list.size() - 1);
					if(startYear > (Integer)b[1])
						return true;
					for (int i = 1; i < list.size(); i++){
						Object[] c = (Object[]) list.get(i);
						if(startYear > (Integer)((Object[])list.get(i - 1))[1] && endYear < (Integer)c[0])
							return true;
					}
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 修改业务
	 * @param oldRight原始业务对象
	 * @param newRight更新业务对象
	 * @return 业务ID
	 */
	public String modifyBusiness(Business oldBusiness, Business newBusiness) {
		// 更新业务属性
		if(newBusiness.getSubType().getId() != null && !"-1".equals(newBusiness.getSubType().getId())){
			SystemOption subtype = (SystemOption) dao.query(SystemOption.class, newBusiness.getSubType().getId());
			oldBusiness.setSubType(subtype);
		}else {
			oldBusiness.setSubType(null);
		}
		oldBusiness.setType(newBusiness.getType());
		oldBusiness.setStatus(newBusiness.getStatus());
		oldBusiness.setStartYear(newBusiness.getStartYear());
		oldBusiness.setEndYear(newBusiness.getEndYear());
		oldBusiness.setBusinessYear(newBusiness.getBusinessYear());
		oldBusiness.setStartDate(newBusiness.getStartDate());
		oldBusiness.setApplicantDeadline(newBusiness.getApplicantDeadline());
		oldBusiness.setDeptInstDeadline(newBusiness.getDeptInstDeadline());
		oldBusiness.setUnivDeadline(newBusiness.getUnivDeadline());
		oldBusiness.setProvDeadline(newBusiness.getProvDeadline());
		oldBusiness.setReviewDeadline(newBusiness.getReviewDeadline());
		dao.modify(oldBusiness);// 修改权限数据
		return oldBusiness.getId();
	}
	
	/**
	 * 查询当年年份形成年份列表，起始年份为2000
	 * @return 年份列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> getYearMap(){
		Map<Integer, Integer> yearMap = new LinkedHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int y = Integer.parseInt(year);
		for(int i=y-10; i<y; i++){
			yearMap.put(i, i);
		}
		yearMap.put(y, y);
		return yearMap;
	}
	
	/**
	 * 根据账号ID获得登录首页中，用于显示的业务日程列表
	 * @param accountId
	 * @return 当前账号涉及业务，包括业务名称，状态，起始时间，业务对象起止时间；
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getBusinessByAccount(String accountId){
		Account account = (Account) dao.query(Account.class, accountId);
		AccountType accountType;//账号类型
		if(account == null){// 账号不存在
			return null;
		}else{
			accountType = account.getType();
		}
		Map map = new HashMap();//参数map
		Date date = new Date();// 获取系统当前时间
		Date date1 = new Date(date.getTime() - 24*60*60*1000);//将系统时间减一天
		map.clear();
		map.put("date", date1);
		StringBuffer str = new StringBuffer("select so.name, b.applicantDeadline, b.deptInstDeadline, b.univDeadline, b.provDeadline, b.reviewDeadline, b.startYear, b.endYear, b.status");
		StringBuffer addHql = new StringBuffer("  from Business b left outer join b.subType so where b.status =1 and b.startDate <= :date");//附加查询条件
//			if(accountType.equals(AccountType.MINISTRY)){
//				addHql.append(" and (b.provDeadline>=:date or b.provDeadline is null) order by b.provDeadline asc");
		if(accountType.equals(AccountType.PROVINCE)){
			addHql.append(" and (b.provDeadline>=:date or b.provDeadline is null) order by b.provDeadline asc");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			addHql.append(" and (b.univDeadline>=:date or b.univDeadline is null) order by b.univDeadline asc");
		}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			addHql.append(" and (b.deptInstDeadline>=:date or b.deptInstDeadline is null) order by b.deptInstDeadline asc");
		}else if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){
			addHql.append(" and (b.applicantDeadline>=:date or b.applicantDeadline is null) order by b.applicantDeadline asc");
		}
		str.append(addHql);
		String hql = str.toString();
		List business = dao.query(hql, map);
		if(business.size() > 5){
			for(int i = 5 ; i < business.size(); i++){
				business.set(i, null);
			}
		}
		return business;
	}
	
}