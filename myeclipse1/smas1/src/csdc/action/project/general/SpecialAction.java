package csdc.action.project.general;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.SystemOption;


public class SpecialAction extends GeneralAction{

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select p.id, p.projectName, p.director, u.name, p.projectType, p.topic, p.discipline from ProjectApplication p, University u where p.type = 'general' and p.year = :defaultYear and p.universityCode = u.code and p.projectType = '专项任务' ";
	private static final String PAGENAME = "generalSpecialPage";
	private static final String PAGE_BUFFER_ID = "p.id";//缓存id
	private static final String column[] = {
		"p.projectName",
		"p.director",
		"u.name",
		"p.projectType",
		"p.topic",
		"p.discipline"
	};
	
	public Object[] simpleSearchCondition(){
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and p.isReviewable = :isReviewable");
		map.put("isReviewable", isReviewable);
		map.put("defaultYear", session.get("defaultYear"));
		
		String s_hql = "";// 检索条件
		if (!keyword.isEmpty()) {
			hql.append(" and ");
			if (searchType == 1) {
				s_hql = "LOWER(p.projectName) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				s_hql = "LOWER(p.director) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				s_hql = "LOWER(u.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			}else if (searchType == 4) {
				s_hql = "LOWER(p.topic) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			}else if (searchType == 5) {
				s_hql = "LOWER(p.discipline) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 6) {// 按学科名称搜索
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += "LOWER(p.discipline) like '%" + dislist.get(i) + "%' or ";
					}
					s_hql = s_hql.substring(0, s_hql.length() - 4);
					s_hql = "(" + s_hql + ")";
				} else {
					s_hql = "LOWER(p.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
			} else {
				s_hql = "(LOWER(p.projectName) like :keyword or LOWER(p.director) like :keyword or LOWER(u.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += " or LOWER(p.discipline) like '%" + dislist.get(i) + "%'";
					}
				} else {
					s_hql += " or LOWER(p.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
				s_hql += ")";
			}
		}
		hql.append(s_hql);
		
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			3,
			null,
			null
		};
	}
	
	/**
	 * 处理列表数据，如有需要，各模块可以重写此方法，本方法默认仅格式化时间。
	 */
	@Transactional
	public void pageListDealWith() {
		Map application = ActionContext.getContext().getApplication();
		//获取学科名称与学科代码的映射
		List disList = (List) application.get("disall");//获取所有学科对象的list
		Map<String, String> disname2discode = new HashMap<String, String>();//学科名称与学科代码的映射
		for (Iterator iterator = disList.iterator(); iterator.hasNext();) {
			SystemOption so = (SystemOption) iterator.next();
			String disname = so.getName();
			String discode = so.getCode();
			disname2discode.put(discode, disname);
		}
		
		laData = new ArrayList();// 处理之后的列表数据
		Object[] item;// 缓存查询结果一行
		
		Date begin = new Date();
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			item = (Object[]) p;
			
			String disciplineCode = "";// 项目学科信息
			if (item[6] != null) {
				disciplineCode = item[6].toString();
				disciplineCode = generalService.getDisciplineInfo(disname2discode, disciplineCode);
				item[6] = disciplineCode;
			}
			
			laData.add(item);// 将处理好的数据存入laData
		}
		Date end = new Date();
		System.out.println("组装数据耗时：" + (end.getTime() - begin.getTime()) + "ms");
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
	public String pageBufferId() {
		return SpecialAction.PAGE_BUFFER_ID;
	}
}
