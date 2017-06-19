package edu.hust.hbase.index;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.MultiTableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * HBase通常是大表，如果用户给定一个列值，要找到对应的行，那么全表扫描必然很慢
 * 如果我们新建一张表，行健为原表的列值，列为原表的行健，那么查询速度就会快得多，这张表就是索引表
 * 但是由于数据量的过大，索引表的建立是很费时间的，好在HBase集成了MapReduce的功能，提供了分布式处理解决方案
 * 
 * 现有表hereos表，列族info，列name,email,power
 * 需求：在name和email列上分别建立索引
 * 
 * From MultiTableOutputFormat.class:
 * Hadoop output format that writes to one or more HBase tables. The key is
 * taken to be the table name while the output value <em>must</em> be either a
 * {@link Put} or a {@link Delete} instance. All tables must already exist, and
 * all Puts and Deletes must reference only valid column families.
 *
 */

public class IndexBuilder {
	
	//给所有索引表指定统一的列族名和列名（显然索引表只有一个列）
	public static final byte[] INDEX_FAMILY = "IDX".getBytes();
	public static final byte[] INDEX_COLUMN = "COLUMN".getBytes();
	
	/**
	 * TableMapper：
	 * HBase的TableMapper限定了输入类型为ImmutableBytesWritable和Result
	 */
	public static class IndexBuilderMapper extends Mapper<ImmutableBytesWritable, Result, ImmutableBytesWritable, Writable> {
		//"原表列名" --> "原表表名-原表列名"   前者用于索引表的行健，后者用于索引表的表名
		private HashMap<byte[], ImmutableBytesWritable> indexes;
		
		private byte[] family;
		
		//建立索引列名到索引表的映射关系，索引表必须事先存在
		@Override
		public void setup(Context context) throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			String tableName = conf.get("index.tablename");
			String familyName = conf.get("index.familyname");
			String[] fields = conf.getStrings("index.fields");
			
			family = familyName.getBytes();
			
			indexes = new HashMap<byte[], ImmutableBytesWritable>();
			for (String field : fields) {
				indexes.put(Bytes.toBytes(field), new ImmutableBytesWritable(Bytes.toBytes(tableName + "-" + field)));
			}
		}
		
		/**
		 * map的输入参数：rowKey为原表的行健值(将原byte[]类型封装)，row为行对象
		 */
		@Override
		public void map(ImmutableBytesWritable rowKey, Result row, Context context)  throws IOException, InterruptedException{
			
			for (Entry<byte[], ImmutableBytesWritable> index : indexes.entrySet()) {
				byte[] qualifier = index.getKey();                    //原表的列，索引表的行健名
				ImmutableBytesWritable tableName = index.getValue();  //原表表名-原表列名，索引表的表名
				byte[] value = row.getValue(family, qualifier);       //原表的列值，索引表的行健值
				
				if(value != null) {
					Put put = new Put(value);  //建立索引表的一行，value为行健值
					put.add(INDEX_FAMILY, INDEX_COLUMN, rowKey.get());
					context.write(tableName, put);  //HBase在MapReduce框架下操作写数据，第一个参数为已存在的表名，第二个参数必须为Put或Delete类型
				}
			}
		}
	}
	
	public static Job configureJob(Configuration conf, String[] args) throws Exception {
		String tableName = args[0];
		String familyName = args[1];
		
		conf.set(TableInputFormat.SCAN, convertScanToString(new Scan()));
		conf.set(TableInputFormat.INPUT_TABLE, tableName);
		conf.set("index.tablename", tableName);
		conf.set("index.familyname", familyName);
		
		String[] fields = new String[args.length - 2];
		for(int i=0; i < fields.length; i++) {
			fields[i] = args[i+2];
		}
		conf.setStrings("index.fields", fields);
		
		//配置任务的运行参数		
		Job job = new Job(conf, tableName);
		job.setJarByClass(IndexBuilder.class);
		job.setInputFormatClass(TableInputFormat.class);  //HBase自定义的数据输入格式，实现对表的操作
		job.setMapperClass(IndexBuilderMapper.class);
		job.setNumReduceTasks(0);
		job.setOutputFormatClass(MultiTableOutputFormat.class);  //MultiTableOutputFormat是对TableOutputFormat的替代，可控制WAL的写入
		conf.setBoolean("hbase.mapreduce.multitableoutputformat.wal", MultiTableOutputFormat.WAL_ON);//这就是默认设置，默认是写WAL的。
		
		return job;
	}
	
	//ableMapReduceUtil.convertScanToString(Scan scan)的方法在此版本中不可用了，此处重写
	public static String convertScanToString(Scan scan) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(out);
		scan.write(dos);
		return Base64.encodeBytes(out.toByteArray());
	}
	
	public static void main(String[] args) throws Exception {
		args = new String[]{
				"heroes","info","name","email"
		};
		
		
		Configuration conf = HBaseConfiguration.create();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if(otherArgs.length < 3){
			System.err.println("Usage:IndexBuilder <TableName> <Family> <ATTR> [<ATTR>....]");
			System.exit(-1);
		}
		
		Job job = configureJob(conf, otherArgs);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
