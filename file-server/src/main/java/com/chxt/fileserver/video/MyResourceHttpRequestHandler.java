package com.chxt.fileserver.video;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class MyResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        String path = request.getAttribute("path") + "";
        return new FileSystemResource(path);
    }
}
