package com.chxt.fantasticmonkey.model.tennis;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chxt.fantasticmonkey.enums.TimetableEnum;
import com.chxt.fantasticmonkey.model.huanglong.BookInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.var;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TennisCourt {

    private Date date;
    private Boolean bookable;
    private Integer price;
    private String fieldName;

    private TimetableEnum timetableEnum;

    @SneakyThrows
    public static List<TennisCourt> getList(BookInfoResponse response, Date date, TimetableEnum timetableEnum) {
        String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd");
        List<TennisCourt> tennisCourts = new ArrayList<>();
        for (var item : response.getData().getBookingArray()) {
            for (var inner : item.getBookingInfos()) {
                if (inner.getStartDateHours() == 20) {
                    String aStr = dateStr + " 20:00";
                    String bStr = dateStr + " 21:00";
                    TennisCourt a = TennisCourt.builder()
                        .date(DateUtils.parseDate(aStr, "yyyy-MM-dd HH:mm"))
                        .bookable(inner.getState().getNo() == 0)
                        .price(inner.getPrice())
                        .timetableEnum(timetableEnum)
                        .fieldName(inner.getFieldName().replaceAll("网球", "").replaceAll("0", "").replaceAll("室内",""))
                        .build();
                    TennisCourt b = TennisCourt.builder()
                        .date(DateUtils.parseDate(bStr, "yyyy-MM-dd HH:mm"))
                        .bookable(inner.getState().getNo() == 0)
                        .price(inner.getPrice())
                        .timetableEnum(timetableEnum)
                        .fieldName(inner.getFieldName().replaceAll("网球", "").replaceAll("0", "").replaceAll("室内",""))
                        .build();
                    tennisCourts.addAll(Arrays.asList(a, b));
                } else {
                    String str = dateStr + " " + inner.getShowStartTime();
                    TennisCourt tennisCourt = TennisCourt.builder()
                        .date(DateUtils.parseDate(str, "yyyy-MM-dd HH:mm"))
                        .bookable(inner.getState().getNo() == 0)
                        .price(inner.getPrice())
                        .timetableEnum(timetableEnum)
                        .fieldName(inner.getFieldName().replaceAll("网球", "").replaceAll("0", "").replaceAll("室内",""))
                        .build();
                    tennisCourts.add(tennisCourt);
                }
            }
        }
        
        return tennisCourts;
    }


}
