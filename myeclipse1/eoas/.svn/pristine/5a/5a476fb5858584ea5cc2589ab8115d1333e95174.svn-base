package eoas.action.recruitment;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import eoas.service.IUploadService;
import eoas.tool.FileRecord;

/**
 * 文件上传统一处理action，必须配合uploadify-ext插件使用
 * @author Isun
 *
 */
public class UploadAction extends ActionSupport {

	private static final long serialVersionUID = 2946004816651195291L;

	/**
	 * 上传的文件，默认情况下上传后存储在temp目录下
	 */
	private File file;
	
	/**
	 * 上传文件的原始文件名
	 */
	private String fileFileName;
	
	/**
	 * 上传文件的原始ContentType
	 */
	private String fileContentType;

	/**
	 * 文件组id
	 */
	private String groupId;
	
	/**
	 * 文件id，由后台自行生成
	 */
	private String fileId;
	
	/**
	 * 传往uploadify前端的数据载体
	 */
	private Map json;
	
	/**
	 * 用于获取文件上传临时存储路径
	 */
	private final String TEMP_UPLOAD_PATH = "tempUploadPath";

	
	private transient IUploadService uploadService;
	
	/**
	 * 重置文件组，需要在初始化文件上传表单时执行
	 * @return
	 */
	public String resetGroup() {
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 接受uploadify上传文件
	 * 接受一个groupId参数，将该文件放入groupId对应的文件组
	 * @return
	 * @throws IOException
	 */
	public String upload() throws IOException {
		Map<String, Object> sc = ActionContext.getContext().getApplication();

		/*
		 * 将上传的文件保存在/{sc.get(TEMP_UPLOAD_PATH)}/{session.id}/{原始文件名}
		 */
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		File dir = new File(ServletActionContext.getServletContext().getRealPath(sc.get(TEMP_UPLOAD_PATH) + "/" + sessionId.replaceAll("\\W+", "")));

		dir.mkdirs();
		File saveFile = new File(dir, fileFileName);
		/*
		 * 文件上传后保存在struts2自身的临时目录，先尝试移动至项目指定的临时目录
		 */
		if (!file.renameTo(saveFile)) {
			/*
			 * 如果移动失败，则尝试复制到目的地
			 */
			FileUtils.copyFile(file, saveFile);
		}
		json = new HashMap<String, String>();
		/*
		 * 自动生成一个文件id，并返回前端
		 */
		json.put("fileId", uploadService.addFile(groupId, saveFile));	//auto-generated fileId 
		return SUCCESS;
	}

	/**
	 * 点击uploadify前端的"X"时调用的action
	 * 表示舍弃该文件
	 * [注意]:对文件的操作并非立刻执行，而当uploadService.flush()执行后才对文件执行操作。
	 * @return
	 */
	public String discard() {
		uploadService.discardFile(groupId, fileId);
		return SUCCESS;
	}
	
	/**
	 * 获取指定group的现有文件的概要信息，包括:文件id、原始文件名、文件大小
	 * 主要用于在修改页面显示已有的文件
	 * @return
	 */
	public String fetchGroupFilesSummary() {
		List<String[]> res = new ArrayList<String[]>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			res.add(new String[]{
				fileRecord.getId(),
				fileRecord.getFileName(),
				fileRecord.getOriginal().length() + ""
			});
		}
		json = new HashMap<String, String>();
		json.put("groupFilesSummary", res);
		return SUCCESS;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Map getJson() {
		return json;
	}

	public void setJson(Map json) {
		this.json = json;
	}

	
}
