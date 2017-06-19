package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 导入基地更名情况<br><br>
 * [格式要求]:<br>
 * 第一列:学校名称<br>
 * 第二列:原基地名称<br>
 * 第三列:新基地名称<br>
 * @author xuhan
 *
 */
public class InstituteNameChangesImporter extends Importer {
	
	private ExcelReader excelReader;

	/**
	 * [学校code+改前基地名] -> [改后基地名]
	 */
	private Map<String, String> instituteNameChangesMap = new HashMap<String, String>();
	
	public InstituteNameChangesImporter() {
	}

	@Autowired
	private UniversityFinder universityFinder;

	
	public void work() throws Exception {
		if (excelReader == null) {
//			String realPath = ServletActionContext.getServletContext().getRealPath("file");
			String realPath = ApplicationContainer.sc.getRealPath("file");
			realPath = realPath.replace('\\', '/');
			String destFolder = "/template/import/基地更名.xls";
			String resultPath = realPath + destFolder;
			excelReader = new ExcelReader(resultPath);
		}
		excelReader.readSheet(0);
		while (next(excelReader)) {
			if (!A.isEmpty() && !B.isEmpty() && !C.isEmpty()) {
				Agency university = universityFinder.getUnivByName(A);
				instituteNameChangesMap.put(university.getCode() + StringTool.fix(B), C);
			}
		}
		excelReader = null;
	}

	
	public Map<String, String> getInstituteNameChangesMap() {
		return instituteNameChangesMap;
	}
}
