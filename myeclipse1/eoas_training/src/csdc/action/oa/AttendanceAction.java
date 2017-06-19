package csdc.action.oa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Asset;
import csdc.bean.Attendance;
import csdc.bean.Department;
import csdc.bean.Person;
import csdc.bean.common.Visitor;
import csdc.service.IBaseService;

public class AttendanceAction extends ActionSupport{
	private IBaseService baseService;
	private Attendance attendance = new Attendance();
	private Account account;
	private String tag;//用于指定考勤类型(12:签到签退列表，1:签到；2：签退 3：请假  4：出差 5：加班)
	private String signupTime;
	private String result = "";
	private String attendanceId;
	Person person = new Person();
	Map jsonMap = new HashMap();
	public String toList() {
		if(tag.equals("12")) {
			result = "onetwo";
		} else if(tag.equals("3")) {
			result = "three";
		} else if(tag.equals("4")) {
			result = "four";
		} else if(tag.equals("5")){
			result = "five";
		}
		return result;
	}
	
	public String list() {
		Map map = new HashMap();
		Map session = ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		map.put("accountId", account.getId());
		if (tag.equals("12")) {
			ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
			List<Object[]> aList = new ArrayList<Object[]>();
			map.put("flag", 1);
			attendanceList = (ArrayList<Attendance>) baseService.list(Attendance.class, map);
			String[] item;
			for (Attendance atd : attendanceList) {
				item = new String[5];
				Account account = (Account) baseService.load(Account.class, atd.getAccount().getId());
				Person person = (Person) baseService.load(Person.class, account.getBelongId());
				item[0] = person.getDepartment().getName();
				item[1] = person.getRealName();
				item[2] = atd.getSignupTime() + "";
				item[3] = atd.getLogoutTime() + "";
				item[4] = atd.getId();
				aList.add(item);
			}
			jsonMap.put("aaData", aList);
			result = "onetwo";
			
		} else if(tag.equals("2")) {

		} else if(tag.equals("3")) {//请假列表
			ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
			List<Object[]> aList = new ArrayList<Object[]>();
			map.put("flag", 2);
			attendanceList = (ArrayList<Attendance>) baseService.list(Attendance.class, map);
			String[] item;
			for (Attendance atd : attendanceList) {
				item = new String[10];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (null != atd.getApplyTime()) {
					item[0] = sdf.format(atd.getApplyTime());
				}
				Person person = (Person) baseService.load(Person.class, atd.getAccount().getBelongId());
				item[1] = person.getRealName();
				if(null != person.getDepartment()) {
					Department department = (Department) baseService.load(Department.class, person.getDepartment().getId());
					item[2] = department.getName();
				} else {
					item[2] = "";
				}
				item[3] = atd.getDays() + "";
				if (null != atd.getStartTime()) {
					item[4] = sdf.format(atd.getStartTime());
				}
				if (null != atd.getEndTime()) {
					item[5] = sdf.format(atd.getEndTime());
				}
				item[6] = atd.getType() + "";
				item[7] = atd.getStatus() + "";
				item[8] = atd.getAuditResult() + "";
				item[9] = atd.getId();
				aList.add(item);
			}
			jsonMap.put("aaData", aList);
			result = "three";
		} else if(tag.equals("4")) {//出差列表
			ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
			List<Object[]> aList = new ArrayList<Object[]>();
			map.put("flag", 3);
			attendanceList = (ArrayList<Attendance>) baseService.list(Attendance.class, map);
			String[] item;
			for (Attendance atd : attendanceList) {
				item = new String[9];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (null != atd.getApplyTime()) {
					item[0] = sdf.format(atd.getApplyTime());
				}
				/*Account account = (Account) baseService.load(Account.class, a.getAccount().getId());*/
				Person person = (Person) baseService.load(Person.class, atd.getAccount().getBelongId());
				item[1] = person.getRealName();

				if(null != person.getDepartment()) {
					Department department = new Department();
					try {
						department = (Department) baseService.load(Department.class, person.getDepartment().getId());
						item[2] = department.getName();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					item[2] = "";
				}
				
				item[3] = atd.getDays() + "";
				if (null != atd.getStartTime()) {
					try {
						item[4] = sdf.format(atd.getStartTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (null != atd.getEndTime()) {
					item[5] = sdf.format(atd.getEndTime());
				}
				item[6] = atd.getType() + "";
				item[7] = atd.getStatus() + "";
				item[8] = atd.getId();
				aList.add(item);
			}
			jsonMap.put("aaData", aList);
			result = "four";
		} else if (tag.equals("5")) {
			ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
			List<Object[]> aList = new ArrayList<Object[]>();
			map.put("flag", 4);
			attendanceList = (ArrayList<Attendance>) baseService.list(Attendance.class, map);
			String[] item;
			for (Attendance atd : attendanceList) {
				item = new String[7];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (null != atd.getApplyTime()) {
					item[0] = sdf.format(atd.getApplyTime());
				}
				/*Account account = (Account) baseService.load(Account.class, a.getAccount().getId());*/
				Person person = (Person) baseService.load(Person.class, atd.getAccount().getBelongId());
				item[1] = person.getRealName();

				if(null != person.getDepartment()) {
					Department department = new Department();
					try {
						department = (Department) baseService.load(Department.class, person.getDepartment().getId());
						item[2] = department.getName();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					item[2] = "";
				}
				
				/*item[3] = atd.getDays() + "";*/
				if (null != atd.getStartTime()) {
					try {
						item[3] = sdf.format(atd.getStartTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (null != atd.getEndTime()) {
					item[4] = sdf.format(atd.getEndTime());
				}
				item[5] = atd.getStatus() + "";
				item[6] = atd.getId();
				aList.add(item);
			}
			jsonMap.put("aaData", aList);
			result = "five";
		}
		
		
		return result;
	}
	
	public String toAdd() {
		Map session = ActionContext.getContext().getSession();
		Account account = ((Visitor) session.get("visitor")).getAccount();
		/*Account account = (Account) session.get("account");*/
		person = (Person) baseService.load(Person.class, account.getBelongId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		signupTime = sdf.format(new Date());
		String result = "";
		if(tag.equals("1")) {
			result = "one";
		} else if (tag.equals("2")) {
			result = "two";
		} else if(tag.equals("3")) {
			result = "three";
		} else if(tag.equals("4")) {
			result = "four";
		} else if(tag.equals("5")){
			result = "five";
		}
		return result;
	}
	
	public String add() {
		if(tag.equals("1")) {//签到
			Map session = ActionContext.getContext().getSession();
			Account account = ((Visitor) session.get("visitor")).getAccount();
			attendance.setAccount(account);
			attendance.setSignupTime(new Date());
			attendance.setFlag(1);
			try {
				baseService.add(attendance);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = "one";
		} else if(tag.equals("2")) {//签退
			Map session = ActionContext.getContext().getSession();
			Account account = (Account) session.get("account");
			Map map = new HashMap();
			map.put("accountId", account.getId());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			//获取当日凌晨时间
			calendar.add(Calendar.DAY_OF_MONTH, 0);
			Date date1 = calendar.getTime();
			//获取次日凌晨时间
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			Date date2 = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("date1", sdf.format(date1));
			map.put("date2", sdf.format(date2));
			Attendance attendance = (Attendance) baseService.load(Attendance.class.getName() + ".loadTodaySignup", map);
			attendance.setLogoutTime(new Date());
			baseService.modify(attendance);
			result = "two";
		} else if(tag.equals("3")) {
			attendance.setFlag(2);
			Map session = ActionContext.getContext().getSession();
			Account account = (Account) session.get("account");
			attendance.setAccount(account);
			baseService.add(attendance);
			result = "three";
		} else if(tag.equals("4")) {
			
		} else if (tag.equals("5")) {
			
		}		
		return result;
	}
	
	public String toAudit() {
		attendance = (Attendance) baseService.load(Attendance.class, attendanceId);
		person = (Person) baseService.load(Person.class, attendance.getAccount().getBelongId());
		if(tag.equals("3")) {
			result = "three";
		} else if(tag.equals("4")) {
			result = "four";
		} else if(tag.equals("5")) {
			result = "five";
		}
		return result;
	}
	
	public String audit() {
		if(tag.equals("3")) {
			Map session = ActionContext.getContext().getSession();
			account = (Account) session.get("account");
			Attendance atd = (Attendance) baseService.load(Attendance.class, attendanceId);
			atd.setStatus(1);
			atd.setAuditAccount(account);
			atd.setAuditDateTime(new Date());
			atd.setAuditOpinion(attendance.getAuditOpinion());
			atd.setAuditResult(attendance.getAuditResult());
			try {
				baseService.modify(atd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = "three";
		}
		return result;
	}
	
	public String delete() {
		baseService.delete(Attendance.class, attendanceId);
		return SUCCESS;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSignupTime() {
		return signupTime;
	}

	public void setSignupTime(String signupTime) {
		this.signupTime = signupTime;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Attendance getAttendance() {
		return attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}

}