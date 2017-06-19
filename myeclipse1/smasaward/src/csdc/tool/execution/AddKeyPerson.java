package csdc.tool.execution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.sun.xml.internal.bind.v2.model.core.ID;

import csdc.bean.Expert;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.service.imp.ExpertService;
import csdc.service.imp.GeneralService;
import csdc.tool.StringTool;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.info.ImporterInfo;

/**
 * 
 * @author 
 * @redmine
 */
public class AddKeyPerson extends Execution{

	/**
	 * smdb的jdbcDao
	 */
	private JdbcDao jdbcDao;
	
	@Autowired
	HibernateBaseDao dao;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private ExpertService es;
	
	/**
	 * 项目年度
	 */
	private Integer year;
	
	/**
	 * 高校名称 -> 高校代码
	 */
	private Map<String, String> univNameCodeMap;
	
	private List<String[]> keyPersons;

	
	protected void work() throws Throwable {
		syncKeyPerson();
	}

	/**
	 * 项目初审
	 */
	private void syncKeyPerson(){
		
		initMap();
		
		//当前年份
		year = (Integer) ActionContext.getContext().getSession().get("defaultYear");
		expertFinder.initData();
		int updateCnt = 0;
		int i = 1;
		int j = 0;
		Map<ImporterInfo, String> conditions = new HashMap<ImporterInfo, String>();
		
		for (String[] keyPerson : keyPersons) {
			if (i == 115) {
				System.out.println("yes");
			}
			System.out.println(i);
			i++;
			//填充匹配条件
			conditions.put(ImporterInfo.BIRTHDAY, keyPerson[8]);
			conditions.put(ImporterInfo.IDCARD_TYPE, keyPerson[6]);
			conditions.put(ImporterInfo.IDCARD_NUMBER, keyPerson[7]);
			conditions.put(ImporterInfo.MOBILE_PHONE, keyPerson[5]);
			conditions.put(ImporterInfo.EMAIL, keyPerson[4]);
			conditions.put(ImporterInfo.HOME_PHONE, keyPerson[1]);
			conditions.put(ImporterInfo.OFFICE_PHONE, keyPerson[2]);
			conditions.put(ImporterInfo.OFFICE_FAX, keyPerson[3]);
			
			Expert otherExpert = expertFinder.findExpert("", keyPerson[0], conditions, false);
			
			if (null != otherExpert) {
				updateCnt++;
				List<String> otherExpertIds = new ArrayList<String>();
				otherExpert.setIsKey(1);
				otherExpertIds.add(otherExpert.getId());
				String reason = otherExpert.getReason();
				if (reason == null) {
					reason = "永久退评：重点人";
				}
				if (reason != null && !reason.contains("永久退评：重点人")) {
					reason = "永久退评：重点人" + "; " + reason;
				} 
				es.setReview(otherExpertIds, 0, reason, 1);
			} else {
				j++;
				System.out.println("未找到：" + keyPerson[0]);
			}
		}
		
		System.out.println(updateCnt);
		System.out.println("没找到的共：" + j);
		//打印输出结果
		expertFinder.printResult();
		
	}
	
	/**
	 * 初始化Map
	 */
	private void initMap() {
		long begin = System.currentTimeMillis();
		
		univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		
		//smdb中的重点人
		keyPersons = jdbcDao.query("select tp.c_name, tp.c_home_phone, tp.c_office_phone, tp.c_office_fax, tp.c_email, tp.c_mobile_phone, tp.c_idcard_type, tp.c_idcard_number, tp.c_birthday, ta.c_name " +
				"from t_person tp left join t_teacher tt on tp.c_id = tt.c_person_id " +
				"left join t_agency ta on tt.c_university_id = ta.c_id " +
				"where tp.c_is_key = '1'");
		
		
		System.out.println("init map complete! Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}

	

	
	public AddKeyPerson() {
	}
	
	public AddKeyPerson(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	

}

