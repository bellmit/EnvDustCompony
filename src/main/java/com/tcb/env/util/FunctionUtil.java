package com.tcb.env.util;

import java.io.File;

/**
 * <p>[功能描述]：静态常用方法</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年2月14日上午8:59:40
 * @since EnvDust 1.0.0
 */
public class FunctionUtil {

    /**
     * <p>[功能描述]：删除单个文件</p>
     *
     * @param fileName
     * @return
     * @author 王垒, 2017年4月6日下午6:43:37
     * @since EnvDust 1.0.0
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        //如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 判断字符长度
     * @param value
     * @return
     */
    public static int StringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

}
