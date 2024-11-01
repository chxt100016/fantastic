//package com.chxt.fantasticmonkey.service.crawl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.chxt.fantastic.common.http.HttpOperator;
//import com.chxt.fantasticmonkey.util.crawler.Crawler;
//import com.chxt.fantasticmonkey.util.crawler.CrawlerFactory;
//import lombok.Data;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Slf4j
//public class GetMediaInfo {
//
//    public static void main(String[] args) throws InterruptedException {
//        JSONObject result = new HttpOperator().uri("http://2202.com:1337/api/karins?pagination[pageSize]=1000&filters[mediaType][$null]=true").doGet().result(JSONObject.class);
//        JSONArray data = result.getJSONArray("data");
//        List<Karin> list = new ArrayList<>();
//        for (Object item : data) {
//            JSONObject jsonObject = JSON.parseObject(item.toString());
//            Karin karin = JSON.parseObject(jsonObject.get("attributes").toString(), Karin.class);
//            karin.setId(Integer.parseInt(jsonObject.get("id").toString()));
//            list.add(karin);
//        }
//        AtomicInteger count = new AtomicInteger();
//        GenericObjectPool<Crawler> pool = getCrawlerPool();
//        for (Karin karin : list) {
//            new Thread(() -> {
//                Crawler crawler;
//                try {
//                    crawler = pool.borrowObject();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    log.info(JSON.toJSONString(karin));
//                    MediaDetail media = getMedia(karin.getUrl(), crawler);
//                    if (media.getType() != null) count.incrementAndGet();
//                    HashMap<String, Karin> map = new HashMap<>();
//                    Karin req = Karin.builder()
//                            .mediaUrl(media.getUrl())
//                            .mediaType(media.getType())
//                            .title(media.title)
//                            .build();
//                    map.put("data", req);
//                    new HttpOperator()
//                            .uri("http://2202.com:1337/api/karins/" + karin.getId())
//                            .entity(map)
//                            .jsonHeader()
//                            .doPut()
//                            .result();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    pool.returnObject(crawler);
//                }
//            }).start();
//        }
//        log.info("{}个搜索到媒体信息， 共{}个", count, list.size());
//        pool.close();
//    }
//
//    @SneakyThrows
//    public static MediaDetail getMedia(String url, Crawler crawler){
//        try {
//            crawler.go(url);
//            TimeUnit.SECONDS.sleep(5);
//            MediaDetail mediaDetail = new MediaDetail();
//            mediaDetail.setTitle(crawler.title());
//
//            Crawler iframe = crawler.getByTag("iframe");
//            if (iframe != null) {
//                mediaDetail.setType("视频");
//                iframe.click();
//                Crawler inner = iframe.switchToFrame(0);
//                Crawler video = inner.getByTag("video");
//                mediaDetail.setUrl(video.src());
//                return mediaDetail;
//            }
//
//            Crawler btn = crawler.getById("audio_play_btn");
//            if (btn != null) {
//                mediaDetail.setType("音频");
//                btn.click();
//                Crawler audio = crawler.getByTag("audio");
//                mediaDetail.setUrl(audio.src());
//                return mediaDetail;
//            }
//            return mediaDetail;
//        } catch (Exception e) {
//            log.error("url: {}, crawler: {}", url, crawler, e);
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    private static GenericObjectPool<Crawler> getCrawlerPool() {
//        GenericObjectPoolConfig<Crawler> poolConfig = new GenericObjectPoolConfig<>();
//        poolConfig.setMaxIdle(5);
//        poolConfig.setMaxTotal(5);
//        poolConfig.setMinIdle(5);
//        poolConfig.setBlockWhenExhausted(true);
//        poolConfig.setTimeBetweenEvictionRunsMillis(1000 * 60 * 30);
//        poolConfig.setJmxEnabled(false);
//
//        //新建一个对象池,传入对象工厂和配置
//        return new GenericObjectPool<>(new CrawlerFactory(), poolConfig);
//
//    }
//
//    @Data
//    private static class MediaDetail {
//        private String title;
//        private String url;
//        private String type;
//    }
//
//}
//
