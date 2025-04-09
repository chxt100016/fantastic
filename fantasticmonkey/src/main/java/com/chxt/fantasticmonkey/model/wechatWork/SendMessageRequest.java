package com.chxt.fantasticmonkey.model.wechatWork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest{
    private String touser;
    private String msgtype;
    private String agentid;
    private SendMessageNews news;
    private Text text;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Text {
        private String content;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendMessageNews {
        List<Map<String, String>> articles;
    }
}
