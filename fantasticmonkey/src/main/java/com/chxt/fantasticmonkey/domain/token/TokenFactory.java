package com.chxt.fantasticmonkey.domain.token;

import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.domain.token.tokenStore.InnerTokenStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory implements ApplicationContextAware {
    public static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static TokenItem innerStore(TokenEnum tokenEnum) {
        return context.getBean(InnerTokenStore.class).getToken(tokenEnum);
    }


}
