package csdc.action.statistic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.service.IBaseService;

/**
 * 匹配算法的警告统计
 * @author jtf
 *
 */
public class WarnStatisticAction extends ActionSupport {
	private static final String HQL_GENERAL = "select p.warningReviewer, COUNT(*) from ProjectApplication p where  p.type = 'general' and p.year = :defaultYear and p.warningReviewer is not null group by p.warningReviewer";
	private static final String HQL_INSTP   = "select p.warningReviewer, COUNT(*) from ProjectApplication p where  p.type = 'instp' and p.year = :defaultYear and p.warningReviewer is not null group by p.warningReviewer";
	@Autowired
	private IBaseService baseService;
	private Integer listType;//1表示一般项目，2表示基地项目

	/**
	 * 警告统计页面
	 * @return
	 */
	public String view(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map session = ActionContext.getContext().getSession();
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", session.get("defaultYear"));
		long wnall = 0; //警告
		String hqli = null;
		if(listType==1){
			hqli = HQL_GENERAL;
		}else {
			hqli = HQL_INSTP;
		}
		List<Object> num = baseService.query(hqli, paraMap);
		if (num != null && !num.isEmpty()) {
			Object[] o;
			for (int i = 0; i < num.size(); i++) {
				o = (Object[]) num.get(i);
				if (o[0] != null && o[1] != null) {
					wnall += Integer.parseInt(o[1].toString());
				}
			}
		}
		request.setAttribute("wnall", wnall);//警告总条数
		request.setAttribute("num", num);//警告信息
		return SUCCESS;
	}
	
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Integer getListType() {
		return listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}
	
}
