package csdc.tool.widget;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Expert;
import csdc.service.IBaseService;

/**
 * 删除专家某字段开头的分号
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class LeadingSemicolonRemover {
	
	@Autowired
	public static IBaseService baseService;
	
	@Transactional
	public void start() {
		List<Expert> experts = baseService.query("select e from Expert e");
		for (Expert expert : experts) {
			if (expert.getReason() != null && expert.getReason().startsWith(";")) {
				System.out.println(expert.getName());
				expert.setReason(expert.getReason().replaceFirst("^; ", ""));
//				session.update(expert);
			}
		}
	}

}
