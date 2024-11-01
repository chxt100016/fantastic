package com.chxt.fantasticmonkey.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class FileChangeUtil {

    public static void changeNameByCreation(String path){
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            assert files != null;
            for (File item : files) {
                BasicFileAttributes basicFileAttributes = Files.readAttributes(Paths.get(item.getPath()), BasicFileAttributes.class);
                Date date = new Date(basicFileAttributes.creationTime().toMillis());
                String extension = item.getName().substring(item.getName().lastIndexOf("."));
                String name = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss") + extension;
                File target = new File(item.getPath().replace(item.getName(), name));
                boolean b = item.renameTo(target);
                if (!b) throw new RuntimeException("修改名字失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void changeName(String path){
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            assert files != null;
            for (File item : files) {

                String name = item.getName();
                name = name.replace("1-", "");
                File target = new File(item.getPath().replace(item.getName(), name));
                boolean b = item.renameTo(target);
                if (!b) throw new RuntimeException("修改名字失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
//        FileChangeUtil.changeNameByCreation("/Users/chenxintong/Downloads/123");
        FileChangeUtil.changeName("/Users/chenxintong/book/综合日语/录音/综合日语 1 册整理");
    }
}
