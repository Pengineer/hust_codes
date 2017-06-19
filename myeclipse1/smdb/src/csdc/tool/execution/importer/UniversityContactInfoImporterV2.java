package csdc.tool.execution.importer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.inject.New;

import org.springframework.aop.ThrowsAdvice;
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
	
	private ExcelReader reader1;
	
	private ExcelReader reader2;
	
	private List<String> univNameList = new ArrayList<String>();
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	
	@Override
	public void work() throws Exception {
//		validate();
		importData();
	}
	

	public void importData() throws Exception {
		reader1.readSheet(0);
		reader2.readSheet(0);

		//这文件表头有两行
//		next(reader2);
		
		while (next(reader1)) {
			System.out.println(reader1.getCurrentRowIndex() + "/" + reader1.getRowNumber());
			
			if (B.isEmpty()) {
				continue;
			}
			
			univNameList.add(B); 
			
		}
		
		int flag = 0;
		while (next(reader2)) {
			System.out.println(reader2.getCurrentRowIndex() + "/" + reader2.getRowNumber());
			
			if (B.isEmpty()) {
				break;
			}
			
			for (int i = 0; i < univNameList.size(); i++) {
				if(B.equals(univNameList.get(i))) {
					flag = 1;
					continue;
				}
			}
			
			if(flag == 1) {
				Agency university = universityFinder.getUnivByName(B);
				university.setImportedDate(new java.util.Date());
				if (!C.isEmpty()) {
					Officer sdirector = univPersonFinder.findOfficer(C, university);
					university.setSdirector(sdirector.getPerson());
					beanFieldUtils.setField(sdirector.getPerson(), "officePhone", D, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
					beanFieldUtils.setField(sdirector.getPerson(), "mobilePhone", E, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
					beanFieldUtils.setField(sdirector.getPerson(), "email", F, BuiltinMergeStrategies.PREPEND);
				}
				
				if (!G.isEmpty()) {
					Officer slinkman = univPersonFinder.findOfficer(G, university);
					university.setSlinkman(slinkman.getPerson());
					beanFieldUtils.setField(slinkman.getPerson(), "officePhone", H, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
					beanFieldUtils.setField(slinkman.getPerson(), "mobilePhone", I, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
					beanFieldUtils.setField(slinkman.getPerson(), "email", J, BuiltinMergeStrategies.PREPEND);
				} else {
					System.out.println(B);
					throw new RuntimeException(B);
				}
				
				beanFieldUtils.setField(university, "saddress", K, BuiltinMergeStrategies.PREPEND);
				beanFieldUtils.setField(university, "spostcode", L, BuiltinMergeStrategies.PREPEND);
				beanFieldUtils.setField(university, "sname", M, BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(university, "fcupNumber", N, BuiltinMergeStrategies.PREPEND);
				beanFieldUtils.setField(university, "fbankBranch", O, BuiltinMergeStrategies.PREPEND);
				beanFieldUtils.setField(university, "fbankAccountName", P, BuiltinMergeStrategies.PREPEND);
				flag = 0;
				
				dao.addOrModify(university);
			}						
		}
	}
	
	public void validate() throws Exception {
		reader1.readSheet(0);
		
		HashSet exMsg = new HashSet();

		//这文件表头有两行
//		next(reader2);
		while (next(reader1)) {
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
	
	public UniversityContactInfoImporterV2(String file1, String file2) {
		reader1 = new ExcelReader(file1);
		reader2 = new ExcelReader(file2);
	}


}
