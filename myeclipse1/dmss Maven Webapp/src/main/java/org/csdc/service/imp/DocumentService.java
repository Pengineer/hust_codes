/**
 * 
 */
package org.csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.csdc.bean.Application;
import org.csdc.bean.DocCutBoard;
import org.csdc.controller.fm.DocumentController;
import org.csdc.controller.fm.VersionController;
import org.csdc.domain.fm.DocForm;
import org.csdc.domain.fm.DocUplaodForm;
import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;
import org.csdc.model.Account;
import org.csdc.model.Bookmark;
import org.csdc.model.Category;
import org.csdc.model.Document;
import org.csdc.model.Garbage;
import org.csdc.model.Template;
import org.csdc.model.Version;
import org.csdc.storage.HdfsDAO;
import org.csdc.tool.DatetimeTool;
import org.csdc.tool.DeCompressUtil;
import org.csdc.tool.FileTool;
import org.csdc.tool.MD5;
import org.csdc.tool.TikaTool;
import org.csdc.tool.bean.FileRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.sun.star.uno.RuntimeException;

/**
 * 文档管理业务类
 * 
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
@Transactional
public class DocumentService extends BaseService {
	@Autowired
	protected Application application;

	@Autowired
	protected HdfsDAO hdfs;

	@Autowired
	protected CategoryService categoryService;

	@Autowired
	protected UploadService uploadService;

	@Autowired
	protected SearchService searchService;

	/**
	 * 创建文档
	 * 
	 * @param fileRecord
	 * @param docUplaodForm
	 * @throws IOException
	 */
	public String createDocument(FileRecord fileRecord,
			DocUplaodForm docUplaodForm, Account account) throws IOException {
		// 判断是否要创建分类目录（文件orignal目录不在temp下的视为创建新的分类）
		String pid = docUplaodForm.getCategoryId();
		if ("pid".startsWith("/")) {
			pid = categoryService.getCategoryIdByPath(pid);
		}
		String relativePath = fileRecord.getOriginal().getAbsolutePath()
				.substring(fileRecord.getDir().length() + 1);
		relativePath = FileTool.getDirectory(relativePath);
		relativePath = relativePath.replace("\\", " ");
		String[] folders = relativePath.split(" ");
		for (String folder : folders) {
			if (!folder.isEmpty()) {
				if (!categoryService.isExistSubCategoryName(folder, pid)) {// 不存在则创建
					categoryService.addSubCategory(folder, folder, pid, null);
				}
				pid = (String) baseDao
						.queryUnique(
								"select c.id from Category c where c.name=? and c.parent.id=?",
								folder, pid);
			}
		}
		// 创建文档
		Document document = new Document();
		if (null != docUplaodForm.getTemplateId()
				&& !docUplaodForm.getTemplateId().isEmpty()) {
			Template template = baseDao.query(Template.class,
					docUplaodForm.getTemplateId());
			document.setTemplate(template);
		}
		Category category = baseDao.query(Category.class, pid);
		document.getCategories().add(category);
		document.setCategoryString(categoryService.getFullPath(category.getId()));
		document.setSourceAuthor(docUplaodForm.getSourceAuthor());
		document.setTags(docUplaodForm.getTags());
		if (null != docUplaodForm.getRating()
				&& !docUplaodForm.getRating().isEmpty()) {
			document.setRating(Double.valueOf(docUplaodForm.getRating()));
		}
		document.setCreator(account);
		document.setAccountName(account.getName());
		document.setTitle(fileRecord.getTitle());
		document.setType(fileRecord.getType().toLowerCase());
		String md5 = MD5.getMD5(fileRecord.getOriginal());
		document.setFingerprint(md5);
		document.setPath(md5toPath(md5));
		document.setFileSize(fileRecord.getOriginal().length());
		document.setCreatedDate(new Date());
		document.setLastModifiedDate(new Date());
		document.setVersion(1);
		document.setLocked(false);
		document.setDeleted(false);
		document.setImmutable(false);
		document.setIndexed(false);
		if (application.isHadoopOn()) {
			String[] o = hdfs.push(fileRecord.getOriginal().getAbsolutePath());
			document.setBlockId(o[0]);
			document.setFileId(o[1]);
		} else {
			fileRecord.setDest(new File(getFetchPath(document)));
		}
		uploadService.flush(DocumentController.GROUP_ID);
		return (String) baseDao.add(document);
	}

	/**
	 * 第三方上传创建文档
	 * 
	 * @param form
	 *            第三方上传表单
	 * @param account
	 *            账号
	 * @param sessionId
	 *            会话ID
	 * @return
	 * @throws IOException
	 */
	public String createDocumentFromThird(ThirdUploadForm form,
			Account account, String sessionId) throws IOException {

		// 保存文件到临时目录
		File dir = new File(
				(application.getTempPath() + "/" + sessionId.replaceAll("\\W+",
						"")));
		dir.mkdirs();
		File saveFile = new File(dir, form.getFileName());
		FileUtils.copyInputStreamToFile(form.getFile().getInputStream(),
				saveFile);

		String categoryId = categoryService.getCatgeoryIdByCategoryString(
				form.getCategoryPath(), true); //根据文档路径找到对应的categoryId，上传到特定的目录里面
		Category category = baseDao.query(Category.class, categoryId);
		Document document = new Document();
		document.getCategories().add(category);
		document.setSourceAuthor(form.getSourceAuthor());
		document.setTags(form.getTags());
		if (null != form.getRating() && !form.getRating().isEmpty()) {
			document.setRating(Double.valueOf(form.getRating()));
		}
		document.setCreator(account);
		document.setAccountName(account.getName());
		document.setTitle(form.getTitle());
		document.setType(FileTool.getExtension(saveFile).toLowerCase());
		String md5 = MD5.getMD5(saveFile);
		document.setFingerprint(md5);
		document.setPath(md5toPath(md5));
		document.setFileSize(saveFile.length());
		document.setCreatedDate(new Date());
		document.setLastModifiedDate(new Date());
		document.setVersion(1);
		document.setLocked(false);
		document.setDeleted(false);
		document.setImmutable(false);
		document.setIndexed(false);
		document.setCategoryString(form.getCategoryPath());// 文档的分类信息字段，为文档自动分类提供支持
		if (application.isHadoopOn()) {
			String[] o = hdfs.push(saveFile.getAbsolutePath());
			document.setBlockId(o[0]);
			document.setFileId(o[1]);
		} else {
			FileUtils.copyFile(saveFile, new File(getFetchPath(document)));
		}
		return (String) baseDao.add(document);
	}

	/**
	 * 第三方检入文档
	 * 
	 * @param form
	 *            第三方上传表单
	 * @param account
	 *            账号
	 * @param sessionId
	 *            会话ID
	 * @return
	 * @throws IOException
	 */
	public String checkInDocumentFromThird(ThirdCheckInForm form,
			Account account, String sessionId) throws IOException {
		// String oldPath = document.getPath();
		Document document = baseDao.query(Document.class, form.getId());
		document.setLocked(false);
		document.setLockedAccount(null);
		Version version = new Version();
		version.setComment(form.getComment());
		version.setDate(new Date());
		version.setFileSize(document.getFileSize());
		version.setBlockId(document.getBlockId());
		version.setFileId(document.getFileId());
		version.setPath(document.getPath());
		version.setTitle(document.getTitle());
		version.setType(document.getType());
		version.setVersion(document.getVersion());
		version.setAccount(document.getCreator());
		version.setDocument(document);
		// 获取接收文件
		File dir = new File(
				(application.getTempPath() + "/" + sessionId.replaceAll("\\W+",
						"")));
		dir.mkdirs();
		File saveFile = new File(dir, form.getFileName());
		FileUtils.copyInputStreamToFile(form.getFile().getInputStream(),
				saveFile);
		// 更新document
		document.setTitle(form.getTitle());
		document.setType(FileTool.getExtension(saveFile).toLowerCase());
		String md5 = MD5.getMD5(saveFile);
		document.setFingerprint(md5);
		document.setPath(md5toPath(md5));
		// 设置FileRecord的目的地址
		// fileRecord.setDest(new File(getFetchPath(document)));
		document.setFileSize(saveFile.length());
		document.setCreator(account);
		document.setCreatedDate(new Date());
		document.setLastModifiedDate(new Date());
		document.setVersion(document.getVersion() + 1);
		document.setIndexed(false);

		if (application.isHadoopOn()) {
			String[] o = hdfs.push(saveFile.getAbsolutePath());
			document.setBlockId(o[0]);
			document.setFileId(o[1]);
		} else {
			FileUtils.copyFile(saveFile, new File(getFetchPath(document)));
		}
		baseDao.modify(document);
		baseDao.add(version);
		return document.getId();
	}

	/**
	 * 保存上传的文档
	 * 
	 * @param docUplaodForm
	 * @throws IOException
	 */
	public String saveUploads(DocUplaodForm docUplaodForm, Account account)
			throws IOException {
		String id = null;
		for (FileRecord fileRecord : uploadService
				.getGroupFiles(DocumentController.GROUP_ID)) {
			id = createDocument(fileRecord, docUplaodForm, account);
		}
		uploadService.flush(DocumentController.GROUP_ID);
		return id;
	}

	/**
	 * 解压缩压缩文档并按压缩目录结构保存文档
	 * 
	 * @param docUplaodForm
	 * @throws Exception
	 */
	public void saveUploadsAndUnZip(DocUplaodForm docUplaodForm, Account account)
			throws Exception {
		for (FileRecord fileRecord : uploadService
				.getGroupFiles(DocumentController.GROUP_ID)) {
			if (fileRecord.getType().toLowerCase().equals("zip")
					|| fileRecord.getType().toLowerCase().equals("rar")) {
				String fullPath = fileRecord.getOriginal().getAbsolutePath();
				String dir = fullPath.substring(0,
						fullPath.lastIndexOf(File.separator));
				DeCompressUtil.deCompress(fullPath, dir);
				File zipFile = new File(FileTool.getFilePrefix(fullPath));
				List<File> fileList = new ArrayList<File>();
				FileTool.listFile(zipFile, fileList);
				for (File f : fileList) {
					if (f.isFile()) {// 根据filereocrd生成
						uploadService.addFile(DocumentController.GROUP_ID, f,
								zipFile.getAbsolutePath());
					}
				}
				uploadService.discardFile(DocumentController.GROUP_ID,
						fileRecord.getId());
			}
		}
		saveUploads(docUplaodForm, account);
	}

	/**
	 * 后台导入数据（没有上传这一过程）
	 * 
	 * @param categoryId
	 * @param dir
	 * @throws IOException
	 */
	public void backgroundImport(String categoryId, String dir,
			DocUplaodForm docUplaodForm, Account account) throws IOException {
		List<FileRecord> fileRecords = new ArrayList<FileRecord>();
		File dirFile = new File(dir);
		List<File> fileList = new ArrayList<File>();
		FileTool.listFile(dirFile, fileList);
		for (File f : fileList) {
			if (f.isFile()) {// 根据filereocrd生成
				fileRecords.add(new FileRecord("", f, f.getName(), dir));
			}
		}
		for (FileRecord fileRecord : fileRecords) {
			createDocument(fileRecord, docUplaodForm, account);
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param docId
	 *            源文件
	 * @param destCategoryId
	 *            目的分类
	 */
	public void move(String docId, String destCategoryId) {
		Document document = baseDao.query(Document.class, docId);
		Category category = baseDao.query(Category.class, destCategoryId);
		document.getCategories().clear();
		document.getCategories().add(category);
	}

	/**
	 * 删除指定id的文件
	 * 
	 * @param id
	 */
	public boolean delete(String id) {
		Document document = baseDao.query(Document.class, id);
		document.setDeleted(true);
		/*
		 * if(document.getCategories().size()<=1){//只存在一个目录中，则删除 String path =
		 * application.getReposPath()+File.separator+document.getPath(); File
		 * file = new File(path); boolean flag = file.delete(); if(!flag){
		 * return false; } } baseDao.delete(document);
		 */
		return true;
	}

	/**
	 * 从回收站侧地删除，并删除文档相关书签
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteRecycle(@PathVariable String id) {
		Document document = baseDao.query(Document.class, id);
		System.out.println(document.getBlockId());
		if (document.getBlockId().contentEquals("tmp")) {
			List<Bookmark> bookmarks = baseDao
					.query("select b from Bookmark b left join b.document d where d.id = ?",
							id);
			for (Bookmark bookmark : bookmarks) {
				baseDao.delete(bookmark);
			}
			Garbage garbage = new Garbage();
			garbage.setBlockId(document.getBlockId());
			garbage.setFileId(document.getFileId());
			garbage.setFileSize(document.getFileSize());
			baseDao.add(garbage);
			document.setCategories(null);
			try {
				hdfs.rmr("dmss/" + document.getBlockId() + "/"
						+ document.getFileId());
				baseDao.delete(document);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			//为持久区的文件则不进行删除操作，等待定时拆分文件时进行删除
		} else if(!document.getBlockId().contentEquals("tmp")){
			document.setDeleted(true);
			return true;
		}
		return false;
	}

	/**
	 * 清空回收站
	 * 
	 * @param id
	 * @return
	 */
	public void deleteRecycleAll() {
		List<Document> documents = baseDao
				.query("select d from Document d where d.deleted = true");
		for (Document document : documents) {
			deleteRecycle(document.getId());
		}
	}

	/**
	 * 文档粘贴
	 * 
	 * @param id
	 *            文档Id
	 * @param pid
	 *            目的目录位置
	 */

	public void paste(DocCutBoard docCutBoard, String destCid) {
		// 判断是复制还是剪切
		Document document = baseDao.query(Document.class, docCutBoard.getId());
		Category destCategory = baseDao.query(Category.class, destCid);
		Set categories = document.getCategories();
		categories.add(destCategory);
		if (docCutBoard.isCut()) {
			Category curCategory = baseDao.query(Category.class,
					docCutBoard.getCurCId());
			categories.remove(curCategory);
		}
		document.setCategories(categories);
	}

	/**
	 * 文件上锁
	 * 
	 * @param id
	 *            文档ID
	 * @param request
	 */
	public void lock(String id, HttpServletRequest request) {
		Document document = baseDao.query(Document.class, id);
		Account lockedAccount = getSessionAccount(request);
		document.setLocked(true);
		document.setLockedAccount(lockedAccount);
	}

	/**
	 * 文档检入
	 * 
	 * @param document
	 *            文档对象
	 * @param account
	 *            操作账号
	 * @param comment
	 *            评论
	 * @throws IOException
	 */
	public void checkIn(Document document, Account account, String comment)
			throws IOException {
		document.setLocked(false);
		document.setLockedAccount(null);
		Version version = new Version();
		version.setComment(comment);
		version.setDate(new Date());
		version.setFileSize(document.getFileSize());
		version.setBlockId(document.getBlockId());
		version.setFileId(document.getFileId());
		version.setPath(document.getPath());
		version.setTitle(document.getTitle());
		version.setType(document.getType());
		version.setVersion(document.getVersion());
		version.setAccount(document.getCreator());
		version.setDocument(document);

		List<FileRecord> fileRecords = uploadService
				.getGroupFiles(VersionController.GROUP_ID);
		FileRecord fileRecord = fileRecords.get(0);
		// 更新document
		document.setTitle(fileRecord.getTitle());
		document.setType(fileRecord.getType().toLowerCase());
		String md5 = MD5.getMD5(fileRecord.getOriginal());
		document.setFingerprint(md5);
		document.setPath(md5toPath(md5));
		// 设置FileRecord的目的地址
		// fileRecord.setDest(new File(getFetchPath(document)));
		document.setFileSize(fileRecord.getOriginal().length());
		document.setCreator(account);
		document.setCreatedDate(new Date());
		document.setLastModifiedDate(new Date());
		document.setVersion(document.getVersion() + 1);
		document.setIndexed(false);

		if (application.isHadoopOn()) {
			String[] o = hdfs.push(fileRecord.getOriginal().getAbsolutePath());
			document.setBlockId(o[0]);
			document.setFileId(o[1]);
		} else {
			fileRecord.setDest(new File(getFetchPath(document)));
		}
		uploadService.flush(VersionController.GROUP_ID);
		baseDao.modify(document);
		baseDao.add(version);
	}

	/**
	 * 添加文档书签
	 * 
	 * @param id
	 *            文档ID
	 * @param request
	 */
	public void addDocumentBookmark(String id, HttpServletRequest request) {
		Document document = baseDao.query(Document.class, id);
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle(document.getTitle());
		bookmark.setDescription(document.getTitle());
		bookmark.setType(document.getType());
		Account account = getSessionAccount(request);
		bookmark.setAccount(account);
		bookmark.setDocument(document);
		bookmark.setLastModifiedDate(new Date());
		bookmark.setDeleted(false);
		String categoryId = request.getParameter("categoryId");
		Category category = baseDao.query(Category.class, categoryId);
		bookmark.setCategory(category);
		baseDao.add(bookmark);
	}

	/**
	 * 删除文档书签
	 * 
	 * @param id
	 *            书签ID
	 */
	public void deleteDocumentBookmark(String id) {
		baseDao.delete(Bookmark.class, id);
	}

	/**
	 * 判断该文档书签是否存在
	 * 
	 * @param documentId
	 * @param categoryId
	 * @return
	 */
	public boolean isExistBookmark(String documentId, String categoryId) {
		Bookmark bookmark = (Bookmark) baseDao
				.queryUnique(
						"select b from Bookmark b where b.document.id =? and b.category.id=?",
						documentId, categoryId);
		return (null != bookmark);
	}

	/**
	 * 还原回收站文档那个
	 * 
	 * @param documentId
	 *            文档ID
	 */
	public void restoreRecycle(String documentId) {
		Document document = baseDao.query(Document.class, documentId);
		document.setDeleted(false);
	}

	/**
	 * 删除指定分类文档
	 * 
	 * @param documentId
	 *            文档ID
	 * @param categoryId
	 *            分类
	 * @return
	 */
	public boolean deleteFromCategory(String documentId, String categoryId) {
		Document document = baseDao.query(Document.class, documentId);
		Category category = baseDao.query(Category.class, categoryId);
		document.getCategories().remove(category);
		baseDao.modify(document);
		return true;
	}

	/**
	 * 从HDFS中下载文件到本地的fetch目录 成功下载返回true,否则返回false
	 * 
	 * @param doc
	 * @return
	 */
	public boolean downloadFileFromHdfs(Document doc) {
		String fullPath = getFetchPath(doc);
		if (!new File(fullPath).exists()) {
			try {
				FileUtils.writeByteArrayToFile(new File(fullPath),
						getDocumentByByteFromHdfs(doc));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 从HDFS中获取文档
	 * 
	 * @param doc
	 * @return
	 */
	public byte[] getDocumentByByteFromHdfs(Document doc) {
		if (null != doc.getBlockId() && null != doc.getFileId()) {
			return hdfs.getFileByByte(doc.getBlockId(), doc.getFileId());
		} else {
			return null;
		}
	}

	/**
	 * 获取指定ID的文档二进制内容（先从上传缓存中取，没有则到hdfs中取）
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getDocumentByByte(Document doc) {
		String fullPath = application.getRootPath() + File.separator + "hdfs"
				+ File.separator + "push" + File.separator + doc.getPath();
		File downloadFile = new File(fullPath);
		if (!downloadFile.exists()) {
			return getDocumentByByteFromHdfs(doc);
		} else {
			return FileTool.getFileByByte(fullPath);
		}
	}

	public byte[] getDocumentContent(String id) {
		Document doc = baseDao.query(Document.class, id);
		return getDocumentByByte(doc);
	}

	/**
	 * 获取一篇文档的自动分类结果字符串
	 * 
	 * @param id
	 * @return 所属类别 (例如："\aa\bb")
	 * @throws IOException
	 */
	public String classify(String id) {
		List<Document> docs = searchService.getSimilarDocs(id);
		List<String> list = new ArrayList<String>();
		for (Document d : docs) {
			list.add(d.getCategoryString());
		}
		return getMaxString(list);
	}

	/**
	 * 获取分类字符串集中最大的类别
	 * 
	 * @param list
	 * @return
	 */
	private String getMaxString(List<String> list) {
		String max = "";
		Integer value = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String key : list) {
			if (map.containsKey(key)) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}
		for (String key : map.keySet()) {
			if (map.get(key) > value) {
				max = key;
				value = map.get(key);
			}
		}
		return max;
	}

	/**
	 * 获取文档的表单
	 * 
	 * @param id
	 * @return
	 */
	public DocForm getDocForm(String id) {
		DocForm form = new DocForm();
		Document doc = baseDao.query(Document.class, id);
		String categoryId = doc.getCategories().iterator().next().getId();
		form.setId(id);
		form.setCategoryString(categoryService.getCategoryString(id));
		form.setCategoryId(categoryId);
		form.setLastModifiedDate(DatetimeTool.getDatetimeString(
				doc.getLastModifiedDate(), "yyyy-MM-dd HH:mm:ss"));
		form.setIndexed(doc.getIndexed());
		form.setLocked(doc.getLocked());
		form.setFingerprint(doc.getFingerprint());
		form.setTitle(doc.getTitle());
		form.setType(doc.getType());
		form.setVersion(doc.getVersion());
		form.setCreatedDate(DatetimeTool.getDatetimeString(
				doc.getCreatedDate(), "yyyy-MM-dd HH:mm:ss"));
		form.setAccountName(doc.getAccountName());
		if (doc.getLocked())
			form.setLockedAccount(doc.getLockedAccount().getName());
		form.setSourceAuthor(doc.getSourceAuthor());
		form.setFileSize(doc.getFileSize());
		if (null != doc.getRating())
			form.setRating(doc.getRating().toString());
		Template template = doc.getTemplate();
		if (null != template) {
			form.setTemplateId(template.getId());
			form.setTemplateName(template.getName());
		}
		form.setContent("111222");
		return form;
	}

	public void modify(DocForm form) {
		Document document = baseDao.query(Document.class, form.getId());
		document.setTitle(form.getTitle());
		document.setSourceAuthor(form.getSourceAuthor());
		document.setRating(Double.valueOf(form.getRating()));
		if (null != form.getTemplateId() && form.getTemplateId().length() > 0) {
			Template template = baseDao.query(Template.class,
					form.getTemplateId());
			document.setTemplate(template);
		}
		/*
		 * Category category =
		 * baseDao.query(Category.class,form.getCategoryId());
		 * document.getCategories().clear();
		 * document.getCategories().add(category);
		 */
		baseDao.addOrModify(document);
	}

	/**
	 * 获取文档的摘要
	 * 
	 * @param id
	 * @return
	 */
	public String getDocumentSummary(String id) {
		Document doc = baseDao.query(Document.class, id);
		downloadFileFromHdfs(doc);
		String summary = TikaTool.fileToTxt(new File(getFetchPath(doc)));
		if (summary.length() > 4000) {
			return summary.substring(0, 4000);
		}
		return summary;
	}

	/*
	 * 获取文档的文本
	 */
	public String getDocumentText(String id) {
		Document doc = baseDao.query(Document.class, id);
		downloadFileFromHdfs(doc);
		return TikaTool.fileToTxt(new File(getFetchPath(doc)));
	}

	/**
	 * 获取文件夹的内容 测试用
	 * 
	 * @param path
	 */
	public void getStatus(String path) {
		try {
			hdfs.ls(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @return hdfs中文档列表
	 * @throws IOException
	 */
	public List<String> checkFileExist() throws IOException {
		return hdfs.getHdfsFileList();
	}
}
