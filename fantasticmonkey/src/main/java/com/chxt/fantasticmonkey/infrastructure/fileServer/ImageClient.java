package com.chxt.fantasticmonkey.infrastructure.fileServer;

import com.chxt.fantastic.common.http.HttpOperator;
import org.springframework.stereotype.Component;

@Component
public class ImageClient {

    public void downloadMission(String domain, String namespace, String name, String url) {
        new HttpOperator().uri("http://2202.com:9999/image/mission")
                .jsonHeader()
                .entity("domain", domain)
                .entity("namespace", namespace)
                .entity("name", name)
                .entity("url", url)
                .doPost();
    }

    public byte[] getImageBinary(String domain, String namespace, String name) {
        return new HttpOperator().uri("http://2202.com:9999/image" + String.format("/%s/%s/%s", domain, namespace, name))
                .doGet()
                .byteArray();
    }


}
