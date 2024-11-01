package com.chxt.fantasticmonkey.model.wechatWork;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatWorkToken {
    private String errcode;
    private String errmsg;
    private String accessToken;
    private Long expiresIn;
}
