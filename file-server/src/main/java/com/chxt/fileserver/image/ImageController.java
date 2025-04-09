package com.chxt.fileserver.image;


import com.chxt.fantastic.common.http.HttpOperator;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Value("${image.path}")
    private String imagePath;

    @SneakyThrows
    @PostMapping("/mission")
    public MissionResponse mission(@RequestBody MissionRequest dto) {
        if (dto.getDomain() == null) dto.setDomain("default");
        if (dto.getNamespace() == null) dto.setNamespace("default");
        if (dto.getUrl() == null ) return null;
        byte[] bytes = new HttpOperator().uri(dto.getUrl()).doGet().byteArray();
        if (dto.getName() == null) {
            dto.setName(dto.getUrl().substring(dto.getUrl().lastIndexOf("/") + 1));
        }
        File file = new File(this.getPath(dto.getDomain(), dto.getNamespace(), dto.getName()));
        FileUtils.writeByteArrayToFile(file, bytes);
        return MissionResponse.builder().name(dto.getName()).build();
    }

    @SneakyThrows
    @GetMapping("/{domain}/{namespace}/{name}")
    public void get(@PathVariable("domain") String domain,
                    @PathVariable("namespace") String namespace,
                    @PathVariable("name")String name,
                    HttpServletResponse response) {
        File file = new File(this.getPath(domain, namespace, name));
        byte[] bytes = FileUtils.readFileToByteArray(file);

        response.setContentType("image/jpg");
        response.getOutputStream().write(bytes);
    }

    @SneakyThrows
    @PutMapping("/{domain}/{namespace}/{name}")
    public void create(@PathVariable("domain") String domain,
                    @PathVariable("namespace") String namespace,
                    @PathVariable("name")String name,
                    MultipartFile file) {
        File dest = new File(this.getPath(domain, namespace, name));
        file.transferTo(dest);
    }

    private String getPath(String domain, String namespace, String name) {
        return this.imagePath + "/" + domain + "/" + namespace + "/" + name;
    }

}
