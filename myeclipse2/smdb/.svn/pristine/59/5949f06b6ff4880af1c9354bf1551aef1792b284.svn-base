package csdc.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.tool.SessionContext;
import csdc.tool.info.GlobalInfo;

@SuppressWarnings("unchecked")
public class SwfuploadAction extends ActionSupport {

	private static final long serialVersionUID = 879746621526710875L;
	private File file;
	private File postponementPlanFile;
	private String filename;
	private String fileId;
	private String[] fileIds;
	private String sessionId;
	private String uploadKey;

	private static HashMap<String, String> keySessionMap = new HashMap<String, String>();

	public synchronized static void addKeySessionPair(String uploadKey, String sessionId){
		keySessionMap.put(uploadKey, sessionId);
	}
	public synchronized static void removeKeySessionPair(String uploadKey){
		keySessionMap.remove(uploadKey);
	}

	public String toUpload(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		uploadKey = (String) session.getAttribute("uploadKey");
		sessionId = session.getId();

		return SUCCESS;
	}

	public String upload() throws IOException{
		synchronized (keySessionMap) {
			sessionId = keySessionMap.get(uploadKey);
		}
		SessionContext sessionContext = SessionContext.getInstance();
		HttpSession httpSession = sessionContext.getSession(sessionId);

		if (httpSession == null || httpSession.getAttribute(GlobalInfo.LOGINER) == null){
			return ERROR;
		}

		Map<String, Object> sc = ActionContext.getContext().getApplication();
		File dir = new File(ServletActionContext.getServletContext().getRealPath(sc.get("tempUploadPath") + "/" + sessionId + "/" + fileId));
		dir.mkdirs();
		File saveFile = new File(dir, filename);
		boolean success = (file != null) ? file.renameTo(saveFile) : postponementPlanFile.renameTo(saveFile);
		System.out.println("renameTo : " + success);
		if (!success){
			FileUtils.copyFile(file, saveFile);
			System.out.println("FileUtils.copyFile over!");
		}

		return SUCCESS;
	}

	public String completeUpload(){
		if (fileIds == null){
			fileIds = new String[0];
		}
		Map<String, Object> sc = ActionContext.getContext().getApplication();
		String basePath = ServletActionContext.getServletContext().getRealPath((String) sc.get("tempUploadPath"));
		for (String st : fileIds) {
			File path = new File(basePath + "/" + st);
			if (path.exists()){
				Iterator it = FileUtils.iterateFiles(path, null, false);
				File curFile = it.hasNext()? (File)it.next() : new File("");
				System.out.println(curFile.getName());
			}
		}
		return SUCCESS;
	}

	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public File getPostponementPlanFile() {
		return postponementPlanFile;
	}
	public void setPostponementPlanFile(File postponementPlanFile) {
		this.postponementPlanFile = postponementPlanFile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}

}
