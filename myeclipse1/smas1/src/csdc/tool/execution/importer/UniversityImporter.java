package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import csdc.bean.University;
import csdc.tool.PinyinCommon;

@SuppressWarnings("unchecked")
public class UniversityImporter extends Importer {
	
	private List dataList = new ArrayList();
	private String standard;


	public UniversityImporter(File file, String standard) {
		super(file);
		this.standard = standard;
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		
		for (String[] row : content) {
			University university = tools.getUnivByCode(row[0]);
			if (university == null) {
				university = new University();
			}
			university.setCode(row[0]);
			university.setName(row[1]);
			university.setFounderCode(row[9]);
			university.setType("360".equals(university.getFounderCode()) ? 1 : 2);
			university.setAbbr(PinyinCommon.getPinYinHeadChar(university.getName().replaceAll("[^\\u4e00-\\u9fa5]", "")));
			university.setStandard(standard);

			dataList.add(university);
		}
		baseService.addOrModify(dataList);
	}

}
