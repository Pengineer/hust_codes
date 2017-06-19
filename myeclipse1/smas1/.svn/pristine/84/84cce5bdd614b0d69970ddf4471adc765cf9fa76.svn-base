package csdc.action.expert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Expert;
import csdc.bean.SystemOption;
import csdc.service.IExpertService;
import csdc.service.IGeneralService;
import csdc.tool.execution.importer.Tools;

public class SelectExpertAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select expert.id, expert.name, u.name, expert.specialityTitle, expert.discipline, expert.reason, expert.isKey from Expert expert, University u where expert.universityCode = u.code ";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGENAME = "selectExpertPage";
	private static final String PAGE_BUFFER_ID = "expert.id";//缓存id
	private static final String column[] = {
			"expert.name",
			"u.name",
			"expert.specialityTitle",
			"expert.discipline",
			"expert.reason"
	};// 排序用的列
	private IExpertService expertService;
	private IGeneralService generalService;
	private Expert expert;
	
	@Autowired
	Tools tool;
	
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (!keyword.isEmpty()) {// 如果关键字为空，不加搜索条件
			hql.append(" and ");
			if (searchType == 1) {
				hql.append("LOWER(expert.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append("LOWER(u.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {
				hql.append("LOWER(expert.specialityTitle) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 4) {
				hql.append("LOWER(expert.discipline) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 5) {
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					hql.append("(");
					for (int i = 0; i < dislist.size(); i++) {
						hql.append("LOWER(expert.discipline) like '%" + dislist.get(i) + "%' or ");
					}
					hql.append(hql.substring(0, hql.length() - 4));
					hql.append(")");
				} else {
					hql.append("LOWER(expert.discipline) like :keyword");// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
			} else {
				hql.append("(LOWER(expert.name) like :keyword or LOWER(u.name) like :keyword or LOWER(expert.specialityTitle) like :keyword");
				map.put("keyword", "%" + keyword + "%");
				List<Object> dislist = generalService.getDisciplineCode(keyword);
				if (dislist != null && !dislist.isEmpty()) {
					for (int i = 0; i < dislist.size(); i++) {
						hql.append(" or LOWER(expert.discipline) like '%" + dislist.get(i) + "%'");
					}
				} else {
					hql.append(" or LOWER(expert.discipline) like :keyword");// 当没有找到相应的学科代码时，以汉字填补，只是为了输出为空
					map.put("keyword", "%" + keyword + "%");
				}
				hql.append(")");
			}
		}
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
			if (item[4] != null) {
				disciplineCode = item[4].toString();//学科代码
				value = generalService.getDisciplineInfo(disname2discode, disciplineCode);
			}
			item[4] = value;
			laData.add(item);// 将处理好的数据存入laData
		}
	}
	
	/**
	 * 弹出层进入添加
	 * @return
	 */
	public String toPopAdd(){
		return SUCCESS;
	}
	
	/**
	 * 弹出层添加
	 * @return
	 */
	public String popAdd(){
		List<Integer> numberList = baseService.query("select max(e.number) from Expert e");
		Integer number = numberList.get(0) + 1;
		expert.setNumber(number);
		String disciplineCode = tool.transformDisc(expert.getDiscipline());
		expert.setDiscipline(disciplineCode);
		entityId = (String) baseService.add(expert);
		jsonMap.put("entityId", entityId);
		return SUCCESS;
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
		return PAGE_BUFFER_ID;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	public IExpertService getExpertService() {
		return expertService;
	}
	public void setExpertService(IExpertService expertService) {
		this.expertService = expertService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	public Expert getExpert() {
		return expert;
	}
	public void setExpert(Expert expert) {
		this.expert = expert;
	}
}
