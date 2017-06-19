package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.ProjectGranted;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《2013-2011年发展报告立项项目_修正导入.xls》
 * @author 
 * @status 
 * 
 */
public class ReportProjectGrantedImporter extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public ReportProjectGrantedImporter() {}
	
	public ReportProjectGrantedImporter(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		int i = 0;
		
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			
			ProjectGranted pg = new ProjectGranted();
			pg.setName(B);
			pg.setApplicantName(E.replaceAll("\\s+", "").replaceAll("、", "; "));
			Agency university = universityFinder.getUnivByName(D);
			if (university == null) {
				System.out.println("找不到该高校：" + D);
				i++;
			}
			pg.setImportedDate(new Date());
			pg.setProjectType("report");
			pg.setStatus(1);
			pg.setIsImported(1);
			System.out.println(i);
			dao.add(pg);
		}
	}

}

