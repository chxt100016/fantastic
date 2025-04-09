package com.chxt.fantasticmonkey.model.jellyfin;

import lombok.Data;

@Data
public class JellyfinItem {

    private String id;
    private String name;
    private String serverId;
    private Boolean isFolder;

}
