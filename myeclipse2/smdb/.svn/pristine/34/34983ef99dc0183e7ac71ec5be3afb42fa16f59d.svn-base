package csdc.action.system.notice;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.bean.Notice;
import csdc.service.INoticeService;
import csdc.service.IUploadService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.NoticeInfo;

/**
 * 通知管理公共部分
 * @author 龚凡
 * @version 2011.01.24
 */
public abstract class NoticeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String PAGE_BUFFER_ID = "n.id";// 上下条查看时用于查找缓存的字段
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected INoticeService noticeService;// 通知管理接口
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	protected Notice notice;// 通知对象
	// 下载附件
	protected String[] fileIds;// 标题提交上来的特征码list
	protected String attachmentName;// 附件下载文件名
	protected InputStream targetFile;// 附件下载流
	protected int status;// 附件索引号
	
	public abstract String HQL();
	public abstract String pageName();
	public abstract String[] column();
	
	public String dateFormat() {
		return NoticeAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return NoticeAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查看新闻校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_VIEW_NULL);
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
	
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
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