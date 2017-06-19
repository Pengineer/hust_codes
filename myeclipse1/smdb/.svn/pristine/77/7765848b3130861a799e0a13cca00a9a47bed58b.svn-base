package csdc.tool.listener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import csdc.bean.Task;
import csdc.bean.TaskConfig;
import csdc.bean.TaskTaskConfig;
import csdc.dao.HibernateBaseDao;
import csdc.service.taskConfig.AutoTaskRunnable;
import csdc.tool.SpringBean;

public class TaskConfigListener implements ServletContextListener {
	private static final long serialVersionUID = 1L;  
	public static Map<String, ScheduledExecutorService> autoExecutorServicesMap = new HashMap<String, ScheduledExecutorService>(); 

	public void contextInitialized(ServletContextEvent event) {  	          
		HibernateBaseDao dao = (HibernateBaseDao) SpringBean.getBean("hibernateBaseDao");
		List<TaskConfig> taskConfigs = dao.query("select tc from TaskConfig tc where tc.isAuto = 1");
		if (taskConfigs != null && taskConfigs.size() > 0) {
			for (TaskConfig taskConfig : taskConfigs) {
				List<TaskTaskConfig> taskTaskConfigs = dao.query("select ttc from TaskTaskConfig ttc where ttc.taskConfig.id = '" + taskConfig.getId() + "' order by ttc.order asc");
				List<Task> taskToExecutes = new ArrayList<Task>();
				for (TaskTaskConfig taskTaskConfig : taskTaskConfigs) {
					taskToExecutes.add(taskTaskConfig.getTask());
				}
				
				//把自动执行的任务配置加入ScheduledExecutor中
				ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
				TaskConfigListener.autoExecutorServicesMap.put(taskConfig.getName(), service);
				System.out.println("定时器已启动");
				AutoTaskRunnable autoTaskRunnable = new AutoTaskRunnable(taskConfig,taskToExecutes);
				if (taskConfig.getExecuteTime() != null) {//如果执行日期（Monday:13:00:00）不为空，则采用执行日期，在指定的执行日期执行该任务，时间间隔为星期。
					int taskDayOfWeek;
					int taskHour;
					int taskMinute;
					int taskSecond;
					Object[] timeObjects = taskConfig.getExecuteTime().split(":");
					if (timeObjects[0].toString().equals("Monday")) {
						taskDayOfWeek = 2;
					}else if (timeObjects[0].toString().equals("Tuesday")) {
						taskDayOfWeek = 3;
					}else if (timeObjects[0].toString().equals("Wednesday")) {
						taskDayOfWeek = 4;
					}else if (timeObjects[0].toString().equals("Thursday")) {
						taskDayOfWeek = 5;
					}else if (timeObjects[0].toString().equals("Friday")) {
						taskDayOfWeek = 6;
					}else if (timeObjects[0].toString().equals("Saturday")) {
						taskDayOfWeek = 7;
					}else {
						taskDayOfWeek = 8;
					}
					taskHour = Integer.parseInt(timeObjects[1].toString());
					taskMinute = Integer.parseInt(timeObjects[2].toString());
					taskSecond = Integer.parseInt(timeObjects[3].toString());
					
					//获取当前时间
					Calendar currentDate = Calendar.getInstance();
					long currentDateLong = currentDate.getTime().getTime();
					//计算满足条件的最近一次执行时间
					Calendar earliestDate = autoTaskRunnable.getEarliestDate(currentDate, taskDayOfWeek, taskHour, taskMinute, taskSecond);
					long earliestDateLong = earliestDate.getTime().getTime();
					//计算从当前时间到最近一次执行时间的时间间隔
					long delay = earliestDateLong - currentDateLong;
					//计算执行周期为一星期
					long period = 7 * 24 * 60 * 60 * 1000;
					//从现在开始delay毫秒之后，每隔一星期执行一次job1
					service.scheduleAtFixedRate(
							autoTaskRunnable, delay,
							period, TimeUnit.MILLISECONDS);
				}else{//如果执行日期（Monday:13:00:00）为空，则采用执行时间间隔（单位分钟），立即执行该任务，并按照设置的时间间隔，周期性的执行该任务
					service.scheduleWithFixedDelay(
							autoTaskRunnable, 0,
							taskConfig.getInterval() * 60 * 1000, TimeUnit.MILLISECONDS);
				}
				System.out.println("已经添加任务调度表");  
			}
		}
	}  
	  
	public void contextDestroyed(ServletContextEvent event) {  
		Map<String, ScheduledExecutorService> autoExecutorServicesMap = TaskConfigListener.autoExecutorServicesMap;
		Set<String> keysSet = autoExecutorServicesMap.keySet();
		Iterator<String> keysIterator = keysSet.iterator();
		while(keysIterator.hasNext()){
			ScheduledExecutorService tempExecutorService = autoExecutorServicesMap.get(keysIterator.next());
			tempExecutorService.shutdownNow();
		}
		System.out.println("定时器销毁");  
	}

}
