package com.chxt.fantasticmonkey.controller.download.v2;

import com.alibaba.fastjson.TypeReference;
import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.infrastructure.transmission.Transmission2Client;
import com.chxt.fantasticmonkey.model.transmission.Torrents;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SpringJob {

    @Resource
    private Transmission2Client transmission2Client;

//    @Resource
//    private DownloadInfoMapper mapper;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void checkDownloading() {
        List<Torrents> torrents = this.transmission2Client.get();
        torrents = torrents.stream().filter(item -> item.getPercentDone() == 1.0).collect(Collectors.toList());

        for (Torrents torrent : torrents) {
//            LambdaQueryWrapper<DownloadInfo> q = new LambdaQueryWrapper<DownloadInfo>();
//            q.in(DownloadInfo::getTorrentName, torrent.getName());
            List<String> name = new HttpOperator().uri("http://2202.com:9999/video/search").entity("name", torrent.getName()).jsonHeader().doPost().result(new TypeReference<List<String>>() {});
//            DownloadInfo complete = DownloadInfo.builder().status("complete").path(JSON.toJSONString(name)).build();
//            mapper.update(complete, q);

            // 删除torrent
            this.transmission2Client.remove(Collections.singletonList(torrent.getId()));
        }
    }
}
