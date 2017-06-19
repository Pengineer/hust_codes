package edu.hust.hdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;


/**
 * 通过FileSystem的方式操作HDFS
 * 
 * 1，文件操作
 * 	1.1，上传文件
 * 	1.2，新建文件，并写入
 * 	1.3，删除文件
 * 	1.4，读取文件
 * 	1.5，文件修改时间
 * 	1.6，拷贝文件
 * 2，目录操作
 * 	2.1，创建目录
 * 	2.2，删除目录
 * 	2.3，遍历hdfs
 * 3，hdfs信息
 * 	3.1，查找某个文件在HDFS集群的位置
 * 	3.2，获取HDFS集群上所有节点名称信息
 * 
 * @author liangjian
 *
 */
public class HDFSFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		CreateFileMethod2();
	}
	
	//获取Hadoop配置信息
	public static void getConfigurationInfo() {
		Configuration conf = new Configuration();
		System.out.println(conf.get("fs.default.name"));
	}
	
	//创建目录
	public static void mkDir() throws IOException {
		//读取Hadoop配置文件中信息
		Configuration conf = new Configuration();
		
		//获取文件系统对象
		FileSystem hdfs = FileSystem.get(conf);
		
		hdfs.mkdirs(new Path("/user/hadoop/JavaTest"));
		System.out.println("创建目录");
	}
	
	//删除目录
	public static void delDir() throws IOException {
		Configuration conf = new Configuration();
		
		FileSystem hdfs = FileSystem.get(conf);
		
		//如果是Path对应的是目录，true代表迭代删除；如果Path代表的是文件，true和false都可以。
		hdfs.delete(new Path("/user/hadoop/user/hadoop/JavaTest"), true);
		System.out.println("删除目录");
	}
	
	//方式一：创建文件，并写入数据，然后读出
	public static void createFileMethod1() throws IOException {
		Configuration conf = new Configuration();
		
		//append写数据块方法需要添加此配置(测试失败，Hadoop1.x对append支持不好)
		conf.setBoolean("dfs.webhdfs.enabled", true);
		conf.setBoolean("dfs.support.broken.append" ,true);
		conf.setBoolean("dfs.support.append",true);
		
		FileSystem hdfs = FileSystem.get(conf);
		
		boolean flag = hdfs.createNewFile(new Path("/user/hadoop/JavaTest/NewFile.txt"));
		System.out.println("文件创建:" + flag);
		
//		FSDataOutputStream outputStream = fs.append(new Path("/user/hadoop/JavaTest/NewFile.txt"));
//		outputStream.writeBytes("Hello Hadoop!");
//		System.out.println("文件写入成功");
		
		FSDataInputStream inputStream = hdfs.open(new Path("/user/hadoop/JavaTest/NewFile.txt"));
		System.out.println("文件读取：" + inputStream.readLine());
		
//		outputStream.close();
		inputStream.close();
	}
	
	//方式二：创建文件，并写入数据，然后读出
	public static void CreateFileMethod2() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		FSDataOutputStream outputStream = null;
		FSDataInputStream inputStream = null;
		BufferedReader reader = null;
		try{
			outputStream = fs.create(new Path("/user/hadoop/JavaTest/createFile.txt"));
			System.out.println("文件创建成功");
			byte[] data = "create a new file".getBytes();
			outputStream.write(data, 0, data.length);//正常
			outputStream.writeBytes("\r" + "writeBytes" + "\r");//正常
			outputStream.writeChars("writeChars" + "\r\n");//应该是全角了
			outputStream.writeUTF("writeUTF" + "\r");//写中文乱码（实际生产环境中不允许这样写文件）
			outputStream.writeUTF("writeUTF goon" + "\r");
			outputStream.writeUTF("welcome guy" + "\r");
			outputStream.writeUTF("welcome hust" + "\r");
			outputStream.writeUTF("writeUTF good" + "\r\n");
			outputStream.flush();
			System.out.println("文件写入成功");

			inputStream = fs.open(new Path("/user/hadoop/JavaTest/createFile.txt"));
			reader = new BufferedReader(new InputStreamReader(inputStream));
			System.out.println("文件读取：" + reader.readLine());
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	//上传文件：Windows本地到Hadoop的HDFS
	public static void CopyFromLocalFile() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		Path src = new Path("f:\\test.txt");
		Path dst = new Path("/user/hadoop/JavaTest");
		fs.copyFromLocalFile(true, src, dst); //true表示上传完成后，删除本地源文件
		
		FileStatus[] fileStatus = fs.listStatus(dst);
		for (FileStatus file : fileStatus) {
			Date date = new Date(file.getModificationTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			System.out.println("Path:" + file.getPath());
			System.out.println("ModifyDate:" + sdf.format(date));
		}
	}
	
	//删除文件
	public static void deleteFile() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs  = FileSystem.get(conf);
		
		boolean isDelete = fs.delete(new Path("/user/hadoop/JavaTest/createFile.txt"), false);//true和false都一样
		System.out.println("isDelete ? " + isDelete);
	}
	
	//删除文件夹
	public static void deleteFiles() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs  = FileSystem.get(conf);
		
		boolean isDelete = fs.delete(new Path("/user/hadoop/JavaTestout/"), true);//如果文件夹非空，需要递归删除（true）
		System.out.println("isDelete ? " + isDelete);
	}
	
	//查找某个文件在集群中的位置
	public static void HDFSInfo() throws IOException {
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(conf);
		
		//获取文件状态信息(文件层面)
		FileStatus fileStatus = hdfs.getFileStatus(new Path("/user/hadoop/JavaTest/hello.txt"));
		System.out.println("文件最后修改时间：" + fileStatus.getModificationTime());
		
		//获取集群层面信息
		Long fdbs = hdfs.getDefaultBlockSize(new Path("/user/hadoop/JavaTest/hello.txt"));
		System.out.println("文件默认块大小：" + fdbs);
		BlockLocation[] blkLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
		int length = blkLocations.length;
		System.out.println("文件占用的块数" + length);
		for(int i=0; i <length; i++) {
			String[] hosts = blkLocations[i].getHosts();
			System.out.println(hosts.length);
			System.out.println("文件块：block" + i+1 + "_location:" + hosts[0]);
		}
	}
	
	//获取集群中所有节点的名称
	public static void getNodeNames() throws IOException {
		Configuration conf = new Configuration();
		//获取分布式文件系统
		DistributedFileSystem dfs = (DistributedFileSystem)FileSystem.get(conf);
		//获取所有节点数
		DatanodeInfo[] dataNodeStats = dfs.getDataNodeStats();
		
		for(int i=0; i<dataNodeStats.length; i++) {
			System.out.println("DataNode_" + i + ":" + "/HostName:"  
				  + dataNodeStats[i].getHostName() + "/Host:"
				  + dataNodeStats[i].getHost() + "/Name:"
				  + dataNodeStats[i].getName() + "/Storage:"
				  + dataNodeStats[i].getStorageID() + "/Level:" 
				  + dataNodeStats[i].getLevel());
		}
		/*输出结果：
		DataNode_0:/HostName:master/Host:192.168.88.155/Name:192.168.88.155:50010/Storage:DS-361234077-192.168.88.155-50010-1436013577164/Level:0
		DataNode_1:/HostName:node1/Host:192.168.88.156/Name:192.168.88.156:50010/Storage:DS-530939881-192.168.88.156-50010-1436066116128/Level:0
		*/
	}

}
