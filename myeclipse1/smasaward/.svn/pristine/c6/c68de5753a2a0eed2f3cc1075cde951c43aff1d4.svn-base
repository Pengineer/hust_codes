package csdc.action.statistic.review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.SystemOption;
import csdc.tool.tableKit.imp.ExpertKit;
import csdc.tool.tableKit.imp.UniversityKit;

/**
 * 高校参评专家数
 * @author fengcl
 *
 */
@SuppressWarnings("unchecked")
public class UniversityStatisticAction  extends ReviewStatisticAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL4General = "select u.name, u.code, u.useExpert, count(distinct pr.reviewer.id) from University u, ProjectApplicationReview pr, Expert e where pr.type = 'general' and u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear ";
	private static final String HQL4INSTITUTE = "select u.name, u.code, u.useExpert, count(distinct pr.reviewer.id) from University u, ProjectApplicationReview pr, Expert e where pr.type = 'instp' and u.code = e.universityCode and e.id = pr.reviewer.id and pr.year = :defaultYear ";
	private static final String HQLG = " group by u.name, u.code, u.useExpert ";
	private static final String PAGENAME = "statisticUPage";
	private static final String PAGE_BUFFER_ID = "u.id";//缓存id
	private static final String column[] = {
		"u.name",
		"u.code",
		"count(distinct pr.reviewer.id)",
		"u.useExpert"
	};// 排序用的列
	
	/**
	 * 高校统计列表 
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
		initConfig();//为各评审参数赋值
//		hql.append(isContainP == 0 ? " and pr.priority <= " + projectExpertNumber + " ": " ");
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
		// 处理之后的列表数据
		laData = new ArrayList();
		Object[] item;// 缓存查询结果一行
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串		
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
			if(item[2].equals(1)) {
				item[2] = "是";
			} else if (item[2].equals(0)) {
				item[2] = "否";
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
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		UniversityKit uk = new UniversityKit();
		map.put("defaultYear", session.get("defaultYear"));//从session中获取当前显示年份
		if(listType == 1){//一般项目
			hql.append(HQL4General);
		}else if(listType == 2){//基地项目
			hql.append(HQL4INSTITUTE);
		}	
		hql.append(HQLG);
		uk.exportUniversityStatistic(hql.toString(),map);
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
}
