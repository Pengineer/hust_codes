package org.csdc.service.imp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.csdc.bean.Application;
import org.csdc.storage.HdfsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SplitService extends BaseService {
	@Autowired
	protected Application application;
	
	@Autowired
	protected DocumentService documentService;

	@Autowired
	protected HdfsDAO hdfs;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void splitFile() throws IOException {
		// 统计有多少块，记录块名
		String findBlock = "select d.blockId from Document d where d.blockId != 'tmp' group by d.blockId";// 统计块信息
		String countDelFile = "select d.deleted, d.fileSize, d.fingerprint, d.fileId from Document d where d.blockId =?";// 扫描块内信息
		long blockSize = 0;// 每个块的大小
		List<String> blockList;
		try {
			blockList = baseDao.query(findBlock.toString());
			// 遍历每个块，计算标记为删除文档的比例
			for (String blockId : blockList) {
				List<String> fingerPrint = new ArrayList<String>();
				List<String> fileIds = new ArrayList<String>();
				List<Object[]> objects;
				try {
					objects = baseDao.query(countDelFile, blockId);
					long deleteFileSize = 0;// 标记为删除的文档大小
					for (Object[] o : objects) {
						if ((Boolean) o[0]) {
							deleteFileSize += (Long) o[1];
						}
						blockSize += (Long) o[1];
						fingerPrint.add((String) o[2]);// 原文件ID，记录在fingerprint中，拆分后新文件的ID
						fileIds.add((String) o[3]);// 合并文件的ID
					}
					if ((deleteFileSize / (double) blockSize) > 0.3) {
						Map fileMap = new HashMap();
						String updateDocument = new String(
								"update Document d set d.blockId = 'tmp', d.fileId =d.fingerprint where d.fileId =:fileId");
						// 对于每个达到阈值的块进行拆分处理
						if (hdfs.split(fingerPrint, fileIds, blockId)) {
							for (String fileId : fileIds) {
								fileMap.put("fileId", fileId);
								// 拆分完成后将blockId更新为tmp，fileId为fingerprint
								baseDao.execute(updateDocument, fileMap);
							}
							hdfs.rmr("/dmss/" + blockId);
							documentService.deleteRecycleAll();//拆分完后清空回收站
						}
					}
					deleteFileSize = 0;
					blockSize = 0;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}

	}
	
	@Scheduled(cron="0 */5 */1 * * *")
	public void scheduleSplitTask() {
		SimpleDateFormat sFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		System.out.println(sFormat.format(new Date()) + "execute split task...");
		try {
			splitFile();
			documentService.deleteRecycleAll();
			System.out.println(sFormat.format(new Date()) + "finished split task...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
