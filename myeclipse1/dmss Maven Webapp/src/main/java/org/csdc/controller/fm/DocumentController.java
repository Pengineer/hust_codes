/**
 * 
 */
package org.csdc.controller.fm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.csdc.bean.DocCutBoard;
import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.domain.fm.DocForm;
import org.csdc.domain.fm.DocUplaodForm;
import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;
import org.csdc.model.Account;
import org.csdc.model.Document;
import org.csdc.service.imp.CategoryService;
import org.csdc.service.imp.DocumentService;
import org.csdc.service.imp.IndexService;
import org.csdc.service.imp.MergeService;
import org.csdc.service.imp.SearchService;
import org.csdc.service.imp.SplitService;
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
 * 文档操作控制器
 * 
 * @author lvjia
 * @date 2014-6-15
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping("/fm/document")
public class DocumentController extends BaseController {

	@Autowired
	protected DocumentService documentService;

	@Autowired
	protected CategoryService categoryService;

	@Autowired
	private SearchService searchService;

	@Autowired
	protected UploadService uploadService;

	@Autowired
	protected IndexService indexService;

	// TODO 临时测试merge service
	@Autowired
	protected MergeService mergeService;

	// TODO 临时测试split service
	@Autowired
	protected SplitService splitService;

	public static String GROUP_ID = "file_add";

	/**
	 * 返回指定分类下的所有文档
	 * 
	 * @param id
	 *            分类ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/{id}", produces = "application/json")
	@ResponseBody
	public Object list(@PathVariable String id, HttpServletRequest request) {
		Map paraMap = new HashMap();
		paraMap.put("pid", id);
		// TODO 这里对于test1 blockId为空
		String hql = "select a.id,a.type,a.title,a.fileSize,ac.name,a.createdDate,a.version,a.locked,a.blockId,a.fileId,a.indexed from Document a left join a.creator ac left join a.categories c where c.id =:pid and a.deleted=false order by a.createdDate desc,a.id";
		if (request.getParameter("search") != null
				&& request.getParameter("search").length() > 0) {
			paraMap.put("key", "%"
					+ request.getParameter("search").toLowerCase() + "%");
			hql = "select a.id,a.type,a.title,a.fileSize,ac.name,a.createdDate,a.version,a.locked,a.blockId,a.fileId,a.indexed from Document a left join a.creator ac left join a.categories c where c.id =:pid and a.deleted=false and LOWER(a.title) like :key or LOWER(ac.name) like :key order by a.createdDate desc,a.id";
		}
		GridModel grid = new GridModel(request, baseDao, hql, paraMap);
		return grid.getResults();
	}

	/**
	 * 文档下载
	 * 
	 * @param id
	 *            文档ID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_DOWNLOAD')")
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public Object doDownload(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JsonData jsonData = new JsonData();
		Document doc = baseDao.query(Document.class, id);
		String fileName = doc.getFileName();
		String fullPath = application.getRootPath() + File.separator + "hdfs"
				+ File.separator + "fetch" + File.separator + doc.getPath()
				+ "." + doc.getType();
		File downloadFile = new File(fullPath);
		if (!downloadFile.exists()) {
			if (null == doc.getBlockId() && null == doc.getFileId()) { // 从上传缓存中取
				String tempPath = application.getRootPath() + File.separator
						+ "temp" + File.separator
						+ request.getSession().getId() + File.separator
						+ doc.getFileName();
				FileUtils.copyFile(new File(tempPath), new File(fullPath));
			} else { // 在HDFS中
				if (!documentService.downloadFileFromHdfs(doc)) {
					jsonData.success = false;
					return jsonData;
				}
			}
		}
		FileInputStream inputStream = new FileInputStream(fullPath);
		response.setContentType("application/octet-stream, charset=utf-8");
		response.setContentLength((int) downloadFile.length());
		System.out.println(response.getContentType());

		// set headers for the response
		String headerKey = "Content-Disposition";
		System.out.println(downloadFile.getName());
		String headerValue = String.format("attachment; filename=\"%s\"",
				new String(fileName.getBytes(), "ISO8859-1")); // 完美解决中文乱码问题，使用正则表达式解决
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
		return jsonData;
	}

	/**
	 * 获取指定文档大小
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_DOWNLOAD')")
	@RequestMapping(value = "/accquireFileSize/{id}", produces = "application/json")
	@ResponseBody
	public Object accquireFileSize(@PathVariable String id,
			HttpServletRequest request) {
		JsonData jsonData = new JsonData();
		Document doc = baseDao.query(Document.class, id);
		long fileSize = doc.getFileSize();
		jsonData.data.put("fileSize", fileSize);
		return jsonData;
	}

	/**
	 * 进入下载页面
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_UPLOAD')")
	@RequestMapping(value = "/toUpload")
	public Object toUpload() {
		return "fm/toUpload";
	}

	/**
	 * 重置上传页面
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_UPLOAD')")
	@RequestMapping(value = "/resetUpload", produces = "application/json")
	@ResponseBody
	public Object resetUpload() {
		uploadService.resetGroup(GROUP_ID);
		return new JsonData();
	}

	/**
	 * 文档上传
	 * 
	 * @param request
	 * @param docUplaodForm
	 *            文档上传表单
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_UPLOAD')")
	@RequestMapping(value = "/upload", produces = "application/json")
	@ResponseBody
	public Object upload(HttpServletRequest request, DocUplaodForm docUplaodForm)
			throws Exception {
		JsonData jsonData = new JsonData();
		String id = null;
		Account account = getSessionAccount(request);
		if (null != docUplaodForm.getAutoUnZip()
				&& docUplaodForm.getAutoUnZip().equals("on")) {// 要求自解压
			documentService.saveUploadsAndUnZip(docUplaodForm, account);
		} else {// 不要求自解压
			id = documentService.saveUploads(docUplaodForm, account);
		}
		jsonData.data.put("id", id);
		return jsonData;
	}

	/**
	 * 第三方上传接口
	 * 
	 * @param request
	 * @param form
	 *            第三方上传表单
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_UPLOAD')")
	@RequestMapping(value = "/webUpload", produces = "application/json")
	@ResponseBody
	public Object webUpload(HttpServletRequest request, ThirdUploadForm form)
			throws Exception {
		String sessionId = request.getSession().getId();
		JsonData jsonData = new JsonData();
		Account account = getSessionAccount(request);
		if (null == form.getTitle() || null == form.getCategoryPath()
				|| null == form.getFileName()) {
			jsonData.success = false;
			jsonData.msg = "参数不合法";
			return jsonData;
		}
		String id = documentService.createDocumentFromThird(form, account,
				sessionId);
		jsonData.data.put("id", id);
		return jsonData;
	}

	/**
	 * 第三方检入接口
	 * 
	 * @param request
	 * @param form
	 *            第三方上传表单
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_UPLOAD')")
	@RequestMapping(value = "/webCheckIn", produces = "application/json")
	@ResponseBody
	public Object webCheckIn(HttpServletRequest request, ThirdCheckInForm form)
			throws Exception {
		String sessionId = request.getSession().getId();
		JsonData jsonData = new JsonData();
		Account account = getSessionAccount(request);
		if (null == form.getTitle() || form.getTitle().length() == 0) {
			jsonData.success = false;
			jsonData.msg = "参数不合法";
			return jsonData;
		}
		String id = documentService.checkInDocumentFromThird(form, account,
				sessionId);
		jsonData.data.put("id", id);
		return jsonData;
	}

	/**
	 * 删除文档
	 * 
	 * @param id
	 *            文档的ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_DELETE')")
	@RequestMapping(value = "/delete/{id}", produces = "application/json")
	@ResponseBody
	public Object delete(@PathVariable String id) {
		JsonData jsonData = new JsonData();
		boolean result = documentService.delete(id);
		jsonData.success = result;
		return jsonData;
	}
	

	/**
	 * 进入修改文档
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_MODIFY')")
	@RequestMapping(value = "/toModify/{id}", produces = "application/json")
	@ResponseBody
	public Object toModify(@PathVariable String id) {
		Map jsonMap = new HashMap();
		jsonMap.put("form", documentService.getDocForm(id));
		jsonMap.put("success", true);
		return jsonMap;
	}

	/**
	 * 执行修改文档
	 * 
	 * @param form
	 *            文档表单对象
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_MODIFY')")
	@RequestMapping(value = "/modify", produces = "application/json")
	@ResponseBody
	public Object modify(DocForm form) {
		documentService.modify(form);
		return new JsonData();
	}

	/**
	 * 彻底删除文档
	 * 
	 * @param id
	 *            文档的ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_DELETE')")
	@RequestMapping(value = "/deletem/{id}", produces = "application/json")
	@ResponseBody
	public Object deletem(@PathVariable String id) {
		JsonData jsonData = new JsonData();
		if (documentService.deleteRecycle(id)) {// 彻底删除文档
			jsonData.success = true;
			return jsonData;
		} else {
			jsonData.msg = "can't delete this document from recycle,just execute delete";
			jsonData.success = false;
			return jsonData;
		}

	}

	/**
	 * 从分类中删除文档
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_DELETE')")
	@RequestMapping(value = "/deleteFromCategory", produces = "application/json")
	@ResponseBody
	public Object deleteFromCategory(@RequestParam String documentId,
			@RequestParam String categoryId) {
		JsonData jsonData = new JsonData();
		boolean result = documentService.deleteFromCategory(documentId,
				categoryId);
		jsonData.success = result;
		return jsonData;
	}

	/**
	 * 文档复制
	 * 
	 * @param id
	 *            被复制的文档的ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_COPY')")
	@RequestMapping(value = "/copy/{id}", produces = "application/json")
	@ResponseBody
	public Object copy(@PathVariable String id, HttpServletRequest request) {
		String curCid = (String) request.getAttribute("curCid");
		DocCutBoard docCutBoard = new DocCutBoard(id, curCid, false);
		request.getSession().setAttribute("docCutBoard", docCutBoard);
		return new JsonData();
	}

	/**
	 * 文档剪切
	 * 
	 * @param id
	 *            被剪切的文档ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_CUT')")
	@RequestMapping(value = "/cut/{id}", produces = "application/json")
	@ResponseBody
	public Object cut(@PathVariable String id, HttpServletRequest request) {
		JsonData jsonData = new JsonData();
		String curCid = (String) request.getParameter("curCid");
		DocCutBoard docCutBoard = new DocCutBoard(id, curCid, true);
		request.getSession().setAttribute("docCutBoard", docCutBoard);
		return jsonData;
	}

	/**
	 * 文档粘贴
	 * 
	 * @param id
	 *            文档ID
	 * @param pid
	 *            目的分类目录ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_PASTE')")
	@RequestMapping(value = "/paste", produces = "application/json")
	@ResponseBody
	public Object paste(@RequestParam String destCid, HttpServletRequest request) {
		JsonData jsonData = new JsonData();
		if (null == request.getSession().getAttribute("docCutBoard")
				&& null != destCid) {
			jsonData.success = false;
		} else {
			jsonData.success = true;
			DocCutBoard docCutBoard = (DocCutBoard) request.getSession()
					.getAttribute("docCutBoard");
			documentService.paste(docCutBoard, destCid);
		}
		return jsonData;
	}

	/**
	 * 更新文档的索引
	 * 
	 * @param id
	 *            要更新索引的文档ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_INDEXU')")
	@RequestMapping(value = "/index/update/{id}", produces = "application/json")
	@ResponseBody
	public Object indexUpdate(@PathVariable String id) {
		indexService.updateIndex(id);
		return new JsonData();
	}

	/**
	 * 相似文档检索页面
	 * 
	 * @param id
	 *            文档ID
	 * @return
	 */
	@RequestMapping(value = "/similar/{id}", produces = "application/json")
	@ResponseBody
	public Object similar(@PathVariable String id) {
		List<Document> docs = searchService.getSimilarDocs(id);
		Map result = new HashMap();
		result.put("data", docs);
		result.put("totalCount", docs.size());
		return result;
	}

	/**
	 * 文档智能分类
	 * 
	 * @param id
	 *            文档的ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_CLASSIFY')")
	@RequestMapping(value = "/classify/{id}", produces = "application/json")
	@ResponseBody
	public Object classify(@PathVariable String id) {
		JsonData jsonData = new JsonData();
		String categoryString = documentService.classify(id);
		String CategoryId = categoryService.getCatgeoryIdByCategoryString(
				categoryString, false);
		jsonData.data.put("id", CategoryId);
		jsonData.data.put("name", categoryString);
		return jsonData;
	}

	/**
	 * 文档移动
	 * 
	 * @param id
	 *            要移动的文档那个ID
	 * @param destId
	 *            目的分类ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_MOVE')")
	@RequestMapping(value = "/move/{id}", produces = "application/json")
	@ResponseBody
	public Object move(@PathVariable String id, @RequestParam String destId) {
		JsonData jsonData = new JsonData();
		documentService.move(id, destId);
		return jsonData;
	}

	/**
	 * 获取文档摘要
	 * 
	 * @param id
	 *            文档ID
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FM_DOCUMENT_VIEW')")
	@RequestMapping(value = "/summary/{id}", produces = "application/json")
	@ResponseBody
	public Object summary(@PathVariable String id) {
		JsonData jsonData = new JsonData();
		String summary = documentService.getDocumentSummary(id);
		jsonData.data.put("summary", summary);
		return jsonData;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/mergeFileTest", produces = "application/json")
	@ResponseBody
	public void mergeFileTest() throws IOException {
		mergeService.mergeTmpFile();
	}

	@RequestMapping(value = "/splitFileTest", produces = "application/json")
	@ResponseBody
	public void splitFileTest() {
		try {
			splitService.splitFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 修复文档在在数据库中有记录，但是其c_file_id和c_block_id不存在的情况，首先核查是否存在hdfs上，若不存在则彻底删除文档
	 */
	@RequestMapping(value = "/fixFileInfo", produces = "application/json")
	@ResponseBody
	public Object checkFileExistOnHadoop() throws IOException {
		JsonData jsonData = new JsonData();
		;
		String hql = "select a.id,a.fingerprint from Document a where a.blockId is null";
		List<Object[]> documents = baseDao.query(hql);
		List<String> fileList = documentService.checkFileExist();
		System.out.println("开始搜索fileId为空文件：");
		int count = 0;
		boolean flag = false;
		for (Object[] document : documents) {
			count++;
			System.out.println("正在hdfs上搜索第" + count + "个文件 id为"
					+ (String) document[0]);
			for (String string : fileList) {
				if (string.contains((String) document[1])) {
					System.out.println("找到文件" + string);

					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				// documentService.delete((String)document[0]);
				System.out.println("准备删除文档" + (String) document[0]);
				documentService.deleteRecycle((String) document[0]);
			}

		}
		jsonData.data.put("document", documents);
		jsonData.msg = "there are " + documents.size()
				+ " files doesn't have fileId ";
		return jsonData;
	}
}
