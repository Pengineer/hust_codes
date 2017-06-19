package csdc.tool.execution.fix;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.SystemOption;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.ExcelReader;

/**
 * 在T_AGENCY表中添加机构标准字段，并更新数据。
 * Excel1：《普通高校代码（2011年1月20日）.xls》
 * Excel2：《成人高校代码（2011年1月20日）.xls》
 * Excel3：《独立学院代码（2011年1月20日）.xls》
 * Excel4：《分校、大专班代码（2011年1月20日）.xls》
 * Excel5：《新增高职院校代码（2011年5月24日）.xls》
 * @author wangyi
 * @status 
 * 备注：
 * 1、《普通高校代码（2011年1月20日）.xls》，找不到的学校1个：昆明医学院，已更名为昆明医科大学；高校办学类型为空1个：广西中医学院，已更名为广西中医药大学。上述情况手工处理为univ_manual。
 * 2、《成人高校代码（2011年1月20日）.xls》，通过高校办学类型未找到1个：徐州教育学院，学校已合并到徐州工程学院，类型从成人高校变为普通高校，已设标准。
 * 3、《独立学院代码（2011年1月20日）.xls》，高校办学类型为空1个：海南大学三亚学院，名称已变更为三亚学院。上述情况手工处理为univ_manual。
 * 4、《新增高职院校代码（2011年5月24日）.xls》，找不到的学校1个：浙江农业商贸职业学院。在数据库中名称为浙江农业商贸职业学院（筹），已设标准。
 */
public class Fix20130531 extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public Fix20130531() {
		
	}
	
	public Fix20130531(String filePath) {
		excelReader = new ExcelReader(filePath);
	}

	public void work() throws Exception {
		excelReader.readSheet(0);
		int i = 0;
		int j = 0;
		while (next(excelReader)) {
			Agency univ = universityFinder.getUnivByName(B);
			/*if (univ == null) {
				System.out.println("找不到的学校：" + B);
				i++;
			} else {
				if (univ.getStyle() != null) {
					//普通高校代码（2011年1月20日）
//					if (!univ.getStyle().contains("11") && !univ.getStyle().contains("12") && !univ.getStyle().contains("13") && !univ.getStyle().contains("14")){
					//成人高校代码（2011年1月20日）
//					if (!univ.getStyle().contains("41") && !univ.getStyle().contains("42") && !univ.getStyle().contains("43") && !univ.getStyle().contains("44") && !univ.getStyle().contains("46")){
					//独立学院代码（2011年1月20日）	
//					if (!univ.getStyle().contains("22")){
					//分校、大专班代码（2011年1月20日）
					if (!univ.getStyle().contains("21")){
						System.out.println("找不到的22：" + B);
						j++;
					} else {
						univ.setStandard("univ_20110120");
					}
				} else {
					System.out.println("类型为空：" + B);
				}
				//新增高职院校代码（2011年5月24日）
				univ.setStandard("univ_20110524");
			}*/
			if (univ == null) {
				System.out.println("找不到的学校：" + B);
			} else {
				if (univ.getIntroduction() != null) {
					System.out.println("已有简介：" + B);
				} else {
					if (C.length() > 0) {
						if (G.length() > 0) {
							univ.setIntroduction("建校基础名称：" + C + "; " + "民办");
						} else {
							univ.setIntroduction("建校基础名称：" + C);
						}
					} else {
						if (G.length() > 0) {
							univ.setIntroduction("民办");
						}
					}
				}
			}
			if(A.length() == 0) {
				break;
			}
		}
		System.out.println("i="+i);
		System.out.println("j="+j);
	}

}
