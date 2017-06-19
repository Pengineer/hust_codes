package csdc.action.dataMining.association;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.dataMining.DadaMiningBaseAction;
import csdc.dao.SqlBaseDao;
import csdc.service.IAssociationService;
import csdc.tool.dataMining.association.AssociationRulesAnalyzer;
import csdc.tool.dataMining.association.FPGrowthAnalyzer;
import csdc.tool.info.GlobalInfo;

/**
 * 数据挖掘：关联规则挖掘
 * @author fengcl
 */
@SuppressWarnings("deprecation")
public class AssociationAction extends DadaMiningBaseAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SqlBaseDao sqlDao;
	
	private IAssociationService associationService;

	private String type;		//分析类型
	private int topKnum;		//top k，前K条（topKnum默认取100）
	private int analyzeAngle;	//分析角度
	private int analyze_startYear;		//分析年度
	private int analyze_endYear;		//分析年度
	
	private long minFrequency;//500
	
	private double minSupport;//0.0003
	
	private double minConfidence;//0.3
	
	private String queryString;
	private int languageType;	//查询语句类型
	private Long costTime;
	
	//导出
	private String fileFileName;//导出文件名
	
	/**
	 * 导出excel
	 * @return
	 */
	public String exportExcel(){
		return SUCCESS;
	}
	
	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception{
		String analysisType = (String) session.get("analysisType");
		Integer angle = (Integer) session.get("analyzeAngle");
		int topK = (Integer) session.get("topKnum");
		if("discipline".equals(analysisType)){
			analysisType = "学科关联性分析";
		}else if("university".equals(analysisType)){
			analysisType = "高校关联性分析";
		}else if("member".equals(analysisType)){
			analysisType = "研究人员关联性分析";
		}
		String associateAngle = "";
		if(angle == 0){
			associateAngle = "申报数据";
			topK = (topK > 10) ? 10 : topK;
		}else if(angle == 1){
			associateAngle = "立项数据";
			topK = (topK > 10) ? 10 : topK;
		}else if(angle == 2){
			associateAngle = "批准经费";
		}
		String header = associateAngle + analysisType + "TOP" + topK + "结果";
		//导出的Excel文件名
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		//获取Excel数据
		if(null == session.get("dataList")){
			return null;
		}
		List dataList = (List) session.get("dataList");	//Excel正文数据源
		return associationService.commonExportExcel(dataList, header);
	}
		
	/**
	 * 进入项目关联性分析页面
	 * @return
	 */
	public String toAnalyze(){
		session.put("analysisType", type);			//分析类型
		session.put("analyzeAngle", analyzeAngle);	//分析角度
		session.put("topKnum", topKnum);			//挖掘条数
		session.put("minFrequency", minFrequency);	//最小支持度
		session.put("toDataBase", toDataBase);		//是否入库
		session.put("startYear", analyze_startYear);		//分析年度
		session.put("endYear", analyze_endYear);			//分析年度
		return SUCCESS;
	}
	
	/**
	 * 关联性分析
	 * @return
	 */
	public String analyze(){
		//参数初始化
		type = (String) session.get("analysisType");
		analyzeAngle = (Integer) session.get("analyzeAngle");
		topKnum = (Integer) session.get("topKnum");
		minFrequency = (Long) session.get("minFrequency");
		toDataBase = (Integer) session.get("toDataBase");
		int startYear = (Integer) session.get("startYear");
		int endYear = (Integer) session.get("endYear");
		//如果分析角度为项目经费
		if (analyzeAngle == 2 && "university".equals(type)) {
			// 获取图表数据
			jsonMap = associationService.universityAssociation(topKnum, toDataBase, startYear, endYear);
		}else {
			// 获取图表数据
			jsonMap = associationService.handleAnalyze(type, analyzeAngle, topKnum, minFrequency, toDataBase, startYear, endYear);
		}
		session.put("dataList", jsonMap.get("dataList"));
		return SUCCESS;
	}
	
	/**
	 * 进入项目研究热点配置页面
	 * @return
	 */
	public String toConfig(){
		return SUCCESS;
	}
	
	/**
	 * 进入定制页面
	 * @return
	 */
	public String toCustom(){
		return SUCCESS;
	}
	
	/**
	 * 获取关联规则
	 * 例如：
	 * 1、select p.c_last_degree, p.c_title, p.c_gender, p.c_age_group from s_d_person p	<br>
	 * 2、select ac.specialityTitle, ac.lastDegree from Academic ac<br>
	 * 3、select pa.projectType, pa.productType, ag.name, pa.discipline, ac.discipline, ac.specialityTitle, ac.lastDegree from ProjectGranted pg left join pg.university ag, ProjectApplication pa, ProjectMember pm, Academic ac where pg.applicationId = pa.id and pg.memberGroupNumber = pm.groupNumber and pa.id = pm.applicationId and ac.person.id = pm.member.id<br>
	 * @return
	 */
	public String fetchAssociationRules(){
		long begin = System.currentTimeMillis();
		
		List<String> rules = null;
		try {
			String queryStr = queryString.toLowerCase();
			String selectCase = queryStr.substring(0, queryStr.indexOf("from"));
			if (!selectCase.contains(",")) {
				jsonMap.put(GlobalInfo.ERROR_INFO, "查询字段数太少，至少两个！");
				return INPUT;
			}
			if (queryStr.trim().indexOf("select") != 0) {
				jsonMap.put(GlobalInfo.ERROR_INFO, "请输入正确的查询语句！");
				return INPUT;
			}
			if (queryStr.contains("delete") || queryStr.contains("insert") || queryStr.contains(";")) {
				jsonMap.put(GlobalInfo.ERROR_INFO, "只支持select查询语句，请输入正确的查询语句！");
				return INPUT;
			}
			
			List<Object[]> dataItems = null;
	
			if (languageType == 0) {
				dataItems = dao.query(queryString);
			}else {
				dataItems = sqlDao.query(queryString);
			}
			
			FPGrowthAnalyzer fpgAnalyzer = new FPGrowthAnalyzer(dataItems, minFrequency, 100, new HashSet<String>());
			
			List<Pair<String, TopKStringPatterns>> frequentPatterns = fpgAnalyzer.work();
			Map<Object, Long> frequency = fpgAnalyzer.readFrequency();
			
			rules = AssociationRulesAnalyzer.generateRules(frequentPatterns, frequency, dataItems.size(), minSupport, minConfidence);
			
//			System.out.println(frequentPatterns.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		costTime = System.currentTimeMillis() - begin;
		jsonMap.put("rules", rules);
		jsonMap.put("costTime", costTime);
		jsonMap.put("minSupport", minSupport);
		jsonMap.put("minConfidence", minConfidence);
		return SUCCESS;
	}
	
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public long getMinFrequency() {
		return minFrequency;
	}
	public void setMinFrequency(long minFrequency) {
		this.minFrequency = minFrequency;
	}
	public int getLanguageType() {
		return languageType;
	}
	public void setLanguageType(int languageType) {
		this.languageType = languageType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTopKnum() {
		return topKnum;
	}
	public void setTopKnum(int topKnum) {
		this.topKnum = topKnum;
	}

	public double getMinSupport() {
		return minSupport;
	}
	public void setMinSupport(double minSupport) {
		this.minSupport = minSupport;
	}
	public double getMinConfidence() {
		return minConfidence;
	}
	public void setMinConfidence(double minConfidence) {
		this.minConfidence = minConfidence;
	}

	public int getAnalyze_startYear() {
		return analyze_startYear;
	}

	public void setAnalyze_startYear(int analyze_startYear) {
		this.analyze_startYear = analyze_startYear;
	}

	public int getAnalyze_endYear() {
		return analyze_endYear;
	}

	public void setAnalyze_endYear(int analyze_endYear) {
		this.analyze_endYear = analyze_endYear;
	}

	@Override
	protected List<Object[]> listData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] column() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pageBufferId() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAnalyzeAngle() {
		return analyzeAngle;
	}

	public void setAnalyzeAngle(int analyzeAngle) {
		this.analyzeAngle = analyzeAngle;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public IAssociationService getAssociationService() {
		return associationService;
	}

	public void setAssociationService(IAssociationService associationService) {
		this.associationService = associationService;
	}
}
