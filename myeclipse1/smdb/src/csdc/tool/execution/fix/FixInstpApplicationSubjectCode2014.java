package csdc.tool.execution.fix;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.InstpApplication;
import csdc.bean.SinossProjectApplication;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.Importer;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 
 * @author pengliang
 * 修复2014年基地项目学科代码
 */

@Component
public class FixInstpApplicationSubjectCode2014 extends Importer {

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;
	
	@Override
	protected void work() throws Throwable {
		// 当前导入项目条数		  
		int currentImport = 0;
		//总项目数
		int totalImport =0;
		
		List<SinossProjectApplication> spas = (List<SinossProjectApplication>)dao.query("select spa from SinossProjectApplication spa where spa.year='2014' and spa.typeCode='base'"); 
		totalImport = spas.size();
		tool.initDiscNameCodeMap();
		for (SinossProjectApplication spa : spas) {
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentImport) + "/" + totalImport+ "条数据");
			InstpApplication application = instpProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer());
			
			//学科门类+学科代码
			String subject = spa.getSubject();
			String subject1_1 = spa.getSubject1_1();
			String subject1_2 = spa.getSubject1_2();
			String researchDir = spa.getResearchDirection();
			String subjectCode = getSubjectCode(subject, subject1_1, subject1_2, researchDir);
			beanFieldUtils.setField(application, "disciplineType", getSubjectType(subject), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(application, "discipline", subjectCode, BuiltinMergeStrategies.REPLACE);
			dao.modify(application);
		}
		System.out.println("success");
	}
	
	/**
	 * 通过一级学科、二级学科、三级学科获取学科代码  
	 * @author 2014-8-30 
	 */
	public String getSubjectCode(String subject1, String subject2, String subject3, String researchDir){
		subject1 = (subject1 == null) ? "" : _discipline(subject1);
		subject2 = (subject2 == null) ? "" : _discipline(subject2);
		subject3 = (subject3 == null) ? "" : _discipline(subject3);
		
		if ((subject1.equals("75047-99") || subject1.equals("75011-44")) && subject2.length()>0) {
			if (subject3.length() > 0 && subject3.contains(subject2)) {
				return subject3 + "/" + tool.discCodeNameMap.get(subject3);
			} else if (subject3.length() > 0 && !subject3.contains(subject2)) {
				if (subject2.compareTo(subject3) > 0) {
					return subject3 + "/" + tool.discCodeNameMap.get(subject3) + "; " + subject2 + "/" + tool.discCodeNameMap.get(subject2);
				} else {
					return subject2 + "/" + tool.discCodeNameMap.get(subject2) + "; " + subject3 + "/" + tool.discCodeNameMap.get(subject3);
				}
			} else {
				return subject2 + "/" + tool.discCodeNameMap.get(subject2);
			} 
		} else if ((subject1.equals("75047-99") || subject1.equals("75011-44")) && subject2.length()==0 && subject3.length()==0) {
				throw new RuntimeException();//只有一级学科且一级学科是个综合学科，需要手动处理
		} else {
			if (subject3.length() > 0) {//有一级、二级和三级
				if (subject3.contains(subject2)) {//三级包含二级
					if (subject2.contains(subject1)) {//三级包含二级，二级包含一级
						return subject3 + "/" + tool.discCodeNameMap.get(subject3);
					} else {//三级包含二级，二级不包含一级
						if(subject1.compareTo(subject3) > 0) {
							return subject3 + "/" + tool.discCodeNameMap.get(subject3) + "; " + subject1 + "/" + tool.discCodeNameMap.get(subject1);
						} else {
							return subject1 + "/" + tool.discCodeNameMap.get(subject1) + "; " + subject3 + "/" + tool.discCodeNameMap.get(subject3);
						}
					}
				} else {//三级不包含二级
					if (subject2.contains(subject1)) {//三级不包含二级，二级包含一级
						if (subject2.compareTo(subject3) > 0){
							return subject3 + "/" + tool.discCodeNameMap.get(subject3) + "; " + subject2 + "/" + tool.discCodeNameMap.get(subject2);
						} else {
							return subject2 + "/" + tool.discCodeNameMap.get(subject2) + "; " + subject3 + "/" + tool.discCodeNameMap.get(subject3);
						}
					} else {//三级不包含二级，二级不包含一级(排序)
						String[] subjects = {subject1, subject2, subject3};
						Arrays.sort(subjects);
						return subjects[0] + "/" + tool.discCodeNameMap.get(subjects[0]) + 
								"; " +subjects[1] + "/" + tool.discCodeNameMap.get(subjects[1]) + 
								"; " +subjects[2] + "/" + tool.discCodeNameMap.get(subjects[2]);
					}
				}
			} else if (subject3.length() == 0 && subject2.length() > 0) {//只有一级和二级
				if (subject2.contains(subject1)){
					return subject2 + "/" + tool.discCodeNameMap.get(subject2);
				} else {
					if (subject2.compareTo(subject1) > 0) {
						return subject1 + "/" + tool.discCodeNameMap.get(subject1) + "; " + subject2 + "/" + tool.discCodeNameMap.get(subject2);
					}
					else {
						return subject2 + "/" + tool.discCodeNameMap.get(subject2) + "; " + subject1 + "/" + tool.discCodeNameMap.get(subject1);
					}
				}
			} else {//只有一级
				return subject1 + "/" + tool.discCodeNameMap.get(subject1);
			}						
		}
	}
	
	/**
	 * 通过一级学科获取学科门类  
	 * @author 2014-8-30 
	 */
	public String getSubjectType(String subject){
		if(subject.startsWith ("170")){
			return "地球科学";
		}else if(subject.startsWith("190")){
			return "心理学";
		}else if(subject.startsWith("630")){
			return "管理学";
		}else if(subject.startsWith("710")){
			return "马克思主义";
		}else if(subject.startsWith("720")){
			if(subject.startsWith("72040")){
				return "逻辑学";
			}else return "哲学";
		}else if(subject.startsWith("730")){
			return "宗教学";
		}else if(subject.startsWith("740")){
			return "语言学";
		}else if (subject.startsWith("75011-44")){
			return "中国文学";
		}else if(subject.startsWith("75047-99")){
			return "外国文学";	
		}else if(subject.startsWith("760")){
			return "艺术学";
		}else if(subject.startsWith("770")){
			return "历史学";
		}else if(subject.startsWith("780")){
			return "考古学";
		}else if(subject.startsWith("790")){
			return "经济学";
		}else if(subject.startsWith("810")){
			return "政治学";
		}else if(subject.startsWith("820")){
			return "法学";
		}else if(subject.startsWith("840")){
			return "社会学";
		}else if(subject.startsWith("850")){
			return "民族学与文化学";
		}else if(subject.startsWith("860")){
			return "新闻学与传播学";
		}else if(subject.startsWith("870")){
			return "图书馆、情报与文献学";
		}else if(subject.startsWith("880")){
			return "教育学";
		}else if(subject.startsWith("890")){
			return "体育学";
		}else if(subject.startsWith("910")){
			return "统计学";
		}else if(subject.startsWith("GAT")){
			return "港澳台问题研究";
		}else if(subject.startsWith("GJW")){
			return "国际问题研究";
		}else if(subject.startsWith("zjxxk")){
			return "综合研究/交叉学科";
		}else return "未知";

	}
	
	/**
	 * 学科转换
	 * @param subject
	 * @return
	 */
	public String _discipline(String subject){
		if(subject.startsWith("GAT")){
			return "980";
		}else if(subject.startsWith("GJW")){
			return "990";
		}else if(subject.startsWith("SXZZJY")){
			return "970";
		}else return subject;
	}

}
