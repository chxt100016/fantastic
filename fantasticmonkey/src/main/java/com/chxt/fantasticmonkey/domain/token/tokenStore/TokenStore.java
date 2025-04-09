package com.chxt.fantasticmonkey.domain.token.tokenStore;

import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.domain.token.TokenEnum;

public interface TokenStore {


    TokenItem getToken(TokenEnum tokenEnum);
}
