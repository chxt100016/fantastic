package com.chxt.fantasticmonkey.domain.token.handler;

import com.chxt.fantasticmonkey.model.token.TokenHandlerParam;
import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.infrastructure.wechatWork.WechatWorkClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WechatWorkTokenHandler implements TokenHandler{

    @Resource
    private WechatWorkClient wechatWorkClient;

    @Override
    public TokenItem getToken(TokenHandlerParam param) {
        return this.wechatWorkClient.getToken(param);
    }

    @Override
    public TokenItem refreshToken(TokenHandlerParam param, TokenItem tokenItem) {
        return this.wechatWorkClient.getToken(param);
    }
}
