package csdc.service.webService.client;

import java.util.Map;

/**
 * 优化根据以下需求，单独提出本"控制层"
 * (1)对SmdbService实现单例模式, 解决之前系统启动依以的问题；
 * (2)对smdbService服务的调用进行一次封装，每次只进行一次操作进行限制；
 * @author zhangnan
 * 2015-5-26
 */
public class SmdbClient {
	//仅在初始化调用的时候，全局保持一个实例变量
	private static SmdbClient instance = null;
	private static SmdbService smdbService = null;
	//线程休眠时间间隔线程休眠长1s（毫秒）
	public static final long WaitUnitTime = 1000;
	//等待时间长短，暂定一小时(分钟)
	public static final long WaitTime1H = 60*60;
	private SmdbClient() {
		
	}
	//线程安全操作,单例模式调用
	public static SmdbClient getInstance() {
		if (instance == null) {  
	        synchronized (SmdbClient.class) {  
		        if (instance == null) {  
		        	instance = new SmdbClient();  
		        }  
	        }  
	    }
		//单例实例化客户端组件
		if (smdbService == null) {  
	        synchronized (SmdbService.class) {  
		        if (smdbService == null) {  
		        	smdbService = new SmdbService();  
		        }  
	        }  
	    }  
		
		return instance;
	}
	
	private SmdbService getSmdbService() {
		if (smdbService == null) {  
	        synchronized (SmdbService.class) {  
		        if (smdbService == null) {  
		        	smdbService = new SmdbService();  
		        }  
	        }  
	    }  
		return smdbService;
	}
	
	/**
	 * 同时只能进行一个作业
	 */
	private boolean busy = false;
	
	/**
	 * smdbService方法的封装
	 */

	/**
	 * 建立安全连接
	 * @param passport 用户名
	 * @param password 密码
	 * @param algorithmName 算法类型，目前系统支持“DES”、“AES”、“DESede”三种类型，填写对应的算法名称即可
	 * <br/>
	 * 如：OpenSecurityConnection("user1","123456","DES")
	 * @return 
	 * (1)"busy":当前有操作为执行完毕 ;<br/>
	 * (2) null : 本次操作中断，没有正常执行，需要后续抛异常;<br/>
	 * (3) 不是以上内容：服务返回的信息<bt/>
	 */
	public String OpenSecurityConnection(String passport, String password, String algorithmName) {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return "busy";
		}
		String resultContent = null; 
		try {
			busy = true;
			resultContent = getSmdbService().OpenSecurityConnection(passport, password, algorithmName);
		} catch (Exception e) {
			e.printStackTrace();
			resultContent = null; 
		} finally {
			busy = false;
			return resultContent;
		}
	}

	/**
	 *  安全服务调用
	 * @param passport 用户名
	 * @param password 密码
	 * @param serviceName 服务名称
	 * @param method 方法
	 * @param argsMap 参数
	 * @return 
	 * (1)"busy":当前有操作为执行完毕 ;<br/>
	 * (2) null : 本次操作中断，没有正常执行，需要后续抛异常;<br/>
	 * (3) 不是以上内容：服务返回的信息<bt/>
	 */
	public String invokeSecurityService(String passport, String password, String serviceName, 
			String method, Map<String,String> argsMap ) {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return "busy";
		}
		String resultContent = null; 
		try {
			busy = true;
			resultContent = getSmdbService().invokeSecurityService(passport, password, serviceName, method, argsMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultContent = null; 
		} finally {
			busy = false;
			return resultContent;
		}
	}
	
	/**
	 * 关闭安全连接
	 * @return 
	 * (1)"busy":当前有操作为执行完毕 ;<br/>
	 * (2) null : 本次操作中断，没有正常执行，需要后续抛异常;<br/>
	 * (3) 其他：服务返回的信息<bt/>
	 */
	public String closeSecurityConnection() {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return "busy";
		}
		String resultContent = null; 
		try {
			busy = true;
			resultContent = getSmdbService().closeSecurityConnection();
		} catch (Exception e) {
			e.printStackTrace();
			resultContent = null; 
		} finally {
			busy = false;
			return resultContent;
		}
	}
	
	/**
	 *  直接服务调用，不经过安全加密方式
	 * @param passport 用户名
	 * @param password 密码
	 * @param serviceName 服务名称
	 * @param method 方法
	 * @param argsMap 参数
	 * @return 
	 * (1)"busy":当前有操作为执行完毕 ;<br/>
	 * (2) null : 本次操作中断，没有正常执行，需要后续抛异常;<br/>
	 * (3) 不是以上内容：服务返回的信息<bt/>
	 */
	public String invokeDirect(String passport, String password, String serviceName, 
			String method, Map<String,String> argsMap)  {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return "busy";
		}
		String resultContent = null; 
		try {
			busy = true;
			resultContent = getSmdbService().invokeDirect(passport, password, serviceName, method, argsMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultContent = null; 
		} finally {
			busy = false;
			return resultContent;
		}
	}

	/**
	 *  直接服务调用，不经过安全加密方式(无参数)
	 * @param passport 用户名
	 * @param password 密码
	 * @param serviceName 服务名称
	 * @param method 方法
	 * @param argsMap 参数
	 * @return 
	 * (1)"busy":当前有操作为执行完毕 ;<br/>
	 * (2) null : 本次操作中断，没有正常执行，需要后续抛异常;<br/>
	 * (3) 不是以上内容：服务返回的信息<bt/>
	 */
	public String invokeDirect(String passport, String password, String serviceName, 
			String method)  {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return "busy";
		}
		String resultContent = null; 
		try {
			busy = true;
			resultContent = getSmdbService().invokeDirect(passport, password, serviceName, method);
		} catch (Exception e) {
			e.printStackTrace();
			resultContent = null; 
		} finally {
			busy = false;
			return resultContent;
		}
	}
	/**
	 * 判断当前任务是否busy
	 * @return
	 * true：当前忙
	 * false：当前空前
	 */
	public boolean isBusy() {
		return busy;
	}
	
	public void setNotBusy() {
		this.busy = false;
	}
}
