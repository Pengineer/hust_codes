package csdc.action.pop.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;


import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;

/**
 * 弹层--学科门类
 * @author 龚凡
 */
public class DisciplineTypeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	protected HibernateBaseDao dao;
	private static final String HQL = "select so.id, so.name from SystemOption so where so.systemOption.standard = 'disciplineType' and so.systemOption.code is null and so.isAvailable = 1";
	private IBaseService baseService;
	@SuppressWarnings("unchecked")
	private List dispTypeList;
	private int selectType;

	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	@SuppressWarnings("unchecked")
	public List getDispTypeList() {
		return dispTypeList;
	}
	@SuppressWarnings("unchecked")
	public void setDispTypeList(List dispTypeList) {
		this.dispTypeList = dispTypeList;
	}
	public int getSelectType() {
		return selectType;
	}
	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}
	/**
	 * 查询学科门类，条件变，排序不变
	 * @return
	 */
	public String simpleSearch() {
		dispTypeList = dao.query(HQL);
		return SUCCESS;
	}
}