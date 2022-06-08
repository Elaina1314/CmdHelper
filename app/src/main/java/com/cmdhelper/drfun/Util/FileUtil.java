package com.cmdhelper.drfun.Util;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUtil {

    private static final int BUFFER = 1024;

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void writeFile(String path, String data) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(data);
        bufferedWriter.close();
    }

    @NonNull
    public static String readFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String string = null;
        StringBuilder stringBuilder = new StringBuilder();
        while((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                f.delete();
            }
        }
        return file.delete(); // 删除文件夹
    }

    public static boolean createDirectory(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    public static String unzip(String filePath,String zipDir) {
        String name = "";
        try {
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile(filePath);

            Enumeration dir = zipfile.entries();
            while (dir.hasMoreElements()){
                entry = (ZipEntry) dir.nextElement();

                if( entry.isDirectory()){
                    name = entry.getName();
                    name = name.substring(0, name.length() - 1);
                    File fileObject = new File(zipDir + name);
                    fileObject.mkdir();
                }
            }

            Enumeration e = zipfile.entries();
            while (e.hasMoreElements()) {
                entry = (ZipEntry) e.nextElement();
                if( entry.isDirectory()){
                    continue;
                }else{
                    is = new BufferedInputStream(zipfile.getInputStream(entry));
                    int count;
                    byte[] dataByte = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(zipDir+entry.getName());
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = is.read(dataByte, 0, BUFFER)) != -1) {
                        dest.write(dataByte, 0, count);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    @NonNull
    public static List<String> getZipFileName(String path) {
        List<String> list = new ArrayList<>();
        try {
            ZipFile zipFile = new ZipFile(path);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                list.add(entries.nextElement().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 封装上面的类
    public static boolean isZipHasFile(String path, String fileName) {
        List<String> fileNames = getZipFileName(path);
        return fileNames.contains(fileName); // 判断是否存在
    }
}
