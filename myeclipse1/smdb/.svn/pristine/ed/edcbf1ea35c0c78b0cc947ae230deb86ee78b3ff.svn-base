package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralVariation;
import csdc.bean.Institute;
import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.InstpVariation;
import csdc.bean.Person;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入未变更的数据（中间表中只有延期一类的变更项目）
 * @author pengliang
 * 备注：导入新变更
 */
@Component
public class ProjectVariationImporterV2 extends Importer {
	
	private int importNumber = 0;
	
	private Map<String, SinossProjectVariation> map;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	private List<SinossMembers> sinossGeneralMembers = new ArrayList<SinossMembers>();
	
	private List<SinossMembers> sinossInstpMembers = new ArrayList<SinossMembers>();
	
	private List<SinossModifyContent> sinossModifyContents = new ArrayList<SinossModifyContent>();
	
	private List error = new ArrayList();
	
	private int agencyError=0;
	
	private List<String> totalDataLast = new ArrayList<String>();
	
	/*
	 * 初始化中间表中所有变更项目的成员
	 */
	private List<SinossMembers> sinossMembers;	
	public void initSinossMembers(){
		Date begin = new Date();
		sinossMembers = dao.query("from SinossMembers o where o.projectVariation is not null");
		System.out.println("initSinossMembers completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	/*
	 * 初始化中间表变更内容
	 */
	private List<SinossModifyContent> initSinossModifyContents;
	public void initSinossModifyContents(){
		Date begin = new Date();
		initSinossModifyContents = dao.query("from SinossModifyContent o where o.projectVariation is not null");
		System.out.println("initSinossModifyContents completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	@Override
	public void work() throws Throwable{
		initSinossMembers();
		initSinossModifyContents();
		importData();
	}

	@SuppressWarnings("unchecked")	
	public void importData() throws Throwable {
	//	List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select o from SinossProjectVariation o where isAdded = 1");
		List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select o from SinossProjectVariation o where to_char(o.dumpDate)='02-9月 -14'");
		importNumber = 0;
		for(int i = 0;i < sinossProjectVariations.size();i++){	
			System.out.println(i+1);
		//	System.out.println("读到第" + (i+1) + "条数据" + "项目名称为：" + sinossProjectVariations.get(i).getName());			
			String codeString = sinossProjectVariations.get(i).getCode();//项目编号
			String projectType = sinossProjectVariations.get(i).getTypeCode();//项目类别
			String variatonReason = sinossProjectVariations.get(i).getModifyReason();//变更原因
			
			Date applyDate = sinossProjectVariations.get(i).getApplyDate();//提交时间
			Date checkDate = sinossProjectVariations.get(i).getCheckDate();//最后审核日期
			int checkStatus = sinossProjectVariations.get(i).getCheckStatus();//审核状态
				
			//基地项目变更
			if (projectType.contains("base")) {				
				//记录变更类型
				StringBuffer deptAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("00000000000000000000");
				String variationId = sinossProjectVariations.get(i).getId();
				
				//找出只有一条变更而且是延期的变更数据
				List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);//获取变更项目的变更内容（项目变更表 -> 变更内容表）；一条变更id可能对应多条变更内容
				List<SinossMembers> sinossInstpMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);
				SinossModifyContent smcTempOne = null;
				if(sinossModifyContents.size() != 1){
					continue;
				}
				if(sinossModifyContents.size() == 1 && sinossInstpMembers.size() == 0){
					smcTempOne = sinossModifyContents.iterator().next();
					String modifyMean = smcTempOne.getModifyFieldMean();
					if(!modifyMean.contains("延期")){
						continue;
					}
				} else {
					continue;
				}
				//判断这条延期变更数据是否已经入库
				InstpGranted granted = instpProjectFinder.findGranted(codeString);
    			Set<InstpVariation> instpVariations = granted.getInstpVariation();
    			boolean isImported = false;
				for(InstpVariation iv : instpVariations){
    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getPostponement() == 1){
    					isImported = true;
    					break;
    				}
    			}
				if(isImported){
					continue;
				}	
											
				importNumber++;
				System.out.println("项目名称为：" + sinossProjectVariations.get(i).getName());
				totalDataLast.add(sinossProjectVariations.get(i).getName());
				InstpVariation instpVariation = new InstpVariation();				
				granted.addVariation(instpVariation);//建立关联关系
				sinossProjectVariations.get(i).setProjectVariation(instpVariation); //中间表中C_VARIATION_ID是我们入库的时候要加进去的，表示这条数据和我们库里面的数据时对应的
				sinossProjectVariations.get(i).setProjectGranted(granted);//将立项实体填入临时表
			//	sinossProjectVariations.get(i).setImportedDate(new Date());
				sinossProjectVariations.get(i).setProjectApplication(granted.getApplication());
				instpVariation.setImportedDate(new Date());//入库时间
				instpVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				instpVariation.setApplicantSubmitStatus(3);
				instpVariation.setVariationReason(variatonReason);//变更原因
				instpVariation.setDeptInstAuditResult(2);//院系审核都通过
				instpVariation.setDeptInstAuditStatus(3);
				instpVariation.setStatus(5);	
				
				instpVariation.setPostponement(1);
				instpVariation.setOldOnceDate(granted.getPlanEndDate());
				instpVariation.setNewOnceDate(tool.getDate(smcTempOne.getAfterValue()));	
				deptAuditResultDetail.setCharAt(5, '1');
				universityAuditResultDetail.setCharAt(5, '1');
				provinceAuditResultDetail.setCharAt(5, '1');
				
				//变更项目审核信息								
				if (checkStatus == 2) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(2);
					instpVariation.setUniversityAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 3) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(1);
					instpVariation.setUniversityAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 4) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(2);
					instpVariation.setUniversityAuditStatus(3);
					instpVariation.setProvinceAuditResult(2);
					instpVariation.setProvinceAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					instpVariation.setProvinceAuditStatus(3);
				}else if (checkStatus == 5) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(2);
					instpVariation.setUniversityAuditStatus(3);
					instpVariation.setProvinceAuditResult(1);
					instpVariation.setProvinceAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					instpVariation.setProvinceAuditStatus(3);
				}else {
					//如果不是上面几个审核状态，就表示这些数据未提交审核，就不用处理审核信息，这些审核状态对应的是社科网的数据字典，和我们这边不一样
					error.add("Instp-审核状态码不存在：：" + variationId + "--" + sinossProjectVariations.get(i).getName() + "--" + checkStatus);					
				}
				//sinossProjectVariations.get(i).setIsAdded(0);
			}												
				

			//一般项目变更
			if (projectType.contains("gener")) {		
				//记录变更类型
				StringBuffer deptAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("00000000000000000000");
				String variationId = sinossProjectVariations.get(i).getId();
				
				//找出只有一条变更而且是延期的变更数据
				List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);//获取变更项目的变更内容（项目变更表 -> 变更内容表）；一条变更id可能对应多条变更内容
				List<SinossMembers> sinossGenerMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);
				SinossModifyContent smcTempOne = null;
				if(sinossModifyContents.size() != 1){
					continue;
				}
				if(sinossModifyContents.size() == 1 && sinossGenerMembers.size() == 0){
					smcTempOne = sinossModifyContents.iterator().next();
					String modifyMean = smcTempOne.getModifyFieldMean();
					if(!modifyMean.contains("延期")){
						continue;
					}
				} else {
					continue;
				}
				//判断这条延期变更数据是否已经入库
				GeneralGranted granted = generalProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(一般项目)
    			Set<GeneralVariation> generVariations = granted.getGeneralVariation();
    			boolean isImported = false;
				for(GeneralVariation gv : generVariations){
    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getPostponement() == 1){
    					isImported = true;
    					break;
    				}
    			}
				if(isImported){
					continue;
				}
				
				
				importNumber++;
				System.out.println("项目名称为：" + sinossProjectVariations.get(i).getName());
				GeneralVariation generalVariation = new GeneralVariation();
				granted.addVariation(generalVariation);//建立关联关系
			//	sinossProjectVariations.get(i).setImportedDate(new Date());
				sinossProjectVariations.get(i).setProjectGranted(granted);//将立项id填入临时表
				sinossProjectVariations.get(i).setProjectVariation(generalVariation);
				sinossProjectVariations.get(i).setProjectApplication(granted.getApplication());
//				String grantIdString = granted.getId();//得到项目的立项id
				generalVariation.setImportedDate(new Date());//入库时间
				generalVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				generalVariation.setApplicantSubmitStatus(3);
				generalVariation.setVariationReason(variatonReason);//变更原因
				generalVariation.setDeptInstAuditResult(2);//院系审核都通过
				generalVariation.setDeptInstAuditStatus(3);
				generalVariation.setStatus(5);
				
				generalVariation.setPostponement(1);
				generalVariation.setOldOnceDate(granted.getPlanEndDate());
				generalVariation.setNewOnceDate(tool.getDate(smcTempOne.getAfterValue()));	
				deptAuditResultDetail.setCharAt(5, '1');
				universityAuditResultDetail.setCharAt(5, '1');
				provinceAuditResultDetail.setCharAt(5, '1');
														
				if (checkStatus == 2) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 3) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(1);
					generalVariation.setUniversityAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 4) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditStatus(3);
					generalVariation.setProvinceAuditResult(2);
					generalVariation.setProvinceAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					generalVariation.setProvinceAuditStatus(3);
				}else if (checkStatus == 5) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditStatus(3);
					generalVariation.setProvinceAuditResult(1);
					generalVariation.setProvinceAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					generalVariation.setProvinceAuditStatus(3);
				}else {
					error.add("gener-审核状态码不存在：：" + variationId + "--" + sinossProjectVariations.get(i).getName() + "--" + checkStatus);
				}							
				//sinossProjectVariations.get(i).setIsAdded(0);					
			}				
		}
		System.out.println("共有" + sinossProjectVariations.size() + "条数据");
		System.out.println("共入库" + importNumber + "条数据");		
		System.out.println(agencyError);
	//	System.out.println(error.toString().replaceAll(",\\s+", "\n"));
		//int i = 1/0;
	}
	
	private void init() {
		Date begin = new Date();
		map = new HashMap<String, SinossProjectVariation>();
		List<SinossProjectVariation> list = dao.query("select o from SinossProjectApplication o");
		for (SinossProjectVariation o : list) {
			map.put(o.getId(), o);
		}
		
		System.out.println("initAcademic complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
		
	}
	
	/**
	* 得到指定月前的日期（格式为：2014-01）

	*/
	public String getBeforMonth(int month, Date date) {
	        Calendar c = Calendar.getInstance();//获得一个日历的实例  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
	  
	        c.setTime(date);//设置日历时间  
	        c.add(Calendar.MONTH,-month);//在日历的月份上增加6个月   
	        String strDate = sdf.format(c.getTime());//的到你想要得6个月前的日期   
	        return strDate;
	 }
	
	public Date getBeforMonthDate(int distinctMonth, Date date) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例   
        c.setTime(date);//设置日历时间  
        c.add(Calendar.MONTH,-distinctMonth);//在日历的月份上增加6个月    
        return c.getTime();
	}
	
	/*
	 * 判断changeBefore和changeCurrent相同的索引位上是否都是1
	 */
	public boolean isAddedBefore(StringBuffer changeBefore, StringBuffer changeCurrent){
		for(int i = 0; i < 20; i++){
			if(changeBefore.charAt(i) == changeCurrent.charAt(i) && changeBefore.charAt(i) == '1'){
				return true;
			}
		}
			
		return false;
	}
	
	public static void main(String[] args) {
		int ee =0;
		int rr =0;
		if(ee <5){
			System.out.println(ee);
			if(rr == 2){
				System.out.println("rr:" + rr) ;
				
			}
		}
	}

	public ProjectVariationImporterV2(){
	}
	
	public ProjectVariationImporterV2(String file) {
		new ExcelReader(file);
	}	
}
		
		
		
		
		
			
			
		
			
		
		
			
			
			
			
			
			
	
	

