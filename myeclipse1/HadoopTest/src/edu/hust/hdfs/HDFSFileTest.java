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
 * ͨ��FileSystem�ķ�ʽ����HDFS
 * 
 * 1���ļ�����
 * 	1.1���ϴ��ļ�
 * 	1.2���½��ļ�����д��
 * 	1.3��ɾ���ļ�
 * 	1.4����ȡ�ļ�
 * 	1.5���ļ��޸�ʱ��
 * 	1.6�������ļ�
 * 2��Ŀ¼����
 * 	2.1������Ŀ¼
 * 	2.2��ɾ��Ŀ¼
 * 	2.3������hdfs
 * 3��hdfs��Ϣ
 * 	3.1������ĳ���ļ���HDFS��Ⱥ��λ��
 * 	3.2����ȡHDFS��Ⱥ�����нڵ�������Ϣ
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
	
	//��ȡHadoop������Ϣ
	public static void getConfigurationInfo() {
		Configuration conf = new Configuration();
		System.out.println(conf.get("fs.default.name"));
	}
	
	//����Ŀ¼
	public static void mkDir() throws IOException {
		//��ȡHadoop�����ļ�����Ϣ
		Configuration conf = new Configuration();
		
		//��ȡ�ļ�ϵͳ����
		FileSystem hdfs = FileSystem.get(conf);
		
		hdfs.mkdirs(new Path("/user/hadoop/JavaTest"));
		System.out.println("����Ŀ¼");
	}
	
	//ɾ��Ŀ¼
	public static void delDir() throws IOException {
		Configuration conf = new Configuration();
		
		FileSystem hdfs = FileSystem.get(conf);
		
		//�����Path��Ӧ����Ŀ¼��true�������ɾ�������Path��������ļ���true��false�����ԡ�
		hdfs.delete(new Path("/user/hadoop/user/hadoop/JavaTest"), true);
		System.out.println("ɾ��Ŀ¼");
	}
	
	//��ʽһ�������ļ�����д�����ݣ�Ȼ�����
	public static void createFileMethod1() throws IOException {
		Configuration conf = new Configuration();
		
		//appendд���ݿ鷽����Ҫ��Ӵ�����(����ʧ�ܣ�Hadoop1.x��append֧�ֲ���)
		conf.setBoolean("dfs.webhdfs.enabled", true);
		conf.setBoolean("dfs.support.broken.append" ,true);
		conf.setBoolean("dfs.support.append",true);
		
		FileSystem hdfs = FileSystem.get(conf);
		
		boolean flag = hdfs.createNewFile(new Path("/user/hadoop/JavaTest/NewFile.txt"));
		System.out.println("�ļ�����:" + flag);
		
//		FSDataOutputStream outputStream = fs.append(new Path("/user/hadoop/JavaTest/NewFile.txt"));
//		outputStream.writeBytes("Hello Hadoop!");
//		System.out.println("�ļ�д��ɹ�");
		
		FSDataInputStream inputStream = hdfs.open(new Path("/user/hadoop/JavaTest/NewFile.txt"));
		System.out.println("�ļ���ȡ��" + inputStream.readLine());
		
//		outputStream.close();
		inputStream.close();
	}
	
	//��ʽ���������ļ�����д�����ݣ�Ȼ�����
	public static void CreateFileMethod2() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		FSDataOutputStream outputStream = null;
		FSDataInputStream inputStream = null;
		BufferedReader reader = null;
		try{
			outputStream = fs.create(new Path("/user/hadoop/JavaTest/createFile.txt"));
			System.out.println("�ļ������ɹ�");
			byte[] data = "create a new file".getBytes();
			outputStream.write(data, 0, data.length);//����
			outputStream.writeBytes("\r" + "writeBytes" + "\r");//����
			outputStream.writeChars("writeChars" + "\r\n");//Ӧ����ȫ����
			outputStream.writeUTF("writeUTF" + "\r");//д�������루ʵ�����������в���������д�ļ���
			outputStream.writeUTF("writeUTF goon" + "\r");
			outputStream.writeUTF("welcome guy" + "\r");
			outputStream.writeUTF("welcome hust" + "\r");
			outputStream.writeUTF("writeUTF good" + "\r\n");
			outputStream.flush();
			System.out.println("�ļ�д��ɹ�");

			inputStream = fs.open(new Path("/user/hadoop/JavaTest/createFile.txt"));
			reader = new BufferedReader(new InputStreamReader(inputStream));
			System.out.println("�ļ���ȡ��" + reader.readLine());
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
	
	//�ϴ��ļ���Windows���ص�Hadoop��HDFS
	public static void CopyFromLocalFile() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		Path src = new Path("f:\\test.txt");
		Path dst = new Path("/user/hadoop/JavaTest");
		fs.copyFromLocalFile(true, src, dst); //true��ʾ�ϴ���ɺ�ɾ������Դ�ļ�
		
		FileStatus[] fileStatus = fs.listStatus(dst);
		for (FileStatus file : fileStatus) {
			Date date = new Date(file.getModificationTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			System.out.println("Path:" + file.getPath());
			System.out.println("ModifyDate:" + sdf.format(date));
		}
	}
	
	//ɾ���ļ�
	public static void deleteFile() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs  = FileSystem.get(conf);
		
		boolean isDelete = fs.delete(new Path("/user/hadoop/JavaTest/createFile.txt"), false);//true��false��һ��
		System.out.println("isDelete ? " + isDelete);
	}
	
	//ɾ���ļ���
	public static void deleteFiles() throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs  = FileSystem.get(conf);
		
		boolean isDelete = fs.delete(new Path("/user/hadoop/JavaTestout/"), true);//����ļ��зǿգ���Ҫ�ݹ�ɾ����true��
		System.out.println("isDelete ? " + isDelete);
	}
	
	//����ĳ���ļ��ڼ�Ⱥ�е�λ��
	public static void HDFSInfo() throws IOException {
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(conf);
		
		//��ȡ�ļ�״̬��Ϣ(�ļ�����)
		FileStatus fileStatus = hdfs.getFileStatus(new Path("/user/hadoop/JavaTest/hello.txt"));
		System.out.println("�ļ�����޸�ʱ�䣺" + fileStatus.getModificationTime());
		
		//��ȡ��Ⱥ������Ϣ
		Long fdbs = hdfs.getDefaultBlockSize(new Path("/user/hadoop/JavaTest/hello.txt"));
		System.out.println("�ļ�Ĭ�Ͽ��С��" + fdbs);
		BlockLocation[] blkLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
		int length = blkLocations.length;
		System.out.println("�ļ�ռ�õĿ���" + length);
		for(int i=0; i <length; i++) {
			String[] hosts = blkLocations[i].getHosts();
			System.out.println(hosts.length);
			System.out.println("�ļ��飺block" + i+1 + "_location:" + hosts[0]);
		}
	}
	
	//��ȡ��Ⱥ�����нڵ������
	public static void getNodeNames() throws IOException {
		Configuration conf = new Configuration();
		//��ȡ�ֲ�ʽ�ļ�ϵͳ
		DistributedFileSystem dfs = (DistributedFileSystem)FileSystem.get(conf);
		//��ȡ���нڵ���
		DatanodeInfo[] dataNodeStats = dfs.getDataNodeStats();
		
		for(int i=0; i<dataNodeStats.length; i++) {
			System.out.println("DataNode_" + i + ":" + "/HostName:"  
				  + dataNodeStats[i].getHostName() + "/Host:"
				  + dataNodeStats[i].getHost() + "/Name:"
				  + dataNodeStats[i].getName() + "/Storage:"
				  + dataNodeStats[i].getStorageID() + "/Level:" 
				  + dataNodeStats[i].getLevel());
		}
		/*��������
		DataNode_0:/HostName:master/Host:192.168.88.155/Name:192.168.88.155:50010/Storage:DS-361234077-192.168.88.155-50010-1436013577164/Level:0
		DataNode_1:/HostName:node1/Host:192.168.88.156/Name:192.168.88.156:50010/Storage:DS-530939881-192.168.88.156-50010-1436066116128/Level:0
		*/
	}

}
