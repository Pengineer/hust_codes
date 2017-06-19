package org.csdc.controller.sm.cluster;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FsServerDefaults;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeDescriptor;
import org.apache.lucene.analysis.charfilter.BaseCharFilter;
import org.apache.solr.common.cloud.ClusterState;
import org.csdc.bean.ServerNode;
import org.csdc.controller.BaseController;
import org.csdc.service.imp.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.star.lib.util.StringHelper;
/**
 * 集群概要管理界面
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/sm/cluster/overview")
public class OverViewController extends BaseController {
	@Autowired
	private ClusterService clusterService;
	
	/**
	 * 获取Hadoop节点
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/hadoopNodes", produces="application/json")
	@ResponseBody
	public Object hadoopNodes() throws IOException{
		List<ServerNode> nodes = new ArrayList<ServerNode>();
		ServerNode nnNode = new ServerNode();
		String nn = application.getParameter("CLUSTER_HADOOP_MASTER");		
		nnNode.setUrl("resources/images/server_live.png");
		nnNode.setHost(nn);
		nnNode.setName("namenode");
		nodes.add(nnNode);
		System.setProperty("HADOOP_USER_NAME", "root");
        InetSocketAddress namenodeAddr = new InetSocketAddress(nn.substring(0,nn.indexOf(":")),Integer.valueOf(nn.substring(nn.indexOf(":")+1)));
        Configuration conf = new Configuration();
        DFSClient client = new DFSClient(namenodeAddr, conf);
        ClientProtocol namenode = client.getNamenode();
        DatanodeInfo[] datanodeReport = namenode.getDatanodeReport(
                DatanodeReportType.ALL);
        int index=0;
        for (DatanodeInfo di : datanodeReport) {
        	ServerNode dNode = new ServerNode();
        	dNode.setName("datanode"+index++);
        	dNode.setHost(di.getName());
            dNode.setUrl("resources/images/server_live.png");  
            nodes.add(dNode);
        }
		return nodes;
	}
	
	/**
	 * 获取Solr节点
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/solrNodes", produces="application/json")
	@ResponseBody
	public Object solrNodes() throws IOException{
		List<ServerNode> nodes = new ArrayList<ServerNode>();
		String culster = application.getParameter("CLUSTER_SOLR");	
		String [] hosts = culster.split(",");
		int index=0;
		for(String h:hosts){
			ServerNode node = new ServerNode();
			node.setName("solrcloud"+index++);
			node.setHost(h);
			node.setUrl("resources/images/server_live.png");
			nodes.add(node);
		}
		return nodes;
	}
	
	/**
	 * 获取tomcat节点
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/tomcatNodes", produces="application/json")
	@ResponseBody
	public Object tomcatNodes() throws IOException{
		List<ServerNode> nodes = new ArrayList<ServerNode>();
		String culster = application.getParameter("CLUSTER_TOMCAT");	
		String [] hosts = culster.split(",");
		int index=0;
		for(String h:hosts){
			ServerNode node = new ServerNode();
			node.setName("tomcat"+index++);
			node.setHost(h);
			node.setUrl("resources/images/server_live.png");
			nodes.add(node);
		}
		return nodes;
	}
	
	/**
	 * 获取nginx节点
	 * @return
	 */
	@RequestMapping(value="/nginxNodes", produces="application/json")
	@ResponseBody
	public Object nginxNodes()  {
		List<ServerNode> nodes = new ArrayList<ServerNode>();
		String culster = application.getParameter("CLUSTER_NGINX");	
		String [] hosts = culster.split(",");
		int index=0;
		for(String h:hosts){
			ServerNode nginx = new ServerNode();
			nginx.setName("nginx"+index++);
			nginx.setHost(h);
			nginx.setUrl("resources/images/server_live.png");
			nodes.add(nginx);
		}
		return nodes;
	}
	
	/**
	 * 获取节点状态的信息
	 * @param name 节点名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/nodeStatus/{name}", produces="application/json")
	@ResponseBody
	public Object getNodeStatus(@PathVariable String name) throws Exception{
		Object[][] o = new Object[10][2];
		Map result = new HashMap();
		int index;
		if(name.startsWith("namenode")){
			o = clusterService.getHadoopNameNodeStatus();        
		}else if (name.startsWith("datanode")) {
			index = Integer.valueOf(name.substring("datanode".length()));
			o = clusterService.getHadoopDataNodeStatus(index);
		}else if (name.startsWith("solrcloud")) {
			index = Integer.valueOf(name.substring("solrcloud".length()));
			o = clusterService.getSolrNodeStatus(index);
		}else if (name.startsWith("tomcat")) {
			index = Integer.valueOf(name.substring("tomcat".length()));
			o = clusterService.getTomcatNodeStatus(index);
		}else if (name.startsWith("nginx")) {
			index = Integer.valueOf(name.substring("nginx".length()));
			o = clusterService.getNginxNodeStatus(index);
		}
		result.put("data", o);
		result.put("totalCount", o.length);
		return result;
	}
	
}
