package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.OtherProduct;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.OtherProductFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_OtherAchieve表
 * 按照[成果名+作者名+出版日期]来判重
 * 
 * @author xuhan
 *
 */
public class CMIPSACOtherAchieveImporter extends Importer {
	
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
	private OtherProductFinder otherProductFinder;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	private void resetReader() throws Exception {
		reader.query(
			"SELECT " +
			"[AchName], " +	//A
			"staff.StaffName, " +	//B
			"otherAchType.TypeName, " +	//C
			"[Department] ," +	//D
			"[PressDate], " +	//E
			"[SupportPrj], " +	//F
			"[Evaluation], " +	//G
			"base.BaseName, " +	//H
			"univ.UniName " +	//I
			"FROM [CMIPS].[dbo].[AC_OtherAchieve] achieve " +
			"left join [CMIPS].[dbo].Base_staff staff on achieve.StaffID = staff.StaffID " +
			"left join [CMIPS].[dbo].Lib_OtherAchType otherAchType on achieve.TypeID = otherAchType.TypeID " +
			"left join [CMIPS].[dbo].BaseList base on achieve.[BaseID] = base.[BaseID] " +
			"left join [CMIPS].[dbo].Lib_University univ on base.UniID = univ.UniID " +
			"order by otherAchType.TypeName desc"
  		);
	}
	
	public void work() throws Exception {
//		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		while (next(reader)) {
			if (H.equals("中心测试账号")) {
				continue;
			}
			if (A == null || A.isEmpty()) {
				continue;
			}
			System.out.println(reader.getCurrentRowIndex());
			
			Agency university = universityFinder.getUnivByName(I);
			Institute institute = instituteFinder.getInstitute(university, H, false);
			
			OtherProduct otherProduct = otherProductFinder.getOtherProduct(A, B, E != null ? sdf.parse(E) : null, true);
			beanFieldUtils.setField(otherProduct, "subtype", C, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(otherProduct, "publishUnit", D, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(otherProduct, "supportProject", F, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(otherProduct, "evaluation", G, BuiltinMergeStrategies.APPEND);
			
			if (B != null && B.length() > 0) {
				Teacher author = univPersonFinder.findTeacher(B, institute);
				otherProduct.setAuthor(author.getPerson());
				otherProduct.setAuthorType(1);
			}
			
			otherProduct.setUniversity(university);
			otherProduct.setInstitute(institute);
			otherProduct.setAgencyName(university.getName());
			otherProduct.setDivisionName(institute.getName());
			otherProduct.setIsImported(1);
			otherProduct.setSubmitStatus(3);
			otherProduct.setAuditStatus(3);
			otherProduct.setAuditResult(2);
		}
	}
	
	public void validate() throws Exception {
	}
	
	
	public CMIPSACOtherAchieveImporter(){
	}
	
	public CMIPSACOtherAchieveImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
