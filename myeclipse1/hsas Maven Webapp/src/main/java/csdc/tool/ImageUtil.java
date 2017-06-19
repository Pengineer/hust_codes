package csdc.tool;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
  
/**
 * 图片压缩工具类 提供的方法中可以设定生成的 缩略图片的大小尺寸、压缩尺寸的比例、图片的质量等
 * <pre>
 * 	调用示例：
 * resiz(srcImg, tarDir + "car_1_maxLength_11-220px-hui.jpg", 220, 0.7F);
 * </pre> 
 * 
 * @project haohui-b2b
 * @author cevencheng
 * @create 2012-3-22 下午8:29:01
 */
public class ImageUtil {  
	 /** 
     * * 图片文件读取 
     *  
     * @param srcImgPath 
     * @return 
     */  
    public static BufferedImage inputImage(String srcImgPath) throws RuntimeException {  
  
        BufferedImage srcImage = null;
        FileInputStream in = null;
        try {  
            // 构造BufferedImage对象  
            File file = new File(srcImgPath);  
            in = new FileInputStream(file);  
            byte[] b = new byte[5];  
            in.read(b);  
            srcImage = javax.imageio.ImageIO.read(file);  
        } catch (IOException e) {  
        	e.printStackTrace();  
        	throw new RuntimeException("读取图片文件出错！", e);
        } finally {
        	if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException("读取图片文件出错！", e);
				}
			}
        }
        return srcImage;  
    }  
    
    public static void resizeFiexedWidth(String srcPath,String destPath,int width) throws IOException{   	
    	BufferedImage src = inputImage(srcPath);   	
    	BufferedImage out = Scalr.resize(src,Scalr.Mode.FIT_TO_WIDTH,width);
    	ImageIO.write(out, "jpeg", new File(destPath)); 
    }
    
    /**
     * 在尽可能获取图片信息的情况下，按设定宽和高进行压缩，有可能图片部分信息丢失
     * @param srcPath
     * @param width
     * @param height
     * @throws IOException
     */
    public static void resizeFiexed(String srcPath,String destPath,int nWidth,int nHeight) throws IOException{   	
    	BufferedImage src = inputImage(srcPath);   
    	BufferedImage out = null;
    	int oWidth = src.getWidth();
    	int oHeight = src.getHeight();
    	if(oWidth*nHeight>nWidth*oHeight){//按高度的比例压缩
    		out = Scalr.resize(src, Scalr.Mode.FIT_TO_HEIGHT,nHeight);
    		int x = (out.getWidth() - nWidth)/2;
    		out = Scalr.crop(out,x, 0, nWidth, nHeight);
    	}else {
    		out = Scalr.resize(src, Scalr.Mode.FIT_TO_WIDTH,nWidth);
    		int y = (out.getHeight() - nHeight)/2;
    		out = Scalr.crop(out,0, y, nWidth, nHeight);    
    	}
    	ImageIO.write(out, "jpeg", new File(destPath)); 
    }
    
    /**
    * 在尽可能获取图片信息的情况下，按设定宽和高进行压缩，有可能图片部分信息丢失
    * @param srcPath
    * @param width
    * @param height
    * @throws IOException
    */
   public static void resizeFiexed(InputStream in,String destPath,int nWidth,int nHeight) throws IOException{   	
   	BufferedImage src = ImageIO.read(in);   
   	BufferedImage out = null;
   	int oWidth = src.getWidth();
   	int oHeight = src.getHeight();
   	if(oWidth*nHeight>nWidth*oHeight){//按高度的比例压缩
   		out = Scalr.resize(src, Scalr.Mode.FIT_TO_HEIGHT,nHeight);
   		int x = (out.getWidth() - nWidth)/2;
   		out = Scalr.crop(out,x, 0, nWidth, nHeight);
   	}else {
   		out = Scalr.resize(src, Scalr.Mode.FIT_TO_WIDTH,nWidth);
   		int y = (out.getHeight() - nHeight)/2;
   		out = Scalr.crop(out,0, y, nWidth, nHeight);    
   	}
   	ImageIO.write(out, "jpeg", new File(destPath)); 
   }
}  
