package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.NssfProjectApplication;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * Excel:《2015年国家社科基金后期资助项目申请数据_修正导入.xls》入库
 * @author pengliang
 */
public class NssfPostApplication2015Importer extends Importer {
	/**
	 * 《2015年国家社科基金后期资助项目申请数据_修正导入.xls》
	 */
	private ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public NssfPostApplication2015Importer() {}
	
	public NssfPostApplication2015Importer(String filePath) {
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
			if (A.length() == 0) {
				break;
		    }
			System.out.println(excelReader.getCurrentRowIndex() + " / " + excelReader.getRowNumber());
			NssfProjectApplication nApplication = new NssfProjectApplication();
			nApplication.setSubject1(D);
			nApplication.setSubjectCode(B);
			nApplication.setName(C + "(据成果名称推测)");
			nApplication.setApplicant(E.replaceAll("\\s+", ""));
			nApplication.setUnit(F);
			nApplication.setProductName(C);
			Agency univ = universityFinder.getUniversityWithLongestName(F);
			if (univ != null) {
				nApplication.setUniversity(univ.getName());
				nApplication.setUniversityId(univ.getId());
			}
			nApplication.setYear(2015);
			nApplication.setType("后期资助项目");
			nApplication.setImportDate(new Date());
			
			dao.add(nApplication);

		}
	}
}
