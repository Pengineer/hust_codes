package csdc.action.statistic.review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.SystemOption;
import csdc.service.IProjectService;
import csdc.service.imp.ProjectService;


/**
 * 统计参评专家的统计信息
 * ，前台调用：
 * 传递：toList.action?update=1 返回jsp
 *       list，                                返回json
 */

/**
 * “评审专家->专家跨类参评项目数”
 * @author zhangnan
 * 2014-11-4
 */
public class ExpertReviewerStatisticAction extends ReviewStatisticAction {
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式ProjectApplicationReview
	//当前年参评专家信息统计
	private static final String HQL4ReveStic = " select e.id, e.name, e.rating, e.discipline, e.isReviewer, u.name, u.code, count(distinct pr.project.id) from Expert e, University u, ProjectApplicationReview pr where u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear ";
	private static final String HQLG = " group by e.id, e.name, u.name, e.rating, e.discipline, e.isReviewer, u.code ";
	private static final String PAGENAME = "exptRevewSticPage";
	private static final String PAGE_BUFFER_ID = "e.id";//缓存id
	private static final String column[] = {
		"e.name",
		"u.name",
		"e.rating",
		"count(distinct pr.project.id)",
		"e.discipline",
		"e.isReviewer"
	};// 排序用的列
	private boolean keyword1, keyword2;// 用于高级检索（选用两个条件）keyword1:选中general,keyword2:选中inspt
	IProjectService projectService;
	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		
		hql.append(HQL4ReveStic);
//		initConfig();//为各评审参数赋值
		if(universityCode == null ) {
			universityCode = "";
		}
		//session.put("listType", listType);
		session.put("universityCode", universityCode);
		if(universityCode != null && !universityCode.isEmpty()) {
			hql.append(" and e.universityCode = '" + universityCode + "' ");
		}
		
		String s_hql = "";// 初级检索条件
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
				List<Object> dislist = projectService.getDisciplineCode(keyword);
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
				List<Object> dislist = projectService.getDisciplineCode(keyword);
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
	//高级检索
	//如果选中keyword1 ： 搜索结果为 评审一般项目非空>=1的专家
	//如果选中keyword2 ： 搜索结果为 评审基地项目非空>=1的专家
	//都选中，以上结果都有
	//什么都不选，则全部搜索出来，不进行任何限制
	@Override
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		hql.append(HQL4ReveStic);
		String adv_hql = "";// 初级检索条件
		if (keyword1  && keyword2) {
			//一般项目和基地项目都参评的评审专家
			//获取专家的不同项目类型评审项目信息，选出两个项目都有的专家id
			Integer defaultYear = (Integer) session.get("defaultYear");//从session中获取当前显示年份
			Map stasticMap = projectService.getReviewersProjectsInfo(defaultYear);
			Map reviewer2GeneralProjectCnt = (Map) stasticMap.get("general");
			Map reviewer2instpProjectCnt = (Map) stasticMap.get("instp");
			List<String> hasBothPojEID = new ArrayList<String>();
			Set idShortSet = null;
			int shortcnt = reviewer2instpProjectCnt.size() < reviewer2GeneralProjectCnt.size() ? reviewer2instpProjectCnt.size() : reviewer2GeneralProjectCnt.size();
			if (shortcnt == reviewer2instpProjectCnt.size()) {
				idShortSet = reviewer2instpProjectCnt.keySet();
			} else if(shortcnt == reviewer2GeneralProjectCnt.size()) {
				idShortSet = reviewer2GeneralProjectCnt.keySet();
			}
			List idShortList = new ArrayList<String>(idShortSet);
			for (int k = 0; k < shortcnt; k++) {
				String expertId = (String) idShortList.get(k);
				if (reviewer2GeneralProjectCnt.containsKey(expertId) 
						&& reviewer2instpProjectCnt.containsKey(expertId)) {
					hasBothPojEID.add(expertId);
				}
			}
			//过滤
			adv_hql = " and e.id in (:hasBothID)";
			map.put("hasBothID", hasBothPojEID);
		} else if (keyword1 && !keyword2) {
			//只含有一般项目的评审专家
			adv_hql = " and pr.type = 'general'";
		} else if (keyword2 && !keyword1) {
			//只含有基地项目评审专家
			adv_hql = " and pr.type = 'instp'";
		} else {
			
		}
		
		hql.append(adv_hql);
		hql.append(HQLG);
		return new Object[]{
				hql.toString(),
				map,
				0,
				null,
				null
			};
	}
	
	/**
	 * 处理列表数据，重写此方法<br>
	 * 在列表项末尾添加：Generl instp项目评审数量
	 */
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
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
		Object[] o;// 缓存查询结果的一行
		Object[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		//获取专家的不同项目类型评审项目信息
		Integer defaultYear = (Integer) session.get("defaultYear");//从session中获取当前显示年份
		Map stasticMap = projectService.getReviewersProjectsInfo(defaultYear);
		Map reviewer2GeneralProjectCnt = (Map) stasticMap.get("general");
		Map reviewer2instpProjectCnt = (Map) stasticMap.get("instp");
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		//学科代码
		String disciplineCode = "";
		//组装后的值：学科代码+学科名称
		String value = "";
		for (Object p : pageList) {
			o = (Object[]) p;
			int itemLength = o.length;
			item = new Object[itemLength + 2];//添加评审项目类别（general， instp），分别添加到末尾
			//处理原来数据字段
			for (int i = 0; i < itemLength; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						item[i] = dateformat.format((Date) o[i]);
					} else {// 如果字段非时间对象，则直接转化为字符串
						item[i] = o[i].toString();
					}
				}
			}
			if (item[3] != null) {
				disciplineCode = item[3].toString();//学科代码
				value = projectService.getDisciplineInfo(disname2discode, disciplineCode);
			}
			item[3] = value;
			if(item[4].equals("1")) {
				item[4] = "是";
			} else if (item[4].equals("0")) {
				item[4] = "否";
			}
			//在末尾添加新字段
			String expertid = (String) o[0];//取出专家id
			//添加一般项目评审数
			if (!reviewer2GeneralProjectCnt.isEmpty() && reviewer2GeneralProjectCnt.get(expertid) != null) {
				item[itemLength] = reviewer2GeneralProjectCnt.get(expertid);
			} else {
				item[itemLength] = "0";
			}
			//添加基地项目评审数
			if (!reviewer2instpProjectCnt.isEmpty() && reviewer2instpProjectCnt.get(expertid) != null) {
				item[itemLength + 1] = reviewer2instpProjectCnt.get(expertid);
			} else {
				item[itemLength + 1] = "0";
			}
			
			laData.add(item);// 将处理好的数据存入laData，最终结果列表数据
		}
	}

	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
	
	public String pageName() {
		return PAGENAME;
	}

	@Override
	public String[] column() {
		return column;
	}

	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public void setKeyword1(boolean keyword1) {
		this.keyword1 = keyword1;
	}
	public void setKeyword2(boolean keyword2) {
		this.keyword2 = keyword2;
	}
	public boolean isKeyword1() {
		return keyword1;
	}
	public boolean isKeyword2() {
		return keyword2;
	}
}
