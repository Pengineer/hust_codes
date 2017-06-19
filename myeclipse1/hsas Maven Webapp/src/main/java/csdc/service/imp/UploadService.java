package csdc.service.imp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.dao.IHibernateBaseDao;

import csdc.service.IUploadService;
import csdc.tool.SignID;
import csdc.tool.bean.FileRecord;


/**
 * 异步文件上传辅助服务实现
 * 该类有一个状态：当前用户本次会话上传的还未处理的文件
 * 使用scope=session来保证不同用户会话之间的隔离性，和同一用户会话内部操作的关联性
 * @author xuhan
 *
 */
@Component
@Scope("session")
public class UploadService implements IUploadService ,Serializable {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4444983453536598216L;

	@Autowired
	private transient IHibernateBaseDao dao;

	/**
	 * 存储上传的文件信息，以如下格式保存
	 * groupId -> (fileId -> file)
	 */
	private Map<String, Map<String, FileRecord>> fileGroups = new HashMap<String, Map<String, FileRecord>>();

	
	synchronized public void resetGroup(String groupId) {
		fileGroups.put(groupId, new LinkedHashMap<String, FileRecord>());
	}

	synchronized public String addFile(String groupId, File file) {
		return addFile(groupId, file, file.getName());
	}

	synchronized public String addFile(String groupId, File file, String fileName) {
		if (!fileGroups.containsKey(groupId)) {
			resetGroup(groupId);
		}
		if (!file.exists()) {
			System.err.println(file.getName() + " doesn't exist!");
			return null;
		} else {
			String fileId = SignID.getRandomString(10);
			fileGroups.get(groupId).put(fileId, new FileRecord(fileId, file, fileName));
			return fileId;
		}
	}

	synchronized public void discardFile(String groupId, String fileId) {
		try {
			FileRecord fileRecord = fileGroups.get(groupId).get(fileId);
			fileRecord.setDiscard(true);
		} catch (Exception e) {
		}
	}
	
	synchronized public List<FileRecord> getGroupFiles(String groupId) {
		return getGroupFiles(groupId, false);
	}

	synchronized public void flush(String groupId) {
		// 先flush数据库的写操作，以免对数据库操作失败，而文件的操作成功，
		// 导致前者回滚、后者无法回滚造成的不一致
		if (dao != null) {
			dao.flush();
		}

		List<FileRecord> fileRecords = getGroupFiles(groupId, true);
		for (FileRecord fileRecord : fileRecords) {
			if (fileRecord.isDiscard()) {
				// 删除该文件
				try {
					fileRecord.getOriginal().delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (fileRecord.getDest() != null) {
				// 移动该文件
				try {
					if (!fileRecord.getOriginal().renameTo(fileRecord.getDest())) {
						FileUtils.copyFile(fileRecord.getOriginal(), fileRecord.getDest());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		fileGroups.remove(groupId);
	}
	
	/**
	 * 对数据库，以及文件的提交操作，isDelete是否删除临时文件，为true时则移动临时文件，为false时则复制临时文件
	 * @param groupId
	 * @param isDelete
	 */
	synchronized public void flush(String groupId, Boolean isDelete) {
		// 先flush数据库的写操作，以免对数据库操作失败，而文件的操作成功，
		// 导致前者回滚、后者无法回滚造成的不一致
		if (dao != null) {
			dao.flush();
		}

		List<FileRecord> fileRecords = getGroupFiles(groupId, true);
		for (FileRecord fileRecord : fileRecords) {
			if (fileRecord.isDiscard()) {
				// 删除该文件
				try {
					fileRecord.getOriginal().delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (fileRecord.getDest() != null) {
				// 移动该文件
				try {
					FileUtils.copyFile(fileRecord.getOriginal(), fileRecord.getDest());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		fileGroups.remove(groupId);
	}
	
	/**
	 * 取出groupId对应文件组的文件信息。如果includeDiscarded==true，则取出所有文件，否则只取出未被废弃的文件。
	 * @param groupId
	 * @param includeDiscarded
	 * @return
	 */
	synchronized private List<FileRecord> getGroupFiles(String groupId, boolean includeDiscarded) {
		if (!fileGroups.containsKey(groupId)) {
			return new ArrayList();
		}
		List<FileRecord> fileRecords = new ArrayList(fileGroups.get(groupId).values());
		Collections.sort(fileRecords, new Comparator<FileRecord>() {
			public int compare(FileRecord o1, FileRecord o2) {
				return o1.getAddTime().compareTo(o2.getAddTime());
			}
		});
		List<FileRecord> res = new ArrayList<FileRecord>();
		for (FileRecord fileRecord : fileRecords) {
			if (includeDiscarded || !fileRecord.isDiscard()) {
				res.add(fileRecord);
			}
		}
		return res;
	}



}
