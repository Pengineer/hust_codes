package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

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

	public UniversityNameChangesImporter(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	
	public void work() throws Exception {
		excelReader.readSheet(0);
		while (next(excelReader)) {
			if (!A.isEmpty() && !C.isEmpty()) {
				universityNameChangesMap.put(StringTool.fix(A), C);
			}
		}
	}

	
	public Map<String, String> getUniversityNameChangesMap() {
		return universityNameChangesMap;
	}

}
