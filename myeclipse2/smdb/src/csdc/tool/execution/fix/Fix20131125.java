package csdc.tool.execution.fix;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpGranted;
import csdc.bean.InstpVariation;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 将社科网对接未审完数据推送至教育部审核
 * Excel：《2013中检变更-系统无变更数据.xls》
 * @author wangyi
 * @status 
 */
public class Fix20131125 extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private Tool tool;
	
	public Fix20131125() {
		
	}
	
	public Fix20131125(String filePath) {
		excelReader = new ExcelReader(filePath);
	}

	public void work() throws Exception {
		excelReader.readSheet(0);
		int i = 0;//需要更改状态数据
		int j = 0;//库中状态已为5
		int k = 0;//库中不存在对应数据
		int l = 0;//只存在中检延期
		while (next(excelReader)) {
			if (B.contains("JJD")) {
				InstpGranted instpGranted = instpProjectFinder.findGranted(B);
				Set<InstpVariation> instpVariations = instpGranted.getInstpVariation();
				int iFlag = 0;
				int iMid = 0;
				for (InstpVariation iVariation: instpVariations) {
					if (iVariation.getImportedDate().after(tool.getDate("20131019")) && (iVariation.getIsImported() == 0)) {
						if ((iVariation.getOtherInfo() != null) && ("中检延期").contains(iVariation.getOtherInfo())) {
							iMid = 1;
						} else {
							if (iVariation.getStatus() == 5) {
								j++;
							} else {
								i++;
								System.out.println(A + B);
							}
						}
						iFlag++;
					}
//					iVariation.setStatus(5);
				}
				if (instpVariations.isEmpty()) {
					k++;
				} else {
					if (iFlag == 0) {
						k++;
					} else if (iFlag == 1 && iMid == 1) {
						l++;
					}
				}
			} else {
				GeneralGranted generalGranted = generalProjectFinder.findGranted(B);
				Set<GeneralVariation> generalVariations = generalGranted.getGeneralVariation();
				
				int gFlag = 0;
				int gMid = 0;
				for (GeneralVariation gVariation: generalVariations) {
					if (gVariation.getImportedDate().after(tool.getDate("20131019")) && (gVariation.getIsImported() == 0)) {
						if ((gVariation.getOtherInfo() != null) && ("中检延期").contains(gVariation.getOtherInfo())) {
							gMid = 1;
						} else {
							if (gVariation.getStatus() == 5) {
								j++;
							} else {
								i++;
								System.out.println(A + B);
							}
						}
						gFlag++;
					}
//					gVariation.setStatus(5);
				}
				if (generalVariations.isEmpty()) {
					k++;
				} else {
					if (gFlag == 0) {
						k++;
					} else if (gFlag == 1 && gMid == 1) {
						l++;
					}
				}
			}
		}
		System.out.println("i="+i);
		System.out.println("j="+j);
		System.out.println("k="+k);
		System.out.println("l="+l);
	}

}
