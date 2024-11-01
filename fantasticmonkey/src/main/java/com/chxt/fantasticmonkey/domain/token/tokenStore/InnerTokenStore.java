package com.chxt.fantasticmonkey.domain.token.tokenStore;

import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.domain.token.TokenEnum;
import com.chxt.fantasticmonkey.domain.token.handler.TokenHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableScheduling
public class InnerTokenStore implements TokenStore {

    private final ConcurrentHashMap<String, TokenItem> tokenStore = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, TokenHandler> handlerMap = new ConcurrentHashMap<>();


    public InnerTokenStore(List<TokenHandler> list) {
        for (TokenEnum value : TokenEnum.values()) {
            TokenHandler tokenHandler = list.stream().filter(item -> item.getClass() == value.getHandler()).findFirst().orElseThrow(()->new RuntimeException("未找到对应token处理器"));
            handlerMap.put(value.name(), tokenHandler);

        }
    }

    public TokenItem getToken(TokenEnum tokenEnum){
        TokenItem tokenItem = this.tokenStore.get(tokenEnum.name());
        if (tokenItem == null) {
            tokenItem = this.handlerMap.get(tokenEnum.name()).getToken(tokenEnum.getParam());
            this.tokenStore.put(tokenEnum.name(), tokenItem);
        } else if (new Date().getTime() > tokenItem.getExpireTime()) {
            tokenItem = this.handlerMap.get(tokenEnum.name()).refreshToken(tokenEnum.getParam(), tokenItem);
            this.tokenStore.put(tokenEnum.name(), tokenItem);
        }
        tokenItem.setTokenEnum(tokenEnum);
        return tokenItem;
    }



}
