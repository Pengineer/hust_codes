package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.JxlExcelReader;

public class FixFirstAuditResult extends Importer {

	private JxlExcelReader reader;
	
	private Map<String, String> projectName2AuditResult_old = new HashMap<String, String>();
	private Map<String, String> projectName2AuditResult_new = new HashMap<String, String>();
	
	@Override
	protected void work() throws Throwable {
		compareExcelData();
	}
	
	private void compareExcelData() throws Exception {
		reader.readSheet(0);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			if(null == D || D.isEmpty()){continue;}
			projectName2AuditResult_old.put(D, B);
		}
		
		reader.readSheet(2);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			if(null == D || D.isEmpty()){continue;}
			projectName2AuditResult_new.put(D, B);
		}
		
		for (Entry<String, String> entry : projectName2AuditResult_new.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if(projectName2AuditResult_old.containsKey(key)){
				String oldValue = projectName2AuditResult_old.get(key);
				if (oldValue.equals(value)) {
					projectName2AuditResult_old.remove(key);
//					projectName2AuditResult_new.remove(key);
					continue;
				}
				else {
					System.out.println("更新：" + key + "-" + oldValue + " --> " + key + "-" + value);
					projectName2AuditResult_old.remove(key);
				}
			}else {
				System.out.println("新增：" + key + "：" + value);
			}
		}
		
		for (Entry<String, String> entry : projectName2AuditResult_old.entrySet()) {
			System.out.println("删除：" + entry.getKey() + "：" + entry.getValue());
		}
	}
	
	public FixFirstAuditResult() {
	}
	
	public FixFirstAuditResult(String file) {
		reader = new JxlExcelReader(file);
	}
}
