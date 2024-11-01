package com.chxt.fantasticmonkey.model.aqara;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTokenParam{

    private String authCode;
    private String account;
    private Integer accountType;
}
