package csdc.tool.execution.fix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.dao.SqlBaseDao;
import csdc.tool.execution.Execution;

/**
 * 更新系统选项表数据的id为32为数字字母,同时更新指向其的子表外键
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120502 extends Execution {

	@Autowired
	private SqlBaseDao dao;

	String charList = "abcdefghijklmnopqrstuvwxyz";
	
	Random random = new Random();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@Override
	protected void work() throws Throwable {
		
		List<Object[]> fks = dao.query(
			"select " +
			"	b.TABLE_NAME 子表表名," +
			"	c.column_name 子表列名," +
			"	a.TABLE_NAME 主表表名," +
			"	b.CONSTRAINT_NAME 约束名 " +
			"from " +
			"	user_indexes a," +
			"	user_constraints b," +
			"	user_cons_columns c " +
			"where " +
			"	a.TABLE_NAME = 'T_SYSTEM_OPTION' " +
			"	and b.R_CONSTRAINT_NAME = a.INDEX_NAME(+) " +
			"	and b.CONSTRAINT_NAME = c.constraint_name"
		);
		
		List<String> soIds = dao.query("select c_id from t_system_option where length(c_id) <> 32 ");
		for (int i = 0; i < soIds.size(); i++) {
			System.out.print("(" + i + " / " + soIds.size() + ")");
			Object[] tmp = (Object[]) dao.queryUnique("select C_NAME, C_DESCRIPTION, C_PARENT_ID, C_CODE, C_IS_AVAILABLE, C_STANDARD, C_ABBR from t_system_option so where so.c_id = ?", soIds.get(i));
			String newId = calcNewId();
			
			System.out.println(soIds.get(i) + " -> " + newId);
			dao.execute(
				"insert into t_system_option " +
				"	(C_ID, C_NAME, C_DESCRIPTION, C_PARENT_ID, C_CODE, C_IS_AVAILABLE, C_STANDARD, C_ABBR) values " +
				"	(?, ?, ?, ?, ?, ?, ?, ?)",
				newId, tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]
			);
			
			for (Object[] fk : fks) {
				String 子表表名 = (String) fk[0];
				String 子表列名 = (String) fk[1];
				dao.execute("update " + 子表表名 + " set " + 子表列名 + " = ? where " + 子表列名 + " = ?", newId, soIds.get(i));
			}

			dao.execute("delete from t_system_option where c_id = ?", soIds.get(i));
		}
		
	}
	
	private String calcNewId() {
		StringBuffer res = new StringBuffer(sdf.format(new Date()));
		while (res.length() < 32) {
			res.append(charList.charAt(random.nextInt(charList.length())));
		}
		if (res.length() != 32) {
			throw new RuntimeException("不可能");
		}
		return res.toString();
	}

}
