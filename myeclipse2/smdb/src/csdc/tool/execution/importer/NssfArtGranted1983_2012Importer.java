package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《1983-2012年艺术学项目_修正导入.xls》
 * excel立项年份为空的数据中，不匹配的项目：中国古代演艺史的文物实证——戏曲绘画与戏曲雕刻的考察与研究延保全
   excel立项年份为空的数据中，不匹配的项目：中国历代传统音乐曲牌考略孙光钧
   excel立项年份为空的数据中，不匹配的项目：藏族民间美术研究康·格桑益希
   excel立项年份为空的数据中，不匹配的项目：甘肃省艺术院团现状调查研究马少青
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：中国古代演艺史的文物实证——戏曲绘画与戏曲雕刻的考察与研究延保全
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：邓小平文艺理论研究李源潮
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：中国历代传统音乐曲牌考略孙光钧
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：蒙古族当代音乐研究乌力吉巴雅尔
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：中国网络文化产业现状、发展趋势及对策研究柳士发; 宋奇慧
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：黑龙江艺术地域特色研究朱雪艳; 陈恕
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：西藏艺术研究米玛洛桑; 次旺扎西; 阿旺晋美
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：藏族民间美术研究康格桑益希
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：甘肃省艺术院团现状调查研究马少青
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：西方现代视觉文化与艺术研究易英; 邵亦杨
   2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：闽粤地方戏曲团体经营模式及演出市场研究白勇华; 胡玲玲
 * 注意：以上数据在数据库中都能找到，只是存在错别字现象，暂以数据库中为准，不作处理。
 * @author yuanxiaojun
 */
public class NssfArtGranted1983_2012Importer extends Importer {

    private ExcelReader excelReader;

    @Autowired
    private Tool tool;

    public NssfArtGranted1983_2012Importer() {
    }

    public NssfArtGranted1983_2012Importer(String filePath) {
        excelReader = new ExcelReader(filePath);
    }

    @Override
    public void work() throws Exception {
        importData();
    }

    /**
     * 正式导入数据
     * @throws Exception
     */
    private void importData() throws Exception {

        excelReader.readSheet(0);

        while (next(excelReader)) {
            // 如果负责人为空，那么跳出读取excel，停止导入
            if (C.length() == 0) {
                break;
            }
            // 立项年份不为空的导入
            if (A.length() > 0) {
                int ddd = Integer.parseInt(A.trim());
                if ((ddd > 2008) && (ddd < 2013)) {
                    // 在年份2009——2012，在数据库中查找与excel课题名称一致的数据
                    Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where  nssf.singleSubject ='艺术学' and nssf.name =?",B);
                    //找不到就打印出来
                    if (nssf == null) {
                        System.out.println("2009-2012艺术学数据库找不到的项目：" + B + C);
                    }else {
                        if (nssf.getApplicant().contains(C)) {
                            int flag = 0;//定义一个导入标签，只要它大于0，则数据库有变动，更新导入时间
                            if (nssf.getStartDate() == null) {
                                nssf.setStartDate(tool.getDate(A + "-08-14"));
                                flag++;
                            }
                            if (nssf.getUnit() == null) {
                                nssf.setUnit(D);
                                flag++;
                            }
                            if (nssf.getProductType() == null && E.length() > 0) {
                                String newstr = E.replace("、", "; ");
                                nssf.setProductType(newstr);
                                flag++;
                            }
                            if (nssf.getPlanEndDate() == null && F.length() > 0) {
                                nssf.setPlanEndDate(tool.getDate(F + "-12-31"));
                                flag++;
                            }
                            if (nssf.getStatus() == 0 ) {
                                if (H.contains("在研")) {
                                    nssf.setStatus(1);
                                    flag++;
                                }
                                if (H.contains("完成")) {
                                    nssf.setStatus(2);
                                    flag++;
                                }
                                if (H.contains("撤项")) {
                                    nssf.setStatus(4);
                                    flag++;
                                }
                            }
                            if (nssf.getType() == null && I.length() > 0) {
                                nssf.setType(I);
                                flag++;
                            }
                            if (flag > 0) {
                                nssf.setImportDate(new Date());
                                saveOrUpdate(nssf);
                            }
                        } else {
                            System.out.println("2009到2012年艺术学数据库与excel负责人不匹配的项目：" + B + C);
                        }
                    }
                } else {
                    // 小于2009年且立项年份不为空的年份中，在数据库中查找正在读取课题名称字段的数据
                    Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where  nssf.singleSubject ='艺术学' and nssf.name =?",B);
                        if (nssf == null) {
                        Nssf newNssf = new Nssf();
                        newNssf.setStartDate(tool.getDate(A + "-08-14"));
                        newNssf.setName(B);
                        newNssf.setApplicant(C);
                        newNssf.setUnit(D);
                        String newstr = E.replace("、", "; ");
                        newNssf.setProductType(newstr);
                        newNssf.setPlanEndDate(tool.getDate(F + "-12-31"));
                        if (H.contains("在研")) {
                            newNssf.setStatus(1);
                        }
                        if (H.contains("完成")) {
                            newNssf.setStatus(2);
                        }
                        if (H.contains("撤项")) {
                            newNssf.setStatus(4);
                        }
                        newNssf.setType(I);
                        newNssf.setImportDate(new Date());
                        newNssf.setSingleSubject("艺术学");
                        dao.add(newNssf);
                    }else {// 如果数据库中课题名称和excel一样，就查看数据库其他字段，为空的就写入
                        if (nssf.getApplicant().contains(C)) {
                            int flag = 0;
                            if (nssf.getStartDate() == null) {
                                nssf.setStartDate(tool.getDate(A + "-08-14"));
                                flag++;
                            }
                            if (nssf.getUnit() == null) {
                                nssf.setUnit(D);
                                flag++;
                            }
                            if (nssf.getProductType() == null && E.length() > 0) {
                                String newstr = E.replace("、", "; ");
                                nssf.setProductType(newstr);
                                flag++;
                            }
                            if (nssf.getPlanEndDate() == null && F.length() > 0) {
                                nssf.setPlanEndDate(tool.getDate(F + "-12-31"));
                                flag++;
                            }
                            if (nssf.getStatus() == 0 ) {
                                if (H.contains("在研")) {
                                    nssf.setStatus(1);
                                    flag++;
                                }
                                if (H.contains("完成")) {
                                    nssf.setStatus(2);
                                    flag++;
                                }
                                if (H.contains("撤项")) {
                                    nssf.setStatus(4);
                                    flag++;
                                }
                            }
                            if (nssf.getType() == null && I.length()>0) {
                                nssf.setType(I);
                                flag++;
                            }
                            if (flag > 0) {
                                nssf.setImportDate(new Date());
                                saveOrUpdate(nssf);
                            }
                        } else {
                            System.out.println("2008年之前数据库与excel项目名称匹配但负责人不匹配的项目：" + B + C);
                        }
                    }
                }
            } else {// 立项年份为空, 在数据库中查找正在读取课题名称字段的数据
                Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where  nssf.singleSubject ='艺术学' and nssf.name =?",B);
                // 如果数据库中找不到课题名称，就把各个字段写入数据库
                if (nssf == null) {
                    Nssf newNssf = new Nssf();
                    newNssf.setStartDate(tool.getDate(A + "-08-14"));
                    newNssf.setName(B);
                    newNssf.setApplicant(C);
                    newNssf.setUnit(D);
                    String newstr = E.replace("、", "; ");
                    newNssf.setProductType(newstr);
                    newNssf.setPlanEndDate(tool.getDate(F + "-12-31"));
                    if (H.contains("在研")) {
                        newNssf.setStatus(1);
                    }
                    if (H.contains("完成")) {
                        newNssf.setStatus(2);
                    }
                    if (H.contains("撤项")) {
                        newNssf.setStatus(4);
                    }
                    newNssf.setType(I);
                    newNssf.setImportDate(new Date());
                    newNssf.setSingleSubject("艺术学");
                    dao.add(newNssf);
                }else {// 如果数据库中找得到课题名称，就查看数据库其他字段，为空的就写入
                    if (nssf.getApplicant().contains(C)) {
                        int flag = 0;
                        if (nssf.getStartDate() == null) {
                            nssf.setStartDate(tool.getDate(A + "-08-14"));
                            flag++;
                        }
                        if (nssf.getUnit() == null) {
                            nssf.setUnit(D);
                            flag++;
                        }
                        if (nssf.getProductType() == null && E.length() > 0) {
                            String newstr = E.replace("、", "; ");
                            nssf.setProductType(newstr);
                            flag++;
                        }
                        if (nssf.getPlanEndDate() == null) {
                            nssf.setPlanEndDate(tool.getDate(F + "-12-31"));
                            flag++;
                        }
                        if (nssf.getStatus() == 0 ) {
                            if (H.contains("在研")) {
                                nssf.setStatus(1);
                                flag++;
                            }
                            if (H.contains("完成")) {
                                nssf.setStatus(2);
                                flag++;
                            }
                            if (H.contains("撤项")) {
                                nssf.setStatus(4);
                                flag++;
                            }
                        }
                        if (nssf.getType() == null && I.length() > 0) {
                            nssf.setType(I);
                            flag++;
                        }
                        if (flag > 0) {
                            nssf.setImportDate(new Date());
                            saveOrUpdate(nssf);
                        }
                    } else {
                        System.out.println("excel立项年份为空的数据中，不匹配的项目：" + B + C);
                    }
                }
            }
        }
    }
}