package org.csdc.controller.fm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.model.Account;
import org.csdc.model.Document;
import org.csdc.model.Version;
import org.csdc.service.imp.CategoryService;
import org.csdc.service.imp.DocumentService;
import org.csdc.service.imp.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 版本管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/fm/version")
public class VersionController extends BaseController{
	
	public static final String GROUP_ID = "checkin";
	@Autowired
	protected UploadService uploadService;
	
	@Autowired 
	private DocumentService documentService;
	
	/**
	 * 显示历史版本列表
	 * @param id 文档ID
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION')") 
	@RequestMapping(value="/list/{id}", produces="application/json")
    @ResponseBody
	public Object list(@PathVariable String id,HttpServletRequest request){    	
    	Map paraMap = new HashMap();
    	paraMap.put("id", id);
    	String hql = "select a.id,a.type,a.title,a.fileSize,a.path,ac.name,a.date,a.version,a.blockId,a.fileId,a.comment from Version a left join a.account ac  where a.document.id=:id order by a.date desc,a.id";    	
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}
	
	/**
	 * 文档检入操作，只有检出者才可以检入
	 * @param id 文档ID
	 * @return
	 * @throws IOException 
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION_CHECKIN')") 
	@RequestMapping(value="/checkin/{id}", produces="application/json")
	@ResponseBody
	public Object checkIn(@PathVariable String id,@RequestParam String comment,HttpServletRequest request) throws IOException{		
		JsonData jsonData = new JsonData();
		Document document = baseDao.query(Document.class,id);
		String accountName = (String) request.getSession().getAttribute("accountName");
		Account account = getSessionAccount(request);
		if((!document.getLocked()) || (document.getLocked() && accountName.equals(document.getLockedAccount().getName()))){
			documentService.checkIn(document, account, comment);
		}else{
			jsonData.success = false;
			jsonData.msg = "文件被他人锁定";
		}
		return jsonData;
	}
	
	/**
	 * 文档检出。功能和下载类似，只是加了个锁，禁止其他人修改文档
	 * @param id 文档ID
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION_CHECKOUT')") 
	@RequestMapping(value="/checkout/{id}", produces="application/json")
	@ResponseBody
	public Object checkOut(@PathVariable String id,HttpServletRequest request){		
		return lock(id, request); //上锁并返回下载链接，在前台控制
	}
	
	/**
	 * 文档上锁。禁止其他人修改文档
	 * @param id 文档ID
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION_LOCK')") 
	@RequestMapping(value="/lock/{id}", produces="application/json")
	@ResponseBody
	public Object lock(@PathVariable String id,HttpServletRequest request){
		JsonData jsonData = new JsonData();
		Document document = baseDao.query(Document.class,id);
		if(null !=document.getLocked() && document.getLocked()==true){
			jsonData.success = false;
			jsonData.msg = "文件已经上锁";
		}else{
			documentService.lock(id, request);
		}
		return jsonData;
	}
	
	/**
	 * 文档解锁
	 * @param id 文档ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION_UNLOCK')") 
	@RequestMapping(value="/unlock/{id}", produces="application/json")
	@ResponseBody
	public Object unlock(@PathVariable String id){
		JsonData jsonData = new JsonData();
		Document document = baseDao.query(Document.class,id);
		if(document.getLocked()){
			document.setLocked(false);
			document.setLockedAccount(null);
			baseDao.modify(document);
		}
		return jsonData;
	}	
	
	/**
	 * 进入检入时文件上传列表
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION_CHECKIN')") 
	@RequestMapping(value="/toCheckIn")
	public Object toCheckIn(){
		return "fm/version/toCheckIn";
	}
	
	/**
	 * 重置文档上传
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_VERSION_CHECKIN')") 
	@RequestMapping(value="/resetUpload", produces="application/json")
	@ResponseBody
	public Object resetUpload(){
		uploadService.resetGroup(GROUP_ID);
		return new JsonData();
	}
	
	/**
	 * 下载历史版本文档 
	 * @param id 版本ID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/download/{id}",method = RequestMethod.GET)
	public void doDownload(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Version version = baseDao.query(Version.class,id);
		Document versionDoc = version.toDocument();
		String fileName = versionDoc.getFileName();
		String fullPath = getFetchPath(versionDoc);
		File downloadFile = new File(fullPath);
		if(!downloadFile.exists()){
			documentService.downloadFileFromHdfs(versionDoc);
		}
		FileInputStream inputStream = new FileInputStream(fullPath);
		response.setContentType("application/octet-stream, charset=utf-8");
		response.setContentLength((int) downloadFile.length());
		
		// set headers for the response
		String headerKey = "Content-Disposition";
		System.out.println(downloadFile.getName());
		String headerValue = String.format("attachment; filename=\"%s\"",
				new String(fileName.getBytes(), "ISO8859-1")); //完美解决中文乱码问题，使用正则表达式解决
		response.setHeader(headerKey, headerValue);	
		
		// get output stream of the response
		OutputStream outStream = response.getOutputStream();
		request.getSession().getServletContext().getResourceAsStream(fullPath);
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}
}
