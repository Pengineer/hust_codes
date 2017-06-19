package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.News;
import csdc.bean.Role;
import csdc.service.IBaseService;

public class NewsAction extends ActionSupport {
	private IBaseService baseService;
	private News news;
	private Account account;
	private String newsId;
	private Map jsonMap = new HashMap();
	private Map map = new HashMap();
	
	
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<News> newsList = new ArrayList<News>();
		List<Object[]> nList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		try {
			newsList = (ArrayList<News>) baseService.list(News.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (News n : newsList) {
			Account account = (Account) baseService.load(Account.class, n.getAccount().getId());
			item = new String[5];
			item[0] = n.getTitle();
			item[1] = account.getEmail();
			item[2] = n.getViewCount() + "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null != n.getCreateDate()) {
				item[3] = sdf.format(n.getCreateDate());
			}
			item[4] = n.getId();
			nList.add(item);
		}
		jsonMap.put("aaData", nList);
		return 	SUCCESS;
	}
	
	public String toAdd() {
		Map session =  ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		return SUCCESS;
	}
	
	public String add() {
		Map map = ActionContext.getContext().getSession();
		account = (Account) map.get("account");
		try {
			baseService.add(news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String delete() {
		baseService.delete(News.class, newsId);
		return SUCCESS;
	}
	
	public String toModify() {
		news = (News) baseService.load(News.class, newsId);
		return SUCCESS;
	}
	
	public String modify() {
		
		
		try {
			news.setId(newsId);
			baseService.modify(news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String view() {
		news = (News) baseService.load(News.class, newsId);
		account = (Account) baseService.load(Account.class, news.getAccount().getId());
		return SUCCESS;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}