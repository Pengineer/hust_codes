package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.tool.StringTool;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.info.ImporterInfo;
import csdc.tool.reader.PoiExcelReader;

/**
 * 导入20121213_最新专家库采集汇总数据（MOEExpert2012）_修正导入.rar文件中的MOEExpert2012.xls
 * @author fengcl
 * @导入记录
 * 1、[smas数据库]
 * 	     专家总数：导入前：11542，导入后：17087；
 *    更新总条数：13099  (新增：5545；更新：7554)<br>
 * 2、[Excel数据]
 *    专家总数：13099
 */
public class Project2013ExpertImporter extends Importer{
	
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
			
			if ("广西中医学院".equals(StringTool.fix(F))) {
				F = "广西中医药大学";
			}else if("贵州民族学院".equals(StringTool.fix(F))) {
				F = "贵州民族大学";
			}else if("江西科技师范学院".equals(StringTool.fix(F))) {
				F = "江西科技师范大学";
			}else if("内蒙古财经学院".equals(StringTool.fix(F))) {
				F = "内蒙古财经大学";
			}else if("西安邮电学院".equals(StringTool.fix(F))) {
				F = "西安邮电大学";
			}else if("襄樊学院".equals(StringTool.fix(F))) {
				F = "湖北文理学院";
			}else if("孝感学院".equals(StringTool.fix(F))) {
				F = "湖北工程学院";
			}else if("重庆警官职业学院".equals(StringTool.fix(F))) {
				F = "重庆警察学院";
			}else if("内蒙古医学院".equals(StringTool.fix(F))) {
				F = "内蒙古医科大学";
			}else if("四川教育学院".equals(StringTool.fix(F))) {
				F = "成都师范学院";
			}else if("海南大学三亚学院".equals(StringTool.fix(F))) {
				F = "三亚学院";
			}else if ("山东财政学院".equals(StringTool.fix(F))) {
				F = "山东财经大学";
			}
			
			//填充匹配条件
			conditions.put(ImporterInfo.BIRTHDAY, C);
			conditions.put(ImporterInfo.IDCARD_TYPE, D);
			conditions.put(ImporterInfo.IDCARD_NUMBER, E);
			conditions.put(ImporterInfo.MOBILE_PHONE, K);
			conditions.put(ImporterInfo.EMAIL, L);
			conditions.put(ImporterInfo.HOME_PHONE, M);
			conditions.put(ImporterInfo.OFFICE_PHONE, N);
			conditions.put(ImporterInfo.OFFICE_FAX, P);
			
			Expert otherExpert = expertFinder.findExpert(univNameCodeMap.get(F), A, conditions, true);
			
			if (null == otherExpert) {
				continue;
			}
			
			otherExpert.setGender(B);
			otherExpert.setBirthday(Tools.getDate(conditions.get(ImporterInfo.BIRTHDAY)));
			otherExpert.setIdCardType(D);
			otherExpert.setIdCardNumber(conditions.get(ImporterInfo.IDCARD_NUMBER).toUpperCase());
			otherExpert.setDepartment(G);
			otherExpert.setJob(H);
			otherExpert.setSpecialityTitle(I);
			otherExpert.setPositionLevel(J);
			beanFieldUtils.setField(otherExpert, "mobilePhone", K, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(otherExpert, "email", L, BuiltinMergeStrategies.PREPEND);
			beanFieldUtils.setField(otherExpert, "homePhone", M, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(otherExpert, "officePhone", N, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			otherExpert.setOfficePostcode(O);
			beanFieldUtils.setField(otherExpert, "officeFax", P, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			otherExpert.setOfficeAddress(Q);
			beanFieldUtils.setField(otherExpert, "discipline", tool.transformDisc(R), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(otherExpert, "researchField", S, BuiltinMergeStrategies.PREPEND);
			otherExpert.setLastEducation(T);
			otherExpert.setDegree(U);
			otherExpert.setIsDoctorTutor(V);
			otherExpert.setLanguage(W);
			otherExpert.setComputerLevel(X);
			otherExpert.setEthnicLanguage(Y);
			otherExpert.setPostDoctor(Z);
			otherExpert.setPartTime(AA);
			otherExpert.setAbroad(AB);
			otherExpert.setAward(AC);
			otherExpert.setMoeProject(AD);
			otherExpert.setNationalProject(AE);
			String introduction = null;
			if (null != AF && !AF.isEmpty() && null != AG && !AG.isEmpty()) {
				introduction = AF + "\n其他说明：" + AG;
			}else if (null != AG && !AG.isEmpty()) {
				introduction = "其他说明：" + AG;
			}else {
				introduction = AF;
			}
			otherExpert.setIntroduction(introduction);
			otherExpert.setImportedDate(new Date());
			
			updateCnt++;
		}
		System.out.println("更新入库总数量：" + updateCnt);
		
		//打印输出结果
		expertFinder.printResult();
		
		//更新专家的专兼职
		expertFinder.updateType();
	}
	
	public Project2013ExpertImporter(){
	}
	
	public Project2013ExpertImporter(String file) {
		reader = new PoiExcelReader(file);
	}
}
