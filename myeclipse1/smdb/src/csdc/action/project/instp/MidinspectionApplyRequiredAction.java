package csdc.action.project.instp;

public class MidinspectionApplyRequiredAction extends csdc.action.project.MidinspectionApplyRequiredAction {

	private static final long serialVersionUID = -5382106351870716640L;

	private static final String PAGE_NAME = "instpMidInspectionRequiredPage";//列表页面名称
	private static final String PROJECT_TYPE = "instp";//项目类别
	
	public String projectType(){
		return MidinspectionApplyRequiredAction.PROJECT_TYPE;
	}
	public String pageName() {
		return MidinspectionApplyRequiredAction.PAGE_NAME;
	}
}
