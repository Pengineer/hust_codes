package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.DevelopmentReport;
import csdc.bean.Nssf;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 *
 */
public class DevelopmentReportGranted2010_2013Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public DevelopmentReportGranted2010_2013Importer() {}
	
	public DevelopmentReportGranted2010_2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}

	@Override
	protected void work() throws Throwable {
//		validate();
		importData();		
	}	

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(3);
		
		while (next(excelReader)) {
			
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			
			if (A == null || A.isEmpty()) {
				break;
			}
			DevelopmentReport dr = new DevelopmentReport();
			dr.setName(B);
			dr.setApplicant(C);
			dr.setYear(Integer.valueOf(D));
			dr.setNumber(E);
			dr.setType(F);
			dr.setTopic(G);
			dr.setUniversity(H);
			Agency university = universityFinder.getUnivByName(H);
			if (university != null) {
				dr.setUniversityId(university.getId());
			}
			dr.setGuide(I);
			dr.setImportDate(new Date());
			dao.add(dr);
		}
	}
	
	private void validate() throws Exception {
		
		excelReader.readSheet(3);
		
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			Agency university = universityFinder.getUnivByName(H);
			if (university == null) {
				System.out.println("该高校不存在：" + H);
			}			
		}
		
	}
}
