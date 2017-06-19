package csdc.tool.execution.importer;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.tool.StringTool;
import csdc.tool.reader.PoiExcelReader;

/**
 * 导入20130104_2012年最终评审专家统计表.xls
 * @author fengcl
 *
 */
public class Expert2012ReviewYearImporter extends Importer{
	
	private PoiExcelReader reader;
	
	private String year = "2012";

	protected void work() throws Throwable {
		importData();
	}
	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(1);
	}
	
	private void importData() throws Exception {
		resetReader();
		
		//高校名称 -> 高校代码
		Map<String, String> univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		
		next(reader);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			Expert expert = findExpert(univNameCodeMap.get(A), StringTool.replaceBlank(C), Integer.parseInt(StringTool.fix(D)));
			
			if (null != expert) {
				Set<String> set = new TreeSet<String>(Arrays.asList((expert.getGeneralReviewYears() == null) ? year : expert.getGeneralReviewYears() + "; " + year));
				expert.setGeneralReviewYears(StringTool.join(set, "; "));
			}else{
				System.out.println(univNameCodeMap.get(A) + A + "：" + C + D);
			}
		}
	}
	
	private Expert findExpert(String univCode, String name, int number){
		Expert expert = (Expert) dao.queryUnique("from Expert e where e.universityCode = ? and e.name = ? and e.number = ?", univCode, name, number);
		return expert;
	}
	
	public Expert2012ReviewYearImporter() {
	}
	
	public Expert2012ReviewYearImporter(String file) {
		reader = new PoiExcelReader(file);
	}
	
	public static void main(String[] args) {
		System.out.println("Byte:" + Byte.SIZE);
		System.out.println("Byte:" + Byte.MIN_VALUE);
		System.out.println("Byte:" + Byte.MAX_VALUE);
		System.out.println("Byte:" + Byte.TYPE);
		
		System.out.println("Character:" + Character.SIZE);
		System.out.println("Character:" + (int)Character.MIN_VALUE);
		System.out.println("Character:" + (int)Character.MAX_VALUE);
		System.out.println("Character:" + Character.TYPE);
		
		System.out.println("Short:" + Short.SIZE);
		System.out.println("Short:" + Short.MIN_VALUE);
		System.out.println("Short:" + Short.MAX_VALUE);
		System.out.println("Short:" + Short.TYPE);
		
		System.out.println("Integer:" + Integer.SIZE);
		System.out.println("Integer:" + Integer.MIN_VALUE);
		System.out.println("Integer:" + Integer.MAX_VALUE);
		System.out.println("Integer:" + Integer.TYPE);
		
		System.out.println("Long:" + Long.SIZE);
		System.out.println("Long:" + Long.MIN_VALUE);
		System.out.println("Long:" + Long.MAX_VALUE);
		System.out.println("Long:" + Long.TYPE);
		
		System.out.println("Double:" + Double.SIZE);
		System.out.println("Double:" + Double.MIN_VALUE);
		System.out.println("Double:" + Double.MAX_VALUE);
		System.out.println("Double:" + Double.TYPE);
		
		System.out.println("Float:" + Float.SIZE);
		System.out.println("Float:" + Float.MIN_VALUE);
		System.out.println("Float:" + Float.MAX_VALUE);
		System.out.println("Float:" + Float.TYPE);
		
	}
}
