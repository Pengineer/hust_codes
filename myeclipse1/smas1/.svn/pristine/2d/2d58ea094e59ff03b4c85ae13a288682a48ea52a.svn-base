package csdc.tool.execution.importer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectApplication;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.GeneralFinder;
import csdc.tool.reader.JxlExcelReader;

public class TestImporter extends Importer{

	private JxlExcelReader reader;
	
	@Autowired
	private GeneralFinder generalFinder;
	
	private Map<String, String> univNameCodeMap;
	
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
		
		univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			//获取项目
			ProjectApplication project = generalFinder.findGeneral(D, B, year, true);
			project.setDirector(StringTool.replaceBlank(B));
			
			String unitName = StringTool.replaceBlank(C);
			String univCode = null;
			String univName = null;
			String department = null;
			if (univNameCodeMap.containsKey(unitName)) {
				univName = StringTool.replaceBlank(C);
				univCode = univNameCodeMap.get(univName);
			}else {
				univName = getUniversityWithLongestName(unitName);
				if (null == univName) {
					System.out.println(A + "：" + B + "/" + C);
					continue;
				}
				univCode = univNameCodeMap.get(univName);
				department = unitName.substring(univName.length());
			}
			project.setUniversityCode(univCode);
			project.setUniversityName(univName);
			project.setDepartment(department);
			project.setProjectName(StringTool.replaceBlank(D));
			project.setProjectType("专项任务");	//项目类别
			project.setTopic("高校思想政治理论课");//项目主题
			
			//设置参评和申请年份
			project.setIsReviewable(1);
			project.setYear(year);
			
		}
		
	}
	
	public TestImporter() {
	}
	
	public TestImporter(String file) {
		reader = new JxlExcelReader(file);
	}
	
	/**
	 * 寻找最长子串，使得是某个学校的名字。返回该学校。
	 * @param agencyName
	 * @return
	 * @throws Exception
	 */
	public String getUniversityWithLongestName(String agencyName) throws Exception {
		if (agencyName == null) {
			return null;
		}
		for (int len = agencyName.length(); len >= 1; len--) {
			for (int i = 0; i + len <= agencyName.length(); i++) {
				String subName = agencyName.substring(i, i + len);
				if (univNameCodeMap.containsKey(subName)) {
					return subName;
				}
			}
		}
		return null;
	}
}
