package csdc.tool.execution.importer;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.UniversityVariation;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 《高校更名.xls》
 * @author wangyi
 * 
 */
public class UniversityRenameImporter extends Importer {
	
	private ExcelReader excelReader;
		
	@Autowired
	private UniversityFinder universityFinder;
	
	public UniversityRenameImporter() {}
	
	public UniversityRenameImporter(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);		
		
		while (next(excelReader)) {
			
			UniversityVariation universityVariation = new UniversityVariation();
			universityVariation.setNameOld(A);
			if (B.length() > 0) {
				universityVariation.setCodeOld(B);
			}
			universityVariation.setNameNew(C);
			Agency univ = universityFinder.getUnivByName(C);
			if (univ != null) {
				universityVariation.setCodeNew(univ.getCode());
			}			
			if (E.contains("更名")) {
				universityVariation.setType(1);
			} else if (E.contains("合并")) {
				universityVariation.setType(2);
			}
			if (F.length() > 0) {
				universityVariation.setDescription(F);
			}
			dao.add(universityVariation);

		}
	}

}
