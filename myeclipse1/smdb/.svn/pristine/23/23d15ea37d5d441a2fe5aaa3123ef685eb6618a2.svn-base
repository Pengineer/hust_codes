package csdc.action.system.query;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.dao.SqlBaseDao;

public class QueryAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private String queryString;
	
	private Integer firstResult;

	private Integer maxResults;
	
	private int type;
	
	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Autowired
	private SqlBaseDao sqlBaseDao;
	
	private Map result;
	
	private Long costTime;
	
	public String toQuery() {
		firstResult = 0;
		maxResults = 100;
		return SUCCESS;
	}
	
	public String query() throws Exception {
		long begin = System.currentTimeMillis();
		try {
			result = new HashMap();
			List data;
			maxResults = Math.min(100, maxResults);
			if (type == 0) {
				data = hibernateBaseDao.query(queryString, firstResult, maxResults);
			} else {
				data = sqlBaseDao.query(queryString, firstResult, maxResults);	
				for (Object row : data) {
					if (row instanceof Object[]) {
						Object[] arrRow = (Object[])row;
						for (int i = 0; i < arrRow.length; i++) {
							if (arrRow[i] != null) {
								arrRow[i] = arrRow[i].toString();
							}
						}
					} else if (row != null) {
						row = row.toString();
					}
				}
			}
			result.put("data", data);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			result.put("errorInfo", sw.toString());
			result.put("data", null);
		} finally {
			costTime = System.currentTimeMillis() - begin;
			result.put("costTime", costTime);
		}
		return SUCCESS;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}

	public Map getResult() {
		return result;
	}

	public void setResult(Map result) {
		this.result = result;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

	
}
