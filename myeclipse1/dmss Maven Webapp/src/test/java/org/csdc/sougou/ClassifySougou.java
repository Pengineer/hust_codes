package org.csdc.sougou;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.csdc.tool.FileTool;

public class ClassifySougou {
	/**
	 * 提取样本数据集和测试数据集，训练样本
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String allDir = "E:\\系自主创新\\研究生论文\\ClassFile";
		String sampleDir = "E:\\系自主创新\\研究生论文\\分类测试文本\\sample";
		String testDir = "E:\\系自主创新\\研究生论文\\分类测试文本\\test";
		Collection<File> allFiles = FileUtils.listFiles(new File(allDir), null, true);
		String dest = null;
		int n = 0;
		for (File f:allFiles) {
			if(f.isFile()){
				if(Integer.valueOf(f.getName().substring(0,f.getName().indexOf(".")))<7000){
					dest = sampleDir + File.separator+f.getParentFile().getName()+File.separator+f.getParentFile().getName()+"_"+f.getName();
					FileUtils.copyFile(f, new File(dest));
				}else {
					dest = testDir + File.separator+f.getParentFile().getName()+File.separator+f.getParentFile().getName()+"_"+f.getName();
					FileUtils.copyFile(f, new File(dest));
				}
			}
			n++;
			if(n%1000 == 0){
				System.out.println(n+"..."+new Date());
			}
		}
	}
}
