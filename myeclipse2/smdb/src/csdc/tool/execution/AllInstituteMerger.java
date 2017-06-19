package csdc.tool.execution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.dao.IHibernateBaseDao;
import csdc.tool.merger.InstituteMerger;

/**
 * 合并所有需要合并的基地
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class AllInstituteMerger extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private InstituteMerger instituteMerger;
	
	@Override
	protected void work() throws Throwable {
		List<Object []> instInfo = dao.query("select inst.id, inst.name, inst.subjection.name from Institute inst");
		System.out.println(instInfo.size());

		for (Object[] objects : instInfo) {
			objects[1] = ((String)objects[1]).replace((String) objects[2], "").replace("[待删除]", "").replace("　", "").trim();
		}
		
		Collections.sort(instInfo, new Comparator<Object []>() {
			public int compare(Object [] a, Object [] b) {
				String univName1 = (String) a[2];
				String univName2 = (String) b[2];
				String instName1 = (String) a[1];
				String instName2 = (String) b[1];
				if (!univName1.equals(univName2)) {
					return univName1.compareTo(univName2);
				} else {
					return instName1.compareTo(instName2);
				}
			}
		});
		
		//同一学校、名字相同的基地作为一组，合并
		for (int begin = 0; begin < instInfo.size();) {
			int end = begin + 1;
			while (end < instInfo.size() && instInfo.get(end)[2].equals(instInfo.get(begin)[2]) && instInfo.get(end)[1].equals(instInfo.get(begin)[1])) {
				++end;
			}

			if (begin + 1 < end) {
				System.out.println((end - begin) + "\t: " + instInfo.get(begin)[2] + " - " + instInfo.get(begin)[1]);
				
				List<Serializable> incomeInstId = new ArrayList<Serializable>();
				for (int i = begin + 1; i < end; i++) {
					incomeInstId.add((Serializable) instInfo.get(i)[0]);
				}
				
				try {
					instituteMerger.mergeInstitute((Serializable) instInfo.get(begin)[0], incomeInstId);
				} catch (Exception e) {
					//如果一个批次的基地合并失败，不停止，继续合并其他基地
					System.out.println("基地合并失败：" + instInfo.get(begin)[2] + " " + instInfo.get(begin)[1]);
					e.printStackTrace();
				}
			}

			begin = end;
		}
	}

}
