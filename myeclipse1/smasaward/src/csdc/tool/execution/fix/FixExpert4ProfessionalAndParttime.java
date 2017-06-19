package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.Execution;

/**
 * 修复因专兼职带来的数据问题
 * 1、评价等级
 * 2、评审年份
 * @author fengcl
 *
 */
public class FixExpert4ProfessionalAndParttime extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Override
	protected void work() throws Throwable {
		
		List<Object[]> experts = dao.query("select e1, e2 from Expert e1, Expert e2 where e1.type = '专职人员' and (e2.type = '兼职人员' or e2.type = '离职人员') and e1.name = e2.name and e1.idCardNumber = e2.idCardNumber ");
		for (Object[] expert : experts) {
			Expert professionalExpert = (Expert) expert[0];
			Expert parttimeExpert = (Expert) expert[1];
			String rating = Math.max(Integer.parseInt(professionalExpert.getRating()), Integer.parseInt(parttimeExpert.getRating())) + ""; 
			professionalExpert.setRating(rating);
			System.out.println(professionalExpert.getIdCardNumber() + professionalExpert.getName() + professionalExpert.getRating() + " = " + parttimeExpert.getIdCardNumber() + parttimeExpert.getName() + parttimeExpert.getRating());
			
			String generalReviewYears1 = professionalExpert.getGeneralReviewYears();
			String generalReviewYears2 = parttimeExpert.getGeneralReviewYears();
			if (null != generalReviewYears2) {
				if(null == generalReviewYears1){
					professionalExpert.setGeneralReviewYears(generalReviewYears2);
				}else {
					List years = new ArrayList();
					String[] years1 = generalReviewYears1.split("\\s*;\\s*");
					String[] years2 = generalReviewYears2.split("\\s*;\\s*");
					for (String year : years1) {
						years.add(year);
					}
					for (String year : years2) {
						if (!years.contains(year)) {
							years.add(year);
						}
					}
					Collections.sort(years);
					
					professionalExpert.setGeneralReviewYears(StringTool.join(years, "; "));
				}
			}
			
		}
	}
}
