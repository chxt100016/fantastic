package com.chxt.fantasticmonkey.domain.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outer {

    private String prefix;

    private String html;

    public List<Info> getList() {
        Document document = Jsoup.parse(this.html);
        Elements rows = document.select("#threadlisttableid tbody[id^=normalthread] .xst");

        List<Info> res = new ArrayList<>();
        for (Element row : rows) {
            Info info = Info.builder()
                    .title(row.text())
                    .url(this.prefix + row.attr("href"))
                    .build();
            res.add(info);
        }

        return res;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Info {
        private String title;
        private String url;
    }

}
