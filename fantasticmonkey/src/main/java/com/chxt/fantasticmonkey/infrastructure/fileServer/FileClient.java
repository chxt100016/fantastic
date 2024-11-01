package com.chxt.fantasticmonkey.infrastructure.fileServer;

import com.alibaba.fastjson.TypeReference;
import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.model.file.FileInfo;
import com.chxt.fantasticmonkey.model.file.FileInfoParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class FileClient {
    private final static String uri = "http://2202.com:9999/file";

    public List<FileInfo> ls(String path) {
        return new HttpOperator()
                .uri(uri+"/ls")
                .param("path", path)
                .doGet()
                .result(new TypeReference<List<FileInfo>>(){});
    }

    public List<FileInfo> getFile(@RequestBody FileInfoParam param) {
        return new HttpOperator()
                .uri(uri+"/getFile")
                .entity(param)
                .doPost()
                .result(new TypeReference<List<FileInfo>>(){});
    }

    public String getSize(String path) {
        return new HttpOperator()
                .uri(uri+"/getSize")
                .param("path", path)
                .doGet()
                .result();
    }

    public boolean delete(String path) throws IOException {
        return new HttpOperator()
                .uri(uri+"/delete")
                .param("path", path)
                .doGet()
                .result(Boolean.class);
    }

    public boolean rename(String path, String name) {
        return new HttpOperator()
                .uri(uri+"/rename")
                .param("path", path)
                .param("name", name)
                .doGet()
                .result(Boolean.class);
    }

    public boolean move(String newPath, String oldPath) throws IOException {
        return new HttpOperator()
                .uri(uri+"/move")
                .param("newPath", newPath)
                .param("oldPath", oldPath)
                .doGet()
                .result(Boolean.class);
    }

    public boolean addFolder(String path){
        return new HttpOperator()
                .uri(uri+"/addFolder")
                .param("path", path)
                .doGet()
                .result(Boolean.class);
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(String path, HttpServletResponse response) throws IOException {

    }

    @RequestMapping("/upload")
    public void upload(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) throws IOException {


    }

}
