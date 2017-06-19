package csdc.tool;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

public class FileTool {
	// 保存上传文件
	public static String saveUpload(File file, String fileName,
			String savePath, String signID) throws Exception {
		// 获得绝对路径
		String realPath = ServletActionContext.getServletContext().getRealPath(
				savePath);
		//System.out.println(realPath);
		// 获取源文件后缀名
		String extendName = fileName.substring(fileName.lastIndexOf("."));
		//System.out.println(extendName);
		// 获取系统时间并转成字符串
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateformat = format.format(date);
		// 生成新的文件名
		// System.out.println(dateformat);
		// System.out.println(signID);
		String realName = dateformat + extendName;
		// System.out.println(realName);
		// System.out.println(realPath + "\\" + realName);
		// 以realPath和realName建立一个输出流
		FileOutputStream fos = new FileOutputStream(realPath + "/" + realName);
		// 以上传文件建立一个输入流
		FileInputStream fis = new FileInputStream(file);
		// 保存文件
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = fis.read(b)) != -1) {
			fos.write(b, 0, len);
		}
		fis.close();
		fos.close();
		return realName;
	}

	// 删除文件
	public static void fileDelete(String fileName) {
		fileName = ServletActionContext.getServletContext().getRealPath(
				fileName);
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	// 生成缩略图
	public static void scaleImage1(String fromFileSrc,// 原始图片的路径 
			String toFileSrc,// 转化后图片的路径
			int formatWidth,// 格式化图片的宽度 
			int formatHeight) throws IOException {// 格式化图片的高度
		// 原始图片的实际路径 
		String fromPath = ServletActionContext.getServletContext().getRealPath(fromFileSrc);
		// 转化后图片的路径
		String toPath = ServletActionContext.getServletContext().getRealPath(toFileSrc);
		File fi = new File(fromPath);
		File fo = new File(toPath);
		AffineTransform transform = new AffineTransform();
		BufferedImage bis = ImageIO.read(fi);
		int w = bis.getWidth();
		int h = bis.getHeight();
		int nw = 120;
		int nh = (nw * h) / w;
		if(nh>120) {
			nh = 120;
			nw = (nh * w) / h;
		}
		double sx = (double)nw / w;
		double sy = (double)nh / h;
		transform.setToScale(sx,sy);
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
		ato.filter(bis,bid);
		ImageIO.write(bid, "jpeg", fo);
	}
	
	public static void scaleImage2(File pic) throws IOException{
		BufferedImage bi = ImageIO.read(pic);
		int height = bi.getHeight();  
		int width = bi.getWidth();
		//System.out.println("高: " + height);
		//System.out.println("宽: " + width);
		int maxd = height > width ? height : width;
		Image image = bi.getScaledInstance(width*170/maxd, height*170/maxd, Image.SCALE_DEFAULT);//获取缩略图
		BufferedImage oimage = new BufferedImage(width*170/maxd, height*170/maxd, Image.SCALE_DEFAULT);
		oimage.getGraphics().drawImage(image, 0, 0, null);
		ImageIO.write(oimage, "jpeg", pic);
	}
	
	/**
	 * 递归新建目录
	 * @param path 完整路径
	 * @return 是否成功新建目录
	 * @author leida 2011-12-23
	 */
	public static boolean mkdir_p(String path) {
		java.io.File file = new java.io.File(path);
		if(!file.exists()) {
			return file.mkdirs();
		}
		else
			return false;
	}

	/**
	 * 递归删除目录及目录下所有文件、子目录
	 * @param folderPath 目录路径
	 * @author leida 2011-12-23
	 */
	public static void rm_fr(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除目录下所有文件及子目录
	 * @param path 目录路径
	 * @return 是否成功删除
	 * @author leida 2011-12-23
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				rm_fr(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 根据文件路径名判断文件是否存在
	 * @param fileName	文件路径名
	 * @return
	 */
	public static boolean isExsits(String fileName){
		boolean flag = false;
		File file = new File(fileName);
		if (file.exists()) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 将String信息保存制定文件
	 * @param content
	 * @param file
	 */
	public static void saveAsFile(String content,String file) {
		FileWriter fwriter = null;
		try {
			fwriter = new FileWriter(file);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
