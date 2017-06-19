package csdc.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

@SuppressWarnings("unchecked")
public class ValidateAction extends ActionSupport {
	
	private static final long serialVersionUID = -4651202342973852779L;
	@SuppressWarnings("rawtypes")
	private Map jsonMap = new HashMap();
	private String startTime;
	private String endTime;
	
	/**
	 * 校验系统数据导出
	 * 限制规则说明：如果当前时间小于7月份，则能导出上年一月一号一号的数据，否则只能导出今年一月一号以后的数据
	 */
	@SuppressWarnings("rawtypes")
	public String validateExport() throws Exception {
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		//对系统管理员不做限制
		if(!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
			
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = sdf.parse(startTime);//parse()返回的是一个Date类型数据
			Date endDate = sdf.parse(endTime);
			String startString = sdf.format(startDate);//format()返回的是一个StringBuffer类型的数据
			String endString = sdf.format(endDate);
			
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endDate);
			
			session.put(GlobalInfo.EXPORT_START_TIME, startCalendar.getTime());
			session.put(GlobalInfo.EXPORT_END_TIME, endCalendar.getTime());
			
			Calendar currentCalendar = Calendar.getInstance();
			boolean isOverHalf= (currentCalendar.get(Calendar.MONTH) + 1 <= 6) ? false : true;
			int exportYear = isOverHalf ? currentCalendar.get(Calendar.YEAR) : currentCalendar.get(Calendar.YEAR) - 1;
			jsonMap.put("exportYear", exportYear);
			
			Calendar limitCalendar = Calendar.getInstance();
			limitCalendar.set(exportYear, 0, 1);
			String limitString = sdf.format(limitCalendar.getTime());
			
			if(startString.compareTo(limitString) < 0 && endString.compareTo(limitString) < 0){
				jsonMap.put(GlobalInfo.ERROR_IS_EXPORT_FORBIDDEN, true);
				return INPUT;
			}else if(startString.compareTo(limitString) < 0 && endString.compareTo(limitString) > 0) {//如果起时间小于限制时间，止时间大于限制时间
				session.put(GlobalInfo.EXPORT_START_TIME, limitCalendar.getTime());
				jsonMap.put(GlobalInfo.EXPORT_START_TIME, limitCalendar.getTime());
				jsonMap.put(GlobalInfo.ERROR_EXPORT_NOT_VALID, true);
				return INPUT;
			}
//			if(startString.before(limitCalendar) && endCalendar.before(limitCalendar)) {//如果起止时间均小于限制时间
//				jsonMap.put(GlobalInfo.ERROR_IS_EXPORT_FORBIDDEN, true);
//				return INPUT;
//			} else if(startCalendar.before(limitCalendar) && endCalendar.after(limitCalendar)) {//如果起时间小于限制时间，止时间大于限制时间
//				session.put(GlobalInfo.EXPORT_START_TIME, limitCalendar.getTime());
//				jsonMap.put(GlobalInfo.EXPORT_START_TIME, limitCalendar.getTime());
//				jsonMap.put(GlobalInfo.ERROR_EXPORT_NOT_VALID, true);
//				return INPUT;
//			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public Map getJsonMap() {
		return jsonMap;
	}
	@SuppressWarnings("rawtypes")
	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}