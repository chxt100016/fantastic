package com.chxt.fantasticmonkey.model.aqara;

import lombok.Data;

@Data
public class TokenResponse {
    private String expiresIn;
    private String openId;
    private String accessToken;
    private String refreshToken;
}
