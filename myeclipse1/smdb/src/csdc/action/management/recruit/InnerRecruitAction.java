package csdc.action.management.recruit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.service.IManagementService;
import csdc.service.IUploadService;

/**
 * 内部管理系统的招聘相关处理的基类
 * @author suwb
 *
 */
public abstract class InnerRecruitAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	protected IManagementService managementService;
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected List<String[]> templateList;//模板列表
	protected String selectedTemplateIds;//选择的模板id
	
	protected String filePath;//附件路径
	
	protected int verifyResult;//审核结果[1：审核通过；2：审核不通过]
	
	protected String name;//职位名称
	protected int number;//招聘人数
	protected String degree;//学位要求
	protected String major;//专业要求
	protected String age;//年龄要求
	protected String requirement;//详细要求
	protected Date publishDate;//发布时间
	protected Date endDate;//截止时间
	
	protected String description;//模板描述
	protected String templateName;//模板文件名

	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	
	public abstract String HQL();
	public abstract String pageName();
	public abstract String[] searchConditions();
	public abstract String[] column();
	public abstract void simpleSearchBaseHql(StringBuffer hql,Map map);
	
	//附件下载
	public String download(){
		return SUCCESS;
	}
		
	public String dateFormat() {
		return InnerRecruitAction.DATE_FORMAT;
	}
	
	//初级检索条件
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();

		simpleSearchBaseHql(hql,map);
		if (keyword != null && !keyword.trim().isEmpty()){
			// 处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = searchConditions();
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
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	public List<String[]> getTemplateList() {
		return templateList;
	}
	public void setTemplateList(List<String[]> templateList) {
		this.templateList = templateList;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getVerifyResult() {
		return verifyResult;
	}
	public void setVerifyResult(int verifyResult) {
		this.verifyResult = verifyResult;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getSelectedTemplateIds() {
		return selectedTemplateIds;
	}
	public void setSelectedTemplateIds(String selectedTemplateIds) {
		this.selectedTemplateIds = selectedTemplateIds;
	}
}
