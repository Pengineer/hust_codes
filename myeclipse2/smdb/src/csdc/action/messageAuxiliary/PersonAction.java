package csdc.action.messageAuxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.net.aso.p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.Passport;
import csdc.service.ILogService;

/**
 * 辅助信息平台，社科人员查询
 * @author yangfq
 *
 */

public class PersonAction extends MessageAuxiliaryBaseAction{
	
	@Autowired
	protected ILogService logService;
	
	protected int personType;// 人员类型
	protected Date startDate;
	protected Date endDate;
	protected Integer director;
	protected int lineNum; //显示行数,自定义的
	
	
	/**
	 * 进入到人员查询页面
	 */
	public String toPersonQuery(){
		return SUCCESS;
	}
	
	
	/**
	 * 查询
	 */
	public String personQuery(){
		List lTitle = new ArrayList();
		Map paraMap = new HashMap();
		Account account = null;
		List visitList = new ArrayList();
		String personId = assistService.getPersonId(entityId, personType);
		for (int data : query_data) {
			if (data == 1) {//访问记录(日志)
				if (personType == 1 || personType == 2 || personType == 3 || personType == 4 || personType == 5) {//管理人员的entityId为officerId
					account = assistService.getAccountByBelongId(entityId, 1);
				} else if (personType == 6) {//外部专家
					account = assistService.getAccountByBelongId(entityId, 2);
				} else if (personType == 7) {//教师
					account = assistService.getAccountByBelongId(entityId, 3);
				} else if (personType == 8) {//学生
					account = assistService.getAccountByBelongId(entityId, 4);
				}
				if (null != account) {
					if (showLineNum == 0) {//自定义条数
						visitList = messageAssistService.getLogHistory(account, startDate, endDate, 0);
					} else {
						visitList = messageAssistService.getLogHistory(account, startDate, endDate, showLineNum);
						jsonMap.put("visitList", visitList.size() == 0 ? null: visitList);
					}
					String name = (String) dao.query("select p.name from Account a left join a.passport p where a.id = ?", account.getId()).get(0);
					Map map = logService.statistic(startDate, endDate, 0, null, name);
					if (chartType != 3) {
						Iterator iter = map.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							jsonMap.put(entry.getKey(), entry.getValue());
						}
						lTitle.add("登录各服务器次数");
						lTitle.add("访问系统功能模块次数");
						lTitle.add("访问系统安全模块次数");
						lTitle.add("访问人员数据模块次数");
						lTitle.add("访问机构数据模块次数");
						lTitle.add("访问项目数据模块次数");
						lTitle.add("访问成果数据模块次数");
						lTitle.add("访问奖励数据模块次数");
						jsonMap.put("lTitle", lTitle);
					} else {
						Long[] numbers = (Long[]) map.get("numbers");
						Long number = (Long) map.get("number") == null ? 0 : (Long) map.get("number");
						jsonMap.put("number", number);
						jsonMap.put("numbers", numbers);
					}
				} else {
					jsonMap.put("visitList", null);
				}
			} else if (data == 2) {//项目情况
				Map map = assistService.projectStatistic(personId, director);
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					jsonMap.put(entry.getKey(), entry.getValue());
				} 
			} else if (data == 3) {//奖励
				List awardList  = assistService.getAward(personId);
				jsonMap.put("awardList", awardList.size() == 0 ? null : awardList);
			} else if (data == 4) {//成果
				Map map = assistService.getProduct(personId);
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					jsonMap.put(entry.getKey(), entry.getValue());
				} 
			}
		}
		jsonMap.put("chartType", chartType);
		query_parm = new ArrayList<String>();
		query_parm.add("人员类型：　" + assistService.getPersonType(personType));
		query_parm.add("查询数据：　" + assistService.getFormatedData(query_data));
		jsonMap.put("query_parm", query_parm);
		return SUCCESS;
	}
	

	public int getPersonType() {
		return personType;
	}

	public void setPersonType(int personType) {
		this.personType = personType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getDirector() {
		return director;
	}

	public void setDirector(Integer director) {
		this.director = director;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
}
