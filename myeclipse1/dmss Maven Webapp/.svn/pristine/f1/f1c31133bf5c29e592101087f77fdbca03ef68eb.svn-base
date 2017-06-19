package org.csdc.service.imp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.csdc.bean.Application;
import org.csdc.model.Document;
import org.csdc.storage.HdfsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
@EnableScheduling
public class MergeService extends BaseService {

	@Autowired
	protected Application application;

	@Autowired
	protected HdfsDAO hdfs;

	/**
	 * @throws IOException
	 */
	public void mergeTmpFile() throws IOException {
		Map map = new HashMap();
		map.put("blockId", "tmp");
		
		// TODO 目前只针对test1类别进行整理
		map.put("categoryString", "/test1");// 查询条件

		String newBlockId = UUID.randomUUID().toString();// 根据UUID生成文件块号

		StringBuffer findDocument = new StringBuffer();
		findDocument
				.append("select d from Document d where d.blockId =:blockId and d.categoryString=:categoryString and d.deleted = 0");

		long tempSize = 0;// 当前预加入包文件总大小

		List<String> mergeFileList = new ArrayList<String>();// 要打包文件的fileId
		int blockSizeThreshold = 90;// 打包阈值，大于100M
		int blockSize = 128;// hadoop块大小
		int newFileId = 0;// 文档在块中的打包后的fileId

		List<Document> documents = baseDao.query(findDocument.toString(), map);
		for (Document document : documents) {

			tempSize += document.getFileSize();// 统计预打包文档大小
			mergeFileList.add(document.getFileId());// 获取预打包文档fileId
			if (tempSize > 80*4/*blockSizeThreshold * 1024 * 1024*/
					&& tempSize < blockSize * 1024 * 1024) {

				if (hdfs.merge(mergeFileList, newBlockId)) {
					Map fileMap = new HashMap();
					String fileId = "0";// 记录finger print ID
					fileMap.put("newBlockId", newBlockId);// 新的块名

					StringBuffer updateDocument = new StringBuffer();
					updateDocument
							.append("update Document d set d.blockId = :newBlockId ,d.fileId = :newFileId where d.fingerprint = :fileId");
					for (String fId : mergeFileList) {
						fileId = fId;
						fileMap.put("fileId", fileId);// 原ID
						fileMap.put("newFileId", String.valueOf(newFileId));
						try {
							baseDao.execute(updateDocument.toString(), fileMap);
							newFileId++;
							hdfs.rmr("/dmss/tmp/" + fId);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					mergeFileList.clear();
					tempSize = 0;
					newBlockId = UUID.randomUUID().toString();
					newFileId = 0;
				} else {
					System.err.println(" 打包文档过程中出现错误");
					break;
				}
			}
		}

	}
	
	@Scheduled(cron="0 */4 */1 * * *")
	public void scheduleMergeTask() {
		try {
			SimpleDateFormat sFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
			System.out.println(sFormat.format(new Date()) + "execute merge task...");
			mergeTmpFile();
			System.out.println(sFormat.format(new Date()) + "finished merge task.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
