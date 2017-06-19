package csdc.action.project.general;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.tool.SpringBean;
import csdc.tool.execution.Execution;
import csdc.tool.info.GlobalInfo;
import csdc.tool.tableKit.imp.ProjectKit;

public class FirstAuditAction extends GeneralAction{

	private static final long serialVersionUID = -8579937731473815698L;

	private static final String HQL = "select p.id, p.projectName, p.projectType, p.director, p.universityName, p.firstAuditResult, TO_CHAR(p.firstAuditDate, 'yyyy-MM-dd') from ProjectApplication p where p.type = 'general' and p.firstAuditResult is not null and p.firstAuditDate is not null and p.year = :defaultYear ";
	private static final String PAGENAME = "generalFirstAuditPage";
	private static final String PAGE_BUFFER_ID = "p.id";//缓存id
	private static final String column[] = {
		"p.projectName",
		"p.projectType",
		"p.director",
		"p.universityName",
		"p.firstAuditDate",
		"p.id"
	};
	//导出
	@Autowired
	private ProjectKit ek;
	private String fileFileName;//导出文件名
	
	
	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));
		hql.append(HQL);
		if(!keyword.isEmpty()){
			hql.append(" and ");
			if (searchType == 1) {// 按项目名称检索
				hql.append(" LOWER(p.projectName) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {// 按项目类型检索
				hql.append(" LOWER(p.projectType) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {// 按负责人检索
				hql.append(" LOWER(p.director) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {// 按高校名称检索
				hql.append(" LOWER(p.universityName) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if(searchType == 5){//按查重情况检索
				hql.append(" LOWER(p.firstAuditResult) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else {// 按上述字段检索
				hql.append(" (LOWER(p.projectName) like :keyword or LOWER(p.projectType) like :keyword or LOWER(p.director) like :keyword or LOWER(p.universityName) like :keyword" +
						" or LOWER(p.firstAuditResult) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}
	
	/**
	 * 更新查重情况
	 * @return
	 */
	@Transactional
	public String updateFirstAudit(){
		try {
			String beanName = "firstAuditGeneralApplication";
			((Execution)SpringBean.getBean(beanName)).excute();
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "查重执行失败！");
			throw new RuntimeException(e);
		} 
		return SUCCESS;
	}
	
	/**
	 * 导出项目判重结果
	 */
	public String exportFirstAudit(){
		return SUCCESS;
	}
	
	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception{
		//导出的Excel文件名
		fileFileName = "教育部人文社会科学研究一般项目初审结果一览表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return ek.exportGeneralFirstAudit();
	}
	
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String pageBufferId() {
		return FirstAuditAction.PAGE_BUFFER_ID;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
