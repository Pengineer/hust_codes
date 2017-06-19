package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import csdc.bean.Expert;
import csdc.tool.reader.PoiExcelReader;

/**
 * 标记重大项目负责人
 * 附件：《重大项目名单(原始).xls》
 * @author pengliang
 *
 */
public class KeyProjectDirector2015Importer extends Importer {

	private PoiExcelReader reader;

	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	protected void work() throws Throwable {
		importData();
	}
	
	public void importData() throws Exception {
		resetReader();
		Map<String, String> paraMap = new HashMap<String, String>();
		Set<String> exp = new HashSet<String>();
		
		while (next(reader)) {
			if(C.length() == 0 || C == null) {
				break;
		    }
			if(reader.getCurrentRowIndex() == 443) {
				continue;
			}
			System.out.println(reader.getCurrentRowIndex() + " / " + reader.getRowNumber());
			paraMap.put("unitName", D);
			paraMap.put("name", C.replaceAll("\\s+", ""));
			String code = (String) dao.queryUnique("select unit.code from University unit where unit.name = :unitName", paraMap);
			if(code == null) {
				exp.add("高校不存在：：" + D + ":" + C);
			} else {
				paraMap.put("code", code);
				Expert expert = (Expert) dao.queryUnique("select expert from Expert expert where expert.universityCode=:code and expert.name=:name",paraMap);
				if(expert == null) {
					exp.add("高校存在，但专家不存在：：" + D + ":" + C);
				} else {
					expert.setIsKeyProjectDirector(1);
					dao.modify(expert);
				}
			}
		}
		
		if(exp.size() > 0) {
			System.out.println(exp.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public KeyProjectDirector2015Importer() {}
	
	public KeyProjectDirector2015Importer(String file) {
		reader = new PoiExcelReader(file);
	}

}
