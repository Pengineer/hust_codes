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
import csdc.bean.Person;
import csdc.bean.Solution;
import csdc.service.IBaseService;
import csdc.tool.DatetimeTool;

public class SolutionAction extends ActionSupport {
	private IBaseService baseService;
	private Solution solution;
	private Account account;
	private String solutionId;
	
	private Map jsonMap = new HashMap();
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Solution> solutionList = new ArrayList<Solution>();
		List<Object[]> sList = new ArrayList<Object[]>();
		try {
			solutionList = (ArrayList<Solution>) baseService.list(Solution.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Solution s : solutionList) {
			item = new String[3];
			item[0] = s.getTitle();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			item[1] = df.format(s.getCreateDate());
			item[2] = s.getId();
			sList.add(item);
		}
		jsonMap.put("aaData", sList);
		return SUCCESS;
	}
	
	public String toAdd() {
		ActionContext context = ActionContext.getContext();
//		sessionId = session.getId();

		//PropertiesLoader.setProperty("connector.userFilesPath", baseDir + "/" + DatetimeTool.getYearMonthString());
		//PropertiesLoader.setProperty("connector.userFilesAbsolutePath", baseDir + "/" + DatetimeTool.getYearMonthString());
		context.getSession().put("FilePath", "/upload/solution" + "/" + DatetimeTool.getYearMonthString());
		System.out.println(context.getSession().get("FilePath"));
		solution = new Solution(); 
		
		return SUCCESS;
	}
	
	public String add() {
		Map session = ActionContext.getContext().getSession();
		account = ((Account) session.get("account"));
		this.solution.setAccount(account);
		solution.setViewCount(0);
		solution.setCreateDate(new Date());
		try {
			baseService.add(solution);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String toModify() {
		ActionContext context = ActionContext.getContext();
//		sessionId = session.getId();

		//PropertiesLoader.setProperty("connector.userFilesPath", baseDir + "/" + DatetimeTool.getYearMonthString());
		//PropertiesLoader.setProperty("connector.userFilesAbsolutePath", baseDir + "/" + DatetimeTool.getYearMonthString());
		context.getSession().put("FilePath", "/upload/solution" + "/" + DatetimeTool.getYearMonthString());
		System.out.println(context.getSession().get("FilePath"));
		
		try {
			solution = (Solution) baseService.load(Solution.class, solutionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String modify() {
		Solution s = (Solution) baseService.load(Solution.class, solutionId);
		s.setCategory(solution.getCategory());
		s.setTitle(solution.getTitle());
		s.setContent(solution.getContent());
		try {
			baseService.modify(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String delete() {
		try {
			baseService.delete(Solution.class, solutionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String view() {
		solution = (Solution) baseService.load(Solution.class, solutionId);
		return SUCCESS;
	}
	
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
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

	public String getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(String solutionId) {
		this.solutionId = solutionId;
	}
	
}