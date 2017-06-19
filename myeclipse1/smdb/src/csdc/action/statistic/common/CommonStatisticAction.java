package csdc.action.statistic.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mondrian.olap.Result;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.statistic.BaseStatisticAction;
import csdc.bean.MDXQuery;
import csdc.tool.DesUtils;
import csdc.tool.info.GlobalInfo;

/**
 * 常规统计分析Action类
 * @author 雷达，张黎，fengcl
 */
public class CommonStatisticAction extends BaseStatisticAction {

	private static final long serialVersionUID = 1L;
	private MDXQuery mdxQuery;
	private String chartConfig;
	private static final String HQL = "select m.id, m.title, m.year,m.date from MDXQuery m where ";
	private static final String pageName = "statisticCommonPage";
	private static final String pageBufferId = "m.id";// 缓存id
	private static final String dateFormat = "yyyy-MM-dd";// 列表时间格式
	private static final String[] column = new String[] { "m.title", "m.year", "m.date desc" };// 排序列
	private static final String[] chartType={
			"NORMAL_BAR","PIE","DOT_LINE"
	};//统计图类别数组
	private Integer chart_config;//0默认排序 1自定义排序
	private Integer chart_column;//展现列or排序列
	private Integer chart_type;//统计图类型
	private Integer viewFlag;
	private Integer measureNum;//查询栏位的总个数
	protected Integer isHomeShow;//图表是否在首页显示


	/**
	 * 进入列表
	 */
	@SuppressWarnings("unchecked")
	public String toList() {
		if (statisticType != null && statisticType.length() > 0) {
//			Map session = ActionContext.getContext().getSession();
			session.put("statisticType", statisticType);
			// 根据统计类别设置标题
			if (statisticType.startsWith("project")) {
				if(statisticType.equals("project")) {
					session.put("statisticListType", "社科项目统计  > 所有统计");
				} else if(statisticType.equals("project_general")) {
					session.put("statisticListType", "社科项目统计  > 一般项目");
				} else if(statisticType.equals("project_instp")) {
					session.put("statisticListType", "社科项目统计  > 基地项目");
				} else if(statisticType.equals("project_post")) {
					session.put("statisticListType", "社科项目统计  > 后期资助项目");
				} else if(statisticType.equals("project_key")) {
					session.put("statisticListType", "社科项目统计  > 重大攻关项目");
				} else if(statisticType.equals("project_entrust")) {
					session.put("statisticListType", "社科项目统计  > 委托应急课题");
				} else if(statisticType.equals("project_multiple")) {
					session.put("statisticListType", "社科项目统计  > 综合统计");
				}
			} else if (statisticType.startsWith("award")) {
				session.put("statisticListType", "社科奖励统计");
			} else if(statisticType.startsWith("person")){
				session.put("statisticListType", "社科人员统计");
			} else if(statisticType.startsWith("product")){
				if(statisticType.equals("product")) {
					session.put("statisticListType", "社科成果统计 > 所有统计");	
				}else if(statisticType.equals("product_paper")) {
					session.put("statisticListType", "社科成果统计  > 论文");
				} else if(statisticType.equals("product_book")) {
					session.put("statisticListType", "社科成果统计  > 著作");
				} else if(statisticType.equals("product_consultation")) {
					session.put("statisticListType", "社科成果统计  > 研究咨询报告");
				} else if(statisticType.equals("product_patent")) {
					session.put("statisticListType", "社科成果统计  > 专利");
				} else if(statisticType.equals("product_electronic")) {
					session.put("statisticListType", "社科成果统计  > 电子出版物");
				} else if(statisticType.equals("product_other")) {
					session.put("statisticListType", "社科成果统计  > 其他成果");
				} else if(statisticType.equals("product_multiple")) {
					session.put("statisticListType", "社科成果统计  > 综合统计");
				}
			} else if(statisticType.startsWith("unit")){
				session.put("statisticListType", "社科机构统计");
			}else if(statisticType.startsWith("expert")){
				session.put("statisticListType", "研修班学员统计");
			}
		}
		super.toList();
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
//		Map session = ActionContext.getContext().getSession();
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		if (searchType == 1) {
			hql.append(" LOWER(m.title) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {
			hql.append(" To_CHAR(m.year) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {
			hql.append(" To_CHAR(m.date,'YYYY-MM-DD') like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append("(LOWER(m.title) like :keyword or To_CHAR(m.year) like :keyword or To_CHAR(m.date,'YYYY-MM-DD') like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		statisticType = (String) session.get("statisticType");
		if (null != statisticType && statisticType.length() > 0) {
			hql.append(" and m.type like '" + statisticType + "%' order by m.date desc ");
			//map.put("statisticType", statisticType);
		}
		return new Object[]{
			hql.toString(),
			map,
			2,
			null
		};
	}

	/**
	 * 查看常规统计分析
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String toView() throws Exception {
//		Map session = ActionContext.getContext().getSession();
		measureNum = null;
		mdxQuery=(MDXQuery)dao.query(MDXQuery.class, entityId);
		//获取统计分析结果
		Result result=statisticService.getMondrianResult(mdxQuery.getType(), mdxQuery.getMdx());
		//生成统计表数据
		List<List> dataList=statisticService.getData(result, 0);
		//重构数据列表，并添加总计行
		dataList = statisticService.dealWithDatalist(dataList);
		
		session.put("dataList", dataList);
		session.put("statisticTitle", mdxQuery.getTitle());
		session.put("statistic_parm", null);
		
		toList();//刷新session
		
		//加密mdx语句
		DesUtils des = new DesUtils();
		encryptedMdxQueryString = des.encrypt(mdxQuery.getMdx());
		statisticType = mdxQuery.getType();
		
		chartConfig = mdxQuery.getChartConfig();
		isHomeShow = mdxQuery.getIsHomeShow();
		MDXQueryId = entityId;
		if(null != session.get("resultLines")){
			resultLines = (Integer)session.get("resultLines");//结果的行数
		}
		return SUCCESS;
	}

	/**
	 * 转到统计添加
	 */
	public String toAdd() {
		return SUCCESS;
	}

	/**
	 * 添加统计主题
	 */
	@Transactional
	public String add() {
		statisticType = (String) session.get("statisticType");
		mdxQuery.setType(statisticType);
		if(chart_config ==1){//自定义排序
			StringBuffer measures=new StringBuffer(chart_column.toString());
			for(int i=0;i<measureNum;i++){//查询栏位的总个数
				if(i != chart_column){//chart_column为展示栏位的序号
					measures.append(","+i);//补全后面的栏位配置
				}
			}
			chartConfig=chartType[(chart_type == null) ? 0 : chart_type]+":"+ measures.toString();//生成图表配置
		}else {
			chartConfig=chartType[0]+":" + 0;//生成图表配置
		}
		mdxQuery.setChartConfig(chartConfig);
		entityId = dao.add(mdxQuery);
		return SUCCESS;
	}

	/**
	 * 删除统计
	 */
	@Transactional
	public String delete() {
		for (String entityId : entityIds) {
			dao.delete(MDXQuery.class, entityId);
		}
		return SUCCESS;
	}

	/**
	 * 删除校验
	 */
	@SuppressWarnings("unchecked")
	public void validateDelete() {
		if (null == entityIds || entityIds.isEmpty()) {// id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_INFO);
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}

	/**
	 * 转到更新常规统计分析
	 */
	public String toModify() {
		//查询实体
		mdxQuery = (MDXQuery) dao.query(MDXQuery.class, entityId);
		chartConfig=mdxQuery.getChartConfig();
		if(null != chartConfig && ! chartConfig.equals("")){
			setChart_config(1);
			String[] type=chartConfig.split(":");
			for(int i=0;i<chartType.length;i++){
				if(chartType[i].equals(type[0])){////匹配统计图类型
					setChart_type(i);//设置统计图的类型序号
					break;
				}
			}
			String[] column=type[1].split(",");
			setChart_column(Integer.parseInt(column[0]));//展示列的序号
		}
		return (null != mdxQuery) ? SUCCESS : INPUT;
	}

	/**
	 * 更新常规统计分析
	 */
	@Transactional
	public String modify() {
		MDXQuery mq = (MDXQuery) dao.query(MDXQuery.class, entityId);
		mq.setTitle(mdxQuery.getTitle());
		mq.setYear(mdxQuery.getYear());
		mq.setDate(mdxQuery.getDate());
		mq.setMdx(mdxQuery.getMdx());
		if(chart_config ==1){
			//展现列
			StringBuffer measures=new StringBuffer(chart_column.toString());
			for(int i=0;i<measureNum;i++){
				if(i != chart_column){
					measures.append(","+i);
				}
			}
			//统计图配置字串
			chartConfig=chartType[(chart_type == null) ? 0 : chart_type]+":"+ measures.toString();
		}else {
			chartConfig=chartType[0]+":" + 0;//生成图表配置
		}
		mq.setChartConfig(chartConfig);
		dao.modify(mq);
		return SUCCESS;
	}

	/**
	 * 配置图表是否在首页显示
	 * @return
	 */
	@Transactional
	public String configHomeShow(){
		if (entityId != null && (isHomeShow == 1 || isHomeShow == 0)) {
			MDXQuery mq = (MDXQuery) dao.query(MDXQuery.class, entityId);
			mq.setIsHomeShow(isHomeShow);
			dao.modify(mq);
		}
		return SUCCESS;
	}
	
	@Override
	public String[] column() {
		return CommonStatisticAction.column;
	}

	@Override
	public String dateFormat() {
		return CommonStatisticAction.dateFormat;
	}

	@Override
	public String pageBufferId() {
		return CommonStatisticAction.pageBufferId;
	}

	@Override
	public String pageName() {
		return CommonStatisticAction.pageName;
	}
	
	public MDXQuery getMdxQuery() {
		return mdxQuery;
	}
	public void setMdxQuery(MDXQuery mdxQuery) {
		this.mdxQuery = mdxQuery;
	}
	public String getChartConfig() {
		return chartConfig;
	}
	public void setChartConfig(String chartConfig) {
		this.chartConfig = chartConfig;
	}
	public Integer getChart_config() {
		return chart_config;
	}
	public void setChart_config(Integer chartConfig) {
		chart_config = chartConfig;
	}
	public Integer getChart_column() {
		return chart_column;
	}
	public void setChart_column(Integer chartColumn) {
		chart_column = chartColumn;
	}
	public Integer getChart_type() {
		return chart_type;
	}
	public void setChart_type(Integer chartType) {
		chart_type = chartType;
	}
	public Integer getViewFlag() {
		return viewFlag;
	}
	public void setViewFlag(Integer viewFlag) {
		this.viewFlag = viewFlag;
	}
	public Integer getMeasureNum() {
		return measureNum;
	}
	public void setMeasureNum(Integer measureNum) {
		this.measureNum = measureNum;
	}
	public Integer getIsHomeShow() {
		return isHomeShow;
	}
	public void setIsHomeShow(Integer isHomeShow) {
		this.isHomeShow = isHomeShow;
	}
}