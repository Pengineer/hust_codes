package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.Paper;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.PaperFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_ConferencePaper表
 * 按照[成果名+作者名+出版日期]来判重
 * 
 * @author xuhan
 *
 */
public class CMIPSACConferencePaperImporter extends Importer {
	
	private JdbcTemplateReader reader;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;

	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private PaperFinder paperFinder;
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	
	private void resetReader() throws Exception {
		reader.query("SELECT [PaperName] " +	//A
				",[ConferenceName] " +	//B
				",[ConferenceDate] " +	//C
				",staff.StaffName " +	//D
				",[CoAuthor] " +	//E
				",[IsDueBase] " +	//F
				",indexType.TypeName " +	//G
				",[AuthorIndex] " +	//H
				",base.BaseName " +	//I
				",univ.UniName " +	//J
				",[Shown] " +	//K
				"FROM [CMIPS].[dbo].[AC_ConferencePaper] paper " + 
				"left join [CMIPS].[dbo].Base_staff staff on paper.StaffID = staff.StaffID " + 
				"left join [CMIPS].[dbo].BaseList base on base.[BaseID] = paper.[BaseID] " + 
				"left join [CMIPS].[dbo].Lib_IndexType indexType on indexType.TypeID = paper.TypeID " +
				"left join [CMIPS].[dbo].Lib_University univ on base.UniID = univ.UniID");
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		SystemOption 会议论文 = systemOptionDao.query("productType", "014");
		
		while (next(reader)) {
			if (I.equals("中心测试账号")) {
				continue;
			}
			if (A == null || A.isEmpty()) {
				continue;
			}
			System.out.println(reader.getCurrentRowIndex());
			
			Agency university = universityFinder.getUnivByName(J);
			Institute institute = instituteFinder.getInstitute(university, I, false);
			
			Paper paper = paperFinder.getPaper(A, D, C != null ? sdf.parse(C) : null, true);
			paper.setType(会议论文);
			beanFieldUtils.setField(paper, "publication", B, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(paper, "otherAuthorName", E, BuiltinMergeStrategies.APPEND);
			//F 是否署名基地
			
			SystemOption 索引类型 = findSystemOption(G.replaceAll("CSSCI.*", "CSSCI"));
			paper.setPublicationLevel(索引类型);
			
			if (D != null && D.length() > 0) {
				Teacher author = univPersonFinder.findTeacher(D, institute);
				paper.setAuthor(author.getPerson());
				paper.setAuthorType(1);
			}
			
			paper.setUniversity(university);
			paper.setInstitute(institute);
			paper.setAgencyName(university.getName());
			paper.setDivisionName(institute.getName());
			paper.setIsImported(1);
			paper.setSubmitStatus(3);
			paper.setAuditStatus(3);
			paper.setAuditResult(2);
		}
	}
	
	Map<String, SystemOption> soMap = new HashMap();
	SystemOption findSystemOption(String name) {
		SystemOption so = soMap.get(name);
		if (so == null) {
			so = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'indexType' and so.name = ? ", name);
			soMap.put(name, so);
		}
		return so;
	}

	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (I.equals("中心测试账号")) {
				continue;
			}
			Agency university = universityFinder.getUnivByName(J);
			Institute institute = instituteFinder.getInstitute(university, I, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + J + " - " + I);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	
	public CMIPSACConferencePaperImporter(){
	}
	
	public CMIPSACConferencePaperImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
