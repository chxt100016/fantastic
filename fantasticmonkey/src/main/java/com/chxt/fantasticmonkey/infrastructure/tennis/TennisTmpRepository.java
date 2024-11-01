package com.chxt.fantasticmonkey.infrastructure.tennis;

import com.chxt.fantasticmonkey.model.tennis.TennisCourt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TennisTmpRepository {

    public volatile Map<String, TennisCourt> mapTmp = new HashMap<>();

    private volatile boolean alarmSensor = false;

    private byte[] image = new byte[0];

    private volatile List<TennisCourt> lastDiff = new ArrayList<>();

    public synchronized boolean checkHasIncrement(List<TennisCourt> list) {
        boolean res = false;
        List<TennisCourt> diff = new ArrayList<>();
        Map<String, TennisCourt> map = list.stream().collect(Collectors.toMap(item -> item.getDateStr() + item.getPlace() + item.getStartTime() + item.getFieldName(), Function.identity()));
        for (String s : map.keySet()) {
            if(this.mapTmp.get(s) == null) {
                this.alarmSensor = true;
                res = true;
                diff.add(map.get(s));
            }
        }

        this.lastDiff = diff;
        this.mapTmp = map;

        return res;
    }

    public synchronized List<TennisCourt> getLastDiff() {
        return this.lastDiff;
    }

    public synchronized void setImage(byte[] image) {
        this.image = image;
    }

    public synchronized byte[] getImage() {
        return this.image;
    }

    public synchronized boolean getAlarmSensor() {
        if (this.alarmSensor) {
            this.alarmSensor = false;
            return true;
        } else {
            return false;
        }
    }
}
