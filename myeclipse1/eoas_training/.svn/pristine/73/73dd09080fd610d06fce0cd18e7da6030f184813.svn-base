package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Notice;
import csdc.service.IBaseService;

public class NoticeAction extends ActionSupport {
	private IBaseService baseService;
	private Notice notice;
	private Account account;
	private String noticeId;
	private Map jsonMap = new HashMap();
	private Map map = new HashMap();
	
	
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Notice> noticeList = new ArrayList<Notice>();
		List<Object[]> nList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		try {
			noticeList = (ArrayList<Notice>) baseService.list(Notice.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Notice n : noticeList) {
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
		notice.setAccount(account);
		notice.setCreateDate(new Date());
		try {
			baseService.add(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String delete() {
		baseService.delete(Notice.class, noticeId);
		return SUCCESS;
	}
	
	public String toModify() {
		Map session =  ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		notice = (Notice) baseService.load(Notice.class, noticeId);
		return SUCCESS;
	}
	
	public String modify() {
		
		
		try {
			notice.setId(noticeId);
			baseService.modify(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String view() {
		notice = (Notice) baseService.load(Notice.class, noticeId);
		account = (Account) baseService.load(Account.class, notice.getAccount().getId());
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

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}