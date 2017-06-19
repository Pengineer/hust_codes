package org.csdc.controller.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.domain.UploadForm;
import org.csdc.service.imp.UploadService;
import org.csdc.tool.bean.FileRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 上传公用控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
public class UploadController extends BaseController{

	@Autowired
	private UploadService uploadService;
	
	/**
	 * 上传文件到临时目录
	 * @param request
	 * @param uploadForm 上传表单
	 * @return
	 * @throws IOException
	 */
    @RequestMapping(value="/upload/upload", produces="application/json")
    @ResponseBody
    public Object upload(HttpServletRequest request,UploadForm uploadForm) throws IOException{
    	String sessionId = request.getSession().getId();
		File dir = new File((application.getTempPath() + "/" + sessionId.replaceAll("\\W+", "")));
		dir.mkdirs();
		File saveFile = new File(dir, uploadForm.getFile().getOriginalFilename());
		FileUtils.copyInputStreamToFile(uploadForm.getFile().getInputStream(), saveFile);
		
		/*
		 * 自动生成一个文件id，并返回前端
		 */
		Map jsonMap = new HashMap();
		jsonMap.put("fileId", uploadService.addFile(uploadForm.getGroupId(), saveFile,dir.getAbsolutePath()));	//auto-generated fileId
		jsonMap.put("result", "success");
		return jsonMap;
    }
    
	/**
	 * 点击uploadify前端的"X"时调用的action
	 * 表示舍弃该文件
	 * [注意]:对文件的操作并非立刻执行，而当uploadService.flush()执行后才对文件执行操作。
	 * @return
	 */    
    @RequestMapping(value="/upload/discard", produces="application/json")
    @ResponseBody
    public Object discard(HttpServletRequest request,HttpServletResponse response,UploadForm uploadForm) {
		uploadService.discardFile(uploadForm.getGroupId(), uploadForm.getFileId());
		return new JsonData();
    }  

    /**
	 * 获取指定group的现有文件的概要信息，包括:文件id、原始文件名、文件大小
	 * 主要用于在修改页面显示已有的文件
	 * @return
	 */   
    @RequestMapping(value="/upload/fetchGroupFilesSummary", produces="application/json")
    @ResponseBody
    public Object fetchGroupFilesSummary(HttpServletRequest request,HttpServletResponse response,UploadForm uploadForm){
		Map jsonMap = new HashMap();
    	List<String[]> res = new ArrayList<String[]>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(uploadForm.getGroupId())) {
			res.add(new String[]{
				fileRecord.getId(),
				fileRecord.getFileName(),
				fileRecord.getOriginal().length() + ""
			});
		}
		jsonMap.put("groupFilesSummary", res); 
		jsonMap.put("result", "success");
		return jsonMap;
    }	 

}
