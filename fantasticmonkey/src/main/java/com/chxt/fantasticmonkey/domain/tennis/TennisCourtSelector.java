package com.chxt.fantasticmonkey.domain.tennis;

import com.chxt.fantasticmonkey.model.property.TennisProperty;
import com.chxt.fantasticmonkey.model.tennis.TennisCourt;

import java.util.ArrayList;
import java.util.List;

public class TennisCourtSelector {

    private final List<TennisCourt> tennisCourts;

    private final TennisProperty property;

    public TennisCourtSelector (List<TennisCourt> tennisCourts, TennisProperty property) {
        this.tennisCourts = tennisCourts;
        this.property = property;
    }


    public List<TennisCourt> getTargetCourt() {
        List<TennisCourt> res = new ArrayList<>();
        for (TennisCourt tennisCourt : tennisCourts) {
            if (!tennisCourt.getBookable()) {
                continue;
            }

            if (tennisCourt.getDay() == 1 || tennisCourt.getDay() == 7) {
                if ("indoor".equals(tennisCourt.getPlace()) && this.property.getIntDoorWeekendHours().contains(tennisCourt.getStartTime())) {
                    res.add(tennisCourt);
                }
                if ("outdoor".equals(tennisCourt.getPlace()) && this.property.getOutDoorWeekendHours().contains(tennisCourt.getStartTime())) {
                    res.add(tennisCourt);
                }
            } else {
                if ("indoor".equals(tennisCourt.getPlace()) && this.property.getInDoorWeekdayHours().contains(tennisCourt.getStartTime())) {
                    res.add(tennisCourt);
                }
                if ("outdoor".equals(tennisCourt.getPlace()) && this.property.getOutDoorWeekdayHours().contains(tennisCourt.getStartTime())) {
                    res.add(tennisCourt);
                }
            }
        }

        return res;
    }
}
