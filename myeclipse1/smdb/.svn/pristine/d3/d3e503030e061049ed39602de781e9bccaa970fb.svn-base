package csdc.action.other;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.bean.Agency;
import csdc.bean.NssfProjectApplication;
import csdc.bean.SinossProjectApplication;
import csdc.service.imp.NssfService;
import csdc.tool.HSSFExport;
import csdc.tool.execution.finder.UniversityFinder;

/**
 * 国家社会科学基金项目申请
 * @author wangyi
 *
 */
public class NssfApplicationAction extends BaseAction {

	private static final long serialVersionUID = 6459717508852974162L;

	private final static String HQL = 
			"select " +
			"	npa.name, " +
			"	npa.type, " +
			"	npa.discipline, " +
			"	npa.applicant, " +
			"	npa.gender, " +
			"	npa.national, " +
			"	npa.birthday, " +
			"	npa.unit, " +
			"	npa.province, " +
			"	npa.productName, " +
			"	npa.year, " +
			"	npa.firstAuditResult, " +
			"	npa.firstAuditDate, " +
			"	npa.university " +
			"from " +
			"	NssfProjectApplication npa " +
			"where 1=1 ";

	private final static String[] COLUMN = new String[]{
		"npa.name",
		"npa.type",
		"npa.discipline",
		"npa.applicant",
		"npa.gender",
		"npa.national",
		"npa.birthday",
		"npa.unit",
		"npa.province",
		"npa.productName",
		"npa.year",
		"npa.firstAuditResult",
		"npa.firstAuditDate",
		"npa.university"
	};// 排序列

	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(npa.name) like :keyword",
		"LOWER(npa.type) like :keyword",
		"LOWER(npa.discipline) like :keyword",
		"LOWER(npa.applicant) like :keyword",
		"LOWER(npa.gender) like :keyword",
		"LOWER(npa.national) like :keyword",
		"LOWER(to_char(npa.birthday, 'yyyy-MM-dd')) like :keyword",
		"LOWER(npa.unit) like :keyword",
		"LOWER(npa.province) like :keyword",
		"LOWER(npa.productName) like :keyword",
		"npa.year = :year",
		"LOWER(npa.firstAuditResult) like :keyword",
		"LOWER(to_char(npa.firstAuditDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(npa.university) like :keyword"
	};

	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	
	@Autowired
	private NssfService nssfService;
	
	private int year;
	private String fileFileName;//导出文件名
	public Map<String, List<SinossProjectApplication>> applicantUnivMap;
	@Autowired
	private UniversityFinder universityFinder;

	@Override
	public String pageName() {
		return "nssfApplicationPage";
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}

	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
		if (year != 0) {
			hql.append(" and npa.year = " + year + " and npa.firstAuditResult is not null ");
		}
		if (keyword != null && !keyword.trim().isEmpty()) {
			//处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = SEARCH_CONDITIONS;
			for (int i = 0; !flag && i < sc.length; i++) {
				if (searchType == i) {
					hql.append(" and ").append(sc[i]);
					flag = true;
				}
				tmp.append(sc[i]).append(i < sc.length - 1 ? " or " : ") ");
			}
			if (!flag) {
				hql.append(" and ").append(tmp);
			}
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
			map.put("year", keyword != null && keyword.matches("\\d{4}")? Integer.valueOf(keyword) : 0);
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return "npa.id";
	}

	/**
	 * 进入初审页面
	 * @return
	 */
	public String toFirstAudit() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String y = sdf.format(new Date());
		year = Integer.parseInt(y);
		return SUCCESS;
	}

	/**
	 * 确认导出初审结果
	 * @return
	 */
	public String confirmExportOverView() {
		return SUCCESS;
	}

	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception {
		//导出的Excel文件名
		fileFileName = "国家社会科学基金项目初审结果一览表.xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		return exportFirstAudit();
	}
	
	/**
	 * 导出xxxx年度国家社会科学基金项目/后期资助项目初审结果一览表
	 * @return
	 * @throws Exception 
	 */
	public InputStream exportFirstAudit() throws Exception {
		//项目初审结果
		StringBuffer hql4Reason = new StringBuffer("select npa.id, npa.name, npa.type, npa.university, npa.applicant, npa.firstAuditResult from " +
				"NssfProjectApplication npa where npa.firstAuditResult is not null and npa.firstAuditDate is not null and npa.year = :year ");
		Map map = new HashMap();
		map.put("year", year);
		keyword = new String(keyword.getBytes("ISO8859-1"), "UTF-8");
		if (keyword != null && !keyword.trim().isEmpty()) {
			//处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = SEARCH_CONDITIONS;
			for (int i = 0; !flag && i < sc.length; i++) {
				if (searchType == i) {
					hql4Reason.append(" and ").append(sc[i]);
					flag = true;
				}
				tmp.append(sc[i]).append(i < sc.length - 1 ? " or " : ") ");
			}
			if (!flag) {
				hql4Reason.append(" and ").append(tmp);
			}
			
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
		}
		hql4Reason.append(" order by npa.university asc");
		List dataList = dao.query(hql4Reason.toString(), map);
		
		//添加序号
		for (int i=0, size = dataList.size(); i < size; i++) {
			Object[] obj = (Object[]) dataList.get(i);
			obj[0] = i + 1;
		}
		
		//导出配置
		String header = "";
		String[] title = {"序号", "项目名称", "项目类别", "学校名称", "申请人", "初审结果"};
		String tail = "";
		header = year + "年度国家社会科学基金项目初审结果一览表";
		tail = "初审规则：\n" +
				"申请国家社科基金的负责人不能同时申请同年度的教育部一般项目和基地项目。";
		float tailHeight = 27.0f;
		return HSSFExport.commonExportExcel(dataList, header, title, tail, tailHeight);
	}
	
	/**
	 * 进行指定条件的初审操作
	 * @return
	 */
	public String firstAudit() {
		try {
			initApplicantUnivMap(year);
		} catch (Exception e) {
			e.printStackTrace();
			return INPUT;
		}
		String hql = "select npa from NssfProjectApplication npa where npa.year = ? ";
		List<NssfProjectApplication> npaList = null;
		try {
			npaList = dao.query(hql, year);

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SinossProjectApplication> spApplications = null;
		for (int i = 0; i < npaList.size(); i++) {
			NssfProjectApplication nApplication = npaList.get(i);
			if (null != nApplication.getFirstAuditDate()) {//预处理：查重前先清空原有初审结果
				nApplication.setFirstAuditResult(null);	//初审结果
				nApplication.setFirstAuditDate(null);	//初审时间
			}
			if (nApplication.getUniversity() != null) {
				spApplications = (List<SinossProjectApplication>) applicantUnivMap.get(nApplication.getApplicant() + nApplication.getUniversity());
			} else {
				spApplications = (List<SinossProjectApplication>) applicantUnivMap.get(nApplication.getApplicant() + nApplication.getUnit());
			}
			if (spApplications != null) {
				String result = "";
				String resultGeneral = "一般项目申请（";
				String resultInstp = "基地项目申请（";
				for (int j = 0; j < spApplications.size(); j++) {
					if (spApplications.get(j).getTypeCode().equals("gener")) {
						resultGeneral = resultGeneral.concat(spApplications.get(j).getProjectName() + "; ");
					} else if (spApplications.get(j).getTypeCode().equals("base")) {
						resultInstp = resultInstp.concat(spApplications.get(j).getProjectName() + "; ");
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
			dao.modify(nApplication);
		}
		jsonMap.put("firstAuditFlag", "success");
		applicantUnivMap = null;//清空applicantUnivMap缓存
		return SUCCESS;
	}
	
	/**
	 * 初始化applicantUnivMap
	 * @return
	 * @throws Exception 
	 */
	private void initApplicantUnivMap(int year) throws Exception {
		long beginTime = new Date().getTime();

		applicantUnivMap = new HashMap<String, List<SinossProjectApplication>>();
		String yearString = year + "";
		List<SinossProjectApplication> list = dao.query("select sp from SinossProjectApplication sp where " +
				"sp.checkStatus in ('2', '4') and sp.typeCode = 'gener' and sp.year = ?", yearString);
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
		List<SinossProjectApplication> list1 = dao.query("select sp from SinossProjectApplication sp where " +
				"sp.checkStatus in ('2', '4') and sp.typeCode = 'base' and sp.year = ?", yearString);
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
	
	public NssfService getNssfService() {
		return nssfService;
	}

	public void setNssfService(NssfService nssfService) {
		this.nssfService = nssfService;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
}