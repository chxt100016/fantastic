package com.chxt.fileserver.video;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    private MyResourceHttpRequestHandler requestHandler;

    @Resource
    public VideoService videoService;

    @RequestMapping("/play/old")
    public void play(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.setAttribute("path", path);
        requestHandler.handleRequest(request, response);
    }

    @RequestMapping("/search")
    public List<String> search (@RequestBody VideoRequest request) {
        return this.videoService.search(request.getName());
    }

    
}
