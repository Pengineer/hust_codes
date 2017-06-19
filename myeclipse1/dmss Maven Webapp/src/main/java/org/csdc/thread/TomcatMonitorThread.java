package org.csdc.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.csdc.bean.Application;
import org.csdc.bean.Performance;
import org.csdc.service.imp.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Tomcat节点监控线程
 * @author jintf
 * @date 2014-6-16
 */
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class TomcatMonitorThread extends Thread{
	private static int size=20;
	private ConcurrentMap<String, LinkedBlockingQueue<Performance>> map = new ConcurrentHashMap<String, LinkedBlockingQueue<Performance>>(); //存储性能监控数据
	@Autowired
	private Application application;
	
	@Autowired
	private ClusterService clusterService;
	
	@PostConstruct
	private void init(){
		String tomcatHostString = application.getParameter("CLUSTER_TOMCAT");
		String[] tomcatHosts = tomcatHostString.split(",");
		for (int i = 0; i < tomcatHosts.length; i++) {
			map.put("tomcat"+i, new LinkedBlockingQueue<Performance>() );
		}
		//this.start(); //暂时不启动监控线程
	}
	
	public void run(){
		while(true){
			try {
				monitor();
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 执行监控
	 * @throws IOException 
	 */
	public void monitor() throws IOException{
		Set<String> set = map.keySet();
		for(String key:set){
			if(key.startsWith("tomcat")){
				int index = Integer.valueOf(key.substring("tomcat".length()));
				this.add(key, clusterService.getTomcatCurrentPerformance(index));
			}
		}
	}
	
	/**
	 * 添加监控数据
	 * @param node
	 * @param p
	 */
	public void add(String node,Performance p){
		LinkedBlockingQueue queue = map.get(node);
		if(queue.size()>=size){
			queue.poll();		
		}
		queue.add(p);
	}
	
	/**
	 * 获取某个节点最新监控数据
	 * @param node 节点名
	 * @return
	 */
	public Performance getLastedPerformance(String node){
		LinkedBlockingQueue<Performance> queue = map.get(node);
		return (Performance) queue.toArray()[size-1];
	}
	
	/**
	 * 获取某个节点最新监控数据
	 * @param node 节点名
	 * @return 
	 */
	public List<Performance> getPerformances(String node){
		List<Performance> list = new ArrayList<Performance>();
		LinkedBlockingQueue<Performance> queue = map.get(node);
		for(Performance p:queue){
			list.add(p);
		}
		int k = size-list.size();
		for (int i = 0; i < k; i++) {
			list.add(0, new Performance());
		}
		return list;
	}

}
