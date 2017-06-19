package csdc.tool.execution.importer;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.SystemOption;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 导入《2005-2010一般项目项目子类_批准经费_拨款信息_增补导入.xls》
 * 更新一般项目项目子类、批准经费、2010年项目的首次拨款信息（不更新申请表）
 * @author xuhan
 *
 */
public class GeneralSubtypeApprovedfeeFundingImporter extends Importer {
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private Tool tool;

	public GeneralSubtypeApprovedfeeFundingImporter() {}
	
	public GeneralSubtypeApprovedfeeFundingImporter(String filePath) {
		super(filePath);
	}
	
	@Override
	public void work() throws Exception {
		SystemOption 规划基金项目 = (SystemOption) dao.query(SystemOption.class, "planFund");
		SystemOption 青年基金项目 = (SystemOption) dao.query(SystemOption.class, "youngFund");
		SystemOption 专项任务项目 = (SystemOption) dao.query(SystemOption.class, "specialFund");
		SystemOption 自筹经费项目 = (SystemOption) dao.query(SystemOption.class, "noFeeFund");
		
		getContentFromExcel(0);
		
		StringBuffer exMsg = new StringBuffer();
		StringBuffer infoMsg = new StringBuffer();
		
		while (next()) {
			if (curRowIndex % 100 == 0) {
				System.out.println(curRowIndex);
			}

			if (A.isEmpty()) {
				continue;
			}
			GeneralApplication ga = generalProjectFinder.findApplication(B, C);
			
			if (ga == null) {
				exMsg.append("找不到的项目: " + A + " - " + B + " - " + C + "\n");
				continue;
			}
			
			if (ga.getYear() != Integer.parseInt(A)) {
				exMsg.append("项目年份异常: " + A + " - " + B + " - " + C + "\n");
				continue;
			}

			if (ga.getGeneralGranted().isEmpty()) {
				exMsg.append("库内无立项数据的项目: " + A + " - " + B + " - " + C + "\n");
				continue;
			}

			GeneralGranted gg = ga.getGeneralGranted().iterator().next();
			SystemOption newSubtype = null;
			if (D.contains("规划")) {
				newSubtype = 规划基金项目;
			} else if (D.contains("青年")) {
				newSubtype = 青年基金项目;
			} else if (D.contains("专项")) {
				newSubtype = 专项任务项目;
			} else if (D.contains("自筹")) {
				newSubtype = 自筹经费项目;
			} else {
				continue;
			}
			
			if (!newSubtype.equals(gg.getSubtype())) {
				infoMsg.append("项目子类有变化: " + A + " - " + B + " - " + C + "\n");
			}
			
			Double newApprovedFee = tool.getFee(E);
			if (!newApprovedFee.equals(gg.getApproveFee())) {
				infoMsg.append("批准经费有变化: " + A + " - " + B + " - " + C + "\n");
			}
			
			gg.setSubtype(newSubtype);
			gg.setApproveFee(newApprovedFee);
			
			Double firstFee = tool.getFee(F);
			if (firstFee > 0.01 && !hasFirstFee(gg)) {
				infoMsg.append("库内无首次拨款，准备导入: " + A + " - " + B + " - " + C + "\n");
				GeneralFunding gf = new GeneralFunding();
				gf.setFee(firstFee);
				gf.setDate(tool.getDate(ga.getYear(), 12, 1));
				gg.addFunding(gf);
			}
		}

		if (exMsg.length() > 0) {
			System.out.println(exMsg);
			throw new RuntimeException();
		}
		if (infoMsg.length() > 0) {
			System.out.println();
			System.out.println(infoMsg);
		}
	}

	/**
	 * 判断该项目是否已经导入了首次拨款数据
	 * @param gg
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean hasFirstFee(GeneralGranted gg) {
		for (Iterator iterator = gg.getGeneralFunding().iterator(); iterator.hasNext();) {
			GeneralFunding gf = (GeneralFunding) iterator.next();
			if (gf.getDate().getYear() + 1900 == gg.getApplication().getYear()) {
				return true;
			}
		}
		return false;
	}
	
}
