package csdc.service.taskConfig;

import java.util.Calendar;
import java.util.List;

import csdc.bean.Task;
import csdc.bean.TaskConfig;
import csdc.tool.SpringBean;

public class AutoTaskRunnable implements Runnable{
    private TaskConfig taskConfig;
    private List<Task> tasks;
       
    public AutoTaskRunnable(TaskConfig taskConfig,List<Task> tasks){
        this.taskConfig = taskConfig;
        this.tasks = tasks;
    }
    
	/**
	 * 计算从当前时间currentDate开始，满足条件dayOfWeek, hourOfDay, 
	 * minuteOfHour, secondOfMinite的最近时间
	 * @return
	 */
	public Calendar getEarliestDate(Calendar currentDate, int dayOfWeek,
			int hourOfDay, int minuteOfHour, int secondOfMinite) {
		//计算当前时间的WEEK_OF_YEAR,DAY_OF_WEEK, HOUR_OF_DAY, MINUTE,SECOND等各个字段值
		int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_YEAR);
		int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
		int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
		int currentMinute = currentDate.get(Calendar.MINUTE);
		int currentSecond = currentDate.get(Calendar.SECOND);

		//如果输入条件中的dayOfWeek小于当前日期的dayOfWeek,则WEEK_OF_YEAR需要推迟一周
		boolean weekLater = false;
		if (dayOfWeek < currentDayOfWeek) {
			weekLater = true;
		} else if (dayOfWeek == currentDayOfWeek) {
			//当输入条件与当前日期的dayOfWeek相等时，如果输入条件中的
			//hourOfDay小于当前日期的
			//currentHour，则WEEK_OF_YEAR需要推迟一周	
			if (hourOfDay < currentHour) {
				weekLater = true;
			} else if (hourOfDay == currentHour) {
                 //当输入条件与当前日期的dayOfWeek, hourOfDay相等时，
                 //如果输入条件中的minuteOfHour小于当前日期的
				//currentMinute，则WEEK_OF_YEAR需要推迟一周
				if (minuteOfHour < currentMinute) {
					weekLater = true;
				} else if (minuteOfHour == currentSecond) {
                     //当输入条件与当前日期的dayOfWeek, hourOfDay， 
                     //minuteOfHour相等时，如果输入条件中的
                    //secondOfMinite小于当前日期的currentSecond，
                    //则WEEK_OF_YEAR需要推迟一周
					if (secondOfMinite < currentSecond) {
						weekLater = true;
					}
				}
			}
		}
		if (weekLater) {
			//设置当前日期中的WEEK_OF_YEAR为当前周推迟一周
			currentDate.set(Calendar.WEEK_OF_YEAR, currentWeekOfYear + 1);
		}
		// 设置当前日期中的DAY_OF_WEEK,HOUR_OF_DAY,MINUTE,SECOND为输入条件中的值。
		currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		currentDate.set(Calendar.MINUTE, minuteOfHour);
		currentDate.set(Calendar.SECOND, secondOfMinite);
		return currentDate;
	}
    
	@Override
    public void run() {  
		try {
			TaskConfigService taskConfigService = (TaskConfigService) SpringBean.getBean("taskConfigService");
			taskConfigService.executeTasks(this.tasks,this.taskConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
