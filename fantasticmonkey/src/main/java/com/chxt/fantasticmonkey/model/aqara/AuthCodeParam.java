package com.chxt.fantasticmonkey.model.aqara;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCodeParam{
    private String account;
    private Integer accountType;
    private String accessTokenValidity;

}
