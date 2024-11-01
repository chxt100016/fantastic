package com.chxt.fantasticmonkey.service.crawl;

import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.util.crawler.Crawler;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
public class JapanLearn {
    public static void main(String[] args) {
        new JapanLearn().crawl();


    }

    @SneakyThrows
    public void crawl() {
        Crawler crawler = Crawler.getLocal(true);
        try {
            this.execute(crawler);
        } catch (Exception e) {
            crawler.close();
        }
    }

    @SneakyThrows
    public void execute(Crawler crawler) {
        crawler.go("https://mp.weixin.qq.com/s?__biz=MzI0NzQ2ODEwOQ==&mid=2247494988&idx=3&sn=929ffcdb5b91c8eabcadb0546013bd39&chksm=e9ad346ededabd788a4d5b9bfbb3d5729af892d2af68989e07aff52776707494819a3fa524ef&scene=21#wechat_redirect");
        TimeUnit.SECONDS.sleep(10);
        Crawler first = crawler.getById("js_content");
        List<Crawler> rows = first.listChildren();

        // 从第五课开始
        Iterator<Crawler> iterator = rows.iterator();
        while (iterator.hasNext()) {
            Crawler next = iterator.next();
            if (next.text().equals("第五课")) {
                break;
            }
            iterator.remove();
        }
        log.info("{}rows", rows.size());

        int session = 4;
        int unit = 0;
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 2, TimeUnit.MINUTES, new LinkedBlockingDeque<>(1000));
        for (int i = 0; i < rows.size(); i ++) {
            Crawler row = rows.get(i);
            String text = row.text();
            log.info("{} / {}, {}", i, rows.size(), text);
            if (StringUtils.isBlank(text)) continue;

            // 课和单元变换
            if (this.sessionChange(text)) {
                session ++;
                unit = 0;
                continue;
            }
            if (this.unitChange(text)) {
                unit ++;
                continue;
            }

            List<Crawler> aArr = row.listByTag("a");

            for (Crawler a : aArr) {
                Karin karin = new Karin();
                Map<String, String> data = new HashMap<>();
                karin.setData(data);
                data.put("url", a.href());
                data.put("outTitle", a.text());
                data.put("session", session+"");
                data.put("unit", unit+"");
                String result = new HttpOperator()
                        .uri("http://2202.com:1337/api/karins")
                        .entity(karin)
                        .jsonHeader()
                        .doPost().result();
                log.info(result);
            }
        }
    }

    public boolean sessionChange(String text) {
        String pattern = "第.*课";
        return Pattern.matches(pattern, text);
    }

    public boolean unitChange(String text) {
        String pattern = ".*ニット.*";
        return Pattern.matches(pattern, text);
    }





    @Data
    private static class Karin {
        private Map<String, String> data;
    }
}
