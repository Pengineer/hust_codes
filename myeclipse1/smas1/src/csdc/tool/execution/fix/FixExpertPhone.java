package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修复专家电话号码
 * @author xuhan
 *
 */
@Component
public class FixExpertPhone extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Override
	public void work() throws Throwable {
		List<Expert> experts = dao.query("from Expert");
		for (Expert expert : experts) {
			expert.setHomePhone(expert.getHomePhone());
			expert.setOfficePhone(expert.getOfficePhone());
		}
	}
	
	public String fixPhone(String origin) {
		if (origin == null) {
			return origin;
		}
		String result = "";
		origin = origin.replace("－", "-").replace("—", "-").replace("（", "(").replace("）", ")");
		String[] tmp = origin.split("[\\s、;；,，\\.。\\\\/]+");
		for (String string : tmp) if (!string.isEmpty()) {
			if (!result.isEmpty()) {
				result += "; ";
			}
			result += string.replaceAll("^\\((\\d+)\\)", "$1-").replaceAll("\\((\\d+)\\)$", "-$1");
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(new FixExpertPhone().fixPhone("0754-82903175－837; 0754-82903175-837"));
	}
	
}
