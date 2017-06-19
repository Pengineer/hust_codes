package org.csdc.domain;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传表单
 * @author jintf
 * @date 2014-6-15
 */
public class UploadForm {
	/**
	 * 上传的文件，默认情况下上传后存储在temp目录下
	 */
	private MultipartFile file;

	/**
	 * 文件组id
	 */
	private String groupId;
	
	/**
	 * 文件id，由后台自行生成
	 */
	private String fileId;


	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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
	
	
}
