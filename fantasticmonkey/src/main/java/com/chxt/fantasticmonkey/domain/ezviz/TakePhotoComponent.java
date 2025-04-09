package com.chxt.fantasticmonkey.domain.ezviz;

import com.chxt.fantasticmonkey.model.ezviz.CaptureResponse;
import com.chxt.fantasticmonkey.domain.token.TokenEnum;
import com.chxt.fantasticmonkey.domain.token.TokenFactory;
import com.chxt.fantasticmonkey.infrastructure.ezviz.EzvizClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TakePhotoComponent {

    @Resource
    private EzvizClient ezvizClient;

    public List<String> execute(String device, int times, int interval){
        ArrayList<String> res = new ArrayList<>();
        // 抓照片
        for (int i = 0; i < times; i++) {
            CaptureResponse captureRes = this.ezvizClient.capture(device, TokenFactory.innerStore(TokenEnum.EZVIZ));
            res.add(captureRes.getData().getPicUrl());
            if (i != times - 1){
                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return res;
    }
}
