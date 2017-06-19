package csdc.action.dm.universityVariation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.UniversityVariation;
import csdc.tool.info.GlobalInfo;

/**
 * 高校更名类
 * 该类没有什么特别的操作，主要是记录高校更名信息，方便数据入库时查询。
 * @author wangyi
 */
public class UniversityVariationAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select ur.id, ur.nameOld, ur.codeOld, ur.nameNew, ur.codeNew, ur.type, ur.date, ur.variationDate from UniversityVariation ur where 1 = 1";
	private final static String[] COLUMN = new String[]{
		"ur.nameOld",
		"ur.codeOld",
		"ur.nameNew",
		"ur.codeNew",
		"ur.type",
		"ur.date",
		"ur.variationDate"
	};

	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String TMP_ENTITY_ID = "universityVariationId";// 用于session缓存实体的ID名称
	
	private UniversityVariation universityVariation;
	
	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView() {
		return SUCCESS;
	}
	
	/**
	 * 查看详细信息
	 * @return
	 */
	public String view(){
		universityVariation = (UniversityVariation) dao.query(UniversityVariation.class, entityId.trim());
		if (universityVariation == null) { 
			jsonMap.put(GlobalInfo.ERROR_INFO, "找不到对应的高校更名！");
			return INPUT;
		} else {
			jsonMap.put("universityVariation", universityVariation);
			return SUCCESS;
		}
	}
	
	/**
	 * 进入添加
	 */
	public String toAdd() {
		return SUCCESS;
	}
	
	/**
	 * 添加高校更名
	 */
	public String add() {
		universityVariation.setDate(new Date());
		entityId = dao.add(universityVariation);
		
		return SUCCESS;
	}
	
	/**
	 * 删除高校更名
	 */
	public String delete() {
		for (String entityId : entityIds) {
			dao.delete(UniversityVariation.class, entityId);
		}
		return SUCCESS;
	}
	
	/**
	 * 进入修改
	 */
	public String toModify() {
		universityVariation = (UniversityVariation) dao.query(UniversityVariation.class, entityId);
		if (universityVariation == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "找不到对应的高校更名！");
			return INPUT;
		} else {
			ActionContext.getContext().getSession().put(TMP_ENTITY_ID, entityId);
			return SUCCESS;
		}
	}
	
	/**
	 * 修改高校更名
	 */
	public String modify() {
		entityId = (String) ActionContext.getContext().getSession().get(TMP_ENTITY_ID);// 获取备用修改ID
		dao.delete(UniversityVariation.class, entityId);
		universityVariation.setDate(new Date());
		entityId = dao.add(universityVariation);
		ActionContext.getContext().getSession().remove("entityId");
		return SUCCESS;
	}
	
	@Override
	public String pageName() {
		return "universityVariationPage";
	}
	@Override
	public String[] column() {
		return COLUMN;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
		
		if (keyword != null && !keyword.trim().isEmpty()){
			//处理查询条件
			if (searchType == 0) {
				hql.append("and LOWER(ur.nameOld) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			} else if (searchType == 1) {
				hql.append("and LOWER(ur.codeOld) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			} else if (searchType == 2) {
				hql.append("and LOWER(ur.nameNew) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			} else if (searchType == 3) {
				hql.append("and LOWER(ur.codeNew) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			} else if (searchType == 4) {
				int key=0;
				if("更名".indexOf(keyword) >= 0 && "合并".indexOf(keyword) >= 0){
					
				} else if ("更名".indexOf(keyword) >= 0) {
					key = 1;
					hql.append("and ur.type = :keyword");
					map.put("keyword", key);
				} else if ("合并".indexOf(keyword) >= 0) {
					key = 2;
					hql.append("and ur.type = :keyword");
					map.put("keyword", key);
				} else {
					key = -1;
					hql.append("and ur.type = :keyword");
					map.put("keyword", key);
				}
			} else {
				hql.append(" and (LOWER(ur.nameOld) like :keyword or LOWER(ur.codeOld) like :keyword or LOWER(ur.nameNew) like :keyword or LOWER(ur.codeNew) like :keyword");
				map.put("keyword", '%'+keyword+'%');
				int key=0;
				if ("更名".indexOf(keyword) >= 0 && "合并".indexOf(keyword) >= 0) {
					hql.append(" or 1=1)");
				} else if ("更名".indexOf(keyword) >= 0) {
					key = 1;
					hql.append(" or ur.type = :keyword2)");
					map.put("keyword2", key);
				} else if ( "合并".indexOf(keyword) >= 0) {
					key = 2;
					hql.append(" or ur.type = :keyword2)");
					map.put("keyword2", key);
				} else {
					hql.append(")");
				}
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return "ur.id";
	}

	public UniversityVariation getUniversityVariation() {
		return universityVariation;
	}

	public void setUniversityVariation(UniversityVariation universityVariation) {
		this.universityVariation = universityVariation;
	}
	
}
