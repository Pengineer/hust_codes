package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.SystemOption;
import csdc.dao.JdbcDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库BaseList表
 * @author xuhan
 *
 */
public class CMIPSBaseListImporter extends Importer {
	
	private JdbcTemplateReader reader;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private Tool tool;

	
	
	private void resetReader() throws Exception {
		reader.query("SELECT baseType.BaseTypeName" +	//A
		     ",[BaseName]" +	//B
		     ",[EnglishName]" +	//C
		     ",[BaseCode]" +	//D
		     ",baseBatch.BatchName" +	//E
		     ",researchType.ResearchName" +	//F
		     ",baseAuthority.AuthName" +	//G
		     ",univ.UniName" +	//H
		     ",[Address]" +	//I
		     ",[PostCode]" +	//J
		     ",[Email]" +	//K
		     ",[Telephone]" +	//L
		     ",[Fax]" +	//M
		     ",[EstablishDate]" +	//N
		     ",[Cn_BookNum]" +	//O
		     ",[En_BookNum]" +	//P
		     ",[Cn_JournalNum]" +	//Q
		     ",[En_JournalNum]" +	//R
		     ",[DataRoom]" +	//S
		     ",[OfficeArea]" +	//T
		     ",[LinkmanID]" +	//U
		     ",[IsSameGrade]" +	//V
		     ",[IsIndependent]" +	//W
		     " FROM [CMIPS].[dbo].[BaseList] base " +
		     " left JOIN [CMIPS].[dbo].[Lib_BaseType] baseType ON base.BaseTypeID = baseType.BaseTypeID " +
		     " JOIN [CMIPS].[dbo].[Lib_BaseBatch] baseBatch ON base.BatchID = baseBatch.BatchID " +
		     " left JOIN [CMIPS].[dbo].[Lib_ResearchType] researchType ON base.ResearchID = researchType.ResearchID " +
		     " left JOIN [CMIPS].[dbo].[Lib_BaseAuthority] baseAuthority ON base.AuthID = baseAuthority.AuthID " +
		     " left JOIN [CMIPS].[dbo].[Lib_University] univ ON base.UniID = univ.UniID " +
		     " order by baseBatch.BatchName ");
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		SystemOption 基础研究 = systemOptionDao.query("researchActivityType", "01");
		SystemOption 应用研究 = systemOptionDao.query("researchActivityType", "03");

		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex());
			if (B.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(H);
			Institute institute = instituteFinder.getInstitute(university, B, false);
			
			beanFieldUtils.setField(institute, "englishName", C, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(institute, "code", D, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(institute, "approveSession", E, BuiltinMergeStrategies.REPLACE);
			
			if (F.equals("基础研究")) {
				institute.setResearchType(基础研究);
			} else if (F.equals("应用研究")) {
				institute.setResearchType(应用研究);
			}
			
			beanFieldUtils.setField(institute, "address", I, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(institute, "postcode", J, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(institute, "email", K, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(institute, "phone", transPhoneNumber(L), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
			beanFieldUtils.setField(institute, "fax", transPhoneNumber(M), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_APPEND);
			beanFieldUtils.setField(institute, "approveDate", tool.getDate(N), BuiltinMergeStrategies.PRECISE_DATE);
			beanFieldUtils.setField(institute, "chineseBookAmount", O, BuiltinMergeStrategies.SUPPLY);
			beanFieldUtils.setField(institute, "foreignBookAmount", P, BuiltinMergeStrategies.SUPPLY);
			beanFieldUtils.setField(institute, "chinesePaperAmount", Q, BuiltinMergeStrategies.SUPPLY);
			beanFieldUtils.setField(institute, "foreignPaperAmount", R, BuiltinMergeStrategies.SUPPLY);
			institute.setDataroomArea(Integer.parseInt(S));
			institute.setOfficeArea(Integer.parseInt(T));
			//LinkmanID 这个库内有，不导了吧 >_<
		}
	}
	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (B.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(H);
			Institute institute = instituteFinder.getInstitute(university, B, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + H + " - " + B);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	private String transPhoneNumber(String origin) {
		String phone = "";
		for (String tmp : origin.split(",")) {
			if (tmp.length() > 4) {
				phone += tmp + "; ";
			}
		}
		return phone;
	}
	
	
	public CMIPSBaseListImporter(){
	}
	
	public CMIPSBaseListImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
