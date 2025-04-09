package com.chxt.fantasticmonkey.service.alarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chxt.fantasticmonkey.domain.tennis.TennisCourtSelector;
import com.chxt.fantasticmonkey.domain.timetable.TimeTableGenerator;
import com.chxt.fantasticmonkey.infrastructure.env.EnvRepository;
import com.chxt.fantasticmonkey.infrastructure.huanglong.HuangLongClient;
import com.chxt.fantasticmonkey.infrastructure.tennis.TennisTmpRepository;
import com.chxt.fantasticmonkey.model.property.TennisProperty;
import com.chxt.fantasticmonkey.model.tennis.TennisCourt;

import com.chxt.fantasticmonkey.model.timetable.TimeTableCell;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TennisService {



    @Resource
    private HuangLongClient huangLongClient;

    @Resource
    private EnvRepository envRepository;

    @Resource
    private TennisTmpRepository tmpRepository;

    @Value("${alarm.tennis.enabled}")
    public boolean enabled;


    public void BookInfoQueryJob() {
        if (!this.enabled) return;
        this.BookInfoQuery();
    }

    public void BookInfoQuery() {
        TennisProperty property = this.envRepository.getTennisReport();

        // 获取网球场信息
        List<TennisCourt> tennisCourts = this.huangLongClient.getOutdoorAndIndoor(property.getDayRange());
        // 过滤需要的场地
        TennisCourtSelector selector = new TennisCourtSelector(property);
        List<TennisCourt> targetCourt = selector.getTargetCourt(tennisCourts, this.tmpRepository.getLastDiff());
        if (CollectionUtils.isEmpty(targetCourt)) {
            return;
        }

        this.tmpRepository.updateLastDiff(targetCourt);

        List<TimeTableCell> timeTables = targetCourt.stream()
                .map(item -> new TimeTableCell(item.getDate(), item.getTimetableEnum()))
                .collect(Collectors.toList());
    


        
    
        TimeTableGenerator generator = new TimeTableGenerator();
        byte[] image = generator.renderTimetable(timeTables);
        this.tmpRepository.setImage(image);
    }

    public byte[] getImage() {
        return this.tmpRepository.getImage();
    }

    public boolean getAlarmSensor() {
        return this.tmpRepository.getAlarmSensor();
    }




}
