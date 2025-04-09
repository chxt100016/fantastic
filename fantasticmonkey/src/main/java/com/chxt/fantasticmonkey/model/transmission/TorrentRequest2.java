package com.chxt.fantasticmonkey.model.transmission;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TorrentRequest2 {
    private Map<String, String> arguments;
    private String method;


}
