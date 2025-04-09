package com.chxt.fantasticmonkey.service.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chxt.fantastic.common.http.HttpOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SetName {
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
            new Thread(()-> {
                System.out.println(i.incrementAndGet());
                String title = karin.getTitle();
                title = title.replace("初级日语学习-", "");
                title = title.replace("初级日语-", "");
                title = title.replace("综合日语1-", "");
                String name = String.format("%s-%s-%s", karin.getSession(), karin.getUnit(), title);
//            String suffix = karin.getMediaType().equals("音频") ? "mp3" : "mp4";

//            new HttpOperator()
//                    .uri(karin.getMediaUrl())
//                    .doGet()
//                    .download("/Users/chenxintong/Downloads/a/{}.{}");
                HashMap<String, Karin> map = new HashMap<>();
                Karin req = Karin.builder()
                        .name(name)
                        .build();
                map.put("data", req);

                new HttpOperator()
                        .uri("http://2202.com:1337/api/karins/" + karin.getId())
                        .entity(map)
                        .jsonHeader()
                        .doPut()
                        .result();
            }).start();
        }



    }
}
