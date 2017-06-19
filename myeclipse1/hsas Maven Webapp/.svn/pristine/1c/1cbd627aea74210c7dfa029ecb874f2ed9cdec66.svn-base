package csdc.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间日期常用处理函数的公共类
 * @author 雷达
 * @autoor 龚凡	2010.12.09	更新时间字符串获取方法，让调用者可指定时间格式
 */
public class DatetimeTool {

	/**
	 * 获取系统当前时间，并按指定格式转换为字符串
	 * @formatString 格式化字符串
	 * @return 返回时间字符串
	 */
	public static String getDatetimeString(String formatString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(formatString);
			Date date = new Date();
			return df.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getDatetimeString(Date date,String formatString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(formatString);
			return df.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date parseYYYYMMDDDate(String dateString){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date parseExtjsDate(String dateString){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public static long daysBetween(Date startDate,Date endDate) throws ParseException{
    	long margin = 0;
    	margin = endDate.getTime() - startDate.getTime();
    	margin = margin/(1000*60*60*24);
    	return margin;
    }
	public static Date getDayBefore(Date date,int i) {  
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	    Date dateTemp=date;
	    if(i>20){
	    	dateTemp =new Date(dateTemp.getTime() - (i-20) * 24 * 60 * 60 * 1000);
	    	dateTemp =new Date(dateTemp.getTime() - 20 * 24 * 60 * 60 * 1000);	    	
	    }else {
	    	if (i<-20) {
	    		dateTemp =new Date(dateTemp.getTime() - (i+20) * 24 * 60 * 60 * 1000);
		    	dateTemp =new Date(dateTemp.getTime() +20 * 24 * 60 * 60 * 1000);
			}else{
		    	dateTemp =new Date(date.getTime() - i * 24 * 60 * 60 * 1000);
		    }
		}
	    return dateTemp;
	}
	
	public static Date getHourBefore(Date date,int i) {  
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	    Date dateTemp=date;
	    dateTemp =new Date(date.getTime() - i * 60 * 60 * 1000);
		return dateTemp;
	}
	
    // 取得日期是某年的第几周  
    public static int getWeekOfYear(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);  
        return week_of_year;  
    }
    
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
	
	public static int[] phraseDateByWeek(Date startDate, Date endDate) throws ParseException {
		int[] ii = {0,0,0};
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
		int begin = cal.get(Calendar.DAY_OF_WEEK) - 1;
		cal.setTime(endDate);
		int end = cal.get(Calendar.DAY_OF_WEEK) - 1;
		ii[0] = 8 - begin;
		ii[2] = end-1;
		ii[1] = (int) DatetimeTool.daysBetween(DatetimeTool.getDayBefore(startDate, (-1)*begin), DatetimeTool.getDayBefore(endDate, end))/7;
		return ii;
	}
    public static List<String> getMonthList(Date sDate, Date eDate) {  
    	String beginTime=getDatetimeString(sDate, "yyyyMMdd");
    	String endTime=getDatetimeString(eDate, "yyyyMMdd");;
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");  
        List<String> monthList = new ArrayList<String>();  
        try {
            Date begin = format.parse(beginTime);
            Date end = format.parse(endTime);
            int months = (end.getYear() - begin.getYear()) * 12  
                    + (end.getMonth() - begin.getMonth());  
              
            for (int i = 0; i <= months; i++) {  
                Calendar calendar = Calendar.getInstance();    
                calendar.setTime(begin);    
                calendar.add(Calendar.MONTH, i);  
                monthList.add(monthFormat.format(calendar.getTime()));  
            }
  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
  
        return monthList;  
    }
    
    public static Date getTodayDate(){
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return calendar.getTime();
    }
    public static List<Date> phraseDateByMonth(Date startDate, Date endDate) throws ParseException {
    	List<Date> months = new ArrayList<Date>();
    	months.add(0,startDate);
    	List<String> temp = DatetimeTool.getMonthList(startDate, endDate);
    	switch (temp.size()) {
    	case 0:
    		break;
		case 1:
			months.add(1,endDate);
			break;
		default:
			for (int i = 1; i < temp.size(); i++) {
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM");
		        Date begin = f1.parse(temp.get(i));
		        months.add(i,begin);
			}
			months.add(temp.size(),endDate);
			break;
		}
		return months;
		
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(getTodayDate());
	}
}