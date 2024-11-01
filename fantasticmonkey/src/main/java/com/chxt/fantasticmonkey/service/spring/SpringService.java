package com.chxt.fantasticmonkey.service.spring;

import com.alibaba.fastjson.JSON;
import com.chxt.fantastic.common.http.HttpOperator;
import com.chxt.fantasticmonkey.domain.spring.Inner;
import com.chxt.fantasticmonkey.domain.spring.Outer;
import com.chxt.fantasticmonkey.domain.spring.SpringEnum;
import com.chxt.fantasticmonkey.infrastructure.spring.SpringDO;
import com.chxt.fantasticmonkey.infrastructure.spring.SpringRepository;
import com.chxt.fantasticmonkey.infrastructure.transmission.Transmission2Client;
import com.chxt.fantasticmonkey.model.transmission.TorrentRequest2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpringService {

    @Resource
    private SpringRepository springRepository;

    @Resource
    private Transmission2Client transmission2Client;


    public void boot() {
        for (SpringEnum value : SpringEnum.values()) {
            this.boot(value);
        }
    }

    public void boot(SpringEnum value) {
        String prefix = value.getUrl().substring(0, value.getUrl().lastIndexOf("/") + 1);

        String html = new HttpOperator().uri(value.getUrl()).doGet().result();
        Outer outer = new Outer(prefix, html);
        List<Outer.Info> rows = outer.getList();

        log.info("{}, rows: {}", value.getType(), JSON.toJSONString(rows));
        for (int i = 0; i < rows.size(); i++) {
            Outer.Info row = rows.get(i);
            log.info("{}, {}/{}", value.getType(), i + 1, rows.size());
            if (CollectionUtils.isNotEmpty(this.springRepository.findByTitle(row.getTitle()))) {
                continue;
            }

            try {
                String innerHtml = new HttpOperator().uri(row.getUrl()).doGet().result();
                Inner inner = new Inner(prefix, innerHtml);

                Inner.Info detail = inner.getDetail();

                SpringDO data = SpringDO.builder()
                        .url(value.getUrl())
                        .type(value.getType())
                        .title(row.getTitle())
                        .name(row.getTitle())
                        .images(detail.getImages().stream().map(item -> new Binary(BsonBinarySubType.BINARY, item)).collect(Collectors.toList()))
                        .imageSize(detail.getImages().size())
                        .torrents(detail.getTorrentUrls())
                        .magnets(detail.getMagnets())
                        .status("init")
                        .build();
                this.springRepository.insert(data);
            } catch (Exception e) {
                log.error("inner error: {}, info: {}", e.getMessage(), JSON.toJSONString(row), e);
            }

        }
    }

    public byte[] getPic(String id, Integer index) {
        SpringDO springDO = springRepository.findById(id).get();
        return springDO.getImages().get(index < springDO.getImages().size() ? index : springDO.getImages().size() - 1).getData();
    }

    public List<SpringDO> list(String status, String type) {
        if (StringUtils.isBlank(status)) {
            status = "init";
        }


        if (type == null) {
            return this.springRepository.findByStatus(status);
        } else {
            return this.springRepository.findByStatusAndType(status, type);
        }
    }

    public void check(String id) {
        SpringDO springDO = this.springRepository.findById(id).get();
        for (String magnet : springDO.getMagnets()) {
            Map<String, String> args = new HashMap<>();
            args.put("filename", magnet);
            TorrentRequest2 req = TorrentRequest2.builder()
                    .arguments(args)
                    .method("torrent-add")
                    .build();
            this.transmission2Client.add(req);
        }

        springDO.setStatus("check");
        this.springRepository.save(springDO);
    }


    public void close(String id) {
        SpringDO springDO = this.springRepository.findById(id).get();
        springDO.setStatus("close");
        this.springRepository.save(springDO);
    }

    public void delete(String id) {
        SpringDO springDO = this.springRepository.findById(id).get();
        springDO.setStatus("delete");
        this.springRepository.save(springDO);
    }

    public void star(String id) {
        SpringDO springDO = this.springRepository.findById(id).get();
        springDO.setStatus("star");
        this.springRepository.save(springDO);
    }
}
