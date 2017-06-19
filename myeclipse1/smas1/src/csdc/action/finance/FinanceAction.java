package csdc.action.finance;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.ProjectApplication;
import csdc.bean.University;
import csdc.service.IFinanceService;
import csdc.service.imp.FinanceService;

/**
 * 项目经费核算
 * @author Administrator
 *
 */
public class FinanceAction extends BaseAction {

	private static final long serialVersionUID = -5897320465593770479L;
	
	private IFinanceService financeService;
	private static final String BUFFERMAP = "bufferMap";
	private String disType;//学科门类
	private String area;//区域
	private String proType;//项目类型
	private int year;//项目年份
	List<String> idsList = new ArrayList<String>();//id集合
	List<String> idsList1 = new ArrayList<String>();//拟立项项目id集合
	List<String> idsList2 = new ArrayList<String>();//确定立项项目id集合
	private double planRate;//规划项目比例
	private double youthRate;//青年项目比例
	private double planFee;//规划项目经费上限
	private double youthFee;//青年项目经费上限
	private Integer xbPlanCount;//西部青年立项数
	private Integer xbYouthCount;//西部青年立项数
	private Integer xjPlanCount;//新疆青年立项数
	private Integer xjYouthCount;//新疆青年立项数
	private Integer xzPlanCount;//西藏青年立项数
	private Integer xzYouthCount;//西藏青年立项数
	private Integer zcCount;//自筹立项数
	
	/**
	 * 高校导入省份信息
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public String importExcel()	{
		List<University> uniList = baseService.query("from University u");
		Map<String, University> uniMap = new HashMap<String, University>();
		for(int i = 0; i < uniList.size(); i++){
			uniMap.put(uniList.get(i).getCode(), uniList.get(i));
		}
		
		//int index = Integer.parseInt(request.getParameter("index"));
		
		String basePath = "C:\\Documents and Settings\\Administrator\\桌面\\smas需求分析\\";
		
		String[] files = new String[] {
			"成人高校代码（2011年1月20日）.xls", "独立学院代码（2011年1月20日）.xls",
			"分校、大专班代码（2011年1月20日）.xls", "普通高校代码（2011年1月20日）.xls"
		};
		
//		Session session = baseService.getSession();
//		Transaction tx = session.beginTransaction();
//		tx.begin();
		
		String file = "";
		for(String s : files){
			file = basePath + s;
			try {
				Workbook wk = Workbook.getWorkbook(new FileInputStream(file));
				Sheet sh0 = wk.getSheet(0);
				String uniCode; University university;
				List<String[]> noList = new ArrayList<String[]>();
				for(int i = 1; i < sh0.getRows(); i++) {
					uniCode = sh0.getCell(0, i).getContents();
					university = uniMap.get(uniCode);
					
					if(null != university){
						university.setProvinceCode(sh0.getCell(2, i).getContents());
						String pro = sh0.getCell(3, i).getContents().replaceAll("\\s+", "");
						university.setProvinceName(pro);
//						session.update(university);
					} else {
						String[] noArr = new String[]{
							uniCode, 
							sh0.getCell(1, i).getContents()
						};
						noList.add(noArr);
					}
				}
				System.out.println("----------------------------未搜索高校记录-------------------------------");
				for(String[] o : noList) {
					System.out.println("高校代码：" + o[0] + "高校名称：" + o[1]);
				}
//				tx.commit();
			} catch (Exception e) {
//				tx.rollback();
				e.printStackTrace();
			} 
//			finally{
//				baseService.releaseSession(session);
//			}
		}
		return NONE;
	}
	
	/**
	 * 生成随机0-100评审测试分数/投票数
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public String generateScore() {
		int count = 0, size = 0;
		do {
			List<ProjectApplication> proList = baseService.query("from ProjectApplication p where p.type = 'general' and p.year = '2010' and p.voteNumber = 0", count, 1000);
			for(ProjectApplication project : proList){
				int voteNumber = new Random().nextInt(6);
				project.setVoteNumber(voteNumber);
			}
			size = proList.size();
			count += 1000;
		} while (size > 0);
		return NONE;
	}
	
	
	public String toCheck()	{
		return SUCCESS;
	}
	
	//初始化
	@SuppressWarnings("unchecked")
	public String init(){
		Map session = ActionContext.getContext().getSession();
		year = (Integer)session.get("defaultYear");
		if(planFee == 0 || youthFee == 0){
			planFee = 9;
			youthFee = 7;
		}
		session.put("planFee", planFee);
		session.put("youthFee", youthFee);
		Map dataMap = financeService.init(year, planFee, youthFee);
		jsonMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		session.put(FinanceAction.BUFFERMAP, dataMap);
		return SUCCESS;
	}
	
	//比例核算
	@SuppressWarnings("unchecked")
	public String checkByRate(){
		Map session = ActionContext.getContext().getSession();
		Map dataMap = (Map)session.get(FinanceAction.BUFFERMAP);
		
		session.put("planFee", planFee);
		session.put("youthFee", youthFee);
		
		Map setMap = new HashMap();
		setMap.put("青年基金项目", youthRate);
		setMap.put("规划基金项目", planRate);
		setMap.put("规划项目经费", planFee);
		setMap.put("青年项目经费", youthFee);
		setMap.put("自筹经费项目", zcCount);
		
		Map xbMap = new HashMap();
		xbMap.put("青年基金项目", xbYouthCount);
		xbMap.put("规划基金项目", xbPlanCount);
		setMap.put("西部", xbMap);
		
		Map xjMap = new HashMap();
		xjMap.put("青年基金项目", xjYouthCount);
		xjMap.put("规划基金项目", xjPlanCount);
		setMap.put("新疆", xjMap);
		
		Map xzMap = new HashMap();
		xzMap.put("青年基金项目", xzYouthCount);
		xzMap.put("规划基金项目", xzPlanCount);
		setMap.put("西藏", xzMap);
		
		dataMap = financeService.checkByRate(dataMap, setMap);
		jsonMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		
		session.put(FinanceAction.BUFFERMAP, dataMap);
		
		return SUCCESS;
		
	}
	
	//微观核算初始化
	@SuppressWarnings("unchecked")
	public String initCheckById() throws UnsupportedEncodingException{
		Map session = ActionContext.getContext().getSession();
		Map dataMap = (Map)session.get(FinanceAction.BUFFERMAP);
		
		disType = URLDecoder.decode(disType, "UTF-8");
		area = URLDecoder.decode(area, "UTF-8");
		proType = URLDecoder.decode(proType, "UTF-8");
		
		session.put("disType", disType);
		session.put("area", area);
		session.put("proType", proType);
		
		pageList = financeService.initCheckById(dataMap, disType, area, proType);
		
		return SUCCESS;
		
	}
	
	//微观核算
	@SuppressWarnings("unchecked")
	public String checkById(){
		Map session = ActionContext.getContext().getSession();
		Map dataMap = (Map)session.get(FinanceAction.BUFFERMAP);
		
		planFee = (Double)session.get("planFee");
		youthFee = (Double)session.get("youthFee");
		disType = (String)session.get("disType");
		area = (String)session.get("area");
		proType = (String)session.get("proType");
		
		//选中的idList从前台传过来
		dataMap = financeService.checkById(dataMap, idsList1, idsList2, disType, area, proType, planFee, youthFee);
		jsonMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		
		session.put(FinanceAction.BUFFERMAP, dataMap);
		
		return SUCCESS;
	}
	
	//微观核算初始化
	@SuppressWarnings("unchecked")
	public String initCheckZcById() throws UnsupportedEncodingException{
		Map session = ActionContext.getContext().getSession();
		Map dataMap = (Map)session.get(FinanceAction.BUFFERMAP);
		disType = URLDecoder.decode(disType, "UTF-8");
		session.put("disType", disType);
		pageList = financeService.initCheckZcById(dataMap, disType);
		return SUCCESS;
		
	}
	
	//微观核算
	@SuppressWarnings("unchecked")
	public String checkZcById(){
		Map session = ActionContext.getContext().getSession();
		Map dataMap = (Map)session.get(FinanceAction.BUFFERMAP);
		disType = (String)session.get("disType");
		//选中的idList从前台传过来
		dataMap = financeService.checkZcById(dataMap, idsList, disType);
		jsonMap = (Map)dataMap.get(FinanceService.CLIENTDATA);
		session.put(FinanceAction.BUFFERMAP, dataMap);
		return SUCCESS;
	}
	
	//完成核算
	@SuppressWarnings("unchecked")
	public String finish(){
		Map session = ActionContext.getContext().getSession();
		Map dataMap = (Map)session.get(FinanceAction.BUFFERMAP);
		
		financeService.finish(dataMap);
		
		return SUCCESS;
	}
	
	
	
	public void setFinanceService(IFinanceService financeService) {
		this.financeService = financeService;
	}
	
	public String getDisType() {
		return disType;
	}

	public void setDisType(String disType) {
		this.disType = disType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public List<String> getIdsList() {
		return idsList;
	}

	public void setIdsList(List<String> idsList) {
		this.idsList = idsList;
	}
	
	public List<String> getIdsList1() {
		return idsList1;
	}

	public void setIdsList1(List<String> idsList1) {
		this.idsList1 = idsList1;
	}

	public List<String> getIdsList2() {
		return idsList2;
	}

	public void setIdsList2(List<String> idsList2) {
		this.idsList2 = idsList2;
	}
	
	public double getPlanRate() {
		return planRate;
	}

	public void setPlanRate(double planRate) {
		this.planRate = planRate;
	}

	public double getYouthRate() {
		return youthRate;
	}

	public void setYouthRate(double youthRate) {
		this.youthRate = youthRate;
	}

	public Integer getXbPlanCount() {
		return xbPlanCount;
	}

	public void setXbPlanCount(Integer xbPlanCount) {
		this.xbPlanCount = xbPlanCount;
	}

	public Integer getXbYouthCount() {
		return xbYouthCount;
	}

	public void setXbYouthCount(Integer xbYouthCount) {
		this.xbYouthCount = xbYouthCount;
	}

	public Integer getXjPlanCount() {
		return xjPlanCount;
	}

	public void setXjPlanCount(Integer xjPlanCount) {
		this.xjPlanCount = xjPlanCount;
	}

	public Integer getXjYouthCount() {
		return xjYouthCount;
	}

	public void setXjYouthCount(Integer xjYouthCount) {
		this.xjYouthCount = xjYouthCount;
	}

	public Integer getXzPlanCount() {
		return xzPlanCount;
	}

	public void setXzPlanCount(Integer xzPlanCount) {
		this.xzPlanCount = xzPlanCount;
	}

	public Integer getXzYouthCount() {
		return xzYouthCount;
	}

	public void setXzYouthCount(Integer xzYouthCount) {
		this.xzYouthCount = xzYouthCount;
	}

	public void setPlanFee(double planFee) {
		this.planFee = planFee;
	}

	public double getPlanFee() {
		return planFee;
	}

	public void setYouthFee(double youthFee) {
		this.youthFee = youthFee;
	}

	public double getYouthFee() {
		return youthFee;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Integer getZcCount() {
		return zcCount;
	}

	public void setZcCount(Integer zcCount) {
		this.zcCount = zcCount;
	}

	@Override
	public String[] column() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String simpleSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String pageBufferId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}