package org.csdc.tool.bean;

import java.io.File;
import java.util.Date;

import org.csdc.tool.FileTool;

/**
 * 上传文件记录
 * @author jintf
 * @date 2014-6-16
 */
public class FileRecord {
	
	private String id;//随机生成的10位ID
	private File original; //原始文件
	private String fileName;
	private Date addTime;
	private String dir; //原始文件的目录，对于上传的文件对应的是temp/sessionid;对于导入的文件为导入的根目录
	
	private File dest; //移动的正式文件
	private boolean discard; //是否被废弃
	
	private String type; //文档类型
	private String title; //文档标题
	public FileRecord(String id, File original, String fileName,String dir) {
		this.id = id;
		this.original = original;
		this.fileName = fileName;
		this.type = FileTool.getExtension(original).toLowerCase();	
		this.dir = dir;
		this.title = FileTool.getFilePrefix(this.fileName);
		this.addTime = new Date();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public File getOriginal() {
		return original;
	}
	public void setOriginal(File original) {
		this.original = original;
	}
	public File getDest() {
		return dest;
	}
	public void setDest(File dest) {
		this.dest = dest;
	}
	public Date getAddTime() {
		return addTime;
	}
	public boolean isDiscard() {
		return discard;
	}
	public void setDiscard(boolean discard) {
		this.discard = discard;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
}
