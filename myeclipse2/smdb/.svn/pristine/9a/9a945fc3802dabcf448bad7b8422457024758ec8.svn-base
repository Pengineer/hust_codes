package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.SystemOption;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修改系统选项表中外语语种的code
 * code存放按照简称排序的序号，name存放中文，description存放英文，abbr存放简称
 * @author zhouzj
 */
@Component
public class FixForeignLanguage extends Execution {

	@Autowired
	private IHibernateBaseDao dao;

	@SuppressWarnings("unchecked")
	@Override
	protected void work() throws Throwable {
		List<SystemOption> sos = dao.query("select so from SystemOption so where so.systemOption.standard = ? order by so.abbr", "ISO 639-1");
		Integer i = 1;
		for (SystemOption so : sos) {
			so.setDescription(so.getCode());
			so.setCode(toCode(i));
			i++;
		}
		dao.flush();
		dao.clear();
		
	}
	
	private String toCode(Integer i) {
		if (i < 10) {
			return "00".concat(i.toString());
		} else if (i < 100){
			return "0".concat(i.toString());
		} else {
			return i.toString();
		}
	}

}
