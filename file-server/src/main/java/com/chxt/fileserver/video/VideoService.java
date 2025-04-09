package com.chxt.fileserver.video;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    @Value("${video.path}")
    public String videoPath;

    public List<String> search(String name) {
        return this.getFile(this.videoPath + "/" + name);
    }


    private List<String> getFile(String path) {
        List<String> res = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files == null) return new ArrayList<>();
            for (File item : files) {
                if (item.isDirectory()){
                    res.addAll(getFile(item.getPath()));
                } else {
                    int i = item.getName().lastIndexOf(".");
                    if (i > 0) {
                        String sub = item.getName().substring(i + 1);
                        if (sub.equals("mp4")){
                            res.add(item.getPath());
                        }
                    }
                }
            }
        }
        return res;
    }
}
