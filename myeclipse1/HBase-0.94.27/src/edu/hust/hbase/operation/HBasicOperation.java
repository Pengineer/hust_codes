package edu.hust.hbase.operation;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 一下是基于旧版API
 * 
 * HBase中的数据修改也是put一条数据，行健相同
 * HBase中的数据均以字节码的形式储存
 * HBase的
 * 
 * 新版API操作更方便：http://blog.csdn.net/rocksword/article/details/27530683
 * 
 */
public class HBasicOperation {
	public static void main(String[] args) throws Exception {
//		querySimpleRow("table1","row2");
//		queryFamily("table1", "fam2");
//		queryAllRows("table1");
//		queryRows("table1", "fam1", "col1");
//		count("table1", "fam1", "col1", "val1");
		queryRowsByRange("table1", "row1", "row2");
	}
	
	/**
	 * 创建表，添加列族
	 */
	public static void createTable() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		HTableDescriptor tableDescriptor = new HTableDescriptor("table1".getBytes());
		tableDescriptor.addFamily(new HColumnDescriptor("fam1"));
		admin.createTable(tableDescriptor);
	}
	
	/**
	 * 向已存在的表中添加列族，添加行
	 */
	public static void addColumnAndValue() throws Exception {
		HBaseAdmin admin = getHBaseAdmin();
		
		HTable table = new HTable(getHBaseConfiguration(), "table1");//关联一张HBase表
		
		admin.disableTable("table1");//修改表结构之前要disable table
		admin.addColumn("table1", new HColumnDescriptor("fam2"));//添加列族
		admin.enableTable("table1");
		
		Put putRow1 = new Put("row1".getBytes());
		putRow1.add("fam1".getBytes(), "col1".getBytes(), "val1".getBytes());//插入行数据时，列族中的列可以不事先存在
		table.put(putRow1);
		
		Put putRow2 = new Put("row2".getBytes());
		putRow2.add("fam2".getBytes(), "col1".getBytes(), "val1".getBytes());
		putRow2.add("fam2".getBytes(), "col2".getBytes(), "val2".getBytes());
		table.put(putRow2);
		table.close();
	}
	
	/**
	 * 查询表中所有的row
	 */
	public static void queryAllRows(String tableName) throws Exception {
		HTable table = new HTable(getHBaseConfiguration(), tableName);
		//获取行扫描器Scanner
		ResultScanner rowScanner = table.getScanner(new Scan());
		
		for(Result row : rowScanner) {
			System.out.println(Bytes.toString(row.getRow()));
		}
		table.close();
	}
	
	/**
	 * 基于行健的单条查询
	 */
	public static void querySimpleRow(String tableName, String rowKey) throws Exception {
		HTable table = new HTable(getHBaseConfiguration(), tableName);
		Get row = new Get(rowKey.getBytes());
		Result r = table.get(row);
		//每一行的每一列都是一个keyValue
		for (KeyValue keyValue : r.raw()) {
			System.out.println("键值对---------------------------------------");
			//1， row2fam1col1  N�~,�	2，row2fam2col1  Nײ{E 	3，row2fam2col2  Nײ{E	显然这个key保存的是行健+列族+列名的综合信息 ，它唯一确定一个行健的列值
			System.out.println(new String(keyValue.getKey()));
			System.out.println(new String(r.getRow()));
			System.out.println(Bytes.toString(keyValue.getFamily())); 
			System.out.println(Bytes.toString(keyValue.getQualifier()));
			System.out.println(Bytes.toString(keyValue.getValue()));
			System.out.println(keyValue.getTimestamp());
		}
		table.close();
	}
	
	/**
	 * 基于行健的范围查询
	 */
	public static void queryRowsByRange(String tableName, String startRow, String stopRow) throws Exception {
		HTable table = new HTable(getHBaseConfiguration(), tableName);
		Scan scanner = new Scan();
		scanner.setStartRow(startRow.getBytes());
		scanner.setStopRow(stopRow.getBytes());
		
		ResultScanner rowScanner = table.getScanner(scanner);
		for (Result row : rowScanner) {
			System.out.println(Bytes.toString(row.getRow()));
		}
	}
	
	/**
	 * 基于列族的查询
	 */
	public static void queryRows(String tableName, String family) throws Exception {
		HTable table = new HTable(getHBaseConfiguration(), tableName);
		//获取有列族限定的行扫描器Scanner，等价于table.getScanner(new Scan().addFamily(family.getBytes()))
		ResultScanner rowScanner = table.getScanner(family.getBytes());  
		
		for (Result row : rowScanner) {
			System.out.println("rowkey:" + new String(row.getRow()));
			
			//查询某一行的所有列和列值：方式一
			KeyValue[] raw = row.raw();
			for (KeyValue keyValue : raw) {
				String column = new String(keyValue.getQualifier());
				String value = new String(keyValue.getValue());
				System.out.println(family + ":" + column + "   " + value);
			}
			
			//查询某一行的列和列值：方式二
//			for (Map.Entry<byte[], byte[]> entry : row.getFamilyMap(family.getBytes()).entrySet()){
//				System.out.println(new String(entry.getKey()) + "  " + new String(entry.getValue()));
//			}
		}
		table.close();
	}
	
	/**
	 * 基于列族+列名的查询（所有的列都属于一个列族，不同的列族可以拥有相同的列，因此不能单纯的基于列名来查询）
	 */
	public static void queryRows(String tableName, String family, String qualifier) throws Exception {
		HTable table = new HTable(getHBaseConfiguration(), tableName);
		//获取行扫描器，添加列族和列的限定
		Scan scanner = new Scan();
		scanner.addColumn(family.getBytes(), qualifier.getBytes());
		ResultScanner rowScanner = table.getScanner(scanner);
		
		for(Result row : rowScanner) {
			System.out.println("-------------------------------");
			System.out.println("行健：" + Bytes.toString(row.getRow()));
			System.out.println("列值：" + Bytes.toString(row.getValue(family.getBytes(), qualifier.getBytes())));
		}
		table.close();
	}
	
	////////以上查询均是基于表结构和行健的查询，并不涉及到普通列值，如果要实现RDBMS中基于一般列值的查询，即where语句，可通过程序来实现
	
	/**
	 * 统计表中某一列的列值为给定值的行数（以列值作为查询条件：实际用得少，一般用索引）
	 */
	public static void count(String tableName, String family, String qualifier, String value) throws Exception {
		HTable table = new HTable(getHBaseConfiguration(), tableName);
		//获取行扫描器，添加列族和列的限定
		Scan scanner = new Scan();
		scanner.addColumn(family.getBytes(), qualifier.getBytes());
		ResultScanner rowScanner = table.getScanner(scanner);
		
		int cnt =0;
		for (Result row : rowScanner) {
			String columnValue = Bytes.toString(row.getValue(family.getBytes(), qualifier.getBytes()));
			if (columnValue != null && columnValue.equals(value)) {
				cnt++;
			}
		}
		System.out.println("总数：" + cnt);
	}
	
	
	
	public static Configuration getHBaseConfiguration() {
		return HBaseConfiguration.create();
	}
	
	public static HBaseAdmin getHBaseAdmin() throws Exception {
		Configuration conf = getHBaseConfiguration();
		return new HBaseAdmin(conf);
	}
}
