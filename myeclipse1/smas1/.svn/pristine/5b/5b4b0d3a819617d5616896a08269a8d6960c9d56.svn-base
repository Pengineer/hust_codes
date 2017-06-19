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
import csdc.tool.execution.finder.InstpFinder;
import csdc.tool.reader.JxlExcelReader;

/**
 * 导入《20130218_2013年基地重大项目（学校通过233项，主管部门通过47项）_修正导入.xls》
 * @author fengcl
 *
 */
public class InstpApplication2013Importer extends Importer{

	private JxlExcelReader reader;
	
	@Autowired
	private InstpFinder instpFinder;
	
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
		reader.readSheet(2);
	}
	
	@Override
	public void work() throws Exception {
		initExpertMap();
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		
		//本年度专家库中专家申请项目的专家统计
		HashSet<String> existingExperts = new HashSet<String>(); 
		
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			//获取项目
			ProjectApplication project = instpFinder.findInstp(G, Q, year, true);
			
			project.setNumber(A);//项目编号
			project.setAuditStatus(B);
			project.setFile(C);	//TODO申请书文件名
			project.setUniversityCode(D);
			project.setUniversityName(E);
			project.setInstituteName(F);
			project.setProjectName(StringTool.replaceBlank(G));
			project.setProjectType(H);
			project.setDisciplineDirection(StringTool.toDBC(I).replace(";", "; "));	//学科分组
			project.setDiscipline(tool.transformDisc(I + " " + J + " " + K + " " + L + " " + M));//学科分组代码
			project.setApplyDate(Tools.getDate(N));
			project.setPlanEndDate(Tools.getDate(O));
			project.setFinalResultType(P);
			project.setDirector(Q.replaceAll("\\s+", "、"));
			project.setBirthday(Tools.getDate(R));
			project.setTitle(S.replaceAll("\\s+", "、"));
			project.setDirectorUniversity(T.replaceAll("\\s+", "、"));
			project.setJob(U);
			project.setMembers(V);
			project.setReason(W);	//退评原因
			
			project.setIsReviewable(1);
			project.setYear(year);
			
			
			//为申请人添加当前年度申请年份
			String[] keys = instpFinder.getKeys(project.getDirectorUniversity(), project.getDirector(), true);
			for (String key : keys) {
				Expert applicant = expertMap.get(key);
				
				if (applicant != null) {
					existingExperts.add(applicant.getName()+applicant.getId());
					//合并过往申请年份
					List<String> ay = new ArrayList<String>(new TreeSet<String>(Arrays.asList((applicant.getInstpApplyYears() + " " + year).split("\\D+"))));
					String newAy = "";
					for (String string : ay) {
						if (string.length() > 0) {
							newAy += (newAy.isEmpty() ? "" : "; ") + string;
						}
					}
					applicant.setInstpApplyYears(newAy);
				}
			}
		}
		
		System.out.println(year+"年度专家库中存在申请项目的专家数量：" + existingExperts.size());
	}
	
	public InstpApplication2013Importer() {
	}
	
	public InstpApplication2013Importer(String file) {
		reader = new JxlExcelReader(file);
	}
	
	
	/**
	 * 学校编号+姓名 到 专家实体 的映射
	 */
	private Map<String, Expert> expertMap = new HashMap<String, Expert>();
	
	/**
	 * 初始化expertMap
	 */
	private void initExpertMap() {
		List<Expert> experts = dao.query("select e from Expert e");
		for (Expert expert : experts) {
			expertMap.put(expert.getUniversityCode() + StringTool.chineseCharacterFix(expert.getName()), expert);
		}
	}
}
