package csdc.action.pop.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;

public class ForeignLanguageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	@Autowired
	protected HibernateBaseDao dao;
	@SuppressWarnings("unchecked")
	public List foreignLanguageList;
	private IBaseService baseService;

	public String toSelect(){
		foreignLanguageList = dao.query("select so from SystemOption pso left join pso.systemOptions so where pso.standard = 'ISO 639-1' and pso.code is null order by so.abbr asc ");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List getForeignLanguageList() {
		return foreignLanguageList;
	}

	@SuppressWarnings("unchecked")
	public void setForeignLanguageList(List foreignLanguageList) {
		this.foreignLanguageList = foreignLanguageList;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}