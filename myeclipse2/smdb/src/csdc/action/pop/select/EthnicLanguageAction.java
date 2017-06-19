package csdc.action.pop.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;

public class EthnicLanguageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	@Autowired
	protected HibernateBaseDao dao;
	@SuppressWarnings("unchecked")
	public List ethnicLanguageList;
	private IBaseService baseService;

	public String toSelect(){
		ethnicLanguageList = dao.query("select so from SystemOption pso left join pso.systemOptions so where pso.standard = 'GBT4881-1985' and pso.code is null order by so.name asc ");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public List getEthnicLanguageList() {
		return ethnicLanguageList;
	}

	@SuppressWarnings("unchecked")
	public void setEthnicLanguageList(List ethnicLanguageList) {
		this.ethnicLanguageList = ethnicLanguageList;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}