package com.chxt.fantasticmonkey.controller.alarm;

import com.chxt.fantasticmonkey.service.alarm.TennisService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alarm/tennis")
public class TennisAlarmController {

    @Resource
    private TennisService tennisService;



    @RequestMapping("/pic/latest")
    @SneakyThrows
    public void latest(HttpServletResponse response) {
        byte[] image = this.tennisService.getImage();
        response.getOutputStream().write(image);
    }

    @RequestMapping("/query")
    public String query() {
        this.tennisService.BookInfoQuery();
        return "ok";
    }

    @RequestMapping("/sensor")
    public boolean sensor(){
        return this.tennisService.getAlarmSensor();
    }

}
