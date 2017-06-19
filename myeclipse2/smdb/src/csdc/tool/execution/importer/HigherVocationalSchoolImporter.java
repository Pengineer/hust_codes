package csdc.tool.execution.importer;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.SystemOption;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《新增高职院校代码（2011年5月24日）.xls》
 * @author xuhan
 *
 */
public class HigherVocationalSchoolImporter extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private HibernateBaseDao dao;
	
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public void work() throws Exception {
		excelReader.readSheet(0);
		while (next(excelReader)) {
			Agency univ = universityFinder.getUnivByCode(F);
			if (univ != null) {
				continue;
			}
			SystemOption 省 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'GBT2260-2007' and so.name = ? ", E);
			Agency 省厅 = (Agency) dao.queryUnique("select ag from Agency ag where ag.type = 2 and ag.name not like '[%' and ag.province.id = ?", 省.getId());
			省厅.getName();

			univ = new Agency();
			univ.setType(4);
			univ.setCode(F);
			univ.setName(B);
			univ.setProvince(省);
			//这些学校都是地方高校
			univ.setSubjection(省厅);
			
			dao.add(univ);
		}
	}

	public HigherVocationalSchoolImporter() {
	}

	public HigherVocationalSchoolImporter(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	
}
