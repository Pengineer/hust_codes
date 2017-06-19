package csdc.tool.execution.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.SystemOption;
import csdc.tool.beanutil.mergeStrategy.MergePhoneNumber;
import csdc.tool.beanutil.mergeStrategy.MergeStrategy;
import csdc.tool.beanutil.mergeStrategy.Replace;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.merger.InstituteMerger;

/**
 * 部级基地、省部共建基地 基地一览表导入
 * 《20111108_基地一览表（总表）_修正导入.xls》
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class MinistryInstituteImporter extends Importer {
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private InstituteMerger instituteMerger;
	
	public MinistryInstituteImporter() {}
	
	public MinistryInstituteImporter(File file) {
		super(file);
	}
	
	@Override
	public void work() throws Throwable {
		testUniversityNames();
		resetInstituteType();
		updateMergeStrategy();
		importData();
	}

	/**
	 * 更新若干字段合并策略
	 */
	private void updateMergeStrategy() {
		HashMap msMap = new HashMap<String, MergeStrategy>();
		
		MergeStrategy replace = new Replace();
		msMap.put("approveSession", replace);
		msMap.put("approveDate", replace);
		msMap.put("approveSession", replace);
		msMap.put("code", replace);
		msMap.put("introduction", replace);
		
		instituteMerger.updateMergeStrategy(msMap);
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		SystemOption bjyjjg = (SystemOption) dao.query(SystemOption.class, "bjyjjg");		//部级研究基地
		SystemOption sbgjyjjg = (SystemOption) dao.query(SystemOption.class, "sbgjyjjg");	//省部共建研究基地
		
		MergePhoneNumber phoneNumberMerger = new MergePhoneNumber();
		
		getContentFromExcel(0);
		while (next()) {
			Institute institute = new Institute();

			Agency university = universityFinder.getUnivByName(A);
			institute.setSubjection(university);
			institute.setName(B);
			institute.setType(D.contains("部级") ? bjyjjg : sbgjyjjg);
			institute.setCode(E);
			institute.setDisciplineType(F);
			institute.setResearchArea(G);
			institute.setApproveDate(tool.getDate(I));
			institute.setApproveSession(J);
			
			institute.setEmail(O);
			institute.setPhone(P);
			institute.setFax(Q);
			institute.setAddress(R);
			institute.setPostcode(S);
			if (T.length() > 0 && !T.startsWith("http")) {
				T = "http://" + T;
			}
			institute.setHomepage(T.replaceAll("\\W+$", ""));
			institute.setIntroduction(U);
			
			Institute tagetInstitute = (Institute) dao.queryUnique("select inst from Institute inst where inst.name = ? and inst.subjection.id = ?", institute.getName(), university.getId());

			//将当前基地合并至库内已有基地
			if (tagetInstitute != null) {
				System.out.println("找到同校同名基地：" + tagetInstitute.getSubjection().getName() + " - " + tagetInstitute.getName());
				List<Institute> incomeInstList = new ArrayList<Institute>();
				incomeInstList.add(institute);
				instituteMerger.mergeInstitute(tagetInstitute, incomeInstList);
			} else {
				dao.add(institute);
				tagetInstitute = institute;
			}
			
			//负责人
			String[] diesctorNames = K.split("\\s+");
			String[] diesctorMobiles = L.split("\\D+");
			for (int i = 0; i < diesctorNames.length; i++) if (diesctorNames[i].length() > 0) {
				String name = diesctorNames[i];
				Officer director = univPersonFinder.findOfficer(name, tagetInstitute);
				
				if (i < diesctorMobiles.length) {
					String mobilePhone = (String) phoneNumberMerger.merge(director.getPerson().getMobilePhone(), diesctorMobiles[i]);
					director.getPerson().setMobilePhone(mobilePhone);
				}
				
				if (i == 0) {
					tagetInstitute.setDirector(director.getPerson());
				}
				director.setInstitute(tagetInstitute);
			}
			
			//联系人
			String[] linkmanNames = M.split("\\s+");
			String[] linkmanMobiles = N.split("\\D+");
			for (int i = 0; i < linkmanNames.length; i++) if (linkmanNames[i].length() > 0)  {
				String name = linkmanNames[i];
				Officer linkMan = univPersonFinder.findOfficer(name, tagetInstitute);
				
				if (i < linkmanMobiles.length) {
					String mobilePhone = (String) phoneNumberMerger.merge(linkMan.getPerson().getMobilePhone(), linkmanMobiles[i]);
					linkMan.getPerson().setMobilePhone(mobilePhone);
				}
				
				if (i == 0) {
					tagetInstitute.setLinkman(linkMan.getPerson());
				}
				linkMan.setInstitute(tagetInstitute);
			}
		}
	}


	/**
	 * 检测学校名合法性
	 * @throws Exception
	 */
	private void testUniversityNames() throws Exception {
		/**
		 * 不存在的学校名称
		 */
		HashSet<String> invalidUnivNames = new HashSet<String>();
		
		getContentFromExcel(0);
		Agency university = null;
		while (next()) {
			if (A.length() > 0) {
				university = universityFinder.getUnivByName(A);
				if (university == null) {
					invalidUnivNames.add(A);
				}
			}
		}
		
		if (invalidUnivNames.size() > 0) {
			for (Iterator<String> iterator = invalidUnivNames.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println("不存在的学校: " + string);
			}
			throw new Exception();
		}
		System.out.println("学校名称正常");
		
	}
	
	/**
	 * 将现有的部级基地和省部共建基地都设成其他基地
	 */
	private void resetInstituteType() {
		SystemOption qtyjjg = (SystemOption) dao.query(SystemOption.class, "qtyjjg");
		List<Institute> insts = dao.query("select inst from Institute inst where inst.type.id = 'bjyjjg' or inst.type.id = 'sbgjyjjg'");
		for (Institute institute : insts) {
			institute.setType(qtyjjg);
		}
	}
	
}
