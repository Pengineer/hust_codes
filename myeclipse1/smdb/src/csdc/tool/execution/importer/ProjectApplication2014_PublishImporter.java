package csdc.tool.execution.importer;

import java.util.List;

import csdc.bean.ProjectApplication;
import csdc.tool.reader.ExcelReader;

/**
 * 给2014年一般申请项目中的出版项目添加备注：出版项目不参与评审
 * @author pengliang  2014-9-9
 *
 */
public class ProjectApplication2014_PublishImporter extends Importer{
	/**
	 * 《附件：20140421_2014年一般项目初审最终结果（社科服务中心提供）.xls》
	 */
	private ExcelReader reader;
	
	public ProjectApplication2014_PublishImporter(){
		
	}
	
	public ProjectApplication2014_PublishImporter(String filepath){
		reader = new ExcelReader(filepath);
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public void work() throws Exception {
		/**
		 * 数据库中ProjectApplication的申请人名称+学校名称+项目名
		 */
		String aNameUNamePNameFromDB = null;   
		/**
		 * Excel中ProjectApplication的申请人名称+学校名称+项目名
		 */
		String aNameUNamePNameFromExcel = null;
		 // 项目数
		int projectNum = 0;
		// 当前导入项目条数		 
		int currentImport = 0;
		reader.readSheet(2);
		List<ProjectApplication> pros = (List<ProjectApplication>)dao.query("select pa from ProjectApplication pa where pa.type='general' and pa.year=2014");
		projectNum = pros.size();
		
		for(ProjectApplication pro : pros){		
			aNameUNamePNameFromDB = pro.getApplicantName().replaceAll("\\s+", "") + pro.getUniversity().getName() + pro.getName();
			reader.setCurrentRowIndex(1);
			while(next(reader)){
				aNameUNamePNameFromExcel = H + C + B;
				if(aNameUNamePNameFromExcel.equals(aNameUNamePNameFromDB)){
					System.out.println("当前导入项目：" + (++currentImport) + "/" + projectNum);
					System.out.println("出版项目：" + B + "--" + H);
					pro.setNote("出版项目不参与评审");
					break;
				}				
			}
			//将上述数据跟新到数据库
			dao.addOrModify(pro);
		}		
	}
}
