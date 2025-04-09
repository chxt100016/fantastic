package com.chxt.fantasticmonkey.infrastructure.tennis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chxt.fantasticmonkey.model.tennis.TennisCourt;

@Component
public class TennisTmpRepository {

    public volatile Map<String, TennisCourt> mapTmp = new HashMap<>();

    private volatile boolean alarmSensor = false;

    private byte[] image = new byte[0];

    private volatile List<TennisCourt> lastDiff = new ArrayList<>();

    public synchronized void updateLastDiff(List<TennisCourt> list) {
        this.lastDiff = list;
        this.alarmSensor = true;
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
