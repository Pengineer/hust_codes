package csdc.tool.bean;
/**
 * Sinoss数据同步接口名称
 *  APP_RESULT：项目申报审核结果
 *  MID_RESULT：项目中检审核结果
 *  VAR_RESULT：项目变更审核结果
 *  END_RESULT：项目结项审核结果
 *  MID_REQUIRED：项目需中检数据
 *  MID_DEFER：项目中检延期审核结果
 * @author Yaoyota
 *
 */
public enum SinossInterface {
	APP_RESULT("requestProjectApplicationResult"),
	MID_RESULT("requestProjectMidinspectionResult"),
	VAR_RESULT("requestProjectVariationResult"),
	END_RESULT("requestProjectEndinspectionResult"),
	MID_REQUIRED("requestProjectMidinspectionRequired"),
	MID_DEFER("requestProjectMidinspectionDeferResult");
	private String _name;
	SinossInterface(String name) {
		_name = name; 
	}
	
    public String getName() {  
        return _name;  
    } 
    
    @Override  
    public String toString() {  
        return _name;  
    }
}
