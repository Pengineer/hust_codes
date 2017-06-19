package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.SqlBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修复专家专业职称
 * @author xuhan
 *
 */
@Component
public class FixExpertSpecialtyTitle extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private SqlBaseDao sqlDao;
	
	private Map<String, String> titleTrans = new HashMap<String, String>();
	
	{
		titleTrans.put("1A 教授", "教授");
		titleTrans.put("研究员（自然科学）", "研究员");
		titleTrans.put("2A副教授", "副教授");
		titleTrans.put("1A", "教授");
		titleTrans.put("主任医师，教授", "教授");
		titleTrans.put("1A教授", "教授");
		titleTrans.put("研究馆员（图书）", "研究馆员");
		titleTrans.put("男", "");
		titleTrans.put("高级编辑", "编辑");
		titleTrans.put("研究员/教授", "教授");
		titleTrans.put("长江学者特聘教授", "教授");
		titleTrans.put("教授1A", "教授");
		titleTrans.put("正高级工程师", "高级工程师");
		titleTrans.put("教授博导", "教授");
		titleTrans.put("教援", "教授");
		titleTrans.put("教授、高级编辑", "教授");
		titleTrans.put("教授、博导", "教授");
		titleTrans.put("档案研究馆员", "研究馆员");
		titleTrans.put("四级教授", "教授");
		titleTrans.put("教授、一级编剧", "教授");
		titleTrans.put("教授/编审", "教授");
		titleTrans.put("教授、编审、文学创作一级", "教授");
		titleTrans.put("教受", "教授");
		titleTrans.put("1212", "");
	}

	
	@Override
	public void work() throws Throwable {
		sqlDao.execute("update t_expert set c_specialty_title = '研究员' where (c_department like '%研究所' or c_department like '%研究院' or c_department like '%研究中心') and c_specialty_title in ('正高级', '正高', '高级', '副高级', '中级')");
		sqlDao.execute("update t_expert set c_specialty_title = '教授' where c_specialty_title in ('正高级', '正高', '高级', '副高级', '中级')");
		
		List<Expert> experts = dao.query("from Expert");
		for (Expert expert : experts) {
			String title = expert.getSpecialityTitle();
			if (title != null && titleTrans.containsKey(title)) {
				expert.setSpecialityTitle(titleTrans.get(title));
			}
		}
		
	}
	
}
