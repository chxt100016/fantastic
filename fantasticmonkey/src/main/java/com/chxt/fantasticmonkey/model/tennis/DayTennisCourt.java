package com.chxt.fantasticmonkey.model.tennis;

import com.chxt.fantasticmonkey.util.DayFormatUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayTennisCourt {

    // 日期
    private String date;

    // 星期几
    private String day;

    // 分时文本展示数据
    private Map<String, String> dataMap;

    private Map<String, String> outdoorMap;

    private Map<String, String> indoorMap;



    public static List<DayTennisCourt> getList(List<TennisCourt> tennisCourts) {
        Map<String, List<TennisCourt>> groupByDate = tennisCourts.stream().collect(Collectors.groupingBy(TennisCourt::getDateStr));

        List<DayTennisCourt> res = new ArrayList<>();
        for (Map.Entry<String, List<TennisCourt>> dateEntry : groupByDate.entrySet()) {
            String date = dateEntry.getKey();
            List<TennisCourt> sameDayTennisCourts = dateEntry.getValue();
            Map<Integer, List<TennisCourt>> groupByTime = sameDayTennisCourts.stream().collect(Collectors.groupingBy(TennisCourt::getStartTime));
            Map<String, String> dayDataMap = new HashMap<>();
            for (Map.Entry<Integer, List<TennisCourt>> timeEntry : groupByTime.entrySet()) {
                Integer time = timeEntry.getKey();
                List<TennisCourt> sameTimeTennisCourt = timeEntry.getValue();
                Map<String, Optional<TennisCourt>> groupByPlace = sameTimeTennisCourt.stream().collect(Collectors.groupingBy(TennisCourt::getPlace, Collectors.reducing((a, b) -> {
                    a.setFieldName(a.getFieldName() + "," + b.getFieldName());
                    return a;
                })));
                List<String> rows = groupByPlace.values().stream().map(item -> item.orElse(null)).filter(Objects::nonNull).map(item -> String.format("(%s)%s", item.getPlace(), item.getFieldName())).collect(Collectors.toList());
                dayDataMap.put(String.valueOf(time), StringUtils.join(rows, ","));
            }

            Map<String, List<TennisCourt>> groupByPlace = sameDayTennisCourts.stream().collect(Collectors.groupingBy(TennisCourt::getPlace));
            Map<String, Map<String, String>> separateMap = new HashMap<>();
            for (Map.Entry<String, List<TennisCourt>> entry : groupByPlace.entrySet()) {
                String place = entry.getKey();
                List<TennisCourt> samePlaceCourts = entry.getValue();
                Map<Integer, Optional<TennisCourt>> samePlaceAndTime = samePlaceCourts.stream().collect(Collectors.groupingBy(TennisCourt::getStartTime, Collectors.reducing((a, b) -> {
                    a.setFieldName(a.getFieldName() + "," + b.getFieldName());
                    return a;
                })));
                Map<String, String> timeMap = new HashMap<>();
                for (Map.Entry<Integer, Optional<TennisCourt>> tmp : samePlaceAndTime.entrySet()) {
                    timeMap.put(tmp.getKey() + "", tmp.getValue().orElse(new TennisCourt()).getFieldName());
                }
                separateMap.put(place, timeMap);
            }


            DayTennisCourt dayTennisCourt = new DayTennisCourt(date, DayFormatUtils.getDayOfWeekEn(date), dayDataMap, separateMap.get("outdoor"), separateMap.get("indoor"));
            res.add(dayTennisCourt);

        }

        res = res.stream().sorted(Comparator.comparing(DayTennisCourt::getDate)).collect(Collectors.toList());
        return res;
    }
}
