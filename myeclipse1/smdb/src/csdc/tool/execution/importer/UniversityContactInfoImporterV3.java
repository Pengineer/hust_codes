package csdc.tool.execution.importer;

import java.sql.Date;
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
 * 导入《20141125_学校联系方式_修正导入.xls》
 * Note: 需要先执行Fix20120507
 * @author pengliang
 */
public class UniversityContactInfoImporterV3 extends Importer {
	
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
		try{
			importData();
		}catch(Exception e){
			univPersonFinder.reset();
		}		
	}
	

	public void importData() throws Exception {
		reader.readSheet(0);	
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber() + ":" + B);
			
			if (B.isEmpty()) {
				continue;
			}
			
			Agency university = null;
			if(B.contains("教育厅")){
				university = universityFinder.getProByName(B);
			} else {
			    university = universityFinder.getUnivByName(B);
			}
			
			university.setImportedDate(new java.util.Date());

			if (!C.isEmpty()) {
				Officer sdirector = univPersonFinder.findOfficer(C, university);
				university.setSdirector(sdirector.getPerson());
				if(!D.isEmpty()){
					beanFieldUtils.setField(sdirector.getPerson(), "officePhone", D, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				}
				if(!E.isEmpty()){
					beanFieldUtils.setField(sdirector.getPerson(), "mobilePhone", E, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				}
				if(!F.isEmpty()){
					F = F.replaceAll("[、/,，]", ";");
					beanFieldUtils.setField(sdirector.getPerson(), "email", F, BuiltinMergeStrategies.PREPEND);
				}
			}
			
			if (!G.isEmpty()) {
				Officer slinkman = univPersonFinder.findOfficer(G, university);
				university.setSlinkman(slinkman.getPerson());
				if(!H.isEmpty()){
					beanFieldUtils.setField(slinkman.getPerson(), "officePhone", H, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				}
				if(!I.isEmpty()){
					beanFieldUtils.setField(slinkman.getPerson(), "mobilePhone", I, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				}
				if(!J.isEmpty()){
					J = J.replaceAll("[、/,，]", ";");
					beanFieldUtils.setField(slinkman.getPerson(), "email", J, BuiltinMergeStrategies.PREPEND);
				}
			}
			
			if(!K.isEmpty()){
				beanFieldUtils.setField(university, "saddress", K, BuiltinMergeStrategies.PREPEND);
			}
			if(!L.isEmpty()){
				beanFieldUtils.setField(university, "spostcode", L, BuiltinMergeStrategies.PREPEND);
			}
			if(!M.isEmpty()){
				beanFieldUtils.setField(university, "sname", M, BuiltinMergeStrategies.REPLACE);
			}
			if(!N.isEmpty()){
				beanFieldUtils.setField(university, "fcupNumber", N, BuiltinMergeStrategies.PREPEND);
			}
			if(!O.isEmpty()){
				beanFieldUtils.setField(university, "fbankBranch", O, BuiltinMergeStrategies.PREPEND);
			}
			if(!P.isEmpty()){
				beanFieldUtils.setField(university, "fbankAccountName", P, BuiltinMergeStrategies.PREPEND);
			}
			
			dao.addOrModify(university);
		}
	}
	
	public void validate() throws Exception {
		reader.readSheet(0);		
		HashSet exMsg = new HashSet();
		HashSet exMsg1 = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(B);
			if (university == null) {
				Agency 教育厅 = (Agency) dao.queryUnique("from Agency where c_name=?",B);
				if(教育厅 == null){
					if(!(B.contains("大学") || B.contains("学院") || B.contains("学校"))){
						exMsg1.add(reader.getCurrentRowIndex() + "——" + "Agency表中不存在的记录: " + B  );
						continue;
					}
					exMsg.add(reader.getCurrentRowIndex() + "——" + "Agency表中不存在的记录: " + B  );
				}				
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg1.toString().replaceAll(",\\s+", "\n"));
			System.out.println("************************************");
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

	public UniversityContactInfoImporterV3(){
	}
	
	public UniversityContactInfoImporterV3(String file) {
		reader = new ExcelReader(file);
	}
}

