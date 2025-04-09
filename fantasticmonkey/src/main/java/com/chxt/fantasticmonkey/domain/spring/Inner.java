package com.chxt.fantasticmonkey.domain.spring;


import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantastic.common.torrent.TorrentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
public class Inner {

    private String prefix;

    private String html;



    public Info getDetail() {
        Document document = Jsoup.parse(this.html);
        Element main = document.select("#postlist .t_fsz").first();

        if (main == null) {
            throw new RuntimeException("main is null");
        }

        //images
        List<byte[]> images = new ArrayList<>();
        Elements imgs = main.select("ignore_js_op img");
        for (Element img : imgs) {
            String imageSrc = StringUtils.isNotBlank(img.attr("file")) ? img.attr("file") : img.attr("src");
            if (StringUtils.isBlank(imageSrc) || !imageSrc.startsWith("http")) {
                continue;
            }

            byte[] bytes = new HttpOperator().uri(imageSrc).doGet().byteArray();
            images.add(bytes);
        }

        // torrent
        List<String> torrentUrls = new ArrayList<>();
        List<String> magnets = new ArrayList<>();
        Elements as = main.select("ignore_js_op a");
        for (Element a : as) {
            String href = a.attr("href");
            String text = a.text();
            if (StringUtils.isBlank(href) || !text.endsWith(".torrent")) {
                continue;
            }

            String torrentUrl = prefix + href;
            torrentUrls.add(torrentUrl);
            byte[] bytes = new HttpOperator().uri(torrentUrl).doGet().byteArray();
            magnets.add(new TorrentInfo(bytes).getMagnetLink());
        }

        return Info.builder()
                .images(images)
                .torrentUrls(torrentUrls)
                .magnets(magnets)
                .build();

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Info {
        private List<byte[]> images;

        private List<String> torrentUrls;

        private List<String> magnets;
    }
}
