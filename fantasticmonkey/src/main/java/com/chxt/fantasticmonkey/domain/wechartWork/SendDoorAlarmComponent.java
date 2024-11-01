package com.chxt.fantasticmonkey.domain.wechartWork;

import com.chxt.fantasticmonkey.model.token.TokenItem;
import com.chxt.fantasticmonkey.domain.token.TokenEnum;
import com.chxt.fantasticmonkey.domain.token.TokenFactory;
import com.chxt.fantasticmonkey.infrastructure.wechatWork.WechatWorkClient;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Component
public class SendDoorAlarmComponent {

    @Resource
    private WechatWorkClient wechatWorkClient;

    public Boolean execute(List<String> pictureList, boolean videoEnabled){
        TokenItem ezvizToken = TokenFactory.innerStore(TokenEnum.EZVIZ);

        Date now = new Date();
        List<Map<String, String>> articles = new ArrayList<>();
        for (int i = 0; i < pictureList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            String date = DateFormatUtils.format(now, "MM月dd日 HH时mm分");
            if (i == 0 && videoEnabled) {
                Date addMinutes = DateUtils.addMinutes(now, 1);
                String format = "yyyyMMddHHmmss";
                String ezopen = String.format("ezopen://open.ys7.com/G69552993/1.rec?begin=%s&end=%s", DateFormatUtils.format(now, format), DateFormatUtils.format(addMinutes, format));
                String url;
                try {
                    url = String.format("https://open.ys7.com/ezopen/h5/iframe?url=%s&accessToken=%s&audio=1", URLEncoder.encode(ezopen, "utf-8"), ezvizToken.getAccessToken());
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

                map.put("title", date + "点击查看回放.");
                map.put("url", url);
            }
            else {
                map.put("title", "抓拍" + (i+1));
                map.put("url", pictureList.get(i));
            }
            map.put("picurl", pictureList.get(i));

            articles.add(map);
        }

        this.wechatWorkClient.appNews(articles, TokenFactory.innerStore(TokenEnum.WECHAT_WORK_ALARM));
        return true;
    }
}
