package csdc.tool.dataMining.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * 分类器帮助类
 * @author Batys
 *
 */
public class ClassifierHelper {
	
	/**
	 * 打开并阅读输入文档
	 * @param inputFile
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader open(String inputFile) throws IOException {
		InputStream in;
		try {
			in = Resources.getResource(inputFile).openStream();
		} catch (IllegalArgumentException e) {
			in = new FileInputStream(new File(inputFile));
		}
		return new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
	}
}
