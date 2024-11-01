package com.chxt.fantasticmonkey.infrastructure.alarm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chxt.fantasticmonkey.model.alarm.Alarm;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AlarmMapper extends BaseMapper<Alarm> {

    @Select("select * from alarm where alarm_time = (select max(alarm_time) from alarm )")
    List<Alarm> getLastAlarm();
}
