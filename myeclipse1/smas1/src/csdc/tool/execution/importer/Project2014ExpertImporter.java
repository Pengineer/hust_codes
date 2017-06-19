package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.info.ImporterInfo;
import csdc.tool.reader.PoiExcelReader;

/**
 * 导入20140220_最新专家库采集汇总数据（MOEExpert2013）.rar文件中的MOEExpert2013.xls
 * @author fengcl
 * @导入记录
 * 1、[smas数据库]
 * 	     专家总数：导入前：17089，导入后：22074；
 *    更新总条数：18742  (新增：4985；更新：13757)<br>
 * 2、[Excel数据]
 *    专家总数：18742
 */
public class Project2014ExpertImporter extends Importer{
	
	private PoiExcelReader reader;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;
	
	@Autowired
	private Tools tool;
	
	@Autowired
	private ExpertFinder expertFinder;

	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		
		expertFinder.initData();
		int updateCnt = 0;
		
		//高校名称 -> 高校代码
		Map<String, String> univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		Map<ImporterInfo, String> conditions = new HashMap<ImporterInfo, String>();
		next(reader);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());	
			
			if (A.equals("许为勤")) {
				System.out.println("ok");
			}
			
			//填充匹配条件
			conditions.put(ImporterInfo.BIRTHDAY, C);
			conditions.put(ImporterInfo.IDCARD_TYPE, D);
			conditions.put(ImporterInfo.IDCARD_NUMBER, E);
			conditions.put(ImporterInfo.MOBILE_PHONE, L);
			conditions.put(ImporterInfo.EMAIL, M);
			conditions.put(ImporterInfo.HOME_PHONE, N);
			conditions.put(ImporterInfo.OFFICE_PHONE, O);
			conditions.put(ImporterInfo.OFFICE_FAX, Q);
			conditions.put(ImporterInfo.EXPERT_ID, AH);
			Expert otherExpert = expertFinder.findExpert(univNameCodeMap.get(F), A, conditions, true);
			
			if (null == otherExpert) {
				continue;
			}
			
			otherExpert.setGender(B);
			otherExpert.setBirthday(Tools.getDate(conditions.get(ImporterInfo.BIRTHDAY)));
			otherExpert.setIdCardType(D);
			otherExpert.setIdCardNumber(conditions.get(ImporterInfo.IDCARD_NUMBER).toUpperCase());
			otherExpert.setUsedName(G);
			otherExpert.setDepartment(H);
			otherExpert.setJob(I);
			otherExpert.setSpecialityTitle(J);
			otherExpert.setPositionLevel(K);
			beanFieldUtils.setField(otherExpert, "mobilePhone", L, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(otherExpert, "email", M, BuiltinMergeStrategies.PREPEND);
			beanFieldUtils.setField(otherExpert, "homePhone", N, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(otherExpert, "officePhone", O, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			otherExpert.setOfficePostcode(P);
			beanFieldUtils.setField(otherExpert, "officeFax", Q, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			otherExpert.setOfficeAddress(R);
			beanFieldUtils.setField(otherExpert, "discipline", tool.transformDisc(S), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(otherExpert, "researchField", T, BuiltinMergeStrategies.PREPEND);
			otherExpert.setLastEducation(U);
			otherExpert.setDegree(V);
			otherExpert.setIsDoctorTutor(W);
			otherExpert.setLanguage(X);
			otherExpert.setComputerLevel(Y);
			otherExpert.setEthnicLanguage(Z);
			otherExpert.setPostDoctor(AA);
			otherExpert.setPartTime(AB);
			otherExpert.setAbroad(AC);
			otherExpert.setAward(AD);
			otherExpert.setIntroduction(AG);
			otherExpert.setImportedDate(new Date());
			String tem;
			if((tem = expertFinder.fixMoeProjectInfo(AE,otherExpert)) != null){
				otherExpert.setMoeProject(tem);
			}
			if((tem = expertFinder.fixNationalProjectInfo(AF,otherExpert)) != null){
				otherExpert.setNationalProject(tem);
			}	
			updateCnt++;
		}
		System.out.println("更新入库总数量：" + updateCnt);
		
		//打印输出结果
		expertFinder.printResult();
		
		//更新专家的专兼职
		expertFinder.updateType();
	}
	
	public Project2014ExpertImporter(){
	}
	
	public Project2014ExpertImporter(String file) {
		reader = new PoiExcelReader(file);
	}
}
