package com.chxt.fantasticmonkey.infrastructure.hass;

import com.chxt.fantastic.common.http.HttpOperator;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Component;

@Component
public class HassClient {


    public void webhook(String id) {
        new HttpOperator()
                .uri("http://2202.com:8123/api/webhook/" + id)
                .doPost();
    }
}
