package org.csdc.controller.sm.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.JsonData;
import org.csdc.bean.Performance;
import org.csdc.service.imp.ClusterService;
import org.csdc.thread.TomcatMonitorThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 集群监控控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/sm/cluster/monitor")
public class MonitorController {
	@Autowired
	private TomcatMonitorThread tomcatMonitorThread; 
	
	@Autowired
	private ClusterService clusterService;
	
	/**
	 * 进入tomcat集群监控页面
	 * @param name 节点名
	 * @param request
	 * @return
	 */
	@RequestMapping("/tomcat/{name}")
	public String toTomcat(@PathVariable String name,HttpServletRequest request){
		List<Performance> list = tomcatMonitorThread.getPerformances(name);
		String cpuSeries = "",memorySeries="";
		for (int i = 0; i < list.size(); i++) {
			cpuSeries += list.get(i).getCpu() +  " ";
			memorySeries += list.get(i).getMemoryRate() + " ";
		}
		request.setAttribute("name", name);
		request.setAttribute("total", list.size());
		request.setAttribute("cpuSeries", cpuSeries);
		request.setAttribute("memorySeries", memorySeries);
		return "sm/cluster/monitor/tomcat";
	}
	
	/**
	 * tomcat服务器性能实时监控
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/tomcat/update/{name}", produces="application/json")
	@ResponseBody
	public Object tomcatUpdate(@PathVariable String name) throws IOException{		
		JsonData jsonData = new JsonData();
		jsonData.data.put("p",clusterService.getTomcatCurrentPerformance(name));
		return jsonData;
	}
	
	/**
	 * 进入SolrCloud性能监控页面（未实现）
	 * @return
	 */
	@RequestMapping("/solrcloud")
	public String toSolrCloud(){
		return "sm/cluster/monitor/solcloud";
	}
	
}
