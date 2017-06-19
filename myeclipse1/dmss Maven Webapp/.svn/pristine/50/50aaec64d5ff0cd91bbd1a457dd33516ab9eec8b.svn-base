package org.csdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 垃圾文件表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_GARBAGE")
public class Garbage {
	
	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;
	
	@Column(name="C_BLOCK_ID",length=20)  
	private String blockId;
	
	@Column(name="C_FILE_ID",length=40)  
	private String fileId;
	
	@Column(name="C_FILE_SIZE")  
	private Long fileSize;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	
}
