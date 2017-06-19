package org.csdc.controller.fm.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.artofsolving.jodconverter.office.OfficeException;
import org.csdc.controller.BaseController;
import org.csdc.model.Document;
import org.csdc.service.imp.DocumentService;
import org.csdc.tool.FileTool;
import org.csdc.tool.PDFConverter;
import org.csdc.tool.SWFToolsSWFConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.protobuf.UnknownFieldSet.Field;

/**
 * 文档预览控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/fm/document/preview")
public class PreviewController extends BaseController{
	private static final int BUFFER_SIZE = 4096; //文件下载缓冲区大小
	
	@Autowired
	private DocumentService documentService;
	
	/**
	 * 文档在线预览
	 * @param id 文档ID
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_PREVIEW')") 
	@RequestMapping(value="/{id}")
	public ModelAndView preview(@PathVariable String id,HttpServletRequest request) throws OfficeException, IOException  {
		String [] supportTypes = {"png","jpg","jpeg","bmp","doc","docx","xls","xlsx","ppt","pdf","txt"};
		ArrayList<String> supports = new ArrayList<String>();
		for (int i = 0; i < supportTypes.length; i++) {
			supports.add(supportTypes[i]);
		}
		Document doc = baseDao.query(Document.class, id);
		documentService.downloadFileFromHdfs(doc);
		String fetchPath = application.getParameter("ROOT")+File.separator+"hdfs"+ File.separator + "fetch"+File.separator+ doc.getPath()+"."+doc.getType();
		String pdfPath = application.getPreviewPath()+File.separator+doc.getPath()+".pdf";
		String swfPath = FileTool.getFilePrefix(pdfPath)+".swf";
		
		File swFile = new File(swfPath);
		PDFConverter pdfConverter = PDFConverter.getInstance();
		SWFToolsSWFConverter swfConverter = new SWFToolsSWFConverter();
		if(!swFile.exists()) {
			if(doc.getType().equals("pdf")) {
				FileUtils.copyFile(new File(fetchPath), new File(pdfPath));
				swfConverter.convert2SWF(pdfPath,swfPath);								
			} else if(doc.getType().equals("txt")) {//带txt文件去掉txt后缀后转换才不会出现乱码
				String txtFetch = FileTool.getFilePrefix(fetchPath);
				FileUtils.copyFile(new File(fetchPath), new File(txtFetch));
				pdfConverter.convert2PDF(txtFetch,pdfPath);
				swfConverter.convert2SWF(pdfPath,swfPath);							
			}else if(supports.contains(doc.getType())){
				pdfConverter.convert2PDF(fetchPath,pdfPath);
				swfConverter.convert2SWF(pdfPath,swfPath);	
			}else {
				throw new OfficeException("");
			}
		}		
		request.setAttribute("id", id);
		return new ModelAndView("fm/preview");
	}
	
	/**
	 * 预览页面swf加载
	 * @param id 文档ID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_PREVIEW')") 
	@RequestMapping(value="/swf/{id}",method = RequestMethod.GET)
	public void getPreview(@PathVariable String id,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Document doc = baseDao.query(Document.class, id);
		String swfPath = application.getPreviewPath()+File.separator+doc.getPath()+".swf";
		File downloadFile = new File(swfPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);
		response.setContentType("application/octet-stream, charset=utf-8");
		response.setContentLength((int) downloadFile.length());
		System.out.println(response.getContentType());

		// set headers for the response
		String headerKey = "Content-Disposition";
		System.out.println(downloadFile.getName());
		String headerValue = String.format("attachment; filename=\"%s\"",
				new String(downloadFile.getName().getBytes(), "ISO8859-1")); //完美解决中文乱码问题，使用正则表达式解决
		response.setHeader(headerKey, headerValue);	
		
		// get output stream of the response
		OutputStream outStream = response.getOutputStream();
		request.getSession().getServletContext().getResourceAsStream(swfPath);
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		
		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();		
	}	
	
}
