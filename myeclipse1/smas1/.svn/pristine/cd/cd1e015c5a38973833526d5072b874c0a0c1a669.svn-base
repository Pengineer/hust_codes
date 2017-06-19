package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csdc.bean.Expert;
import csdc.bean.University;

/**
 * 导入2011年申报专家库《[修正导入]20110512_项目审核数据.xls》
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class Project2011ExpertImporter extends Importer {
	
	/**
	 * 学校编号+姓名+身份证前10位 到 专家实体 的映射
	 */
	private Map<String, Expert> expertMap = new HashMap<String, Expert>();
	
	/**
	 * Excel中 学校编号+姓名+身份证前10位 集合
	 */
	private Set<String> keySet = new HashSet<String>();
	
	private List dataList = new ArrayList();
	
	


	public Project2011ExpertImporter(File file) {
		super(file);
		
		List<Expert> experts = baseService.query("select e from Expert e");
		for (Expert expert : experts) {
			String idcardNumber = expert.getIdCardNumber();
			if (idcardNumber == null) {
				idcardNumber = "";
			}
			expertMap.put(expert.getUniversityCode() + expert.getName() + idcardNumber.substring(0, Math.min(12, idcardNumber.length())), expert);
		}
		
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		StringBuffer exceptionMsg = new StringBuffer();
		StringBuffer duplicateExperts = new StringBuffer();
		
		int nowExpertNumber = 10010001;
		for (String[] row : content) {
			University university = tools.getUnivByName(row[0]);
			if (university == null) {
				exceptionMsg.append(row[0] + " 找不到\n");
				continue;
			}
			String univCode = university.getCode();
			String key = univCode + row[2] + row[8].substring(0, Math.min(12, row[8].length()));
			if (keySet.contains(key)) {
				duplicateExperts.append(university.getName() + " " + row[2] + " " + row[8] + " 多次出现\n");
			}
			keySet.add(key);
			
			Expert expert = expertMap.get(key);
			if (expert == null) {
				expert = new Expert();
			}
			String expertNumberString = row[3].replaceAll("\\D+", "");
			Integer expertNumber = expertNumberString.length() > 6 ? Integer.parseInt(expertNumberString) : expert.getNumber();
			if (expertNumber == null) {
				expertNumber = nowExpertNumber++;
			}
			String reviewYears = row[6].length() > 0 ? "2009" : "";
			if (row[7].length() > 0) {
				reviewYears += (reviewYears.length() > 0 ? "; " : "") + "2010";
			}

			expert.setUniversityCode(univCode);
			expert.setName(row[2]);
			expert.setNumber(expertNumber);
			//expert.setApplyStatus2011(row[5]);
			expert.setGeneralReviewYears(reviewYears);
			if (row[8].length() > 0) {
				expert.setIdCardNumber(row[8]);
			}
			if (row[9].length() > 0) {
				expert.setGender(row[9]);
			}
			if (row[10].length() > 0) {
				expert.setBirthday(Tools.getDate(row[10]));
			}
			if (row[11].length() > 0) {
				expert.setSpecialityTitle(row[11]);
			}
			if (row[12].length() > 0) {
				expert.setDepartment(row[12]);
			}
			if (row[13].length() > 0) {
				expert.setJob(row[13]);
			}
			if (row[14].length() > 0) {
				expert.setIsDoctorTutor(row[14]);
			}
			if (row[15].length() > 0) {
				expert.setPositionLevel(row[15]);
			}
			if (row[16].length() > 0) {
				expert.setDegree(row[16]);
			}
			if (row[17].length() > 0) {
				expert.setLanguage(row[17]);
			}
			if (row[18].length() > 0) {
				expert.setHomePhone(row[18]);
			}
			if (row[19].length() > 0) {
				expert.setMobilePhone(row[19]);
			}
			if (row[20].length() > 0) {
				expert.setOfficePhone(row[20]);
			}
			if (row[21].length() > 0) {
				expert.setEmail(row[21]);
			}
			if (row[22].length() > 0) {
				expert.setPartTime(row[22]);
			}

			expert.setDiscipline(tools.transformDisc(expert.getDiscipline() + " " + row[23] + " " + row[24] + " " + row[25] + " " + row[26] + " " + row[27] + " " + row[28] + " " + row[33]));
			
			if (row[29].length() > 0) {
				expert.setMoeProject(row[29]);
			}
			if (row[30].length() > 0) {
				expert.setNationalProject(row[30]);
			}
			if (row[31].length() > 0) {
				expert.setRating(row[31]);
			}
			if (row[32].length() > 0) {
				expert.setAward(row[32]);
			}
			if (row[33].length() > 0) {
				expert.setResearchField(row[33]);
			}
			if (row[34].length() > 0) {
				expert.setOfficeAddress(row[34]);
			}
			if (row[35].length() > 0) {
				expert.setOfficePostcode(row[35]);
			}
			if (row[36].length() > 0) {
				expert.setRemark(row[36]);
			}
			
			expert.setIsReviewer(1);
			
			dataList.add(expert);
		}
		if (exceptionMsg.length() > 0) {
			throw new RuntimeException(exceptionMsg.toString());
		}

		baseService.addOrModify(dataList);
		System.out.println(duplicateExperts);
	}

}
