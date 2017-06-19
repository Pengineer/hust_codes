package csdc.action.statistic.review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.SystemOption;
import csdc.service.IGeneralService;
import csdc.tool.tableKit.imp.ExpertKit;
import csdc.tool.tableKit.imp.UniversityKit;

/**
 * 专家参评项目数
 * @author fengcl
 *
 */
@SuppressWarnings("unchecked")
public class ExpertStatisticAction extends ReviewStatisticAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL4General = "select e.name, u.name, count(distinct pr.project.id), e.id, e.rating, u.code, e.discipline, e.isReviewer from University u, ProjectApplicationReview pr, Expert e where pr.type = 'general' and u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear ";
	private static final String HQL4INSTITUTE = "select e.name, u.name, count(distinct pr.project.id), e.id, e.rating, u.code, e.discipline, e.isReviewer from University u, ProjectApplicationReview pr, Expert e where pr.type = 'instp' and  u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear ";
	private static final String HQLG = " group by e.name, u.name,e.discipline, e.id, e.rating, u.code, e.isReviewer ";
	private static final String PAGENAME = "statisticEPage";
	private static final String PAGE_BUFFER_ID = "e.id";//缓存id
	private static final String column[] = {
		"e.name",
		"u.name",
		"e.rating",
		"count(distinct pr.project.id)",
		"e.discipline",
		"e.isReviewer"
	};// 排序用的列
	private IGeneralService generalService;
	@Autowired
	private ExpertKit expertKit;
	/**
	 * 专家统计列表 
	 */
	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		if(listType == 1){//一般项目
			hql.append(HQL4General);
		}else if(listType == 2){//基地项目
			hql.append(HQL4INSTITUTE);
		}
		if(universityCode == null ) {
			universityCode = "";
		}
		session.put("listType", listType);
		session.put("universityCode", universityCode);
		initConfig();//为各评审参数赋值
//		hql.append(isContainP == 0 ? " and pr.priority <= " + projectExpertNumber + " ": " ");
		if(universityCode != null && !universityCode.isEmpty()) {
			hql.append(" and e.universityCode = '" + universityCode + "' ");
		}
		String s_hql = "";// 检索条件
		if (keyword != null && !keyword.isEmpty()) {
			hql.append(" and ");
			if (searchType == 1) {
				s_hql = "LOWER(e.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				s_hql = "LOWER(u.name) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				s_hql = "LOWER(e.discipline) like :keyword";
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += "LOWER(e.discipline) like '%" + dislist.get(i) + "%' or ";
					}
					s_hql = s_hql.substring(0, s_hql.length() - 4);
					s_hql = "(" + s_hql + ")";
				} else {
					s_hql = "LOWER(e.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
			} else {
				s_hql = "(LOWER(e.name) like :keyword or LOWER(u.name) like :keyword or LOWER(e.discipline) like :keyword";
				map.put("keyword", "%" + keyword + "%");
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						s_hql += " or LOWER(e.discipline) like '%" + dislist.get(i) + "%'";
					}
				} else {
					s_hql += " or LOWER(e.discipline) like :keyword";// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
				s_hql += ")";
			}
		}
		hql.append(s_hql);
		hql.append(HQLG);
		
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
	 * 处理列表数据，如有需要，各模块可以重写此方法，本方法默认仅格式化时间。
	 */
	public void pageListDealWith() {
		//获取学科名称与学科代码的映射
		Map application = ActionContext.getContext().getApplication();
		List disList = (List) application.get("disall");//获取所有学科对象的list
		Map<String, String> disname2discode = new HashMap<String, String>();//学科名称与学科代码的映射
		for (Iterator iterator = disList.iterator(); iterator.hasNext();) {
			SystemOption so = (SystemOption) iterator.next();
			String disname = so.getName();
			String discode = so.getCode();
			disname2discode.put(discode, disname);
		}
		// 处理之后的列表数据
		laData = new ArrayList();
		Object[] item;// 缓存查询结果一行
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		//学科代码
		String disciplineCode = "";
		//组装后的值：学科代码+学科名称
		String value = "";
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			item = (Object[]) p;
			for (int i = 0; i < item.length; i++) {
				if (item[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (item[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) item[i]);
						item[i] = datestr;
					}
				}
			}
			if (item[6] != null) {
				disciplineCode = item[6].toString();//学科代码
				value = generalService.getDisciplineInfo(disname2discode, disciplineCode);
			}
			item[6] = value;
			if(item[7].equals(1)) {
				item[7] = "是";
			} else if (item[7].equals(0)) {
				item[7] = "否";
			}
			laData.add(item);// 将处理好的数据存入laData
		}
	}

	/**
	 * 导出专家参评项目数
	 * @return
	 * @throws Exception
	 */
	public String exportExpertStatistic() throws Exception {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		if(listType == 1){//一般项目
			hql.append(HQL4General);
		}else if(listType == 2){//基地项目
			hql.append(HQL4INSTITUTE);
		}
		if(universityCode != null && !universityCode.isEmpty()) {
			hql.append(" and e.universityCode = '" + universityCode + "' ");
		}		
		hql.append(HQLG);
		expertKit.exportExpertStatistic(hql.toString(),map);
		return SUCCESS;
	}
	
	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView(){
		return SUCCESS;
	}
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	
}

