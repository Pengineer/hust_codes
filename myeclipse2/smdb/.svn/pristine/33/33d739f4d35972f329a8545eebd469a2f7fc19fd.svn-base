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
 * 导入《附件1：2012年度教育部人文社会科学重点研究基地预算拨款一览表.xls》、《附件2：2012年度部省共建人文社会科学重点研究基地预算拨款一览表.xls》、《附件3：2012年人权基地预算拨款一览表（不公布）.xls》
 * 
 * @author xuhan
 *
 */
public class InstituteFunding2012Importer extends Importer {
	
	/**
	 * 《附件1：2012年度教育部人文社会科学重点研究基地预算拨款一览表.xls》
	 */
	private ExcelReader reader1;
	
	/**
	 * 《附件2：2012年度部省共建人文社会科学重点研究基地预算拨款一览表.xls》
	 */
	private ExcelReader reader2;
	
	/**
	 * 《附件3：2012年人权基地预算拨款一览表（不公布）.xls》
	 */
	private ExcelReader reader3;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;

	@Autowired
	private Tool tool;
	
	private final int YEAR = 2012;
	
	private Date date;
	
	
	public void work() throws Exception {
		date = tool.getDate(2012, 6, 1);
		validate();
		importData();
	}

	public void importData() throws Exception {
		
		//导入部级基地拨款
		reader1.readSheet(0);
		next(reader1);
		next(reader1);
		next(reader1);
		while (next(reader1)) if (A.length() > 0) {
			System.out.println(reader1.getCurrentRowIndex() + " / " + reader1.getRowNumber());
			
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
			funding.setFee(tool.getFee(I));
			
			dao.addOrModify(funding);
		}
		
		
		//导入省部共建基地拨款
		reader2.readSheet(0);
		next(reader2);
		next(reader2);
		next(reader2);
		while (next(reader2)) if (A.length() > 0) {
			System.out.println(reader2.getCurrentRowIndex() + " / " + reader2.getRowNumber());
			
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

		//导入人权基地拨款
		reader3.readSheet(0);
		next(reader3);
		next(reader3);
		next(reader3);
		while (next(reader3)) if (A.length() > 0) {
			System.out.println(reader3.getCurrentRowIndex() + " / " + reader3.getRowNumber());
			
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
	
	public InstituteFunding2012Importer() {
	}

	public InstituteFunding2012Importer(String filePath1, String filePath2, String filePath3) {
		reader1 = new ExcelReader(filePath1);
		reader2 = new ExcelReader(filePath2);
		reader3 = new ExcelReader(filePath3);
	}
}
