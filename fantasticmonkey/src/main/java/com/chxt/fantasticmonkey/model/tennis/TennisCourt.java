package com.chxt.fantasticmonkey.model.tennis;


import com.chxt.fantasticmonkey.model.huanglong.BookInfoResponse;
import com.chxt.fantasticmonkey.util.DayFormatUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TennisCourt {

    private String dateStr;
    private Date date;

    // 1 周日  2 周一 7 周日
    private Integer day;
    private Integer startTime;
    private Integer endTime;
    private Boolean bookable;
    private Integer price;
    private String place;
    private String fieldName;

    public static List<TennisCourt> getList(Date date, BookInfoResponse response, String place) {
        String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd");
        int day = DayFormatUtils.getDay(date);
        return response.getData().getBookingArray()
                .stream()
                .flatMap(
                        item -> item.getBookingInfos()
                                .stream()
                                .map(inner -> TennisCourt.builder()
                                        .dateStr(dateStr)
                                        .date(date)
                                        .day(day)
                                        .bookable(inner.getState().getNo() == 0)
                                        .startTime(inner.getStartDateHours())
                                        .endTime(inner.getEndDateHours())
                                        .price(inner.getPrice())
                                        .place(place)
                                        .fieldName(inner.getFieldName().replaceAll("网球", "").replaceAll("0", "").replaceAll("室内",""))
                                        .build()
                                )
                ).collect(Collectors.toList());
    }


}
