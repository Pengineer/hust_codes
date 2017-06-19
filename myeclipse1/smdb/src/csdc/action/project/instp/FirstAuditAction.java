package csdc.action.project.instp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import csdc.action.BaseAction;
import csdc.service.IInstpService;
import csdc.service.IProductService;
import csdc.service.IProjectService;
import csdc.tool.SpringBean;
import csdc.tool.execution.Execution;
import csdc.tool.info.GlobalInfo;

public class FirstAuditAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGE_BUFFER_ID = "app.id";//缓存id
	private static final String DATE_FORMAT = "yyyy-MM-dd";//列表页面时间格式
	private static final String PAGE_NAME = "instpApplicationFirstAuditPage";//列表页面名称
	private static final String HQL = "select app.id, app.name, app.discipline, u.name, app.applicantName, app.firstAuditDate, app.firstAuditResult " +
			"from InstpApplication app left join app.university u where app.firstAuditResult is not null ";//列表页面名称
	private static final String[] COLUMN = {
		"app.name",
		"app.discipline",
		"u.name",
		"app.applicantName",
		"app.firstAuditDate",
		"app.firstAuditResult"
	};
	
	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(app.name) like :keyword",
		"LOWER(app.type) like :keyword",
		"LOWER(app.discipline) like :keyword",
		"LOWER(app.applicantName) like :keyword",
		"LOWER(app.gender) like :keyword",
		"LOWER(app.national) like :keyword",
		"LOWER(to_char(app.birthday, 'yyyy-MM-dd')) like :keyword",
		"LOWER(app.unit) like :keyword",
		"LOWER(app.province) like :keyword",
		"LOWER(app.productName) like :keyword",
		"app.year = :year",
		"LOWER(app.firstAuditResult) like :keyword",
		"LOWER(to_char(app.firstAuditDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(u.name) like :keyword"
	};

	protected IProjectService projectService;//项目管理接口
	protected IProductService productService;//成果管理接口
	private IInstpService instpService;
	protected Integer year;//项目年度
	

	@Override
	public String pageName() {
		return this.PAGE_NAME;
	}

	@Override
	public String[] column() {
		return this.COLUMN;
	};

	@Override
	public String dateFormat() {
		return this.DATE_FORMAT;
	}

	@Override
	public Object[] simpleSearchCondition() {
		Map map = new HashMap();
		StringBuffer hql = new StringBuffer();
		int columnLabel = 0;
		hql.append(HQL);
		if (year != 0 && year != null) {
			hql.append("and app.year = " + year );
		}
		if (keyword != null && !keyword.trim().isEmpty()) {
			//处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = SEARCH_CONDITIONS;
			for (int i = 0; !flag && i < sc.length; i++) {
				if (searchType == i) {
					hql.append(" and ").append(sc[i]);
					flag = true;
				}
				tmp.append(sc[i]).append(i < sc.length - 1 ? " or " : ") ");
			}
			if (!flag) {
				hql.append(" and ").append(tmp);
			}
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
			map.put("year", keyword != null && keyword.matches("\\d{4}")? Integer.valueOf(keyword) : 0);
		}
		return new Object[]{
				hql.toString(),
				map,
				columnLabel,
				null
			};
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
	
	/**
	 * 进入初审页面
	 * @return
	 */
	public String toFirstAudit() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String y = sdf.format(new Date());
		year = Integer.parseInt(y);
		return SUCCESS;
	}
	
	/**
	 * 进行指定条件的初审操作
	 * @return
	 */
	@Transactional
	public String firstAudit() {
		try {
			String beanName = "firstAuditInstpApplication";
			((Execution)SpringBean.getBean(beanName)).excute();
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "查重执行失败！");
			throw new RuntimeException(e);
		} 
		return SUCCESS;
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IInstpService getInstpService() {
		return instpService;
	}

	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
	
}
