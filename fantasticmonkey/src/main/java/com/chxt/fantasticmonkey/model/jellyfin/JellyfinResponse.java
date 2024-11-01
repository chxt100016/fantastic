package com.chxt.fantasticmonkey.model.jellyfin;

import lombok.Data;

import java.util.List;

@Data
public class JellyfinResponse {
    private List<JellyfinItem> items;
    private String totalRecordCount;
    private String startIndex;

}
