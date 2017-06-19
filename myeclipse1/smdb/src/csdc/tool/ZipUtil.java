package csdc.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件夹zip压缩，支持win和linux
 */
public class ZipUtil {
	
	/**
	 * 默认编码
	 */
	private static final String ENCODING = "UTF-8";//编码设置为UTF-8 或  GBK
	
	/**
	 * zip压缩
	 * @param inputFileName	待打包的文件夹
	 * @param zipFileName	打包后的zip文件
	 * @param encoding		指定编码（未指定，默认为UTF-8）
	 * @throws Exception
	 */
	public static OutputStream zip(String inputFileName, String zipFileName, String encoding) throws Exception{ 
		if (encoding == null) {//未指定编码
			encoding = ENCODING;
		}
		return zip(zipFileName, new File(inputFileName), encoding);
	}
	
	public static OutputStream zip(String inputFileName, String zipFileName) throws Exception{ 
		return zip(zipFileName, new File(inputFileName), ENCODING);
	}

	private static OutputStream zip(String zipFileName, File inputFile, String encoding) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		out.setEncoding(encoding);// 指定编码GBK，解决linux中文乱码
		zip(out, inputFile, "");
		out.close();
		return out;
	}

	public static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) { // 判断是否为目录
			File[] fl = f.listFiles();
//			ZipEntry zipEntry = new ZipEntry(base + System.getProperties().getProperty("file.separator"));
//			zipEntry.setUnixMode(755);	// 解决linux乱码
//			out.putNextEntry(zipEntry);	// 这一句会在打包后增加一个空文件夹
			base = base.length() == 0 ? "" : base + System.getProperties().getProperty("file.separator");
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else { // 压缩目录中的所有文件
			ZipEntry zipEntry = new ZipEntry(base);
			zipEntry.setUnixMode(644);// 解决linux乱码
			out.putNextEntry(zipEntry);
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}
	
	/**
	 * zip解压缩
	 * @param zipFileName	待解压的zip包
	 * @param destDir		待解压的目录
	 * @param encoding		指定编码（未指定，默认为UTF-8）
	 * @throws Exception
	 */
	public static void unZip(String zipFileName, String destDir, String encoding) throws Exception{ 
		if (encoding == null) {//未指定编码
			encoding = ENCODING;
		}
        try{ 
            Project p = new Project(); 
            Expand e = new Expand(); 
            e.setProject(p); 
            e.setSrc(new File(zipFileName)); 
            e.setOverwrite(false); 
            e.setDest(new File(destDir)); 
            e.setEncoding(encoding);  //根据操作系统的实际编码设置
            e.execute(); 
        }catch(Exception e){ 

            throw e; 
        }
    } 
}
