package com.chxt.fantasticmonkey.model.transmission;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestArguments {
    private String metainfo;
    private String filename;
    private List<String> fields;
    private List<Integer> ids;

}
