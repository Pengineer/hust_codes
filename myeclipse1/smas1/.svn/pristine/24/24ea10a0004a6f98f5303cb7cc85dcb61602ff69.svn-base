package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.GeneralFinder;
import csdc.tool.reader.JxlExcelReader;

/**
 * 导入《2013_一般项目_学校通过9753_主管部门通过33574_修正导入.xls.xls》
 * @author fengcl
 *
 */
public class GeneralApplication2013Importer extends Importer{

	private JxlExcelReader reader;
	
	@Autowired
	private GeneralFinder generalFinder;
	
	@Autowired
	private Tools tool;
	
	/**
	 * 项目年度
	 */
	private int year = 2013;
	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(1);
	}
	
	@Override
	public void work() throws Exception {
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		
		//本年度专家库中专家申请项目的专家统计
		HashSet<String> existingExperts = new HashSet<String>(); 
		
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			//获取项目
			ProjectApplication project = generalFinder.findGeneral(F, O, year, true);
			
			boolean isSpeacial = false;
			if (null != G && G.contains("专项")) {
				isSpeacial = true;
			}
			
			project.setNumber(A);//项目编号
			project.setAuditStatus(B);
			project.setFile(C);	//TODO申请书文件名
			project.setUniversityCode(D);
			project.setUniversityName(E);
			project.setProjectName(StringTool.replaceBlank(F));
			if (isSpeacial) {//是专项任务
				project.setProjectType("专项任务");	//项目类别
				project.setTopic(G.substring(0, G.lastIndexOf("专项")));//项目主题
			}else {
				project.setProjectType(G);	//项目类别
			}
			project.setDisciplineType(H);
			project.setDiscipline(tool.transformDisc(I));// 研究方向及代码
			project.setResearchType(J);
			project.setPlanEndDate(Tools.getDate(K));
			project.setApplyFee(L);
			project.setOtherFee(M);
			project.setFinalResultType(N);
			project.setDirector(StringTool.replaceBlank(O));
			project.setGender(P);
			project.setBirthday(Tools.getDate(Q));
			project.setTitle(R);
			project.setDepartment(S);
			project.setJob(T);
			project.setEducation(U);
			project.setDegree(V);
			project.setForeign(W);
			project.setAddress(X);
			project.setPostcode(Y);
			project.setPhone(Z);
			project.setMobile(AA);
			project.setEmail(AB);
			project.setIdcard(AC);
			project.setMembers(AD);
			
//			//【说明】下面三项只是在导入高校思想政治工作专项任务数据时需要，否则注释掉
//			project.setAddDate(Tools.getDate(AE));//添加日期
//			project.setApplyDate(Tools.getDate(AF));//申请日期
//			project.setSubType(AG);//项目子类
			
			//设置参评和申请年份
			project.setIsReviewable(1);
			project.setYear(year);
			
			if (!isSpeacial) {//非专项任务时，才需要更新专家的申请年份
				if (null == expertMap) {
					initExpertMap();
				}
				//为申请人添加2013申请年份
				Expert applicant = expertMap.get(D + O.replaceAll("\\s+", ""));
				
				if (applicant != null) {
					existingExperts.add(applicant.getName()+applicant.getId());
					//合并过往申请年份
					List<String> ay = new ArrayList<String>(new TreeSet<String>(Arrays.asList((applicant.getGeneralApplyYears() + " " + year).split("\\D+"))));
					String newAy = "";
					for (String string : ay) {
						if (string.length() > 0) {
							newAy += (newAy.isEmpty() ? "" : "; ") + string;
						}
					}
					applicant.setGeneralApplyYears(newAy);
				}
			}
		}
		
		System.out.println(year+"年度专家库中存在申请项目的专家数量：" + existingExperts.size());
	}
	
	public GeneralApplication2013Importer() {
	}
	
	public GeneralApplication2013Importer(String file) {
		reader = new JxlExcelReader(file);
	}
	
	
	/**
	 * 学校编号+姓名 到 专家实体 的映射
	 */
	private Map<String, Expert> expertMap;
	
	/**
	 * 初始化expertMap
	 */
	private void initExpertMap() {
		expertMap = new HashMap<String, Expert>();
		
		List<Expert> experts = dao.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + expert.getName(), expert);
		}
	}
}
