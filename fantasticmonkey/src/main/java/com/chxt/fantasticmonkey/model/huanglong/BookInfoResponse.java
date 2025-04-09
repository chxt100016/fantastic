package com.chxt.fantasticmonkey.model.huanglong;

import lombok.Data;

import java.util.List;

@Data
public class BookInfoResponse {
    private Integer code;
    private BookInfoData data;

    @Data
    public static class BookInfoData {
        private List<BookRow> bookingArray;
        private String timeSlot;
        private String fieldSlot;
        private String showLine;
    }

    @Data
    public static class BookRow {
        private List<BookingInfo> bookingInfos;
        private String _time;
    }

    @Data
    public static class BookingInfo {
        private String fieldName;
        private String total;
        private String unit;
        private Integer price;
        private String startDate;
        private Integer startDateHours;
        private String startDateMins;
        private String endDate;
        private Integer endDateHours;
        private String endDateMins;
        private String colspan;
        private String rowspan;
        private boolean isRebuild;
        private State state;
        private String orderInfo;
        private String showStartTime;
        private String showEndTime;
        private String showTime;
        private boolean isShowCancel;
    }

    @Data
    public static class State{
        private Integer no;
        private String state;
    }
}
