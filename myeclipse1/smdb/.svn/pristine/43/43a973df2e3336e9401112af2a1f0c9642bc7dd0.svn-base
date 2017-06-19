package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Book;
import csdc.bean.Institute;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.BookFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_Book表
 * 按照[成果名+作者名+出版日期]来判重
 * 
 * 
 * @author xuhan
 *
 */
public class CMIPSACBookImporter extends Importer {
	
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
	private BookFinder bookFinder;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	
	private void resetReader() throws Exception {
		reader.query(
			"SELECT [BookName]," +	//A
			"staff.StaffName," +	//B
			"[CoAuthor]," +	//C
			"bookType.TypeName," +	//D
			"[PressDate]," +	//E
			"[PressDepartment]," +	//F
			"base.BaseName," +	//G
			"univ.UniName " +	//H
			" FROM [CMIPS].[dbo].[AC_Book] book" +
			" left join [CMIPS].[dbo].Base_staff staff on book.StaffID = staff.StaffID " +
			" left join [CMIPS].[dbo].Lib_BookType bookType on book.TypeID = bookType.TypeID " +
			" left join [CMIPS].[dbo].BaseList base on book.[BaseID] = base.[BaseID] " +
			" left join [CMIPS].[dbo].Lib_University univ on base.UniID = univ.UniID ");
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		SystemOption 著 = systemOptionDao.query("productType", "021");
		SystemOption 译著 = systemOptionDao.query("productType", "024");
		SystemOption 编著 = systemOptionDao.query("productType", "022");
		
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex());
			if (G.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(H);
			Institute institute = instituteFinder.getInstitute(university, G, false);
			
			Book book = bookFinder.getBook(A, B, E != null ? sdf.parse(E) : null, true);
			
			beanFieldUtils.setField(book, "otherAuthorName", C, BuiltinMergeStrategies.APPEND);
			if (D.equals("专著")) {
				book.setType(著);
			} else if (D.equals("译著")) {
				book.setType(译著);
			} else if (D.equals("编著")) {
				book.setType(编著);
			}
			beanFieldUtils.setField(book, "publishUnit", F, BuiltinMergeStrategies.REPLACE);
			
			if (B != null && B.length() > 0) {
				Teacher author = univPersonFinder.findTeacher(B, institute);
				book.setAuthor(author.getPerson());
				book.setAuthorType(1);
			}
			
			book.setUniversity(university);
			book.setInstitute(institute);
			book.setAgencyName(university.getName());
			book.setDivisionName(institute.getName());
			book.setIsImported(1);
			book.setSubmitStatus(3);
			book.setAuditStatus(3);
			book.setAuditResult(2);
		}
	}
	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (G.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(H);
			Institute institute = instituteFinder.getInstitute(university, G, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + H + " - " + G);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	

	
	
	public CMIPSACBookImporter(){
	}
	
	public CMIPSACBookImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
