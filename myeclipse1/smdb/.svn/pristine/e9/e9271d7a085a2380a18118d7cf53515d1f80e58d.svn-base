package csdc.service.webService.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.e;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.InstpMember;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.tool.XMLTool;

public class SmasWebService extends BaseWebService implements ISmasWebService {
	
	public String addKeyPerson() throws IOException {
		StringBuffer hql = new StringBuffer();
		hql.append("select p.name, p.homePhone, p.officePhone, p.officeFax, p.email, p.mobilePhone, p.idcardType, p.idcardNumber, p.birthday, u.name " +
				"from Person p left join p.teacher t left join t.university u " +
				"where p.isKey = '1'");
		List<Object[]> list = dao.query(hql.toString());
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[10];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] instanceof Date) {
					str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}
			Element result = content.addElement("item");
			Element name = result.addElement("name");//姓名
			name.setText(str[0]);
			Element homePhone =  result.addElement("homePhone");//住宅电话
			homePhone.setText(str[1]);
			Element officePhone =  result.addElement("officePhone");//办公电话
			officePhone.setText(str[2]);
			Element officeFax =  result.addElement("officeFax");//办公传真
			officeFax.setText(str[3]);			
			Element email =  result.addElement("email");//邮箱
			email.setText(str[4]);
			Element mobilePhone =  result.addElement("mobilePhone");//电话
			mobilePhone.setText(str[5]);
			Element idcardType =  result.addElement("idcardType");//证件类型
			idcardType.setText(str[6]);
			Element idcardNumber =  result.addElement("idcardNumber");//证件号
			idcardNumber.setText(str[7]);
			Element birthday =  result.addElement("birthday");//生日
			birthday.setText(str[8]);
			Element agencyName =  result.addElement("agencyName");//机构名称
			agencyName.setText(str[9]);
		}
		XMLTool.generateXmlFile(document);//根据document对象生成本地文件
		return responseContent("data", document);
	}

	/**
	 * smas --> smdb <br>
	 * 说明：同步一般项目需要注意，项目子类是“专项任务”的排除在外，不需要同步 <br>
	 * 参考：
	 * t_project_application pa left join t_system_option so on pa.c_subtype_id = so.c_id
	 * @param year 项目年份
	 * @param projectType 项目类别
	 * @param fetchSize 每次获取数量（同一次录入大小不不变）
	 * @param counts 同一次录入中第几次获取的批次
	 * @return 
	 */
	public String requestProjectApplication(String year, String projectType, int fetchSize, int counts) {
		//同步字段参考社科网的申请入库字段，字段内容形式参考smas的入库代码
		String data = null;
		if (projectType.equals("general")) {
			data =  getGeneralProjectApplication(year, fetchSize, counts);
		} else if (projectType.equals("instp")) {
			data = getInstpProjectApplication(year, fetchSize, counts);
		}
		return data;
	}
	
	/**
	 * smas->smdb
	 * 根据项目类型，项目名称，申请人姓名，申请年度获取立项信息
	 */
	public String requestUniqueProjectApplication(String projectType, String projectName, String applicantName, int year) {
		//同步字段参考社科网的申请入库字段，字段内容形式参考smas的入库代码
		String data = null;
		if (projectType.equals("general")) {
			data =  getUniqueGeneralProjectApplication(projectName, applicantName, year);
		} else if (projectType.equals("instp")) {
			data = getUniqueInstpProjectApplication(projectName, applicantName, year);
		}
		return data;
	}
	/**
	 * smas --> smdb 获取 year 年的一般项目的申请数据<br>
	 * 说明：<br>
	 * 1.按照尽可能满足项目流程中的非规范项目申请数据情况<br>
	 * 同步规则：<br>
	 * (1) 省级审核状态已提交 且 省级审核通过；<br>
	 * (2) 省级审未提交且 省级未提交 且 校审核状态已提交 且 校级审核通过；<br>
	 * 备注：
	 * (1) reason字段信息时不符合申请条件信息，该类项目必然不通过，此字段不同步；
	 * (2) smdb 查重后，再进行后续数据同步工作。
	 * @param year 获取申请的年度条件
	 * @param fetchSize 获取数据大小
	 * @param counts 获取批次
	 * @return
	 */
	private String getInstpProjectApplication(String year, int fetchSize, int counts) {
		long begin = System.currentTimeMillis();
		Map  argsMap = new HashMap();
		argsMap.put("year", Integer.parseInt(year));
		StringBuffer hql = new StringBuffer();
		
/*		String hqlselect = " select ia.id, ia.type, ia.discipline, ia.disciplineType, ia.name, ia.year, projectType.name, reType.name, ia.isReviewable, ia.applicantSubmitDate, " +//0-9
				" ia.planEndDate, ia.file, ia.productType, ia.productTypeOther, ia.divisionName, ia.universityAuditResult, ia.provinceAuditResult, u.name, u.code, u.type, institute.name, " +//10 - 20
				" ia.note, ia.finalAuditResult , ia.finalAuditorName, ia.finalAuditDate , ia.finalAuditOpinion , ia.finalAuditOpinionFeedback, ia.reviewDate , ia.reviewTotalScore , ia.reviewAverageScore , ia.reviewWay , " +
				" ia.reviewResult , ia.reviewOpinion , soreviewGread.name , ia.reviewOpinionQualitative, projectFee ";*/
		/**
		 * 说明：
		 * （1）基地disciplineDirection，在负责人表中，以及 birthday，title，job，members 
		 * （2）finalResultType 有两个字段拼接ia.productType, ia.productTypeOther，同时这两个字段也需要同步
		 * （3）符合条件的允许同步过去的项目，默认都为参评状态，即smas端isReviewable设置为1
		 * （4）【关键】对于同步的项目一般、基地，在smas端需要分别修改对应的申请人字段申请年份字段信息
		 * （5）【注意】按照波波的入库代码，基地项目同步至smas后，projectType字段全部设为"基地项目"
		 * （6）ins.setDisciplineDirection(),ins.setBirthday(),ins.setTitle(),ins.setJob()
		 * ins.setJob()
		 * （7）基地项目同步过去默认都是参评状态IsReviewable=1
		 * 
		 * 无ia.reason字段
		 * 
		 *无 ia.sinossId字段改为sinossApp.id
		 */
		String hqlselect = " select ia.id, ia.name, ia.applicantName, institute.name, u.code, u.name, projectType.name, ia.year, ia.applicantSubmitDate, ia.planEndDate, " +
				" ia.discipline, ia.disciplineType, ia.productType, ia.productTypeOther, ia.file, ia.note, u.type, projectFee, ia.firstAuditResult , " +
				" ia.firstAuditDate, ia.applicantId, ia.type,sinossApp.id,ia.researchType.name ,ia.applyFee, ia.otherFee"; 

		
		
		// from  SinossProjectApplication sinossApp left join sinossApp.projectApplication ia 
		
		
		/*String hqlfromwhere = " from  InstpApplication ia left join ia.subtype projectType left join ia.institute institute left join ia.researchType reType left join ia.university u left join ia.projectFee projectFee, SinossProjectApplication sinossApp left join sinossApp.projectApplication smdbApp " +
				" where ia.type = 'instp' and (" +
				" (ia.provinceAuditStatus = 3 and ia.provinceAuditResult = 2) or " +
				" (ia.provinceAuditStatus = 0 and ia.provinceAuditResult = 0 and ia.universityAuditStatus = 3 and ia.universityAuditResult = 2) " +
				" ) and ia.year = :year and smdbApp.id = ia.id  order by ia.id ";*/
		
		String hqlfromwhere  = " from  SinossProjectApplication sinossApp left join sinossApp.projectApplication ia left join ia.subtype projectType left join ia.institute institute left join ia.researchType reType left join ia.university u left join ia.projectFee projectFee" +
				" where ia.type = 'instp' and (" +
				" (ia.provinceAuditStatus = 3 and ia.provinceAuditResult = 2) or " +
				" (ia.provinceAuditStatus = 0 and ia.provinceAuditResult = 0 and ia.universityAuditStatus = 3 and ia.universityAuditResult = 2) " +
				" ) and ia.year = :year  order by ia.id ";
		
		
		hql.append(hqlselect).append(hqlfromwhere);
		
		long allSize = 0;
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
		try {
			allSize = dao.query(hql.toString(), argsMap).size();
			//判断是否取完，并返回提示
			int first = 0, maxresults = 0;
			if (counts*fetchSize <= allSize ) {
				first = (counts-1)*fetchSize;
				maxresults = fetchSize;
			} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize && allSize != 0) {
				first = (counts-1)*fetchSize;
				maxresults = (int) (allSize - (counts-1)*fetchSize);
			} else { //(counts-1)*fetchSize > allSize),数据已经获取完毕， 返回提示信息
				return responseContent("notice", "success");
			}
			//添加控制条信息
			
			records.addElement("totalNum").setText(String.valueOf(allSize));
			//按照条件获取数据
			List<Object[]> list = dao.query(hql.toString(), argsMap, first, maxresults);
			for (Object[] itemOjbect : list) {
				//toWorkGeneralProjectApplicationItem(records, itemOjbect);
				toWorkInstpProjectApplicationItem(records, itemOjbect);
				if (counts % 4 == 0) {
					dao.clear();
				}
			}
			long end = System.currentTimeMillis();
			int toCount = first + maxresults - 1 ;
			System.out.println("instp" + "(共" + allSize + "条)/第" + counts + "批 "  + "(#" + first + " ~ #" + toCount + "), 本次fetch:"  + list.size() + "条, 耗时 " + (end - begin) + "ms！");
		} catch (Exception e) {
			e.printStackTrace();
			return responseContent("error", "服务端错误！");
		}
		return responseContent("data", document);
		
			
		/*hql.append(hqlselect).append(hqlfromwhere);
		
		long allSize = 0;
		try {
			//allSize = dao.query(hql.toString(), argsMap).size();
			allSize = dao.count(hql.toString(), argsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int first = 0, maxresults = 0;
		if (counts*fetchSize <= allSize ) {
			first = (counts-1)*fetchSize;
			maxresults = fetchSize;
		} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize) {
			first = (counts-1)*fetchSize;
			maxresults = (int) (allSize - (counts-1)*fetchSize);
		} else { //counts-1 > allSize ,数据已经获取完毕， 返回提示信息
			return responseContent("notice", "success");
		}
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
	try {
		
		//按照条件获取数据

		List<Object[]> list = dao.query(hql.toString(), argsMap, first, maxresults);
		for (Object[] o : list) {
			
			toWorkInstpProjectApplicationItem(records, o);
			//释放内存
			if (counts %4 == 0) {
				dao.clear();
			}
		}
	
		long end = System.currentTimeMillis();
		int toCount = first + maxresults - 1 ;
		System.out.println("instp" + "(共" + allSize + "条)/第" + counts + "批 "  + "(#" + first + " ~ #" + toCount + "), 本次fetch:"  + list.size() + "条, 耗时 " + (end - begin) + "ms！");
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return responseContent("data", document);*/
	}

	/**
	 * smas --> smdb 获取 year 年的一般项目的申请数据<br>
	 * 说明：<br>
	 * 1.按照尽可能满足项目流程中的非规范项目申请数据情况<br>
	 * 同步规则：<br>
	 * (1) 省级审核状态已提交 且 省级审核通过；<br>
	 * (2) 省级审核状态未提交且 省级结果未提交 且 校审核状态已提交 且 校级审核通过；<br>
	 * 备注：
	 * (1) reason字段信息时不符合申请条件信息，该类项目必然不通过，此字段不同步（已在数据库中核实）
	 * (2) smdb 查重后，再进行后续数据同步工作。
	 * (3) “专项任务项目”排除在外 ;
	 * @param year 获取申请的年度条件
	 * @param fetchSize 获取数据大小
	 * @param counts 获取批次
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getGeneralProjectApplication(String year, int fetchSize, int counts) {
		long begin = System.currentTimeMillis();
		//新数据和往年同步字段信息不一样
		Map argMap = new HashMap();
		argMap.put("year", Integer.parseInt(year));
		StringBuffer hql = new StringBuffer();
		//以波波14年入库代码为参考，smas项目申请表为主要依据
		/**
		 * 说明：
		 * （1）只有项目子类是"专项任务项目"的才细分二级分主题，三级子类；
		 * （2）finalResultType,由smdb两个字段组成 C_PRODUCT_TYPE ；C_PRODUCT_TYPE_OTHER 组成；
		 * 		同时，smas段也有这两个字段对应，便于项目变更处理
		 * （3）gender等负责人信息，与members信息同步 可以根据项目申请id查找；
		 * （4）同步至smas段的所有数据都默认置为 isReviewable = 1；
		 * （5）C_NUMBER 项目编号 不是必选项，不参与同步 、 
		 * （6）C_REASON	，同步的都是参评项目，此字段不同步
		 * （7）smas端的type对应smdb段的type:项目类型general instp ； 
		 * 		smas端的projectType对应smdb的subtype
		 * （8）smas端的isImported字段不需要同步[0：走流程SMAS同步；1：SMAS直接录入产生],修改处理为0（默认）
		 * 		和importedDate修改
		 * （9）	以下是基地项目特有信息
		 * 		C_DIRECTOR_DIVISION_NAME	负责人所属部门（院系/基地）名称
				C_DISCIPLINE_DIRECTION	研究方向
				C_INSTITUTE_NAME	基地名称
				C_DIRECTOR_UNIVERSITY	负责人所属学校名称
		 *	(10)项目在同步至smas之前，需要首先在smdb做一次“初评审核”，然后将firstAuditResult 初审结果 firstAuditDate初评日期段信息后，在进行同步。
		 *  (11)【关键】项目负责人字段有多人情况处理办法：
		 *  	C_APPLICANT_NAME字段数据直接同步过去，但是在同步负责人信息时，applicantID以";"分割，只同步排在第一位的项目申请人的基本信息，
		 *  	但是要把第二个的项目成员信息加到member中。
		 *	(12)【关键】对于同步的项目一般、基地，在smas端需要分别修改对应的申请人字段申请年份字段信息
		 *  (13) 一般项目入库时，没有通过审核的，教育部给的excel中有一个字段直接置入ProjectApplication.provinceAuditOpinion,此字段不需要同步
		 * 		初评操作在smdb中进行，初评字段有两个： genApp.firstAuditResult , genApp.firstAuditDate需要同步
		 *  (14) sinossid的同步，需要关联sinoss申请表对应的sinossid字段;
		 *  (15) （特殊数据）4028d82a4cf8aca3014cf8b18c880006 这个申请id是smdb后续添加的，中间表中sinoss中没有对应记录
		 */
		
		String hqlselect = "select genApp.id, genApp.name, genApp.applicantName, u.code, u.name, projectType.name, genApp.year, genApp.applicantSubmitDate, genApp.planEndDate, genApp.disciplineType," +
				" genApp.discipline, reType.name, genApp.productType, genApp.productTypeOther, genApp.applyFee, genApp.otherFee, genApp.file, genApp.note, genApp.type, projectFee, " +
				" genApp.id, genApp.firstAuditResult , genApp.firstAuditDate, u.type, genApp.applicantId , sinossApp.sinossId, genApp.divisionName ";
		//测试，用genApp.name 代替初评三个结果genApp.reason, genApp.firstAuditResult , genApp.firstAuditDate

		String hqlfromwhere = " from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee ,SinossProjectApplication sinossApp left join sinossApp.projectApplication smdbApp " +
				" where genApp.type = 'general' and projectType.name != '专项任务项目' and (" +
				" (genApp.provinceAuditStatus = 3 and genApp.provinceAuditResult = 2) or " +
				" (genApp.provinceAuditStatus = 0 and genApp.provinceAuditResult = 0 and genApp.universityAuditStatus = 3 and genApp.universityAuditResult = 2) " +
				" ) and genApp.year = :year and smdbApp.id = genApp.id order by genApp.id ";
		
		/**
		 * 以下是修复片段代码：
		 * 代码修复只执行一次
		 */
//		// (1) 新增一套无sinoss id的数据特殊项目同步id=4028d82a4cf8aca3014cf8b18c880006）
		// (2) 新增62条数据 smdb中的入库代码考虑不周smdb有62条不同过项目变为审核通过，补全smas中62条新增数据
		// (3) 新增6条项目，同步至smas
//	 	String ProjectItems62ID = "4028d82a4c6ec026014c6ef061065a34;4028d82a4c6ec026014c6ef5acac6452;4028d82a4c6ec026014c6ece9d671869;4028d82a4c6ec026014c6ec578e401b7;4028d82a4c6ec026014c6ee25e4637e0;4028d82a4c6ec026014c6ec662fe0410;4028d82a4c6ec026014c6edf77923003;4028d82a4c6ec026014c6eef872a57d8;4028d82a4c6ec026014c6eefee455923;4028d82a4c6ec026014c6ef411aa6142;4028d82a4c6ec026014c6ecd817b152c;4028d82a4c6f281f014c6f391acd2604;4028d82a4c6f281f014c6f434d8f3fcf;4028d82a4c6f281f014c6f34538b19c0;4028d82a4c6f281f014c6f3494001aad;4028d82a4c6f281f014c6f6afc6236cd;4028d82a4c6f281f014c6f67e89c08ef;4028d82a4c6f281f014c6f5c06cf718b;4028d82a4c6f281f014c6f69bb4926f8;4028d82a4c6f281f014c6f7728ad69e6;4028d82a4c6f281f014c6ff26b8a2dc1;4028d82a4c6f281f014c6fe6359d08bc;4028d82a4c6f281f014c6f7debb07afe;4028d82a4c6f281f014c6f8c38a31b38;4028d82a4c6f281f014c6f7ece3e7c89;4028d82a4c6f281f014c6f82530703e7;4028d82a4c6f281f014c6f7c06c67612;4028d82a4c6f281f014c6ff7539f3ac5;4028d82a4c6f281f014c6ffb79814464;4028d82a4c6f281f014c700d87d763a0;4028d82a4c6f281f014c7016a3ab7fa1;4028d82a4c6f281f014c700a68f65817;4028d82a4c6f281f014c701d991c0fc1;4028d82a4c6f281f014c70223c3d1fdc;4028d82a4c6f281f014c7013e42e7363;4028d82a4c726e6d014c7281caac0794;4028d82a4c726e6d014c729b4aac4a3f;4028d82a4c726e6d014c72a1315e5bbd;4028d82a4c726e6d014c72a8a5186c7c;4028d82a4c726e6d014c728aee51249d;4028d82a4c726e6d014c728dcb782de2;4028d82a4c726e6d014c729ca0af4e55;4028d82a4c726e6d014c7289183a1e32;4028d82a4c726e6d014c729762233e93;4028d82a4c726e6d014c72f67d9b0418;4028d82a4c726e6d014c72fa4e7e0d60;4028d82a4c726e6d014c7310c4b93c92;4028d82a4c726e6d014c7324528b6178;4028d82a4c726e6d014c73249e77623c;4028d82a4c726e6d014c72fe567b1627;4028d82a4c726e6d014c731c284d4c37;4028d82a4c726e6d014c730560992660;4028d82a4c726e6d014c730e041a3774;4028d82a4c726e6d014c72f5741400ee;4028d82a4c726e6d014c7325b04964fd;4028d82a4c726e6d014c7307078829ca;4028d82a4c726e6d014c731b0cf74927;4028d82a4c726e6d014c7300348c1b03;4028d82a4c726e6d014c732fd20c7b2d;4028d82a4c726e6d014c733955440b9a;4028d82a4c726e6d014c7334b7550496;4028d82a4c726e6d014c73358af20596";
//	 	String projectItems6ID = "4028d82a4c726e6d014c7302a3312007;4028d82a4c6f281f014c6f5c299971bf;4028d82a4c6f281f014c6f5c8b347214;4028d82a4c6ec026014c6efeda89760f;4028d82a4c726e6d014c73081bf72b93;4028d82a4c726e6d014c7289e08c20c7";
//		String[] id6Strings = projectItems6ID.split(";");
//	 	argMap.put("id6Strings", id6Strings);
//		String hqlselect = "select genApp.id, genApp.name, genApp.applicantName, u.code, u.name, projectType.name, genApp.year, genApp.applicantSubmitDate, genApp.planEndDate, genApp.disciplineType," +
//				" genApp.discipline, reType.name, genApp.productType, genApp.productTypeOther, genApp.applyFee, genApp.otherFee, genApp.file, genApp.note, genApp.type, projectFee, " +
//				" genApp.id, genApp.firstAuditResult , genApp.firstAuditDate, u.type, genApp.applicantId , sinossApp.sinossId, genApp.divisionName ";
//		//测试，用genApp.name 代替初评三个结果genApp.reason, genApp.firstAuditResult , genApp.firstAuditDate
//		String hqlfromwhere = " from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee ,SinossProjectApplication sinossApp left join sinossApp.projectApplication smdbApp " +
//				" where genApp.type = 'general' and projectType.name != '专项任务项目' and (" +
//				" (genApp.provinceAuditStatus = 3 and genApp.provinceAuditResult = 2) or " +
//				" (genApp.provinceAuditStatus = 0 and genApp.provinceAuditResult = 0 and genApp.universityAuditStatus = 3 and genApp.universityAuditResult = 2) " +
//				" ) and genApp.year = :year and smdbApp.id = genApp.id and genApp.id in (:id6Strings) ";
	 	
//		String hqlselect = "select genApp.id, genApp.name, genApp.applicantName, u.code, u.name, projectType.name, genApp.year, genApp.applicantSubmitDate, genApp.planEndDate, genApp.disciplineType," +
//				" genApp.discipline, reType.name, genApp.productType, genApp.productTypeOther, genApp.applyFee, genApp.otherFee, genApp.file, genApp.note, genApp.type, projectFee, " +
//				" genApp.id, genApp.firstAuditResult , genApp.firstAuditDate, u.type, genApp.applicantId , genApp.firstAuditResult, genApp.divisionName ";
//		//测试，用genApp.name 代替初评三个结果genApp.reason, genApp.firstAuditResult , genApp.firstAuditDate
//		String hqlfromwhere = " from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee " +
//				" where genApp.type = 'general' and projectType.name != '专项任务项目' and (" +
//				" (genApp.provinceAuditStatus = 3 and genApp.provinceAuditResult = 2) or " +
//				" (genApp.provinceAuditStatus = 0 and genApp.provinceAuditResult = 0 and genApp.universityAuditStatus = 3 and genApp.universityAuditResult = 2) " +
//				" ) and genApp.year = :year and genApp.id = '4028d82a4cf8aca3014cf8b18c880006' ";

		hql.append(hqlselect).append(hqlfromwhere);

		long allSize = 0;
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
		try {
			allSize = dao.query(hql.toString(), argMap).size();
			//判断是否取完，并返回提示
			int first = 0, maxresults = 0;
			if (counts*fetchSize <= allSize ) {
				first = (counts-1)*fetchSize;
				maxresults = fetchSize;
			} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize && allSize != 0) {
				first = (counts-1)*fetchSize;
				maxresults = (int) (allSize - (counts-1)*fetchSize);
			} else { //(counts-1)*fetchSize > allSize),数据已经获取完毕， 返回提示信息
				return responseContent("notice", "success");
			}
			//添加控制条信息
			
			records.addElement("totalNum").setText(String.valueOf(allSize));
			//按照条件获取数据
			List<Object[]> list = dao.query(hql.toString(), argMap, first, maxresults);
			for (Object[] itemOjbect : list) {
				toWorkGeneralProjectApplicationItem(records, itemOjbect);
				if (counts % 4 == 0) {
					dao.clear();
				}
			}
			long end = System.currentTimeMillis();
			int toCount = first + maxresults - 1 ;
			System.out.println("general" + "(共" + allSize + "条)/第" + counts + "批 "  + "(#" + first + " ~ #" + toCount + "), 本次fetch:"  + list.size() + "条, 耗时 " + (end - begin) + "ms！");
		} catch (Exception e) {
			e.printStackTrace();
			return responseContent("error", "服务端错误！");
		}
		return responseContent("data", document);
	}
	
	/*
	 * smas->smdb
	 */
	private String getUniqueInstpProjectApplication(String projectName, String applicantName, int year) {
		StringBuffer hql = new StringBuffer();
		String hqlselect = " select ia.id, ia.type, ia.discipline, ia.disciplineType, ia.name, ia.year, projectType.name, reType.name, ia.isReviewable, ia.applicantSubmitDate, " +//0-9
				" ia.planEndDate, ia.file, ia.productType, ia.productTypeOther, ia.divisionName, ia.universityAuditResult, ia.provinceAuditResult, u.name, u.code, u.type, institute.name, " +//10 - 20
				" ia.note, ia.finalAuditResult , ia.finalAuditorName, ia.finalAuditDate , ia.finalAuditOpinion , ia.finalAuditOpinionFeedback, ia.reviewDate , ia.reviewTotalScore , ia.reviewAverageScore , ia.reviewWay , " +
				" ia.reviewResult , ia.reviewOpinion , soreviewGread.name , ia.reviewOpinionQualitative, projectFee ";
		String hqlfromhwere = " from InstpApplication ia left join ia.subtype projectType left join ia.researchType reType left join ia.university u left join ia.institute institute left join ia.projectFee projectFee left join ia.reviewGrade soreviewGread " +
				" where ia.type = 'instp' and ia.name = ? and ia.year = ? and ia.applicantName = ? ";
		hql.append(hqlselect).append(hqlfromhwere);
		//判断是否取完，并返回提示
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
		try {
			//按照条件获取数据
			List resultList = dao.query(hql.toString(), projectName, year, applicantName);
			if(resultList != null && resultList.size() == 1) {
				Object[] o = (Object[]) resultList.get(0);
				toParseInstpProjectApplicationItem(records, o);
			} else if (resultList != null && resultList.size() > 1) {
				Object[] o = toFilterMultiInstpProjectApplicationItem(hqlselect,projectName, applicantName, year);
				if (null != o) {
					toParseInstpProjectApplicationItem(records, o);
				} else {
					return responseContent("error", projectName + "/" + applicantName + "/" + year +" :smdb中存在多种结果");
				}
			} else {
				Object[] o = toWidenInstpProjectApplicationItem(hqlselect, projectName, applicantName, year);
				if (null != o) {
					toParseInstpProjectApplicationItem(records, o);
				} else {
					return responseContent("error", projectName + "/" + applicantName + "/" + year +" :smdb中无对应匹配数据");
				}
			}
		} catch (Exception e) {
			System.err.print(projectName + "/" + applicantName + "/" + year +" :smdb中存在多种结果");
			return responseContent("error", projectName + "/" + applicantName + "/" + year +" :smdb中存在多种结果");
		}
		return responseContent("data", document);
	}
	private String getUniqueGeneralProjectApplication(String projectName, String applicantName, int year) {
		StringBuffer hql = new StringBuffer();
		String hqlselect = " select genApp.id, genApp.type, genApp.discipline, genApp.disciplineType, genApp.name, genApp.year, reType.name, projectType.name ,genApp.isReviewable, genApp.applicantSubmitDate, " +
				" genApp.planEndDate, genApp.file, genApp.productType, genApp.productTypeOther, genApp.applyFee, genApp.otherFee, genApp.divisionName, genApp.universityAuditResult, genApp.provinceAuditResult, u.name, u.code, " +
				" u.type, genApp.note, genApp.finalAuditResult , genApp.finalAuditorName, genApp.finalAuditDate, genApp.finalAuditOpinion, genApp.finalAuditOpinionFeedback, genApp.reviewDate , genApp.reviewTotalScore , genApp.reviewAverageScore , " +
				" genApp.reviewWay, genApp.reviewResult, genApp.reviewOpinion, soreviewGread.name , genApp.reviewOpinionQualitative, projectFee ";//30-35
				
		String hqlfromwhere = " from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee left join genApp.reviewGrade soreviewGread " +
				" where genApp.type = 'general' and projectType.name != '专项任务项目' and genApp.name = ? and genApp.year = ? and genApp.applicantName = ? ";//
		
		hql.append(hqlselect).append(hqlfromwhere);
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
	try {
		//按照条件获取数据
		List resultList = dao.query(hql.toString(), projectName, year, applicantName);
		if(resultList != null && resultList.size() == 1) {
			Object[] o = (Object[]) resultList.get(0);
			toParseGeneralProjectApplicationItem(records, o);
		} else if (resultList != null && resultList.size() > 1) {
			//多个结果 添加条件过滤
			Object[] o = toFilterMultiGeneralProjectApplicationItem(hqlselect,projectName, applicantName, year);
			if (null != o) {
				toParseGeneralProjectApplicationItem(records, o);
			} else {
				return responseContent("error", projectName + "/" + applicantName + "/" + year +" :smdb中存在多种结果");
			}
		} else {
			//查询结果为0，放宽条件 
			Object[] o = toWidenGeneralProjectApplicationItem(hqlselect, projectName, applicantName, year);
			if (null != o) {
				toParseGeneralProjectApplicationItem(records, o);
			} else {
				return responseContent("error", projectName + "/" + applicantName + "/" + year +" :smdb中无对应匹配数据");
			}
		}
		
	} catch (Exception e) {
		//将异常信息返回
		System.err.print(projectName + "/" + applicantName + "/" + year +" :smdb中存在多种结果");
		return responseContent("error", projectName + "/" + applicantName + "/" + year +" :smdb中存在多种结果");
	}
	return responseContent("data", document);
}
	/**
	 * 批量返回立项信息
	 * @param fetchSize 每次取的大小建议 500 ~ 1000
	 * @param counts 取的次数 基于1开始，1，2，3，···
	 * @return
	 */
	public String requestProjectGranted(String projectType, int fetchSize, int counts) {
		//项目立项数据，所有通过外键关联查出来封装好,经费详情单独建立一个子分支
		long begin = System.currentTimeMillis();
		StringBuffer hql = new StringBuffer();
		String hqlSelect = "select pg.id, pg.name, pg.projectType, pg.number, pg.status, pg.agencyName, " +//0-5
				"pg.applicantName, pg.applicantSubmitDate, pg.applicationId, pg.approveDate, pg.approveFee, pg.auditType, " +
				"pg.divisionName, pg.endStopWithdrawDate, pg.endStopWithdrawOpinion, pg.endStopWithdrawPerson, pg.file, " +
				"pg.finalAuditDate, pg.finalAuditOpinion, pg.finalAuditOpinionFeedback, pg.finalAuditResult, pg.finalAuditorName," +
				"pg.importAuditDate, pg.endStopWithdrawOpinionFeedback, pg.planEndDate, pg.productType, pg.productTypeOther, " + 
				//project传送id，subtype传送id，要在smas中配置经费表，和systemconfig中的配置。
				"subtype.name, u.name, u.code, pfee ";//30
		
		
		//String hqlSelect = "select pg.id, pg.name, pg.projectType, pg.agencyName, pg.applicantName ";//5
		
		String hqlFromWhere = null;
		
		
		
		
		/*if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u " +
					"where subtype.name != '专项任务项目' and pg.projectType = 'general'"; 
			//
			hqlFromWhere = "from GeneralGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u left join pg. " +
			"where subtype.name != '专项任务项目' and pg.projectType = 'general'"; 
			
			
			
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u " +
					"where pg.type = 'instp'";
		}*/
		
		
		//更改by hxw
		//*************************//
		
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u ,ProjectApplication pa " +
					"where subtype.name != '专项任务项目' and pg.projectType = 'general' and  pg.applicationId = pa.id and pa.year='2015'"; 
			
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u " +
					"where pg.type = 'instp'";
		}
		
		
		//*******************//
		hql.append(hqlSelect).append(hqlFromWhere);
		//判断是否取完，并返回提示
		//long allSize = dao.count(hql.toString()); 
		
		long allSize = dao.query(hql.toString()).size();
		
		
		int first = 0, maxresults = 0;
		if (counts*fetchSize <= allSize ) {
			first = (counts-1)*fetchSize;
			maxresults = fetchSize;
		} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize) {
			first = (counts-1)*fetchSize;
			maxresults = (int) (allSize - (counts-1)*fetchSize);
		} else { //counts-1 > allSize ,数据已经获取完毕， 返回提示信息
			return responseContent("notice", "success");
		}
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
		
		
		//records.addElement("totalNum").setText(String.valueOf(allSize));
		//按照条件获取数据
		List<Object[]> list = dao.query(hql.toString(), first, maxresults);
		for (Object[] o : list) {
			toParseProjectGrantedItem(records, o);
		}//for
		//释放内存
		if (counts % 2 == 0) {
			dao.clear();
		}
		long end = System.currentTimeMillis();
		int toCount = first + maxresults - 1 ;
		System.out.println(projectType + "/第" + counts + "次同步，此次同步" + list.size() + "条数据,总耗时 :" + (end - begin) + "ms," + "范围(#" + first + " ~ #" + toCount + ")" + ",共：" + allSize + "条" );
		return responseContent("data", document);
	}
	//smas端获取中间临时表信息对应的服务接口
	/*public String requestProjectGrantedTempInfo(String projectType, int fetchSize, int counts) {
		long begin = System.currentTimeMillis();
		StringBuffer hql = new StringBuffer();
		String hqlSelect = "select pg.id, pg.name, pg.projectType, pg.agencyName, pg.applicantName ";//5
		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralGranted pg left join pg.subtype subtype where subtype.name != '专项任务项目' and pg.type = 'general'"; 
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpGranted pg left join pg.subtype subtype where pg.type = 'instp'";
		}
		hql.append(hqlSelect).append(hqlFromWhere);
		//判断是否取完，并返回提示
		long allSize = dao.count(hql.toString()); 
		int first = 0, maxresults = 0;
		if (counts*fetchSize <= allSize ) {
			first = (counts-1)*fetchSize;
			maxresults = fetchSize;
		} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize) {
			first = (counts-1)*fetchSize;
			maxresults = (int) (allSize - (counts-1)*fetchSize);
		} else { //counts-1 > allSize ,数据已经获取完毕， 返回提示信息
			return responseContent("notice", "success");
		}
		Document document = DocumentHelper.createDocument();
		Element response = document.addElement("response");
		response.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Element records = response.addElement("records");
		//按照条件获取数据
		List<Object[]> list = dao.query(hql.toString(), first, maxresults);
		for (Object[] o : list) {
			Element itemElement = records.addElement("item");
			String[] str = new String[30];//共31个数据，最后一个为经费对象
			for (int i = 0; i < o.length - 1 ; i++) {
				if (o[i] instanceof String) {
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] instanceof Date) {
					str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}	
			ProjectFee pFee = (ProjectFee) o[o.length-1];
			//组装一个item
			Element idElement = itemElement.addElement("id");
			idElement.setText(str[0]);
			Element nameElement = itemElement.addElement("name");
			nameElement.setText(str[1]);
			Element projectTypeElement = itemElement.addElement("projectType");
			projectTypeElement.setText(str[2]);
			Element agencyNameElement = itemElement.addElement("agencyName");
			agencyNameElement.setText(str[3]);
			Element applicantNameElement = itemElement.addElement("applicantName");
			applicantNameElement.setText(str[4]);
		}//for
		//释放内存
		if (counts % 2 == 0) {
			dao.clear();
		}
		long end = System.currentTimeMillis();
		int toCount = first + maxresults - 1 ;
		System.out.println(projectType + "/第" + counts + "次同步，此次同步" + list.size() + "条数据,总耗时 :" + (end - begin) + "ms," + "范围(#" + first + " ~ #" + toCount + ")" + ",共：" + allSize + "条" );
		return responseContent("data", document);
	}*/
	/**
	 * 根据申请id获取对应的立项信息
	 * @param projectType
	 * @param projectApplicationID
	 * @return
	 */
	public String requestUniqueProjectGranted(String projectType, String projectApplicationID) {
		StringBuffer hql = new StringBuffer();
		String hqlSelect = "select pg.id, pg.name, pg.projectType, pg.number, pg.status, pg.agencyName, " +//0-5
				"pg.applicantName, pg.applicantSubmitDate, pg.applicationId, pg.approveDate, pg.approveFee, pg.auditType, " +
				"pg.divisionName, pg.endStopWithdrawDate, pg.endStopWithdrawOpinion, pg.endStopWithdrawPerson, pg.file, " +
				"pg.finalAuditDate, pg.finalAuditOpinion, pg.finalAuditOpinionFeedback, pg.finalAuditResult, pg.finalAuditorName," +
				"pg.importAuditDate, pg.endStopWithdrawOpinionFeedback, pg.planEndDate, pg.productType, pg.productTypeOther, " + 
				//project传送id，subtype传送id，要在smas中配置经费表，和systemconfig中的配置。
				"subtype.name, u.name, u.code, pfee ";//30
		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u left join pg.application pa " +
					"where subtype.name != '专项任务项目' and pg.projectType = 'general' and pa.id = ? "; 
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpGranted pg left join pg.projectFee pfee left join pg.subtype subtype left join pg.university u left join pg.application pa " +
					"where pg.projectType = 'instp' and pa.id = ? ";
		}
		hql.append(hqlSelect).append(hqlFromWhere);

		//按照条件获取数据
		List<Object[]> list = dao.query(hql.toString(), projectApplicationID);
		if (list != null && list.size() == 1) {
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
			Object[] o = list.get(0);
			toParseProjectGrantedItem(records, o);
			System.out.println("requestUniqueProjectGranted:projectType/projectApplicationID =" + projectType + "/" + projectApplicationID );
			return responseContent("data", document);
		} else {
			return responseContent("error", projectType + "/" + projectApplicationID + " :smdb中无此立项数据准确对应此申请id");
		}
	}

	
	/**
	 * 获取项目变更信息
	 * @param fetchSize
	 * @param counts
	 * @return
	 */
	public String requestProjectVariation(String projectType, int fetchSize, int counts) {
		long begin = System.currentTimeMillis();
		String hqlSelect = "select pv.id, granted.id, pv.file, pv.applicantSubmitDate, pv.variationReason, pv.changeMember, granted.applicationId, pv.oldMemberGroupNumber, pv.newMemberGroupNumber, pv.changeAgency, pv.oldAgencyName || '; ' || pv.oldDivisionName, pv.newAgencyName || '; ' || pv.newDivisionName, " +// 0-11
				"pv.changeProductType, pv.oldProductType, pv.oldProductTypeOther, pv.newProductType, pv.newProductTypeOther, pv.changeName, pv.newName, pv.oldName, pv.changeContent, pv.postponement, " +//10
				"pv.oldOnceDate, pv.newOnceDate, pv.stop, pv.withdraw, pv.other, pv.otherInfo, pv.postponementPlanFile, pv.finalAuditResult, pv.finalAuditResultDetail, pv.finalAuditorName, pv.finalAuditDate, " +//11
				"pv.finalAuditOpinion, pv.finalAuditOpinionFeedback, pv.projectType, pv.universityAuditResultDetail, pv.provinceAuditResultDetail, pv.changeFee, newFee, oldFee ";//37  

		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralVariation pv left join pv.granted granted left join granted.subtype subtype left join pv.oldProjectFee oldFee left join pv.newProjectFee newFee left join pv.oldAgency oldAgency left join pv.newAgency newAgency " +
						" where (subtype.name != '专项任务项目' or subtype is null) and granted.id = ?";
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpVariation pv left join pv.granted granted left join pv.oldProjectFee oldFee left join pv.newProjectFee newFee left join pv.oldAgency oldAgency left join pv.newAgency newAgency " +
					" where granted.id = ?";
		}
		String hql = hqlSelect + hqlFromWhere;
		//判断是否取完，并返回提示
		long allSize = 0;
		try {
			allSize = dao.count(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int first = 0, maxresults = 0;
		if (counts*fetchSize <= allSize ) {
			first = (counts-1)*fetchSize;
			maxresults = fetchSize;
		} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize) {
			first = (counts-1)*fetchSize;
			maxresults = (int) (allSize - (counts-1)*fetchSize);
		} else { //counts-1 > allSize ,数据已经获取完毕， 返回提示信息
			return responseContent("notice", "success");
		}
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
		try {
			//按照条件获取数据
			List<Object[]> list = dao.query(hql.toString(), first, maxresults);
			for (Object[] o : list) {
				toParseProjectVariationItem(records, o);
				//释放
				if (counts % 2 == 0) {
					dao.clear();
				}
			}
			long end = System.currentTimeMillis();
			int toCount = first + maxresults - 1 ;
			System.out.println(projectType + "/第" + counts + "次同步，此次同步" + list.size() + "条数据,总耗时 :" + (end - begin) + "ms," + "范围(#" + first + " ~ #" + toCount + ")" + ",共：" + allSize + "条" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseContent("data", document);
	}
	/**
	 * 获取立项id对应的批量变更信息
	 * @param projectType
	 * @param projectGrantedID
	 * @return
	 */
	public String requestBatchProjectVariation(String projectType, String projectGrantedID) {
		String hqlSelect = "select pv.id, granted.id, pv.file, pv.applicantSubmitDate, pv.variationReason, pv.changeMember, granted.applicationId, pv.oldMemberGroupNumber, pv.newMemberGroupNumber, pv.changeAgency, pv.oldAgencyName || '; ' || pv.oldDivisionName, pv.newAgencyName || '; ' || pv.newDivisionName, " +// 0-11
				"pv.changeProductType, pv.oldProductType, pv.oldProductTypeOther, pv.newProductType, pv.newProductTypeOther, pv.changeName, pv.newName, pv.oldName, pv.changeContent, pv.postponement, " +//10
				"pv.oldOnceDate, pv.newOnceDate, pv.stop, pv.withdraw, pv.other, pv.otherInfo, pv.postponementPlanFile, pv.finalAuditResult, pv.finalAuditResultDetail, pv.finalAuditorName, pv.finalAuditDate, " +//11
				"pv.finalAuditOpinion, pv.finalAuditOpinionFeedback, pv.projectType, pv.universityAuditResultDetail, pv.provinceAuditResultDetail, pv.changeFee, newFee, oldFee ";//37  

		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralVariation pv left join pv.granted granted left join granted.subtype subtype left join pv.oldProjectFee oldFee left join pv.newProjectFee newFee left join pv.oldAgency oldAgency left join pv.newAgency newAgency " +
						" where (subtype.name != '专项任务项目' or subtype is null) and granted.id = ?";
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpVariation pv left join pv.granted granted left join pv.oldProjectFee oldFee left join pv.newProjectFee newFee left join pv.oldAgency oldAgency left join pv.newAgency newAgency " +
					" where granted.id = ?";
		}
		String hql = hqlSelect + hqlFromWhere;
		List<Object[]> list = dao.query(hql.toString(), projectGrantedID);
		if (list != null && !list.isEmpty()) {
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
			for (Object[] o : list) {
				toParseProjectVariationItem(records, o);
			}
			System.out.println("requestBatchProjectVariation:projectType/projectGrantedID=" + projectType + "/" + projectGrantedID );
			return responseContent("data", document);
		} else {
			return responseContent("notice", projectType + "/" + projectGrantedID + " :smdb中此projectGrantedID无对应的项目变更数据");
		}
	}
	/**
	 * 获取项目中检信息
	 * @param fetchSize
	 * @param counts
	 * @return
	 */
	public String requestProjectMidinspection(String projectType, int fetchSize, int counts) {
		long begin = System.currentTimeMillis();
		String hqlSelect = "select pm.id, pm.file, pm.applicantSubmitDate, pm.progress, pm.productIntroduction, pm.finalAuditResult, pm.finalAuditorName, pm.finalAuditDate, pm.finalAuditOpinion, pm.finalAuditOpinionFeedback, " +
				"pm.projectType, pm.grantedId, projectFee ";
		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralMidinspection pm left join pm.projectFee projectFee left join pm.granted pg left join pg.subtype subtype  " +
					"where (subtype.name != '专项任务项目' or subtype is null) and pg.id = ?";
			
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpMidinspection pm left join pm.projectFee projectFee left join pm.granted pg  " +
					"where pg.id = ?";
		}
		String hql = hqlSelect + hqlFromWhere;
		//过滤条件查询待分析，查出的结果为0
		//判断是否取完，并返回提示
		long allSize = 0;
		try {
			allSize = dao.count(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int first = 0, maxresults = 0;
		if (counts*fetchSize <= allSize ) {
			first = (counts-1)*fetchSize;
			maxresults = fetchSize;
		} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize) {
			first = (counts-1)*fetchSize;
			maxresults = (int) (allSize - (counts-1)*fetchSize);
		} else { //counts-1 > allSize ,数据已经获取完毕， 返回提示信息
			return responseContent("notice", "success");
		}
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("records");
		try {
			//按照条件获取数据
			List<Object[]> list = dao.query(hql.toString(), first, maxresults);
			for (Object[] o : list) {
				toParseProjectMidinspectionItem(records, o);
				if (counts % 2 == 0) {
					dao.clear();
				}
			}
			long end = System.currentTimeMillis();
			int toCount = first + maxresults - 1 ;
			System.out.println(projectType + "/第" + counts + "次同步，此次同步" + list.size() + "条数据,总耗时 :" + (end - begin) + "ms," + "范围(#" + first + " ~ #" + toCount + ")" + ",共：" + allSize + "条" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseContent("data", document);
	}
	/**
	 * 获取立项id对应的批量中检信息
	 * @param projectType
	 * @param projectGrantedID
	 * @return
	 */
	public String requestBatchProjectMidinspection(String projectType, String projectGrantedID) {
		String hqlSelect = "select pm.id, pm.file, pm.applicantSubmitDate, pm.progress, pm.productIntroduction, pm.finalAuditResult, pm.finalAuditorName, pm.finalAuditDate, pm.finalAuditOpinion, pm.finalAuditOpinionFeedback, " +
				"pm.projectType, pm.grantedId, projectFee ";
		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralMidinspection pm left join pm.projectFee projectFee left join pm.granted pg left join pg.subtype subtype  " +
					"where (subtype.name != '专项任务项目' or subtype is null) and pg.id = ?";
			
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpMidinspection pm left join pm.projectFee projectFee left join pm.granted pg " +
					"where pg.id = ?";
		}
		String hql = hqlSelect + hqlFromWhere;
		List<Object[]> list = dao.query(hql, projectGrantedID);
		if (list != null && !list.isEmpty()) {
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
			for (Object[] o : list) {
				toParseProjectMidinspectionItem(records, o);
			}
			System.out.println("requestBatchProjectMidinspection:projectType/projectGrantedID=" + projectType + "/" + projectGrantedID );
			return responseContent("data", document);
		} else {
			return responseContent("notice", projectType + "/" + projectGrantedID + " :smdb中无此中检数据准确对应此projectGrantedID");
		}
	}
	
	/**
	 * 获取项目结项信息
	 * @param fetchSize
	 * @param counts
	 * @return
	 */
	public String requestProjectEndinspection(String projectType, int fetchSize, int counts) {
			long begin = System.currentTimeMillis();
			String hqlSelect = "select pe.id, pe.certificate, pe.file, pe.applicantSubmitDate, pe.isApplyNoevaluation, pe.isApplyExcellent, pe.memberName, pe.ministryAuditorName, pe.ministryAuditDate, pe.ministryAuditOpinion, " +
					"pe.ministryResultEnd, pe.ministryResultNoevaluation, pe.ministryResultExcellent, pe.finalAuditorName, pe.finalAuditDate, pe.finalAuditResultEnd, pe.finalAuditResultNoevaluation, pe.finalAuditResultExcellent, pe.finalAuditOpinion, pe.finalAuditOpinionFeedback, " +
					"pe.reviewerName, pe.reviewDate, pe.reviewTotalScore, pe.reviewAverageScore, pe.reviewWay, pe.reviewResult, pe.reviewOpinion, reviewGrade.name, pe.reviewOpinionQualitative, pe.projectType, " +
					"pe.grantedId, pe.importedProductInfo, pe.importedProductTypeOther, projectFee ";
			String hqlFromWhere = null;
			if (projectType.equals("general")) {
				hqlFromWhere = "from GeneralEndinspection pe left join pe.granted pg left join pg.subtype subtype left join pe.reviewGrade reviewGrade left join pe.projectFee projectFee left join pe.granted pg " +
						"where (subtype.name != '专项任务项目' or subtype is null) and pg.id = ? ";
			} else if (projectType.equals("instp")) {
				hqlFromWhere = "from InstpEndinspection pe left join pe.granted pg left join pe.reviewGrade reviewGrade left join pe.projectFee projectFee left join pe.granted pg " +
						"where pg.id = ? ";
			}
			String hql = hqlSelect + hqlFromWhere;
			//过滤条件查询待分析，查出的结果为0
			//判断是否取完，并返回提示
			long allSize = 0;
			try {
				allSize = dao.count(hql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int first = 0, maxresults = 0;
			if (counts*fetchSize <= allSize ) {
				first = (counts-1)*fetchSize;
				maxresults = fetchSize;
			} else if (counts*fetchSize > allSize && (counts-1)*fetchSize <= allSize) {
				first = (counts-1)*fetchSize;
				maxresults = (int) (allSize - (counts-1)*fetchSize);
			} else { //counts-1 > allSize ,数据已经获取完毕， 返回提示信息
				return responseContent("notice", "success");
			}
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
		try {
			//按照条件获取数据
			List<Object[]> list = dao.query(hql.toString(), first, maxresults);
			for (Object[] o : list) {
				toParseProjectEndinspectionItem(records, o);
				//释放
				if (counts % 2 == 0) {
					dao.clear();
				}
			}
			long end = System.currentTimeMillis();
			int toCount = first + maxresults - 1 ;
			System.out.println(projectType + "/第" + counts + "次同步，此次同步" + list.size() + "条数据,总耗时 :" + (end - begin) + "ms," + "范围(#" + first + " ~ #" + toCount + ")" + ",共：" + allSize + "条" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseContent("data", document);
	}
	
	/**
	 * 获取立项id对应的批量结项信息
	 * @param projectType
	 * @param projectGrantedID
	 * @return
	 */
	public String requestBatchProjectEndinspection(String projectType, String projectGrantedID) {
		String hqlSelect = "select pe.id, pe.certificate, pe.file, pe.applicantSubmitDate, pe.isApplyNoevaluation, pe.isApplyExcellent, pe.memberName, pe.ministryAuditorName, pe.ministryAuditDate, pe.ministryAuditOpinion, " +
				"pe.ministryResultEnd, pe.ministryResultNoevaluation, pe.ministryResultExcellent, pe.finalAuditorName, pe.finalAuditDate, pe.finalAuditResultEnd, pe.finalAuditResultNoevaluation, pe.finalAuditResultExcellent, pe.finalAuditOpinion, pe.finalAuditOpinionFeedback, " +
				"pe.reviewerName, pe.reviewDate, pe.reviewTotalScore, pe.reviewAverageScore, pe.reviewWay, pe.reviewResult, pe.reviewOpinion, reviewGrade.name, pe.reviewOpinionQualitative, pe.projectType, " +
				"pe.grantedId, pe.importedProductInfo, pe.importedProductTypeOther, projectFee ";
		String hqlFromWhere = null;
		if (projectType.equals("general")) {
			hqlFromWhere = "from GeneralEndinspection pe left join pe.granted pg left join pg.subtype subtype left join pe.reviewGrade reviewGrade left join pe.projectFee projectFee left join pe.granted pg " +
					"where (subtype.name != '专项任务项目' or subtype is null) and pg.id = ? ";
		} else if (projectType.equals("instp")) {
			hqlFromWhere = "from InstpEndinspection pe left join pe.granted pg left join pe.reviewGrade reviewGrade left join pe.projectFee projectFee left join pe.granted pg " +
					"where pg.id = ? ";
		}
		String hql = hqlSelect + hqlFromWhere;
		
		List<Object[]> list = dao.query(hql, projectGrantedID);
		if (list != null && !list.isEmpty()) {
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
			for (Object[] o : list) {
				toParseProjectEndinspectionItem(records, o);
			}
			System.out.println("requestBatchProjectMidinspection:projectType/projectGrantedID=" + projectType + "/" + projectGrantedID );
			return responseContent("data", document);
		} else {
			return responseContent("notice", projectType + "/" + projectGrantedID + " :smdb中无此结项准确对应此projectGrantedID");
		}
	}
	public String requestUniqueUniversityInfo(String universityCode) {
		String hql = "select u.name, u.code, u.organizer, u.type, u.abbr, provi.name, provi.code, u.style, u.importedDate from Agency u left join u.province provi where u.code = ?";
		//按照条件获取数据
		List<Object[]> list = dao.query(hql, universityCode);
		if (list != null && list.size() == 1) {
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
			Object[] o = list.get(0);
			Element itemElement = records.addElement("item");
			String[] str = new String[9];
			for (int i = 0; i < o.length ; i++) {
				if (o[i] instanceof String) {
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] instanceof Date) {
					str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}
			//组装一个item
			itemElement.addElement("name").setText(str[0]);
			itemElement.addElement("code").setText(str[1]);
			
			itemElement.addElement("founderCode").setText(str[2]);
			String smasType = "null";
			if (str[3].equals("3")) {
				smasType = "1";
			} else if (str[3].equals("4")) {
				smasType = "2";
			} 
			itemElement.addElement("type").setText(smasType);
			itemElement.addElement("abbr").setText(str[4]);
			itemElement.addElement("provinceName").setText(str[5]);
			itemElement.addElement("provinceCode").setText(str[6]);
			itemElement.addElement("style").setText(str[7]);
			itemElement.addElement("importedDate").setText(str[8]);
			System.out.println("university.code:" + universityCode );
			return responseContent("data", document);
		} else {
			return responseContent("error", universityCode + " :smdb中无此高校代码对应的高校记录");
		}
	}
	/**
	 * 修复smas程序
	 * 	
	 */
	public String requestFixSmasProgram(String projectApplicationID) {
		String hql = " select pa.id, pa.file from ProjectApplication pa where pa.id = ?";
		//按照条件获取数据
		List<Object[]> list = dao.query(hql, projectApplicationID);
		if (list != null && list.size() == 1) {
			Document document = DocumentHelper.createDocument();
			Element records = document.addElement("records");
			Object[] o = list.get(0);
			Element itemElement = records.addElement("item");
		
			String[] str = new String[o.length];
			for (int i = 0; i < o.length ; i++) {
				if (o[i] instanceof String) {
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] instanceof Date) {
					str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}
			//组装一个item
			itemElement.addElement("id").setText(str[0]);
			itemElement.addElement("CFile").setText(str[1]);
			return responseContent("data", document);
		} else {
			return responseContent("error", projectApplicationID + " :smdb中此申请id对应的记录！");
		}
	}
	
	/**
	 * 入库smas后，更新smdb中的部级审核信息，采用逐条同步方式
	 * @param universityCode
	 * @return
	 */
	@Transactional
	public String updateProjApplMinistryAuditInfo(Map<String, String> argsMap) {
		//取出数据进行更新操作，单条数据处理，失败不回滚，返回异常信息

		String smdbProjectApplicationID = argsMap.get("projectApplicationID");
		String ministryAuditorName = argsMap.get("ministryAuditorName");

		String ministryAuditStatus = argsMap.get("ministryAuditStatus");
		String ministryAuditResult = argsMap.get("ministryAuditResult");
		String ministryAuditOpinion = argsMap.get("ministryAuditOpinion");
		String ministryAuditDate = argsMap.get("ministryAuditDate");
		String finalAuditOpinionFeedback = argsMap.get("finalAuditOpinionFeedback");

		try {
			ProjectApplication projectApplication = dao.query(ProjectApplication.class, smdbProjectApplicationID);
			//更新
			projectApplication.setMinistryAuditorName(ministryAuditorName);
			Officer 范明宇 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '范明宇'");
			
	    	if (范明宇 == null) {
				throw new RuntimeException("审核人信息为空");
			}
	    	projectApplication.setMinistryAuditor(范明宇);
			projectApplication.setMinistryAuditorAgency(范明宇.getAgency());
	    	
			projectApplication.setMinistryAuditDate(_date(ministryAuditDate));
			projectApplication.setMinistryAuditOpinion(ministryAuditOpinion);
			
			if (ministryAuditStatus.equals("3") && ministryAuditResult.equals("1")) {
				//部级审核状态提交且不同意，还要同时更新最终结果信息
				
				projectApplication.setMinistryAuditResult(1);
				projectApplication.setMinistryAuditStatus(3);

				projectApplication.setFinalAuditDate(_date(ministryAuditDate));
				projectApplication.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				projectApplication.setFinalAuditor(范明宇);
				projectApplication.setFinalAuditorAgency(范明宇.getAgency());
				projectApplication.setFinalAuditorDept(范明宇.getDepartment());
				projectApplication.setFinalAuditorInst(范明宇.getInstitute());
				projectApplication.setFinalAuditorName("范明宇");
				projectApplication.setFinalAuditResult(1);
				projectApplication.setFinalAuditStatus(3);

			} else {
				//部级审核状态提交且同意，只更新部级审核字段，最终结果信息不修改，专家评审后再更新
				projectApplication.setMinistryAuditResult(2);
				projectApplication.setMinistryAuditStatus(3);
			}

			dao.addOrModify(projectApplication);
			//返回提示，状态成功处理
			return responseContent("notice", "success");

		} catch (Exception e) {
			e.printStackTrace();
			return responseContent("error", "更新入库异常");
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//smdb录入审核信息时不确定录入的哪一个，此方法暂时没有用到
	//严格限制条件: 最终审核结果  部级审核结果非空过滤
	private Object[] toFilterMultiGeneralProjectApplicationItem(String hqlSelect, String projectName, String applicantName, int year) {
		//&&限制部级
		String hqlfromwhere = "from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee left join genApp.reviewGrade soreviewGread " +
				"where genApp.type = 'general' and projectType.name != '专项任务项目' and genApp.name = ? and genApp.year = ? " +
				"and genApp.finalAuditStatus != 0 and genApp.finalAuditResult != 0 and genApp.ministryAuditStatus != 0 and genApp.ministryAuditResult != 0 ";
		String hql = hqlSelect + hqlfromwhere;
		List resultList = dao.query(hql, projectName, year, applicantName);
		if(resultList != null && resultList.size() == 1) {
			Object[] o = (Object[]) resultList.get(0);
			return o;
		} else if(resultList != null && resultList.size() > 1) {
			//&& 限制省级
			String hqlfromwhere2 = "from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee left join genApp.reviewGrade soreviewGread " +
					"where genApp.type = 'general' and projectType.name != '专项任务项目' and genApp.name = ? and genApp.year = ? and genApp.finalAuditStatus != 0 and genApp.finalAuditResult != 0 and genApp.ministryAuditStatus != 0 and genApp.ministryAuditResult != 0 " +
					"and genApp.provinceAuditResult != 0 ";
			String hql2 = hqlSelect + hqlfromwhere2;
			List resultList2 = dao.query(hql2, projectName, year, applicantName);
			if(resultList2 != null && resultList2.size() == 1) {
				Object[] o = (Object[]) resultList2.get(0);
				return o;
			} else if(resultList2 != null && resultList2.size() > 1) {
				// && 限制校级
				String hqlfromwhere3 = "from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee left join genApp.reviewGrade soreviewGread " +
						"where genApp.type = 'general' and projectType.name != '专项任务项目' and genApp.name = ? and genApp.year = ? and genApp.finalAuditStatus != 0 and genApp.finalAuditResult != 0 and genApp.ministryAuditStatus != 0 and genApp.ministryAuditResult != 0 and genApp.provinceAuditResult != 0 " +
						"and genApp.universityAuditResult !=0 ";
				String hql3 = hqlSelect + hqlfromwhere3;
				List resultList3 = dao.query(hql3, projectName, year, applicantName);
				if(resultList3 != null && resultList3.size() == 1) {
					Object[] o = (Object[]) resultList3.get(0);
					return o;
				} 
				return null;//三级检索
			}
			return null;//二级检索为空
		}
		return null;//一级检索为空
	}
	//严格限制条件: 最终审核结果  部级审核结果非空过滤
	private Object[] toFilterMultiInstpProjectApplicationItem(String hqlSelect, String projectName, String applicantName, int year) {
		//&&限制部级
		String hqlfromhwere = "from InstpApplication ia left join ia.subtype projectType left join ia.researchType reType left join ia.university u left join ia.institute institute left join ia.projectFee projectFee left join ia.reviewGrade soreviewGread " +
				"where ia.type = 'instp' and ia.name = ? and ia.year = ? and ia.applicantName = ? " +
				"and ia.finalAuditStatus != 0 and ia.finalAuditResult != 0 and ia.ministryAuditStatus != 0 and ia.ministryAuditResult != 0 ";
		String hql = hqlSelect + hqlfromhwere;
		List resultList = dao.query(hql, projectName, year, applicantName);
		if(resultList != null && resultList.size() == 1) {
			Object[] o = (Object[]) resultList.get(0);
			return o;
		}else if(resultList != null && resultList.size() > 1) {
			//&& 限制省级
			String hqlfromhwere2 = "from InstpApplication ia left join ia.subtype projectType left join ia.researchType reType left join ia.university u left join ia.institute institute left join ia.projectFee projectFee left join ia.reviewGrade soreviewGread " +
					"where ia.type = 'instp' and ia.name = ? and ia.year = ? and ia.applicantName = ? and ia.finalAuditStatus != 0 and ia.finalAuditResult != 0 and ia.ministryAuditStatus != 0 and ia.ministryAuditResult != 0 " +
					"and ia.provinceAuditResult != 0 ";
			String hql2 = hqlSelect + hqlfromhwere2;
			List resultList2 = dao.query(hql2, projectName, year, applicantName);
			if(resultList2 != null && resultList2.size() == 1) {
				Object[] o = (Object[]) resultList2.get(0);
				return o;
			} else if(resultList2 != null && resultList2.size() > 1) {
				// && 限制校级
				String hqlfromhwere3 = "from InstpApplication ia left join ia.subtype projectType left join ia.researchType reType left join ia.university u left join ia.institute institute left join ia.projectFee projectFee left join ia.reviewGrade soreviewGread " +
						"where ia.type = 'instp' and ia.name = ? and ia.year = ? and ia.applicantName = ? and ia.finalAuditStatus != 0 and ia.finalAuditResult != 0 and ia.ministryAuditStatus != 0 and ia.ministryAuditResult != 0 and ia.provinceAuditResult != 0 " +
						"and ia.universityAuditResult !=0 ";
				String hql3 = hqlSelect + hqlfromhwere3;
				List resultList3 = dao.query(hql3, projectName, year, applicantName);
				if(resultList3 != null && resultList3.size() == 1) {
					Object[] o = (Object[]) resultList3.get(0);
					return o;
				} 
				return null;//三级检索
			}
			return null;//二级检索为空
		}
		return null;
	}
	//放宽检索条件
	private Object[] toWidenGeneralProjectApplicationItem(String hqlSelect, String projectName, String applicantName, int year) {
		//一级，去空格查询
		String hqlfromwhere = "from GeneralApplication genApp left join genApp.subtype projectType left join genApp.researchType reType left join genApp.university u left join genApp.projectFee projectFee left join genApp.reviewGrade soreviewGread " +
				"where genApp.type = 'general' and projectType.name != '专项任务项目' and trim(genApp.name) = ? and genApp.year = ? and trim(genApp.applicantName) = ? "; //
		String hql = hqlSelect + hqlfromwhere;
		List resultList = dao.query(hql, projectName, year, applicantName);
		if(resultList != null && resultList.size() == 1) {
			Object[] o = (Object[]) resultList.get(0);
			return o;
		}else if(resultList != null && resultList.size() > 1) {
			//二级，根据 项目负责人，年份去检索
		
		}
		return null;
	}
	//放宽检索条件
	private Object[] toWidenInstpProjectApplicationItem(String hqlSelect, String projectName, String applicantName, int year) {
		//一级，去空格查询
		String hqlfromhwere = "from InstpApplication ia left join ia.subtype projectType left join ia.researchType reType left join ia.university u left join ia.institute institute left join ia.projectFee projectFee left join ia.reviewGrade soreviewGread " +
				"where ia.type = 'instp' and trim(ia.name) = ? and ia.year = ? and trim(ia.applicantName) = ? ";
		String hql = hqlSelect + hqlfromhwere;
		List resultList = dao.query(hql.toString(), year, applicantName);
		if(resultList != null && resultList.size() == 1) {
			Object[] o = (Object[]) resultList.get(0);
			return o;
		}else if(resultList != null && resultList.size() > 1) {
			//二级，根据 项目负责人，年份去检索
		
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//2015后（含2015）的一般项目数据封装为一个item
	private void toWorkGeneralProjectApplicationItem(Element records, Object[] o ) {

		////组装一个item
		Element itemElement = records.addElement("item");
		ProjectFee pFee = null;
		String[] str = new String[ o.length];
		for (int i = 0; i < o.length ; i++) {
			if (i == 19) {//项目经费对象
				 pFee = (ProjectFee) o[i];
				 continue;
			}
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
		
		List<Object[]> oArrList = getMemberListByAPPID("general", str[0]);//appID
		
		//负责人姓名全部同步，但是负责人信息只需要同步排在第一位
		String directorNames = str[2];
		String directorNameFirst = !(str[2].indexOf(";") > 0) ? str[2] : str[2].substring(0, str[2].indexOf(";"));
		//T_PERSON id 
		String applicantIDFirst = !(str[24].indexOf(";") > 0) ? str[24] : str[24].substring(0, str[24].indexOf(";"));
			
		GeneralMember directorMember = null;
		Person directorPerson = null;;//负责人的人员属性
		Academic dirAcademic = null;
//		
		String dirJob = null;//职位
		String dirTitle = null;//职称
		String dirBirthday = null;
		
		StringBuffer membInfo = new StringBuffer();//不包括项目负责人信息，姓名（高校）: 郭线庐(西安美术学院); 周靓(西安美术学院); 陈霞(西安美术学院)
		for (int i = 0; i < oArrList.size(); i++) {
			Object[] objects = oArrList.get(i);
			GeneralMember mem = (GeneralMember) objects[0];
			Person personInMember = (Person) objects[1];
			Academic academicInPerson = (Academic) objects[2];
			Agency universityInMember = (Agency) objects[3];//项目成员高校信息
			if (mem.getIsDirector() == 1) { //是负责人
				//处理负责人信息，将非首位的负责人信息采集到member其他成员中
				if (mem.getMemberName().equals(directorNameFirst)) {
					//获取项目负责人信息
					directorMember = mem;
					directorPerson = personInMember;
					dirAcademic = academicInPerson;
					
					dirJob = mem.getPosition();//职位
					dirTitle = mem.getSpecialistTitle();//职称
					dirBirthday = mem.getBirthday() == null ? "null" : (new SimpleDateFormat("yyyy-MM-dd")).format(mem.getBirthday());
					
					continue;
				}
				String otherDirector = mem.getMemberName();//姓名
				String uniName = universityInMember == null ? "; " : "(" + universityInMember.getName() + "); ";
				membInfo.append( otherDirector + uniName);
			} else { //不是负责人，即是项目成员
				String memberName = mem.getMemberName();
				String uniName = universityInMember == null ? "; " : "(" + universityInMember.getName() + "); ";
				membInfo.append( memberName + uniName);
			}
		}
		String memberAndUnivs = null;
		if (membInfo != null && membInfo.length() != 0) {
			memberAndUnivs = membInfo.substring(0, membInfo.length()-1);
		} else {
			memberAndUnivs = "null";
		}
		//type 
		itemElement.addElement("projectName").setText(str[1]);
		itemElement.addElement("id").setText(str[0]);
		//项目负责人多人情况，只取首位负责人信息
		itemElement.addElement("director").setText(directorNames);
		
		itemElement.addElement("universityCode").setText(str[3]);
		itemElement.addElement("universityName").setText(str[4]);
		itemElement.addElement("projectType").setText(str[5]);// 对应smas的projectType,general下面的项目子类
		itemElement.addElement("year").setText(str[6]);
		itemElement.addElement("applyDate").setText(str[7]);
		itemElement.addElement("planEndDate").setText(str[8]);
		itemElement.addElement("disciplineType").setText(str[9]);
		
		//smas的discipline类型：63044; 8101020; 81020
		//smdb的discipline类型：19065/应用心理学; 52099/计算机科学技术其他学科; 74010/普通语言学
		//要处理
		itemElement.addElement("discipline").setText(filterDisciplineType(str[10]));
		//
		itemElement.addElement("researchType").setText(str[11]);
		
		if (!str[12].equals("null") && !str[12].trim().equals("")) {
			itemElement.addElement("finalResultType").setText(
					(str[12] + (str[13].equals("null") ? "" : str[13]))
					);//最终成果形式拼接形成
		} else {
			itemElement.addElement("finalResultType").setText(str[13]
					);//最终成果形式拼接形成
		}
		itemElement.addElement("finalResultType").setText(
				(str[12].equals("null")||str[12].trim().equals("") ? str[13] : str[12] + "; " + str[13]));//最终成果形式拼接形成
		
		itemElement.addElement("productType").setText(str[12]);
		itemElement.addElement("productTypeOther").setText(str[13]);
		
		itemElement.addElement("applyFee").setText(str[14]);
		itemElement.addElement("otherFee").setText(str[15]);	
		itemElement.addElement("file").setText(str[16]);
		itemElement.addElement("note").setText(str[17]);
		itemElement.addElement("type").setText(str[18]);
		//此字段是教育部给的申请项目不符合申请条件的原因，对应的项目必然不通过项目申请		
//		itemElement.addElement("reason").setText(str[20]);
//		itemElement.addElement("firstAuditResult").setText(str[21]);
//		itemElement.addElement("firstAuditDate").setText(str[22]);
		
		// 高校类型
		if (str[23].equals("3")) {//
			itemElement.addElement("auditStatus").setText("学校审核通过");
		} else if (str[23].equals("4")) {//地方高校省级审核结果
			itemElement.addElement("auditStatus").setText("主管部门审核通过");
		} else {//其他
			itemElement.addElement("auditStatus").setText("null");
		}
		
		itemElement.addElement("SinossApplicationID").setText(str[25]);
		//divisionName 依托部门
		itemElement.addElement("department").setText(str[26]);
	
		itemElement.addElement("members").setText(memberAndUnivs);
		itemElement.addElement("job").setText(dirJob == null ? "null" : dirJob);
		itemElement.addElement("title").setText(dirTitle == null ? "null" : dirTitle);
		itemElement.addElement("birthday").setText(dirBirthday == null ? "null" : dirBirthday);
		
		itemElement.addElement("email").setText(directorPerson.getEmail() != null ? directorPerson.getEmail() : "null");
		itemElement.addElement("idcardType").setText(directorPerson.getIdcardType() != null ? directorPerson.getIdcardType() : "null");
		itemElement.addElement("idcardNumber").setText(directorPerson.getIdcardNumber() != null ? directorPerson.getIdcardNumber() : "null");
		itemElement.addElement("mobile").setText(directorPerson.getMobilePhone() != null ? directorPerson.getMobilePhone() : "null");
		itemElement.addElement("phone").setText(directorPerson.getOfficePhone() != null ? directorPerson.getOfficePhone() : "null");
		
		Map<String, String> addInfoMap = getPersonAddress(directorPerson);
		itemElement.addElement("address").setText(addInfoMap.get("officeAdd"));
		itemElement.addElement("postcode").setText(addInfoMap.get("officePost"));
		itemElement.addElement("gender").setText(directorPerson.getGender() != null ? directorPerson.getGender() : "null");
		
		itemElement.addElement("foreign").setText((dirAcademic != null && dirAcademic.getLanguage() != null) ? dirAcademic.getLanguage() : "null");
		itemElement.addElement("degree").setText((dirAcademic != null && dirAcademic.getLastDegree() != null) ? dirAcademic.getLastDegree() : "null");
		itemElement.addElement("education").setText((dirAcademic != null && dirAcademic.getLastEducation() != null) ? dirAcademic.getLastEducation() : "null");
		
		//封装经费对象
		toParseProjectFeeItem(itemElement, pFee);
		
	}
	//2015后（含2015）的一般项目数据封装为一个item
	private void toWorkInstpProjectApplicationItem(Element records, Object[] o ) {
	/*String hqlselect = " select ia.id, ia.name, ia.applicantName, institute.name, u.code, u.name, projectType.name, ia.year, ia.applicantSubmitDate, ia.planEndDate, " +
				" ia.discipline, ia.disciplineType, ia.productType, ia.productTypeOther, ia.file, ia.note, u.type, projectFee, ia.reason , ia.firstAuditResult , " +
				" ia.firstAuditDate, ia.applicantId, ia.type, ia.sinossId"; */
		Element itemElement = records.addElement("item");
		ProjectFee pFee = null;
		//转化数据类型
		String[] str = new String[o.length];
		for (int i = 0; i < o.length ; i++) {
			if (i == 17) {
				pFee = (ProjectFee) o[17];
				continue;
			}
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
		List<Object[]> oArrList = getMemberListByAPPID("instp", str[0]);//appID
		//负责人姓名全部同步，但是负责人信息只需要同步排在第一位
		String directorNames = str[2];
		String directorNameFirst = !(str[2].indexOf(";") > 0) ? str[2] : str[2].substring(0, str[2].indexOf(";"));
		//T_PERSON id 
		String applicantIDFirst = !(str[20].indexOf(";") > 0) ? str[20] : str[20].substring(0, str[20].indexOf(";"));
			
		InstpMember directorMember = null;
		Person directorPerson = null;;//负责人的人员属性
		Academic dirAcademic = null;
		
		String dirJob = null;//职位
		String dirTitle = null;//职称
		String dirBirthday = null;
		
		StringBuffer membInfo = new StringBuffer();//不包括项目负责人信息，姓名（高校）: 郭线庐(西安美术学院); 周靓(西安美术学院); 陈霞(西安美术学院)
		for (int i = 0; i < oArrList.size(); i++) {
			Object[] objects = oArrList.get(i);
			InstpMember mem = (InstpMember) objects[0];
			Person personInMember = (Person) objects[1];
			Academic academicInPerson = (Academic) objects[2];
			Agency universityInMember = (Agency) objects[3];//项目成员高校信息
			if (mem.getIsDirector() == 1) { //是负责人
				//处理负责人信息，将非首位的负责人信息采集到member其他成员中
				if (mem.getMemberName().equals(directorNameFirst)) {
					directorMember = mem;
					directorPerson = personInMember;
					dirAcademic = academicInPerson;
					
					dirJob = mem.getPosition();//职位
					dirTitle = mem.getSpecialistTitle();//职称
					dirBirthday = mem.getBirthday() == null ? "null" : (new SimpleDateFormat("yyyy-MM-dd")).format(mem.getBirthday());
					continue;
				}
				String otherDirector = mem.getMemberName();//姓名
				String uniName = universityInMember == null ? "; " : "(" + universityInMember.getName() + "); ";
				membInfo.append( otherDirector + uniName);
			} else { //不是负责人，即是项目成员
				String memberName = mem.getMemberName();
				String uniName = universityInMember == null ? "; " : "(" + universityInMember.getName() + "); ";
				membInfo.append( memberName + uniName);
			}
		}
		String memberAndUnivs = null;
		if (membInfo != null && membInfo.length() != 0) {
			memberAndUnivs = membInfo.substring(0, membInfo.length()-1);
		} else {
			memberAndUnivs = "null";
		}
		
		String disciplineDirection = dirAcademic.getResearchField();
		
		String dirUniversityName = directorMember.getUniversity() == null ? "null" : directorMember.getUniversity().getName();//负责人高校名称
		String dirDivisionName = directorMember.getDivisionName();//负责人所属部门

		
		itemElement.addElement("id").setText(str[0]);
		itemElement.addElement("projectName").setText(str[1]);
		//负责人姓名可能存在多人情况
		itemElement.addElement("director").setText(directorNames == null ? "null" : directorNames);
		
		
		//************************//
		itemElement.addElement("instituteName").setText(str[3] == null ? "null" : str[3]);
		itemElement.addElement("directorUniversity").setText(dirUniversityName == null ? "null" : dirUniversityName);
		itemElement.addElement("department").setText(dirDivisionName == null ? "null" : dirDivisionName);
		itemElement.addElement("disciplineDirection").setText(disciplineDirection == null ? "null" : disciplineDirection);
		//**********************//
		
		itemElement.addElement("universityCode").setText(str[4]);
		itemElement.addElement("universityName").setText(str[5]);
		
		itemElement.addElement("projectType").setText("基地项目");
		itemElement.addElement("year").setText(str[7]);
		itemElement.addElement("applyDate").setText(str[8]);
		itemElement.addElement("planEndDate").setText(str[9]);
		itemElement.addElement("discipline").setText(filterDisciplineType(str[10]));
		itemElement.addElement("disciplineType").setText(str[11]);
		
		if (!str[12].equals("null") && !str[12].trim().equals("")) {
			itemElement.addElement("finalResultType").setText(
					(str[12] + (str[13].equals("null") ? "" : str[13]))
					);//最终成果形式拼接形成
		} else {
			itemElement.addElement("finalResultType").setText(str[13]
					);//最终成果形式拼接形成
		}
		
		
		
		itemElement.addElement("finalResultType").setText(
				(str[12].equals("null")||str[12].trim().equals("") ? str[13] : str[12] + "; " + str[13]));//最终成果形式拼接形成
	
		
		itemElement.addElement("productType").setText(str[12]);
		itemElement.addElement("productTypeOther").setText(str[13]);
		
		
		
		//新增
		itemElement.addElement("file").setText(str[14]);
		itemElement.addElement("note").setText(str[15]);
		if (str[16].equals("3")) {//高校类型u.type
			itemElement.addElement("auditStatus").setText("学校审核通过");
		} else if (str[16].equals("4")) {//地方高校省级审核结果
			itemElement.addElement("auditStatus").setText("主管部门审核通过");
		} else {//其他
			itemElement.addElement("auditStatus").setText("null");
		}
		//itemElement.addElement("reason").setText(str[18]);
		itemElement.addElement("firstAuditResult").setText(str[18]);
		itemElement.addElement("firstAuditDate").setText(str[19]);
		itemElement.addElement("type").setText(str[21]);//type
		itemElement.addElement("SinossApplicationID").setText(str[22]);
			itemElement.addElement("researchType").setText(str[23]);
			itemElement.addElement("applyFee").setText(str[24]);
			itemElement.addElement("otherFee").setText(str[25]);
		
		
		itemElement.addElement("members").setText(memberAndUnivs);

		itemElement.addElement("job").setText(dirJob == null ? "null" : dirJob);
		itemElement.addElement("title").setText(dirTitle == null ? "null" : dirTitle);
		itemElement.addElement("birthday").setText(dirBirthday == null ? "null" : dirBirthday);
		

		itemElement.addElement("email").setText(directorPerson.getEmail() != null ? directorPerson.getEmail() : "null");
		itemElement.addElement("idcardType").setText(directorPerson.getIdcardType() != null ? directorPerson.getIdcardType() : "null");
		itemElement.addElement("idcardNumber").setText(directorPerson.getIdcardNumber() != null ? directorPerson.getIdcardNumber() : "null");
		itemElement.addElement("mobile").setText(directorPerson.getMobilePhone() != null ? directorPerson.getMobilePhone() : "null");
		itemElement.addElement("phone").setText(directorPerson.getOfficePhone() != null ? directorPerson.getOfficePhone() : "null");
		
		Map<String, String> addInfoMap = getPersonAddress(directorPerson);
		itemElement.addElement("address").setText(addInfoMap.get("officeAdd"));
		itemElement.addElement("postcode").setText(addInfoMap.get("officePost"));
		itemElement.addElement("gender").setText(directorPerson.getGender() != null ? directorPerson.getGender() : "null");
		
		itemElement.addElement("foreign").setText((dirAcademic != null && dirAcademic.getLanguage() != null) ? dirAcademic.getLanguage() : "null");
		itemElement.addElement("degree").setText((dirAcademic != null && dirAcademic.getLastDegree() != null) ? dirAcademic.getLastDegree() : "null");
		itemElement.addElement("education").setText((dirAcademic != null && dirAcademic.getLastEducation() != null) ? dirAcademic.getLastEducation() : "null");
		
		//封装经费对象
		toParseProjectFeeItem(itemElement, pFee);
	}
	
	
	
	
	//2015后（含2015）的一般项目数据封装为一个item
		private void toWorkInstpProjectApplicationItemOld(Element records, Object[] o ) {
		/*String hqlselect = " select ia.id, ia.name, ia.applicantName, institute.name, u.code, u.name, projectType.name, ia.year, ia.applicantSubmitDate, ia.planEndDate, " +
					" ia.discipline, ia.disciplineType, ia.productType, ia.productTypeOther, ia.file, ia.note, u.type, projectFee, ia.reason , ia.firstAuditResult , " +
					" ia.firstAuditDate, ia.applicantId, ia.type, ia.sinossId"; */

			ProjectFee pFee = null;
			//转化数据类型
			String[] str = new String[o.length];
			for (int i = 0; i < o.length ; i++) {
				if (i == 17) {
					pFee = (ProjectFee) o[17];
					continue;
				}
				if (o[i] instanceof String) {
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] instanceof Date) {
					str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}
			List<Object[]> oArrList = getMemberListByAPPID("instp", str[0]);//appID
			//负责人姓名全部同步，但是负责人信息只需要同步排在第一位
			String directorNames = str[2];
			String directorNameFirst = !(str[2].indexOf(";") > 0) ? str[2] : str[2].substring(0, str[2].indexOf(";"));
			//T_PERSON id 
			String applicantIDFirst = !(str[20].indexOf(";") > 0) ? str[20] : str[20].substring(0, str[20].indexOf(";"));
				
			InstpMember directorMember = null;
			Person directorPerson = null;;//负责人的人员属性
			Academic dirAcademic = null;
			
			
			StringBuffer membInfo = new StringBuffer();//不包括项目负责人信息，姓名（高校）: 郭线庐(西安美术学院); 周靓(西安美术学院); 陈霞(西安美术学院)
			for (int i = 0; i < oArrList.size(); i++) {
				Object[] objects = oArrList.get(i);
				InstpMember mem = (InstpMember) objects[0];
				Person personInMember = (Person) objects[1];
				Academic academicInPerson = (Academic) objects[2];
				Agency universityInMember = (Agency) objects[3];//项目成员高校信息
				if (mem.getIsDirector() == 1) { //是负责人
					//处理负责人信息，将非首位的负责人信息采集到member其他成员中
					if (mem.getMemberName().equals(directorNameFirst)) {
						directorMember = mem;
						directorPerson = personInMember;
						dirAcademic = academicInPerson;
						continue;
					}
					String otherDirector = mem.getMemberName();//姓名
					String uniName = universityInMember == null ? "; " : "(" + universityInMember.getName() + "); ";
					membInfo.append( otherDirector + uniName);
				} else { //不是负责人，即是项目成员
					String memberName = mem.getMemberName();
					String uniName = universityInMember == null ? "; " : "(" + universityInMember.getName() + "); ";
					membInfo.append( memberName + uniName);
				}
			}
			String memberAndUnivs = null;
			if (membInfo != null && membInfo.length() != 0) {
				memberAndUnivs = membInfo.substring(0, membInfo.length()-1);
			} else {
				memberAndUnivs = "null";
			}
			
			String dirJob = directorMember.getPosition();//职位
			String dirTitle = directorMember.getSpecialistTitle();//职称
			String dirBirthday = directorMember.getBirthday() == null ? "null" : (new SimpleDateFormat("yyyy-MM-dd")).format(directorMember.getBirthday());
			String disciplineDirection = dirAcademic.getResearchField();
			
			String dirUniversityName = directorMember.getUniversity() == null ? "null" : directorMember.getUniversity().getName();//负责人高校名称
			String dirDivisionName = directorMember.getDivisionName();//负责人所属部门

			Element itemElement = records.addElement("item");
			itemElement.addElement("id").setText(str[0]);
			itemElement.addElement("projectName").setText(str[1]);
			//负责人姓名可能存在多人情况
			itemElement.addElement("director").setText(directorNames == null ? "null" : directorNames);
			itemElement.addElement("instituteName").setText(str[3] == null ? "null" : str[3]);
			itemElement.addElement("directorUniversity").setText(dirUniversityName == null ? "null" : dirUniversityName);
			itemElement.addElement("directorDivisionName").setText(dirDivisionName == null ? "null" : dirDivisionName);
			itemElement.addElement("disciplineDirection").setText(disciplineDirection == null ? "null" : disciplineDirection);
			
			itemElement.addElement("universityCode").setText(str[4]);
			itemElement.addElement("universityName").setText(str[5]);
			
			itemElement.addElement("projectType").setText("基地项目");
			itemElement.addElement("year").setText(str[7]);
			itemElement.addElement("applyDate").setText(str[8]);
			itemElement.addElement("planEndDate").setText(str[9]);
			itemElement.addElement("discipline").setText(filterDisciplineType(str[10]));
			itemElement.addElement("disciplineType").setText(str[11]);
			
			itemElement.addElement("finalResultType").setText(
					(str[12].equals("null")||str[12].trim().equals("") ? str[13] : str[12] + "; " + str[13]));//最终成果形式拼接形成
			itemElement.addElement("productType").setText(str[12]);
			itemElement.addElement("productTypeOther").setText(str[13]);
			
			//新增
			itemElement.addElement("file").setText(str[14]);
			itemElement.addElement("note").setText(str[15]);
			if (str[16].equals("3")) {//高校类型u.type
				itemElement.addElement("auditStatus").setText("学校审核通过");
			} else if (str[16].equals("4")) {//地方高校省级审核结果
				itemElement.addElement("auditStatus").setText("主管部门审核通过");
			} else {//其他
				itemElement.addElement("auditStatus").setText("null");
			}
			//itemElement.addElement("reason").setText(str[18]);
			itemElement.addElement("firstAuditResult").setText(str[18]);
			itemElement.addElement("firstAuditDate").setText(str[19]);
			itemElement.addElement("type").setText(str[21]);//type
			itemElement.addElement("SinossApplicationID").setText(str[22]);
			
			itemElement.addElement("members").setText(memberAndUnivs);

			itemElement.addElement("job").setText(dirJob == null ? "null" : dirJob);
			itemElement.addElement("title").setText(dirTitle == null ? "null" : dirTitle);
			itemElement.addElement("birthday").setText(dirBirthday == null ? "null" : dirBirthday);
			

			itemElement.addElement("email").setText(directorPerson.getEmail() != null ? directorPerson.getEmail() : "null");
			itemElement.addElement("idcardType").setText(directorPerson.getIdcardType() != null ? directorPerson.getIdcardType() : "null");
			itemElement.addElement("idcardNumber").setText(directorPerson.getIdcardNumber() != null ? directorPerson.getIdcardNumber() : "null");
			itemElement.addElement("mobile").setText(directorPerson.getMobilePhone() != null ? directorPerson.getMobilePhone() : "null");
			itemElement.addElement("phone").setText(directorPerson.getOfficePhone() != null ? directorPerson.getOfficePhone() : "null");
			
			Map<String, String> addInfoMap = getPersonAddress(directorPerson);
			itemElement.addElement("address").setText(addInfoMap.get("officeAdd"));
			itemElement.addElement("postcode").setText(addInfoMap.get("officePost"));
			itemElement.addElement("gender").setText(directorPerson.getGender() != null ? directorPerson.getGender() : "null");
			
			itemElement.addElement("foreign").setText((dirAcademic != null && dirAcademic.getLanguage() != null) ? dirAcademic.getLanguage() : "null");
			itemElement.addElement("degree").setText((dirAcademic != null && dirAcademic.getLastDegree() != null) ? dirAcademic.getLastDegree() : "null");
			itemElement.addElement("education").setText((dirAcademic != null && dirAcademic.getLastEducation() != null) ? dirAcademic.getLastEducation() : "null");
			
			//封装经费对象
			toParseProjectFeeItem(itemElement, pFee);
		}
		
	
	
	
	
	//申请数据
	private void toParseGeneralProjectApplicationItem(Element records, Object[] o ) {
		////组装一个item
		Element itemElement = records.addElement("item");
		String[] str = new String[36];
		for (int i = 0; i < o.length-1 ; i++) {
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
		
		ProjectFee pFee = (ProjectFee) o[o.length-1];
		List<Object[]> oArrList = getMemberListByAPPID("general", str[0]);//appID
		//取出负责人信息
		StringBuffer membInfo = new StringBuffer();//不包括项目负责人信息，姓名（高校），郭线庐(西安美术学院); 周靓(西安美术学院); 陈霞(西安美术学院)
		String directorName = null ,dirJob = null, dirBirthday = null, dirTitle = null;//项目负责人信息
		Person directorPerson = null;//负责人的人员属性
		Academic dirAcademic = null;//负责人的学术属性
		
		for (int i = 0; i < oArrList.size(); i++) {
			 Object[] objects = oArrList.get(i);
			 GeneralMember member = (GeneralMember) objects[0];
			 Person personInMember = (Person) objects[1];
			 Academic academicInPerson = (Academic) objects[2];
			 Agency universityInMember = (Agency) objects[3];//项目成员高校信息
			if (member.getIsDirector() == 1) { //是负责人
				directorName = member.getMemberName();//姓名
				dirJob = member.getPosition();//职位
				dirTitle = member.getSpecialistTitle();//职称
				dirBirthday = personInMember.getBirthday() == null ? "" : (new SimpleDateFormat("yyyy-MM-dd")).format(personInMember.getBirthday());
				directorPerson = personInMember;//负责人的人员属性
				dirAcademic = academicInPerson;
			} else { //不是负责人，即是项目成员
				String memberName = member.getMemberName();
				String uniName = universityInMember == null ? ";" : "(" + universityInMember.getName() + ");";
				membInfo.append( memberName + uniName);
			}
		}
		String memberAndUnivs = null;
		if (membInfo != null && membInfo.length() != 0) {
			memberAndUnivs = membInfo.substring(0, membInfo.length()-1);
		} else {
			memberAndUnivs = "null";
		}
		itemElement.addElement("id").setText(str[0]);
		itemElement.addElement("type").setText(str[1]);
		itemElement.addElement("discipline").setText(filterDisciplineType(str[2]));
		itemElement.addElement("disciplineType").setText(str[3]);
		itemElement.addElement("projectName").setText(str[4]);
		itemElement.addElement("year").setText(str[5]);
		itemElement.addElement("researchType").setText(str[6]);
		itemElement.addElement("projectType").setText(str[7]);// 对应smas的projectType
		itemElement.addElement("isReviewable").setText(str[8]);
		itemElement.addElement("applyDate").setText(str[9]);
		
		itemElement.addElement("planEndDate").setText(str[10]);
		itemElement.addElement("file").setText(str[11]);
		itemElement.addElement("productType").setText(str[12]);
		itemElement.addElement("productTypeOther").setText(str[13]);
		
		itemElement.addElement("applyFee").setText(str[14]);
		itemElement.addElement("otherFee").setText(str[15]);
		//divisionName 依托部门
		itemElement.addElement("department").setText(str[16]);
		
		itemElement.addElement("universityName").setText(str[19]);
		itemElement.addElement("universityCode").setText(str[20]);
		itemElement.addElement("universityType").setText(str[21]);
		if (str[21].equals("3") && str[17].equals("2")) {//部署高校校级审核结果
			itemElement.addElement("auditStatus").setText("学校审核通过");
		} else if (str[21].equals("4") && str[18].equals("2")) {//地方高校省级审核结果
			itemElement.addElement("auditStatus").setText("主管部门审核通过");
		} else {//其他
			itemElement.addElement("auditStatus").setText("null");
		}
		//新增
		itemElement.addElement("note").setText(str[22]);
		itemElement.addElement("finalAuditResult").setText(str[23]);
		itemElement.addElement("finalAuditorName").setText(str[24]);
		itemElement.addElement("finalAuditDate").setText(str[25]);
		itemElement.addElement("finalAuditOpinion").setText(str[26]);
		itemElement.addElement("finalAuditOpinionFeedback").setText(str[27]);
		itemElement.addElement("reviewDate").setText(str[28]);
		itemElement.addElement("reviewTotalScore").setText(str[29]);
		itemElement.addElement("reviewAverageScore").setText(str[30]);
		
		itemElement.addElement("reviewWay").setText(str[31]);
		itemElement.addElement("reviewResult").setText(str[32]);
		itemElement.addElement("reviewOpinion").setText(str[33]);
		itemElement.addElement("reviewGread").setText(str[34]);
		itemElement.addElement("reviewOpinionQualitative").setText(str[35]);
		//
		
		itemElement.addElement("members").setText(memberAndUnivs);
		itemElement.addElement("director").setText(directorName == null ? "null" : directorName);
		itemElement.addElement("job").setText(dirJob == null ? "null" : dirJob);
		itemElement.addElement("title").setText(dirTitle == null ? "null" : dirTitle);
		itemElement.addElement("birthday").setText(dirBirthday == null ? "null" : dirBirthday);
		itemElement.addElement("email").setText(directorPerson.getEmail() != null ? directorPerson.getEmail() : "null");
		itemElement.addElement("idcardType").setText(directorPerson.getIdcardType() != null ? directorPerson.getIdcardType() : "null");
		itemElement.addElement("idcardNumber").setText(directorPerson.getIdcardNumber() != null ? directorPerson.getIdcardNumber() : "null");
		itemElement.addElement("mobile").setText(directorPerson.getMobilePhone() != null ? directorPerson.getMobilePhone() : "null");
		itemElement.addElement("phone").setText(directorPerson.getOfficePhone() != null ? directorPerson.getOfficePhone() : "null");
		Map<String, String> addInfoMap = getPersonAddress(directorPerson);
		itemElement.addElement("address").setText(addInfoMap.get("officeAdd"));
		itemElement.addElement("postcode").setText(addInfoMap.get("officeAdd"));
		itemElement.addElement("gender").setText(directorPerson.getGender() != null ? directorPerson.getGender() : "null");
		
		itemElement.addElement("foreign").setText((dirAcademic != null && dirAcademic.getLanguage() != null) ? dirAcademic.getLanguage() : "null");
		itemElement.addElement("degree").setText((dirAcademic != null && dirAcademic.getLastDegree() != null) ? dirAcademic.getLastDegree() : "null");
		itemElement.addElement("education").setText((dirAcademic != null && dirAcademic.getLastEducation() != null) ? dirAcademic.getLastEducation() : "null");
		
		//封装经费对象
		toParseProjectFeeItem(itemElement, pFee);
		
	}
	//申请数据
	private void toParseInstpProjectApplicationItem(Element records, Object[] o ) {
		//转化数据类型
		String[] str = new String[35];
		for (int i = 0; i < o.length - 1; i++) {
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
		ProjectFee pFee = (ProjectFee) o[o.length-1];
		List<Object[]> oArrList = getMemberListByAPPID("instp", str[0]);//appID
		//取出负责人信息
		StringBuffer membInfo = new StringBuffer();//不包括项目负责人信息，姓名（高校），郭线庐(西安美术学院); 周靓(西安美术学院); 陈霞(西安美术学院)
		String directorName = null ,dirJob = null, dirBirthday = null, dirTitle = null, 
				dirUniversityName = null, dirDivisionName = null, dirDisciplineDirection = null;//项目负责人信息
		for (int i = 0; i < oArrList.size(); i++) {
			 Object[] objects = oArrList.get(i);
			 InstpMember member = (InstpMember) objects[0];
			 Person personInMember = (Person) objects[1];
			 Academic academicInPerson = (Academic) objects[2];
			 Agency universityInMember = (Agency) objects[3];//项目成员高校信息
			if (member.getIsDirector() == 1) { //是负责人
				directorName = member.getMemberName();//姓名
				dirJob = member.getPosition();//职位
				dirTitle = member.getSpecialistTitle();//职称
				dirBirthday = personInMember.getBirthday() == null ? "" : (new SimpleDateFormat("yyyy-MM-dd")).format(personInMember.getBirthday());
				dirUniversityName = universityInMember == null ? "" : universityInMember.getName();
				dirDivisionName = member.getDivisionName();
				dirDisciplineDirection = academicInPerson == null ? "null" :academicInPerson.getDiscipline();
			} else { //不是负责人，即是项目成员
				String memberName = member.getMemberName();
				String uniName = universityInMember == null ? ";" : "(" + universityInMember.getName() + ");";
				membInfo.append( memberName + uniName);
			}
		}
		String memberAndUnivs = null;
		if (membInfo != null && membInfo.length() != 0) {
			memberAndUnivs = membInfo.substring(0, membInfo.length()-1);
		} else {
			memberAndUnivs = "null";
		}
		
		////组装一个item
		Element itemElement = records.addElement("item");
		
		itemElement.addElement("id").setText(str[0]);
		itemElement.addElement("type").setText(str[1]);
		itemElement.addElement("discipline").setText(filterDisciplineType(str[2]));
		itemElement.addElement("disciplineType").setText(str[3]);
		itemElement.addElement("projectName").setText(str[4]);
		itemElement.addElement("year").setText(str[5]);
		itemElement.addElement("projectType").setText(str[6]);
		itemElement.addElement("researchType").setText(str[7]);
		itemElement.addElement("isReviewable").setText(str[8]);
		itemElement.addElement("applyDate").setText(str[9]);
		
		itemElement.addElement("planEndDate").setText(str[10]);
		itemElement.addElement("file").setText(str[11]);
		itemElement.addElement("productType").setText(str[12]);
		itemElement.addElement("productTypeOther").setText(str[13]);
		itemElement.addElement("divisionName").setText(str[14]);
		//divisionName 依托部门
		itemElement.addElement("universityName").setText(str[17]);
		itemElement.addElement("universityCode").setText(str[18]);
		itemElement.addElement("universityType").setText(str[19]);
		if (str[19].equals("3") && str[15].equals("2")) {//部署高校校级审核结果
			itemElement.addElement("auditStatus").setText("学校审核通过");
		} else if (str[19].equals("4") && str[16].equals("2")) {//地方高校省级审核结果
			itemElement.addElement("auditStatus").setText("主管部门审核通过");
		} else { //其他
			itemElement.addElement("auditStatus").setText("null");
		}
		itemElement.addElement("instituteName").setText(str[20]);
		
		//新增
		itemElement.addElement("note").setText(str[21]);
		itemElement.addElement("finalAuditResult").setText(str[22]);
		itemElement.addElement("finalAuditorName").setText(str[23]);
		itemElement.addElement("finalAuditDate").setText(str[24]);
		itemElement.addElement("finalAuditOpinion").setText(str[25]);
		itemElement.addElement("finalAuditOpinionFeedback").setText(str[26]);
		itemElement.addElement("reviewDate").setText(str[27]);
		itemElement.addElement("reviewTotalScore").setText(str[28]);
		itemElement.addElement("reviewAverageScore").setText(str[29]);
		itemElement.addElement("reviewWay").setText(str[30]);
		
		itemElement.addElement("reviewResult").setText(str[31]);
		itemElement.addElement("reviewOpinion").setText(str[32]);
		itemElement.addElement("reviewGread").setText(str[33]);
		itemElement.addElement("reviewOpinionQualitative").setText(str[34]);
		//
		itemElement.addElement("members").setText(memberAndUnivs);
		itemElement.addElement("director").setText(directorName == null ? "null" : directorName);
		itemElement.addElement("job").setText(dirJob == null ? "null" : dirJob);
		itemElement.addElement("title").setText(dirTitle == null ? "null" : dirTitle);
		itemElement.addElement("birthday").setText(dirBirthday == null ? "null" : dirBirthday);
		
		itemElement.addElement("directorUniversity").setText(dirUniversityName == null ? "null" : dirUniversityName);
		itemElement.addElement("directorDivisionName").setText(dirDivisionName == null ? "null" : dirDivisionName);
		itemElement.addElement("disciplineDirection").setText(dirDisciplineDirection == null ? "null" : dirDisciplineDirection);
		
		//封装经费对象
		toParseProjectFeeItem(itemElement, pFee);
		
	}
	//立项数据
	private void toParseProjectGrantedItem(Element records, Object[] o ) {
		Element itemElement = records.addElement("item");
		String[] str = new String[30];//共31个数据，最后一个为经费对象
		for (int i = 0; i < o.length - 1 ; i++) {
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}	
		ProjectFee pFee = (ProjectFee) o[o.length-1];
		
		//组装一个item
		Element idElement = itemElement.addElement("id");
		idElement.setText(str[0]);
		Element nameElement = itemElement.addElement("name");
		nameElement.setText(str[1]);
		Element projectTypeElement = itemElement.addElement("projectType");
		projectTypeElement.setText(str[2]);
		Element numberElement = itemElement.addElement("number");
		numberElement.setText(str[3]);
		Element statusElement = itemElement.addElement("status");
		statusElement.setText(str[4]);
		Element agencyNameElement = itemElement.addElement("agencyName");
		agencyNameElement.setText(str[5]);
		Element applicantNameElement = itemElement.addElement("applicantName");
		applicantNameElement.setText(str[6]);
		Element applicantSubmitDateElement = itemElement.addElement("applicantSubmitDate");
		applicantSubmitDateElement.setText(str[7]);
		Element applicationIdElement = itemElement.addElement("applicationId");
		
		applicationIdElement.setText(str[8]);
		Element approveDateElement = itemElement.addElement("approveDate");
		approveDateElement.setText(str[9]);
		Element approveFeeElement = itemElement.addElement("approveFee");
		approveFeeElement.setText(str[10]);
		Element auditTypeElement = itemElement.addElement("auditType");
		auditTypeElement.setText(str[11]);
		Element divisionName = itemElement.addElement("divisionName");
		divisionName.setText(str[12]);
		Element endStopWithdrawDate = itemElement.addElement("endStopWithdrawDate");
		endStopWithdrawDate.setText(str[13]);
		Element endStopWithdrawOpinion = itemElement.addElement("endStopWithdrawOpinion");
		endStopWithdrawOpinion.setText(str[14]);
		Element endStopWithdrawPerson = itemElement.addElement("endStopWithdrawPerson");
		endStopWithdrawPerson.setText(str[15]);
		Element file = itemElement.addElement("file");
		file.setText(str[16]);
		Element finalAuditDate = itemElement.addElement("finalAuditDate");
		finalAuditDate.setText(str[17]);
		Element finalAuditOpinion = itemElement.addElement("finalAuditOpinion");
		finalAuditOpinion.setText(str[18]);
		Element finalAuditOpinionFeedback = itemElement.addElement("finalAuditOpinionFeedback");
		finalAuditOpinionFeedback.setText(str[19]);
		Element finalAuditResult = itemElement.addElement("finalAuditResult");
		finalAuditResult.setText(str[20]);
		Element  finalAuditorName = itemElement.addElement("finalAuditorName");
		finalAuditorName.setText(str[21]);
		Element importAuditDate = itemElement.addElement("importAuditDate");
		importAuditDate.setText(str[22]);
		Element endStopWithdrawOpinionFeedback = itemElement.addElement("endStopWithdrawOpinionFeedback");
		endStopWithdrawOpinionFeedback.setText(str[23]);
		Element planEndDate = itemElement.addElement("planEndDate");
		planEndDate.setText(str[24]);
		Element productType = itemElement.addElement("productType");
		productType.setText(str[25]);
		Element productTypeOther = itemElement.addElement("productTypeOther");
		productTypeOther.setText(str[26]);
		Element subtypeName = itemElement.addElement("subtypeName");
		subtypeName.setText(str[27]);
		Element UniversityName = itemElement.addElement("UniversityName");
		UniversityName.setText(str[28]);
		Element UniversityCode = itemElement.addElement("UniversityCode");
		UniversityCode.setText(str[29]);
		Element researchType = itemElement.addElement("researchTypeName");
		String researchTypeName = getGrantedProjectResearchType(str[8]);
		researchType.setText(researchTypeName == null ? "null" : researchTypeName);
		Element projectFeeItem = itemElement.addElement("projectFeeItem");
		
		
		//**************测试*******//
		
//		String sql = "select pa from ProjectApplication pa where pa.id = '"+str[8]+"' and pa.year=2015";
//		long Size = dao.query(sql.toString()).size();
//		if(Size>0)
//		System.out.println("*********************"+Size+++"*************"+str[8]);
		
		
		//封装经费对象
		if (null != pFee) {
			Element ID = projectFeeItem.addElement("projectFeeID");
			ID.setText(pFee.id);
			Element bookFee = projectFeeItem.addElement("bookFee");
			bookFee.setText(pFee.getBookFee() == null ? "null" : pFee.getBookFee().toString());
			Element bookNote = projectFeeItem.addElement("bookNote");
			bookNote.setText(pFee.getBookNote() == null ? "null" : pFee.getBookNote());
			Element dataFee = projectFeeItem.addElement("dataFee");
			dataFee.setText(pFee.getDataFee() == null ? "null" : pFee.getDataFee().toString());
			Element dataNote = projectFeeItem.addElement("dataNote");
			dataNote.setText(pFee.getDataNote() == null ? "null" : pFee.getDataNote());
			Element travelFee = projectFeeItem.addElement("travelFee");
			travelFee.setText(pFee.getTravelFee() == null ? "null" : pFee.getTravelFee().toString());
			Element travelNote = projectFeeItem.addElement("travelNote");
			travelNote.setText(pFee.getTravelNote() == null ? "null" : pFee.getTravelNote());
			Element deviceFee = projectFeeItem.addElement("deviceFee");
			deviceFee.setText(pFee.getDeviceFee() == null ? "null" : pFee.getDeviceFee().toString());
			Element deviceNote = projectFeeItem.addElement("deviceNote");
			deviceNote.setText(pFee.getDeviceNote() == null ? "null" : pFee.getDeviceNote());
			Element conferenceFee = projectFeeItem.addElement("conferenceFee");
			conferenceFee.setText(pFee.getConferenceFee() == null ? "null" : pFee.getConferenceFee().toString());
			Element conferenceNote = projectFeeItem.addElement("conferenceNote");
			conferenceNote.setText(pFee.getConferenceNote() == null ? "null" : pFee.getConferenceNote());
			Element consultationFee = projectFeeItem.addElement("consultationFee");
			consultationFee.setText(pFee.getConsultationFee() == null ? "null" : pFee.getConsultationFee().toString());
			Element consultationNote = projectFeeItem.addElement("consultationNote");
			consultationNote.setText(pFee.getConsultationNote() == null ? "null" : pFee.getConsultationNote());
			Element laborFee = projectFeeItem.addElement("laborFee");
			laborFee.setText(pFee.getLaborFee() == null ? "null" : pFee.getLaborFee().toString());
			Element laborNote = projectFeeItem.addElement("laborNote");
			laborNote.setText(pFee.getLaborNote() == null ? "null" : pFee.getLaborNote());
			Element printFee = projectFeeItem.addElement("printFee");
			printFee.setText(pFee.getPrintFee() == null ? "null" : pFee.getPrintFee().toString());
			Element printNote = projectFeeItem.addElement("printNote");
			printNote.setText(pFee.getPrintNote()  == null ? "null" : pFee.getPrintNote());
			Element internationalFee = projectFeeItem.addElement("internationalFee");
			internationalFee.setText(pFee.getInternationalFee() == null ? "null" : pFee.getInternationalFee().toString());
			Element internationalNote = projectFeeItem.addElement("internationalNote");
			internationalNote.setText(pFee.getInternationalNote() == null ? "null" : pFee.getInternationalNote());
			Element indirectFee = projectFeeItem.addElement("indirectFee");
			indirectFee.setText(pFee.getIndirectFee() == null ? "null" : pFee.getIndirectFee().toString());
			Element indirectNote = projectFeeItem.addElement("indirectNote");
			indirectNote.setText(pFee.getIndirectNote()  == null ? "null" : pFee.getIndirectNote());
			Element otherFee = projectFeeItem.addElement("otherFee");
			otherFee.setText(pFee.getOtherFee() == null ? "null" : pFee.getOtherFee().toString());
			Element otherNote = projectFeeItem.addElement("otherNote");
			otherNote.setText(pFee.getOtherNote()  == null ? "null" : pFee.getOtherNote());
			Element totalFee = projectFeeItem.addElement("totalFee");
			totalFee.setText(pFee.getTotalFee() == null ? "null" : pFee.getTotalFee().toString());
			Element type = projectFeeItem.addElement("type");
			type.setText(pFee.getType() == null ? "null" : pFee.getType().toString());
			Element feeNote = projectFeeItem.addElement("feeNote");
			feeNote.setText(pFee.getFeeNote() == null ? "null" : pFee.getFeeNote());
			Element fundedFee = projectFeeItem.addElement("fundedFee");
			fundedFee.setText(pFee.getFundedFee() == null ? "null" : pFee.getFundedFee().toString());
		} else {
			projectFeeItem.setText("null");
		}
	}
	//变更数据
	private void toParseProjectVariationItem(Element records, Object[] o ) {
//		String hqlSelect = "select pv.id, granted.id, pv.file, pv.applicantSubmitDate, pv.variationReason, pv.changeMember, granted.applicationId, pv.oldMemberGroupNumber, pv.newMemberGroupNumber, pv.changeAgency, pv.oldAgencyName || '; ' || pv.oldDivisionName, pv.newAgencyName || '; ' || pv.newDivisionName, " +// 0-11
//				"pv.changeProductType, pv.oldProductType, pv.oldProductTypeOther, pv.newProductType, pv.newProductTypeOther, pv.changeName, pv.newName, pv.oldName, pv.changeContent, pv.postponement, " +//10
//				"pv.oldOnceDate, pv.newOnceDate, pv.stop, pv.withdraw, pv.other, pv.otherInfo, pv.postponementPlanFile, pv.finalAuditResult, pv.finalAuditResultDetail, pv.finalAuditorName, pv.finalAuditDate, " +//11
//				"pv.finalAuditOpinion, pv.finalAuditOpinionFeedback, pv.projectType, pv.universityAuditResultDetail, pv.provinceAuditResultDetail, pv.changeFee, newFee, oldFee ";//37  
//
		//组装一个item
		Element itemElement = records.addElement("item");
		//转化数据类型
		String[] str = new String[39];//
		for (int i = 0; i < o.length-2 ; i++) {
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
		//转化数据类型
		ProjectFee newFee = (ProjectFee) o[o.length-2];
		ProjectFee oldFee = (ProjectFee) o[o.length-1];
		
		
		itemElement.addElement("id").setText(str[0]);//projectVariationID
		itemElement.addElement("grantedID").setText(str[1]);
		itemElement.addElement("file").setText(str[2]);
		itemElement.addElement("applicantSubmitDate").setText(str[3]);
		itemElement.addElement("variationReason").setText(str[4]);
		
		String applicationID = str[6];
		if (str[5] != "null" && str[7] != "null" && str[8] != "null") {
			int oldMemberGroupNumber = Integer.parseInt(str[7]);
			int newMemberGroupNumber = Integer.parseInt(str[8]);
			itemElement.addElement("changeMember").setText(str[5]);
			Map memInfos = getChangeMemberInfo(applicationID, newMemberGroupNumber, oldMemberGroupNumber);
			itemElement.addElement("oldMembers").setText((String) memInfos.get("oldMembers"));
			itemElement.addElement("newMembers").setText((String) memInfos.get("newMembers"));
		} else {
			itemElement.addElement("changeMember").setText("null");
			itemElement.addElement("oldMembers").setText("null");
			itemElement.addElement("newMembers").setText("null");
		}
		itemElement.addElement("changeAgency").setText(str[9]);
		itemElement.addElement("oldAgencyName").setText(str[10].equals("; ") ? "null" : str[10]);
		itemElement.addElement("newAgencyName").setText(str[11].equals("; ") ? "null" : str[11]);
		
		itemElement.addElement("changeProductType").setText(str[12]);
		itemElement.addElement("oldProductType").setText(str[13]);
		itemElement.addElement("oldProductTypeOther").setText(str[14]);
		itemElement.addElement("newProductType").setText(str[15]);
		itemElement.addElement("newProductTypeOther").setText(str[16]);
		itemElement.addElement("changeName").setText(str[17]);
		itemElement.addElement("newName").setText(str[18]);
		itemElement.addElement("oldName").setText(str[19]);
		itemElement.addElement("changeContent").setText(str[20]);
		itemElement.addElement("postponement").setText(str[21]);
		
		itemElement.addElement("oldOnceDate").setText(str[22]);
		itemElement.addElement("newOnceDate").setText(str[23]);
		itemElement.addElement("stop").setText(str[24]);
		itemElement.addElement("withdraw").setText(str[25]);
		itemElement.addElement("other").setText(str[26]);
		itemElement.addElement("otherInfo").setText(str[27]);
		itemElement.addElement("postponementPlanFile").setText(str[28]);
		itemElement.addElement("finalAuditResult").setText(str[29]);
		itemElement.addElement("finalAuditResultDetail").setText(str[30]);
		itemElement.addElement("finalAuditorName").setText(str[31]);
		itemElement.addElement("finalAuditDate").setText(str[32]);
		
		itemElement.addElement("finalAuditOpinion").setText(str[33]);
		itemElement.addElement("finalAuditOpinionFeedback").setText(str[34]);
		itemElement.addElement("type").setText(str[35]);
		
		itemElement.addElement("universityAuditResultDetail").setText(str[36]);
		itemElement.addElement("provinceAuditResultDetail").setText(str[37]);
		
		itemElement.addElement("changeFee").setText(str[38]);
		
		Element oldFeeItem = itemElement.addElement("oldFeeItem");
		Element newFeeItem = itemElement.addElement("newFeeItem");
		
		if (null != oldFee) {
			oldFeeItem.addElement("projectFeeID").setText(newFee.id);
			oldFeeItem.addElement("bookFee").setText(oldFee.getBookFee() == null ? "null" : oldFee.getBookFee().toString());
			oldFeeItem.addElement("bookNote").setText(oldFee.getBookNote() == null ? "null" : oldFee.getBookNote());
			oldFeeItem.addElement("dataFee").setText(oldFee.getDataFee() == null ? "null" : oldFee.getDataFee().toString());
			oldFeeItem.addElement("dataNote").setText(oldFee.getDataNote() == null ? "null" : oldFee.getDataNote());
			oldFeeItem.addElement("travelFee").setText(oldFee.getTravelFee() == null ? "null" : oldFee.getTravelFee().toString());
			oldFeeItem.addElement("travelNote").setText(oldFee.getTravelNote() == null ? "null" : oldFee.getTravelNote());
			oldFeeItem.addElement("deviceFee").setText(oldFee.getDeviceFee() == null ? "null" : oldFee.getDeviceFee().toString());
			oldFeeItem.addElement("deviceNote").setText(oldFee.getDeviceNote() == null ? "null" : oldFee.getDeviceNote());
			oldFeeItem.addElement("conferenceFee").setText(oldFee.getConferenceFee() == null ? "null" : oldFee.getConferenceFee().toString());
			oldFeeItem.addElement("conferenceNote").setText(oldFee.getConferenceNote() == null ? "null" : oldFee.getConferenceNote());
			oldFeeItem.addElement("consultationFee").setText(oldFee.getConsultationFee() == null ? "null" : oldFee.getConsultationFee().toString());
			oldFeeItem.addElement("consultationNote").setText(oldFee.getConsultationNote() == null ? "null" : oldFee.getConsultationNote());
			oldFeeItem.addElement("laborFee").setText(oldFee.getLaborFee() == null ? "null" : oldFee.getLaborFee().toString());
			oldFeeItem.addElement("laborNote").setText(oldFee.getLaborNote() == null ? "null" : oldFee.getLaborNote());
			oldFeeItem.addElement("printFee").setText(oldFee.getPrintFee() == null ? "null" : oldFee.getPrintFee().toString());
			oldFeeItem.addElement("printNote").setText(oldFee.getPrintNote()  == null ? "null" : oldFee.getPrintNote());
			oldFeeItem.addElement("internationalFee").setText(oldFee.getInternationalFee() == null ? "null" : oldFee.getInternationalFee().toString());
			oldFeeItem.addElement("internationalNote").setText(oldFee.getInternationalNote() == null ? "null" : oldFee.getInternationalNote());
			oldFeeItem.addElement("indirectFee").setText(oldFee.getIndirectFee() == null ? "null" : oldFee.getIndirectFee().toString());
			oldFeeItem.addElement("indirectNote").setText(oldFee.getIndirectNote()  == null ? "null" : oldFee.getIndirectNote());
			oldFeeItem.addElement("otherFee").setText(oldFee.getOtherFee() == null ? "null" : oldFee.getOtherFee().toString());
			oldFeeItem.addElement("otherNote").setText(oldFee.getOtherNote()  == null ? "null" : oldFee.getOtherNote());
			oldFeeItem.addElement("totalFee").setText(oldFee.getTotalFee() == null ? "null" : oldFee.getTotalFee().toString());
			oldFeeItem.addElement("type").setText(oldFee.getType() == null ? "null" : oldFee.getType().toString());
			oldFeeItem.addElement("feeNote").setText(oldFee.getFeeNote() == null ? "null" : oldFee.getFeeNote());
			oldFeeItem.addElement("fundedFee").setText(oldFee.getFundedFee() == null ? "null" : oldFee.getFundedFee().toString());
		} else {
			oldFeeItem.setText("null");
		}
		if (null != newFee) {
			newFeeItem.addElement("projectFeeID").setText(newFee.id);
			newFeeItem.addElement("bookFee").setText(newFee.getBookFee() == null ? "null" : newFee.getBookFee().toString());
			newFeeItem.addElement("bookNote").setText(newFee.getBookNote() == null ? "null" : newFee.getBookNote());
			newFeeItem.addElement("dataFee").setText(newFee.getDataFee() == null ? "null" : newFee.getDataFee().toString());
			newFeeItem.addElement("dataNote").setText(newFee.getDataNote() == null ? "null" : newFee.getDataNote());
			newFeeItem.addElement("travelFee").setText(newFee.getTravelFee() == null ? "null" : newFee.getTravelFee().toString());
			newFeeItem.addElement("travelNote").setText(newFee.getTravelNote() == null ? "null" : newFee.getTravelNote());
			newFeeItem.addElement("deviceFee").setText(newFee.getDeviceFee() == null ? "null" : newFee.getDeviceFee().toString());
			newFeeItem.addElement("deviceNote").setText(newFee.getDeviceNote() == null ? "null" : newFee.getDeviceNote());
			newFeeItem.addElement("conferenceFee").setText(newFee.getConferenceFee() == null ? "null" : newFee.getConferenceFee().toString());
			newFeeItem.addElement("conferenceNote").setText(newFee.getConferenceNote() == null ? "null" : newFee.getConferenceNote());
			newFeeItem.addElement("consultationFee").setText(newFee.getConsultationFee() == null ? "null" : newFee.getConsultationFee().toString());
			newFeeItem.addElement("consultationNote").setText(newFee.getConsultationNote() == null ? "null" : newFee.getConsultationNote());
			newFeeItem.addElement("laborFee").setText(newFee.getLaborFee() == null ? "null" : newFee.getLaborFee().toString());
			newFeeItem.addElement("laborNote").setText(newFee.getLaborNote() == null ? "null" : newFee.getLaborNote());
			newFeeItem.addElement("printFee").setText(newFee.getPrintFee() == null ? "null" : newFee.getPrintFee().toString());
			newFeeItem.addElement("printNote").setText(newFee.getPrintNote()  == null ? "null" : newFee.getPrintNote());
			newFeeItem.addElement("internationalFee").setText(newFee.getInternationalFee() == null ? "null" : newFee.getInternationalFee().toString());
			newFeeItem.addElement("internationalNote").setText(newFee.getInternationalNote() == null ? "null" : newFee.getInternationalNote());
			newFeeItem.addElement("indirectFee").setText(newFee.getIndirectFee() == null ? "null" : newFee.getIndirectFee().toString());
			newFeeItem.addElement("indirectNote").setText(newFee.getIndirectNote()  == null ? "null" : newFee.getIndirectNote());
			newFeeItem.addElement("otherFee").setText(newFee.getOtherFee() == null ? "null" : newFee.getOtherFee().toString());
			newFeeItem.addElement("otherNote").setText(newFee.getOtherNote()  == null ? "null" : newFee.getOtherNote());
			newFeeItem.addElement("totalFee").setText(newFee.getTotalFee() == null ? "null" : newFee.getTotalFee().toString());
			newFeeItem.addElement("type").setText(newFee.getType() == null ? "null" : newFee.getType().toString());
			newFeeItem.addElement("feeNote").setText(newFee.getFeeNote() == null ? "null" : newFee.getFeeNote());
			newFeeItem.addElement("fundedFee").setText(newFee.getFundedFee() == null ? "null" : newFee.getFundedFee().toString());
		} else {
			newFeeItem.setText("null");
		}
	}
	//中检数据
	private void toParseProjectMidinspectionItem(Element records, Object[] o ) {
		//组装一个item
		Element itemElement = records.addElement("item");
		//转化数据类型
		String[] str = new String[12];
		for (int i = 0; i < o.length-1 ; i++) {
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
//		String hqlSelect = "select pm.id, pm.file, pm.applicantSubmitDate, pm.progress, pm.productIntroduction, pm.finalAuditResult, pm.finalAuditorName, pm.finalAuditDate, pm.finalAuditOpinion, pm.finalAuditOpinionFeedback, " +
//				"pm.projectType, pm.grantedId, projectFee ";
		//转化数据类型
		ProjectFee pojFee = (ProjectFee) o[o.length-1];
		itemElement.addElement("id").setText(str[0]);//projectVariationID
		itemElement.addElement("file").setText(str[1]);
		itemElement.addElement("applicantSubmitDate").setText(str[2]);
		itemElement.addElement("progress").setText(str[3]);
		itemElement.addElement("productIntroduction").setText(str[4]);
		itemElement.addElement("finalAuditResult").setText(str[5]);
		itemElement.addElement("finalAuditorName").setText(str[6]);
		itemElement.addElement("finalAuditDate").setText(str[7]);
		itemElement.addElement("finalAuditOpinion").setText(str[8]);
		itemElement.addElement("finalAuditOpinionFeedback").setText(str[9]);
		
		itemElement.addElement("type").setText(str[10]);
		itemElement.addElement("grantedId").setText(str[11]);
		
		Element projectFeeItem = itemElement.addElement("projectFeeItem");
		if (null != pojFee) {
			projectFeeItem.addElement("projectFeeID").setText(pojFee.id);
			projectFeeItem.addElement("bookFee").setText(pojFee.getBookFee() == null ? "null" : pojFee.getBookFee().toString());
			projectFeeItem.addElement("bookNote").setText(pojFee.getBookNote() == null ? "null" : pojFee.getBookNote());
			projectFeeItem.addElement("dataFee").setText(pojFee.getDataFee() == null ? "null" : pojFee.getDataFee().toString());
			projectFeeItem.addElement("dataNote").setText(pojFee.getDataNote() == null ? "null" : pojFee.getDataNote());
			projectFeeItem.addElement("travelFee").setText(pojFee.getTravelFee() == null ? "null" : pojFee.getTravelFee().toString());
			projectFeeItem.addElement("travelNote").setText(pojFee.getTravelNote() == null ? "null" : pojFee.getTravelNote());
			projectFeeItem.addElement("deviceFee").setText(pojFee.getDeviceFee() == null ? "null" : pojFee.getDeviceFee().toString());
			projectFeeItem.addElement("deviceNote").setText(pojFee.getDeviceNote() == null ? "null" : pojFee.getDeviceNote());
			projectFeeItem.addElement("conferenceFee").setText(pojFee.getConferenceFee() == null ? "null" : pojFee.getConferenceFee().toString());
			projectFeeItem.addElement("conferenceNote").setText(pojFee.getConferenceNote() == null ? "null" : pojFee.getConferenceNote());
			projectFeeItem.addElement("consultationFee").setText(pojFee.getConsultationFee() == null ? "null" : pojFee.getConsultationFee().toString());
			projectFeeItem.addElement("consultationNote").setText(pojFee.getConsultationNote() == null ? "null" : pojFee.getConsultationNote());
			projectFeeItem.addElement("laborFee").setText(pojFee.getLaborFee() == null ? "null" : pojFee.getLaborFee().toString());
			projectFeeItem.addElement("laborNote").setText(pojFee.getLaborNote() == null ? "null" : pojFee.getLaborNote());
			projectFeeItem.addElement("printFee").setText(pojFee.getPrintFee() == null ? "null" : pojFee.getPrintFee().toString());
			projectFeeItem.addElement("printNote").setText(pojFee.getPrintNote()  == null ? "null" : pojFee.getPrintNote());
			projectFeeItem.addElement("internationalFee").setText(pojFee.getInternationalFee() == null ? "null" : pojFee.getInternationalFee().toString());
			projectFeeItem.addElement("internationalNote").setText(pojFee.getInternationalNote() == null ? "null" : pojFee.getInternationalNote());
			projectFeeItem.addElement("indirectFee").setText(pojFee.getIndirectFee() == null ? "null" : pojFee.getIndirectFee().toString());
			projectFeeItem.addElement("indirectNote").setText(pojFee.getIndirectNote()  == null ? "null" : pojFee.getIndirectNote());
			projectFeeItem.addElement("otherFee").setText(pojFee.getOtherFee() == null ? "null" : pojFee.getOtherFee().toString());
			projectFeeItem.addElement("otherNote").setText(pojFee.getOtherNote()  == null ? "null" : pojFee.getOtherNote());
			projectFeeItem.addElement("totalFee").setText(pojFee.getTotalFee() == null ? "null" : pojFee.getTotalFee().toString());
			projectFeeItem.addElement("type").setText(pojFee.getType() == null ? "null" : pojFee.getType().toString());
			projectFeeItem.addElement("feeNote").setText(pojFee.getFeeNote() == null ? "null" : pojFee.getFeeNote());
			projectFeeItem.addElement("fundedFee").setText(pojFee.getFundedFee() == null ? "null" : pojFee.getFundedFee().toString());
		} else {
			projectFeeItem.setText("null");
		}
	}
	//结项数据
	private void  toParseProjectEndinspectionItem(Element records, Object[] o ) {
		//组装一个item
		Element itemElement = records.addElement("item");
		//转化数据类型
		String[] str = new String[33];
		for (int i = 0; i < o.length-1 ; i++) {
			if (o[i] instanceof String) {
				str[i] = (String) o[i];
			} else if (o[i] instanceof Integer) {
				int temp = (Integer) o[i];
				str[i] = String.valueOf(temp);
			} else if (o[i] instanceof Double) {
				str[i] = String.valueOf(o[i]);
			} else if (o[i] instanceof Date) {
				str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
			} else if (o[i] == null) {
				str[i] = "null";//当对象为空时
			}
		}
//		String hqlSelect = "select pe.id, pe.certificate, pe.file, pe.applicantSubmitDate, pe.isApplyNoevaluation, pe.isApplyExcellent, pe.memberName, pe.ministryAuditorName, pe.ministryAuditDate, pe.ministryAuditOpinion, " +
//				"pe.ministryResultEnd, pe.ministryResultNoevaluation, pe.ministryResultExcellent, pe.finalAuditorName, pe.finalAuditDate, pe.finalAuditResultEnd, pe.finalAuditResultNoevaluation, pe.finalAuditResultExcellent, pe.finalAuditOpinion, pe.finalAuditOpinionFeedback, " +
//				"pe.reviewerName, pe.reviewDate, pe.reviewTotalScore, pe.reviewAverageScore, pe.reviewWay, pe.reviewResult, pe.reviewOpinion, reviewGrade.name, pe.reviewOpinionQualitative, pe.projectType, " +
//				"pe.grantedId, pe.importedProductInfo, pe.importedProductTypeOther, projectFee ";
		//转化数据类型
		ProjectFee pojFee = (ProjectFee) o[o.length-1];
		
		itemElement.addElement("id").setText(str[0]);//projectVariationID
		itemElement.addElement("certificate").setText(str[1]);
		itemElement.addElement("file").setText(str[2]);
		itemElement.addElement("applicantSubmitDate").setText(str[3]);
		itemElement.addElement("isApplyNoevaluation").setText(str[4]);
		itemElement.addElement("isApplyExcellent").setText(str[5]);
		itemElement.addElement("memberName").setText(str[6]);
		itemElement.addElement("ministryAuditorName").setText(str[7]);
		itemElement.addElement("ministryAuditDate").setText(str[8]);
		itemElement.addElement("ministryAuditOpinion").setText(str[9]);
		
		itemElement.addElement("ministryResultEnd").setText(str[10]);
		itemElement.addElement("ministryResultNoevaluation").setText(str[11]);
		itemElement.addElement("ministryResultExcellent").setText(str[12]);
		itemElement.addElement("finalAuditorName").setText(str[13]);
		itemElement.addElement("finalAuditDate").setText(str[14]);
		itemElement.addElement("finalAuditResultEnd").setText(str[15]);
		itemElement.addElement("finalAuditResultNoevaluation").setText(str[16]);
		itemElement.addElement("finalAuditResultExcellent").setText(str[17]);
		itemElement.addElement("finalAuditOpinion").setText(str[18]);
		itemElement.addElement("finalAuditOpinionFeedback").setText(str[19]);
		
		itemElement.addElement("reviewerName").setText(str[20]);
		itemElement.addElement("reviewDate").setText(str[21]);
		itemElement.addElement("reviewTotalScore").setText(str[22]);
		itemElement.addElement("reviewAverageScore").setText(str[23]);
		itemElement.addElement("reviewWay").setText(str[24]);
		itemElement.addElement("reviewResult").setText(str[25]);
		itemElement.addElement("reviewOpinion").setText(str[26]);
		itemElement.addElement("reviewGrade").setText(str[27]);
		itemElement.addElement("reviewOpinionQualitative").setText(str[28]);
		itemElement.addElement("type").setText(str[29]);
		
		itemElement.addElement("grantedId").setText(str[30]);
		itemElement.addElement("importedProductInfo").setText(str[31]);
		itemElement.addElement("importedProductTypeOther").setText(str[32]);
		
		Element projectFeeItem = itemElement.addElement("projectFeeItem");
		if (null != pojFee) {
			projectFeeItem.addElement("projectFeeID").setText(pojFee.id);
			projectFeeItem.addElement("bookFee").setText(pojFee.getBookFee() == null ? "null" : pojFee.getBookFee().toString());
			projectFeeItem.addElement("bookNote").setText(pojFee.getBookNote() == null ? "null" : pojFee.getBookNote());
			projectFeeItem.addElement("dataFee").setText(pojFee.getDataFee() == null ? "null" : pojFee.getDataFee().toString());
			projectFeeItem.addElement("dataNote").setText(pojFee.getDataNote() == null ? "null" : pojFee.getDataNote());
			projectFeeItem.addElement("travelFee").setText(pojFee.getTravelFee() == null ? "null" : pojFee.getTravelFee().toString());
			projectFeeItem.addElement("travelNote").setText(pojFee.getTravelNote() == null ? "null" : pojFee.getTravelNote());
			projectFeeItem.addElement("deviceFee").setText(pojFee.getDeviceFee() == null ? "null" : pojFee.getDeviceFee().toString());
			projectFeeItem.addElement("deviceNote").setText(pojFee.getDeviceNote() == null ? "null" : pojFee.getDeviceNote());
			projectFeeItem.addElement("conferenceFee").setText(pojFee.getConferenceFee() == null ? "null" : pojFee.getConferenceFee().toString());
			projectFeeItem.addElement("conferenceNote").setText(pojFee.getConferenceNote() == null ? "null" : pojFee.getConferenceNote());
			projectFeeItem.addElement("consultationFee").setText(pojFee.getConsultationFee() == null ? "null" : pojFee.getConsultationFee().toString());
			projectFeeItem.addElement("consultationNote").setText(pojFee.getConsultationNote() == null ? "null" : pojFee.getConsultationNote());
			projectFeeItem.addElement("laborFee").setText(pojFee.getLaborFee() == null ? "null" : pojFee.getLaborFee().toString());
			projectFeeItem.addElement("laborNote").setText(pojFee.getLaborNote() == null ? "null" : pojFee.getLaborNote());
			projectFeeItem.addElement("printFee").setText(pojFee.getPrintFee() == null ? "null" : pojFee.getPrintFee().toString());
			projectFeeItem.addElement("printNote").setText(pojFee.getPrintNote()  == null ? "null" : pojFee.getPrintNote());
			projectFeeItem.addElement("internationalFee").setText(pojFee.getInternationalFee() == null ? "null" : pojFee.getInternationalFee().toString());
			projectFeeItem.addElement("internationalNote").setText(pojFee.getInternationalNote() == null ? "null" : pojFee.getInternationalNote());
			projectFeeItem.addElement("indirectFee").setText(pojFee.getIndirectFee() == null ? "null" : pojFee.getIndirectFee().toString());
			projectFeeItem.addElement("indirectNote").setText(pojFee.getIndirectNote()  == null ? "null" : pojFee.getIndirectNote());
			projectFeeItem.addElement("otherFee").setText(pojFee.getOtherFee() == null ? "null" : pojFee.getOtherFee().toString());
			projectFeeItem.addElement("otherNote").setText(pojFee.getOtherNote()  == null ? "null" : pojFee.getOtherNote());
			projectFeeItem.addElement("totalFee").setText(pojFee.getTotalFee() == null ? "null" : pojFee.getTotalFee().toString());
			projectFeeItem.addElement("type").setText(pojFee.getType() == null ? "null" : pojFee.getType().toString());
			projectFeeItem.addElement("feeNote").setText(pojFee.getFeeNote() == null ? "null" : pojFee.getFeeNote());
			projectFeeItem.addElement("fundedFee").setText(pojFee.getFundedFee() == null ? "null" : pojFee.getFundedFee().toString());
		} else {
			projectFeeItem.setText("null");
		}
	}
	///////////////////////////////////////////////////////////////////
	/**
	 * 获取立项项目对应的申请项目研究类型
	 * @param string
	 * @return
	 */
	private String getGrantedProjectResearchType(String applicationID) {
		Map argMap = new HashMap<String, String>();
		argMap.put("applicationId", applicationID);
		String hql = "select researchType.name from ProjectApplication pa left join pa.researchType researchType where pa.id = :applicationId";
		return (String) dao.queryUnique(hql, argMap);
	}
	/**
	 * 根据申请项目id
	 * 返回｛项目成员，人员属性，学科属性，所在学校属性｝列表
	 * @param projectType
	 * @param applicationID
	 * @return
	 */
	private List getMemberListByAPPID(String projectType, String applicationID) {
		if (applicationID.isEmpty() || applicationID.equals("")) {
			return null;
		}
		String hql = null;
		if (projectType.equals("general")) {
			hql = " select genMem, person, ac, u from GeneralMember genMem left join genMem.member person " +
					" left join genMem.member.academic ac left join genMem.university u, GeneralApplication genApp " +
					" where genApp.id = genMem.application.id and genApp.id = ?";
		} else if (projectType.equals("instp")) {
			hql = " select instpMem, person, ac, u from InstpMember instpMem left join instpMem.member person " +
				" left join instpMem.member.academic ac left join instpMem.university u, InstpApplication instpApp " +
				" where instpApp.id = instpMem.application.id and instpApp.id = ?";
		}
		return  dao.query(hql.toString(), applicationID);
	}
	/**
	 * 获取项目变更的成员信息
	 * @return
	 */
	private Map getChangeMemberInfo(String applicationID, int newGroupNumber, int oldGroupNumber) {
		Map resultMap = new HashMap<String, String>();
		Map argMap = new HashMap<String, String>();
		argMap.put("applicationId", applicationID);
		argMap.put("groupNumber", newGroupNumber);
		String Hql = "select pm from ProjectMember pm where pm.applicationId = :applicationId and pm.groupNumber = :groupNumber";
		List<ProjectMember> newMembers = dao.query(Hql, argMap);
		Document document = DocumentHelper.createDocument();
		Element membersElement = document.addElement("Members");
		if (!newMembers.isEmpty() && newMembers != null) {
			for (int i = 0; i < newMembers.size(); i++) {
				ProjectMember mb = newMembers.get(i);
				Element memberElement = membersElement.addElement("member");
				Element mtElement = memberElement.addElement("memberType");
				mtElement.setText(String.valueOf(mb.getMemberType() == null ? "" : mb.getMemberType()));
				Element mnElement = memberElement.addElement("memberName");
				mnElement.setText(mb.getMemberName() == null ? "" : mb.getMemberName());
				Element anElement = memberElement.addElement("agencyName");
				anElement.setText(mb.getAgencyName() == null ? "" : mb.getAgencyName());
				Element dnElement = memberElement.addElement("divisionName");
				dnElement.setText(mb.getDivisionName() == null ? "" : mb.getDivisionName());
				Element itElement = memberElement.addElement("idcardType");
				itElement.setText(mb.getIdcardType() == null ? "" : mb.getIdcardType());
				Element inElement = memberElement.addElement("idcardNumber");
				inElement.setText(mb.getIdcardNumber() == null ? "" : mb.getIdcardNumber());
				Element idElement = memberElement.addElement("isDirector");
				idElement.setText(String.valueOf(mb.getIsDirector()));
			}
			resultMap.put("newMembers", document.asXML());
		}
		argMap.put("groupNumber", oldGroupNumber);
		List<ProjectMember> oldMembers = dao.query(Hql, argMap);
		StringBuffer oldMembersBuffer = new StringBuffer();
		if (!oldMembers.isEmpty()) {
			for (int i = 0; i < oldMembers.size(); i++) {
				ProjectMember mb = oldMembers.get(i);
				oldMembersBuffer.append(mb.getMemberName() + "(" + mb.getAgencyName() == null ? "" :mb.getAgencyName() + mb.getDivisionName() == null ? "" : mb.getDivisionName() + ")");
				if (i != oldMembers.size() - 1) {
					oldMembersBuffer.append("; ");
				}
			}
			resultMap.put("oldMembers", oldMembersBuffer.toString());
		}
		return resultMap;
	}
	//获取人员相关联的办公地址信息
	private Map getPersonAddress(Person person){
		Map addInfoMap = new HashMap<String, String>();
//		List list = new ArrayList<String>();
		if(!person.getOfficeAddressIds().isEmpty() && person.getOfficeAddressIds()!=null){
			List<Address> addressList = dao.query("select address from Address address where address.ids = ?", person.getOfficeAddressIds());
			String officerAddress = "";
			String officePostcode = "";
			for(Address address : addressList){
				if(address.getAddress()!=null && !address.getAddress().equals("")){
					officerAddress += officerAddress.equals("") ? address.getAddress() : ("; " + address.getAddress());
				}
				if(address.getPostCode()!=null && !address.getPostCode().equals("")){
					officePostcode += officePostcode.equals("") ? address.getPostCode() : ("; " + address.getPostCode());
				}
			}
			addInfoMap.put("officeAdd", officerAddress.equals("") ? "null" : officerAddress);
			addInfoMap.put("officePost", officePostcode.equals("") ? "null" : officePostcode);
		}else {
			addInfoMap.put("officeAdd", "null");
			addInfoMap.put("officePost", "null");
		}
		return addInfoMap;
	}
	
}
