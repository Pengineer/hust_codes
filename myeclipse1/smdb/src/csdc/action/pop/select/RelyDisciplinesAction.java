package csdc.action.pop.select;

import csdc.service.ext.IUnitExtService;

public class RelyDisciplinesAction extends BaseAction{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private IUnitExtService unitExtService;
	private static final String PAGE_BUFFER_ID = "a.id";// 上下条查看时用于查找缓存的字段

	public String toSelect(){
		return SUCCESS;
	}

	public IUnitExtService getUnitExtService() {
		return unitExtService;
	}

	public void setUnitExtService(IUnitExtService unitExtService) {
		this.unitExtService = unitExtService;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public String[] column() {
		return null;
	}

	@Override
	public String dateFormat() {
		return null;
	}

	@Override
	public String pageName() {
		return null;
	}

	@Override
	public String simpleSearch() {
		return null;
	}
	public String pageBufferId() {
		return RelyDisciplinesAction.PAGE_BUFFER_ID;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}

}
