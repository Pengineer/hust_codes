package eoas.service;

import java.io.File;
import java.util.List;

import eoas.tool.*;;

/**
 * 异步文件上传辅助服务
 * @author xuhan
 *
 */
public interface IUploadService {
	
	/**
	 * 重置文件组，需要在初始化文件上传表单时执行
	 * @param groupId
	 */
	public void resetGroup(String groupId);

	/**
	 * 向文件组(groupId)添加一个文件，文件名使用该文件在磁盘中的名字
	 * @param groupId
	 * @param file
	 * @return
	 */
	public String addFile(String groupId, File file);

	/**
	 * 向文件组(groupId)添加一个文件，使用fileName作为文件名
	 * @param groupId
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String addFile(String groupId, File file, String fileName);

	/**
	 * 在文件组(groupId)中废弃一个文件(fileId)，该文件将在flush时被删除
	 * @param groupId
	 * @param fileId
	 */
	public void discardFile(String groupId, String fileId);
	
	/**
	 * 获取文件组(groupId)中未被废弃的文件
	 * @param groupId
	 * @return
	 */
	public List<FileRecord> getGroupFiles(String groupId);

	/**
	 * 将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
	 * @param groupId
	 */
	public void flush(String groupId);

}
