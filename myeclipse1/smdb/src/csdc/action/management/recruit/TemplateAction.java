package csdc.action.management.recruit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import csdc.bean.JobTemplate;
import csdc.bean.Template;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.bean.FileRecord;

/**
 * 招聘的模板管理
 * @author suwb
 *
 */
public class TemplateAction extends InnerRecruitAction {
	
	private static final long serialVersionUID = 1L;
	
	private final static String HQL = "select t.name, t.description, t.date, t.id from Template t ";
	private final static String[] COLUMN = {
		"t.name",
		"t.description, t.name",
		"t.date, t.name"
	};// 排序列
	private final static String PAGE_NAME = "templatePage";
	private final static String[] SEARCH_CONDITIONS = {
		"LOWER(t.name) like :keyword",
		"LOWER(t.description) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "t.id";// 缓存id
	
	@Override
	public void simpleSearchBaseHql(StringBuffer hql, Map map) {
		hql.append(HQL());
	}
	
	//进入模板添加
	public String toAdd(){
		String groupId = "template_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	//模板添加
	public String add(){
		Template t = new Template();
		t.setDescription(description);
		t.setName(name);
		t.setDate(new Date());
		//处理模板附件
		String groupId = "template_add";
		String savePath = (String) ApplicationContainer.sc.getAttribute("RecruitTemplateUploadPath");//设置模板的路径
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newFilePath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			t.setTemplateFile(newFilePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(t.getTemplateFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		entityId = dao.add(t);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	//进入模板修改
	public String toModify(){
		Template t = (Template)dao.query(Template.class, entityId);
		//将已有的附件加入文件组，以在编辑页面显示
		String groupId = "template_" + t.getId();
		uploadService.resetGroup(groupId);
		String fileRealPath = ApplicationContainer.sc.getRealPath(t.getTemplateFile());
		if (fileRealPath != null) {
			uploadService.addFile(groupId, new File(fileRealPath));
		}
		jsonMap.put("entityId", entityId);
		return SUCCESS;
	}
	//模板修改
	public String modify(){
		Template t = (Template)dao.query(Template.class, entityId);
		//处理附件
		String groupId = "template_" + t.getId();
		String fileSavePath = (String) ApplicationContainer.sc.getAttribute("RecruitTemplateUploadPath");//设置附件的路径
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newFilePath = FileTool.getAvailableFilename(fileSavePath, fileRecord.getOriginal());
			t.setTemplateFile(newFilePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(t.getTemplateFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		t.setDescription(description);
		t.setName(name);
		dao.modify(t);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	//进入模板查看
	public String toView(){
		jsonMap.put("entityId", entityId);
		return SUCCESS;
	}
	//模板查看
	public String view(){
		Template t = dao.query(Template.class, entityId);
		jsonMap.put("name", t.getName());
		jsonMap.put("description", t.getDescription());
		jsonMap.put("date", t.getDate());
		jsonMap.put("id", t.getId());
		jsonMap.put("templateFile", t.getTemplateFile());
		String fileSize = null;
		if (t.getTemplateFile() != null) {//文件是否存在的判断
			InputStream is = null;
			is = ApplicationContainer.sc.getResourceAsStream(t.getTemplateFile());
			if (null != is) {
				try {
					fileSize = baseService.accquireFileSize(is.available());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
		jsonMap.put("fileSize", fileSize);
		return SUCCESS;
	}
	//模板删除
	public String delete(){
		for(String id : entityIds){
			Template t = dao.query(Template.class, id);
			List<String> jtIds = dao.query("select jt.id from JobTemplate jt left join jt.template t where t.id = ?", id);
			for(String jtId : jtIds){
				JobTemplate jt = dao.query(JobTemplate.class, jtId);
				dao.delete(jt);
			}
			dao.delete(t);
		}
		return SUCCESS;
	}
	//文件是否存在的判断
	public String validateFile() throws UnsupportedEncodingException{
		Template t = dao.query(Template.class, entityId);
		filePath = t.getTemplateFile();
		String filename = new String(filePath.getBytes("iso8859-1"),"utf-8");
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			session.put("errorInfo", "文件不存在！");
		}
		return SUCCESS;
	}
	//附件流
	public InputStream getTargetFile() throws Exception{
		Template t = (Template)dao.query(Template.class, entityId);
		filePath = t.getTemplateFile();
		String filename="";
		if(filePath != null && filePath.length()!=0){
			filename=new String(filePath.getBytes("iso8859-1"),"utf-8");
			filePath=new String(t.getName().getBytes(), "ISO8859-1") + "." + filename.split("\\.")[1];
			return ApplicationContainer.sc.getResourceAsStream(filename);
		}
		return null;
	}
	
	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String HQL() {
		return HQL;
	}

	@Override
	public String[] searchConditions() {
		return SEARCH_CONDITIONS;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
}
