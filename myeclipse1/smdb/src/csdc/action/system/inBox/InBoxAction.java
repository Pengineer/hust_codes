package csdc.action.system.inBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Friend;
import csdc.bean.InBox;
import csdc.bean.InBoxAccount;
import csdc.bean.Person;
import csdc.service.IInBoxService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.InBoxInfo;

/**
 * 站内信管理模块
 * @author yangfq
 *
 */
public class InBoxAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select ib.id, ib.theme, ib.sendName, ib.recName, ib.status, ib.pDate, ib.sendType";
	private static final String GROUP_BY = "group by ib.id, ib.sendId, ib.recId, ib.status, ib.pDate";
	private static final String PAGE_NAME = "inBoxPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "ib.id";// 上下条查看时用于查找缓存的字段
	private String accountType;//帐号类型
	private Integer sendType;//发信方式 0：广播go；1：单播
	private IInBoxService inBoxService;//站内信管理接口
	private Integer status;//站内信查看状态0:未读 1:已读

	private int keyword1;//收件箱发件箱的选择
	private List<String> recName;//广播收件人的集合
	private String recNames;//单播或多播收件人的姓名集合
	protected InBox inBox;//站内信对象
	protected InBoxAccount inBoxAccount;//站内信记录对象
	private String theme;//主题
	private String message;//站内信内容
	private Account account;
	private List<String> entityIds;//单播或多播选中的人员或机构的实体ID集合
	protected String content;
	private String nameSpace; 

	private static final String[] COLUMN = {
		"ib.sendId",
		"ib.recId",
		"ib.status",
		"ib.pDate",
		"ib.sendType"
	};// 用于拼接的排序列	

	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String dateFormat() {
		return InBoxAction.DATE_FORMAT;
	}
	public String HQL() {
		return HQL;
	}
	@Override
	public String pageBufferId() {
		return InBoxAction.PAGE_BUFFER_ID;
	}

	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL());
		if(loginer.getAccount().getType().equals(AccountType.ADMINISTRATOR)){//系统管理员可以查看所有的站内信
			if(keyword1 == 2 || keyword1 == 0){
				hql.append(" , ib.viewStatus from InBox ib ");
			} else if(keyword1 == 0){//收件箱
				Account recId = loginer.getAccount();//当前登陆者的ID等于接受者的ID
				map.put("recId", recId);
				hql.append(", iba.status, iba.readTime from InBox ib,InBoxAccount iba where  ib.id = iba.inBoxId.id	 and iba.recId = :recId ");
			}else if (keyword1 == 1){//发件箱
				Account sendId = loginer.getAccount();//当前登陆者的ID等于发送者的ID
				map.put("sendId", sendId);
				hql.append(" from InBox ib where ib.sendId = :sendId ");
			}
		} else {
			if(keyword1 == 0){//收件箱
				Account recId = loginer.getAccount();//当前登陆者的ID等于接受者的ID
				map.put("recId", recId);
				hql.append(", iba.status, iba.readTime from InBox ib,InBoxAccount iba where  ib.id = iba.inBoxId.id	 and iba.recId = :recId ");
			} else if(keyword1 == 1){//发件箱
				Account sendId = loginer.getAccount();//当前登陆者的ID等于发送者的ID
				map.put("sendId", sendId);
				hql.append(" from InBox ib where ib.sendId = :sendId ");
			}
		}
		if (!keyword.isEmpty()) {
			if (searchType == 1) {// 按主题搜索
				hql.append("and LOWER(ib.theme) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {// 按发信者搜索
				hql.append("and LOWER(ib.sendName) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else {// 按上述字段检索
				hql.append("and (LOWER(ib.theme) like :keyword or LOWER(ib.sendName) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		hql.append(" order by iba.status asc ");
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	/**
	 * 站内信提醒功能
	 */
//	public String remind(){
//		Map map = new HashMap();
//		map.put("currentLoginerId", currentLoginerId);
//		List<String> unReadInBox = dao.query("select iba.id from InBoxAccount iba where iba.recId.id =:currentLoginerId and iba.status = 0", map);
//		int unReadNumber = unReadInBox.size();
//		jsonMap.put("unReadNumber", unReadNumber);
//		return SUCCESS;
//	}
	
	/**
	 * 添加人员或机构至站内信收件人时，验证是否存在帐号
	 */
	public String checkAccountId(){
		Map map = new HashMap();
		Map session = ActionContext.getContext().getSession();
		session.put("entityIds", entityIds);
		session.put("nameSpace", nameSpace);
		int flag = 0;//有账号的人员或机构的数目
		recNames = "";
		for (String entityId : entityIds) {//遍历所选中的人员或机构的ID
			map.put("entityId", entityId);
			if(nameSpace.split("/")[1].equals("ministryOfficer") || nameSpace.split("/")[1].equals("provinceOfficer") || nameSpace.split("/")[1].equals("universityOfficer") || nameSpace.split("/")[1].equals("departmentOfficer") || nameSpace.split("/")[1].equals("instituteOfficer")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Officer o where ac.officer.id = o.id and o.id = ?", entityId);
				if(accountIds.size() > 0){
					++flag;
				}
			} else if (nameSpace.split("/")[1].equals("expert")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Expert ept where ac.person.id = ept.person.id and ept.id = :entityId", map);
				if(accountIds.size() > 0){
					++flag;
				}
			} else if (nameSpace.split("/")[1].equals("teacher")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Teacher tc where ac.person.id = tc.person.id and tc.id = :entityId", map);
				if(accountIds.size() > 0){
					++flag;
				}
			} else if (nameSpace.split("/")[1].equals("student")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Student st where ac.person.id = st.person.id and st.id = :entityId", map);
				if(accountIds.size() > 0){
					++flag;
				}
			} else if (nameSpace.split("/")[1].equals("ministry") || nameSpace.split("/")[1].equals("province") || nameSpace.split("/")[1].equals("university")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Agency ag where ac.agency.id = ag.id and ag.id = :entityId", map);
				if(accountIds.size() > 0){
					++flag;
				}
			} else if (nameSpace.split("/")[1].equals("department")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Department dpm where ac.department.id = dpm.id and dpm.id = :entityId", map);
				if(accountIds.size() > 0){
					++flag;
				}
			} else if (nameSpace.split("/")[1].equals("institute")){
				List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Institute ist where ac.institute.id = ist.id and ist.id = :entityId", map);
				if(accountIds.size() > 0){
					++flag;
				}
			}
		}
		if(flag > 0 && flag < entityIds.size()){
			int fail = entityIds.size() - flag;
			jsonMap.put(GlobalInfo.ERROR_INFO, "因无帐号，有" + fail + "条发送失败。\n是否确定发送？");	
		}else if(flag == 0 ){
			jsonMap.put(GlobalInfo.ERROR_INFO, "因无帐号，无法发送！");
		}
		return SUCCESS;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String toAdd() {
		Map map = new HashMap();
		Map session = ActionContext.getContext().getSession();
		session.put("entityIds", entityIds);
		session.put("nameSpace", nameSpace);
		session.put("sendType", sendType);
		int flag = 0;
		recNames = "";
		if(entityIds == null){
			return SUCCESS;
		} else {
			for (String entityId : entityIds) {
				map.put("entityId", entityId);
				if(nameSpace.split("/")[1].equals("ministryOfficer") || nameSpace.split("/")[1].equals("provinceOfficer") || nameSpace.split("/")[1].equals("universityOfficer") || nameSpace.split("/")[1].equals("departmentOfficer") || nameSpace.split("/")[1].equals("instituteOfficer")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Officer o where ac.officer.id = o.id and o.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select p.name from  Officer o, Person p  where o.person.id = p.id and o.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					} 
				} else if (nameSpace.split("/")[1].equals("expert")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Expert ept where ac.person.id = ept.person.id and ept.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select p.name from Expert ept, Person p  where ept.person.id = p.id and ept.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					}
				} else if (nameSpace.split("/")[1].equals("teacher")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Teacher tc where ac.person.id = tc.person.id and tc.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select p.name from Teacher tc, Person p  where tc.person.id = p.id and tc.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					}
				} else if (nameSpace.split("/")[1].equals("student")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Student st where ac.person.id = st.person.id and st.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select p.name from Student st, Person p  where st.person.id = p.id and st.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					}
				} else if (nameSpace.split("/")[1].equals("ministry") || nameSpace.split("/")[1].equals("province") || nameSpace.split("/")[1].equals("university")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Agency ag where ac.agency.id = ag.id and ag.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select ag.name from Agency ag where ag.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					}
				} else if (nameSpace.split("/")[1].equals("department")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Department dpm where ac.department.id = dpm.id and dpm.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select dpm.name from Department dpm where dpm.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					}
				} else if (nameSpace.split("/")[1].equals("institute")){
					List<String> accountIds = dao.query("select distinct(ac.id) from Account ac, Institute ist where ac.institute.id = ist.id and ist.id = :entityId", map);
					if(accountIds.size() > 0){
						List<String> name = dao.query("select ist.name from Institute ist where ist.id = :entityId", map);
						String nameStr = name.get(0).toString();
						recNames += nameStr + ";";
					}
				}		
			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String add() {
		InBox inBox = new InBox();
		InBoxAccount inBoxAccount = new InBoxAccount();
		Map map = new HashMap();
		Map session = ActionContext.getContext().getSession();
		entityIds = (List<String>) session.get("entityIds");
		nameSpace =  (String) session.get("nameSpace");
		if (sendType == 1) {//单播或多播
			inBox.setSendType(1);			
			for (String entityId : entityIds){
				map.put("entityId", entityId);
				if(nameSpace.split("/")[1].equals("ministryOfficer") || nameSpace.split("/")[1].equals("provinceOfficer") || nameSpace.split("/")[1].equals("universityOfficer") || nameSpace.split("/")[1].equals("departmentOfficer") || nameSpace.split("/")[1].equals("instituteOfficer")){
					List<Account> accountList = dao.query("select ac from Account ac, Officer o where ac.officer.id = o.id and o.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);					
						inBoxAccount.setRecId(account);	
					}
					List<String> name = dao.query("select p.name from  Officer o, Person p  where o.person.id = p.id and o.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
				} else if (nameSpace.split("/")[1].equals("expert")){
					List<Account> accountList = dao.query("select ac from Account ac, Expert ept where ac.person.id = ept.person.id and ept.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);
						inBoxAccount.setRecId(account);
					}
					List<String> name = dao.query("select p.name from Expert ept, Person p  where ept.person.id = p.id and ept.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
					inBox.setIsPrincipal(1);
					List<String> type = dao.query("select ac.type from Account ac, Expert ept where ac.person.id = ept.person.id and ept.id = :entityId", map);
					map.put("type", type.get(0));
					inBox.setAccountType((AccountType) map.get("type"));
				} else if (nameSpace.split("/")[1].equals("teacher")){
					List<Account> accountList = dao.query("select ac from Account ac, Teacher tc where ac.person.id = tc.person.id and tc.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);
						inBoxAccount.setRecId(account);
					}
					List<String> name = dao.query("select p.name from Teacher tc, Person p  where tc.person.id = p.id and tc.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
					inBox.setIsPrincipal(1);
					List<String> type = dao.query("select ac.type from Account ac, Teacher tc where ac.person.id = tc.person.id and tc.id = :entityId", map);
					map.put("type", type.get(0));
					inBox.setAccountType((AccountType) map.get("type"));
				} else if (nameSpace.split("/")[1].equals("student")){
					List<Account> accountList = dao.query("select ac from Account ac, Student st where ac.person.id = st.person.id and st.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);
						inBoxAccount.setRecId(account);
					}
					List<String> name = dao.query("select p.name from Student st, Person p  where st.person.id = p.id and st.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
					inBox.setIsPrincipal(1);
					List<String> type = dao.query("select ac.type from Account ac, Student st where ac.person.id = st.person.id and st.id = :entityId", map);
					map.put("type", type.get(0));
					inBox.setAccountType((AccountType) map.get("type"));
				} else if (nameSpace.split("/")[1].equals("ministry") || nameSpace.split("/")[1].equals("province") || nameSpace.split("/")[1].equals("university")){
					List<Account> accountList = dao.query("select ac from Account ac, Agency ag where ac.agency.id = ag.id and ag.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);
						inBoxAccount.setRecId(account);
					}
					List<String> name = dao.query("select ag.name from Agency ag where ag.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
				} else if (nameSpace.split("/")[1].equals("department")){
					List<Account> accountList = dao.query("select ac from Account ac, Department dpm where ac.department.id = dpm.id and dpm.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);
						inBoxAccount.setRecId(account);
					}
					List<String> name = dao.query("select dpm.name from Department dpm where dpm.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
					inBox.setIsPrincipal(1);
					List<String> type = dao.query("select ac.type from Account ac, Department dpm where ac.department.id = dpm.id and dpm.id = :entityId", map);
					map.put("type", type.get(0));
					inBox.setAccountType((AccountType) map.get("type"));
				} else if (nameSpace.split("/")[1].equals("institute")){
					List<Account> accountList = dao.query("select ac from Account ac, Institute ist where ac.institute.id = ist.id and ist.id = :entityId", map);
					for (Account account : accountList){
						inBox.setRecId(account);
						inBoxAccount.setRecId(account);
					}
					List<String> name = dao.query("select ist.name from Institute ist where ist.id = :entityId", map);
					inBox.setRecName(name.get(0).toString());
					inBox.setIsPrincipal(1);
					List<String> type = dao.query("select ac.type from Account ac, Institute ist where ac.institute.id = ist.id and ist.id = :entityId", map);
					map.put("type", type.get(0));
					inBox.setAccountType((AccountType) map.get("type"));
				}
				String accountBelong = "";
				if (loginer.getCurrentBelongUnitName() != null) {
					accountBelong = loginer.getCurrentBelongUnitName();
				}
				if (loginer.getCurrentPersonName() != null) {
					accountBelong = loginer.getCurrentPersonName();
				}
				inBox.setSendName(accountBelong);//记录发送者姓名
				inBox.setSendId(loginer.getAccount());//记录发送者账号信息
				inBox.setTheme(theme);//记录主题
				inBox.setMessage(message);//记录站内信内容
				inBox.setStatus(1);//记录站内信发送状态
				inBox.setpDate(new Date());//记录站内信发送时间
				inBoxAccount.setStatus(0);//站内信记录的查看状态
				String inBoxIdStr = dao.add(inBox);
				map.put("inBoxIdStr", inBoxIdStr);
				List<InBox> inBoxList =dao.query("select ib from InBox ib where ib.id = :inBoxIdStr", map);
				for(InBox inBoxId:inBoxList){
					inBoxAccount.setInBoxId(inBoxId);
				}
				dao.add(inBoxAccount);
			}	
		} else if (sendType == 2) {//广播
			inBox.setSendType(2);
			for(String name:recName){
				if(name.equals("ministry") || name.equals("ministryOfficer") || name.equals("province") || name.equals("provinceOfficer") || name.equals("ministryUniversity") || name.equals("ministryUniversityOfficer") || name.equals("localUniversity") || name.equals("localUniversityOfficer")
						|| name.equals("department") || name.equals("departmentOfficer") || name.equals("institute") || name.equals("instituteOfficer") || name.equals("expert") || name.equals("teacher") || name.equals("student")){
					map = inBoxService.QueryNameForType(name);
					inBox.setAccountType((AccountType) map.get("type"));//收信者的账号类型
					inBox.setIsPrincipal((Integer) map.get("isPrincipal"));//是否是主账号
					String accountBelong = "";
					if (loginer.getCurrentBelongUnitName() != null) {
						accountBelong = loginer.getCurrentBelongUnitName();
					}
					if (loginer.getCurrentPersonName() != null) {
						accountBelong = loginer.getCurrentPersonName();
					}
					inBox.setSendName(accountBelong);//记录发送者姓名
					inBox.setSendId(loginer.getAccount());//记录发送者账号信息
					inBox.setTheme(theme);//记录主题
					inBox.setMessage(message);//记录站内信内容
					inBox.setStatus(1);//记录站内信发送状态
					inBox.setpDate(new Date());//记录站内信发送时间
					String inBoxIdStr = dao.add(inBox);
					map.put("inBoxIdStr", inBoxIdStr);
					List<InBox> inBoxList =dao.query("select ib from InBox ib where ib.id = :inBoxIdStr", map);
					AccountType accountType = (AccountType) map.get("type");
					int isPrincipal = (Integer) map.get("isPrincipal");
					List<Account> accounts = dao.query("select a from Account a where a.type = '" + accountType +"' and a.isPrincipal = ? ", isPrincipal);
					for(Account account: accounts){
						inBoxAccount.setRecId(account);		
						inBoxAccount.setStatus(0);//站内信记录的查看状态
						for(InBox inBoxId:inBoxList){
						inBoxAccount.setInBoxId(inBoxId);
						}
						dao.add(inBoxAccount);
					}				
				}
			}			
		} else if (sendType == 4) {//好友之间的站内信
			//处理登陆者的信息
			LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
			Person logPerson = dao.query(Person.class, loginer.getPerson().getId());//当前登陆者
			inBox.setPerson(loginer.getPerson().getId());//当前登陆者;发送者
			inBox.setSendId(loginer.getAccount());
			inBox.setSendName(logPerson.getName());
			//处理接受者的信息
			Friend friend = dao.query(Friend.class, entityId);
			Person person = dao.query(Person.class, friend.getFriend().getId());
			inBox.setFriend(person.getId());//接受者
			Account recAccount = (Account) dao.query("from Account ac where ac.person.id =?",person.getId()).get(0);
			inBox.setRecId(recAccount);
			inBox.setRecName(person.getName());
			inBox.setMessage(content);
			inBox.setpDate(new Date());
			inBox.setStatus(1);
			inBox.setViewStatus(0);
			inBox.setSendType(4);
			inBox.setTheme("好友交流");
			inBox.setSendType(4);
			dao.add(inBox);
			return "friend";
		}
		return SUCCESS;
	}
	
	public String delete(){
		Map map = new HashMap();
		for(String entityId: entityIds){
			map.put("entityId", entityId);
			List<String> sendId = dao.query("select ib.sendId.id from InBox ib where ib.id =:entityId", map);
			map.put("sendId", sendId.get(0));
			String sendIdStr = sendId.get(0);
			String loginerId = loginer.getAccount().getId();
			map.put("loginerId", loginerId);
			if(loginer.getAccount().getType().equals(AccountType.ADMINISTRATOR)){
				dao.delete(InBox.class, entityId);
			} else if(loginerId.equals(sendId.get(0))){
				dao.delete(InBox.class, entityId);
			} else {
				try {
					List<String> inBoxAccountId = dao.query("select distinct iba.id from InBox ib, InBoxAccount iba  where ib.id = iba.inBoxId.id and ib.id ='" + entityId +"' and iba.recId ='" + loginerId +"' and  ib.sendId ='" + sendIdStr +"'");
					dao.delete(InBoxAccount.class, inBoxAccountId.get(0));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		return SUCCESS;
	}
	/**
	 * 进入查看页面
	 * 
	 * @return
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 进入查看页面校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// 查看邮件ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, InBoxInfo.ERROR_VIEW_NULL);
		}
	}


	/**
	 * 查看站内信
	 * 
	 * @throws IOException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String view(){
		 Map map = new HashMap();
		 inBox = dao.query(InBox.class, entityId);		
		 String accountBelong = "";
		 if(loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)){
			 jsonMap.put("inBox", inBox);
		 } else if ((loginer.getCurrentBelongUnitName() != null && loginer.getCurrentBelongUnitName().contains(inBox.getSendName())) ||
				(loginer.getCurrentPersonName() != null && loginer.getCurrentPersonName().contains(inBox.getSendName()))) {
			 	jsonMap.put("inBox", inBox);
		 	} else{
			map.put("entityId", entityId);
			String accountStr = loginer.getAccount().getId();
			map.put("accountStr",accountStr);
			List<String> inBoxAccountList = dao.query("select iba.id from InBoxAccount iba where iba.inBoxId = '" + entityId +"' and iba.recId = '" + accountStr +"'");
			String inBoxAccountStr = inBoxAccountList.get(0).toString();
		    map.put("inBoxAccountStr", inBoxAccountStr);
			inBoxAccount = dao.query(InBoxAccount.class,inBoxAccountStr);				
			inBoxAccount.setStatus(1);
			inBoxAccount.setReadTime(new Date());//站内信记录的查看时间
			dao.modify(inBoxAccount);
			jsonMap.put("inBox", inBox); 
		 }		 
		 return SUCCESS;
	 }

	/**
	 * 判重地址列表中的重复项
	 */
	@SuppressWarnings("unused")
	private String getDistinctAddresses(String address){
		if (address == null){
			address = "";
		}
		Set<String> result = new TreeSet<String>();
		result.add(address);
		return new ArrayList<String>(result).toString();
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	public static String getGroupBy() {
		return GROUP_BY;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setInbox(InBox inbox) {
		this.inBox = inbox;
	}
	
	public IInBoxService getInBoxService() {
		return inBoxService;
	}

	public void setInBoxService(IInBoxService inBoxService) {
		this.inBoxService = inBoxService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InBox getInBox() {
		return inBox;
	}

	public void setInBox(InBox inBox) {
		this.inBox = inBox;
	}

	public String getTheme() {
		return theme;
	}

	public String getRecNames() {
		return recNames;
	}

	public void setRecNames(String recNames) {
		this.recNames = recNames;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	public List<String> getEntityIds() {
		return entityIds;
	}

	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}
	
	public String getNameSpace() {
		return nameSpace;
		
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(int keyword1) {
		this.keyword1 = keyword1;
	}

	public List<String> getRecName() {
		return recName;
	}

	public void setRecName(List<String> recName) {
		this.recName = recName;
	}

	public InBoxAccount getInBoxAccount() {
		return inBoxAccount;
	}

	public void setInBoxAccount(InBoxAccount inBoxAccount) {
		this.inBoxAccount = inBoxAccount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
