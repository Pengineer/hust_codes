package csdc.tool.execution.importer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.NssfProjectApplication;
import csdc.bean.SinossProjectApplication;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 1、Excel:《2015年国家社科基金申请数据（全部）.xls》入库
 * 2、2014年国社科申请数据查重：
 *  初审规则：申请国家社科基金的负责人不能同时申请同年度的教育部一般项目和基地项目。
 * @author pengliang
 * @status 
 */
public class NssfApplication2015Importer extends Importer {
	/**
	 * 《2015年国家社科基金申请数据（全部）.xls》
	 */
	private ExcelReader excelReader;
	
	public Map<String, List<SinossProjectApplication>> applicantUnivMap;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public NssfApplication2015Importer() {}
	
	public NssfApplication2015Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 导入数据并进行查重
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + " / " + excelReader.getRowNumber());
			NssfProjectApplication nApplication = new NssfProjectApplication();
			nApplication.setName(D.trim());
			nApplication.setApplicant(A.replaceAll("\\s+", ""));
			nApplication.setGender(B);
			nApplication.setBirthday(tool.getDate(C));
			nApplication.setProvince(E);
			nApplication.setUnit(F);
			nApplication.setYear(2015);
			Agency univ = null;
			univ = universityFinder.getUniversityWithLongestName(F);
			if (univ != null) {
				nApplication.setUniversity(univ.getName());
				nApplication.setUniversityId(univ.getId());
			}
			nApplication.setImportDate(new Date());
			nApplication.setIsDupCheckGeneral(1);
			dao.add(nApplication);

		}
	}
}
