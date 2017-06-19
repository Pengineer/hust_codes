package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.PopularBook;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

public class PopularBookApplicationOnStudy2015Importer extends Importer {
	
	ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;

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
		excelReader.readSheet(4);
		
		while (next(excelReader)) {
			
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber() + ":" + E + "——" + B);
			
			if (A == null || A.isEmpty()) {
				break;
			}
			PopularBook pb = new PopularBook();
			pb.setName(B.trim());
			pb.setApplicant(C.replaceAll("\\s+", ""));
			pb.setYear(Integer.valueOf(E));
			pb.setNumber(A.trim());
			Agency university = universityFinder.getUnivByName(D.trim());
			if (university != null) {
				pb.setUniversity(D.trim());
				pb.setUniversityId(university.getId());
			}
			pb.setImportDate(new Date());
			dao.add(pb);
		}
	}
	
	private void validate() throws Exception {
		
		excelReader.readSheet(4);
		
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			Agency university = universityFinder.getUnivByName(D.trim());
			if (university == null) {
				System.out.println("该高校不存在：" + D);
			}			
		}
		
	}

	public PopularBookApplicationOnStudy2015Importer() {
	}
	
	public PopularBookApplicationOnStudy2015Importer(String fileName) {
		excelReader = new ExcelReader(fileName);
	}

}
