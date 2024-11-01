package com.chxt.fantasticmonkey.service.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chxt.fantastic.common.http.HttpOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
public class Download {
    public static void main(String[] args) {
        JSONObject result = new HttpOperator().uri("http://2202.com:1337/api/karins?pagination[pageSize]=1000&filters[mediaType][$notNull]=true").doGet().result(JSONObject.class);
        JSONArray data = result.getJSONArray("data");
        List<Karin> list = new ArrayList<>();
        for (Object item : data) {
            JSONObject jsonObject = JSON.parseObject(item.toString());
            Karin karin = JSON.parseObject(jsonObject.get("attributes").toString(), Karin.class);
            karin.setId(Integer.parseInt(jsonObject.get("id").toString()));
            list.add(karin);
        }

        AtomicInteger i = new AtomicInteger();
        for (Karin karin : list) {
            String name = karin.getName();
            String suffix = karin.getMediaType().equals("音频") ? "mp3" : "mp4";

            log.info("name: {}, {}, item: {}", name, i, JSON.toJSONString(karin));
            try {
                new HttpOperator()
                        .uri(karin.getMediaUrl())
                        .doGet()
                        .download(String.format("/Users/chenxintong/Downloads/a/%s.%s", name, suffix));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
