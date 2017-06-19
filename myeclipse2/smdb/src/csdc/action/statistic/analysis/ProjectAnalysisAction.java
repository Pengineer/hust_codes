package csdc.action.statistic.analysis;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import csdc.action.BaseAction;
import csdc.bean.ProjectGranted;
import csdc.tool.HSSFExport;

/**
 * 项目关联性分析
 * @author fengcl
 *
 */
public class ProjectAnalysisAction extends BaseAction{

	private static final long serialVersionUID = 4699270454061045276L;
	private String fileFileName;	
	private int firstYear;			//年度
	private int secondYear;			//年度
	private int yearInterval;		//年度区间
	
	//用于教育部人文社会科学研究一般项目限项申报核算表
	private int accountingYear;		//待核算项目年度
	private int midStartYear;		//应中检起始年度
	private int midEndYear;			//应中检终止年度
	private int endStartYear;		//应结项起始年度
	private int endEndYear;			//应结项终止年度

	/**
	 * 进入分析页面
	 * @return
	 */
	public String toAnalysis(){
		return SUCCESS;
	}
	
	/**
	 * firstYear（如2012）年结项人员在secondYear（如2013）年的项目申报情况
	 * @return
	 */
	public String exportApplyInEnd() {
		return SUCCESS;
	}
	
	/**
	 * firstYear（例如：2012）年结项人员在secondYear（例如：2013）年的项目申报情况
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		//firstYear（如2012）年结项
		String hqlEnd = "select pg.applicantId, pg.applicantName, pg.name, pg.number, to_char(pe.finalAuditDate, 'yyyy-MM-dd') from ProjectEndinspection pe, ProjectGranted pg " +
			"where pe.grantedId = pg.id and pe.finalAuditResultEnd = 2 and pe.finalAuditStatus = 3 and to_char(pe.finalAuditDate, 'yyyy') = " + firstYear;
		List<Object[]> listEnd = dao.query(hqlEnd);
		//负责人ID到结项项目信息的映射
		Map<Object, List<Object[]>> ids2EndMap = new HashMap<Object, List<Object[]>>();
		for (Object[] ends : listEnd) {
			Object applicantId = ends[0];
			List<Object[]> projects = ids2EndMap.get(applicantId);
			if (null == projects) {
				projects = new ArrayList<Object[]>();
				ids2EndMap.put(applicantId, projects);
			}
			projects.add(ends);
		}
		//secondYear（如2013）年申报
		String hqlApply = "select pa.applicantId, pa.name from ProjectApplication pa where pa.year = " + secondYear;
		List<Object[]> listApply = dao.query(hqlApply);
//		//增加申报项目立项与否的信息
//		List<Object[]> listApply = new ArrayList<Object[]>();
//		for(Object[] o: list){
//			String[] os = {"", "", ""};
//			os[0] = o[0].toString();
//			os[1] = o[1].toString();
//			String hql = "select pg from ProjectGranted pg where pg.applicationId =:applicationId";
//			Map map = new HashMap();
//			map.put("applicationId", o[0]);
//			ProjectGranted pg = (ProjectGranted)dao.queryUnique(hql, map);//一次20ms左右，40000条以上的数据，耗时超过十分钟
//			if(pg != null){
//				os[2] = "已立项";
//			} else {
//				os[2] = "未立项";
//			}
//			listApply.add(os);
//		}
		//负责人ID到申报项目信息的映射
		Map<Object, List<Object[]>> ids2ApplyMap = new HashMap<Object, List<Object[]>>();
		for (Object[] applys : listApply) {
			Object applicantId = applys[0];
			if (ids2EndMap.containsKey(applicantId)) {//只考虑结项项目中的负责人
				List<Object[]> projects = ids2ApplyMap.get(applicantId);
				if (null == projects) {
					projects = new ArrayList<Object[]>();
					ids2ApplyMap.put(applicantId, projects);
				}
				projects.add(applys);
			}
		}
		//组装导出数据
		List dataList = new ArrayList();
		//按照项目负责人逐个遍历
		for (Entry<Object, List<Object[]>> entry: ids2ApplyMap.entrySet()) {
			Object applicantId = entry.getKey();
			List<Object[]> applyProjects = entry.getValue();//申报项目
            List<Object[]> endProjects = ids2EndMap.get(applicantId);//结项项目 
            int applySize = applyProjects.size();
            int endSize = endProjects.size();
            int maxRows = applySize;//当前负责人的最多项目数
            //修正申报项目和结项项目List大小相同，便于导出
            if (applySize > endSize) {
            	for (int j = 0; j < applySize - endSize; j++) {
            		endProjects.add(null);
            	}
            	maxRows = applySize;
            }else if(applySize < endSize){
            	for (int j = 0; j < endSize - applySize; j++) {
            		applyProjects.add(null);
            	}
            	maxRows = endSize;
            }
            Object applicantName = endProjects.get(0)[1];//负责人
            //一个负责人可能对应多个项目
            for(int i = 0; i < maxRows; i++){
            	Object[] applys = applyProjects.get(i);//申报项目信息
            	Object[] ends = endProjects.get(i);	   //结项项目信息	
            	//创建合并信息的数组
            	Object[] datas = new Object[5];
            	datas[0] = applicantName;						//负责人姓名
            	datas[1] = (null == applys) ? null : applys[1];	//2013年申报项目名称
            	datas[2] = (null == ends) ? null : ends[2];		//2012年结项项目名称
            	datas[3] = (null == ends) ? null : ends[3];		//2012年项目批准号
            	datas[4] = (null == ends) ? null : ends[4];		//2012年结项时间
            	dataList.add(datas);
            }
		}
		//调用导出接口
		String header = firstYear + "年结项人员在" + secondYear + "年的项目申报情况";
		String[] title = {"负责人姓名", secondYear + "年申报项目名称", firstYear + "年结项项目名称", firstYear + "年项目批准号", firstYear + "年结项时间"};
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		return HSSFExport.commonExportExcel(dataList, header, title);
	}

	/**
	 * 教育部人文社会科学研究一般项目限项申报核算表
	 * yearInterval（如3）
	 * @return
	 */
	public String exportAccountingTable(){
		return SUCCESS;
	}
	
	/**
	 * 教育部人文社会科学研究一般项目限项申报核算表
	 * 由于2011年度处理规则特殊，以2011为分界线，具体如下：
	 * 待核算项目年度为2014：则申报、立项起始年度为2011-2013，中检起始年度为2008-2010，结项起始年度为2007-2009
	 * 待核算项目年度为2015：则申报、立项起始年度为2012-2014，中检起始年度为2010-2012，结项起始年度为2008-2010
	 * 待核算项目年度为2016：则申报、立项起始年度为2013-2015，中检起始年度为2011-2013，结项起始年度为2010-2012
	 * 待核算项目年度为2017：则申报、立项起始年度为2014-2016，中检起始年度为2012-2014，结项起始年度为2011-2013
	 * 以后逐年加1
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public InputStream getDownloadAccountingFile() throws UnsupportedEncodingException{
		//西部地区（不包含新疆，西藏）
		String[] xbAreas = new String[]{
			"重庆市", "四川省", "贵州省", "云南省", "陕西省", "甘肃省", "宁夏回族自治区", "青海省", "内蒙古自治区", "广西壮族自治区"
		};	
		List<String> xbAreaList = Arrays.asList(xbAreas);
		//所有高校
		String hqlUniv = "select ag.id, ag.name, ag.type, pr.name from Agency ag left join ag.province pr where (ag.type=3 and ag.organizer is not null) or ag.type=4 order by ag.name asc";
		List<Object[]> univs = dao.query(hqlUniv);
		//专职社科活动人员数量
		String hqlPerson = "select t.university.id, count(t.id) from Teacher t where t.type = '专职人员' group by t.university.id";
		List<Object[]> persons = dao.query(hqlPerson);
		Map personMap = new HashMap();
		for (Object[] objs : persons) {
			personMap.put(objs[0], objs[1]);
		}
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); 	
//		int nowYear = Integer.valueOf(sdf.format(new Date()));	//立项终止年度，当前年
//		int midEndYear = nowYear - 2;							//中检终止年度，为当前年减去2年
//		int endEndYear = nowYear - 3;							//结项终止年度，为当前年减去3年
//		int graStartYear = nowYear - yearInterval + 1;			//立项起始年度，间隔为yearInterval年
//		int midStartYear = midEndYear - yearInterval + 1;		//中检起始年度，间隔为yearInterval年
//		int endStartYear = endEndYear - yearInterval + 1;		//结项起始年度，间隔为yearInterval年
		
//		int nowYear = 2013;					//立项终止年度，当前年
		int graStartYear = 2009;	//立项起始年度
		int graEndYear = 2013;//立项终止年度，为待核实年度减1年
		midStartYear = 2006;				//中检起始年度，间隔为yearInterval年
		midEndYear = 2010;					//中检终止年度，为当前年减去2年
		endStartYear = 2005;				//结项起始年度，间隔为yearInterval年
		endEndYear = 2007;					//结项终止年度，为当前年减去5年
		//申报数
		String hqlApp = "select pa.university.id, count(pa.id) from ProjectApplication pa where pa.projectType = 'general' and pa.year between " + graStartYear + " and " + graEndYear + " group by pa.university.id"; 
		List<Object[]> apps = dao.query(hqlApp);
		Map appMap = new HashMap();
		for (Object[] objs : apps) {
			appMap.put(objs[0], objs[1]);
		}
		//立项数
		String hqlGra = "select pg.university.id, count(pg.id) from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.projectType = 'general' and pa.year between " + graStartYear + " and " + graEndYear + " group by pg.university.id"; 
		try {
			dao.query(hqlGra);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Object[]> gras = dao.query(hqlGra);
		Map graMap = new HashMap();
		for (Object[] objs : gras) {
			graMap.put(objs[0], objs[1]);
		}
		
		//中检年度区间的立项数
		String hqlGra4Mid = "select pg.university.id, count(pg.id) from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.projectType = 'general' and pa.year between " + midStartYear + " and " + midEndYear + " group by pg.university.id"; 
		List<Object[]> gra4Mids = dao.query(hqlGra4Mid);
		Map gra4MidMap = new HashMap();
		for (Object[] objs : gra4Mids) {
			gra4MidMap.put(objs[0], objs[1]);
		}
		//按期中检
		String hqlMid = "select pg.university.id, count(pm.id) from ProjectMidinspection pm, ProjectGranted pg, ProjectApplication pa where pm.grantedId = pg.id and pg.applicationId = pa.id and pa.projectType = 'general' and " +
				"pm.finalAuditResult = 2 and pm.finalAuditStatus = 3 and (to_char(pm.finalAuditDate, 'yyyy') - pa.year) <= 2 and pa.year between " + midStartYear + " and " + midEndYear + " group by pg.university.id"; 
		List<Object[]> mids = dao.query(hqlMid);
		Map midMap = new HashMap();
		for (Object[] objs : mids) {
			midMap.put(objs[0], objs[1]);
		}
		
		//结项年度区间的立项数
		String hqlGra4End = "select pg.university.id, count(pg.id) from ProjectGranted pg, ProjectApplication pa where pg.applicationId = pa.id and pa.projectType = 'general' and pa.year between " + endStartYear + " and " + endEndYear + " group by pg.university.id"; 
		List<Object[]> gra4Ends = dao.query(hqlGra4End);
		Map gra4EndMap = new HashMap();
		for (Object[] objs : gra4Ends) {
			gra4EndMap.put(objs[0], objs[1]);
		}
		//按期结项
		String hqlEnd = "select pg.university.id, count(pe.id) from ProjectEndinspection pe, ProjectGranted pg, ProjectApplication pa where pe.grantedId = pg.id and pg.applicationId = pa.id and pa.projectType = 'general' and " +
				"pe.finalAuditResultEnd = 2 and pe.finalAuditStatus = 3 and (to_char(pe.finalAuditDate, 'yyyy') - pa.year) <= 5 and pa.year between " + endStartYear + " and " + endEndYear + " group by pg.university.id"; 
		List<Object[]> ends = dao.query(hqlEnd);
		Map endMap = new HashMap();
		for (Object[] objs : ends) {
			endMap.put(objs[0], objs[1]);
		}
		
		//组装导出数据
		List dataList = new ArrayList();
		for (Object[] objs : univs) {
			String univId = (String) objs[0];
			Object[] datas = new Object[24];
			datas[0] = objs[1];//高校名称
			datas[1] = ((Integer)objs[2] == 3) ? "部属" : null;//高校类型（部属）
			datas[2] = xbAreaList.contains(objs[3]) ? "西部" : null;//所在地区（西部）
			datas[3] = objs[3];//所在省份（新疆/西藏）
			
//			datas[1] = (personMap.containsKey(univId)) ? personMap.get(univId) : 0;//研究人员数量
			datas[4] = (appMap.containsKey(univId)) ? appMap.get(univId) : 0;//申报数量
			datas[5] = (graMap.containsKey(univId)) ? graMap.get(univId) : 0;//立项数量
			datas[6] = calculate(datas[5], datas[4]);//立项率
			
			datas[7] = (gra4MidMap.containsKey(univId)) ? gra4MidMap.get(univId) : 0;//立项数量
			datas[8] = (midMap.containsKey(univId)) ? midMap.get(univId) : 0;//按期中检数量
			datas[9] = calculate(datas[8], datas[7]);//按期中检率
			
			datas[10] = (gra4EndMap.containsKey(univId)) ? gra4EndMap.get(univId) : 0;//立项数量
			datas[11] = (endMap.containsKey(univId)) ? endMap.get(univId) : 0;//按期结项数量
			datas[12] = calculate(datas[11], datas[10]);//按期结项率
			
			datas[13] = null;//最近一届获奖（成果奖）
			
			datas[14] = null;//年度限项申报基数（往年平均申报数）
			datas[15] = null;//立项率奖励数
			datas[16] = null;//逾期中检惩罚数
			datas[17] = null;//逾期结项惩罚数
			datas[18] = null;//奖励得分奖励数
			datas[19] = null;//初算：限项申报数量
			datas[20] = null;//部属高校倾斜
			datas[21] = null;//西部高校倾斜
			datas[22] = null;//发布：限项申报数量
			datas[23] = null;//预测：预期申报数量
			dataList.add(datas);
		}
		//按照申报数量降序排列
		Comparator<Object[]> appCountComparator = new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Integer cnt1 = Integer.parseInt(String.valueOf(o1[4]));
				Integer cnt2 = Integer.parseInt(String.valueOf(o2[4]));
				return cnt2.compareTo(cnt1);
			}
		};
		Collections.sort(dataList, appCountComparator);
		
//		//调用导出接口
		String header = "教育部人文社会科学研究一般项目限项申报核算表";
//		String graYear = graStartYear + "-" + graEndYear +""; //立项年度区间，例如2011-2013
//		String midYear = midStartYear + "-" + midEndYear +""; //中检年度区间，例如2011-2013
//		String endYear = endStartYear + "-" + endEndYear +""; //结项年度区间，例如2011-2013
//		String[] title = {"高校名称", "高校类型（部属）", "所在地区（西部）", "所在省份",
////				"专职社科活动人员数量", 
//				graYear + "年度项目申报数量", graYear + "年度项目立项数量", graYear + "年度项目立项率", 
//				midYear + "年度项目立项数量", midYear + "年度项目按期中检数量", midYear + "年度项目按期中检率",  
//				endYear + "年度项目立项数量", endYear + "年度项目按期结项数量", endYear + "年度项目按期结项率",
//				"最近一届获奖",
//				(graEndYear + 1) + "年度项目限项申报数量"
//				};
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		
		List<List> arrayList = new ArrayList<List>();
		arrayList.add(dataList);
		String modelFilePath = "D:/20131114_2014年度教育部人文社会科学研究一般项目限项申报核算表.xls"; 
		//调用导出公共方法
		return HSSFExport.exportFromModel(arrayList, modelFilePath, 8);
		
//		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
	/**
	 * 计算比例（率）
	 * @param dividend	被除数
	 * @param divider	除数
	 * @return	百分数
	 */
	private Object calculate(Object dividend, Object divider){
		Number per = 0;
		DecimalFormat df=(DecimalFormat)NumberFormat.getPercentInstance();
		df.applyPattern("#0.000000000");
		
		if (null != divider && null != dividend) {
			double _dividend = Double.parseDouble(String.valueOf(dividend));
			double _divider = Double.parseDouble(String.valueOf(divider));
			if(_divider != 0){
				double result = _dividend / _divider;
				String percentResult = df.format(result);
				try {
					per = df.parse(percentResult);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {
				try {
					per = df.parse("1");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return per;
	}
	
	@Override
	public String pageName() {
		return null;
	}
	@Override
	public String[] column() {
		return null;
	}
	@Override
	public String dateFormat() {
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return null;
	}

	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public int getFirstYear() {
		return firstYear;
	}
	public void setFirstYear(int firstYear) {
		this.firstYear = firstYear;
	}
	public int getSecondYear() {
		return secondYear;
	}
	public void setSecondYear(int secondYear) {
		this.secondYear = secondYear;
	}
	public int getYearInterval() {
		return yearInterval;
	}
	public void setYearInterval(int yearInterval) {
		this.yearInterval = yearInterval;
	}
	public int getAccountingYear() {
		return accountingYear;
	}
	public void setAccountingYear(int accountingYear) {
		this.accountingYear = accountingYear;
	}
	public int getMidStartYear() {
		return midStartYear;
	}
	public void setMidStartYear(int midStartYear) {
		this.midStartYear = midStartYear;
	}
	public int getMidEndYear() {
		return midEndYear;
	}
	public void setMidEndYear(int midEndYear) {
		this.midEndYear = midEndYear;
	}
	public int getEndStartYear() {
		return endStartYear;
	}
	public void setEndStartYear(int endStartYear) {
		this.endStartYear = endStartYear;
	}
	public int getEndEndYear() {
		return endEndYear;
	}
	public void setEndEndYear(int endEndYear) {
		this.endEndYear = endEndYear;
	}
}
