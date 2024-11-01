package com.chxt.fantasticmonkey.controller.spring;

import com.chxt.fantasticmonkey.service.spring.SpringService;
import com.chxt.fantasticmonkey.infrastructure.spring.SpringDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/spring")
@RestController
public class SpringController {

    @Resource
    private SpringService springService;

    @RequestMapping("/boot")
    public void boot() {
        this.springService.boot();
    }

    @RequestMapping("/list")
    public List<SpringDO> list(String status, String type) {
        return this.springService.list(status, type);
    }

    @GetMapping("/pic/{id}/{index}")
    public void getPic(@PathVariable("id") String id, @PathVariable("index") Integer index, HttpServletResponse response) throws IOException {
        byte[] pic = this.springService.getPic(id, index);
        response.setContentType("image/jpeg");
        response.getOutputStream().write(pic);
    }

    @RequestMapping("/check/{id}")
    public void check(@PathVariable("id") String id) {
        this.springService.check(id);
    }

    @RequestMapping("/close/{id}")
    public void close(@PathVariable("id") String id) {
        this.springService.close(id);
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        this.springService.delete(id);
    }

    @RequestMapping("/star/{id}")
    public void star(@PathVariable("id") String id) {
        this.springService.star(id);
    }



}
