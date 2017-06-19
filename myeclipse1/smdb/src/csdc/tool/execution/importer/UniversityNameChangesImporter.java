package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入高校更名情况
 * @author xuhan
 *
 */
public class UniversityNameChangesImporter extends Importer {
	
	private ExcelReader excelReader;
	
	/**
	 * 改前学校名 -> 改后学校名
	 */
	private Map<String, String> universityNameChangesMap = new HashMap<String, String>();

	public UniversityNameChangesImporter() {
	}

	public void work() throws Exception {
		if (excelReader == null) {
//			String realPath = ServletActionContext.getServletContext().getRealPath("file");
			String realPath = ApplicationContainer.sc.getRealPath("file");
			realPath = realPath.replace('\\', '/');
			String destFolder = "/template/import/高校更名.xls";
			String resultPath = realPath + destFolder;
			excelReader = new ExcelReader(resultPath);
		}
		excelReader.readSheet(0);
		while (next(excelReader)) {
			if (!A.isEmpty() && !C.isEmpty()) {
				universityNameChangesMap.put(StringTool.fix(A), C);
			}
		}
		excelReader = null;
	}

	
	public Map<String, String> getUniversityNameChangesMap() {
		return universityNameChangesMap;
	}

}
