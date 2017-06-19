package csdc.action.pop.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;

/**
 * 弹层--选择索引类别
 * @author 余潜玉
 */
public class IndexTypeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	@Autowired
	protected HibernateBaseDao dao;
	@SuppressWarnings("unchecked")
	public List indexTypeList;
	private IBaseService baseService;

	public String toSelect(){
		indexTypeList = dao.query("select so.id, so.name from SystemOption so where so.systemOption.name='索引类型' and so.isAvailable=1 order by so.code ");
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public List getIndexTypeList() {
		return indexTypeList;
	}
	@SuppressWarnings("unchecked")
	public void setIndexTypeList(List indexTypeList) {
		this.indexTypeList = indexTypeList;
	}
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}