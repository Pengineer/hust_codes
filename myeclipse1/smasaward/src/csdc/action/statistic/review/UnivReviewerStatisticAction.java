package csdc.action.statistic.review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.SystemOption;
import csdc.service.IProjectService;
import csdc.tool.tableKit.imp.ExpertKit;
import csdc.tool.tableKit.imp.UniversityKit;

/**
 * 专家跨类-高校评审专家统计
 * @author fengcl
 *
 */
@SuppressWarnings("unchecked")
public class UnivReviewerStatisticAction  extends ReviewStatisticAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL4Univ = "select u.name, u.code, u.useExpert, count(distinct pr.reviewer.id) from University u, ProjectApplicationReview pr, Expert e where u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear ";
	private static final String HQLG = " group by u.name, u.code, u.useExpert ";
	private static final String pageName = "univRevwerSticPage";
	private static final String PAGE_BUFFER_ID = "u.id";//缓存id
	private static final String column[] = {
		"u.name",
		"u.code",
		"count(distinct pr.reviewer.id)",
		"u.useExpert"
	};// 排序用的列
	private boolean keyword1, keyword2;// 用于高级检索（选用两个条件）keyword1:选中general,keyword2:选中inspt
	
	IProjectService projectService;
	
	/**
	 * 高校统计列表 
	 */
	@Override
	public Object[] simpleSearchCondition() {
		
		StringBuffer hql = new StringBuffer();
		hql.append(HQL4Univ);
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		
		// 处理检索参数
		map.put("keyword", "%" + keyword + "%");
		// 拼接检索条件
		hql.append(" and ");
		if (searchType == 1) {// 按账号名称检索
			hql.append(" LOWER(u.name) like :keyword ");
		} else if (searchType == 2) {// 按事件描述检索
			hql.append(" LOWER(u.code) like :keyword ");
		} else {// 按上述字段检索
			hql.append(" (LOWER(u.name) like :keyword or LOWER(u.code) like :keyword) ");
		}			
//		initConfig();//为各评审参数赋值
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
	 * 处理列表数据
	 * 封装每个高校评审专家跨类项目的专家数量
	 */
	public void pageListDealWith() {
		// 处理之后的列表数据
		laData = new ArrayList();
		Object[] o;// 缓存查询结果的一行
		Object[] item;// 缓存查询结果一行
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串		
		//拼接获取数据 获取高校的专家跨类项目评审项目信息
		Integer defaultYear = (Integer) session.get("defaultYear");//从session中获取当前显示年份
		Map stasticMap = projectService.getUnivReviewersProjectsInfo(defaultYear);
		Map univId2GeneralCnt = (Map) stasticMap.get("general");
		Map univId2InstpCnt = (Map) stasticMap.get("instp");
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			o = (Object[]) p;
			int itemLength = o.length;
			item = new Object[itemLength + 2];//添加评审项目类别（general， instp），分别添加到末尾
			for (int i = 0; i < itemLength; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) o[i]);
						item[i] = datestr;
					} else {
						item[i] = o[i].toString();
					}
				}
			}
			if(o[2].equals(1)) {
				item[2] = "是";
			} else if (item[2].equals(0)) {
				item[2] = "否";
			}
			//添加封装后的字段
			String univCode = (String) item[1];
			//添加评审一般项目专家数
			if (!univId2GeneralCnt.isEmpty() && univId2GeneralCnt.get(univCode) != null) {
				item[itemLength] = univId2GeneralCnt.get(univCode);
			} else {
				item[itemLength] = "0";
			}
			//添加评审基地项目专家数
			if (!univId2InstpCnt.isEmpty() && univId2InstpCnt.get(univCode) != null) {
				item[itemLength + 1] = univId2InstpCnt.get(univCode);
			} else {
				item[itemLength + 1] = "0";
			}
			laData.add(item);// 将处理好的数据存入laData
		}
	}
	
	/**
	 * 导出高校专家参评数
	 * @return
	 * @throws Exception
	 */
	public String exportUniversityStatistic() throws Exception {
		//TODO
		return null;
	}	
	//高级检索
	//如果选中keyword1 ： 搜索结果为 一般项目的专家 >0
	//如果选中keyword2 ： 搜索结果为 基地项目的专家 >0
	//都选中，以上结果都有>0
	//什么都不选，则全部搜索出来，不进行任何限制
	@Override
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		hql.append(HQL4Univ);
		String adv_hql = "";// 初级检索条件
		if (keyword1  && keyword2) {
			//一般项目和基地项目都参评的评审专家
			//获取专家的不同项目类型评审项目信息，选出两个项目都有的专家id
			Integer defaultYear = (Integer) session.get("defaultYear");//从session中获取当前显示年份
			Map stasticMap = projectService.getUnivReviewersProjectsInfo(defaultYear);
			Map univGeneral2ReverCnt = (Map) stasticMap.get("general");
			Map univInstp2ReverCnt = (Map) stasticMap.get("instp");
			List<String> hasBothID = new ArrayList<String>();
			Set idShortSet = null;
			int shortcnt = univInstp2ReverCnt.size() < univGeneral2ReverCnt.size() ? univInstp2ReverCnt.size() : univGeneral2ReverCnt.size();
			if (shortcnt == univInstp2ReverCnt.size()) {
				idShortSet = univInstp2ReverCnt.keySet();
			} else if(shortcnt == univGeneral2ReverCnt.size()) {
				idShortSet = univGeneral2ReverCnt.keySet();
			}
			List idShortList = new ArrayList<String>(idShortSet);
			for (int k = 0; k < shortcnt; k++) {
				String uCode = (String) idShortList.get(k);
				if (univGeneral2ReverCnt.containsKey(uCode) 
						&& univInstp2ReverCnt.containsKey(uCode)) {
					hasBothID.add(uCode);
				}
			}
			//过滤
			adv_hql = " and u.code in (:hasBothID)";
			map.put("hasBothID", hasBothID);
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
		return pageName;
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
