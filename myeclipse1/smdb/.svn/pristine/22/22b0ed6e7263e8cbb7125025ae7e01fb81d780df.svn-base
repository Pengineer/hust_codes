package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

public class FixChechTotal20150512 extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private Tool tool;

	@SuppressWarnings("unchecked")
	@Override
	protected void work() throws Throwable {
		reader.readSheet(0);
//		Map<String, String> map = new HashMap<String, String>();
		
		List<String> temp = new ArrayList<String>();
		
		List<Object[]> lists = dao.query("select app.name, app.applicantName from ProjectApplication app where app.type='general' and c_year=2015 and app.ministryAuditorName='范明宇'");
		
		for (Object[] list : lists){
			String name = (String)list[0];
			String applicantName = (String)list[1];
			temp.add(name + "--" + applicantName);
		}
		int current =0;
		int total = reader.getRowNumber();
		while(next(reader)){
			System.out.println((++current) + "/" + total);
			int index = temp.indexOf(B.trim() + "--" + tool.getName(S));
			if(index != -1){
				temp.remove(B.trim() + "--" + tool.getName(S));
			}
			if(index == -1){
				System.out.println(B + "--" + S);
			}
		}
		
		if(temp.size() > 0){
			System.out.println(temp.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public FixChechTotal20150512(){}
	
	public FixChechTotal20150512(String fileName){
		reader = new ExcelReader(fileName);
	}
	
	/*
	 * 
1，审核状态为8（已修改：教师提交申请，学校审核退回修改，教师已修改，还未提交），数据中心判定为校级通过（误判）
多层次房价泡沫监测及其市场风险演化模式研究--郭文伟 
新公共管理理论对高校教育管理创新的借鉴意义研究--李玉秀
新媒介语境下诚信体系建构与转型跨越发展关系的研究----以山西省重点领域为例--李牡丹
2，社科网提供的最终审核状态2（校级通过）
大学生创业观念发展及教育规律研究--曹扬
3，省级审核通过后退给校级，校级不通过（数据中心误判）
民国重庆度政研究（1912—1949）--廖小波


媒介融合趋势下我国传媒产业发展问题及对策研究--师岚
	 * */

}
