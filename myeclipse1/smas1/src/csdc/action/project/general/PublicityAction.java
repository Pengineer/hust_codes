package csdc.action.project.general;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectGranted;
import csdc.bean.common.Visitor;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
import csdc.tool.tableKit.imp.ProjectKit;
import csdc.tool.widget.NumberHandle;

/**
 * 一般项目公示
 * @author fengcl
 *
 */
@SuppressWarnings("unchecked")
public class PublicityAction extends GeneralAction{
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select p.id, p.projectName, p.director, u.name, p.discipline, p.voteNumber, p.score, p.isGranting, p.isGranted from ProjectApplication p, University u where p.type = 'general' and p.universityCode = u.code ";
	private static final String PAGENAME = "generalPublicityPage";
	private static final String PAGE_BUFFER_ID = "p.id";//缓存id
	private static final String column[] = {
		"p.projectName",
		"p.director",
		"u.name",
		"CONCAT(p.voteNumber, p.score)",
		"p.isGranting",
		"p.isGranted",
		"p.id"
	};
	@Autowired
	private ProjectKit ek;
	private int publicFlag; //公示标识：1，公示 ；0，不公示
	private int listFlag; //列表类型：1，公示项目 ；2，不公示项目
	
	
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" and p.year = :defaultYear");
		map.put("defaultYear", session.get("defaultYear"));
		if(!keyword.isEmpty()){
			hql.append(" and ");
			if (searchType == 1) {// 按项目标题检索
				hql.append(" LOWER(p.projectName) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {// 按项目负责人检索
				hql.append(" LOWER(p.director) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 3) {// 按项目所属高校检索
				hql.append(" LOWER(u.name) like :keyword ");
				map.put("keyword", "%" + keyword + "%");
			} else {// 按上述字段检索
				hql.append(" (LOWER(p.projectName) like :keyword or LOWER(p.director) like :keyword or LOWER(u.name) like :keyword) ");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		if(listFlag == 2){//不公示的项目
			hql.append(" and p.isGranting = 0");
			session.put("radioValue", 2);//不公示的项目
		}else{//公示的项目
			hql.append(" and (p.isGranting > 0)");
			session.put("radioValue", 1);//公示的项目
		}
		
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}
	
	/**
	 * 举报信息弹出层项目信息初始化
	 * @return
	 */
	public String popInform(){
		project = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		if (project == null) {
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
			return INPUT;
		} 
		project.setInformDate((project.getInformDate() == null) ? new Date() : project.getInformDate());
		return SUCCESS;
	}
	
	public void validatePopInform(){
		if(entityId == null || entityId.isEmpty()){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
		}
	}
	
	/**
	 * 修改拟公示状态
	 * @return
	 */
	public String updatePublic() {
		project = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		if (project == null) {
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
			return INPUT;
		}
		if(publicFlag == 1)
			project.setIsGranting(0);//不公示（不立项）
		else
			project.setIsGranting(1);//公示
		baseService.modify(project);
		return SUCCESS;
	}

	/**
	 * 添加举报信息
	 * @return
	 */
	public String addInform(){
		ProjectApplication mproject = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		mproject.setInformTitle(project.getInformTitle());
		mproject.setInformContent(project.getInformContent());
		mproject.setInformer(project.getInformer());
		mproject.setInformerPhone(project.getInformerPhone());
		mproject.setInformerEmail(project.getInformerEmail());
		mproject.setInformDate(project.getInformDate());
		baseService.modify(mproject);
		return SUCCESS;
		
	}
	
	/**
	 * 确定立项<br>
	 * 将公示的项目确定立项，并自动生成项目批准号
	 * @return
	 */
	public String confirmGrant(){
		HashSet<String> specialSet = new HashSet<String>(Arrays.asList(GlobalInfo.SPECIAL_AREAS));
		HashSet<String> xbSet = new HashSet<String>(Arrays.asList(GlobalInfo.XB_AREAS));
		String aprrovalNumber = "";//项目批准号
		//查询所有待确定立项的项目id
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));
		
		//============查询数据库，获取各类项目批准号的最大值，放入对应类型的map中============
		//多次确定立项判断：获取当前年度已经立项项目批准号的最大值，已生成新的批准号（如11YJA760086,则下次的本类别批准号从11YJA760087开始）
		int maxPN = 0;//最大项目编号（如上述的86）
		String hql4pan = "select p.approvalNumber from ProjectApplication p where p.type = 'general' and p.year =:defaultYear and p.isGranted = 1 and p.approvalNumber is not null";
		List pansList = baseService.query(hql4pan, map);//公示的项目的id
		for(Object panObj : pansList){//pan表示 project approvalNumber
			String pan = (panObj == null) ? "" : panObj.toString();
			int len4pan = pan.length();
			int currentPN = Integer.parseInt(pan.substring(len4pan - 3));//将最后三位转换数字（如086 -> 86)
			String beforePNStr = pan.substring(0, len4pan - 3);//项目批准号中项目编号前面的字符子串
			maxPN = (map.get(beforePNStr) == null)? 0 : (Integer) map.get(beforePNStr);//获取本类型已有最大值
			maxPN = (currentPN > maxPN) ? currentPN : maxPN;//与当前值比较
			map.put(beforePNStr, (maxPN == 0) ? null : maxPN);
		}
		
		//立项方式1：当entityIds==null所有公示的项目全部确定立项；否则立项方式2：选定的部分公示的项目确定立项
		if(entityIds == null || entityIds.isEmpty()){
			String hql4Public = "select p.id from ProjectApplication p where p.type = 'general' and p.isGranting > 0 and p.year =:defaultYear and p.isGranted = 0";
			entityIds = baseService.query(hql4Public, map);//公示的项目的ids
		}
		for(Object idObj : entityIds){
			String pid = (idObj == null) ? "" : idObj.toString();//项目id
			ProjectApplication p = (ProjectApplication) baseService.query(ProjectApplication.class, pid);
			if(p == null){
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
				return INPUT;
			}
			//如果当前项目已经立项，则不用再立项
			if(p.getIsGranted() == 1){
				continue;
			}
			//判断项目所属地区
			String hql4Area = "select u.provinceName from University u where u.code =:universityCode";
			map.put("universityCode", p.getUniversityCode().trim());
			List areaList = baseService.query(hql4Area, map);
			if(areaList == null || areaList.isEmpty()){
				jsonMap.put(GlobalInfo.ERROR_INFO, "该项目省份信息缺失");
				return INPUT;
			}
			String provinceName = areaList.get(0).toString();
			//============生成项目批准号============
			//1、项目年度
			aprrovalNumber = (p.getYear()+"").substring(2);//年度(截取后两位)
			
			//2、项目类别
			if(!specialSet.contains(provinceName)){//一般项目
				aprrovalNumber += "Y";	
			}else if(xbSet.contains(provinceName)){//西部项目
				aprrovalNumber += "X";
			}else if(provinceName.equals("新疆维吾尔自治区")){//新疆项目
				aprrovalNumber += "XJ";
			}else if(provinceName.equals("西藏自治区")){//西藏项目
				aprrovalNumber += "XZ";
			}
			//3、项目类型
			if(p.getProjectType() != null && p.getProjectType().trim().equals("规划基金项目")){
				aprrovalNumber += "JA";
			}else if(p.getProjectType() != null && p.getProjectType().trim().equals("青年基金项目")){
				aprrovalNumber += "JC";
			}else if(p.getProjectType() != null && p.getProjectType().trim().equals("自筹经费项目")){
				aprrovalNumber += "JE";
			}
			//3、学科代码(用学科门类)
			String hql4disCode = "select so.code from SystemOption so where so.name =:disciplineType";
			map.put("disciplineType", p.getDisciplineType().trim());
			List codeList = baseService.query(hql4disCode, map);
			if(codeList == null || codeList.isEmpty()){
				jsonMap.put(GlobalInfo.ERROR_INFO, "该项目学科门类信息不完整！");
				return INPUT;
			}
			String disciplineCode = codeList.get(0).toString();
			aprrovalNumber += disciplineCode;
			//4、项目编号
			String key4pn = aprrovalNumber;//按照编号类别生成项目批准号（与上面的map.get(beforePNStr)相关联）
			int pn = (map.get(key4pn) == null)? 1 : (Integer) map.get(key4pn) + 1;
			map.put(key4pn, pn);
			
			String projectNumber = NumberHandle.numFormat(pn, 3);//这里设置项目编号为3位数
			aprrovalNumber += projectNumber;//最终批准号
//			System.out.println(aprrovalNumber);
			
			//============更新数据库============
			String subType = p.getProjectType().trim().toString();//项目类型
			double applyFee = Double.parseDouble(p.getApplyFee().trim().toString());//申请经费
			double approveFee = 0;//批准经费
			if(subType.equals("青年基金项目")){
				approveFee = Math.min(applyFee, 7); 
			}else if(subType.equals("规划基金项目")){
				approveFee = Math.min(applyFee, 9);
			}
			p.setApproveFee(approveFee+"");//批准经费
			p.setApprovalNumber(aprrovalNumber);//项目批准号
			Date approveDate = new Date();
			p.setIsGranted(1);//确定立项
			p.setGrantedDate(approveDate);//确定立项时间
			p.setFinalAuditDate(approveDate);
			p.setFinalAuditResult(2);
			Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
			p.setFinalAuditorName(visitor.getUser().getUsername());
			//新建立项项目
			ProjectGranted granted = new ProjectGranted(p.getType());
			projectService.setGrantedInfoFromApp(p, granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(p);
			baseService.modify(p);
			baseService.modify(granted);
		}
		return SUCCESS;
	}
	
	/**
	 * 导出公示项目清单
	 * @return
	 * @throws Exception 
	 */
	public String exportPublicityList(){
//		ek = new ProjectKit();
		try {
			ek.exportPublicityList();
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "导出出错！");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public ProjectKit getEk() {
		return ek;
	}
	public void setEk(ProjectKit ek) {
		this.ek = ek;
	}
	public void setPublicFlag(int publicFlag) {
		this.publicFlag = publicFlag;
	}
	public int getPublicFlag() {
		return publicFlag;
	}

	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String pageBufferId() {
		return PublicityAction.PAGE_BUFFER_ID;
	}

	public void setListFlag(int listFlag) {
		this.listFlag = listFlag;
	}

	public int getListFlag() {
		return listFlag;
	}
}
