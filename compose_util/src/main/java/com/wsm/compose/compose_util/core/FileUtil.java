package com.wsm.compose.compose_util.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUtil {
    /**
     * 文件上传
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 文件删除
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @return
     */
    public static Map<String, Object> downLoadFile(String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            return null;
        }
        String name = fileName.substring(fileName.lastIndexOf("/") + 1);
        FileInputStream in = new FileInputStream(filePath + name);
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (Exception e2) {
            System.out.println("关闭流失败");
            return null;
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("byteArray", byteArray);
        map.put("name", name);
        return map;
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) {
        String name = "/123/123/123";
        System.out.println(name.substring(0, name.lastIndexOf("/")));
    }

    /**
     * 判断文件是否存在 存在则加入编号
     * 返回文件名
     */
    public static String exists(String filePath, String fileName) {
        //获取文件名
        String lastname = fileName.substring(fileName.lastIndexOf("/") + 1);
        lastname = fileName.substring(0, lastname.lastIndexOf("."));
        //获取文件类型
        String type = fileName.substring(fileName.lastIndexOf("."));
        //判断文件是否存在  存在则加入编号
        File file = new File(filePath + fileName);
        int i = 0;
        while (file.exists()) {
            i++;
            fileName = lastname + i + type;
            file = new File(filePath + fileName);
        }
        return fileName;
    }
}
