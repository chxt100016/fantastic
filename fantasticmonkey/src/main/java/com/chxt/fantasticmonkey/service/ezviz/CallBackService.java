//package com.chxt.fantasticmonkey.service.ezviz;
//
//import com.alibaba.fastjson.JSON;
//import com.chxt.fantasticmonkey.bean.alarm.Alarm;
//import com.chxt.fantasticmonkey.bean.ezviz.AlarmRequest;
//import com.chxt.fantasticmonkey.bean.wechatWork.EzvizProperty;
//import com.chxt.fantasticmonkey.component.ezviz.TakePhotoComponent;
//import com.chxt.fantasticmonkey.component.wechartWork.SendDoorAlarmComponent;
//import com.chxt.fantasticmonkey.dao.alarm.AlarmMapper;
//import com.chxt.fantasticmonkey.dependency.ezviz.EzvizClient;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class CallBackService {
//
//    @Resource
//    private TakePhotoComponent takePhotoComponent;
//
//    @Resource
//    private SendDoorAlarmComponent sendDoorAlarmComponent;
//
//    @Resource
//    private EzvizProperty ezvizProperty;
//
//    @Resource
//    private AlarmMapper alarmMapper;
//
//    @Value("${ezviz.alarm_img_path}")
//    private String imgPath;
//
//    @Resource
//    private EzvizClient ezvizClient;
//
//
//    @SneakyThrows
//    public void alarm(AlarmRequest request) {
//        log.info("收到萤石告警消息, {}", JSON.toJSONString(request));
//
//        // 保存告警信息
//        this.save(request);
//
//        if (!ezvizProperty.getInclude().contains(request.getHeader().getDeviceId())) {
//            return;
//        }
//
//        List<String> pictureList = request.getBody().getPictureList().stream().map(AlarmRequest.Picture::getUrl).collect(Collectors.toList());
//
//        // 抓照片
//        pictureList.addAll(this.takePhotoComponent.execute(request.getBody().getDevSerial(), ezvizProperty.getTimes(), ezvizProperty.getInterval()));
//
//        // 添加推送应用消息
//        this.sendDoorAlarmComponent.execute(pictureList, true);
//    }
//
//    private void save(AlarmRequest request){
//        AlarmRequest.Body body = request.getBody();
//        String url = body.getPictureList().get(0).getUrl();
//        String path = this.imgPath + "/" + body.getDevSerial() + "/" + body.getAlarmTime();
//        Date alarmTime = new Date();
//        this.ezvizClient.downloadImg(url, path);
//        this.alarmMapper.insert(Alarm.builder().outId(body.getAlarmId()).imgPath(path).deviceId(body.getDevSerial()).alarmTime(alarmTime).build());
//    }
//
//    public static void main(String[] args) {
//        int[] dist = new int[]{100000};
//        double hour = 0.1;
//
//        int max = (int)(Math.ceil(Math.max(Arrays.stream(dist).max().getAsInt(), dist[dist.length-1] / (hour%1))));
//        System.out.println(max);
//    }
//
//}
