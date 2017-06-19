package org.csdc.service.imp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.server.namenode.dfsclusterhealth_jsp;
import org.csdc.bean.KeyValue;
import org.csdc.bean.Performance;
import org.csdc.bean.ServerNode;
import org.csdc.tool.Base64Util;
import org.csdc.tool.DatetimeTool;
import org.csdc.tool.HttpTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.tools.javac.resources.compiler;
import com.sun.tools.jdi.LinkedHashMap;
/**
 * 集群管理业务类
 * @author jintf
 * @date 2014-6-16
 */
@Service
@Transactional
public class ClusterService extends BaseService{
	
	/**
	 * 获取Hadoop集群NameNode节点状态
	 * @return
	 * @throws IOException
	 */
	public Object[][] getHadoopNameNodeStatus() throws IOException{
		DecimalFormat df = new DecimalFormat("#.00");
		Object[][] o = new Object[12][2];
		
		String nn = application.getParameter("CLUSTER_HADOOP_MASTER");	
		System.setProperty("HADOOP_USER_NAME", "root");
        InetSocketAddress namenodeAddr = new InetSocketAddress(nn.substring(0,nn.indexOf(":")),Integer.valueOf(nn.substring(nn.indexOf(":")+1)));
        Configuration conf = new Configuration();
        DFSClient client = new DFSClient(namenodeAddr, conf);
        ClientProtocol namenode = client.getNamenode();
        long [] data = namenode.getStats();
        o[0][0] ="系统总容量"; o[0][1]= getDiskSize(data[0]);
        o[1][0] ="DFS使用容量"; o[1][1]=getDiskSize(data[1]);
        o[2][0] ="DFS剩余容量"; o[2][1]=getDiskSize(data[2]);
        o[3][0] ="总块数"; o[3][1]=data[3];
        o[4][0] ="有损坏副本块数"; o[4][1]=data[4];
        o[5][0] ="副本全损块数"; o[5][1]=data[5];
        o[6][0] ="主机地址"; o[6][1]=namenodeAddr.getAddress().toString();
        o[7][0] ="端口"; o[7][1]=namenodeAddr.getPort();
        o[8][0] ="DFS使用率"; o[8][1]= df.format(100*data[1]/(data[0]*1.0)).toString()+"%";
        o[9][0] ="DFS剩余率"; o[9][1]= df.format(100*data[2]/(data[0]*1.0)).toString()+"%";
		//节点统计
        DatanodeInfo[] datanodeReportLive = namenode.getDatanodeReport(DatanodeReportType.LIVE);
        DatanodeInfo[] datanodeReportDead = namenode.getDatanodeReport(DatanodeReportType.DEAD);
        o[10][0] ="活动节点数"; o[10][1] = datanodeReportLive.length;
        o[11][0] ="不活动节点数"; o[11][1] = datanodeReportDead.length;
        return o;
	}
	
	/**
	 * 获取Hadoop集群DataNode节点状态
	 * @return
	 * @throws IOException
	 */
	public Object[][] getHadoopDataNodeStatus(int index) throws IOException{
		DecimalFormat df = new DecimalFormat("#.00");
		Object[][] o = new Object[10][2];
		String nn = application.getParameter("CLUSTER_HADOOP_MASTER");	
		System.setProperty("HADOOP_USER_NAME", "root");
        InetSocketAddress namenodeAddr = new InetSocketAddress(nn.substring(0,nn.indexOf(":")),Integer.valueOf(nn.substring(nn.indexOf(":")+1)));
        Configuration conf = new Configuration();
        DFSClient client = new DFSClient(namenodeAddr, conf);
        ClientProtocol namenode = client.getNamenode();
        DatanodeInfo[] datanodeReport = namenode.getDatanodeReport(DatanodeReportType.ALL);
        DatanodeInfo datanode = datanodeReport[index];
        
        System.out.println(datanode.getDatanodeReport());
        o[0][0] ="主机名"; o[0][1]= datanode.getHostName();
        o[1][0] ="主机地址"; o[1][1] = datanode.getInfoAddr().substring(0, datanode.getInfoAddr().indexOf(":"));
        o[2][0] ="主机端口"; o[2][1]= datanode.getInfoPort();
        o[3][0] ="总容量"; o[3][1]= getDiskSize(datanode.getCapacity());
        o[4][0] ="DFS使用容量"; o[4][1]= getDiskSize(datanode.getDfsUsed());
        o[5][0] ="非DFS占用容量"; o[5][1]= getDiskSize(datanode.getNonDfsUsed());
		o[6][0] ="DFS剩余容量"; o[6][1]= getDiskSize(datanode.getRemaining());
        o[7][0] ="DFS使用率"; o[7][1]= df.format(datanode.getDfsUsedPercent())+"%";
        o[8][0] ="DFS剩余率"; o[8][1]= df.format(datanode.getRemainingPercent())+"%";;      
        o[9][0] ="上次报告时间"; o[9][1]= DatetimeTool.getDatetimeString(new Date(datanode.getLastUpdate()), "yyyy-MM-dd hh:mm:ss");
        return o;
	}
	
	/**
	 * 获取Solr节点状态
	 * @param index
	 * @return
	 */
	public Object[][] getSolrNodeStatus(int index){
		String systemUrl = "";
		List<KeyValue<String, String>> pairs = new ArrayList<KeyValue<String,String>>();
		String hostString = application.getParameter("CLUSTER_SOLR");
		String [] hosts = hostString.split(",");
		String host = hosts[index];		
		String url = "http://"+host + "/solr/admin/cores";
		Map para = new HashMap<String, String>();
		para.put("wt", "json");
		JSONObject jsonObject = HttpTool.getJson(url, para);
		pairs.add(new KeyValue("SolrCore名",jsonObject.get("defaultCoreName")));
		JSONObject status = (JSONObject)jsonObject.get("status");
		Set<String> shareds = status.keySet();
		pairs.add(new KeyValue("分片数", shareds.size()));
		for(String shared : shareds){
			systemUrl = "http://"+host+"/solr/"+shared+"/admin/system?wt=json";
			pairs.add(new KeyValue("分片名", shared));
			JSONObject sharedJson = status.getJSONObject(shared);
			pairs.add(new KeyValue("存储路径", sharedJson.get("instanceDir")));
			pairs.add(new KeyValue("启动时间", sharedJson.get("startTime")));
			JSONObject indexJson = sharedJson.getJSONObject("index");
			pairs.add(new KeyValue("文档数", indexJson.get("numDocs")));
			pairs.add(new KeyValue("删除文档数", indexJson.get("deletedDocs")));
			pairs.add(new KeyValue("索引段数", indexJson.get("segmentCount")));
			pairs.add(new KeyValue("版本", indexJson.get("version")));
			pairs.add(new KeyValue("索引大小", indexJson.get("size")));
			pairs.add(new KeyValue("上次更新时间", indexJson.get("lastModified")));
		}
		JSONObject systemJson = HttpTool.getJson(systemUrl);
		pairs.add(1,new KeyValue("LUCENE版本", ((JSONObject)systemJson.get("lucene")).get("solr-spec-version")));
		pairs.add(1,new KeyValue("操作系统版本", ((JSONObject)systemJson.get("system")).get("name")));
		pairs.add(1,new KeyValue("CPU架构", ((JSONObject)systemJson.get("system")).get("arch")));
		pairs.add(1,new KeyValue("运行模式", systemJson.get("mode")));
		//存入object数组
		Object[][] o = new Object[pairs.size()][2];
		int i=0;
		for (KeyValue<String, String> pair:pairs) {
			o[i][0] = pair.getKey();
			o[i][1] = pair.getValue();
			i++;
		}		
        return o;
	}
	
	/**
	 * 获取Tomcat节点状态
	 * @param index 
	 * @return
	 * @throws Exception
	 */
	public Object[][] getTomcatNodeStatus(int index) throws Exception{
		DecimalFormat df = new DecimalFormat("#.00");
		List<KeyValue<String, String>> pairs = new ArrayList<KeyValue<String,String>>();
		//String hostString ="192.168.88.90:9080";
		String hostString = application.getParameter("CLUSTER_TOMCAT");
		String [] hosts = hostString.split(",");
		String host = hosts[index];	
		//服务器信息
		String serverInfo = getJmxResponse("http://"+host+"/manager/serverinfo");
		String[] serverInfoLines = serverInfo.split("\r\n");
		pairs.add(new KeyValue("Tomcat版本", serverInfoLines[1].substring(serverInfoLines[1].indexOf(": ")+2)));
		pairs.add(new KeyValue("操作系统", serverInfoLines[2].substring(serverInfoLines[2].indexOf(": ")+2)));
		pairs.add(new KeyValue("CPU架构", serverInfoLines[4].substring(serverInfoLines[4].indexOf(": ")+2)));
		pairs.add(new KeyValue("JVM版本", serverInfoLines[5].substring(serverInfoLines[5].indexOf(": ")+2)));
		//机器内存信息
		String operationSystemInfo = getJmxResponse("http://"+host+"/manager/jmxproxy?qry=java.lang:type=OperatingSystem");
		String[] operationSystemInfoLines = operationSystemInfo.split("\r\n");		
		pairs.add(new KeyValue("CPU核数", operationSystemInfoLines[14].substring(operationSystemInfoLines[14].indexOf(": ")+2)));
		pairs.add(new KeyValue("CPU占用率", df.format(100*Double.valueOf(operationSystemInfoLines[13].substring(operationSystemInfoLines[13].indexOf(": ")+2)))+"%"));
		pairs.add(new KeyValue("总物理内存", getDiskSize(Long.valueOf(operationSystemInfoLines[11].substring(operationSystemInfoLines[11].indexOf(": ")+2)))));
		pairs.add(new KeyValue("剩余物理内存", getDiskSize(Long.valueOf(operationSystemInfoLines[10].substring(operationSystemInfoLines[10].indexOf(": ")+2)))));
		//线程管理
		String threadInfo = getJmxResponse("http://"+host+"/manager/jmxproxy?qry=*%3Atype%3DThreadPool%2C*");
		String[] threadInfoLines = threadInfo.split("\r\n");
		pairs.add(new KeyValue("最大线程数", threadInfoLines[9].substring(threadInfoLines[9].indexOf(": ")+2)));
		pairs.add(new KeyValue("当前线程数", threadInfoLines[19].substring(threadInfoLines[19].indexOf(": ")+2)));
		//存入object数组
		Object[][] o = new Object[pairs.size()][2];
		int i=0;
		for (KeyValue<String, String> pair:pairs) {
			o[i][0] = pair.getKey();
			o[i][1] = pair.getValue();
			i++;
		}		
        return o;
	}
	
	/**
	 * 获取Nginx节点状态
	 * @param index
	 * @return
	 * @throws IOException
	 */
	public Object[][] getNginxNodeStatus(int index) throws IOException{
		DecimalFormat df = new DecimalFormat("#.00");
		List<KeyValue<String, String>> pairs = new ArrayList<KeyValue<String,String>>();
		//String hostString ="192.168.88.90";
		String hostString = application.getParameter("CLUSTER_NGINX");
		String [] hosts = hostString.split(",");
		String host = hosts[index];	
		//服务器信息
		String serverInfo = getJmxResponse("http://"+host+"/status");
		String[] serverInfoLines = serverInfo.split("\r\n");
		pairs.add(new KeyValue("活动连接数", serverInfoLines[0].substring(serverInfoLines[0].indexOf(": ")+2)));
		String[] counts = serverInfoLines[2].trim().split(" ");
		pairs.add(new KeyValue("接收来连接数", counts[0]));
		pairs.add(new KeyValue("成功连接数", counts[1]));
		pairs.add(new KeyValue("请求总数", counts[2]));
		String[] items = serverInfoLines[3].split(" ");
		pairs.add(new KeyValue("Reading", items[1]));
		pairs.add(new KeyValue("Writing", items[3]));
		pairs.add(new KeyValue("Waiting", items[5]));
		//存入object数组
		Object[][] o = new Object[pairs.size()][2];
		int i=0;
		for (KeyValue<String, String> pair:pairs) {
			o[i][0] = pair.getKey();
			o[i][1] = pair.getValue();
			i++;
		}		
        return o;
	}
	
	/**
	 * 获取jmx响应
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	private String getJmxResponse(String urlString) throws IOException{		
		StringBuffer sb= new StringBuffer();
		URL url = new URL(urlString);
		URLConnection conn = (URLConnection) url.openConnection();
		// URL授权访问 -- Begin
		String password = "admin:admin"; // manager角色的用户
		String encodedPassword = Base64Util.encode(password.getBytes());
		conn.setRequestProperty("Authorization", "Basic " + encodedPassword);
		// URL授权访问 -- End
		InputStream is = conn.getInputStream();
		BufferedReader bufreader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = bufreader.readLine()) != null) {
		    sb.append(line);
		    sb.append("\r\n");
		}
		return sb.toString();
		
	}
	
	/**
	 * 获取Tomcat服务器节点性能
	 * @param node 节点名
	 * @return
	 * @throws IOException
	 */
	public Performance getTomcatCurrentPerformance(String node) throws IOException{
		int index = Integer.valueOf(node.substring(node.length()-1));
		return getTomcatCurrentPerformance(index);
	}
	
	/**
	 * 获取Tomcat服务器节点性能
	 * @param index
	 * @return
	 * @throws IOException
	 */
	public Performance getTomcatCurrentPerformance(int index) throws IOException{
		Performance p = new Performance();
		DecimalFormat df = new DecimalFormat("#.00");
		List<KeyValue<String, String>> pairs = new ArrayList<KeyValue<String,String>>();
		//String hostString ="192.168.88.90:9080";
		String hostString = application.getParameter("CLUSTER_TOMCAT");
		String [] hosts = hostString.split(",");
		String host = hosts[index];			
		//机器内存信息
		String operationSystemInfo = getJmxResponse("http://"+host+"/manager/jmxproxy?qry=java.lang:type=OperatingSystem");
		String[] operationSystemInfoLines = operationSystemInfo.split("\r\n");		
		p.setCpu(100*Double.valueOf(operationSystemInfoLines[12].substring(operationSystemInfoLines[12].indexOf(": ")+2)));
		//p.setMemoryAll(Long.valueOf(operationSystemInfoLines[12].substring(operationSystemInfoLines[12].indexOf(": ")+2))/1024.0/1024.0);
		double memoryAll = Double.valueOf(operationSystemInfoLines[11].substring(operationSystemInfoLines[11].indexOf(": ")+2));
		double memoryRemain = Double.valueOf(operationSystemInfoLines[10].substring(operationSystemInfoLines[10].indexOf(": ")+2));
		p.setMemoryRate((memoryAll-memoryRemain)/(memoryAll*1.0));
		return p;		
	}
	
	/**
	 * 获取Solr节点性能
	 * @param index
	 * @return
	 * @throws IOException
	 */
	public Performance getSolrCloudCurrentPerformance(int index) throws IOException{
		Performance p = new Performance();
		return p;		
	}

}
