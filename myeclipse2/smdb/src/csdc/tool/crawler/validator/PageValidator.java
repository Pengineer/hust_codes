package csdc.tool.crawler.validator;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.client.HttpClient;

import csdc.tool.crawler.Crawler;

/**
 * 页面合法性校验基类
 * 每个子类只有一个实例，使用登记式单例模式实现
 * 
 * @author Isun
 *
 */
abstract public class PageValidator {
	
	private static HashMap<Class<? extends PageValidator>, PageValidator> instances = new HashMap<Class<? extends PageValidator>, PageValidator>();
	
	public static PageValidator getInstance(Class<? extends PageValidator> clazz) throws InstantiationException, IllegalAccessException {
		if (!instances.containsKey(clazz)) {
			synchronized (PageValidator.class) {
				if (!instances.containsKey(clazz)) {
					instances.put(clazz, clazz.newInstance());
				}
			}
		}
		return instances.get(clazz);
	}
	
	
	protected static HttpClient httpClient = Crawler.getHttpClient();
	
	private static final long ONE_WEEK = 7 * 86400 * 1000L;
	
	/**
	 * 页面是否更改过，导致爬虫不再有效
	 */
	private boolean outdated = false;
	
	/**
	 * 上次校验页面合法性时间
	 */
	private Date lastCheckValidDate = new Date(0L);
	
	private Lock lock = new ReentrantLock();
	
	
	
	/**
	 * 具体页面校验工作，由子类实现
	 * @return
	 * @throws Exception 
	 */
	abstract protected boolean validInner() throws Exception;

	/**
	 * 对外的校验接口
	 * 一旦检测出过期，则永远失效；如果上次检测是一周以前，则需要重新检测
	 *  
	 * @return
	 */
	public boolean valid() {
		if (outdated) {
			return false;
		}
		if (new Date().getTime() - lastCheckValidDate.getTime() > ONE_WEEK) {
			lock.lock();
			try {
				if (new Date().getTime() - lastCheckValidDate.getTime() > ONE_WEEK) {
					outdated = !validInner();
					lastCheckValidDate = new Date();
				}
			} catch (Exception e) {
				e.printStackTrace();
				outdated = true;
			} finally {
				lock.unlock();
			}
		}
		return !outdated;
	}


	public boolean isOutdated() {
		return outdated;
	}
}
