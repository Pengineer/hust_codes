package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.University;
import csdc.dao.JdbcDao;
import csdc.tool.StringTool;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * SMDB库的高校数据全部导入SMAS
 * 1.smas数据更新
 * 2.smas数据修复
 * 3.smas高校信息删除
 * @author fengcl
 */
public class SMDBUniversityImporter extends Importer {

	private JdbcTemplateReader reader;
	@Autowired
	protected BeanFieldUtils beanFieldUtils;
	
	protected void work() throws Throwable {
		importData();	
	}

	//只需查出[举办者代码、高校名称、高校代码、高校类型、拼音缩写、所在省直辖市名称、所在省直辖市代码、高校办学类型[办学类型代码/办学类型，如：22/独立学院]、所属标准、备注]
	private void resetReader() throws Exception {
		reader.query(
			"SELECT REGEXP_REPLACE(a.C_ORGANIZER,'/.*','')," +	//A 举办者代码
			"a.c_name," +	//B 高校名称
			"a.c_code," +	//C 高校代码
			"a.c_type," +	//D 高校类型[1：(360高校),2：(非360高校)]
			"a.C_ACRONYM," +	//E 拼音缩写
			"pr.c_name," +	//F 所在省直辖市名称
			"pr.c_code," +	//G 所在省直辖市代码
			"a.c_style" +	//H 高校办学类型[办学类型代码/办学类型，如：22/独立学院]
			" from t_agency a join t_system_option pr on a.c_province_id = pr.c_id where (a.c_type = 3 or a.c_type = 4) and a.c_code is not null order by a.c_code ");
	}
	
	//smdb高校信息map<universitycode,universityname>
	private Map<String, String> smdbUnivMap = new HashMap<String, String>();
	//smas未匹配上高校信息map<smasuniversitycodeold,smasuniversitycodenew>
	private Map<String, String> univCodeVariationMap;
	//smas未匹配上高校信息map<universitycode,university>
	private Map<String, University> specialUnivMap = null;
	public void importData() throws Exception {
		resetReader();
		int matchCnt = 0;
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());

			if (StringTool.fix(C).equals("99999")) {//不修复该数据
				continue;
			}
			University otherUniversity = getUniversity(StringTool.fix(C), StringTool.fix(B), true);
			
			matchCnt++;
			otherUniversity.setFounderCode(A);
			otherUniversity.setName(B);
			if (D.contains("3")) {
				otherUniversity.setType(1);
			}else if (D.contains("4")) {
				otherUniversity.setType(2);
			}
			otherUniversity.setAbbr(E);
			otherUniversity.setProvinceName(F);
			otherUniversity.setProvinceCode(G);
			otherUniversity.setStyle(H);
			otherUniversity.setImportedDate(new Date());
			smdbUnivMap.put(StringTool.fix(otherUniversity.getCode()), StringTool.fix(otherUniversity.getName()));

		}
		System.out.println("处理总计：" + matchCnt);
		findSmasSpecialInfo();//找出smas有的而smdb没有的高校code数据
		
		if (specialUnivMap != null && specialUnivMap.size() > 0) {
			//取得smdb数据库中的高校变更表
			if (smdbUnivRenames.size() == 0) {
				getSmdbUnivRename();
			}
			
			//生成smas特殊数据对应的高校code的map
			createVariationMapping();
			
			//修复smas一般项目，基地项目，专家信息
			fixGeneralAndInstpAndExpert();

			//删除smas中不需要的高校信息
			deleteUniversity();
		}
	}
	
	/**
	 * 找出smas有的而smdb没有的高校code数据
	 */
	private void findSmasSpecialInfo(){
		for (Entry<String, University> entry : universityMap.entrySet()) {
			String key = entry.getKey();
			University university = entry.getValue();
			if(!smdbUnivMap.containsKey(key)){
		    	//新建一个map，存放未匹配数据
		    	if (specialUnivMap == null) {
		    		specialUnivMap = new HashMap<String, University>();
				}
		    	specialUnivMap.put(key, university);
		    }
		}
		specialUnivMap.remove("shsky");//不用修复以下三条数据
		specialUnivMap.remove("bjsky");
		specialUnivMap.remove("skzx");
	}
	/**
	 * 通过smdb高校变更表，制作smas高校对应的变更信息
	 */
	private void createVariationMapping(){
		Set<String> keys = specialUnivMap.keySet();
		//遍历key集合，获取value
		for(String key : keys) {
			University university = specialUnivMap.get(key);
			String univCode = university.getCode();
			String univName = university.getName();
			do {
				univCode = fixMutableVariation(StringTool.fix(univCode),StringTool.fix(univName));
			} while (univCode != null && !smdbUnivMap.containsKey(univCode));		
			if (univCode != null) {
				if (univCodeVariationMap == null) {
					univCodeVariationMap = new HashMap<String, String>();
				}
				univCodeVariationMap.put(university.getCode(),univCode);	
			}
		}
		System.out.println("ok");
	}
	
	/**
	 * 根据smas中的高校code查找smdb高校变更表，直到匹配上正确的高校变更code
	 * @param univCode
	 * @param univName
	 * @return
	 */
	private String fixMutableVariation(String univCode, String univName){
		String smasToSmdbUnivCode = null;
		String[] univVariation = new String[3];
		for (int i = 0; i < smdbUnivRenames.size(); i++) {
			univVariation = smdbUnivRenames.get(i);
			if (univVariation[1] != null) {
				if (univVariation[1].equals(univCode)) {
					smasToSmdbUnivCode = univVariation[2].toString();
					break;
				}else if (univVariation[0].equals(univName)) {
					smasToSmdbUnivCode = univVariation[2];
					break;
				}
			}else{
				if (univVariation[0].equals(univName)) {
					smasToSmdbUnivCode = univVariation[2];
					break;
				}
			}
		}
		return smasToSmdbUnivCode;
	}
	
	/**
	 * 修复smas一般项目，基地项目，专家信息
	 */
	private void fixGeneralAndInstpAndExpert(){
		
		//初始化匹配一般项目
		initGeneralMap();
		//初始化匹配基地项目
		initInstpMap();
		//初始化匹配专家
		initExpertsMap();
		
		if (univCodeVariationMap != null) {
			for (Entry<String, String> entry : univCodeVariationMap.entrySet()) {
				String oldCode = entry.getKey();
				String newCode = entry.getValue();

				//1、修复一般项目信息			
				List<ProjectApplication> generals = generalMap.get(oldCode);
				if (generals != null) {
					for (ProjectApplication gp : generals) {
						gp.setUniversityCode(newCode);
					}
				}

				//2、修复基地项目信息
				List<ProjectApplication> instps = instpMap.get(oldCode);
				if (instps != null) {
					for (ProjectApplication ip : instps) {
						ip.setUniversityCode(newCode);
					}
				}

				//3、修复专家信息
				List<Expert> experts = expertMap.get(oldCode);
				if(experts != null){
					for (Expert ep : experts) {
						ep.setUniversityCode(newCode);
					}
				}				
			}
		}
	}
	
	/**
	 * 删除smas中不需要的高校信息
	 */
	private void deleteUniversity(){
		for (Entry<String, University> entry : specialUnivMap.entrySet()) {
			dao.delete(entry.getValue());
		}	
	}
	
	/**
	 *初始化匹配一般项目
	 */
	private Map<String, List<ProjectApplication>> generalMap = null;
	/**
	 * 初始化
	 */
	private void initGeneralMap() {
		long begin = System.currentTimeMillis();
		generalMap = new HashMap<String, List<ProjectApplication>>();
		
		Map parMap = new HashMap();
		if (univCodeVariationMap != null) {
			Set<String> univCodes = univCodeVariationMap.keySet();
			parMap.put("univCodes", univCodes);
			List<ProjectApplication> projects = dao.query("from ProjectApplication p where p.type = 'general' and p.universityCode in (:univCodes)", parMap);
			
			for (ProjectApplication project : projects) {
				List<ProjectApplication> gps = generalMap.get(project.getUniversityCode());
				if (gps == null) {
					gps = new ArrayList<ProjectApplication>();
					generalMap.put(project.getUniversityCode(), gps);
				}
				gps.add(project);
			}
		}
		System.out.println("init General complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 初始化匹配基地项目
	 */
	private Map<String, List<ProjectApplication>> instpMap = null;
	/**
	 * 初始化
	 */
	private void initInstpMap() {
		long begin = System.currentTimeMillis();
		instpMap = new HashMap<String, List<ProjectApplication>>();
		
		Map parMap = new HashMap();
		if (univCodeVariationMap != null) {
			Set<String> univCodes = univCodeVariationMap.keySet();
			parMap.put("univCodes", univCodes);
			List<ProjectApplication> projects = dao.query("from ProjectApplication p where p.type = 'instp' and p.universityCode in (:univCodes)", parMap);
			
			for (ProjectApplication project : projects) {
				List<ProjectApplication> ips = instpMap.get(project.getUniversityCode());
				if (ips == null) {
					ips = new ArrayList<ProjectApplication>();
					instpMap.put(project.getUniversityCode(), ips);
				}
				ips.add(project);
			}
		}

		System.out.println("init Instp complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}	
	
	/**
	 * 初始化匹配专家
	 */
	private Map<String, List<Expert>> expertMap = null;
	/**
	 * 初始化
	 */
	private void initExpertsMap() {
		long begin = System.currentTimeMillis();
		expertMap = new HashMap<String, List<Expert>>();
		
		Map parMap = new HashMap();
		if (univCodeVariationMap != null) {
			Set<String> univCodes = univCodeVariationMap.keySet();
			parMap.put("univCodes", univCodes);
			List<Expert> experts = dao.query("from Expert e where e.universityCode in (:univCodes)", parMap);
			
			for (Expert expert : experts) {
				List<Expert> eps = expertMap.get(expert.getUniversityCode());
				if (eps == null) {
					eps = new ArrayList<Expert>();
					expertMap.put(expert.getUniversityCode(), eps);
				}
				eps.add(expert);
			}
		}

		System.out.println("init University complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * smdb高校变更表数据
	 */
	private List<String[]> smdbUnivRenames = new ArrayList<String[]>();	
	
	/**
	 * 查出smdb高校变更表中的数据
	 * @throws Exception
	 */
	private void getSmdbUnivRename() throws Exception {
		reader.query(
			"SELECT u.c_name_old,"+	//A 高校变更前的名字
			"u.c_code_old," +	//B 高校变更前的代码
			"u.c_code_new" +	//C 高校变更后的代码
			" from t_university_variation u ");
		while (next(reader)) {
			String[] smdbUnivRename = new String[3];
			smdbUnivRename[0] = A;
			smdbUnivRename[1] = B;
			smdbUnivRename[2] = C;
			smdbUnivRenames.add(smdbUnivRename);
		}
	}

	/**
	 * 高校代码+高校名称  到 高校实体 的映射
	 */
	private Map<String, University> universityMap = null;
	/**
	 * 初始化universityMap
	 */
	private void initUniversityMap() {
		long begin = System.currentTimeMillis();
		
		universityMap = new HashMap<String, University>();
		List<University> universities = dao.query("select u from University u");
		for (University university : universities) {
			universityMap.put(StringTool.fix(university.getCode()), university);
		}
		
		System.out.println("init University complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 根据[高校代码]和[高校姓名]找到University实体
	 * @param code
	 * @param name
	 * @param addIfNotFound 如果不存在此高校，是否向库内添加一个
	 * @return
	 */
	public University getUniversity(String code, String name, boolean addIfNotFound) {
		if (universityMap == null) {
			initUniversityMap();
		}
		University university = universityMap.get(code);
		if (null == university && addIfNotFound) {
				university = new University();
				university.setCode(code);
				university.setName(name);
				university.setUseExpert(1);
				university.setUseViceExpert(0);
				university.setRating("1");
				dao.add(university);
				universityMap.put(code, university);
		}
		return university;
	}
	
	public SMDBUniversityImporter(){
	}
	
	public SMDBUniversityImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
