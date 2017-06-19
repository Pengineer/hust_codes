package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.NssfProjectApplication;
import csdc.bean.SinossProjectApplication;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 1、Excel:《2014年国家社科基金后期项目申报数据_修正导入.xls》入库
 * 2、2014年国社科后期申报数据查重：
 *  初审规则：申请国家社科基金后期项目的负责人不能同时申请同年度的教育部一般项目和基地项目。
 * @author wangyi
 * @status 
 */
public class NssfPostApplication2014Importer extends Importer {
	/**
	 * 《2014年国家社科基金后期项目申报数据_修正导入.xls》
	 */
	private ExcelReader excelReader;
	
	public Map<String, List<SinossProjectApplication>> applicantUnivMap;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	public NssfPostApplication2014Importer() {}
	
	public NssfPostApplication2014Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 导入数据并进行查重
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		initApplicantUnivMap();
		excelReader.readSheet(0);		
		
		while (next(excelReader)) {
			if (A.length() == 0) {
				break;
		    }
			System.out.println(excelReader.getCurrentRowIndex() + " / " + excelReader.getRowNumber());
			NssfProjectApplication nApplication = new NssfProjectApplication();
			nApplication.setName(B + "(据成果名称推测)");
			nApplication.setApplicant(C);
			nApplication.setGender(E);
			nApplication.setBirthday(tool.getDate(F));
			nApplication.setProvince(G);
			nApplication.setUnit(D);
			nApplication.setProductName(B);
			nApplication.setYear(2014);
			nApplication.setType("后期资助项目");
			Agency univ = null;
			List<SinossProjectApplication> spApplications = null;
			univ = universityFinder.getUniversityWithLongestName(D);
			if (univ != null) {
				nApplication.setUniversity(univ.getName());
				nApplication.setUniversityId(univ.getId());
				spApplications = (List<SinossProjectApplication>) applicantUnivMap.get(C + univ.getName());
			} else {
				spApplications = (List<SinossProjectApplication>) applicantUnivMap.get(C + D);
			}
			nApplication.setImportDate(new Date());
			if (spApplications != null) {
				String result = "";
				String resultGeneral = "一般项目申报（";
				String resultInstp = "基地项目申报（";
				for (int i = 0; i < spApplications.size(); i++) {
					if (spApplications.get(i).getTypeCode().equals("gener")) {
						resultGeneral = resultGeneral.concat(spApplications.get(i).getProjectName() + "; ");
					} else if (spApplications.get(i).getTypeCode().equals("base")) {
						resultInstp = resultInstp.concat(spApplications.get(i).getProjectName() + "; ");
					}
				}
				if (resultGeneral.contains(";")) {
					resultGeneral = resultGeneral.substring(0, resultGeneral.length() - 2);
					resultGeneral = resultGeneral.concat("）");
					result += resultGeneral;
				}
				if (resultInstp.contains(";")) {
					resultInstp = resultInstp.substring(0, resultInstp.length() - 2);
					resultInstp = resultInstp.concat("）");
					if (result.contains(resultGeneral)) {
						result += "; ";
					}
					result += resultInstp;
				}
				nApplication.setFirstAuditResult(result);
				nApplication.setFirstAuditDate(new Date());
			}
			dao.add(nApplication);

		}
	}
	
	/**
	 * 初始化applicantUnivMap
	 * @return
	 * @throws Exception 
	 */
	private void initApplicantUnivMap() throws Exception {
		long beginTime  = new Date().getTime();

		applicantUnivMap = new HashMap<String, List<SinossProjectApplication>>();
		
		List<SinossProjectApplication> list = dao.query("select sp from SinossProjectApplication sp where sp.checkStatus in ('2', '4') and sp.typeCode = 'gener'");
		for (SinossProjectApplication sp : list) {
			List<SinossProjectApplication> sinossProjectApplications = new ArrayList<SinossProjectApplication>();
			sinossProjectApplications.add(sp);
			List<SinossProjectApplication> spApplicationList = null;
			spApplicationList = (List<SinossProjectApplication>) applicantUnivMap.get(sp.getApplyer() + sp.getUnitName());
			if (spApplicationList != null) {
				for (int i = 0; i < spApplicationList.size(); i++) {
					sinossProjectApplications.add(spApplicationList.get(i));
				}
			}
			applicantUnivMap.put(sp.getApplyer() + sp.getUnitName(), sinossProjectApplications);
		}
		List<SinossProjectApplication> list1 = dao.query("select sp from SinossProjectApplication sp where sp.checkStatus in ('2', '4') and sp.typeCode = 'base'");
		for (SinossProjectApplication sp : list1) {
			List<SinossProjectApplication> sinossProjectApplications = new ArrayList<SinossProjectApplication>();
			sinossProjectApplications.add(sp);
			Agency univAgency = universityFinder.getUniversityWithLongestName(sp.getDivision());
			if (univAgency == null) {
				univAgency = universityFinder.getUniversityWithLongestName(sp.getUnitName());
			}
			List<SinossProjectApplication> spApplicationList = null;
			spApplicationList = (List<SinossProjectApplication>) applicantUnivMap.get(sp.getApplyer() + univAgency.getName());
			if (spApplicationList != null) {
				for (int i = 0; i < spApplicationList.size(); i++) {
					sinossProjectApplications.add(spApplicationList.get(i));
				}
			}
			applicantUnivMap.put(sp.getApplyer() + univAgency.getName(), sinossProjectApplications);
		}
		
		System.out.println("initApplicantUnivMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
}
