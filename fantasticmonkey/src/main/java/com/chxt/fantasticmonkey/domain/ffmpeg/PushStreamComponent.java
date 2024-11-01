//package com.chxt.fantasticmonkey.layer.c.domain.ffmpeg;
//
//import com.chxt.fantasticmonkey.bean.alarm.Alarm;
//import com.chxt.fantasticmonkey.layer.d.infrastructure.alarm.AlarmMapper;
//import com.chxt.fantasticmonkey.layer.d.infrastructure.fileServer.ImageClient;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Java2DFrameConverter;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;w
//
//@Slf4j
//@Component
//public class PushStreamComponent {
//
//    private List<BufferedImage> images;
//
//    @Resource
//    private AlarmMapper alarmMapper;
//
//    @Resource
//    private ImageClient imageClient;
//
//    private volatile boolean signal = true;
//
////    @PostConstruct
////    public void postConstruct() {
////        if (true) {
////            return;
////        }
////        new Thread(this::run).start();
////    }
//
//    public synchronized void updateImages(List<URL> urls) {
//        this.images = urls.stream().map(item -> {
//            try {
//                return ImageIO.read(item);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toList());
//    }
//
//    public synchronized void updateImagesByIs(List<InputStream> inputs) {
//        this.images = inputs.stream().map(item -> {
//            try {
//                return ImageIO.read(item);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toList());
//    }
//
//
//    public void run() {
//        try {
//            if (signal) {
//                signal = false;
//                List<Alarm> lastAlarm = this.alarmMapper.getLastAlarm();
//                List<InputStream> inputs = new ArrayList<>();
//                for (Alarm alarm : lastAlarm) {
//                    Date date = alarm.getAlarmTime();
//                    String namespace = "outdoor" + DateFormatUtils.format(date, "yyyy-MM");
//                    byte[] data = this.imageClient.getImageBinary("alarm", namespace, alarm.getName());
//                    ByteArrayInputStream in = new ByteArrayInputStream(data);
//                    inputs.add(in);
//                }
//                this.updateImagesByIs(inputs);
//                this.push();
//
//            }
//        } finally {
//            signal = true;
//        }
//
//    }
//
//
//    @SneakyThrows
//    private void push(){
//        FFmpegFrameRecorder recorder = null;
//        Java2DFrameConverter converter = null;
//        try {
//            recorder = new FFmpegFrameRecorder("rtmp://2202.com:1935/live/test", 1920, 1080);
//            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//            recorder.setFrameRate(30);
//            recorder.setGopSize(1);
//            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
//            recorder.setFormat("flv");
//            recorder.start();
//
//            converter = new Java2DFrameConverter();
//
//            int index = 0;
//
//            while (true) {
//                for(int i = 0; i < 30; i++) {
//                    recorder.record(converter.getFrame(images.get(index)));
//                }
//                TimeUnit.MILLISECONDS.sleep(600);
//                index = (index + 1) % images.size();
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            assert recorder != null;
//            recorder.close();
//            assert converter != null;
//            converter.close();
//        }
//    }
//}
