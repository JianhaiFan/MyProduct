package com.xiaofan.product.adaptation.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;

/**
 * @author: 范建海
 * @createTime: 2016/8/27 14:22 
 * @className:  MakeAdaptationFileUtil
 * @Description: 通过普通的JavaSE工程生成适配文件的工具类
 */
public class MakeAdaptationFileUtil {

    public final static String Template ="<dimen name=\"ymsxxxdimensxxxdp\">yyydp</dimen>\n";
    public final static String ROOT_FILE_320 = "values-w320dp";
    public final static String ROOT_FILE_360 = "values-w360dp";
    public final static double GAP_320 = 0.58;
    public final static double GAP_360 = 0.46;

    public final static String ROOT_LDPI = "values-ldpi";
    public final static String ROOT_MDPI = "values-mdpi";
    public final static String ROOT_HDPI = "values-hdpi";
    public final static String ROOT_XHDPI = "values-xhdpi";
    public final static String ROOT_XXHDPI = "values-xxhdpi";
    public final static String ROOT_XXXHDPI = "values-xxxhdpi";

    public final static double GAP_LDPI = 0.38;
    public final static double GAP_MDPI = 0.50;
    public final static double GAP_HDPI = 0.75;
    public final static double GAP_XHDPI = 1.00;
    public final static double GAP_XXHDPI = 1.50;
    public final static double GAP_XXXHDPI = 2.50;

    public static void main(String[] args) {
//        makeAdaptationXml(ROOT_FILE_320, GAP_320);
//        makeAdaptationXml(ROOT_FILE_360, GAP_360);
        makeAdaptationXml(ROOT_LDPI, GAP_LDPI);
        makeAdaptationXml(ROOT_MDPI, GAP_MDPI);
        makeAdaptationXml(ROOT_HDPI, GAP_HDPI);
        makeAdaptationXml(ROOT_XHDPI, GAP_XHDPI);
        makeAdaptationXml(ROOT_XXHDPI, GAP_XXHDPI);
        makeAdaptationXml(ROOT_XXXHDPI, GAP_XXXHDPI);
    }

    /**
     * 生成适配器文件
     * @param filePath
     * @param gap
     */
    private static void makeAdaptationXml(String filePath, double gap) {
        DecimalFormat df1 = new DecimalFormat("#.0");
        DecimalFormat df2 = new DecimalFormat("#.00");
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");

        for(int i = 1;i <= 2001;i ++) {
            String str = Template.replaceAll("xxx", (df1.format(Double.parseDouble(String.valueOf(0.5 * i)))));
            sb.append(str.replaceAll("yyy", df2.format(gap * i)));
        }

        sb.append("</resources>");
        File rootFile = new File(filePath);

        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }

        PrintStream pw = null;

        try {
            pw = new PrintStream(new FileOutputStream(new File(rootFile,"dimens.xml")));
            pw.print(sb.toString());
            pw.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(pw != null) {
                pw.close();
            }
        }

    }
}
