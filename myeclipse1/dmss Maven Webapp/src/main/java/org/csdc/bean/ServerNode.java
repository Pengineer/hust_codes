package org.csdc.bean;

/**
 * 集群节点
 * @author jintf
 * @date 2014-6-15
 */
public class ServerNode {
	private String name; //节点名
	private String host; //节点主机名
	private String url; //节点url
	private boolean live; //节点是否正常
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
}
