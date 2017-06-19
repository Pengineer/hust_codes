package csdc.action.mobile.statistic;
  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import mondrian.olap.Result;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.mobile.MobileAction;
import csdc.service.IMobileStatisticService;
import csdc.service.IStatisticService;
import csdc.tool.bean.AccountType;

/**
 * mobile定制统计模块
 * @author suwb
 *
 */
public class CustomStatisticAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IMobileStatisticService mobileStatisticService;
	@Autowired
	private IStatisticService statisticService;
	
	private static final String PAGENAME = "customStatisticPage"; 
	
	private String statisticType;
	private String mdx;
	private List<String> statistic_parm = new ArrayList<String>();
	
	//用于奖励Mdx拼接
	private Integer statistic_start_sesison;// 统计起始届次
	private Integer statistic_end_sesison;// 统计终止届次
	private String statistic_university_name;// 指定高校统计(也用于人员)
	private String statistic_discipline; //指定学科门类统计(也用于项目)	
	//用于项目Mdx拼接
	private Integer statistic_startYear; //起始年度
	private Integer statistic_endYear; //终止年度
	private String statistic_university; //高校名称
	private String statistic_projectType; //项目类型
	private String statistic_subType; //项目子类
	private String statistic_projectArea; //项目区域
	private String statistic_productType; //成果类型
	private String statistic_province; //所在省份
	private String statistic_evaluType; //评审类型
	private String statistic_universityType; //高校类别
	//
	private String chartShowIndexes; //需要显示的列
	protected String mdx_rows; //mdx查询语句中的rows
	protected String mdx_columns; //mdx查询语句中的columns
	protected String mdx_sortColumn; //mdx查询语句中的sortColumn
	
	protected String statistic_data; //需要统计的数据
	protected String statistic_index; //统计指标
	protected String statistic_sortColumn; //排序指标
	protected int statistic_showLineNum; //显示行数
	protected int sort_type; //排序方式  1.默认排序， 2.自定义排序， 3.不排序
	protected int statistic_sortType; //1 降序，2 升序
	protected int statistic_showNonZero; //1只显示非零行 0全部显示
	
	//图表配置(多个配置用分号隔开；每个配置中用引号隔开，引号左边为图表类型，
	//引号右边为用逗号隔开的数字，第一个数字代表排序列栏位，
	//后面跟着的数字代表要显示的tip栏位
	protected String chartConfig; //图表配置（eg "PIE:0,1,2; NORMAL_BAR:3,1"）
	protected String chartType; // 图表类型

	//隐藏类初始化法
	//PERSONITEMS：人员
	private static final ArrayList PERSONITEMS = new ArrayList();
	static{
		PERSONITEMS.add("社科人员定制统计#1");
	}
	//UNITITEMS：机构
	private static final ArrayList UNITITEMS = new ArrayList();
	static{
		UNITITEMS.add("社科机构定制统计#1");
	}
	//PROJECTITEMS：项目
	private static final ArrayList PROJECTITEMS = new ArrayList();
	static{
		PROJECTITEMS.add("社科项目定制统计#1");
	}
	//PRODUCTITEMS：成果
	private static final ArrayList PRODUCTITEMS = new ArrayList();
	static{
		PRODUCTITEMS.add("社科成果定制统计#1");
	}
	//AWARDITEMS：奖励
	private static final ArrayList AWARDITEMS = new ArrayList();
	static{
		AWARDITEMS.add("社科奖励定制统计#1");
	}
	
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		mainItems.put("Person", PERSONITEMS);
		mainItems.put("Unit", UNITITEMS);
		mainItems.put("Project", PROJECTITEMS);
		mainItems.put("Product", PRODUCTITEMS);
		mainItems.put("Award", AWARDITEMS);
		switch (accountType) {
		case ADMINISTRATOR://管理员
		case MINISTRY://部级管理人员
			jsonMap.put("listItem", mainItems);	
			break;
		case PROVINCE://省级管理人员
		case LOCAL_UNIVERSITY:
		case MINISTRY_UNIVERSITY://高校管理人员	
		case DEPARTMENT://院系管理人员	
		case INSTITUTE://基地管理人员	
		case EXPERT://外部专家	
		case TEACHER://教师	
		case STUDENT://学生			
			jsonMap.put("listItem", null);
			break;
		default:
			break;
		}
		return SUCCESS;	
	}
	
	/**
	 * 定制统计分析详情
	 * @return
	 * @throws Exception 
	 */
	public String view() throws Exception{
//		String mdx = getMdx().get("mdx").toString();
//		List<String> statistic_parm = (List<String>)getMdx().get("statistic_parm");//定制统计相关参数
		Map session = ActionContext.getContext().getSession();	
		String mdxQuery = (String)session.get("mdx");
		String statistic_Type = (String)session.get("statisticType");
		String chart_Config = (String)session.get("chartConfigTmp");
		System.out.println(mdxQuery);
		Result result = statisticService.getMondrianResult(statistic_Type, mdxQuery);
		List<List> dataList;//获取表格数据
		try {
			dataList = statisticService.getData(result, 0);
			dataList = statisticService.dealWithDatalist(dataList);//添加总计行
		} catch (Exception e) {
			dataList = null;
		}
		//还要去session里取statistic_parm(定制统计的上方相关参数展示)
		String[] chartConfigs = chart_Config.split(";");
		for (String config : chartConfigs) {
			String[] temp = config.trim().split(":");
			String strChartType = temp[0].trim();//图表类型
			String chartShowIndexes = temp[1].trim();//需要显示的列
			try {
				jsonMap = mobileStatisticService.getMobileJsonMap(result, strChartType, chartShowIndexes);//获得图表json
			} catch (Exception e) {
				if(chartType.equals("PIE")){
					jsonMap.put("vList", null);					
				}
				else {
					jsonMap.put("xList", null);	
					jsonMap.put("yList", null);	
				}
			}
			jsonMap.put("dataList", dataList);
			jsonMap.put("statistic_parm", session.get("statistic_parm"));
			jsonMap.put("listTitle", "社科定制统计");
		}
		return SUCCESS;
	}

	/**
	 * 定制统计Mdx条件拼接
	 * @return
	 * @throws Exception
	 */
	public String getMdxToView() throws Exception{
		StringBuffer mdxBuffer = new StringBuffer();
		String statisticUniversityName = statistic_university_name;//指定高校
		String statisticIndex = statistic_index;
		String statisticData = statistic_data;
		String statisticSortColumn = statistic_sortColumn;
		String statisticUniversity = statistic_university;//所属高校
		String statisticDiscipline = statistic_discipline;
		String statisticProjectType = statistic_projectType;
		String statisticSubType = statistic_subType;
		String statisticProjectArea = statistic_projectArea;
		String statisticProductType = statistic_productType;
		String statisticProvince = statistic_province;
		String statisticEvaluType = statistic_evaluType;
		String statisticUniversityType = statistic_universityType;
		if("person".equals(statisticType)){//定制人员统计
			if(statisticIndex.equals("年龄段")) statisticIndex = new String("[人员维度.年龄段].[年龄段].Members");
			else if(statisticIndex.equals("性别")) statisticIndex = new String("[人员维度.性别].[性别].Members");
			else if(statisticIndex.equals("最后学历")) statisticIndex = new String("[人员维度.学历].[学历].Members");
			else if(statisticIndex.equals("最后学位")) statisticIndex = new String("[人员维度.学位].[学位].Members");
			else if(statisticIndex.equals("导师类型")) statisticIndex = new String("[人员维度.导师类型].[导师类型].Members");
			else if(statisticIndex.equals("所属省份")) statisticIndex = new String("[机构维度.省份名称].[省份名称].Members");
			else if(statisticIndex.equals("所属高校")) statisticIndex = new String("[机构维度.高校名称].[高校名称].Members");
			else if(statisticIndex.equals("所属高校类别")) statisticIndex = new String("[机构维度.高校类别].[高校类别].Members");
			else if(statisticIndex.equals("所属高校性质")) statisticIndex = new String("[机构维度.性质类别].[性质类别].Members");
			else if(statisticIndex.equals("参与项目类别")) statisticIndex = new String("[人员维度.项目类别].[项目类别].Members");
			else if(statisticIndex.equals("是否项目负责人")) statisticIndex = new String("[人员维度.项目负责人].[项目负责人].Members");
			else if(statisticIndex.equals("是否项目成员")) statisticIndex = new String("[人员维度.项目成员].[项目成员].Members");
			else if(statisticIndex.equals("专业职称")) statisticIndex = new String("[人员维度.职称].[职称].Members");
			else if(statisticIndex.equals("学科门类")) statisticIndex = new String("[人员维度.学科门类].[学科门类].Members");
			else return INPUT;
			StringBuffer data = new StringBuffer();
			if(statisticData.contains("人员数量")) data.append("[Measures].[人员数目],");
			statisticData = data.substring(0, data.toString().length()-1).toString();
			if(statisticSortColumn.equals("人员数量")) statisticSortColumn = new String("[Measures].[人员数目]");
			else return INPUT;
			setMdx_columns(statisticData);
			setMdx_rows(statisticIndex);
			setMdx_sortColumn(statisticSortColumn);
			String[] indexes = statisticData.split(",");
			int sortNum = 0;
			for(int i = 0; i < indexes.length; i++) {
				if(indexes[i].trim().equals(statisticSortColumn)) {
					sortNum = i;
					break;
				}
			}
			StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
			for(int i = 0; i < indexes.length; i++) {
				if(i != sortNum) {
					chartConfigTmp.append("," + i);
				}
			}
			chartConfig = chartConfigTmp.toString();
//			System.out.println("chartConfig = " + chartConfigTmp.toString());
//			setChartConfig(chartConfigTmp.toString());
			mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
			String rowsString = "";
			if(statistic_showNonZero == 1) {
				rowsString = " Filter({ " + getMdx_rows() + " }, " + statisticSortColumn + " > 0) ";
			} else if (statistic_showNonZero == 0) {
				rowsString = "{ " + getMdx_rows() + " }";
			}
			if(sort_type == 3) {
				if(getStatistic_showLineNum() != -1) {
					mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
				} else {
					mdxBuffer.append(rowsString);
				}
			} else {
				if (statistic_sortType == 1) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + statisticSortColumn + ", BDESC ) " );
					} else {
						mdxBuffer.append(" TopCount( " + rowsString + ", " + statistic_showLineNum + ", " + statisticSortColumn + " ) " );
					}	
				} else if (statistic_sortType == 2) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + statisticSortColumn + ", BASC ) " );
					} else {
						mdxBuffer.append(" BottomCount( " + rowsString + ", " + statistic_showLineNum + ", " + statisticSortColumn + " ) " );
					}
				} 
			}
			mdxBuffer.append(" ON ROWS from [Person] ");
			if(statisticUniversityName != null && !statisticUniversityName.isEmpty()) {
				mdxBuffer.append(" where ([机构维度.高校名称].&[" + statisticUniversityName + "])");
			}
			mdx = mdxBuffer.toString();
//			listMap.put("mdx", mdxBuffer.toString());
//			System.out.println("mdxBuffer = " + mdxBuffer);
//			DesUtils des = new DesUtils();
//			encryptedMdxQueryString = des.encrypt(mdxBuffer.toString());//对Mdx语句进行加密
//			request.getSession().setAttribute("measureNum", statistic_index.split(",").length);
			//定制统计的相关说明

			statistic_parm.add("统计指标=" + statisticService.getFormatedData(statisticIndex));
			if(statisticUniversityName != null && !statisticUniversityName.isEmpty()) {
				statistic_parm.add("高校名称=" + statisticUniversityName);
			}
			statistic_parm.add("统计数据=" + statisticService.getFormatedData(statisticData));
			if(sort_type != 3) {
				String sortString = "";
				if(statistic_sortType == 1) {
					sortString = "(降序)";
				} else if(statistic_sortType == 2) {
					sortString = "(升序)";
				}
				statistic_parm.add("排序列=" + statisticService.getFormatedData(statisticSortColumn) + sortString);
			}
		}else if("unit".equals(statisticType)){//定制机构统计
			if(statisticIndex.equals("所在省份")) statisticIndex = new String("[机构维度.省份名称].[省份名称].Members");
			else if(statisticIndex.equals("办学类型")) statisticIndex = new String("[机构维度.办学类型].[办学类型].Members");
			else if(statisticIndex.equals("性质类别")) statisticIndex = new String("[机构维度.性质类别].[性质类别].Members");
			else if(statisticIndex.equals("结构类别")) statisticIndex = new String("[机构维度.高校类别].[高校类别].Members");
			else if(statisticIndex.equals("举办者")) statisticIndex = new String("[机构维度.举办者].[举办者].Members");
			else return INPUT;
			StringBuffer data = new StringBuffer();
			if(statisticData.contains("所有高校")) data.append("[Measures].[高校总数],");
			if(statisticData.contains("部属高校")) data.append("[Measures].[部属高校],");
			if(statisticData.contains("地方高校")) data.append("[Measures].[地方高校],");
			statisticData = data.substring(0, data.toString().length()-1).toString();
			if(statisticSortColumn.equals("所有高校")) statisticSortColumn = new String("[Measures].[高校总数]");
			else if(statisticSortColumn.equals("部属高校")) statisticSortColumn = new String("[Measures].[部属高校]");
			else if(statisticSortColumn.equals("地方高校")) statisticSortColumn = new String("[Measures].[地方高校]");
			else return INPUT;
			setMdx_columns(statisticData);
			setMdx_rows(statisticIndex);
			setMdx_sortColumn(statisticSortColumn);
			String[] indexes = statisticData.split(",");
			int sortNum = 0;
			for(int i = 0; i < indexes.length; i++) {
				if(indexes[i].trim().equals(statisticSortColumn)) {
					sortNum = i;
					break;
				}
			}
			StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
			for(int i = 0; i < indexes.length; i++) {
				if(i != sortNum) {
					chartConfigTmp.append("," + i);
				}
			}
			chartConfig = chartConfigTmp.toString();
//			System.out.println("chartConfig = " + chartConfigTmp.toString());
//			setChartConfig(chartConfigTmp.toString());
			mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
			
			String rowsString = "";
			if(statistic_showNonZero == 1) {
				rowsString = " Filter({ " + getMdx_rows() + " }, " + getMdx_sortColumn() + " > 0) ";
			} else if (statistic_showNonZero == 0) {
				rowsString = "{ " + getMdx_rows() + " }";
			}
			if(sort_type == 3) {
				if(getStatistic_showLineNum() != -1) {
					mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
				} else {
					mdxBuffer.append(rowsString);
				}
			} else {
				if (statistic_sortType == 1) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BDESC ) " );
					} else {
						mdxBuffer.append(" TopCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}	
				} else if (statistic_sortType == 2) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BASC ) " );
					} else {
						mdxBuffer.append(" BottomCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}
				} 
			}
			mdxBuffer.append(" ON ROWS from [Unit] ");
			mdx = mdxBuffer.toString();
//			listMap.put("mdx", mdxBuffer.toString());
//			System.out.println("mdxBuffer = " + mdxBuffer);
//			DesUtils des = new DesUtils();
//			encryptedMdxQueryString = des.encrypt(mdxBuffer.toString());
//			request.getSession().setAttribute("measureNum", statistic_index.split(",").length);
			
			statistic_parm.add("统计指标=" + statisticService.getFormatedData(statisticIndex));
			statistic_parm.add("统计数据=" + statisticService.getFormatedData(statisticData));
			if(sort_type != 3) {
				String sortString = "";
				if(statistic_sortType == 1) {
					sortString = "(降序)";
				} else if(statistic_sortType == 2) {
					sortString = "(升序)";
				}
				statistic_parm.add("排序列=" + statisticService.getFormatedData(statisticSortColumn) + sortString);
			}
		}else if("project".equals(statisticType)){//定制项目统计
			if(statisticIndex.equals("项目依托高校")) statisticIndex = new String("[机构维度.高校名称].[高校名称].Members");
			else if(statisticIndex.equals("项目学科门类")) statisticIndex = new String("[项目维度.学科门类].[学科门类].Members");
			else if(statisticIndex.equals("项目成果形式")) statisticIndex = new String("[项目维度.成果形式].[成果形式].Members");
			else if(statisticIndex.equals("项目类型")) statisticIndex = new String("[项目维度.项目类型].[项目类型].Members");
			else if(statisticIndex.equals("项目子类")) statisticIndex = new String("[项目维度.项目子类].[项目子类].Members");
			else if(statisticIndex.equals("项目区域")) statisticIndex = new String("[项目维度.项目区域].[项目区域].Members");
			else if(statisticIndex.equals("项目年度")) statisticIndex = new String("[项目维度.项目年度].[项目年度].Members");
			else if(statisticIndex.equals("负责人职称")) statisticIndex = new String("[人员维度.职称].[职称].Members");
			else if(statisticIndex.equals("负责人年龄段")) statisticIndex = new String("[项目维度.负责人年龄段].[负责人年龄段].Members");
			else if(statisticIndex.equals("高校所在省份")) statisticIndex = new String("[机构维度.省份名称].[省份名称].Members");
			else if(statisticIndex.equals("项目中检次数")) statisticIndex = new String("[项目维度.中检次数].[中检次数].Members");
			else if(statisticIndex.equals("项目评审类型")) statisticIndex = new String("[项目维度.评审类型].[评审类型].Members");
			else if(statisticIndex.equals("结构类别")) statisticIndex = new String("[机构维度.高校类别].[高校类别].Members");
			else if(statisticIndex.equals("申请优秀成果")) statisticIndex = new String("[项目维度.申请优秀成果].[申请优秀成果].Members");
			else return INPUT;
			StringBuffer data = new StringBuffer();
			if(statisticData.contains("申请项目数量")) data.append("[Measures].[申请数],");
			if(statisticData.contains("立项项目数量")) data.append("[Measures].[立项数],");
			if(statisticData.contains("立项率")) data.append("[Measures].[立项率],");
			if(statisticData.contains("中检通过数")) data.append("[Measures].[中检通过数],");
			if(statisticData.contains("中检通过率")) data.append("[Measures].[中检通过率],");
			if(statisticData.contains("结项数")) data.append("[Measures].[结项数],");
			if(statisticData.contains("结项率")) data.append("[Measures].[结项率],");
			if(statisticData.contains("批准经费")) data.append("[Measures].[批准经费],");
//			if(statisticData.contains("支出经费")) data.append("[Measures].[地方高校],");
//			if(statisticData.contains("人均支出经费")) data.append("[Measures].[部属高校],");
//			if(statisticData.contains("支出经费同比增加")) data.append("[Measures].[地方高校],");
			statisticData = data.substring(0, data.toString().length()-1).toString();
			if(statisticSortColumn.equals("申请项目数量")) statisticSortColumn = new String("[Measures].[申请数]");
			else if(statisticSortColumn.equals("立项项目数量")) statisticSortColumn = new String("[Measures].[立项数]");
			else if(statisticSortColumn.equals("立项率")) statisticSortColumn = new String("[Measures].[立项率]");
			else if(statisticSortColumn.equals("中检通过数")) statisticSortColumn = new String("[Measures].[中检通过数]");
			else if(statisticSortColumn.equals("中检通过率")) statisticSortColumn = new String("[Measures].[中检通过率]");
			else if(statisticSortColumn.equals("结项数")) statisticSortColumn = new String("[Measures].[结项数]");
			else if(statisticSortColumn.equals("结项率")) statisticSortColumn = new String("[Measures].[结项率]");
			else if(statisticSortColumn.equals("批准经费")) statisticSortColumn = new String("[Measures].[批准经费]");
//			else if(statisticSortColumn.equals("支出经费")) statisticSortColumn = new String("[Measures].[地方高校]");
//			else if(statisticSortColumn.equals("人均支出经费")) statisticSortColumn = new String("[Measures].[部属高校]");
//			else if(statisticSortColumn.equals("支出经费同比增加")) statisticSortColumn = new String("[Measures].[地方高校]");
			else return INPUT;
			setMdx_columns(statisticData);//统计指标
			setMdx_rows(statisticIndex);//统计数据
			setMdx_sortColumn(statisticSortColumn);//排序列
			String[] indexes = statisticData.split(",");
			int sortNum = 0;
			for(int i = 0; i < indexes.length; i++) {
				if(indexes[i].trim().equals(statisticSortColumn)) {
					sortNum = i;//获取排序指标的序号
					break;
				}
			}
			StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
			for(int i = 0; i < indexes.length; i++) {
				if(i != sortNum) {
					chartConfigTmp.append("," + i);
				}
			}
			chartConfig = chartConfigTmp.toString();
//			System.out.println("chartConfig = " + chartConfigTmp.toString());
//			setChartConfig(chartConfigTmp.toString());
			if(statistic_startYear > statistic_endYear) {
				statistic_startYear = statistic_endYear;//起始年不能大于终止年
			}
			//组装各类统计总和的mdx方便后续使用
			String yearString = " sum({[项目维度.项目年度].[" + statistic_startYear + "]:[项目维度.项目年度].[" + statistic_endYear + "]},";
			String applyCount = yearString + "[Measures].[applyCount]) ";
			String grantedCount = yearString + "[Measures].[grantedCount]) ";
			String passMidCount = yearString + "[Measures].[passMidCount]) ";
			String passEndCount = yearString + "[Measures].[passEndCount]) ";
			String approveFee = yearString + "[Measures].[approveFee]) ";
			//组装各类计算XX率的mdx方便后续使用
			mdxBuffer.append("with ");
			if(statisticData.contains("[Measures].[立项率]")) {
				mdxBuffer.append("member [Measures].[立项率] as 'IIf((" + applyCount + " > 0.0), (" + grantedCount + " / " + applyCount + "), 0.0)', FORMAT_STRING = \"0.00%\" ");
			}
			if(statisticData.contains("[Measures].[中检通过率]")) {
				mdxBuffer.append("member [Measures].[中检通过率] as 'IIf((" + grantedCount + " > 0.0), (" + passMidCount + " / " + grantedCount + "), 0.0)', FORMAT_STRING = \"0.00%\" ");
			}
			if(statisticData.contains("[Measures].[结项率]")) {
				mdxBuffer.append("member [Measures].[结项率] as 'IIf((" + grantedCount + " > 0.0), (" + passEndCount + " / " + grantedCount + "), 0.0)', FORMAT_STRING = \"0.00%\" ");
			}
			if(statisticData.contains("[Measures].[申请数]")) {
				mdxBuffer.append("member [Measures].[申请数] as " + applyCount);
			}
			if(statisticData.contains("[Measures].[立项数]")) {
				mdxBuffer.append("member [Measures].[立项数] as " + grantedCount);
			}
			if(statisticData.contains("[Measures].[中检通过数]")) {
				mdxBuffer.append("member [Measures].[中检通过数] as " + passMidCount);
			}
			if(statisticData.contains("[Measures].[结项数]")) {
				mdxBuffer.append("member [Measures].[结项数] as " + passEndCount);
			}
			if(statisticData.contains("[Measures].[批准经费]")) {
				mdxBuffer.append("member [Measures].[批准经费] as " + approveFee);
			}
			mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
			String rowsString = "";
			if(statistic_showNonZero == 1) {//只统计非0行
				rowsString = " Filter({ " + getMdx_rows() + " }, " + getMdx_sortColumn() + " > 0) ";
			} else if (statistic_showNonZero == 0) {
				rowsString = "{ " + getMdx_rows() + " }";
			}
			if(sort_type == 3) {//不排序
				if(getStatistic_showLineNum() != -1) {//配置结果显示多少行
					mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
				} else {
					mdxBuffer.append(rowsString);
				}
			} else {//自定义排序&默认排序
				if (statistic_sortType == 1) {//降序
					if(getStatistic_showLineNum() == -1) {//显示结果全部行
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BDESC ) " );
					} else {
						mdxBuffer.append(" TopCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}	
				} else if (statistic_sortType == 2) {//升序
					if(getStatistic_showLineNum() == -1) {//显示结果全部行
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BASC ) " );
					} else {
						mdxBuffer.append(" BottomCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}
				} 
			}
			mdxBuffer.append(" ON ROWS from [Project] ");//查询项目立方体
			//下面都是切片指标
			List<String> whereStrings = new ArrayList<String>();
			if(!mdx_rows.contains("[机构维度.高校名称]") && statisticUniversity != null && !statisticUniversity.isEmpty()) {
				whereStrings.add("[机构维度.高校名称].[" + statisticUniversity + "]");
			}
			if(!mdx_rows.contains("[机构维度.省份名称].[省份名称]") && statisticProvince != null && !statisticProvince.isEmpty()) {
				whereStrings.add("[机构维度.省份名称].[省份名称].[" + statisticProvince + "]");
			}
			if(!mdx_rows.contains("[项目维度.项目子类]") && statisticSubType != null && !statisticSubType.isEmpty()) {
				whereStrings.add("[项目维度.项目子类].[" + statisticSubType + "]");
			}
			if(!mdx_rows.contains("[项目维度.项目区域]") && statisticProjectArea != null && !statisticProjectArea.isEmpty()) {
				whereStrings.add("[项目维度.项目区域].[" + statisticProjectArea + "]");
			}
			if(!mdx_rows.contains("[项目维度.成果形式]") && statisticProductType != null && !statisticProductType.isEmpty()) {
				whereStrings.add("[项目维度.成果形式].[" + statisticProductType + "]");
			}
			if(!mdx_rows.contains("[项目维度.项目类型]") && statisticProjectType != null && !statisticProjectType.isEmpty()) {
				whereStrings.add("[项目维度.项目类型].[" + statisticProjectType + "]");
			}
			if(!mdx_rows.contains("[项目维度.评审类型]") && statisticEvaluType != null && !statisticEvaluType.isEmpty()) {
				whereStrings.add("[项目维度.评审类型].[" + statisticEvaluType + "]");
			}
			if(!mdx_rows.contains("[机构维度.高校类别]") && statisticUniversityType != null && !statisticUniversityType.isEmpty()) {
				whereStrings.add("[机构维度.高校类别].[" + statisticUniversityType + "]");
			}
			if(!mdx_rows.contains("[项目维度.学科门类]") && statisticDiscipline != null && !statisticDiscipline.isEmpty()) {
				if(statisticDiscipline.trim().equals("马克思主义")) {
					statisticDiscipline = "马克思主义理论/思想政治教育";
				}
				whereStrings.add("[项目维度.学科门类].[" + statisticDiscipline + "]");
			}
			//根据用户账号级别限制其统计的数据范围
			try{
				if(statisticService.getAccountBelongArea(loginer.getAccount().getId()) != null) {
					whereStrings.add(statisticService.getAccountBelongArea(loginer.getAccount().getId()));
				}								
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//将前面的切片指标拼装好
			if(whereStrings.size() > 0) {
				mdxBuffer.append(" where (");
				for(int i = 0; i < whereStrings.size() - 1; i++) {
					mdxBuffer.append(whereStrings.get(i) + ",");
				}
				mdxBuffer.append(whereStrings.get(whereStrings.size() - 1));
				mdxBuffer.append(")");
			}
			mdx = mdxBuffer.toString();
//			listMap.put("mdx", mdxBuffer.toString());
//			System.out.println("mdxBuffer = " + mdxBuffer);
//			DesUtils des = new DesUtils();
//			//将生成mdx加密供前台显示统计表和统计图用
//			encryptedMdxQueryString = des.encrypt(mdxBuffer.toString());
//			request.getSession().setAttribute("measureNum", statistic_index.split(",").length);
			if(statisticProjectType != null && !statisticProjectType.isEmpty()) {
				statistic_parm.add("项目类型=" + statisticProjectType);
			} else {
				statistic_parm.add("项目类型=所有项目");
			}
			statistic_parm.add("统计指标=" + statisticService.getFormatedData(statisticIndex));
			statistic_parm.add("统计数据=" + statisticService.getFormatedData(statisticData));
			if(statistic_startYear.compareTo(statistic_endYear) < 0) {
				statistic_parm.add("项目年度=" + statistic_startYear + "年至" + statistic_endYear  + "年");
			} else if(statistic_startYear.compareTo(statistic_endYear) == 0) {
				statistic_parm.add("项目年度=" + statistic_startYear + "年");
			}
			if(statisticUniversity != null && !statisticUniversity.isEmpty()) {
				statistic_parm.add("高校名称=" + statisticUniversity);
			}
			if(statisticDiscipline != null && !statisticDiscipline.isEmpty()) {
				statistic_parm.add("学科门类=" + statisticDiscipline);
			}
			if(statisticSubType != null && !statisticSubType.isEmpty()) {
				statistic_parm.add("项目子类=" + statisticSubType);
			}
			if(statisticProjectArea != null && !statisticProjectArea.isEmpty()) {
				statistic_parm.add("项目区域=" + statisticProjectArea);
			}
			if(statisticProductType != null && !statisticProductType.isEmpty()) {
				statistic_parm.add("成果形式=" + statisticProductType);
			}
			if(statisticProvince != null && !statisticProvince.isEmpty()) {
				statistic_parm.add("所在省份=" + statisticProvince);
			}
			if(statisticEvaluType != null && !statisticEvaluType.isEmpty()) {
				statistic_parm.add("评审类型=" + statisticEvaluType);
			}
			if(statisticUniversityType != null && !statisticUniversityType.isEmpty()) {
				statistic_parm.add("高校类别=" + statisticUniversityType);
			}
			if(sort_type != 3) {
				String sortString = "";
				if(statistic_sortType == 1) {
					sortString = "(降序)";
				} else if(statistic_sortType == 2) {
					sortString = "(升序)";
				}
				statistic_parm.add("排序列=" + statisticService.getFormatedData(statisticSortColumn) + sortString);
			}			
//			Map session = ActionContext.getContext().getSession();		
		}else if("product".equals(statisticType)){//定制成果统计
			if(statisticIndex.equals("成果类型")) statisticIndex = new String("[成果维度.成果类型].[成果类型].Members");
			else if(statisticIndex.equals("学科门类")) statisticIndex = new String("[成果维度.学科门类].[学科门类].Members");
			else if(statisticIndex.equals("所属高校")) statisticIndex = new String("[机构维度.高校名称].[高校名称].Members");
			else if(statisticIndex.equals("所属省份")) statisticIndex = new String("[机构维度.省份名称].[省份名称].Members");
//			else if(statisticIndex.equals("成果形态")) statisticIndex = new String("[项目维度.项目子类].[项目子类].Members");
//			else if(statisticIndex.equals("成果级别")) statisticIndex = new String("[项目维度.项目区域].[项目区域].Members");
//			else if(statisticIndex.equals("成果学科门类")) statisticIndex = new String("[项目维度.项目年度].[项目年度].Members");
//			else if(statisticIndex.equals("是否获奖")) statisticIndex = new String("[人员维度.职称].[职称].Members");
//			else if(statisticIndex.equals("获奖等级")) statisticIndex = new String("[项目维度.负责人年龄段].[负责人年龄段].Members");
//			else if(statisticIndex.equals("获奖届次")) statisticIndex = new String("[机构维度.省份名称].[省份名称].Members");
//			else if(statisticIndex.equals("年度")) statisticIndex = new String("[项目维度.中检次数].[中检次数].Members");
			else return INPUT;
			if(statisticData.contains("成果数量")) statisticData = new String("[Measures].[成果总数]");
			else return INPUT;
			if(statisticSortColumn.equals("成果数量")) statisticSortColumn = new String("[Measures].[成果总数]");
			else return INPUT;
			setMdx_columns(statisticData);
			setMdx_rows(statisticIndex);
			setMdx_sortColumn(statisticSortColumn);
			String[] indexes = statisticData.split(",");
			int sortNum = 0;
			for(int i = 0; i < indexes.length; i++) {
				if(indexes[i].trim().equals(statisticSortColumn)) {
					sortNum = i;
					break;
				}
			}
			StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
			for(int i = 0; i < indexes.length; i++) {
				if(i != sortNum) {
					chartConfigTmp.append("," + i);
				}
			}
			chartConfig = chartConfigTmp.toString();
//			System.out.println("chartConfig = " + chartConfigTmp.toString());
//			setChartConfig(chartConfigTmp.toString());
			mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
			String rowsString = "";
			if(statistic_showNonZero == 1) {
				rowsString = " Filter({ " + getMdx_rows() + " }, " + getMdx_sortColumn() + " > 0) ";
			} else if (statistic_showNonZero == 0) {
				rowsString = "{ " + getMdx_rows() + " }";
			}
			if(sort_type == 3) {
				if(getStatistic_showLineNum() != -1) {
					mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
				} else {
					mdxBuffer.append(rowsString);
				}
			} else {
				if (statistic_sortType == 1) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BDESC ) " );
					} else {
						mdxBuffer.append(" TopCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}	
				} else if (statistic_sortType == 2) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BASC ) " );
					} else {
						mdxBuffer.append(" BottomCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}
				} 
			}
			mdxBuffer.append(" ON ROWS from [Product] ");
			if(statisticUniversityName != null && !statisticUniversityName.isEmpty()) {
				mdxBuffer.append(" where ([机构维度.高校名称].&[" + statisticUniversityName + "])");
			}
			mdx = mdxBuffer.toString();
//			listMap.put("mdx", mdxBuffer.toString());
//			System.out.println("mdxBuffer = " + mdxBuffer);
//			DesUtils des = new DesUtils();
//			encryptedMdxQueryString = des.encrypt(mdxBuffer.toString());
//			request.getSession().setAttribute("measureNum", statistic_index.split(",").length);

			statistic_parm.add("统计指标=" + statisticService.getFormatedData(statisticIndex));
			if(statisticUniversityName != null && !statisticUniversityName.isEmpty()) {
				statistic_parm.add("高校名称=" + statisticUniversityName);
			}
			statistic_parm.add("统计数据=" + statisticService.getFormatedData(statisticData));
			if(sort_type != 3) {
				String sortString = "";
				if(statistic_sortType == 1) {
					sortString = "(降序)";
				} else if(statistic_sortType == 2) {
					sortString = "(升序)";
				}
				statistic_parm.add("排序列=" + statisticService.getFormatedData(statisticSortColumn) + sortString);
			}
//			Map session = ActionContext.getContext().getSession();
		}else if("award".equals(statisticType)){//定制奖励统计
			if(statisticIndex.equals("奖励所属高校")) statisticIndex = new String("[机构维度.高校名称].[高校名称].Members");
			else if(statisticIndex.equals("奖励学科门类")) statisticIndex = new String("[奖励维度.学科门类].[学科门类].Members");
			else if(statisticIndex.equals("获奖成果形式")) statisticIndex = new String("[奖励维度.成果类型].[成果类型].Members");
			else if(statisticIndex.equals("奖励届次")) statisticIndex = new String("[奖励维度.届次].[届次].Members");
//			else if(statisticIndex.equals("奖励所属高校类型")) statisticIndex = new String("[项目维度.项目子类].[项目子类].Members");
//			else if(statisticIndex.equals("奖励等级")) statisticIndex = new String("[项目维度.项目区域].[项目区域].Members");
//			else if(statisticIndex.equals("获奖成果形态")) statisticIndex = new String("[项目维度.项目年度].[项目年度].Members");
//			else if(statisticIndex.equals("获奖成果级别")) statisticIndex = new String("[人员维度.职称].[职称].Members");
//			else if(statisticIndex.equals("奖励所在省、市、自治区")) statisticIndex = new String("[项目维度.负责人年龄段].[负责人年龄段].Members");
			else return INPUT;
			StringBuffer data = new StringBuffer();
			if(statisticData.contains("特等奖数")) data.append("[Measures].[特等奖数],");
			if(statisticData.contains("一等奖数")) data.append("[Measures].[一等奖数],");
			if(statisticData.contains("二等奖数")) data.append("[Measures].[二等奖数],");
			if(statisticData.contains("三等奖数")) data.append("[Measures].[三等奖数],");
			if(statisticData.contains("普及奖数")) data.append("[Measures].[普及奖数],");
			if(statisticData.contains("获奖总数")) data.append("[Measures].[获奖总数],");
			if(statisticData.contains("获奖总分")) data.append("[Measures].[获奖总分],");
			statisticData = data.substring(0, data.toString().length()-1).toString();
			if(statisticSortColumn.equals("特等奖数")) statisticSortColumn = new String("[Measures].[特等奖数]");
			else if(statisticSortColumn.equals("一等奖数")) statisticSortColumn = new String("[Measures].[一等奖数]");
			else if(statisticSortColumn.equals("二等奖数")) statisticSortColumn = new String("[Measures].[二等奖数]");
			else if(statisticSortColumn.equals("三等奖数")) statisticSortColumn = new String("[Measures].[三等奖数]");
			else if(statisticSortColumn.equals("普及奖数")) statisticSortColumn = new String("[Measures].[普及奖数]");
			else if(statisticSortColumn.equals("获奖总数")) statisticSortColumn = new String("[Measures].[获奖总数]");
			else if(statisticSortColumn.equals("获奖总分")) statisticSortColumn = new String("[Measures].[获奖总分]");
			else return INPUT;
			setMdx_columns(statisticData);
			setMdx_rows(statisticIndex);
			setMdx_sortColumn(statisticSortColumn);
			String[] indexes = statisticData.split(",");
			int sortNum = 0;
			for(int i = 0; i < indexes.length; i++) {
				if(indexes[i].trim().equals(statisticSortColumn)) {
					sortNum = i;
					break;
				}
			}
			StringBuffer chartConfigTmp = new StringBuffer(chartType + ":" + sortNum);
			for(int i = 0; i < indexes.length; i++) {
				if(i != sortNum) {
					chartConfigTmp.append("," + i);
				}
			}
			chartConfig = chartConfigTmp.toString();
//			System.out.println("chartConfig = " + chartConfigTmp.toString());
//			setChartConfig(chartConfigTmp.toString());
			if(statistic_start_sesison > statistic_end_sesison) {
				statistic_start_sesison = statistic_end_sesison;
			}
			
			String award_0_prize = "[Measures].[award_0_prize]";
			String award_1_prize = "[Measures].[award_1_prize]";
			String award_2_prize = "[Measures].[award_2_prize]";
			String award_3_prize = "[Measures].[award_3_prize]";
			String award_4_prize = "[Measures].[award_4_prize]";
			String award_totalScore = "[Measures].[award_totalScore]";
			String award_totalCount = "[Measures].[award_totalCount]";
			if (!getMdx_rows().contains("[奖励维度.届次].[届次].Members")) {
				String award_session = " sum({[奖励维度.届次].[第" + statistic_start_sesison + "届]:[奖励维度.届次].[第" + statistic_end_sesison + "届]},";
				award_0_prize = award_session + award_0_prize + ") ";
				award_1_prize = award_session + award_1_prize + ") ";
				award_2_prize = award_session + award_2_prize + ") ";
				award_3_prize = award_session + award_3_prize + ") ";
				award_4_prize = award_session + award_4_prize + ") ";
				award_totalScore = award_session + award_totalScore + ") ";
				award_totalCount = award_session + award_totalCount + ") ";
			} else {
				String mdxRows = "[奖励维度.届次].[第" + statistic_start_sesison + "届]";
				int sessions = statistic_end_sesison - statistic_start_sesison;
				for(int i = 1; i <= sessions; i++){
					mdxRows += ", [奖励维度.届次].[第" + (statistic_start_sesison + i) + "届]";
				}
				setMdx_rows(mdxRows);
			}			
			mdxBuffer.append("with ");
			if(statisticData.contains("[Measures].[特等奖数]")) {
				mdxBuffer.append("member [Measures].[特等奖数] as " + award_0_prize);
			}
			if(statisticData.contains("[Measures].[一等奖数]")) {
				mdxBuffer.append("member [Measures].[一等奖数] as " + award_1_prize);
			}
			if(statisticData.contains("[Measures].[二等奖数]")) {
				mdxBuffer.append("member [Measures].[二等奖数] as " + award_2_prize);
			}
			if(statisticData.contains("[Measures].[三等奖数]")) {
				mdxBuffer.append("member [Measures].[三等奖数] as " + award_3_prize);
			}
			if(statisticData.contains("[Measures].[普及奖数]")) {
				mdxBuffer.append("member [Measures].[普及奖数] as " + award_4_prize);
			}
			if(statisticData.contains("[Measures].[获奖总分]")) {
				mdxBuffer.append("member [Measures].[获奖总分] as " + award_totalScore);
			}
			if(statisticData.contains("[Measures].[获奖总数]")) {
				mdxBuffer.append("member [Measures].[获奖总数] as " + award_totalCount);
			}
			mdxBuffer.append(" select { " + getMdx_columns() + " } ON COLUMNS, ");
			
			String rowsString = "";
			if(statistic_showNonZero == 1) {
				rowsString = " Filter({ " + getMdx_rows() + " }, " + getMdx_sortColumn() + " > 0) ";
			} else if (statistic_showNonZero == 0) {
				rowsString = "{ " + getMdx_rows() + " }";
			}
			if(sort_type == 3) {
				if(getStatistic_showLineNum() != -1) {
					mdxBuffer.append(" Head( " + rowsString + ", " + statistic_showLineNum + " ) " );
				} else {
					mdxBuffer.append(rowsString);
				}
			} else {
				if (statistic_sortType == 1) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BDESC ) " );
					} else {
						mdxBuffer.append(" TopCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}	
				} else if (statistic_sortType == 2) {
					if(getStatistic_showLineNum() == -1) {
						mdxBuffer.append(" Order( " + rowsString + ", " + getMdx_sortColumn() + ", BASC ) " );
					} else {
						mdxBuffer.append(" BottomCount( " + rowsString + ", " + getStatistic_showLineNum() + ", " + getMdx_sortColumn() + " ) " );
					}
				} 
			}
			mdxBuffer.append(" ON ROWS from [Award] ");
			List<String> whereStrings = new ArrayList<String>();
			if(!mdx_rows.contains("[机构维度.高校名称]") && statisticUniversityName != null && !statisticUniversityName.isEmpty()) {
				whereStrings.add("[机构维度.高校名称].[" + statisticUniversityName + "]");
			}
			if(!mdx_rows.contains("[项目维度.学科门类]") && statisticDiscipline != null && !statisticDiscipline.isEmpty()) {
				if(statisticDiscipline.trim().equals("马克思主义")) {
					statisticDiscipline = "马克思主义理论/思想政治教育";
				}
				whereStrings.add("[奖励维度.学科门类].[学科门类].[" + statisticDiscipline + "]");
			}
			if(statisticService.getAccountBelongArea(loginer.getAccount().getId()) != null) {
				whereStrings.add(statisticService.getAccountBelongArea(loginer.getAccount().getId()));
			}
			if(whereStrings.size() > 0) {
				mdxBuffer.append(" where (");
				for(int i = 0; i < whereStrings.size() - 1; i++) {
					mdxBuffer.append(whereStrings.get(i) + ",");
				}
				mdxBuffer.append(whereStrings.get(whereStrings.size() - 1));
				mdxBuffer.append(")");
			}
			mdx = mdxBuffer.toString();
//			listMap.put("mdx", mdxBuffer.toString());
//			System.out.println(mdxBuffer);
//			DesUtils des = new DesUtils();
//			encryptedMdxQueryString = des.encrypt(mdxBuffer.toString());
//			request.getSession().setAttribute("measureNum", statistic_index.split(",").length);

			statistic_parm.add("统计指标=" + statisticService.getFormatedData(statisticIndex));
			statistic_parm.add("统计数据=" + statisticService.getFormatedData(statisticData));
			if(statistic_start_sesison.compareTo(statistic_end_sesison) < 0) {
				statistic_parm.add("统计届次=第" + statistic_start_sesison + "届 至 第" + statistic_end_sesison  + "届");
			} else if(statistic_start_sesison.compareTo(statistic_end_sesison) == 0) {
				statistic_parm.add("统计届次=第" + statistic_start_sesison + "届");
			}
			if(statisticUniversityName != null && !statisticUniversityName.isEmpty()) {
				statistic_parm.add("高校名称=" + statisticUniversityName);
			}
			if(statisticDiscipline != null && !statisticDiscipline.isEmpty()) {
				statistic_parm.add("学科门类=" + statisticDiscipline);
			}
			if(sort_type != 3) {
				String sortString = "";
				if(statistic_sortType == 1) {
					sortString = "(降序)";
				} else if(statistic_sortType == 2) {
					sortString = "(升序)";
				}
				statistic_parm.add("排序列=" + statisticService.getFormatedData(statisticSortColumn) + sortString);
			}
//			Map session = ActionContext.getContext().getSession();
		}
		System.out.println(mdx);
		Map session = ActionContext.getContext().getSession();
		session.put("statistic_parm", statistic_parm);
		session.put("mdx", mdx);
		session.put("statisticType", statisticType);
		session.put("chartConfigTmp", chartConfig);
		return SUCCESS;
	}
	
	/**
	 * 定制统计切片指标中指定高校的列表和查询
	 * @return
	 */
	public String selectUniversity(){
		String HQL = "select a.id, a.name from Agency a where ";
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		hql.append(HQL);
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
			hql.append(" (a.type = 4 or a.type = 3)");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
			hql.append(" a.type = 4 and a.subjection.id = :unitid");
			map.put("unitid", loginer.getCurrentBelongUnitId());
		} else if(loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(" a.id =:unitid");
			map.put("unitid", loginer.getCurrentBelongUnitId());
		} else{
			hql.append(" 1=0");
		}
		if (keyword != null) {
			hql.append(" and (LOWER(a.name) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		search(hql.toString(),map);		
		return SUCCESS;
	}
	
	//获取定制统计项目年度范围
	public String getProjectYears(){
		Map map = statisticService.getProjectYear();
		jsonMap.put("projectYears", map.keySet());
		return SUCCESS;
	}
	
	//获取定制统计奖励届次范围
	public String getAwardSession(){
		Map map = statisticService.getAwardSession();
		jsonMap.put("awardSession", map.keySet());
		return SUCCESS;
	}

	public String getStatisticType() {
		return statisticType;
	}

	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}
	public Integer getStatistic_startYear() {
		return statistic_startYear;
	}

	public void setStatistic_startYear(Integer statistic_startYear) {
		this.statistic_startYear = statistic_startYear;
	}

	public Integer getStatistic_endYear() {
		return statistic_endYear;
	}

	public void setStatistic_endYear(Integer statistic_endYear) {
		this.statistic_endYear = statistic_endYear;
	}

	public String getStatistic_university() {
		return statistic_university;
	}

	public void setStatistic_university(String statistic_university) {
		this.statistic_university = statistic_university;
	}

	public String getStatistic_discipline() {
		return statistic_discipline;
	}

	public void setStatistic_discipline(String statistic_discipline) {
		this.statistic_discipline = statistic_discipline;
	}

	public String getStatistic_projectType() {
		return statistic_projectType;
	}

	public void setStatistic_projectType(String statistic_projectType) {
		this.statistic_projectType = statistic_projectType;
	}

	public String getStatistic_subType() {
		return statistic_subType;
	}

	public void setStatistic_subType(String statistic_subType) {
		this.statistic_subType = statistic_subType;
	}

	public String getStatistic_projectArea() {
		return statistic_projectArea;
	}

	public void setStatistic_projectArea(String statistic_projectArea) {
		this.statistic_projectArea = statistic_projectArea;
	}

	public String getStatistic_productType() {
		return statistic_productType;
	}

	public void setStatistic_productType(String statistic_productType) {
		this.statistic_productType = statistic_productType;
	}

	public String getStatistic_province() {
		return statistic_province;
	}

	public void setStatistic_province(String statistic_province) {
		this.statistic_province = statistic_province;
	}

	public String getStatistic_evaluType() {
		return statistic_evaluType;
	}

	public void setStatistic_evaluType(String statistic_evaluType) {
		this.statistic_evaluType = statistic_evaluType;
	}

	public String getStatistic_universityType() {
		return statistic_universityType;
	}

	public void setStatistic_universityType(String statistic_universityType) {
		this.statistic_universityType = statistic_universityType;
	}
	public Integer getStatistic_start_sesison() {
		return statistic_start_sesison;
	}

	public void setStatistic_start_sesison(Integer statistic_start_sesison) {
		this.statistic_start_sesison = statistic_start_sesison;
	}

	public Integer getStatistic_end_sesison() {
		return statistic_end_sesison;
	}

	public void setStatistic_end_sesison(Integer statistic_end_sesison) {
		this.statistic_end_sesison = statistic_end_sesison;
	}

	public String getStatistic_university_name() {
		return statistic_university_name;
	}

	public void setStatistic_university_name(String statistic_university_name) {
		this.statistic_university_name = statistic_university_name;
	}
	public String getChartShowIndexes() {
		return chartShowIndexes;
	}

	public void setChartShowIndexes(String chartShowIndexes) {
		this.chartShowIndexes = chartShowIndexes;
	}

	public String getMdx_rows() {
		return mdx_rows;
	}

	public void setMdx_rows(String mdx_rows) {
		this.mdx_rows = mdx_rows;
	}

	public String getMdx_columns() {
		return mdx_columns;
	}

	public void setMdx_columns(String mdx_columns) {
		this.mdx_columns = mdx_columns;
	}

	public String getMdx_sortColumn() {
		return mdx_sortColumn;
	}

	public void setMdx_sortColumn(String mdx_sortColumn) {
		this.mdx_sortColumn = mdx_sortColumn;
	}

	public String getStatistic_data() {
		return statistic_data;
	}

	public void setStatistic_data(String statistic_data) {
		this.statistic_data = statistic_data;
	}

	public String getStatistic_index() {
		return statistic_index;
	}

	public void setStatistic_index(String statistic_index) {
		this.statistic_index = statistic_index;
	}

	public String getStatistic_sortColumn() {
		return statistic_sortColumn;
	}

	public void setStatistic_sortColumn(String statistic_sortColumn) {
		this.statistic_sortColumn = statistic_sortColumn;
	}

	public int getStatistic_showLineNum() {
		return statistic_showLineNum;
	}

	public void setStatistic_showLineNum(int statistic_showLineNum) {
		this.statistic_showLineNum = statistic_showLineNum;
	}

	public int getSort_type() {
		return sort_type;
	}

	public void setSort_type(int sort_type) {
		this.sort_type = sort_type;
	}

	public int getStatistic_sortType() {
		return statistic_sortType;
	}

	public void setStatistic_sortType(int statistic_sortType) {
		this.statistic_sortType = statistic_sortType;
	}

	public int getStatistic_showNonZero() {
		return statistic_showNonZero;
	}

	public void setStatistic_showNonZero(int statistic_showNonZero) {
		this.statistic_showNonZero = statistic_showNonZero;
	}

	public String getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(String chartConfig) {
		this.chartConfig = chartConfig;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public List<String> getStatistic_parm() {
		return statistic_parm;
	}

	public void setStatistic_parm(List<String> statistic_parm) {
		this.statistic_parm = statistic_parm;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}
		
	public String getMdx() {
		return mdx;
	}

	@Override
	public String pageName() {
		return PAGENAME;
	}
}
