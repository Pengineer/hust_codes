package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.StringTool;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《2014-2015年全国教育规划立项课题鉴定情况一览表 .xls》
 * @author pengliang
 * @status 
 * 备注：
 * 1、通过批准号找立项数据，共有300条数据找不到对应立项，原因可能是批准号错误。手工通过负责人核查数据后，项目名称一样的批准号以
 *   立项数据为准。剩余无法匹配的结项数据新建记录。
 * 2、经核查立项数据中批准号GFA、ELA后面接7位数字，与其他规则不符，结项数据对应为6位数字，可能有问题，但是为了保持处理规则一致，
 *   先暂以立项数据中批准号为准入库。
 * 3、经核查有部分数据，项目相同，但立项与结项的负责人不一样，先暂以立项为准入库。
 * 
 */
public class NssfNespEndinspection2014_2015Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	/**
	 * [项目名称+姓名] -> [Nssf.id]
	 */
	private Map<String, String> map;
	
	public NssfNespEndinspection2014_2015Importer() {}
	
	public NssfNespEndinspection2014_2015Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
//		checkProjectExistence();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			if (map == null) {
				initMap();
			}
			if (A.length() == 0) {
				break;
		    }
			if (I.equals("暂缓结题")) {
				continue;
			}
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", D);
			if (nssf == null) {
				String key = StringTool.fix(E) + StringTool.fix(F);
				String nssfId = map.get(key);
				if (nssfId != null) {//项目名称和申请人都相同 则更新
					nssf = dao.query(Nssf.class, nssfId);					
				} else {
					Nssf newNssf = new Nssf();
					if (B.length() > 0) {
						newNssf.setDisciplineType(B);
					}
					if (C.length() > 0) {
						newNssf.setType(C);
					}
					if (D.length() > 0) {
						newNssf.setNumber(D);
					}
					if (E.length() > 0) {
						newNssf.setName(E);
					}
					if (F.length() > 0) {
						newNssf.setApplicant(F);
					}
					if (G.length() > 0) {
						newNssf.setUnit(G);
					}
					if (H.length() > 0) {
						newNssf.setCertificate(H);
					}
					if (L.length() > 0) {
						newNssf.setExperts(L.replaceAll("\\s+", "").replaceAll("、", "; "));
					}
					if (I.length() > 0) {
						newNssf.setProductLevel(I);
					}
					if (K.length() > 0) {
						newNssf.setEndDate(tool.getDate(K));
					}										
					if (J.length() > 0) {
						newNssf.setNoIdentifyReason(J);
					}
					newNssf.setImportDate(new Date());
					newNssf.setIsDupCheckGeneral(0);
					newNssf.setStatus(2);
					newNssf.setSingleSubject("教育学");
					dao.add(newNssf);
				}
			} 
			if (nssf != null) {
				if (H.length() > 0) {
					nssf.setCertificate(H);
				}
				if (I.length() > 0) {
					nssf.setProductLevel(I);
				}
				if (K.length() > 0) {
					nssf.setEndDate(tool.getDate(K));
				}
				if (L.length() > 0) {
					nssf.setExperts(L.replaceAll("\\s+", "").replaceAll("、", "; "));
				}
				if (J.length() > 0) {
					nssf.setNoIdentifyReason(J);
				}
				if (C.length() > 0) {
					nssf.setType(C);
				}
				nssf.setImportDate(new Date());
				nssf.setIsDupCheckGeneral(0);
				nssf.setStatus(2);
				saveOrUpdate(nssf);
			}
		}
		
	}
	
	/**
	 * 检查数据是否库内存在
	 * @throws Exception 
	 */
	private void checkProjectExistence() throws Exception {
		if (map == null) {
			initMap();
		}
		excelReader.readSheet(0);
		
		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;
		List<Nssf> applicantList = dao.query("select nssf.applicant from Nssf nssf");
		String applicantString = applicantList.toString();
		while (next(excelReader)) {
			if (A.length() == 0) {
				break;
		    }
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", D);
			if (nssf == null) {
				String key = StringTool.fix(E) + StringTool.fix(F);
				String nssfId = map.get(key);
				if (nssfId != null) {
					System.out.println("存在同校同名：" + D + F + "--" + "项目名称：" + E);
					m++;
				}
				if (applicantString.contains(F)) {
					System.out.println("存在：" + D + F + "--" + "项目名称：" + E);
					i++;
				} else {
					System.out.println("找不到的项目：" + D + F + "--" + "项目名称：" + E);
					j++;
				}								
			} else {
				k++;
			}

		}
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(m);
	}

	private void initMap() {

		Date begin = new Date();
		
		map = new HashMap<String, String>();
		List<Object[]> list = dao.query("select nssf.name, nssf.applicant, nssf.id from Nssf nssf");
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);
			String applicant = StringTool.fix((String) o[1]);
			String nssfId = (String) o[2];
			
			map.put(projectName + applicant, nssfId);
		}
		
		System.out.println("initMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
}
