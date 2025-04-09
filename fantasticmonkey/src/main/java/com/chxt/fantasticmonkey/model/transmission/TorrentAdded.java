package com.chxt.fantasticmonkey.model.transmission;

import lombok.Data;

@Data
public class TorrentAdded{
    private String hashString;
    private Integer id;
    private String name;

}