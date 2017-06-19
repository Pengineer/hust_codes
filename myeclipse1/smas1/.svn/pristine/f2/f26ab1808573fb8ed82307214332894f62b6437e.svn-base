package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UniversityNameChangesImporter extends Importer {
	
	/**
	 * 改前学校名 -> 改后学校名
	 */
	public Map<String, String> universityNameChangesMap = new HashMap<String, String>();

	public UniversityNameChangesImporter(File file) {
		super(file);
	}
	
	public void work() throws Exception {
		getContentFromExcel(0);
		for (String[] row : content) {
			if (!row[0].isEmpty() && !row[1].isEmpty()) {
				universityNameChangesMap.put(row[0], row[1]);
			}
		}
	}

}
