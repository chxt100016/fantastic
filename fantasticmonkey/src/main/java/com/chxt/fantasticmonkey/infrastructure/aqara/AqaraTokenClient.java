package com.chxt.fantasticmonkey.infrastructure.aqara;


import com.alibaba.fastjson.JSONObject;
import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.model.aqara.*;
import com.chxt.fantasticmonkey.model.token.TokenHandlerParam;
import com.chxt.fantasticmonkey.util.AqaraUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AqaraTokenClient {

    @Value("${uri.aqara}")
    private String uri;

    public AuthCodeResult getAuthCode(AuthCodeParam param, TokenHandlerParam tokenHandlerParam) {
        return this.handler("config.auth.getAuthCode", param, tokenHandlerParam, AuthCodeResult.class);

    }

    public TokenResponse getToken(GetTokenParam param, TokenHandlerParam tokenHandlerParam){
        return this.handler("config.auth.getToken", param, tokenHandlerParam, TokenResponse.class);
    }

    public TokenResponse refreshToken(RefreshTokenParam param, TokenHandlerParam tokenHandlerParam){
        return this.handler("config.auth.refreshToken", param, tokenHandlerParam, TokenResponse.class);
    }


    private <T> T handler(String intent, Object param, TokenHandlerParam tokenHandlerParam, Class<T> clazz){
        return this.handler(intent, param, tokenHandlerParam, null, clazz);
    }

    private <T> T handler(String intent, Object param, TokenHandlerParam tokenHandlerParam, String token, Class<T> clazz){
        AqaraParam entity = AqaraParam.builder().intent(intent).data(param).build();
        Map<String, String> header = AqaraUtil.getHeader(token, tokenHandlerParam.getAppId(), tokenHandlerParam.getKeyId(), tokenHandlerParam.getAppKey());
        AqaraResponse response = new HttpOperator()
                .uri(this.uri)
                .header(header)
                .jsonHeader()
                .entity(entity)
                .doPost()
                .result(AqaraResponse.class);

        return JSONObject.parseObject(response.getResult(), clazz);
    }




}
