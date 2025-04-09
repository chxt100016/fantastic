package com.chxt.fantasticmonkey.model.aqara;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenParam{

    private String refreshToken;

}
