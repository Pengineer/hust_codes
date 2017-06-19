package csdc.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件夹zip压缩，支持win和linux
 */
public class ZipUtil {
	/**
	 * @param inputFileName
	 *            文件夹
	 * @param zipFileName
	 *            打包后文件名字
	 * @throws Exception
	 */
	public static OutputStream zip(String inputFileName, String zipFileName) throws Exception {
		return zip(zipFileName, new File(inputFileName));
	}

	private static OutputStream zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		out.setEncoding("UTF-8");// 解决linux乱码
		zip(out, inputFile, "");
		out.close();
		return out;
	}

	public static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) { // 判断是否为目录
			File[] fl = f.listFiles();
//			ZipEntry zipEntry = new ZipEntry(base + System.getProperties().getProperty("file.separator"));
//			zipEntry.setUnixMode(755);// 解决linux乱码
//			out.putNextEntry(zipEntry);
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
}
