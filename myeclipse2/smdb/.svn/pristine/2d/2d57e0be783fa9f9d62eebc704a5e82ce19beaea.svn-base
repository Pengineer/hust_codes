package csdc.action.system.news;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.News;
import csdc.bean.Passport;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.NewsInfo;

@SuppressWarnings("unchecked")
public class Inner extends NewsAction {

	private static final long serialVersionUID = 1L;

	private final static String HQL = "select n.id, n.title, sys.name, n.isOpen, a.id, p.name, n.createDate, n.viewCount, n.accountBelong from News n left join n.account a left join n.type sys left join a.passport p where 1=1 ";
	private final static String[] COLUMN = new String[]{
		"n.title",
		"sys.name, n.title",
		"n.isOpen, n.title",
		"p.name, n.title",
		"n.createDate desc, n.title",
		"n.viewCount, n.title",
		"n.accountBelong, n.title"
	};// 排序列
	private static final String PAGE_NAME = "newsInnerPage";// 列表页面名称
	private static final String TMP_NEWS_ID = "newsId";

	private String keyword1, keyword2, keyword3, keyword4, keyword5;// 用于高级检索
	private Date startDate, endDate;// 用于高级检索
	private int isOpen, viewFlag;// 用于高级检索
	//异步文件上传所需
    private String uploadKey;// 文件上传授权码
	private String existingAttachment;	//已有的附件(修改新闻时用于加载)
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）

	public String pageName(){
		return PAGE_NAME;
	}
	public String[] column(){
		return COLUMN;
	}
	public String HQL() {
		return HQL;
	}
	/**
	 * 进入添加新闻页面
	 * @return
	 */
	public String toAdd() {
//		ActionContext context = ActionContext.getContext();
//		Map application = context.getApplication();
//		Map session = context.getSession();
//		String baseDir = (String) application.get("NewsFileUploadPath");
//		session.put("FilePath", baseDir + "/" + DatetimeTool.getDatetimeString("yyyyMM"));
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 添加新闻
	 * @return
	 */
	@Transactional
	public String add() {
		news.setAccount(loginer.getAccount());
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		news.setAccountBelong(accountBelong);
		entityId = newsService.addNews(news);
		
		//处理附件
		String groupId = "file_add";
		String savePath = (String) ApplicationContainer.sc.getAttribute("NewsFileUploadPath");
		List<String> attachments = new ArrayList<String>();
		List<String> attachmentNames = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newFilePath = FileTool.getTrueFilename(savePath, entityId,fileRecord.getOriginal());//文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			attachments.add(newFilePath);
			attachmentNames.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		news.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
		news.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
		dao.modify(news);
		
		uploadService.flush(groupId);
		return SUCCESS;
		
	}

	/**
	 * 添加新闻校验
	 */
	public void validateAdd() {
		if (news.getTitle() == null || news.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_TITLE_NULL);
		} else if (news.getTitle().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_TITLE_ILLEGAL);
		}
		if (news.getSource().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_SOURCE_ILLEGAL);
		}
		if (news.getIsOpen() != 0 && news.getIsOpen() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_IS_OPEN);
		}
		if (news.getContent() == null || news.getContent().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_BODY_NULL);
		}
	}

	/**
	 * 进入修改新闻页面
	 * @return
	 */
	public String toModify() {
		flag = 1;
		news = newsService.viewNews(entityId);
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + news.getId();
		uploadService.resetGroup(groupId);
		if (news.getAttachmentName() != null && news.getAttachment() != null) {
			String[] tempFileRealpath = news.getAttachment().split("; ");
			String[] attchmentNames = news.getAttachmentName().split("; ");
			//遍历要修改的新闻中已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null) {
					uploadService.addFile(groupId, new File(fileRealpath), attchmentNames[i]);
				}
			}
		}
		session.put(TMP_NEWS_ID, news.getId());
		return SUCCESS;
	}

	/**
	 * 修改新闻校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_MODIFY_NULL);
		}
	}

	/**
	 * 修改新闻
	 * @return
	 */
	@Transactional
	public String modify() {
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get(TMP_NEWS_ID);
		newsService.modifyNews(entityId, news);// 更新附件之外的信息
		News orgNews = newsService.viewNews(entityId);
		
		//处理附件
		String groupId = "file_" + orgNews.getId();
		String savePath = (String) ApplicationContainer.sc.getAttribute("NewsFileUploadPath");
		List<String> attachments = new ArrayList<String>();
		List<String> attachmentNames = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String orignalFileName = fileRecord.getFileName();//原始文件名
			String newFilePath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());//文件在数据库中的存储地址（包括存储路径和新的文件名）
			attachments.add(newFilePath);
			attachmentNames.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		orgNews.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
		orgNews.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
		dao.modify(orgNews);
		
		session.remove(TMP_NEWS_ID);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 修改新闻校验
	 */
	public void validateModify() {
		if (news.getTitle() == null || news.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_TITLE_NULL);
		} else if (news.getTitle().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_TITLE_ILLEGAL);
		}
		if (news.getSource().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_SOURCE_ILLEGAL);
		}
		if (news.getIsOpen() != 0 && news.getIsOpen() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_IS_OPEN);
		}
		if (news.getContent() == null || news.getContent().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_BODY_NULL);
		}
	}
	
	/**
	 * 进入查看
	 */
	public String toView() {
//		if(pageNumber>0){
//			backToList(pageName(),pageNumber);
//		}
		return SUCCESS;
	}

	/**
	 * 查看新闻校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_VIEW_NULL);
		}
	}
	/**
	 * 查看新闻
	 * @return
	 * @throws IOException 
	 */
	public String view() throws IOException {
		news = newsService.viewNews(entityId);
		news.setViewCount(news.getViewCount() + 1);// 查看数量+1
		dao.modify(news);
		if (news.getAttachmentName() != null) {// 处理附件名称用于显示，去掉最后一个"; "
			news.setAttachmentName(news.getAttachmentName().substring(0, news.getAttachmentName().length() ));
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = news.getAttachment().split("; ");
			InputStream is = null;
			for (String path : attachPath) {
				is = ServletActionContext.getServletContext().getResourceAsStream(path);
				if (null != is) {
					attachmentSizeList.add(baseService.accquireFileSize(is.available()));
				} else {// 附件不存在
					attachmentSizeList.add(null);
				}
				jsonMap.put("attachmentSizeList", attachmentSizeList);
			}
		}
		jsonMap.put("news", news);
		if ( null != news.getAccount()) {
			Passport passport = (Passport) dao.query(Passport.class, news.getAccount().getPassport().getId());
			jsonMap.put("accountName", passport == null ? null : passport.getName());
		} else {
			jsonMap.put("accountName", null);
		}
		jsonMap.put("newsType", (news.getType() == null ? null : news.getType().getName()));
//		backToList(pageName(), -1, entityId);
		return SUCCESS;
	}
	
	public String download() throws UnsupportedEncodingException {
		news = (News) dao.query(News.class, entityId);
		if (news == null) {
//			request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
			addActionError(GlobalInfo.ERROR_ATTACH_NULL);
			return INPUT;
		} else {
			if (news.getAttachmentName() == null || news.getAttachment() == null) {// 检查是否有附件
//				request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				addActionError(GlobalInfo.ERROR_ATTACH_NULL);
				return INPUT;
			} else {
				String[] attachName = news.getAttachmentName().split("; ");// 封转附件名称为数组
				String[] attachPath = news.getAttachment().split("; ");// 封转附件路径为数组
				if (attachName.length < status + 1) {// 检查索引的附件是否存在
//					request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					addActionError(GlobalInfo.ERROR_ATTACH_NULL);
					return INPUT;
				} else {
					attachmentName = attachName[status];
					attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
					targetFile = ServletActionContext.getServletContext().getResourceAsStream(attachPath[status]);
					return SUCCESS;
				}
			}
		}
	}
	
	/**
	 * 文件是否存在校验
	 */
	public String validateFile()throws Exception{
		System.out.println("1");
		if (null == entityId || entityId.trim().isEmpty()) {//通知id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_FILE_NOT_MATCH);
		} else {
			News news = (News) dao.query(News.class, this.entityId);
			if(null == news){//通知不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(news.getAttachmentName() == null || news.getAttachment() == null){//路径或文件名错误
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_FILE_NOT_MATCH);
			} else {//如何判断路径有，但文件不存在
				String[] attachName = news.getAttachmentName().split("; ");// 封转附件名称为数组
				String[] attachPath = news.getAttachment().split("; ");// 封转附件路径为数组
				attachmentName = attachName[status];
				attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
				targetFile = ServletActionContext.getServletContext().getResourceAsStream(attachPath[status]);
				if( targetFile == null ){
					jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 删除新闻
	 * @return
	 */
	@Transactional
	public String delete() {
		newsService.deleteNews(entityIds);
//		if (type == 1) {
//			backToList(pageName(), pageNumber, null);
//		} else {
//			backToList(pageName(), -1, entityIds.get(0));
//		}
		return SUCCESS;
	}
	
	/**
	 * 删除新闻校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_DELETE_NULL);
		}
	}
	
	/**
	 * 初级检索
	 */
	@Override
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		hql.append(" and ");
		if (searchType == 1) {// 按新闻标题检索
			hql.append(" LOWER(n.title) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按新闻正文检索
			hql.append(" LOWER(n.content) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按发布账号检索
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 4) {// 按发布者检索
			hql.append(" LOWER(n.accountBelong) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(n.title) like :keyword or LOWER(n.content) like :keyword or LOWER(p.name) like :keyword or LOWER(n.accountBelong) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[]{
			hql.toString(),
			map,
			4,
			null
		};
	}
	
	/**
	 * 高级检索
	 */
	public Object[] advSearchCondition() {
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL());
		if (news.getTitle() != null && !news.getTitle().isEmpty()) {
			map.put("title", "%" + news.getTitle() + "%");
			hql.append(" and LOWER(n.title) like :title ");
		}
		if (news.getContent() != null && !news.getContent().isEmpty()) {
			map.put("content", "%" + news.getContent() + "%");
			hql.append(" and LOWER(n.content) like :content ");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {
			keyword3 = keyword3.toLowerCase();
			map.put("keyword3", keyword3);
			hql.append(" and n.type.id = :keyword3 ");
		}
		if(accountType.equals(AccountType.ADMINISTRATOR)){
			if (keyword4 != null && !keyword4.isEmpty()) {
				keyword4 = keyword4.toLowerCase();
				map.put("keyword4", keyword4);
				hql.append(" and LOWER(p.name) like :keyword4 ");
			}
			if (news.getAccountBelong() != null && !news.getAccountBelong().isEmpty()) {
				map.put("accountBelong", "%" + news.getAccountBelong() + "%");
				hql.append(" and LOWER(n.accountBelong) like :accountBelong ");
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			hql.append(" and to_char(n.createDate,'yyyy-MM-dd') > :startDate");
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			hql.append(" and to_char(n.createDate,'yyyy-MM-dd') < :endDate");
		}
		if (isOpen == 0 || isOpen == 1) {
			map.put("isOpen", isOpen);
			hql.append(" and n.isOpen = :isOpen");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}

	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if(null != news.getTitle() && !news.getTitle().isEmpty()){
			searchQuery.put("title", news.getTitle());
		}
		if(null != news.getContent() && !news.getContent().isEmpty()){
			searchQuery.put("content", news.getContent());
		}
		if (null != keyword3 && !keyword3.isEmpty()) {
			searchQuery.put("type", keyword3);
		}
		if(isOpen == 0 || isOpen == 1){
			searchQuery.put("isOpen", isOpen);
		}
		if(accountType.equals(AccountType.ADMINISTRATOR)){
			if(null != keyword4 && !keyword4.isEmpty()){
				searchQuery.put("account", keyword4);
			}
			if(null != news.getAccountBelong() && !news.getAccountBelong().isEmpty()){
				searchQuery.put("accountBelong", news.getAccountBelong());
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
	}
	
	
	
	/**
	 * 高级检索新闻校验
	 *//*
	public void validateAdvSearch() {
		if(startDate != null && endDate != null) {
			if (startDate.compareTo(endDate) >= 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_DETE_ILLEGAL);
			}
		}
	}*/
	
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public String getExistingAttachment() {
		return existingAttachment;
	}
	public void setExistingAttachment(String existingAttachment) {
		this.existingAttachment = existingAttachment;
	}
	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}
	public int getViewFlag() {
		return viewFlag;
	}
	public String getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}