package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 
import csdc.bean.Expert;

/**
 * 给专家增加2015标识
 * @author pengliang
 * 1，只通过身份证查找：奖励申请者的名称可能填写的是笔名；奖励申请者可能是团体（证件号写的团体第一人）；
 * 2，通过身份证找不到，再通过高校代码+姓名查找。
 */

@Component
public class FillYearToExpertAward extends Importer {
	
	List<String[]> cai;
	Map<String, Expert> experts;
	
	@Autowired
	Tools tools;
	
	protected void work() throws Throwable {
		init();
		Set<String> msgSet = new HashSet<String>();
		Expert expert=null;
		for (String[] str : cai) {
			expert=experts.get(str[2]);
			if(expert == null){
				expert = tools.getExpert(str[1], str[0]);
				if(expert == null) {
					msgSet.add(str[1] + "::" + str[0] + "::" + str[2]);
					continue;
				}
			}
			expert.setAwardApplyYears("2015");
			dao.modify(expert);
		}
		
		if(msgSet.size()>0){
			System.out.println(msgSet.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public void init() {
		System.out.println("start");
		String[] tmp = {null,null,null};
		if(cai == null) {
			cai = new ArrayList<String[]>();
		}
		List<Object[]> lists = dao.query("select app.unitCode,app.applicantName,app.idcardNumber from AwardApplication app where app.idcardNumber is not null");
		for(Object[] list : lists) {
			String code = (String)list[0];
			String name = (String)list[1];
			String number = (String)list[2];
			tmp = new String[3];
			tmp[0] = code;
			tmp[1] = name;
			tmp[2] = number;
			cai.add(tmp);
		}
		
		if(experts == null){
			experts = new HashMap<String, Expert>();
		}
		List<Expert> expertlList = dao.query("select e from Expert e");
		for(Expert exp : expertlList){
			experts.put(exp.getIdCardNumber(), exp);
		}
		System.out.println("init over!");
	}

}
