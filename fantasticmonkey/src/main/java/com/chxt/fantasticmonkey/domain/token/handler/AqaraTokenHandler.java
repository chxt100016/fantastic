package com.chxt.fantasticmonkey.domain.token.handler;

import com.chxt.fantasticmonkey.model.aqara.RefreshTokenParam;
import com.chxt.fantasticmonkey.model.token.TokenHandlerParam;
import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.infrastructure.aqara.AqaraTokenClient;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

@Component
public class AqaraTokenHandler implements TokenHandler {

    @Resource
    private AqaraTokenClient client;

    @Override
    public TokenItem getToken(TokenHandlerParam param) {
        try {
            return TokenItem.builder()
                    .accessToken("ba06c8a357b09523236322b2bf3d9438")
                    .refreshToken("a7180b7d2b4cb2f414a4140811361228")
                    .expireTime(DateUtils.parseDate("2022-09-02 13:01:51", "yyyy-MM-dd HH:mm:ss").getTime())
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public TokenItem refreshToken(TokenHandlerParam param, TokenItem tokenItem) {
        this.client.refreshToken(RefreshTokenParam.builder().refreshToken(tokenItem.getRefreshToken()).build(), param);
        return null;
    }
}
