package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Nssf;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《全国教育科学“十二五”规划2014年度课题立项名单.xls》
 * @author pengliang
 */
public class NssfEducationSingle2014Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public NssfEducationSingle2014Importer() {}
	
	public NssfEducationSingle2014Importer(String filePath) {
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
		
		while (next(excelReader)) {
			Nssf nssf = new Nssf();
			nssf.setNumber(A);
			nssf.setName(C);
			nssf.setType(B);
			nssf.setApplicant(D.replaceAll("\\s+", ""));
			nssf.setUnit(E);
			Agency university = universityFinder.getUniversityWithLongestName(E);
			if (university != null) {
				nssf.setUniversity(university.getName());
				nssf.setUniversityId(university.getId());
			}
			nssf.setSubject("年度课题");
			nssf.setImportDate(new Date());
			nssf.setSingleSubject("教育学");
			nssf.setYear("2014");
			nssf.setStatus(1);   //2014年的立项录完后，在录结项数据（14年撤项的项目暂时无法考虑），显然置0也不合理。
			dao.add(nssf);
		}
	}
}
