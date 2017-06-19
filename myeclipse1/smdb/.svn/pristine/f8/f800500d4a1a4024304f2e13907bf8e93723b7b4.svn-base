package csdc.tool.captcha;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import csdc.tool.ApplicationContainer;

/**
 * 国自科网站验证码识别器
 * @author jintf
 * 使用方法：直接调用CaptchaRecognizer.recoginze(file)方法返回识别出的字符串
 */
public class CaptchaRecognizer {

	private static Map<BufferedImage, String> trainMap = null;
	private static int index = 0;

	public static int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
			return 1;
		}
		return 0;
	}

	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return 1;
		}
		return 0;
	}

	public static BufferedImage removeBackgroud(String picFile)
			throws Exception {
		BufferedImage img = ImageIO.read(new File(picFile));
		int width = img.getWidth();  
	    int height = img.getHeight();  
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);  
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = img.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  
	        }  
	    } 
		return reverse(grayImage);
	}	
	
	/**
	 * 图像反色
	 * @param picFile
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage reverse(BufferedImage img)
			throws Exception {
		int width = img.getWidth();  
	    int height = img.getHeight();  
		BufferedImage reverseImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);  
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = img.getRGB(i, j);  
	        Color col = new Color(rgb);
	        int r = col.getRed();
	        int g = col.getGreen();
	        int b = col.getBlue();
	        int r_ = 255-r;
	        int g_ = 255-g;
	        int b_ = 255-b;
	        Color newCol = new Color(r_,g_,b_);
	        reverseImage.setRGB(i, j, newCol.getRGB());  
	        }  
	    } 
		return reverseImage;
	}
	

	public static BufferedImage removeBlank(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();
		int start = 0;
		int end = 0;
		Label1: for (int y = 0; y < height; ++y) {
			int count = 0;
			for (int x = 0; x < width; ++x) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					count++;
				}
				if (count >= 1) {
					start = y;
					break Label1;
				}
			}
		}
		Label2: for (int y = height - 1; y >= 0; --y) {
			int count = 0;
			for (int x = 0; x < width; ++x) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					count++;
				}
				if (count >= 1) {
					end = y;
					break Label2;
				}
			}
		}
		return img.getSubimage(0, start, width, end - start + 1);
	}

	public static List<BufferedImage> splitImage(BufferedImage img)
			throws Exception {
		List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
		int width = img.getWidth();
		int height = img.getHeight();
		List<Integer> weightlist = new ArrayList<Integer>();
		for (int x = 0; x < width; ++x) {
			int count = 0;
			for (int y = 0; y < height; ++y) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					count++;
				}
			}
			weightlist.add(count);
		}
		for (int i = 1; i < weightlist.size();i++) {
			if(weightlist.get(i-1)==0 && weightlist.get(i)>0){
				int j = i;
				while (weightlist.get(j++) > 0) {

				}
				subImgs.add(removeBlank(img.getSubimage(i-1, 0,
						j-i, height)));
			}
		}
		return subImgs;
	}

	public static Map<BufferedImage, String> loadTrainData() throws Exception {
		if (trainMap == null) {
			Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
			File dir = new File(ApplicationContainer.sc.getRealPath("/train"));
			File[] files = dir.listFiles();
			for (File file : files) {
				if(!file.getName().contains(".svn")) {
					map.put(ImageIO.read(file), file.getName().charAt(0) + "");
				}
			}
			trainMap = map;
		}
		return trainMap;
	}

	public static String getSingleCharOcr(BufferedImage img,
			Map<BufferedImage, String> map) {
		String result = "";
		int width = img.getWidth();
		int height = img.getHeight();
		int min = width * height;
		for (BufferedImage bi : map.keySet()) {
			int count = 0;
			int widthmin = width < bi.getWidth() ? width : bi.getWidth();
			int heightmin = height < bi.getHeight() ? height : bi.getHeight();
			Label1: for (int x = 0; x < widthmin; ++x) {
				for (int y = 0; y < heightmin; ++y) {
					if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
						count++;
						if (count >= min)
							break Label1;
					}
				}
			}
			if (count < min) {
				min = count;
				result = map.get(bi);
			}
		}
		return result;
	}

	/**
	 * 识别验证码文件中的字符
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static String recognize(String input)  {
		String result = null;
		try {
			BufferedImage img = removeBackgroud(input);
			List<BufferedImage> listImg = splitImage(img);
			Map<BufferedImage, String> map = loadTrainData();
			result = "";
			for (BufferedImage bi : listImg) {
				result += getSingleCharOcr(bi, map);
			}
		} catch (Exception e) {
			return "0000";
		}
		return result;
	}

	public static void downloadImage() {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = null;
		for (int i = 0; i < 30; i++) {
			getMethod = new GetMethod("http://isisn.nsfc.gov.cn/egrantindex/validatecode.jpg");
			try {
				// 执行getMethod
				int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: "
							+ getMethod.getStatusLine());
				}
				// 读取内容
				String picName = ApplicationContainer.sc.getRealPath("/img2/" + i + ".jpg");
				InputStream inputStream = getMethod.getResponseBodyAsStream();
				OutputStream outStream = new FileOutputStream(picName);
				IOUtils.copy(inputStream, outStream);
				outStream.close();
				System.out.println(i + "OK!");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 释放连接
				getMethod.releaseConnection();
			}
		}
	}

	/**
	 * 请求验证码
	 * @return cookie
	 */
	public static String checkCode() {
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
		sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String date = sf.format(new Date()).replaceAll(" ", "%20") + "%20GMT+0800";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod("http://isisn.nsfc.gov.cn/egrantindex/validatecode.jpg?date=" + date);
		InputStream inputStream = null;
		String cookie = null;
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			Cookie[] cookies = httpClient.getState().getCookies();
			cookie = cookies[0].toString();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
			String picName = ApplicationContainer.sc.getRealPath("/temp/" + "validatecode.jpg");
			inputStream = getMethod.getResponseBodyAsStream();
			OutputStream outStream = new FileOutputStream(picName);
			IOUtils.copy(inputStream, outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放连接
//			getMethod.releaseConnection();
		}
		return cookie;
	}
	
	public static void trainData() throws Exception {
		File dir = new File(ApplicationContainer.sc.getRealPath("/temp"));
		File[] files = dir.listFiles();
		for (File file : files) {
			BufferedImage img = removeBackgroud(ApplicationContainer.sc.getRealPath("/temp/" + file.getName()));
			List<BufferedImage> listImg = splitImage(img);
			if (listImg.size() == 4) {
				for (int j = 0; j < listImg.size(); ++j) {
					ImageIO.write(listImg.get(j), "JPG", new File(ApplicationContainer.sc.getRealPath("/train/" + file.getName().charAt(j) + "-" + (index++) + ".jpg")));
				}
			}
		}
	}

}
