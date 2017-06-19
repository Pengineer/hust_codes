package csdc.action.system.news;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.bean.News;
import csdc.service.INewsService;
import csdc.service.IUploadService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.NewsInfo;

/**
 * 新闻管理公共部分
 * @author 龚凡
 * @version 2011.01.24
 */
public abstract class NewsAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String PAGE_BUFFER_ID = "n.id";// 上下条查看时用于查找缓存的字段
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected INewsService newsService;// 新闻管理接口
	
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected News news;// 新闻对象
	// 附件下载
	protected String[] fileIds;// 标题提交上来的特征码list
    protected String attachmentName;// 附件下载文件名
    protected InputStream targetFile;// 附件下载流
    protected int status;// 附件索引号

    public abstract String HQL();
	public abstract String pageName();
	public abstract String[] column();

	public String dateFormat() {
		return NewsAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return NewsAction.PAGE_BUFFER_ID;
	}
	
	/**
	 * 查看新闻校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NewsInfo.ERROR_VIEW_NULL);
		}
	}
	
	/**
	 * 下载附件校验
	 */
	public void validateDownload() {
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_BELONG_NULL);
		}
	}
	
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public InputStream getTargetFile() {
		return targetFile;
	}
	public void setTargetFile(InputStream targetFile) {
		this.targetFile = targetFile;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
}