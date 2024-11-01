package com.chxt.fantasticmonkey.model.alarm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("alarm")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alarm {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String deviceId;

    private Date alarmTime;

}
