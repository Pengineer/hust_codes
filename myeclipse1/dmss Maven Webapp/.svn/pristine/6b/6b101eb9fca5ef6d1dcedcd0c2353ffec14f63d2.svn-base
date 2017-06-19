package org.csdc.storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.MapFile.Writer;
import org.apache.hadoop.io.Text;
import org.csdc.bean.Application;
import org.csdc.tool.FileTool;
import org.csdc.tool.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hadoop接口类
 * 
 * @author jintf
 * @date 2014-6-16
 */
@Service
public class HdfsDAO {
	@Autowired
	private Application application;

	private String hdfsPath; // hdfs路径，格式为：hdfs://ip:port
	private Configuration conf;
	private List<String> fileList = new ArrayList<String>();

	public HdfsDAO() {
		this.conf = new Configuration();
		System.setProperty("HADOOP_USER_NAME", "root"); // 设置hsdf用户
	}

	public HdfsDAO(String hdfs, Configuration conf) {
		this.hdfsPath = hdfs;
		this.conf = conf;
		System.setProperty("HADOOP_USER_NAME", "root");
	}

	/**
	 * 在HdfsDAO初始化后执行，设置hdfsPath
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		hdfsPath = "hdfs://"
				+ application.getParameter("CLUSTER_HADOOP_MASTER") + "/";
	}

	/**
	 * 创建目录(以/开始的表示从根目录)
	 * 
	 * @param folder
	 * @throws IOException
	 */
	public void mkdirs(String folder) throws IOException {
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		if (!fs.exists(path)) {
			fs.mkdirs(path);
			System.out.println("Create: " + folder);
		}
		fs.close();
	}

	/**
	 * 删除一个文件夹或文件
	 * 
	 * @param folder
	 *            文件夹或文件均可
	 * @throws IOException
	 */
	public void rmr(String folder) throws IOException {
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		try {
			fs.deleteOnExit(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Delete: " + folder);
		fs.close();
	}

	/**
	 * 显示某一目录下的所有文件
	 * 
	 * @param folder
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void ls(String folder) throws IOException {
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FileStatus[] list = fs.listStatus(path);
		System.out.println("ls: " + folder);
		System.out
				.println("==========================================================");
		for (FileStatus f : list) {
			System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath(),
					f.isDir(), f.getLen());
		}
		System.out
				.println("==========================================================");
		fs.close();
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public void createFile(String file, String content) throws IOException {
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		byte[] buff = content.getBytes();
		FSDataOutputStream os = null;
		try {
			os = fs.create(new Path(file));
			os.write(buff, 0, buff.length);
			System.out.println("Create: " + file);
		} finally {
			if (os != null)
				os.close();
		}
		fs.close();
	}

	/**
	 * 复制本地文件到HDFS
	 * 
	 * @param local
	 * @param remote
	 * @throws IOException
	 */
	public void copyFile(String local, String remote) throws IOException {
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.copyFromLocalFile(new Path(local), new Path(remote));
		System.out.println("copy from: " + local + " to " + remote);
		fs.close();
	}

	/**
	 * 从HDFS获取到本地目录
	 * 
	 * @param remote
	 * @param local
	 * @throws IOException
	 */
	public void fetch(String remote, String local) throws IOException {
		OutputStream out = new FileOutputStream(local);
		Path remotePath = new Path(remote);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FSDataInputStream fsdis = null;
		try {
			fsdis = fs.open(remotePath);
			IOUtils.copyBytes(fsdis, out, 4096, false);
		} finally {
			IOUtils.closeStream(fsdis);
			fs.close();
			out.close();
		}
	}

	/**
	 * cat用来显示文件内容
	 * 
	 * @param remoteFile
	 * @throws IOException
	 */
	public void cat(String remoteFile) throws IOException {
		Path path = new Path(remoteFile);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FSDataInputStream fsdis = null;
		System.out.println("cat: " + remoteFile);
		try {
			fsdis = fs.open(path);
			IOUtils.copyBytes(fsdis, System.out, 4096, false);
		} finally {
			IOUtils.closeStream(fsdis);
			fs.close();
		}
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param remotePath
	 * @return
	 */
	public boolean isExists(String remotePath) {
		boolean flag = false;
		try {
			Path path = new Path(remotePath);
			FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
			flag = fs.exists(path);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return flag;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public String getHdfsPath() {
		return hdfsPath;
	}

	public void setHdfsPath(String hdfsPath) {
		this.hdfsPath = hdfsPath;
	}

	/**
	 * 根据文档块号和文件号获取文件二进制流
	 * 
	 * @param blockId
	 *            文档块号
	 * @param fileId
	 *            文档文件号
	 * @return 文档文件二进制流
	 */
	public byte[] getFileByByte(String blockId, String fileId) {
		if (blockId.equals("tmp")) { // 存储于临时存储区
			return getTmpFileByByte(fileId);
		} else { // 存储于持久存储区
			return getPersistentFileByByte(blockId, fileId);
		}
	}

	/**
	 * 获取持久区的文件二进制流
	 * 
	 * @param blockId
	 * @param fileId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public byte[] getPersistentFileByByte(String blockId, String fileId) {
		MapFile.Reader reader = null;
		IntWritable key = new IntWritable(Integer.valueOf(fileId));
		BytesWritable value = new BytesWritable();
		try {
			String uri = hdfsPath + "dmss/" + blockId;
			FileSystem fs = FileSystem.get(URI.create(uri), conf);
			reader = new MapFile.Reader(fs, uri, conf);
			reader.get(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(reader);
		}
		return value.copyBytes();
	}

	/**
	 * 获取临时区的文件二进制流
	 * 
	 * @param fileId
	 * @return
	 */
	public byte[] getTmpFileByByte(String fileId) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			FSDataInputStream fsdis = null;
			Path remotePath = new Path("/dmss/tmp/" + fileId);
			FileSystem fs = null;
			fs = FileSystem.get(URI.create(hdfsPath), conf);
			fsdis = fs.open(remotePath);
			IOUtils.copyBytes(fsdis, out, 4096, false);
			IOUtils.closeStream(fsdis);
			// fs.close();
			out.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 把文件推送至HDFS
	 * 
	 * @param filePath
	 *            本地文件路径
	 * @return Object数组，Object[0]为块号，Object[1]为文件号
	 * @throws IOException
	 */
	public String[] push(String filePath) {
		String fileId = MD5.getMD5(new File(filePath));
		String uri = hdfsPath + "dmss/tmp/" + fileId;
		FileSystem fs;
		try {
			fs = FileSystem.get(URI.create(hdfsPath), conf);
			byte[] buff = FileTool.getFileByByte(filePath);
			FSDataOutputStream os = null;

			os = fs.create(new Path(uri));
			os.write(buff, 0, buff.length);

			if (os != null)
				os.close();

			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String[] { "tmp", fileId };
	}

	/**
	 * 获取下一个块序号
	 * 
	 * @param path
	 *            (年月，例如201401,201402)
	 * @return
	 */
	public String getCurBlockId(String pathString) {
		String blockId = "0";
		String folder = "/dmss/" + pathString;
		// 查当前块序号
		try {
			long time = 0l;
			String maxpath = null;// 块序号从1开始
			Path path = new Path(folder);
			if (!isExists(folder)) {
				mkdirs(folder);
				return blockId;
			}
			FileSystem fs = FileSystem
					.get(URI.create(getHdfsPath()), getConf());
			FileStatus[] list = fs.listStatus(path);
			for (FileStatus f : list) {
				if (f.getModificationTime() > time) {
					maxpath = f.getPath().toString();
					time = f.getModificationTime();
				}
			}
			fs.close();
			if (maxpath != null) {
				blockId = maxpath.substring(maxpath.lastIndexOf("/") + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blockId;
	}

	/**
	 * 获取下个块ID号
	 * 
	 * @param pathString
	 * @return
	 */
	public String getNextBolckId(String pathString) {
		Integer a = Integer.valueOf(getCurBlockId(pathString));
		a++;
		return a.toString();
	}

	/**
	 * 获取hadoop端文件目录，在控制台显示
	 */
	public void listStatus() throws IOException {
		String uri = hdfsPath + "dmss/tmp";
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		Path path = new Path(uri);
		FileStatus[] status = fs.listStatus(path);
		Path[] listedPaths = FileUtil.stat2Paths(status);
		System.out.println("there are:" + listedPaths.length + " files in "
				+ path.toString());
		for (Path p : listedPaths) {
			System.out.println(p);
		}
	}

	public void search(Path path) throws IOException {
		FileSystem fs = FileSystem.get(URI.create(path.toString()), conf);
		FileStatus[] status = fs.listStatus(path);
		Path[] listedPaths = FileUtil.stat2Paths(status);
		for (Path p : listedPaths) {
			if (fs.isDirectory(p)) {
				search(p);
			} else {
				addFileto(p.toString());
			}
		}
	}

	public List<String> getHdfsFileList() throws IOException {
		String baseuri = hdfsPath + "dmss/";
		Path path = new Path(baseuri);
		search(path);
		return getFileList();
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public void addFileto(String fileString) {
		this.fileList.add(fileString);
	}

	public boolean merge(List<String> fileId, String blockId)
			throws IOException {
		IntWritable key = new IntWritable();
		Text value = new Text();
		String baseUrl = "hdfs://192.168.88.160:9000/dmss/";
		String url = baseUrl + blockId;
		FileSystem fs = FileSystem.get(URI.create(url), conf);
		@SuppressWarnings("deprecation")
		Writer writer = new Writer(conf, fs, url, key.getClass(),
				value.getClass());
		for (int i = 0; i < fileId.size(); i++) {
			key.set(i);
			value.set(this.getTmpFileByByte(fileId.get(i)));
			if (value.getLength() == 0) {// 若文档不存在，则返回停止合并为文档
				System.err.println("文档" + fileId.get(i) + "不存在！");
				IOUtils.closeStream(writer);
				return false;
			} else {
				try {
					writer.append(key, value);

				} catch (Exception e) {
					e.printStackTrace();
					IOUtils.closeStream(writer);
					return false;
				}
			}

		}
		IOUtils.closeStream(writer);
		return true;
	}

	/**
	 * 将data中的文件读取出来，在tmp区建立新的文件
	 * 
	 * @param fingerprint
	 * @param fileIds
	 * @param blockId
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public boolean split(List<String> fingerprint, List<String> fileIds,
			String blockId) throws IOException {
		//
		IntWritable key = new IntWritable();
		Text value = new Text();
		String baseUrl = "hdfs://192.168.88.160:9000/dmss/";
		String tmpUrl = "hdfs://192.168.88.160:9000/dmss/tmp/";
		String url = baseUrl + blockId;
		FileSystem fs = FileSystem.get(URI.create(url), conf);
		MapFile.Reader reader = null;

		for (int i = 0; i < fileIds.size(); i++) {
			key.set(Integer.valueOf(fileIds.get(i)));
			reader = new MapFile.Reader(fs, url, conf);
			reader.get(key, value);
			this.createFile(tmpUrl + fingerprint.get(i), value.toString());
		}
		return true;

	}
}