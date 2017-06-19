package org.csdc.hadoop;
import java.net.InetSocketAddress;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FsServerDefaults;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.util.VersionInfo;

public class NNConnTest {

    

   


    public static void main(String[] args) throws Exception {
    	System.setProperty("HADOOP_USER_NAME", "root");
        InetSocketAddress namenodeAddr = new InetSocketAddress("master",9000);

        Configuration conf = new Configuration();

        //query NameNode
        DFSClient client = new DFSClient(namenodeAddr, conf);
        ClientProtocol namenode = client.getNamenode();
        long[] stats = namenode.getStats();
        for(long o:stats){
        	System.out.println(o);
        }
        FsServerDefaults fss = namenode.getServerDefaults();
        System.out.println("备份数"+fss.getReplication());
        // System.out.println(stats);
        DatanodeInfo[] datanodeReport = namenode.getDatanodeReport(
                DatanodeReportType.ALL);
        for (DatanodeInfo di : datanodeReport) {
            System.out.println("Host: " + di.getHostName());
            System.out.println(di.getDatanodeReport());                  
        }
        

    }

}