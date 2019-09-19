package com.sunzn.epub.library;

import android.text.TextUtils;
import android.util.Log;

public class EpubUtils {

    /**
     * 存储content.opf文件路径
     */
    private static final String META_INF_CONTAINER = "META-INF/container.xml";
    /**
     * 默认解压后进行暂存的地址
     */
    private static String SAVE_INFO_PATH = "/sdcard/aEpubHeadInfo/";

    public static String createCover(String ePubPath, String imgDir, String fileName) {
        //路径是否存在
        if (TextUtils.isEmpty(ePubPath))
            return null;
        //是否是epub书籍
        if (!FileUtils.getFileExtension(ePubPath).equalsIgnoreCase("epub")) {
            return null;
        }
        //思路是将指定部分文件解压至指定目录，上一次如果解析过会有残留，当然也可以不清。
        if (FileUtils.isDir(SAVE_INFO_PATH)) {
            FileUtils.deleteDir(SAVE_INFO_PATH);
        }
        try {
            //存储content.opf文件路径信息
            String contentPath;
            //1.解压MEAT-INF文件，解析container.xml的 rootfile 标签，获取 content.opf 的路径。
            if (ZipUtils.zipSpecifiedFile(ePubPath, SAVE_INFO_PATH, META_INF_CONTAINER)) {
                contentPath = XmlUtils.xmlSubtagNameAnalysis(SAVE_INFO_PATH + META_INF_CONTAINER, "rootfiles", "rootfile", "full-path");
            } else {
                Log.e("epub解析", ePubPath + "解析错误，请检查书本");
                return null;
            }
            //2.解压获取到的content.opf路径，并用xml解析获取书名、作者等信息
            if (ZipUtils.zipSpecifiedFile(ePubPath, SAVE_INFO_PATH, contentPath)) {
                String imgXmlFlag = XmlHelper.getCoverPath(SAVE_INFO_PATH + contentPath, "manifest", "href", "frontcover");
                if (imgXmlFlag != null) {
                    String imgPath;
                    String[] content = contentPath.split("/");
                    imgPath = content[0] + "/" + imgXmlFlag;
                    if (ZipUtils.zipCover(ePubPath, imgDir, imgPath, fileName)) {
                        return imgDir + fileName;
                    } else {
                        return null;
                    }
                }
            } else {
                Log.e("epub解析", ePubPath + "解析错误，请检查书本（可能原因，书籍被加密）");
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
