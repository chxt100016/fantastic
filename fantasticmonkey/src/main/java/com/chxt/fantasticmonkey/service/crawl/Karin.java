package com.chxt.fantasticmonkey.service.crawl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Karin {
    private Integer id;
    private String name;
    private String url;
    private String title;
    private String outTitle;
    private String mediaType;
    private String mediaUrl;
    private String content;
    private String session;
    private String unit;
}
