package com.chxt.fantasticmonkey.model.alarm;

import java.util.List;

public class AqaraCallBackParam {
    private List<Data> data;
    private String msgId;
    private String msgType;
    private String openId;
    private String time;
    private String token;

    public static class Data {
        private String resourceId;
        private Integer statusCode;
        private String subjectId;
        private String time;
        private TriggerSource triggerSource;
        private String value;
    }

    public static class TriggerSource {
        private String time;
        private Integer type;
    }
}
