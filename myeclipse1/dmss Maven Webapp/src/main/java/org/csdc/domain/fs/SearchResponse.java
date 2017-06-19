package org.csdc.domain.fs;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * 全文检索 标准json返回数据
 * @author jintf
 * @date 2014-6-15
 */
public class SearchResponse {
	private Map highlighting = new HashMap();
	private Map response = new HashMap();;
	private Map responseHeader = new HashMap();;
	
	public SearchResponse(QueryResponse query){
		highlighting = query.getHighlighting();
		response.put("numFound", query.getResults().getNumFound());
		response.put("docs", query.getResults());
		response.put("start", query.getResults().getStart());
		responseHeader.put("QTime", query.getQTime());
		responseHeader.put("status", query.getStatus());
		
		responseHeader.put("params", query.getResponseHeader().get("params"));
	}

	public Map getHighlighting() {
		return highlighting;
	}

	public void setHighlighting(Map highlighting) {
		this.highlighting = highlighting;
	}

	public Map getResponse() {
		return response;
	}

	public void setResponse(Map response) {
		this.response = response;
	}

	public Map getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(Map responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	
}
