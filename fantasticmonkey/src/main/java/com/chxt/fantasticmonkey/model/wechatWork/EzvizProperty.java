package com.chxt.fantasticmonkey.model.wechatWork;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("ezviz")
@Data
public class EzvizProperty {
    private Integer times;
    private Integer interval;
    private List<String> include;
}
