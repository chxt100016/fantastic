package com.chxt.fantasticmonkey.infrastructure.openDota;


import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.model.openDota.OpenDotaWinLose;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlayerClient {
    @Value("${open_dota.token}")
    public String token;

    @Value("${open_dota.url}")
    public String url;

    public OpenDotaWinLose getWL(String accountId, String date) {
        return new HttpOperator()
                .uri(this.url + String.format("/players/%s/wl", accountId))
                .param("api_key", token)
                .param("date", date)
                .message("open dota WL数据")
                .doGet()
                .result(OpenDotaWinLose.class);
    }

}
