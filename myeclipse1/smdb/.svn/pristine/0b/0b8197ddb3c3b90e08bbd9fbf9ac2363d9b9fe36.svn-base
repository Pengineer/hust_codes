package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralSpecial;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 《教育部人文社会科学专项任务项目在研数据（截至2015年3月31日）_修正导入.xls》
 * @author pengliang
 *	备注：
 *	2014以及之前录的数据是有问题的，type字段和number字段数据混乱。
 *	因此本次所有的在研专项都新建。
 */

public class SpecialProjectOnStudy20150415Importer extends Importer {

	ExcelReader excelReader;
	
	@Autowired
	UniversityFinder universityFinder;
	
	@Override
	protected void work() throws Throwable {
		marxImport();//马三化、中特理论
		IdeoPoliticWorkImport();//思政工作
		ResearchIntegrityImport();//科研诚信
		EduIncorruptImport();//教育廉政
		TechnologyImport();//工程科技
	}
	
	public void marxImport() throws Exception {
		validate(0);
		System.out.println("validate over!");
		excelReader.readSheet(0);
		excelReader.setCurrentRowIndex(0);
		while(next(excelReader)) {
			if (A ==null || A.isEmpty()) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			GeneralSpecial special = new GeneralSpecial();
			special.setNumber(B);
			special.setTopic(C); //topic表示专项的子类
			special.setType(D); //type表示专项的子类的子类
			special.setName(E);
			special.setUnit(F);
			special.setUniversity(F);
			special.setUniversityId(universityFinder.getUnivByName(F.trim()).getId());
			special.setApplicant(G.replaceAll("\\s+", ""));
			if (B.startsWith("09")) {
				special.setYear("2009");
			} else if (B.startsWith("10")){
				special.setYear("2010");
			} else if (B.startsWith("11")) {
				special.setYear("2011");
			} else if (B.startsWith("12")) {
				special.setYear("2012");
			} else if (B.startsWith("13")) {
				special.setYear("2013");
			} else if (B.startsWith("14")) {
				special.setYear("2014");
			}
			special.setImportDate(new Date());
			special.setIsDupCheckGeneral(1);
			dao.add(special);
		}
	}
	
	public void IdeoPoliticWorkImport() throws Exception {
		validate(1);
		System.out.println("validate over!");
		excelReader.readSheet(1);
		excelReader.setCurrentRowIndex(0);
		while(next(excelReader)) {
			if (A ==null || A.isEmpty()) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			GeneralSpecial special = new GeneralSpecial();
			special.setName(B);
			special.setUnit(F);
			special.setUniversity(F);
			special.setUniversityId(universityFinder.getUnivByName(F.trim()).getId());
			special.setApplicant(D.replaceAll("\\s+", ""));
			special.setYear(E);
			special.setTopic("高校思想政治工作"); //topic表示专项的子类
			special.setType(G); //type表示专项的子类的子类
			special.setImportDate(new Date());
			special.setIsDupCheckGeneral(1);
			dao.add(special);
		}
	}
	
	public void ResearchIntegrityImport() throws Exception {
		validate(2);
		System.out.println("validate over!");
		excelReader.readSheet(2);
		excelReader.setCurrentRowIndex(0);
		while(next(excelReader)) {
			if (A ==null || A.isEmpty()) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			GeneralSpecial special = new GeneralSpecial();
			special.setName(D);
			special.setTopic("科研诚信与学风建设"); //topic表示专项的子类
			special.setType(B); //type表示专项的子类的子类
			special.setUnit(F);
			special.setUniversity(F);
			special.setUniversityId(universityFinder.getUnivByName(F.trim()).getId());
			special.setApplicant(E.replaceAll("\\s+", ""));
			special.setImportDate(new Date());
			special.setIsDupCheckGeneral(1);
			dao.add(special);
		}
	}
	
	public void EduIncorruptImport() throws Exception {
		validate(3);
		System.out.println("validate over!");
		excelReader.readSheet(3);
		excelReader.setCurrentRowIndex(0);
		while(next(excelReader)) {
			if (A ==null || A.isEmpty()) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			GeneralSpecial special = new GeneralSpecial();
			special.setName(C);
			special.setNumber(D.trim());
			special.setTopic("教育廉政理论研究"); //topic表示专项的子类
			special.setUnit(F);
			special.setUniversity(F);
			special.setUniversityId(universityFinder.getUnivByName(F.trim()).getId());
			special.setApplicant(E.replaceAll("\\s+", ""));
			special.setImportDate(new Date());
			special.setIsDupCheckGeneral(1);
			dao.add(special);
		}
	}
	
	public void TechnologyImport() throws Exception {
		validate(4);
		System.out.println("validate over!");
		excelReader.readSheet(4);
		excelReader.setCurrentRowIndex(0);
		while(next(excelReader)) {
			if (A ==null || A.isEmpty()) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			GeneralSpecial special = new GeneralSpecial();
			special.setName(B);
			special.setNumber(C.trim());
			special.setTopic("工程科技人才培养研究"); //topic表示专项的子类
			special.setUnit(F);
			special.setUniversity(F);
			special.setUniversityId(universityFinder.getUnivByName(F.trim()).getId());
			special.setApplicant(D.replaceAll("\\s+", ""));
			special.setImportDate(new Date());
			special.setIsDupCheckGeneral(1);
			dao.add(special);
		}
	}
	
	/**
	 * 校验学校是否存在
	 */
	private void validate(int sheet) throws Exception{
		excelReader.readSheet(sheet);
		excelReader.setCurrentRowIndex(0);
		HashSet<String> exMsg = new HashSet<String>();
		while (next(excelReader)) {
			if (A ==null || A.isEmpty()) {
				break;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			Agency universityFindByName = universityFinder.getUnivByName(F.trim());
			if (universityFindByName == null) { 
				exMsg.add("不存在的高校名称: " + F.trim());
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public SpecialProjectOnStudy20150415Importer() {}
	
	public SpecialProjectOnStudy20150415Importer(String fileName) {
		excelReader = new ExcelReader(fileName);
	}

}
