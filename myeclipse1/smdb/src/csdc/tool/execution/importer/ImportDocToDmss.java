package csdc.tool.execution.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.csdc.domain.fm.ThirdUploadForm;
import org.csdc.service.imp.DmssService;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.service.imp.BaseService;
import csdc.tool.ApplicationContainer;

/**
 * @author geekle
 * 
 */
public class ImportDocToDmss extends Importer {

	@Autowired
	private IHibernateBaseDao dao;

	@Autowired
	protected DmssService dmssService;
	
	protected BaseService baseService;

	List<String> fileList = new ArrayList<String>();// 保存要上传文档的列表

	/**
	 * 奖励申请文档导入到DMSS
	 * 
	 * @throws IOException
	 */
	public void importAward() throws IOException {
		String basePath = "upload/award";
		basePath = "E:/smdbhome/upload/award";// 这里地址用于windows上临时测试用
		int count = 0, error = 0; // 用于统计上传文档正确与错误数

		File logFile = new File(ApplicationContainer.sc.getRealPath("upload/awardLog.txt"));
		FileOutputStream o = new FileOutputStream(logFile, true);
		StringBuffer sb = new StringBuffer();
		fileList.clear();
		getFile(new File(ApplicationContainer.sc.getRealPath("upload/award")));
		Map pMap = new HashMap();
		for (String file : fileList) {
			if (file.contains("app")) {
				String realFile = file.replace("\\", "/").substring(file.indexOf("upload"));
				pMap.put("appFile", realFile);
				List<Object[]> results = dao
						.query("select aa.id, aa.applicantName from AwardApplication aa where aa.file=:appFile",
								pMap);
				if (results.size() > 1) {
					error++;
					sb.append("have more records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else if (results.size() == 0) {
					error++;
					sb.append("have no record in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(realFile));
					form.setFileName(getFileName(realFile));
					form.setSourceAuthor((String) results.get(0)[1]);
					form.setRating("5.0");
					form.setTags("award,app");
					form.setCategoryPath(getDmssCategory(realFile));
					try {
						String dfsId = dmssService.upload(file, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0)[0]);
							paraMap.put("dfsId", dfsId);
							dao.execute(
									"update AwardApplication ap set ap.dfsId=:dfsId where ap.id=:id",
									paraMap);
							count++;
							if (count%100==0) {
								sb.append("upload " + count + " "
										+ System.getProperty("line.separator"));
								o.write(sb.toString().getBytes());
								o.flush();
								sb.setLength(0);
							}
						} else {
							// 文件上传失败
							error++;
							sb.append("upload file " + realFile
									+ " failed error null dfs"
									+ System.getProperty("line.separator"));
						}
					} catch (Exception e) {
						error++;
						sb.append("upload file " + realFile
								+ " failed error " + e.toString()
								+ System.getProperty("line.separator"));
						o.write(sb.toString().getBytes());
						o.flush();
						sb.setLength(0);
						e.printStackTrace();
					}
				}
				o.write(sb.toString().getBytes());
				o.flush();
				sb.setLength(0);
			}
		}
		sb.append("totle file numbers: " + fileList.size()
				+ System.getProperty("line.separator")
				+ " error file numbers: " + error
				+ System.getProperty("line.separator")
				+ " success file numbers: " + count
				+ System.getProperty("line.separator"));
		o.write(sb.toString().getBytes());
		o.close();
	}

	public void importNews() throws IOException {
		String basePath = "upload/news";
		fileList.clear();
		
		getFile(new File(ApplicationContainer.sc.getRealPath(basePath)));
		
		File logFile = new File(ApplicationContainer.sc.getRealPath("upload/newslog.txt"));
		if (logFile.exists()) {
			logFile.delete();
		} else {
			logFile.createNewFile();
		}
		FileOutputStream o = new FileOutputStream(logFile, true);
		StringBuffer sb = new StringBuffer();
		int count = 0,error = 0;
		Map pMap = new HashMap();
		for (String file : fileList) {
			String realFile = file.replace("\\", "/").substring(file.indexOf("upload"));
			pMap.put("newsFile", realFile);
			List<Object[]> results = dao.query("select n.id, n.title from News n where n.attachment=:newsFile",pMap);
			if (results.size() > 1) {
				error++;
				sb.append("have no record in database File: " + realFile);
//				o.write(b)
				o.flush();
				
			}
		}
	}

	/**
	 * 
	 * 项目导入模块
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void importProject() throws IOException {
		
		// 当前导入文件目录及文件
		String basePath = "upload/project/general/mid/2014";// 项目根文件夹
		fileList.clear();
//		String basePath1 = "upload/project/general/mid/2010";
//		String basePath2 = "upload/project/general/mid/2011";
//		String basePath3 = "upload/project/general/mid/2012";
//		List<String> sum =  new ArrayList<String>();
//		getFile(new File(ApplicationContainer.sc.getRealPath(basePath1)));
//		sum.addAll(fileList);
//		fileList.clear();
//		getFile(new File(ApplicationContainer.sc.getRealPath(basePath2)));
//		sum.addAll(fileList);
//		fileList.clear();
//		getFile(new File(ApplicationContainer.sc.getRealPath(basePath3)));
//		sum.addAll(fileList);
//		getFile(new File(ApplicationContainer.sc.getRealPath(basePath)));

		
		getFile(new File(ApplicationContainer.sc.getRealPath(basePath)));
		
		// log日志记录上传信息
		File logFile = new File(ApplicationContainer.sc.getRealPath("upload/projectlog.txt"));
		if (logFile.exists()) {
			logFile.delete();
		} else {
			logFile.createNewFile();
		}
		FileOutputStream o = new FileOutputStream(logFile, true);
		StringBuffer sb = new StringBuffer();	
		// 统计信息
		int count = 0, error = 0;
		
		Map pMap = new HashMap();
		for (String file : fileList) {
			String realFile = file.replace("\\", "/").substring(file.indexOf("upload"));
			// 项目申请
			if (realFile.contains("app")) {
				pMap.put("appFile", realFile);
				List<Object[]> results = dao
						.query("select pa.id, pa.applicantName, pa.type from ProjectApplication pa where pa.file =:appFile",
								pMap);
				if (results.size() > 1) {
					error++;
					sb.append("have more records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else if (results.size() == 0) {
					error++;
					sb.append("have no record in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(realFile));
					form.setFileName(getFileName(realFile));
					form.setSourceAuthor((String) results.get(0)[1]);
					form.setRating("5.0");
					form.setTags("project," + (String) results.get(0)[2] + ",app");
					form.setCategoryPath(getDmssCategory(realFile));
					try {
						String dfsId = dmssService.upload(file, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0)[0]);
							paraMap.put("dfsId", dfsId);
							dao.execute(
									"update ProjectApplication pa set pa.dfs=:dfsId where pa.id=:id",
									paraMap);
							count++;
							if (count%100==0) {
								sb.append("upload " + count  +" " 
										+ System.getProperty("line.separator"));
								o.write(sb.toString().getBytes());
								o.flush();
								sb.setLength(0);
							}
						} else { // 文件上传失败
							error++;
							sb.append("upload file " + realFile
									+ " failed error null dfs"
									+ System.getProperty("line.separator"));
						} 
					} catch (Exception e) {
						error++;
						sb.append("upload file " + realFile
								+ " failed error " + e.toString()
								+ System.getProperty("line.separator"));
						o.write(sb.toString().getBytes());
						o.flush();
						sb.setLength(0);
						e.printStackTrace();
					}
				}
				o.write(sb.toString().getBytes());
				o.flush();
				sb.setLength(0);
				continue;
			}
			if (realFile.contains("var")) {// 项目变更
				List<Object> results = null;
				if (file.contains("pos")) {// 变更延期申请书
					pMap.put("varPostFile", realFile);
					results = dao
							.query("select pv.id from ProjectVariation pv where pv.postponementPlanFile=:varPostFile",
									pMap);
					if (results.size() > 1) {
						error++;
						sb.append("have more records in dataBase File: "
								+ realFile
								+ System.getProperty("line.separator"));
					} else if (results.size() == 0) {
						error++;
						sb.append("have no record in dataBase File: "
								+ realFile
								+ System.getProperty("line.separator"));
					} else {
						ThirdUploadForm form = new ThirdUploadForm();
						form.setTitle(getFileTile(realFile));
						form.setFileName(getFileName(realFile));
						ProjectVariation pv = (ProjectVariation) dao
								.query(ProjectVariation.class,
										(String) results.get(0));
						ProjectGranted pg = (ProjectGranted) dao.query(
								ProjectGranted.class, pv.getGrantedId());
						form.setSourceAuthor(pg.getApplicantName());
						form.setRating("5.0");
						form.setTags("project," + pv.getProjectType() + ","
								+ "pos,var");
						form.setCategoryPath(getDmssCategory(realFile));
						try {
							String dfsId = dmssService.upload(file, form);
							if (null != dfsId) {
								Map paraMap = new HashMap();
								paraMap.put("id", results.get(0));
								paraMap.put("postponementPlanDfs", dfsId);// 变更延期申请书云存储ID
								dao.execute(
										"update ProjectVariation pv set pv.postponementPlanDfs=:postponementPlanDfs where pv.id=:id",
										paraMap);
								count++;
								if (count%100==0) {
									sb.append("upload " + count + " "
											+ System.getProperty("line.separator"));
									o.write(sb.toString().getBytes());
									o.flush();
									sb.setLength(0);
								}
							} else {
								sb.append("upload " + file + " to dmss failed  " 
										+ System.getProperty("line.separator"));
							}
						} catch (Exception e) {
							error++;
							sb.append("upload file " + realFile
									+ " failed error" + e.toString()
									+ System.getProperty("line.separator"));
							o.write(sb.toString().getBytes());
							o.flush();
							sb.setLength(0);
							e.printStackTrace();
						} 
					}
					o.write(sb.toString().getBytes());
					o.flush();
					sb.setLength(0);
					continue;// 变更延期申请处理结束
				}

				pMap.put("varFile", realFile);

				results = dao
						.query("select pv.id from ProjectVariation pv where pv.file=:varFile",
								pMap);
				if (results.size() > 1) {
					System.out.println("have more record in database File: "
							+ realFile);
					error++;
					sb.append("have more records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));

				} else if (results.size() == 0) {
					System.out.println("hava no record in database File: "
							+ realFile);
					error++;
					sb.append("have no record in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(realFile));
					form.setFileName(getFileName(realFile));

					ProjectVariation pv = (ProjectVariation) dao.query(
							ProjectVariation.class, (String) results.get(0));
					ProjectGranted pg = (ProjectGranted) dao.query(
							ProjectGranted.class, pv.getGrantedId());
					form.setSourceAuthor(pg.getApplicantName());
					form.setRating("5.0");
					form.setTags("project," + pv.getProjectType() + "," + "var");
					form.setCategoryPath(getDmssCategory(realFile));

					try {
						String dfsId = dmssService.upload(file, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0));
							paraMap.put("dfsId", dfsId);
							System.out.println("upload " + count + " "
									+ realFile);
							count++;
							if (count%100==0) {
								sb.append("upload " + count + " "
										+ System.getProperty("line.separator"));
								o.write(sb.toString().getBytes());
								o.flush();
								sb.setLength(0);
							}
							dao.execute(
									"update ProjectVariation pv set pv.dfs=:dfsId where pv.id=:id",
									paraMap);
							continue;
						} else {
							sb.append("upload " + file + " to dmss failed  " 
									+ System.getProperty("line.separator"));
						}
					} catch (Exception e) {
						error++;
						sb.append("upload file " + realFile
								+ " failed error " + e.toString()
								+ System.getProperty("line.separator"));
						o.write(sb.toString().getBytes());
						o.flush();
						sb.setLength(0);
						e.printStackTrace();
					}

				}
				o.write(sb.toString().getBytes());
				o.flush();
				sb.setLength(0);
				continue;
			}

			if (realFile.contains("end")) {// 结项
				List<Object> results = null;
				pMap.put("endFile", realFile);
				results = dao
						.query("select pe.id from ProjectEndinspection pe where pe.file=:endFile",
								pMap);
				if (results.size() > 1) {
					System.out
							.println("have more than one record in database File:"
									+ realFile);
					error++;
					sb.append("have more records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else if (results.size() == 0) {
					System.out.println("have no record in database File:"
							+ realFile);
					error++;
					sb.append("have no records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(realFile));
					form.setFileName(getFileName(realFile));

					String idString = (String) results.get(0);
					ProjectEndinspection pe = dao.query(
							ProjectEndinspection.class, idString);
					form.setSourceAuthor(pe.getGranted().getApplicantName());

					form.setRating("5.0");
					form.setTags("project," + pe.getProjectType() + "," + "end");
					form.setCategoryPath(getDmssCategory(realFile));
					try {
						String dfsId = dmssService.upload(file, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0));
							paraMap.put("dfsId", dfsId);
							dao.execute(
									"update ProjectEndinspection pe set pe.dfs=:dfsId where pe.id=:id",
									paraMap);
							System.out.println("upload " + count + " "
									+ realFile);
							count++;
							if (count%100==0) {
								sb.append("upload " + count + " " 
										+ System.getProperty("line.separator"));
								o.write(sb.toString().getBytes());
								o.flush();
								sb.setLength(0);
							}
							continue;
						} else {
							sb.append("upload " + file + " to dmss failed  " 
									+ System.getProperty("line.separator"));
						}
					} catch (Exception e) {
						error++;
						sb.append("upload file " + realFile
								+ " failed error " + e.toString()
								+ System.getProperty("line.separator"));
						o.write(sb.toString().getBytes());
						o.flush();
						sb.setLength(0);
						e.printStackTrace();
					}
				}
				o.write(sb.toString().getBytes());
				o.flush();
				sb.setLength(0);
				continue;
			}

			if (realFile.contains("mid")) {// 中检
				List<Object> results = null;
				pMap.put("midFile", realFile);
				results = dao
						.query("select pm.id from ProjectMidinspection pm where pm.file=:midFile",
								pMap);
				if (results.size() > 1) {
					error++;
					sb.append("have more records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else if (results.size() == 0) {
					error++;
					sb.append("have no records in dataBase File: " + realFile
							+ System.getProperty("line.separator"));
				} else {
					ThirdUploadForm form = new ThirdUploadForm();
					form.setTitle(getFileTile(realFile));
					form.setFileName(getFileName(realFile));

					ProjectMidinspection pm = (ProjectMidinspection) dao
							.query(ProjectMidinspection.class,
									(String) results.get(0));
					form.setSourceAuthor(pm.getGranted().getApplicantName());
					form.setRating("");
					form.setTags("project," + pm.getProjectType() + ",mid");
					form.setCategoryPath(getDmssCategory(realFile));
					try {
						String dfsId = dmssService.upload(file, form);
						if (null != dfsId) {
							Map paraMap = new HashMap();
							paraMap.put("id", results.get(0));
							paraMap.put("dfsId", dfsId);
							dao.execute(
									"update ProjectMidinspection pm set pm.dfs=:dfsId where pm.id=:id",
									paraMap);
							count++;
							if (count%100==0) {
								sb.append("upload " + count + " " 
										+ System.getProperty("line.separator"));
								o.write(sb.toString().getBytes());
								o.flush();
								sb.setLength(0);
							}
							continue;
						} else {
							sb.append("upload " + file + " to dmss failed  " 
									+ System.getProperty("line.separator"));
						}
					} catch (Exception e) {
						error++;
						sb.append("upload file " + realFile
								+ " failed error" + e.toString()
								+ System.getProperty("line.separator"));
						o.write(sb.toString().getBytes());
						o.flush();
						sb.setLength(0);
						e.printStackTrace();
					}
				}
				o.write(sb.toString().getBytes());
				o.flush();
				sb.setLength(0);
				continue;
			}
			error++;
			sb.append("upload file " + realFile
					+ " doesn't match "
					+ System.getProperty("line.separator"));
			o.write(sb.toString().getBytes());
			o.flush();
			sb.setLength(0);
		}
		sb.append("totle file numbers: " + fileList.size()
				+ System.getProperty("line.separator")
				+ "error file numbers: " + error
				+ System.getProperty("line.separator")
				+ "success file numbers: " + count
				+ System.getProperty("line.separator"));

		o.write(sb.toString().getBytes());
		o.close();
	}

	public void importProduct() {
		// String basePath = "upload/product"
		this.getClass();
	}
	
	public String getDataSource() {
		Properties prop = new Properties(); 
		InputStream in = this.getClass().getResourceAsStream("/init.properties"); 
		try {
			prop.load(in);
			return prop.getProperty("jndiname");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void getFile(File file) {// 递归获取路径下所有文件
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				getFile(subFile);
			}
		} else {
			fileList.add(file.toString());
		}
	}

	public void uploadFile(String filePath, String fileType) {
		String realFile = filePath.replace("\\", "/");
		Map pMap = new HashMap();
		pMap.put("file", realFile);
	}

	private String getFileTile(String filePath) {
		if (filePath.lastIndexOf(".") > -1) {
			return filePath.substring(filePath.lastIndexOf("/") + 1,
					filePath.lastIndexOf("."));
		} else {
			return getFileName(filePath);
		}
	}

	private String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	/**
	 * 根据SMDB文档存储的相对路径，确定在DMSS上存储的相对目录
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDmssCategory(String filePath) {
		return "/SMDB/" + getRelativeFileDir(filePath);
	}

	/**
	 * 获取上传后的文件的相对目录 （比如
	 * filePath为"upload/award/moesocial/app/2001/hello.doc",则返回目录为
	 * "award/moesocial/app/2001"）
	 * 
	 * @param filePath
	 *            上传后的文件相对路径
	 * @return 上传后的文件的相对目录
	 */
	public String getRelativeFileDir(String filePath) {
		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}
		return filePath.substring(filePath.indexOf("/") + 1,
				filePath.lastIndexOf("/"));
	}
	
	public void writeLog(String str) {
		super.getClass().getName();
	}

	@Override
	public void work() throws Exception {
//		 importAward();
//		importProject();
	}
	public static void main(String[] args) throws IOException {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		ac.getBean("dataSource");
//		File fileString = new File("WEB-INF\\classes\\init.properties");
//		System.out.println(fileString.getAbsolutePath());
//		InputStream is = new FileInputStream(fileString);  
//		Properties properties = new Properties();  
//		properties.load(is);  
		ImportDocToDmss im = new ImportDocToDmss();
		String jndiname = im.getDataSource();
//		Object dataSource = SpringBean.getBean("dataSource",ApplicationContainer.sc);
		System.out.println(jndiname);
		String dataSource = jndiname.substring(jndiname.indexOf("smdb"));
		if (dataSource.equals("smdbtest")) {
			System.out.println("正式库");
		} else {
			System.out.println("测试库");
		}
	}

}
