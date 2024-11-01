package com.chxt.fantasticmonkey.service.alarm;


import com.chxt.fantasticmonkey.model.alarm.Alarm;
import com.chxt.fantasticmonkey.domain.ezviz.TakePhotoComponent;
import com.chxt.fantasticmonkey.domain.wechartWork.SendDoorAlarmComponent;
import com.chxt.fantasticmonkey.infrastructure.alarm.AlarmMapper;
import com.chxt.fantasticmonkey.infrastructure.fileServer.ImageClient;
import com.chxt.fantasticmonkey.infrastructure.hass.HassClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AlarmService {
    @Resource
    private AlarmMapper alarmMapper;

    @Resource
    private TakePhotoComponent takePhotoComponent;

    @Resource
    private SendDoorAlarmComponent sendDoorAlarmComponent;

    @Resource
    private ImageClient imageClient;

    @Resource
    private HassClient hassClient;

    public void outdoorAlarm(){
        log.info("门口有人出没");
        // 调用摄像头接口拍照
        List<String> pictureList = this.takePhotoComponent.execute("G69552993", 3, 1);

        // 将照片存储在file-server
        Date now = new Date();
        for (int i = 0; i < pictureList.size(); i++) {
            String namespace = "outdoor" + DateFormatUtils.format(now, "yyyy-MM");
            String name = DateFormatUtils.format(now, "yyyyMMddHHmmss") + String.format("%03d", i) + ".jpeg";
            this.imageClient.downloadMission("alarm", namespace, name, pictureList.get(i));
            this.alarmMapper.insert(Alarm.builder().name(name).deviceId("G69552993").alarmTime(now).build());
        }

        // 更新推流图片
//        List<URL> urls = pictureList.stream().map(item -> {
//            try {
//                return new URL(item);
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toList());
//        this.pushStreamComponent.updateImages(urls);

        // 发送告警消息
        this.sendDoorAlarmComponent.execute(pictureList, false);

        // 触发homekit通知
        this.hassClient.webhook("-mNs50672IlXBx4usxbQ14wvt");
    }


}
