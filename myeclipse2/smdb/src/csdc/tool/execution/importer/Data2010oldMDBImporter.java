package csdc.tool.execution.importer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Discipline;
import csdc.bean.Doctoral;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;

/**
 * 导入《20110607_全国高校教授数据库.rar》 -> 《data2010old.mdb》 -> 人员数据、单位数据
 * @author xuhan
 *
 */
public class Data2010oldMDBImporter extends Importer {

//	private List dataList = new ArrayList();
	
	/**
	 * 单位ID + "_" + 学校CODE 到 单位实体的映射
	 */
	private Map<String, Object> unitMap = new HashMap<String, Object>();
	
	/**
	 * 学校代码变更
	 */
	private Map<String, String> univCodeChange;

	/**
	 * 民族代码到民族名称的映射
	 */
	private Map<Integer, String> nationMap;

	/**
	 * 职称代码到职称名称的映射
	 */
	private Map<String, String> titleMap;

	/**
	 * 政治面貌代码到政治面貌名称的映射
	 */
	private Map<Integer, String> policyMap;

	/**
	 * 基地研究类型名称到极地研究类型实体的映射
	 */
	private Map<String, SystemOption> researchTypeMap;
	
	/**
	 * 最后学历代码到最后学历名称的映射
	 */
	private Map<String, String> eduLevelMap;
	
	/**
	 * 最后学位代码到最后学位名称的映射
	 */
	private Map<String, String> eduDegreeMap;
	
	/**
	 * 语言代码到语言名称的映射
	 */
	private Map<String, String> languageMap;
	
	/**
	 * 语言掌握程度代码到语言掌握程度名称的映射
	 */
	private Map<String, String> languageLevelMap;
	
	
	private SystemOption bujijidi;		//部级基地
	private SystemOption xiaojijidi;	//校级基地
	private SystemOption qitajidi;		//其他基地
	
	public Data2010oldMDBImporter(){}

	public Data2010oldMDBImporter(File file) {
		super(file);
	}
	
	public Data2010oldMDBImporter(File[] files) {
		super(files);
	}
	
	public void work() throws Exception {
		initRAT();
		initNation();
		initTitle();
		initPolicy();
		initResearchType();
		initUnivCodeChange();
		initEduLevel();
		initEduDegree();
		initLanguage();
		initLanguageLevel();
		
//		tools.initAcademicMap();
//		tools.initCodeUnivMap();
//		tools.initDiscCodeNameMap();
//		tools.initDiscNameCodeMap();
//		tools.initNameUnivMap();
//		tools.initOfficerMap();
//		tools.initTeacherMap();
//		tools.initUnitMap();
//		tools.initUnivNameOtherDeptMap();
		
		importUnit();
		importPerson();
		
		finish();
//		baseService.addOrModify(dataList);
	}
	


	/**
	 * 导入院系(01)、基地(02)、研究机构(06)、博士点(04)、重点学科(05)
	 * @throws Exception
	 */
	private void importUnit() throws Exception {
		getContentFromAccess("select * from unit");
//		getContentFromCSV(0);
		
		StringBuffer invalidUnivCode = new StringBuffer();
		
		while (next()) {
			if (univCodeChange.containsKey(G)) {
				G = univCodeChange.get(G);
			}
			Agency university = tools.getUnivByCode(G);
			if (university == null) {
				invalidUnivCode.append(G + "不存在\n");
			}
			if (invalidUnivCode.length() > 0) {
				continue;
			}
			
			//后面导入的人员有可能从属于此机构，故名称不合法的也导入
			String unitName = C.isEmpty() ? "未知机构" : C;
			
			//单位负责人没有外国人，将汉字、空格、·之外的字符去掉
			AH = AH.replaceAll("[^\\u4e00-\\u9fa5\\s·]+", "");
			AI = AI.replaceAll("[^\\u4e00-\\u9fa5\\s·]+", "");

			if (B.equals("01") || unitName.endsWith("系") || unitName.endsWith("学院")) {
				//院系
				Department department = (Department) tools.getUnit(unitName, university, Department.class);
				department.setName(unitName);
				department.setUniversity(university);
				department.setPhone(O);
				department.setFax(P);
				department.setAddress(Q);
				department.setPostcode(R);
				department.setEmail(S);
				department.setHomepage(T);
				
				if (!AH.isEmpty() || !AI.isEmpty()) {
					Officer officer = tools.getOfficer(AH.isEmpty() ? AI : AH, university);
					officer.setDepartment(department);
					department.setDirector(officer.getPerson());
					saveOrUpdate(officer.getPerson());
					saveOrUpdate(officer);
				}
				
				department.setIntroduction(AK + "\n" + AL);
				department.setCode(BF);
				
				saveOrUpdate(department);
				unitMap.put(A + "_" + university.getCode(), department);
			} else if (B.equals("02") || B.equals("06")) {
				//基地
				Institute institute = (Institute) tools.getUnit(unitName, university, Institute.class);
				institute.setName(unitName);
				institute.setEnglishName(E);
				institute.setAbbr(F);
				institute.setSubjection(university);
				institute.setApproveDate(tools.getDate(M));
				institute.setPhone(O);
				institute.setFax(P);
				institute.setAddress(Q);
				institute.setPostcode(R);
				institute.setEmail(S);
				institute.setHomepage(T);
				institute.setDisciplineType(tools.transformDisc(U));
				institute.setResearchType(researchTypeMap.get(W));
				
				if (AF.equals("A002")) {
					institute.setType(bujijidi);
				} else if (AF.equals("A003")) {
					institute.setType(xiaojijidi);
				} else {
					institute.setType(qitajidi);
				}
				institute.setApproveSession(AG);
				
				if (!AH.isEmpty() || !AI.isEmpty()) {
					Officer officer = tools.getOfficer(AH.isEmpty() ? AI : AH, university);
					officer.setInstitute(institute);
					institute.setDirector(officer.getPerson());
					saveOrUpdate(officer.getPerson());
					saveOrUpdate(officer);
				}
				
				institute.setIntroduction(AK + "\n" + AL);
				institute.setChineseBookAmount(AQ);
				institute.setForeignBookAmount(AR);
				institute.setChinesePaperAmount(AS);
				institute.setForeignPaperAmount(AT);
				institute.setCode(BF);
				
				saveOrUpdate(institute);
				unitMap.put(A + "_" + university.getCode(), institute);
			} else if (B.equals("04")) {
				//博士点
				Doctoral doctoral = (Doctoral) tools.getUnit(unitName, university, Doctoral.class);
				doctoral.setName(unitName);
				doctoral.setCode(BF);
				doctoral.setDiscipline(tools.transformDisc(U));
				doctoral.setIsKey(AN.equals("1") ? 1 : 0);
				doctoral.setUniversity(university);

				saveOrUpdate(doctoral);
				unitMap.put(A + "_" + university.getCode(), doctoral);
			} else if (B.equals("05")) {
				//重点学科
				Discipline discipline = (Discipline) tools.getUnit(unitName, university, Discipline.class);
				discipline.setName(unitName);
				discipline.setCode(BF);
				discipline.setDiscipline(tools.transformDisc(U));
				discipline.setUniversity(university);

				saveOrUpdate(discipline);
				unitMap.put(A + "_" + university.getCode(), discipline);
			}

		}
		if (invalidUnivCode.length() > 0) {
			throw new Exception(invalidUnivCode.toString());
		}
		System.out.println("Unit maxLength:");
		outputMaxLength();
	}
	

	/**
	 * 导入人员信息
	 * @throws Exception
	 */
	private void importPerson() throws Exception {
		getContentFromAccess("select * from person");
//		getContentFromCSV(1);
	
		StringBuffer invalidUnivCode = new StringBuffer();
		StringBuffer invalidUnitId = new StringBuffer();
		
		while (next()) {
//			int length = AO.getBytes("UTF-8").length;
//			if (length > 3900) {
//				System.out.println("C_RESEARCH_SPECIALITY 长度: " + length + " " + D + " " + Q);
//			}
//			length = AR.getBytes("UTF-8").length;
//			if (length > 3900) {
//				System.out.println("c_further_education 长度: " + length + " " + D + " " + Q);
//			}
//			length = AM.getBytes("UTF-8").length;
//			if (length > 3900) {
//				System.out.println("C_RESEARCH_FIELD 长度: " + length + " " + D + " " + Q);
//			}
//			length = AS.getBytes("UTF-8").length;
//			if (length > 3900) {
//				System.out.println("C_PARTTIME_JOB 长度: " + length + " " + D + " " + Q);
//			}
//			length = AN.getBytes("UTF-8").length;
//			if (length > 80) {
//				System.out.println("C_MAJOR 长度: " + length + " " + D + " " + Q);
//			}
//			continue;

			if (univCodeChange.containsKey(Q)) {
				Q = univCodeChange.get(Q);
			}
			Agency university = tools.getUnivByCode(Q);
			if (university == null) {
				invalidUnivCode.append(Q + " 不存在\n");
			}
			if (invalidUnivCode.length() > 0) {
				continue;
			}

			Object unit = unitMap.get(AY + "_" + university.getCode());
			if (unit == null) {
				invalidUnitId.append(AY + "_" + Q + " 不存在\n");
			}
//			if (invalidUnitId.length() > 0) {
//				continue;
//			}
			
			String unitName = null;
			Department department = null;
			Institute institute = null;
			if (unit instanceof Department) {
				department = (Department)unit;
				unitName = department.getName();
			} else if (unit instanceof Institute) {
				institute = (Institute)unit;
				unitName = institute.getName();
			} else {
				department = tools.getOtherDepartment(university);
				unitName = department.getName();
			}
			
			Person person;	//人员基本信息
			Officer officer = null;
			Teacher teacher = null;
			if (D.startsWith("[B@")) {
				//错误姓名
				continue;
			}
			D = tools.getName(D);
			if (D.isEmpty()) {
				//姓名为空则不导入
				continue;
			}
			if ((unitName.contains("社科") || unitName.contains("社会科学") || unitName.contains("科技") || unitName.contains("科学技术")) && (unitName.contains("部") || unitName.contains("处"))) {
				officer = tools.getOfficer(D, university);
				person = officer.getPerson();
				
				officer.setStartDate(tools.getDate(P));
				officer.setPosition(N);
				if (department != null) {
					officer.setDepartment(department);
				} else if (institute != null) {
					officer.setInstitute(institute);
				}
			} else {
				teacher = tools.getTeacher(D, university);
				person = teacher.getPerson();

				teacher.setStartDate(tools.getDate(P));
				teacher.setPosition(N);
				if (department != null) {
					teacher.setDepartment(department);
				} else if (institute != null) {
					teacher.setInstitute(institute);
				}
			}
			if (!C.isEmpty()) {
				person.setIdcardNumber(C);
			}
			if (!E.isEmpty()) {
				person.setEnglishName(E);
			}
			if (!F.isEmpty()) {
				person.setUsedName(F);
			}
			if (H.equals("0")) {
				person.setGender("女");
			} else if (H.equals("1")) {
				person.setGender("男");
			}
			person.setEthnic(nationMap.get(J));
			if (!K.isEmpty()) {
				person.setBirthday(tools.getDate(K));
			}
			Academic academic = tools.getAcademic(person);
			academic.setDiscipline(tools.transformDisc(academic.getDiscipline() + " " + L + " " + AJ + " " + AK));
			//正常学科长度应该不会大于400，故截取前400字符
			while (academic.getDiscipline().getBytes("UTF-8").length > 395) {
				String disc = academic.getDiscipline();
				academic.setDiscipline(disc.substring(0, disc.lastIndexOf(";")));
			}
			academic.setRelativeDiscipline(tools.transformDisc(academic.getRelativeDiscipline() + " " + AL));
			if (titleMap.containsKey(M)) {
				academic.setSpecialityTitle(titleMap.get(M));
			}
			if (!O.isEmpty()) {
				Integer policyCode = -1;
				try {
					policyCode = Integer.parseInt(O);
				} catch (Exception e) {}
				if (policyMap.containsKey(policyCode)) {
					person.setMembership(policyMap.get(policyCode));
				}
			}
			if (!S.isEmpty()) {
				person.setOfficeAddress(S);
			}
			if (!T.isEmpty()) {
				person.setOfficePostcode(T);
			}
			if (!U.isEmpty()) {
				person.setHomePhone(U);
			}
			if (!V.isEmpty()) {
				person.setHomeFax(V);
			}
			if (!X.isEmpty()) {
				person.setMobilePhone(X);
			}
			if (!Y.isEmpty()) {
				person.setEmail(Y);
			}
			if (!Z.isEmpty()) {
				person.setHomepage(Z);
			}
			if (!AA.isEmpty()) {
				person.setOfficePhone(AA);
			}
			if (!AB.isEmpty()) {
				person.setOfficeFax(AB);
			}
			if (AC.equals("1")) {
				academic.setTutorType("博士生导师");
			}
			if (eduLevelMap.containsKey(AD)) {
				academic.setLastEducation(eduLevelMap.get(AD));
			}
			if (eduDegreeMap.containsKey(AE)) {
				academic.setLastDegree(eduDegreeMap.get(AE));
			}
			String lang1 = transLanguage(AF, AG);
			String lang2 = transLanguage(AH, AI);
			String originLang = academic.getLanguage() == null ? "" : academic.getLanguage();
			if (!lang1.isEmpty() && !originLang.contains(lang1)) {
				if (!originLang.isEmpty()) {
					originLang += "; ";
				}
				originLang += lang1;
			}
			if (!lang2.isEmpty() && !originLang.contains(lang2)) {
				if (!originLang.isEmpty()) {
					originLang += "; ";
				}
				originLang += lang2;
			}
			academic.setLanguage(originLang);

			//专业
			academic.setMajor(AN);


			//研究领域（长度超过2000则追加在个人简介后）
			if (AM.getBytes("UTF-8").length > 1998) {
				String intro = person.getIntroduction();
				if (intro == null) {
					intro = "";
				}
				person.setIntroduction(intro + "\n\n" + AM);
			} else {
				academic.setResearchField(AM);
			}
			
			//专业特长（长度超过2000则追加在个人简介后）
			if (AO.getBytes("UTF-8").length > 1998) {
				String intro = person.getIntroduction();
				if (intro == null) {
					intro = "";
				}
				person.setIntroduction(intro + "\n\n" + AO);
			} else {
				academic.setResearchSpeciality(AO);
			}
			
			//进修情况（长度超过2000则追加在个人简介后）
			if (AR.getBytes("UTF-8").length > 1998) {
				String intro = person.getIntroduction();
				if (intro == null) {
					intro = "";
				}
				person.setIntroduction(intro + "\n\n" + AR);
			} else {
				academic.setFurtherEducation(AR);
			}
			
			//学术兼职（长度超过2000则追加在个人简介后）
			if (AS.getBytes("UTF-8").length > 1998) {
				String intro = person.getIntroduction();
				if (intro == null) {
					intro = "";
				}
				person.setIntroduction(intro + "\n\n" + AS);
			} else {
				academic.setParttimeJob(AS);
			}

			saveOrUpdate(person);
			saveOrUpdate(academic);
			if (officer != null) {
				saveOrUpdate(officer);
			} else if (teacher != null) {
				saveOrUpdate(teacher);
			}
			if (academic.getDiscipline().length() > 1000 || academic.getLanguage().length() > 1000 || academic.getRelativeDiscipline().length() > 1000) {
				System.out.println(person.getName());
				System.out.println(academic.getDiscipline());
				System.out.println(academic.getRelativeDiscipline() + "\n");
				break;
			}
		}
		if (invalidUnivCode.length() > 0) {
			throw new Exception(invalidUnivCode.toString());
		}	
//		if (invalidUnitId.length() > 0) {
//			throw new Exception(invalidUnitId.toString());
//		}
		System.out.println("Person maxLength:");
		outputMaxLength();
	}
	
	/**
	 * 初始化研究活动类型映射
	 */
	private void initResearchType() {
		researchTypeMap = new HashMap<String, SystemOption>();
		researchTypeMap.put("A", (SystemOption) baseService.query(SystemOption.class, "baseResearchRAT"));
		researchTypeMap.put("B", (SystemOption) baseService.query(SystemOption.class, "theoryRAT"));
		researchTypeMap.put("C", (SystemOption) baseService.query(SystemOption.class, "applicationRAT"));
	}

	
	/**
	 * 初始化学校代码变更映射
	 */
	private void initUnivCodeChange() {
		univCodeChange = new HashMap<String, String>();
		univCodeChange.put("10575", "11078");	//广州大学
		univCodeChange.put("10266", "10248");	//上海交通大学
		univCodeChange.put("10079", "10054");
		univCodeChange.put("10916", "11071");
		univCodeChange.put("11515", "11070");
		univCodeChange.put("11292", "10351");
		univCodeChange.put("10090", "10081");	//河北联合大学
		univCodeChange.put("10656", "10589");	//海南大学
		univCodeChange.put("10575", "11078");
		univCodeChange.put("10625", "10635");	//西南大学
		univCodeChange.put("10682", "11393");
		univCodeChange.put("10745", "10743");	//青海大学
		univCodeChange.put("10772", "11232");	//北京信息科技大学
		univCodeChange.put("10916", "11071");	//新乡学院
		univCodeChange.put("11262", "11261");	//吉林工商学院
		univCodeChange.put("11292", "10351");	//温州大学
		univCodeChange.put("11515", "11070");	//洛阳理工学院
		univCodeChange.put("11794", "11336");	//荆楚理工学院
		univCodeChange.put("10325", "10304");	//南通大学
		univCodeChange.put("10658", "10657");	//贵州大学
		univCodeChange.put("10734", "10730");	//兰州大学
		univCodeChange.put("11351", "11548");	//广西财经学院
		univCodeChange.put("11829", "11551");	//重庆科技学院
		univCodeChange.put("46123", "13819");	//陕西警官职业学院
		univCodeChange.put("10311", "10304");	//南通大学
		univCodeChange.put("10374", "10370");	//安徽师范大学
		univCodeChange.put("10565", "10589");	//海南大学
	}
	
	/**
	 * 初始化基地类型
	 */
	private void initRAT() {
		bujijidi = (SystemOption) baseService.query(SystemOption.class, "bjyjjg");
		xiaojijidi = (SystemOption) baseService.query(SystemOption.class, "xjyjjg");
		qitajidi = (SystemOption) baseService.query(SystemOption.class, "qtyjjg");
	}

	/**
	 * 初始化民族映射
	 */
	private void initNation() {
		nationMap = new HashMap<Integer, String>();
		nationMap.put(Integer.parseInt("01"), "汉族");
		nationMap.put(Integer.parseInt("11"), "满族");
		nationMap.put(Integer.parseInt("10"), "朝鲜族");
		nationMap.put(Integer.parseInt("53"), "赫哲族");
		nationMap.put(Integer.parseInt("02"), "蒙古族");
		nationMap.put(Integer.parseInt("31"), "达斡尔族");
		nationMap.put(Integer.parseInt("45"), "鄂温克族");
		nationMap.put(Integer.parseInt("52"), "鄂伦春族");
		nationMap.put(Integer.parseInt("03"), "回族");
		nationMap.put(Integer.parseInt("26"), "东乡族");
		nationMap.put(Integer.parseInt("30"), "土族");
		nationMap.put(Integer.parseInt("35"), "撒拉族");
		nationMap.put(Integer.parseInt("47"), "保安族");
		nationMap.put(Integer.parseInt("48"), "裕固族");
		nationMap.put(Integer.parseInt("05"), "维吾尔族");
		nationMap.put(Integer.parseInt("17"), "哈萨克族");
		nationMap.put(Integer.parseInt("29"), "柯尔克孜族");
		nationMap.put(Integer.parseInt("38"), "锡伯族");
		nationMap.put(Integer.parseInt("41"), "塔吉克族");
		nationMap.put(Integer.parseInt("43"), "乌孜别克族");
		nationMap.put(Integer.parseInt("44"), "俄罗斯族");
		nationMap.put(Integer.parseInt("50"), "塔塔尔族");
		nationMap.put(Integer.parseInt("04"), "藏族");
		nationMap.put(Integer.parseInt("54"), "门巴族");
		nationMap.put(Integer.parseInt("55"), "珞巴族");
		nationMap.put(Integer.parseInt("33"), "羌族");
		nationMap.put(Integer.parseInt("07"), "彝族");
		nationMap.put(Integer.parseInt("14"), "白族");
		nationMap.put(Integer.parseInt("16"), "哈尼族");
		nationMap.put(Integer.parseInt("18"), "傣族");
		nationMap.put(Integer.parseInt("20"), "傈僳族");
		nationMap.put(Integer.parseInt("21"), "佤族");
		nationMap.put(Integer.parseInt("24"), "拉祜族");
		nationMap.put(Integer.parseInt("27"), "纳西族");
		nationMap.put(Integer.parseInt("28"), "景颇族");
		nationMap.put(Integer.parseInt("34"), "布朗族");
		nationMap.put(Integer.parseInt("39"), "阿昌族");
		nationMap.put(Integer.parseInt("40"), "普米族");
		nationMap.put(Integer.parseInt("42"), "怒族");
		nationMap.put(Integer.parseInt("46"), "德昂族");
		nationMap.put(Integer.parseInt("51"), "独龙族");
		nationMap.put(Integer.parseInt("56"), "基诺族");
		nationMap.put(Integer.parseInt("06"), "苗族");
		nationMap.put(Integer.parseInt("09"), "布依族");
		nationMap.put(Integer.parseInt("12"), "侗族");
		nationMap.put(Integer.parseInt("25"), "水族");
		nationMap.put(Integer.parseInt("37"), "仡佬族");
		nationMap.put(Integer.parseInt("08"), "壮族");
		nationMap.put(Integer.parseInt("13"), "瑶族");
		nationMap.put(Integer.parseInt("32"), "仫佬族");
		nationMap.put(Integer.parseInt("36"), "毛难族");
		nationMap.put(Integer.parseInt("49"), "京族");
		nationMap.put(Integer.parseInt("15"), "土家族");
		nationMap.put(Integer.parseInt("19"), "黎族");
		nationMap.put(Integer.parseInt("22"), "畲族");
		nationMap.put(Integer.parseInt("23"), "高山族");
		nationMap.put(Integer.parseInt("00"), "其他");
	}
	
	/**
	 * 初始化职称映射
	 */
	private void initTitle() {
		titleMap = new HashMap<String, String>();
		titleMap.put("1A", "教授");
		titleMap.put("1B", "高级工程师");
		titleMap.put("1C", "高级农艺师");
		titleMap.put("1D", "研究员");
		titleMap.put("1E", "高级实验师");
		titleMap.put("1F", "高级经济师");
		titleMap.put("1G", "高级律师");
		titleMap.put("1H", "高级会计师");
		titleMap.put("1I", "高级统计师");
		titleMap.put("1J", "编审");
		titleMap.put("1K", "特级记者");
		titleMap.put("1L", "译审");
		titleMap.put("1M", "研究馆员");
		titleMap.put("2A", "副教授");
		titleMap.put("2D", "副研究员");
		titleMap.put("2J", "副编审");
		titleMap.put("2K", "高级记者");
		titleMap.put("2L", "副译审");
		titleMap.put("2M", "副研究馆员");
		titleMap.put("3A", "讲师");
		titleMap.put("3B", "工程师");
		titleMap.put("3C", "农艺师");
		titleMap.put("3D", "助理研究员");
		titleMap.put("3E", "实验师");
		titleMap.put("3F", "经济师");
		titleMap.put("3G", "律师");
		titleMap.put("3H", "会计师");
		titleMap.put("3I", "统计师");
		titleMap.put("3J", "编辑");
		titleMap.put("3K", "二三等记者");
		titleMap.put("3L", "二三等翻译");
		titleMap.put("3M", "馆员");
		titleMap.put("4", "助教");
		titleMap.put("5", "初级Ⅰ");
		titleMap.put("6", "初级Ⅱ");
		titleMap.put("7", "辅助人员");
		titleMap.put("81", "博士研究生");
		titleMap.put("82", "硕士研究生");
		titleMap.put("83", "研究生班");
	}
	
	/**
	 * 初始化政治面貌映射
	 */
	private void initPolicy() {
		policyMap = new HashMap<Integer, String>();
		policyMap.put(Integer.parseInt("01"), "中共党员");
		policyMap.put(Integer.parseInt("04"), "民革党员");
		policyMap.put(Integer.parseInt("05"), "民盟盟员");
		policyMap.put(Integer.parseInt("06"), "民建会员");
		policyMap.put(Integer.parseInt("07"), "民进会员");
		policyMap.put(Integer.parseInt("08"), "农工党员");
		policyMap.put(Integer.parseInt("09"), "致公党员");
		policyMap.put(Integer.parseInt("10"), "九三社员");
		policyMap.put(Integer.parseInt("11"), "台盟盟员");
		policyMap.put(Integer.parseInt("12"), "无党派民主人士");
		policyMap.put(Integer.parseInt("02"), "中共预备党员");
		policyMap.put(Integer.parseInt("03"), "共青团员");
		policyMap.put(Integer.parseInt("13"), "群众");
		policyMap.put(Integer.parseInt("99"), "其他");
	}
	
	/**
	 * 初始化学历映射
	 */
	private void initEduLevel() {
		eduLevelMap = new HashMap<String, String>();
		eduLevelMap.put("1", "研究生");
		eduLevelMap.put("2", "本科生");
		eduLevelMap.put("3", "大专生");
		eduLevelMap.put("4", "中专生");
		eduLevelMap.put("5", "其他");
	}
	
	/**
	 * 初始化学位映射
	 */
	private void initEduDegree() {
		eduDegreeMap = new HashMap<String, String>();
		eduDegreeMap.put("1", "名誉博士");
		eduDegreeMap.put("2", "博士");
		eduDegreeMap.put("3", "硕士");
		eduDegreeMap.put("4", "学士");
		eduDegreeMap.put("201", "哲学博士");
		eduDegreeMap.put("301", "哲学硕士");
		eduDegreeMap.put("401", "哲学学士");
		eduDegreeMap.put("202", "经济学博士");
		eduDegreeMap.put("302", "经济学硕士");
		eduDegreeMap.put("402", "经济学学士");
		eduDegreeMap.put("203", "法学博士");
		eduDegreeMap.put("303", "法学硕士");
		eduDegreeMap.put("403", "法学学士");
		eduDegreeMap.put("204", "教育学博士");
		eduDegreeMap.put("304", "教育学硕士");
		eduDegreeMap.put("404", "教育学学士");
		eduDegreeMap.put("205", "文学博士");
		eduDegreeMap.put("305", "文学硕士");
		eduDegreeMap.put("405", "文学学士");
		eduDegreeMap.put("206", "历史学博士");
		eduDegreeMap.put("306", "历史学硕士");
		eduDegreeMap.put("406", "历史学学士");
		eduDegreeMap.put("207", "理学博士");
		eduDegreeMap.put("307", "理学硕士");
		eduDegreeMap.put("407", "理学学士");
		eduDegreeMap.put("208", "工学博士");
		eduDegreeMap.put("308", "工学硕士");
		eduDegreeMap.put("408", "工学学士");
		eduDegreeMap.put("209", "农学博士");
		eduDegreeMap.put("309", "农学硕士");
		eduDegreeMap.put("409", "农学学士");
		eduDegreeMap.put("210", "医学博士");
		eduDegreeMap.put("310", "医学硕士");
		eduDegreeMap.put("410", "医学学士");
		eduDegreeMap.put("211", "军事学博士");
		eduDegreeMap.put("311", "军事学硕士");
		eduDegreeMap.put("411", "军事学学士");
		eduDegreeMap.put("999", "其他");
	}
	
	/**
	 * 初始语言映射
	 */
	private void initLanguage() {
		languageMap = new HashMap<String, String>();
		languageMap.put("en", "英语");
		languageMap.put("ru", "俄语");
		languageMap.put("de", "德语");
		languageMap.put("fr", "法语");
		languageMap.put("es", "西班牙语");
		languageMap.put("ar", "阿拉伯语");
		languageMap.put("ja", "日语");
		languageMap.put("ko", "朝鲜语");
		languageMap.put("iw", "希伯莱语");
		languageMap.put("pl", "波兰语");
		languageMap.put("pt", "葡萄牙语");
		languageMap.put("hu", "匈牙利语");
		languageMap.put("it", "意大利语");
		languageMap.put("la", "拉丁语");
		languageMap.put("99", "其他");
	}
	
	/**
	 * 初始语言程度映射
	 */
	private void initLanguageLevel() {
		languageLevelMap = new HashMap<String, String>();
		languageLevelMap.put("1", "精通");
		languageLevelMap.put("2", "熟练");
		languageLevelMap.put("3", "良好");
		languageLevelMap.put("4", "一般");
	}
	
	/**
	 * 将语言代码和语言掌握程度代码转换成 [语言/语言掌握程度]形式
	 * @param languageCode
	 * @param levelCode
	 * @return
	 */
	private String transLanguage(String languageCode, String levelCode) {
		if (languageMap.containsKey(languageCode)) {
			String language = languageMap.get(languageCode);
			String level = languageLevelMap.get(levelCode);
			return language + "/" + (level == null ? "一般" : level);
		}
		return "";
	}


}
