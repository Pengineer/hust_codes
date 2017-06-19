package csdc.action.selfspace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Memo;
import csdc.bean.Passport;
import csdc.service.IMemoService;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MemoInfo;

public class MemoAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private Memo memo;
	private Date datetime1, datetime2;//高级检索
//	private Date reverseDate;//倒计时提醒日期
	private int modifyFlag;
//	private int hours;//倒计时提醒小时
//	private int minutes;//倒计时提醒分钟
	private IMemoService memoService;
//	private static final String HQL = "select m.id, a.id, m.title, m.content, m.updateTime, m.remindTime, m.isRemind, m.remindWay, m.week, m.month, " + 
//    "m.startDateDay, m.endDateDay, m.startDateWeek, m.endDateWeek, m.reverseRemindTime, m.remindDay from " +
//	"Memo m left outer join m.account a where 1=1 ";
	private static final String HQL = "select m.id, a.id, m.title, m.content, m.updateTime, m.remindTime, m.isRemind, m.remindWay, m.week, m.month, " + 
    "m.startDateDay, m.endDateDay, m.startDateWeek, m.endDateWeek from " +
	"Memo m left outer join m.account a where 1=1 ";
	private static final String pageName = "MemoPage";
	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	private static final String pageBufferId = "m.id";
	private static final String[] column = new String[]{
		"m.title",
		"m.updateTime",
		"m.isRemind",
		"m.remindWay",
	};
	protected int type;
//	private int flags;
	
//	/**
//	 * 获取倒计时提醒条件，以触发弹出层
//	 * @param flag
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public String flag(int flag){
//		this.flags = flag;
//		jsonMap.put("reverseFlag", this.flags);
//		return SUCCESS;
//	}
	
	
	
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if(!keyword.isEmpty()){
			hql.append(" and ");
			if (searchType == 1) {// 按标题检索
				hql.append(" LOWER(m.title) like :keyword ");
			}
			else if(searchType == 2){// 按是否提醒检索
				if(keyword.contains("是")){keyword = "1";}
				if(keyword.contains("否")){keyword = "0";}
				hql.append(" TO_CHAR(m.isRemind) like :keyword ");
			}
			else if(searchType == 3){// 按提醒方式检索
				if(keyword.contains("日期")){keyword = "1";}
				if(keyword.contains("天")){keyword = "2";}
				if(keyword.contains("周")){keyword = "3";}
				if(keyword.contains("月")){keyword = "4";}
//			if(keyword.contains("倒计时")){keyword = "5";}
//			if(keyword.contains("阴历")){keyword = "6";}
				hql.append(" TO_CHAR(m.remindWay) like :keyword ");
			}
			else{
				hql.append("(LOWER(m.title) like :keyword or TO_CHAR(m.isRemind) like :keyword or " +
				" TO_CHAR(m.remindWay) like :keyword ) ");
			}
			map.put("keyword", "%" + keyword + "%");
		}
		System.out.println(hql.toString());
		System.out.println(map);
		
		return new Object[]{
				hql.toString(),
				map,
				0,
				null
			};
	}
	
	/**
	 * 进入高级检索页面
	 */
	public String toAdvSearch() {
		return SUCCESS;
	}

	/**
	 * 高级检索
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (null != memo.getTitle() && !memo.getTitle().trim().isEmpty()){
			map.put("title", "%"+memo.getTitle().toLowerCase().trim()+"%");
			hql.append(" and LOWER(m.title) like :title ");
		}
		if (datetime1 != null) {
			hql.append(" and m.updateTime > :datetime1 ");
			map.put("datetime1", datetime1);
		}
		if (datetime2 != null) {
			hql.append(" and m.updateTime < :datetime2 ");
			map.put("datetime2",datetime2);
		}
		if (memo.getIsRemind() == 0 || memo.getIsRemind() == 1) {
			hql.append(" and m.isRemind = :isRemind");
			map.put("isRemind", memo.getIsRemind());
		}
//		if (memo.getRemindWay() == 1 || memo.getRemindWay() == 2 || memo.getRemindWay() == 3 || memo.getRemindWay() == 4 || memo.getRemindWay() == 5 || memo.getRemindWay() == 6){
	    if (memo.getRemindWay() == 1 || memo.getRemindWay() == 2 || memo.getRemindWay() == 3 || memo.getRemindWay() == 4 ){
			hql.append(" and m.remindWay = :remindWay");
			map.put("remindWay", memo.getRemindWay());
		}
		return new Object[]{
				hql.toString(),
				map,
				0,
				null
			};
	}
	
	/**
	 * 高级检索校验
	 */
	public void validateAdvSearch() {
		if(datetime1 != null && datetime2 != null) {
			if (datetime1.compareTo(datetime2) >= 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_ILLEGAL);
			}
		}
	}
	/**
	 * 转到备忘录添加
	 */
	public String toAdd() {
		setMemo(new Memo());
		return SUCCESS;
	}
	
	/**
	 * 备忘录添加
	 */
	@SuppressWarnings({ "unchecked" })
	public String add(){
		memo.setUpdateTime(new Date());
		if(memo.getIsRemind() == 0){
			memo.setRemindWay(0);
		}else{
			switch(memo.getRemindWay()){//根据提醒方式修改开始提醒时间
				case 1://按指定日期
					memo.setRemindWay(memo.getRemindWay());
					memo.setRemindTime(memo.getRemindTime());
					break;
				case 2://按天
					memo.setRemindWay(memo.getRemindWay());
					memo.setStartDateDay(memo.getStartDateDay());
					memo.setEndDateDay(memo.getEndDateDay());
					memo.setExcludeDate(memo.getExcludeDate().replace(",", ";"));
					break;
				case 3://按周
					memo.setRemindWay(memo.getRemindWay());
					memo.setStartDateWeek(memo.getStartDateWeek());
					memo.setEndDateWeek(memo.getEndDateWeek());
					memo.setWeek(memo.getWeek().replace(",", ";"));
					break;
				case 4://按月
					memo.setRemindWay(memo.getRemindWay());
					memo.setMonth(memo.getMonth().replace(",", ";"));
					if(memo != null && memo.getMonth() != null ) {
						String[] monthStrs = memo.getMonth().split("[;]");
						List arrayList = new ArrayList();

						for(String monthStr : monthStrs){
							Date monthDate = null;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							try {
								monthDate = sdf.parse(monthStr);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							arrayList.add(monthDate.compareTo(new Date()));
						}
					}
					break;
//				case 5://倒计时
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(reverseDate);
//					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hours, minutes);
//					Date reverseTime = cal.getTime();
//					memo.setReverseRemindTime(reverseTime);
//					memo.setRemindWay(memo.getRemindWay());
//					MemoTimer.startTask(memo.getReverseRemindTime());
//					break;
//				case 6://阴历
//					memo.setRemindWay(memo.getRemindWay());
//					memo.setRemindDay(memo.getRemindDay().replace(",", ";"));
//					break;
			}
		}
		//获取登陆的用户
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo)session.get(GlobalInfo.LOGINER);
		Account account = loginer.getAccount();
		memo.setAccount(account);
//		if(memo.getRemindWay()==2 && memo.getExcludeDate()!=null){
//			memo.setExcludeDate(memo.getExcludeDate().replace(",", ";"));//存储数据用分号代替逗号
//		}
//		if(memo.getRemindWay()==3 && memo.getWeek()!=null){
//			memo.setWeek(memo.getWeek().replace(",", ";"));//存储数据用分号代替逗号
//		}
//		if(memo.getRemindWay()==5){
//			Date date1 = memo.getStartDateReverse();
//			date1.setHours(starthours);
//			date1.setMinutes(startminutes);
//			date1.setSeconds(startseconds);
//			memo.setStartDateReverse(date1);
//			Date date2 = memo.getEndDateReverse();
//			date2.setHours(endhours);
//			date2.setMinutes(endmintues);
//			date2.setSeconds(endseconds);
//			
//		}
		entityId = dao.add(memo);
		return SUCCESS;
	}
	
	
	/**
	 * 备忘录添加校验
	 */
	public void validateAdd(){
		if (memo.getTitle() == null || memo.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_TITLE_NULL);
		} else if (memo.getTitle().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_TITLE_ILLEGAL);
		}
		else {
			switch(memo.getRemindWay()){//根据提醒方式添加
				case 1://按指定日期
					if(memo.getRemindTime() != null) {
						if (memo.getRemindTime().compareTo(new Date()) <= 0) {
							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_REMIND);
						}
					}
					else {
						this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
					}
					break;
				case 2://按天
					if(memo != null && memo.getStartDateDay() != null && memo.getEndDateDay() != null) {
					if(memo != null && memo.getExcludeDate() != null && !memo.getExcludeDate().equals("")) {
						String[] monthStrs = memo.getExcludeDate().split("[,]");
					    for(String monthStr : monthStrs){
							Date monthDate = null;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							try {
								monthDate = sdf.parse(monthStr);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if (monthDate.compareTo(memo.getStartDateDay()) <= 0||monthDate.compareTo(memo.getEndDateDay()) >= 0) {
								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_REMIND);}
					       }
				        }
					    if (memo.getStartDateDay() != null && memo.getEndDateDay() != null) {
							if (memo.getStartDateDay().compareTo(new Date())<=0 || memo.getStartDateDay().compareTo(memo.getEndDateDay())>= 0) {
								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_REMIND);	}
					 }
					}
					else {
						this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
					}
				break;
				case 3://按周
					if(memo.getStartDateWeek() != null && memo.getEndDateWeek() != null) {
						if (memo.getStartDateWeek().compareTo(new Date())<=0 ||memo.getStartDateWeek().compareTo(memo.getEndDateWeek()) >= 0) {
							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_ILLEGAL);
						}
					}
					else {
						this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
					}
					break;
				case 4://按月
					if(memo != null && memo.getMonth() != null ) {
						String[] monthStrs = memo.getMonth().split("[,]");
						for(String monthStr : monthStrs){
							Date monthDate = null;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							try {
								monthDate = sdf.parse(monthStr);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if (monthDate.compareTo(new Date()) <= 0) {
								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_MONTH_ILLEGAL);
							}
						}
					}
					else {
						this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
					}
					break;
//				case 5://倒计时
//					if(reverseDate != null) {
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(reverseDate);
//						cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hours, minutes);
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						String nowTime = sdf.format(new Date());
//						String reverseTime = sdf.format(cal.getTime());
//						if (reverseTime.compareTo(nowTime) < 0) {
//							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_ILLEGAL);
//						}
//					}
//					else {
//						this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
//					}
//					break;
//				case 6://阴历
//					if(memo != null && memo.getRemindDay() != null ) {
//						String[] monthStrs = memo.getRemindDay().split(",");
//						for(String monthStr : monthStrs){
//							Date monthDate = null;
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//							try {
//								monthDate = sdf.parse(monthStr);
//							} catch (ParseException e) {
//								e.printStackTrace();
//							}
//							if (monthDate.compareTo(new Date()) <= 0) {
//								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_MONTH_ILLEGAL);
//							}
//						}
//					}
//					else {
//						this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
//					}
//					break;
				default:
					break;	
			}
		}
	}
	
	/**
	 * 进入查看页面
	 */
	public String toView() {
		return SUCCESS;
	}
	/**
	 * 查看备忘录
	 */
	@SuppressWarnings("unchecked")
	public String view(){
		memo = (Memo) dao.query(Memo.class, entityId);
		if(null == memo){
			jsonMap.put(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_MEMO_NULL);
			return INPUT;
		}
		if ( null != memo.getAccount()) {
			Passport passport = (Passport) dao.query(Passport.class, memo.getAccount().getPassport().getId());
			jsonMap.put("accountName", passport == null ? null : passport.getName());
		} else {
			jsonMap.put("accountName", null);
		}
		jsonMap.put("memo", memo);
		return SUCCESS;
	}
	
	/**
	 * 进入修改页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toModify() {
		session.put("entityId", entityId);
		memo = (Memo) dao.query(Memo.class, entityId);
		if(null != memo){
		}
		return (null != memo) ? SUCCESS : INPUT;
	}
	
	/**
	 * 进入修改校验
	 */
	@SuppressWarnings("unchecked")
	public void validateToModify() {
		if(null == entityId || "".equals(entityId.trim())) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "没有对应的备忘录信息！");
		} else {
			memo = (Memo) dao.query(Memo.class, entityId);
			if(null == memo){
				jsonMap.put(GlobalInfo.ERROR_INFO, "记录不存在！");
			}
		}
	}
	/**
	 *备忘录修改 
	 */
	@SuppressWarnings("unchecked")
	public String modify(){
		if(modifyFlag !=2){
			entityId = (String) session.get("entityId");
			memoService.modifyMemo(entityId, memo);
			return SUCCESS;
//			if(memo.getRemindWay()==5){
//			Calendar cal = Calendar.getInstance();
////			cal.setTime(reverseDate);
//			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hours, minutes);
//			Date reverseTime = cal.getTime();
////			memo.setReverseRemindTime(reverseTime);
//			MemoTimer.startTask(memo.getReverseRemindTime());
//			}
		}
		else{//只用于不再提醒功能
			memo = (Memo) dao.query(Memo.class, entityId);
			memo.setIsRemind(0);
			memo.setRemindWay(0);
			dao.modify(memo);
			jsonMap.put("entityId", entityId);
			return "mFlag";
		}

	}
	
	/**
	 * 备忘录修改校验
	 */
	public void validateModify() {
		if(modifyFlag != 2){
			if (memo.getTitle() == null || memo.getTitle().trim().equals("")) {
				this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_TITLE_NULL);
			}else if (memo.getTitle().trim().length() > 100) {
				this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_TITLE_ILLEGAL);
			}
			if(memo.getIsRemind() == 0){
				memo.setRemindWay(0);
			}else if(memo.getIsRemind() == 1){
				switch(memo.getRemindWay()){//根据提醒方式添加
					case 1://按指定日期
						if(memo.getRemindTime() != null) {
							if (memo.getRemindTime().compareTo(new Date()) <= 0) {
								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_REMIND);
							}
						}
						else {
							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
						}
						break;
					case 2://按天
						if(memo != null && memo.getStartDateDay() != null && memo.getEndDateDay() != null) {
						        if(memo != null && memo.getExcludeDate() != null && !memo.getExcludeDate().equals("")) {
								String[] monthStrs = memo.getExcludeDate().split("[,]");
							    for(String monthStr : monthStrs){
									Date monthDate = null;
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									try {
										monthDate = sdf.parse(monthStr);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									if (monthDate.compareTo(memo.getStartDateDay()) <= 0||monthDate.compareTo(memo.getEndDateDay()) >= 0) {
										this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_REMIND);}
							       }
						        }
							    if (memo.getStartDateDay() != null && memo.getEndDateDay() != null) {
									if (memo.getStartDateDay().compareTo(new Date())<=0 || memo.getStartDateDay().compareTo(memo.getEndDateDay())>= 0) {
										this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_REMIND);	}
							}
						}
						else {
							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
						}
						break;
					case 3://按周
						if(memo.getStartDateWeek() != null && memo.getEndDateWeek() != null) {
							if (memo.getStartDateWeek().compareTo(new Date())<=0 ||memo.getStartDateWeek().compareTo(memo.getEndDateWeek()) >= 0) {
								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_ILLEGAL);
							}
						}
						else {
							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
						}
						break;
					case 4://按月
						if(memo != null && memo.getMonth() != null ) {
							String[] monthStrs = memo.getMonth().split("[,]");
							for(String monthStr : monthStrs){
								Date monthDate = null;
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								try {
									monthDate = sdf.parse(monthStr);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								if (monthDate.compareTo(new Date()) <= 0) {
									this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_MONTH_ILLEGAL);
								}
							}
						}
						else {
							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
						}
						break;
//					case 5://倒计时
//						if(reverseDate != null) {
//							Calendar cal = Calendar.getInstance();
//							cal.setTime(reverseDate);
//							cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hours, minutes);
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//							String nowTime = sdf.format(new Date());
//							String reverseTime = sdf.format(cal.getTime());
//							if (reverseTime.compareTo(nowTime) < 0) {
//								this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_ILLEGAL);
//							}
//						}
//						else {
//							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
//						}
//						break;
//					case 6://阴历
//						if(memo != null && memo.getRemindDay() != null ) {
//							String[] monthStrs = memo.getRemindDay().split(",");
//							for(String monthStr : monthStrs){
//								Date monthDate = null;
//								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//								try {
//									monthDate = sdf.parse(monthStr);
//								} catch (ParseException e) {
//									e.printStackTrace();
//								}
//								if (monthDate.compareTo(new Date()) <= 0) {
//									this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_MONTH_ILLEGAL);
//								}
//							}
//						}
//						else {
//							this.addFieldError(GlobalInfo.ERROR_INFO, MemoInfo.ERROR_DATE_NULL);
//						}
//						break;
					default:
						break;	
				}
			}
		}
	}
	
	
	/**
	 * 备忘录删除
	 */
	public String delete() {
		for (String entityId : entityIds){
			dao.delete(Memo.class, entityId);
		}
//		if (type == 1) {
//			backToList(pageName(), pageNumber, null);
//		} else {
//			backToList(pageName(), -1, entityIds.get(0));
//		}
		return SUCCESS;
	}
	/**
	 * 删除校验
	 */
	@SuppressWarnings("unchecked")
	public void validateDelete() {
		if (null == entityIds || entityIds.isEmpty()) {// id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_INFO);
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}

	
	@Override
	public String[] column() {
		return column;
	}
	public String dateFormat() {
		return dateFormat;
	}
	public String pageBufferId() {
		return pageBufferId;
	}
	public String pageName() {
		return pageName;
	}
	public static String getHql() {
		return HQL;
	}
	
	public Memo getMemo() {
		return memo;
	}
	public void setMemo(Memo memo) {
		this.memo = memo;
	}
	public void setMemoService(IMemoService memoService) {
		this.memoService = memoService;
	}
	public IMemoService getMemoService() {
		return memoService;
	}

	public void setDatetime1(Date datetime1) {
		this.datetime1 = datetime1;
	}

	public Date getDatetime1() {
		return datetime1;
	}

	public void setDatetime2(Date datetime2) {
		this.datetime2 = datetime2;
	}

	public Date getDatetime2() {
		return datetime2;
	}

//	public void setReverseDate(Date reverseDate) {
//		this.reverseDate = reverseDate;
//	}
//
//	public Date getReverseDate() {
//		return reverseDate;
//	}
//	public int getHours() {
//		return hours;
//	}
//
//	public void setHours(int hours) {
//		this.hours = hours;
//	}
//
//	public int getMinutes() {
//		return minutes;
//	}
//
//	public void setMinutes(int minutes) {
//		this.minutes = minutes;
//	}

	public void setModifyFlag(int modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public int getModifyFlag() {
		return modifyFlag;
	}
}