package com.chxt.fantasticmonkey.domain.convert;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ConvertChainManager {

    @Resource
    private List<KeywordConvert> keywordConvertList;

    public String keywordConvert(String data) {
        for (KeywordConvert keywordConvert : this.keywordConvertList) {
            if (keywordConvert.preCheck(data)) data = keywordConvert.convert(data);
        }
        return data;
    }

}
