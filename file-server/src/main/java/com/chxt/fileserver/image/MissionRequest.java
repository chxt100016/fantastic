package com.chxt.fileserver.image;

import lombok.Data;

@Data
public class MissionRequest {
    private String domain;
    private String namespace;
    private String name;
    private String url;

}
