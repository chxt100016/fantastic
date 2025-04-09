package com.chxt.fantasticmonkey.model.timetable;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.enums.TimetableEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeTableCell {

    private Integer dayOfWeek;
    private String time;
    private String iconKey;
    private String iconBase64;

    public TimeTableCell(Date date, TimetableEnum timetableEnum) {
        this.iconBase64 = timetableEnum.getIconBase64();
        this.iconKey = timetableEnum.getCode();
        
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 将Calendar的星期天从1-7的范围转换为星期一到星期天的范围
        this.dayOfWeek = (dayOfWeek == Calendar.SUNDAY) ? 7 : dayOfWeek - 1;
        // 获取小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.time = String.format("%02d:00", hour);
        
        
    }

       
}
