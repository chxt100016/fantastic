package com.chxt.fantasticmonkey.infrastructure.transmission;

import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.model.transmission.*;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class Transmission2Client {

    private final SessionIdProcessor processor = new SessionIdProcessor();

    private final String uri = "http://2202.com:3000/transmission/rpc";

    @SneakyThrows
    public TorrentAddResponse add(TorrentRequest2 entity) {
        return new HttpOperator()
                .uri(this.uri)
                .header("X-Transmission-Session-Id", this.processor.getTmp())
                .jsonHeader()
                .entity(entity)
                .doPost(this.processor)
                .result(TorrentAddResponse.class);
    }

    public List<Torrents> get() {
        TorrentRequest entity = TorrentRequest.builder()
                .method("torrent-get")
                .arguments(RequestArguments.builder().fields(Arrays.asList("id", "name", "percentDone")).build()).build();
        TorrentGetResponse result = new HttpOperator()
                .uri(this.uri)
                .header("X-Transmission-Session-Id", this.processor.getTmp())
                .jsonHeader()
                .entity(entity)
                .doPost(this.processor)
                .result(TorrentGetResponse.class);
        return result.getArguments().getTorrents();
    }

    public void remove(List<Integer> ids) {
        TorrentRequest entity = TorrentRequest.builder().method("torrent-remove").arguments(RequestArguments.builder().ids(ids).build()).build();
        new HttpOperator()
                .uri(this.uri)
                .header("X-Transmission-Session-Id", this.processor.getTmp())
                .jsonHeader()
                .entity(entity)
                .doPost(this.processor)
                .result(String.class);
    }

    public static class SessionIdProcessor implements HttpOperator.PostProcessor {

        private String tmp;

        @Override
        public CloseableHttpResponse process(CloseableHttpClient a, HttpRequestBase b, CloseableHttpResponse c) throws IOException {
            if (c.getStatusLine().getStatusCode() == 409) {
                Header lastHeader = c.getLastHeader("X-Transmission-Session-Id");
                this.tmp = lastHeader.getValue();
                b.setHeader(lastHeader);
                return a.execute(b);
            }
            return c;
        }

        public String getTmp(){
            return this.tmp;
        }
    }





}
