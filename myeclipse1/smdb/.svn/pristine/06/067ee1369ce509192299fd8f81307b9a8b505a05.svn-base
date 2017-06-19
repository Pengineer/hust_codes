package csdc.tool.execution.fix;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

/**
 * Excel1:《国家社科基金中华学术外译项目在研名单（截至2014.3.17）_修正导入.xls》
 * Excel2:《国家社科基金后期资助项目在研名单（截至2014.3.12）_修正导入.xls》
 * @author wangyi
 * 
 */
public class FixNssf2014 extends Importer {

	/**
	 * 《国家社科基金中华学术外译项目在研名单（截至2014.3.17）_修正导入.xls》
	 */
	private ExcelReader excelReader1;
	
	/**
	 * 《国家社科基金后期资助项目在研名单（截至2014.3.12）_修正导入.xls》
	 */
	private ExcelReader excelReader2;

	public void work() throws Exception {
		//《国家社科基金中华学术外译项目在研名单（截至2014.3.17）_修正导入.xls》
		excelReader1.readSheet(0);

		while (next(excelReader1)) {
			if (A.length() == 0) {
				break;
		    }
			
			Nssf nssf = null;
			try {
				nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.name = ? and nssf.type = '中华学术外译项目'", B);
			} catch (Exception e) {
				System.out.println("E1项目名重复:" + B);
			}
			if (nssf == null) {
			    System.out.println("E1找不到的项目:" + B);
			} else {
				if (C.equals(nssf.getApplicant()) && D.equals(nssf.getUnit())) {
					nssf.setStatus(1);
					saveOrUpdate(nssf);
				} else {
					System.out.println("E1项目负责人错误或者单位错误:" + B);
				}
				
			}

		}
		
		//《国家社科基金后期资助项目在研名单（截至2014.3.12）_修正导入》
		excelReader2.readSheet(0);

		int j = 0;
		int k = 0;
		while (next(excelReader2)) {
			if (A.length() == 0) {
				break;
		    }
			
			Nssf nssf = null;
			try {
				nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ? and nssf.type = '后期资助项目' and nssf.name = ?", B, C);
			} catch (Exception e) {
				System.out.println("E2批准号和名称重复:" + C);
			}
			if (nssf == null) {
			    System.out.println("E2找不到的项目:" + C);
			    nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ? and nssf.type = '后期资助项目' and nssf.name = '民主迷局与发展悖论——冷战后国际“民主化”的经验与教训'", B);
			    if (nssf != null) {
					nssf.setStatus(1);
					saveOrUpdate(nssf);
			    	j++;
			    }
			} else {
				if (!D.equals(nssf.getApplicant())) {
					nssf.setApplicantNew(D);
				}
				nssf.setStatus(1);
				saveOrUpdate(nssf);
				k++;
			}

		}
		System.out.println("E2负责人合计:" + j);
		System.out.println("E2项目名合计:" + k);
	}

	public FixNssf2014() {
	}

	public FixNssf2014(String filePath1, String filePath2) {
		excelReader1 = new ExcelReader(filePath1);
		excelReader2 = new ExcelReader(filePath2);
	}
}
