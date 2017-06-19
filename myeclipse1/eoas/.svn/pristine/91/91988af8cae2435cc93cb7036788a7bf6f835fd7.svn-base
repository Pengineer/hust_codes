package eoas.tool;

import java.io.File;
import java.util.Date;

public class FileRecord {
	
	private String id;
	private File original;  //现在的文件
	private String fileName;
	private Date addTime;
	
	private File dest;
	private boolean discard;//false 不删  true 删

	
	public FileRecord(String id, File original, String fileName) {
		this.id = id;
		this.original = original;
		this.fileName = fileName;
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
	
}
