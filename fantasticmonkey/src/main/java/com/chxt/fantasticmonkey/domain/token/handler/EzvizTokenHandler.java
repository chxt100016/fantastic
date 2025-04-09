package com.chxt.fantasticmonkey.domain.token.handler;

import com.chxt.fantasticmonkey.model.token.TokenHandlerParam;
import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.infrastructure.ezviz.EzvizClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EzvizTokenHandler implements TokenHandler{

    @Resource
    private EzvizClient ezvizClient;

    @Override
    public TokenItem getToken(TokenHandlerParam param) {
        return this.ezvizClient.getToken(param);
    }

    @Override
    public TokenItem refreshToken(TokenHandlerParam param, TokenItem tokenItem) {
        return this.ezvizClient.getToken(param);
    }
}
