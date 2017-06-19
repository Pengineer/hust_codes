package csdc.tool.execution.fix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

/**
 * Excel1:《国家社科基金年度项目（重点、一般、青年）在研名单（截至2015.3.31）_修正导入.xls》
 * Excel2:《国家社科基金后期资助项目在研名单（截至2015.3.25）_修正导入.xls》
 * Excel3:《国家社科基金重大项目在研名单（截至2015.3.25）_修正导入.xls》
 * @author pengliang
 * 
 * 注意：执行本代码前需要将所有国社科的重点、一般、青年、后期资助项目和重大项目的status=1的项目状态都设置成0
 * 
 * 执行步骤：先查出Excel中项目在库中不存在的项目；然后修复异常数据；最后添加新增数据
 * 
 */
public class FixNssf2015 extends Importer {

	/**
	 * 《国家社科基金年度项目（重点、一般、青年）在研名单（截至2015.3.31）_修正导入.xls》
	 */
	private ExcelReader excelReader1;
	
	/**
	 * 《国家社科基金后期资助项目在研名单（截至2015.3.25）_修正导入.xls》
	 */
	private ExcelReader excelReader2;
	
	/**
	 * 《国家社科基金重大项目在研名单（截至2015.3.25）_修正导入.xls》
	 */
	private ExcelReader excelReader3;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	/**
	 * 注意：执行本代码前需要将所有的status=1的项目状态都设置成0
	 * @throws Exception
	 */
	public void work() throws Exception {
//		nssfKeygenaralyouthData();
//		nssfPostData();
		nsffSignificantData();
	}
	
	public void nssfKeygenaralyouthData() throws Exception {
		//《国家社科基金年度项目（重点、一般、青年）在研名单（截至2015.3.31）_修正导入.xls》
		excelReader1.readSheet(0);
		Map<String, String> para = new HashMap<String, String>();
		Set<String> msg1 = new HashSet<String>();
		Set<String> msg2 = new HashSet<String>();
		int add =0;
		while (next(excelReader1)) {
			if (A == null || A.length() == 0) {
				break;
		    }
			System.out.println(excelReader1.getCurrentRowIndex() + "/" + excelReader1.getRowNumber() + ":"+ A + "——" + B);
			Nssf nssf = null;
			try {
//				para.put("name", B.trim());
				para.put("applicant", C.trim());
				para.put("number", A.trim());
				nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where trim(nssf.applicant) = :applicant and trim(nssf.number) = :number", para);
			} catch (Exception e) {
				msg2.add("重复项目:" + B);
			}
			
			if (nssf == null) {
//			    msg1.add("库中不存在项目：" + A + "——" + B);
				Nssf newNssf = new Nssf();
				newNssf.setNumber(A.trim());
				newNssf.setName(B.trim());
				newNssf.setApplicant(C.replaceAll("\\s+", ""));
				newNssf.setProvince(D.trim());
				newNssf.setUnit(E.trim());
				newNssf.setImportDate(new Date());
				newNssf.setStatus(1);
				dao.add(newNssf);
				add++;
			} else {
				nssf.setStatus(1);
				nssf.setProvince(D.trim());
				nssf.setUnit(E);
				nssf.setApplicant(C.replaceAll("\\s+", ""));
				dao.modify(nssf);
			}
		}
		
		if (msg1.size() > 0 || msg2.size() > 0) {
			System.out.println(msg1.toString().replaceAll(",\\s+", "\n"));
			System.out.println(msg2.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
		
		System.out.println("新增入库数：" + add);
		System.out.println("over");
	}
	
	public void nssfPostData() throws Exception {
		//《国家社科基金后期资助项目在研名单（截至2015.3.25）_修正导入.xls》
		excelReader2.readSheet(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Map<String, String> para = new HashMap<String, String>();
		Set<String> msg1 = new HashSet<String>();
		Set<String> msg2 = new HashSet<String>();
		int add =0;
		while (next(excelReader2)) {
			if (A == null || A.length() == 0) {
				break;
		    }
			System.out.println(excelReader2.getCurrentRowIndex() + "/" + excelReader2.getRowNumber() + ":"+ A + "——" + D);
			Nssf nssf = null;
			try {
				para.put("name", D.trim());
				para.put("number", A.trim());
				nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where trim(nssf.name) = :name and trim(nssf.number) = :number", para);
			} catch (Exception e) {
				msg2.add("重复项目:" + D);
			}
			
			if (nssf == null) {
//				msg1.add("库中不存在项目：" + A + "——" + D);
				Nssf newNssf = new Nssf();
				newNssf.setNumber(A.trim());
				newNssf.setType(B.trim());
				newNssf.setDisciplineType(C.trim());
				newNssf.setName(D.trim());
				newNssf.setStartDate(sdf.parse(E.trim()));
				newNssf.setApplicant(F.replaceAll("\\s+", ""));
				newNssf.setSpecialityTitle(G.trim());
				newNssf.setUnit(H.trim());
				newNssf.setUnitType(I.trim());
				newNssf.setProvince(J.trim());
				newNssf.setBelongSystem(K.trim());
				newNssf.setImportDate(new Date());
				newNssf.setStatus(1);
				dao.add(newNssf);
				add++;
			} else {
				nssf.setStatus(1);
				nssf.setType("后期资助项目");
				nssf.setDisciplineType(C.trim());
				nssf.setStartDate(sdf.parse(E.trim()));
				nssf.setSpecialityTitle(G.trim());
				nssf.setUnit(H.trim());
				nssf.setUnitType(I.trim());
				nssf.setProvince(J.trim());
				nssf.setBelongSystem(K.trim());
				nssf.setApplicant(F.replaceAll("\\s+", ""));
				dao.modify(nssf);
			}
		}
		
		if (msg1.size() > 0 || msg2.size() > 0) {
			System.out.println(msg1.toString().replaceAll(",\\s+", "\n"));
			System.out.println(msg2.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
		
		System.out.println("新增入库数：" + add);
		System.out.println("over");
	}

	public void nsffSignificantData() throws Exception {
		excelReader3.readSheet(0);
		Map<String, String> para = new HashMap<String, String>();
		Set<String> msg1 = new HashSet<String>();
		Set<String> msg2 = new HashSet<String>();
		int add =0;
		while (next(excelReader3)) {
			if (A == null || A.length() == 0) {
				break;
		    }
			System.out.println(excelReader3.getCurrentRowIndex() + "/" + excelReader3.getRowNumber() + ":"+ A + "——" + B);
			Nssf nssf = null;
			try {
				para.put("name", B.trim());
				para.put("number", A.trim());
				para.put("applicant", C.trim());
				nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where trim(nssf.name) = :name and trim(nssf.number) = :number and trim(nssf.applicant) = :applicant", para);
			} catch (Exception e) {
				msg2.add("重复项目:" + B);
			}
			
			if (nssf == null) {
//			    msg1.add("库中不存在项目：" + A + "——" + B);
				Nssf newNssf = new Nssf();
				newNssf.setNumber(A.trim());
				newNssf.setType("重大项目");
				newNssf.setName(B.trim());
				newNssf.setApplicant(C.replaceAll("\\s+", ""));
				newNssf.setUnit(D.trim());
				newNssf.setImportDate(new Date());
				newNssf.setStatus(1);
				dao.add(newNssf);
				add++;
			} else {
				nssf.setStatus(1);
				nssf.setUnit(D.trim());
				nssf.setApplicant(C.replaceAll("\\s+", ""));
				dao.modify(nssf);
			}
		}
		
		if (msg1.size() > 0 || msg2.size() > 0) {
			System.out.println(msg1.toString().replaceAll(",\\s+", "\n"));
			System.out.println(msg2.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
		
		System.out.println("新增入库数：" + add);
		System.out.println("over");
	}

	public FixNssf2015() {
	}

	public FixNssf2015(String filePath1, String filePath2, String filePath3) {
		excelReader1 = new ExcelReader(filePath1);
		excelReader2 = new ExcelReader(filePath2);
		excelReader3 = new ExcelReader(filePath3);
	}
}