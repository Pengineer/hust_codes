package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Officer;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《20111114_学校联系方式_修正导入.xls》
 * Note: 需要先执行Fix20120507
 * @author xuhan
 * 
 */
public class UniversityContactInfoImporterV2 extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	
	@Override
	public void work() throws Exception {
		validate();
		importData();
	}
	

	public void importData() throws Exception {
		reader.readSheet(0);

		//这文件表头有两行
		next(reader);
		
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			if (C.isEmpty()) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(B);

			Officer sdirector = univPersonFinder.findOfficer(C, university);
			university.setSdirector(sdirector.getPerson());
			beanFieldUtils.setField(sdirector.getPerson(), "officePhone", D, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(sdirector.getPerson(), "mobilePhone", E, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(sdirector.getPerson(), "email", F, BuiltinMergeStrategies.PREPEND);
			
			Officer slinkman = univPersonFinder.findOfficer(G, university);
			university.setSlinkman(slinkman.getPerson());
			beanFieldUtils.setField(slinkman.getPerson(), "officePhone", H, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(slinkman.getPerson(), "mobilePhone", I, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(slinkman.getPerson(), "email", J, BuiltinMergeStrategies.PREPEND);
			
			beanFieldUtils.setField(university, "saddress", K, BuiltinMergeStrategies.PREPEND);
			beanFieldUtils.setField(university, "spostcode", L, BuiltinMergeStrategies.PREPEND);
			beanFieldUtils.setField(university, "sname", M, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(university, "fcupNumber", N, BuiltinMergeStrategies.PREPEND);
			beanFieldUtils.setField(university, "fbankBranch", O, BuiltinMergeStrategies.PREPEND);
		}
	}
	
	public void validate() throws Exception {
		reader.readSheet(0);
		
		HashSet exMsg = new HashSet();

		//这文件表头有两行
		next(reader);
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(B);
			if (university == null) {
				exMsg.add("不存在的高校: " + B);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	
	
	public UniversityContactInfoImporterV2(){
	}
	
	public UniversityContactInfoImporterV2(String file) {
		reader = new ExcelReader(file);
	}


}
