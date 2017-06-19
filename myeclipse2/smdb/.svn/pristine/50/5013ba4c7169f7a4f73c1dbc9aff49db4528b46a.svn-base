package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.InstituteFunding;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2010年基地经费拨款一览表.xls》
 * 
 * @author xuhan
 *
 */
public class InstituteFunding2010Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;

	@Autowired
	private Tool tool;
	
	private final int YEAR = 2010;
	
	private Date date;
	
	
	public void work() throws Exception {
		date = tool.getDate(2010, 12, 23);
		validate();
		importData();
	}

	public void importData() throws Exception {
		
		//导入部级基地拨款
		reader.readSheet(0);
		next(reader);
		while (next(reader)) if (A.length() > 0) {
			System.out.println(reader.getCurrentRowIndex() + " / " + reader.getRowNumber());
			
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = instituteFinder.getInstitute(university, B, false);
			
			InstituteFunding funding = (InstituteFunding) dao.queryUnique("from InstituteFunding funding where funding.date >= ? and funding.date < ? and funding.institute.id = ?", tool.getDate(YEAR, 1, 1), tool.getDate(YEAR + 1, 1, 1), institute.getId());
			if (funding == null) {
				funding = new InstituteFunding();
				funding.setInstitute(institute);
				funding.setDate(date);
				funding.setAttn("刘杰");
			}
			funding.setProjectFee(tool.getFee(C));
			funding.setConferenceFee(tool.getFee(D));
			funding.setDataFee(tool.getFee(E));
			funding.setJournalFee(tool.getFee(F));
			funding.setNetFee(tool.getFee(G));
			funding.setDatabaseFee(tool.getFee(H));
			funding.setAwardFee(tool.getFee(I));
			funding.setFee(tool.getFee(J));
			
			dao.addOrModify(funding);
		}
		
		
		//导入省部共建基地拨款
		reader.readSheet(1);
		next(reader);
		while (next(reader)) if (A.length() > 0) {
			System.out.println(reader.getCurrentRowIndex() + " / " + reader.getRowNumber());
			
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = instituteFinder.getInstitute(university, B, false);
			
			InstituteFunding funding = (InstituteFunding) dao.queryUnique("from InstituteFunding funding where funding.date >= ? and funding.date < ? and funding.institute.id = ?", tool.getDate(YEAR, 1, 1), tool.getDate(YEAR + 1, 1, 1), institute.getId());
			if (funding == null) {
				funding = new InstituteFunding();
				funding.setInstitute(institute);
				funding.setDate(date);
				funding.setAttn("刘杰");
			}
			funding.setProjectFee(tool.getFee(C));
			funding.setFee(tool.getFee(C));
			
			dao.addOrModify(funding);
		}

	}
	
	public void validate() throws Exception {
	}
	
	public InstituteFunding2010Importer() {
	}

	public InstituteFunding2010Importer(String filePath) {
		reader = new ExcelReader(filePath);
	}
}
