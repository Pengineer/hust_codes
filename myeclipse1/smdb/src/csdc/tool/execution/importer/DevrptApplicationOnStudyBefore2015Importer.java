package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Devrpt;
import csdc.bean.Nssf;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《评价中心在研项目名单（截至2015年3月31日）_修正导入.xls》
 * @author pengliang
 *
 */
public class DevrptApplicationOnStudyBefore2015Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public DevrptApplicationOnStudyBefore2015Importer() {}
	
	public DevrptApplicationOnStudyBefore2015Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}

	@Override
	protected void work() throws Throwable {
	//	validate();
		importData();		
	}	

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(3);
		
		while (next(excelReader)) {
			
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber() + ":" + B + "——" + C);
			
			if (A == null || A.isEmpty()) {
				break;
			}
			Devrpt dr = new Devrpt();
			dr.setName(C.trim());
			dr.setApplicant(E.replaceAll("\\s+", ""));
			dr.setYear(Integer.valueOf(B));
			dr.setNumber(A.trim());
			Agency university = universityFinder.getUnivByName(D.trim());
			if (university != null) {
				dr.setUniversity(D.trim());
				dr.setUniversityId(university.getId());
			}
			dr.setImportDate(new Date());
			dao.add(dr);
		}
	}
	
	private void validate() throws Exception {
		
		excelReader.readSheet(3);
		
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			Agency university = universityFinder.getUnivByName(D.trim());
			if (university == null) {
				System.out.println("该高校不存在：" + D);
			}			
		}
		
	}
}
