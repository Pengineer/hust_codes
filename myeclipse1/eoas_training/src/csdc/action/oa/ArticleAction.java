package csdc.action.oa;

import csdc.bean.Article;
import csdc.bean.Account;
import csdc.service.IBaseService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class ArticleAction extends ActionSupport {
	private IBaseService baseService;
	private Article article;
	private Account account;
	private String type;
	private Map jsonMap = new HashMap();
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Article> articleList = new ArrayList<Article>();
		List<Object[]> aList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		try {
			articleList = (ArrayList<Article>) baseService.list(Article.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Article article : articleList) {
			Account account = (Account) baseService.load(Account.class, article.getAccount().getId());
			item = new String[5];
			item[0] = article.getTitle();
			item[1] = account.getEmail();
			item[2] = article.getViewCount() + "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null != article.getCreatedDate()) {
				item[3] = sdf.format(article.getCreatedDate());
			}
			item[4] = article.getId();
			aList.add(item);
		}
		jsonMap.put("aaData", aList);
		return 	SUCCESS;
	}
	
	public String toAdd() {
		Map session = ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public  String add() {
		Map session = ActionContext.getContext().getSession();
		Account account = (Account) session.get("account");
		Article article = new Article();
		article.setAccount(account);
		try {
			baseService.add(article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}