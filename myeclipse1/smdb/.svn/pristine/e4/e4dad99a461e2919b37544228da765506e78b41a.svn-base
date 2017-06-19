package csdc.tool.crawler;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.csdc.domain.fm.ThirdUploadForm;
import org.csdc.service.imp.DmssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.tool.ApplicationContainer;
import csdc.tool.crawler.validator.PageValidator;

/**
 * @author yingao
 * 文档上传至dmss具体类
 */
@Component
@Scope("prototype")
public class DocumentUpload extends Crawler {
	
	@Autowired
	protected DmssService dmssService;
	
	@Override
	Class<? extends PageValidator> getPageValidatorClass() {
		return null;
	}

	/* (non-Javadoc)
	 * @see csdc.tool.crawler.Crawler#work()
	 * 具体任务处理，根据任务的argument选择不同的方法组装参数，进行上传任务
	 */
	@Override
	protected void work() throws Exception {
		Map arguments = JSONObject.fromObject(crawlTask.getArguments());
		String path = arguments.get("path").toString();
		if (path.contains("award")) {
			uploadAward(path);
		} else if (path.contains("news")) {
			uploadNews(path);
		} else if (path.contains("project")) {
			uploadProject(path);
		} else if (path.contains("person")) {
			uploadPerson(path);
		} else if (path.contains("recruit")) {
			uploadRecruit(path);
		} else if (path.contains("statistic")) {
			uploadStatistic(path);
		} else {
			updateTask("Unknow file Type" + path);
		}
	}

	private void uploadProject(String path) {
		Map pMap = new HashMap();
		String realFile = ApplicationContainer.sc.getRealPath(path);//真实的文件路径
		String file = path.replace("\\", "/").substring(path.indexOf("upload"));//数据库中记录的文件路径
		if (file.contains("app")) {// 项目申请
			pMap.put("appFile", file);
			List<Object[]> results = hibernateBaseDao.query("select pa.id, pa.applicantName, pa.type from ProjectApplication pa where pa.file =:appFile",pMap);
			if (results.size() > 1) {
				updateTask("have more records in dataBase File: " + file);
			} else if (results.size() == 0) {
				updateTask("have no record in dataBase File: " + file);
			} else {
				ThirdUploadForm form = new ThirdUploadForm();
				form.setTitle(getFileTile(file));
				form.setFileName(getFileName(file));
				form.setSourceAuthor((String) results.get(0)[1]);
				form.setRating("5.0");
				form.setTags("project," + (String) results.get(0)[2] + ",app");
				form.setCategoryPath(getDmssCategory(file));
				try {
					String dfsId = dmssService.upload(realFile, form);//这里path推敲下
					if (null != dfsId) {
						Map paraMap = new HashMap();
						paraMap.put("id", results.get(0)[0]);
						paraMap.put("dfsId", dfsId);
						hibernateBaseDao.execute("update ProjectApplication pa set pa.dfs=:dfsId where pa.id=:id",paraMap);
						updateTask();
					} else { // 文件上传失败
						updateTask("upload file " + file + " failed error null dfs");
					} 
				} catch (Exception e) {
					updateTask("upload file " + file + " failed error " + e.toString());
				}
			}
		} else if (realFile.contains("var")) {//项目变更
			List<Object> results = null;
			if (path.contains("pos")) {// 变更延期申请书
				pMap.put("varPostFile", file);
				results = hibernateBaseDao.query("select pv.id from ProjectVariation pv where pv.postponementPlanFile=:varPostFile",pMap);
				if (results.size() > 1) {
					updateTask("have more records in dataBase File: " + file);
				} else if (results.size() == 0) {
					updateTask("have no record in dataBase File: " + file);
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(file));
					form.setFileName(getFileName(file));
					ProjectVariation pv = (ProjectVariation) hibernateBaseDao.query(ProjectVariation.class,(String) results.get(0));
					ProjectGranted pg = (ProjectGranted) hibernateBaseDao.query(ProjectGranted.class, pv.getGrantedId());
					form.setSourceAuthor(pg.getApplicantName());
					form.setRating("5.0");
					form.setTags("project," + pv.getProjectType() + "," + "pos,var");
					form.setCategoryPath(getDmssCategory(file));
					try {
						String dfsId = dmssService.upload(realFile, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0));
							paraMap.put("postponementPlanDfs", dfsId);// 变更延期申请书云存储ID
							hibernateBaseDao.execute("update ProjectVariation pv set pv.postponementPlanDfs=:postponementPlanDfs where pv.id=:id",paraMap);
						} else {
							updateTask("upload " + file+ " to dmss failed  ");
						}
					} catch (Exception e) {
						updateTask("upload file " + file + " failed error" + e.toString());
					} 
				}
			} else {
				pMap.put("varFile", file);
				results = hibernateBaseDao.query("select pv.id from ProjectVariation pv where pv.file=:varFile",pMap);
				if (results.size() > 1) {
					updateTask("have more records in dataBase File: " + file);
				} else if (results.size() == 0) {
					updateTask("have no record in dataBase File: " + file);
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(file));
					form.setFileName(getFileName(file));
					ProjectVariation pv = (ProjectVariation) hibernateBaseDao.query(
							ProjectVariation.class, (String) results.get(0));
					ProjectGranted pg = (ProjectGranted) hibernateBaseDao.query(
							ProjectGranted.class, pv.getGrantedId());
					form.setSourceAuthor(pg.getApplicantName());
					form.setRating("5.0");
					form.setTags("project," + pv.getProjectType() + "," + "var");
					form.setCategoryPath(getDmssCategory(file));
					try {
						String dfsId = dmssService.upload(realFile, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0));
							paraMap.put("dfsId", dfsId);
							hibernateBaseDao.execute("update ProjectVariation pv set pv.dfs=:dfsId where pv.id=:id",paraMap);
						} else {
							updateTask("upload " + file + " to dmss failed  ");
						}
					} catch (Exception e) {
						updateTask("upload file " + file + " failed error " + e.toString());
					}
				}
			}
		} else if (file.contains("end")) {// 结项
			List<Object> results = null;
			pMap.put("endFile", file);
			results = hibernateBaseDao.query("select pe.id from ProjectEndinspection pe where pe.file=:endFile",pMap);
			if (results.size() > 1) {
				updateTask("have more records in dataBase File: " + file);
			} else if (results.size() == 0) {
				updateTask("have no records in dataBase File: " + file);
			} else {
				ThirdUploadForm form = new ThirdUploadForm();
				form.setTitle(getFileTile(file));
				form.setFileName(getFileName(file));

				String idString = (String) results.get(0);
				ProjectEndinspection pe = hibernateBaseDao.query(
						ProjectEndinspection.class, idString);
				form.setSourceAuthor(pe.getGranted().getApplicantName());

				form.setRating("5.0");
				form.setTags("project," + pe.getProjectType() + "," + "end");
				form.setCategoryPath(getDmssCategory(file));
				try {
					String dfsId = dmssService.upload(realFile, form);
					if (null != dfsId) {
						Map paraMap = new HashMap();
						paraMap.put("id", results.get(0));
						paraMap.put("dfsId", dfsId);
						hibernateBaseDao.execute("update ProjectEndinspection pe set pe.dfs=:dfsId where pe.id=:id",paraMap);
					} else {
						updateTask("upload " + file + " to dmss failed  ");
					}
				} catch (Exception e) {
					updateTask("upload file " + file+ " failed error " + e.toString());
				}
			}
		} else if (file.contains("mid")) {// 中检
			List<Object> results = null;
			pMap.put("midFile", file);
			results = hibernateBaseDao.query("select pm.id from ProjectMidinspection pm where pm.file=:midFile",pMap);
			if (results.size() > 1) {
				updateTask("have more records in dataBase File: " + file);
			} else if (results.size() == 0) {
				updateTask("have no records in dataBase File: " + file);
			} else {
				ThirdUploadForm form = new ThirdUploadForm();
				form.setTitle(getFileTile(file));
				form.setFileName(getFileName(file));

				ProjectMidinspection pm = (ProjectMidinspection) hibernateBaseDao.query(ProjectMidinspection.class,(String) results.get(0));
				form.setSourceAuthor(pm.getGranted().getApplicantName());
				form.setRating("");
				form.setTags("project," + pm.getProjectType() + ",mid");
				form.setCategoryPath(getDmssCategory(file));
				try {
					String dfsId = dmssService.upload(realFile, form);
					if (null != dfsId) {
						Map paraMap = new HashMap();
						paraMap.put("id", results.get(0));
						paraMap.put("dfsId", dfsId);
						hibernateBaseDao.execute("update ProjectMidinspection pm set pm.dfs=:dfsId where pm.id=:id",paraMap);
					} else {
						updateTask("upload " + file + " to dmss failed  " );
					}
				} catch (Exception e) {
					updateTask("upload file " + file+ " failed error" + e.toString());
				}
			}
		} else {
			updateTask("upload file " + file + " doesn't match ");
		}
	}

	private void updateTask(String string) {
		throw new RuntimeException(string);
	}

	private void updateTask() {
		crawlTask.setFinishTime(new Date());
		hibernateBaseDao.modify(crawlTask);		
	}

	private void uploadNews(String path) {
		
	}

	private void uploadAward(String path) {
		Map pMap = new HashMap();
		String realFile = ApplicationContainer.sc.getRealPath(path);//真实的文件路径
		String file = path.replace("\\", "/").substring(path.indexOf("upload"));//数据库中记录的文件路径
		if (path.contains("app")) {
			pMap.put("appFile", file);
			List<Object[]> results = hibernateBaseDao
					.query("select aa.id, aa.applicantName from AwardApplication aa where aa.file=:appFile",pMap);
			if (results.size() > 1) {
				updateTask("have more records in dataBase File: " + file);
			} else if (results.size() == 0) {
				updateTask("have no record in dataBase File: " + file);
			} else {
				ThirdUploadForm form = new ThirdUploadForm();
				form.setTitle(getFileTile(file));
				form.setFileName(getFileName(file));
				form.setSourceAuthor((String) results.get(0)[1]);
				form.setRating("5.0");
				form.setTags("award,app");
				form.setCategoryPath(getDmssCategory(file));
				try {
					String dfsId = dmssService.upload(realFile, form);
					if (null != dfsId ) { // TODO 添加MD5校验
						Map paraMap = new HashMap();
						paraMap.put("id", results.get(0)[0]);
						paraMap.put("dfsId", dfsId);
						hibernateBaseDao.execute("update AwardApplication ap set ap.dfsId=:dfsId where ap.id=:id",paraMap);
						
					} else {// 文件上传失败
						updateTask("upload file " + file + " failed error null dfs");
					}
				} catch (Exception e) {
					updateTask("upload file " + file + " failed error " + e.toString());
				}
			}
		}
	}
	
	private void uploadStatistic(String path) {
		// TODO Auto-generated method stub
		
	}

	private void uploadRecruit(String path) {
		// TODO Auto-generated method stub
		
	}

	private void uploadPerson(String path) {
		// TODO Auto-generated method stub
		
	}

	public String getDmssCategory(String filePath) {
		return "/SMDB/" + getRelativeFileDir(filePath);
	}
	
	private String getFileTile(String Path) {
		if (Path.lastIndexOf(".") > -1) {
			return Path.substring(Path.lastIndexOf("/") + 1,
					Path.lastIndexOf("."));
		} else {
			return getFileName(Path);
		}
	}

	private String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}
	
	/**
	 * 获取上传后的文件的相对目录 （比如
	 * filePath为"upload/award/moesocial/app/2001/hello.doc",则返回目录为
	 * "award/moesocial/app/2001"）
	 * 
	 * @param filePath 上传后的文件相对路径
	 * @return 上传后的文件的相对目录
	 */
	public String getRelativeFileDir(String filePath) {
		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}
		return filePath.substring(filePath.indexOf("/") + 1,
				filePath.lastIndexOf("/"));
	}
}
