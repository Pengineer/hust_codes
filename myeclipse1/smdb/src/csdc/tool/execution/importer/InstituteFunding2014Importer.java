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
 * 导入《2014年基地项目拨款明细_修正导入.xls》
 * 
 * @author pengliang
 *
 */
public class InstituteFunding2014Importer extends Importer {
	
	private ExcelReader excelReader;	
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;

	@Autowired
	private Tool tool;
	
	private final int YEAR = 2014;
	
	private Date date;
	
	
	public void work() throws Exception {
		date = tool.getDate(2014, 7, 28);
	//	validate();
		importData();
	}

	public void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) if (A.length() > 0) {
			System.out.println(A + "——" + B + ": " + excelReader.getCurrentRowIndex() + " / " + excelReader.getRowNumber());
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = instituteFinder.getInstitute(university, B, false);
			
			InstituteFunding funding = (InstituteFunding) dao.queryUnique("from InstituteFunding funding where funding.date >= ? and funding.date < ? and funding.institute.id = ?", tool.getDate(YEAR, 1, 1), tool.getDate(YEAR + 1, 1, 1), institute.getId());
			if (funding == null) {
				funding = new InstituteFunding();
				funding.setInstitute(institute);
				funding.setDate(date);
				funding.setAttn("白晓");
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
		
		

	}
	
	public void validate() throws Exception {
		
		excelReader.readSheet(0);
		int i = 0;
		int j = 0;
		
		while(next(excelReader)) if (A.length() > 0) {
			Agency university = universityFinder.getUnivByName(A);
			if (university == null) {
				System.out.println("找不到该学校：" + A);
				i++;
			}
			
			Institute institute = instituteFinder.getInstitute(university, B, false);
			if (institute == null) {
				System.out.println("找不到该基地：" + A + "——" + B);
				j++;
			}
			
		}
		
		System.out.println(i);
		System.out.println(j);
		
		if(i !=0 && j !=0){
			throw new RuntimeException();
		}
	}
	
	public InstituteFunding2014Importer() {
	}

	public InstituteFunding2014Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
}

