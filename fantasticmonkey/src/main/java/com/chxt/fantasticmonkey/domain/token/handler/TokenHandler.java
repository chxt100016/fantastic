package com.chxt.fantasticmonkey.domain.token.handler;

import com.chxt.fantasticmonkey.model.token.TokenHandlerParam;
import com.chxt.fantasticmonkey.model.token.TokenItem;

public interface TokenHandler {
    TokenItem getToken(TokenHandlerParam param);

    TokenItem refreshToken(TokenHandlerParam param, TokenItem tokenItem);

}
