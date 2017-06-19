package csdc.tool.execution.importer;

import java.io.File;

import csdc.bean.Agency;
import csdc.bean.Person;

/**
 * 导入《20110504_学校联系方式.xls》
 * @author xuhan
 * 
 */
public class UniversityContactInfoImporter extends Importer {
	
	public UniversityContactInfoImporter(File file) {
		super(file);
	}
	
	
	public void work() throws Exception {
		getContentFromExcel(0);
		StringBuffer exceptionMsg = new StringBuffer();
		
		while (next()) {
			getRowData(row);
			
			if (A == null || A.isEmpty()) {
				continue;
			}

			Agency university = tools.getUnivByCode(A);
			if (university == null || !university.getName().equals(B)) {
				exceptionMsg.append("学校代码/名称错误：" + A + " - " + B);
				continue;
			}
			
			if (C != null && !C.isEmpty()) {
				Person sdirector = new Person();
				sdirector.setName(C);
				sdirector.setOfficePhone(D);
				sdirector.setMobilePhone(E);
				sdirector.setEmail(F);
				university.setSdirector(sdirector);
				saveOrUpdate(sdirector);
			}
			
			if (G != null && !G.isEmpty()) {
				Person slinkman = new Person();
				slinkman.setName(G);
				slinkman.setOfficePhone(H);
				slinkman.setMobilePhone(I);
				slinkman.setEmail(J);
				university.setSlinkman(slinkman);
				saveOrUpdate(slinkman);
			}
			
			university.setSaddress(K);
			university.setSpostcode(L);
			university.setSname(M);
			
			saveOrUpdate(university);
		}
		if (exceptionMsg.length() > 0) {
			throw new Exception(exceptionMsg.toString());
		}
		
		finish();
	}

}
