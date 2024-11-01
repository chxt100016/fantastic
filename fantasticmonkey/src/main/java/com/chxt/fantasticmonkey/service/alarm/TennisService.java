package com.chxt.fantasticmonkey.service.alarm;

import com.chxt.fantasticmonkey.domain.tennis.TennisCourtSelector;
import com.chxt.fantasticmonkey.domain.tennis.TennisReportGenerator;
import com.chxt.fantasticmonkey.infrastructure.env.EnvRepository;
import com.chxt.fantasticmonkey.infrastructure.huanglong.HuangLongClient;
import com.chxt.fantasticmonkey.infrastructure.tennis.TennisTmpRepository;
import com.chxt.fantasticmonkey.model.property.TennisProperty;
import com.chxt.fantasticmonkey.model.tennis.TennisCourt;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class TennisService {

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource
    private HuangLongClient huangLongClient;

    @Resource
    private EnvRepository EnvRepository;

    @Resource
    private TennisTmpRepository tmpRepository;

    @Value("${alarm.tennis.enabled}")
    public boolean enabled;


    public void BookInfoQueryJob() {
        if (!this.enabled) return;
        this.BookInfoQuery();
    }

    public void BookInfoQuery() {
        TennisProperty property = this.EnvRepository.getTennisReport();

        // 获取网球场信息
        List<TennisCourt> tennisCourts = this.huangLongClient.getOutdoorAndIndoor(property.getDayRange());
        // 过滤需要的场地
        TennisCourtSelector selector = new TennisCourtSelector(tennisCourts, property);
        List<TennisCourt> targetCourt = selector.getTargetCourt();
        if (targetCourt.isEmpty()) {
            return;
        }

        // 校验是否有新增的场地
        boolean hasIncrement = this.tmpRepository.checkHasIncrement(targetCourt);

        // 有新增场地 生成图片
        if (hasIncrement) {
            List<TennisCourt> lastDiff = this.tmpRepository.getLastDiff();
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            TennisReportGenerator generator = new TennisReportGenerator(configuration, targetCourt, lastDiff);
            byte[] image = generator.get();
            this.tmpRepository.setImage(image);
        }

    }

    public byte[] getImage() {
        return this.tmpRepository.getImage();
    }

    public boolean getAlarmSensor() {
        return this.tmpRepository.getAlarmSensor();
    }




}
