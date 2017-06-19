package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralMember;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 一般项目申请Excel中有些项目将负责人重复写进了成员中，导致成员表出现重复数据
 * 本程序将这些重复成员删除，并将同项目内其他成员的编号向前顺延
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120711 extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	

	@Override
	protected void work() throws Throwable {
		List<GeneralMember> members = dao.query("select gm from GeneralMember gm where gm.isDirector = 0 and gm.memberName = gm.application.applicantName order by gm.memberSn desc");
		for (int i = 0; i < members.size(); i++) {
			System.out.println(i + " / " + members.size());

			GeneralMember member = members.get(i);
			dao.execute("update GeneralMember gm set gm.memberSn = gm.memberSn - 1 where gm.application.id = ? and gm.groupNumber = ? and gm.memberSn > ?", member.getApplication().getId(), member.getGroupNumber(), member.getMemberSn());
			dao.delete(member);
		}
	}

}
